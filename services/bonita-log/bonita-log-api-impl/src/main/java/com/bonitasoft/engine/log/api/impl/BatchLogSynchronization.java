/*******************************************************************************
 * Copyright (C) 2009, 2014 BonitaSoft S.A.
 * BonitaSoft is a trademark of BonitaSoft SA.
 * This software file is BONITASOFT CONFIDENTIAL. Not For Distribution.
 * For commercial licensing information, contact:
 * BonitaSoft, 32 rue Gustave Eiffel – 38000 Grenoble
 * or BonitaSoft US, 51 Federal Street, Suite 305, San Francisco, CA 94107
 *******************************************************************************/
package com.bonitasoft.engine.log.api.impl;

import java.util.ArrayList;
import java.util.List;

import org.bonitasoft.engine.persistence.PersistentObject;
import org.bonitasoft.engine.queriablelogger.model.SQueriableLog;
import org.bonitasoft.engine.services.PersistenceService;
import org.bonitasoft.engine.services.SPersistenceException;
import org.bonitasoft.engine.transaction.BonitaTransactionSynchronization;
import org.bonitasoft.engine.transaction.TransactionState;

/**
 * @author Baptiste Mesta
 * @author Matthieu Chaffotte
 * @author Elias Ricken de Medeiros
 */
class BatchLogSynchronization implements BonitaTransactionSynchronization {

    private final PersistenceService persistenceService;

    private final boolean delayable;

    private Exception exception;

    private BatchLogBuffer batchLogBuffer;

    private InsertBatchLogsJobRegister jobRegister;

    private final ThreadLocal<BatchLogSynchronization> synchronizations = new ThreadLocal<BatchLogSynchronization>();

    private final List<SQueriableLog> logs = new ArrayList<SQueriableLog>();

    public BatchLogSynchronization(PersistenceService persistenceService, BatchLogBuffer batchLogBuffer, InsertBatchLogsJobRegister jobRegister,
            boolean delayable) {
        super();
        this.persistenceService = persistenceService;
        this.jobRegister = jobRegister;
        this.delayable = delayable;
        this.batchLogBuffer = batchLogBuffer;
    }

    @Override
    public void afterCompletion(final TransactionState transactionState) {
        if (delayable && TransactionState.COMMITTED == transactionState) {
            batchLogBuffer.addLogs(this.logs);
            jobRegister.registerJobIfNotRegistered();
        }
        synchronizations.remove();
    }

    @Override
    public void beforeCommit() {
        if (!delayable) {
            if (this.logs != null && !this.logs.isEmpty()) {
                try {
                    persistenceService.insertInBatch(new ArrayList<PersistentObject>(this.logs));
                    persistenceService.flushStatements();
                } catch (final SPersistenceException e) {
                    this.exception = e;
                    // FIXME what to do?
                } finally {
                    this.logs.clear();
                }
            }
        }
    }

    public Exception getException() {
        return this.exception;
    }

    public void addLog(final SQueriableLog sQueriableLog) {
        // no synchronized required as we are working on a threadLocal
        this.logs.add(sQueriableLog);
    }

}
