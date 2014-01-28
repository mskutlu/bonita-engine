/*******************************************************************************
 * Copyright (C) 2014 BonitaSoft S.A.
 * BonitaSoft is a trademark of BonitaSoft SA.
 * This software file is BONITASOFT CONFIDENTIAL. Not For Distribution.
 * For commercial licensing information, contact:
 * BonitaSoft, 32 rue Gustave Eiffel – 38000 Grenoble
 * or BonitaSoft US, 51 Federal Street, Suite 305, San Francisco, CA 94107
 *******************************************************************************/
package com.bonitasoft.engine.expression;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bonitasoft.engine.core.expression.control.model.SExpressionContext;
import org.bonitasoft.engine.core.process.instance.api.FlowNodeInstanceService;
import org.bonitasoft.engine.core.process.instance.model.SFlowNodeInstance;
import org.bonitasoft.engine.data.instance.api.DataInstanceContainer;
import org.bonitasoft.engine.expression.NonEmptyContentExpressionExecutorStrategy;
import org.bonitasoft.engine.expression.exception.SExpressionDependencyMissingException;
import org.bonitasoft.engine.expression.exception.SExpressionEvaluationException;
import org.bonitasoft.engine.expression.model.ExpressionKind;
import org.bonitasoft.engine.expression.model.SExpression;
import org.bonitasoft.engine.persistence.SBonitaReadException;

import com.bonitasoft.engine.business.data.BusinessDataRespository;
import com.bonitasoft.engine.core.process.instance.api.RefBusinessDataService;
import com.bonitasoft.engine.core.process.instance.model.SRefBusinessDataInstance;

/**
 * @author Colin Puy
 * @author Emmanuel Duchastenier
 */
public class BusinessDataExpressionExecutorStrategy extends NonEmptyContentExpressionExecutorStrategy {

    private final RefBusinessDataService refBusinessDataService;

    private final BusinessDataRespository businessDataRepository;

    private final FlowNodeInstanceService flowNodeInstanceService;

    public BusinessDataExpressionExecutorStrategy(final RefBusinessDataService refBusinessDataService, final BusinessDataRespository businessDataRepository,
            final FlowNodeInstanceService flowNodeInstanceService) {
        this.refBusinessDataService = refBusinessDataService;
        this.businessDataRepository = businessDataRepository;
        this.flowNodeInstanceService = flowNodeInstanceService;
    }

    @Override
    public ExpressionKind getExpressionKind() {
        return KIND_BUSINESS_DATA;
    }

    @Override
    public Object evaluate(final SExpression expression, final Map<String, Object> context, final Map<Integer, Object> resolvedExpressions)
            throws SExpressionDependencyMissingException, SExpressionEvaluationException {
        String bizDataName = expression.getContent();
        if (context.containsKey(bizDataName)) {
            return context.get(bizDataName);
        }
        try {
            long processInstanceId = getProcessInstanceId((Long) context.get(SExpressionContext.containerIdKey),
                    (String) context.get(SExpressionContext.containerTypeKey));
            SRefBusinessDataInstance refBusinessDataInstance = refBusinessDataService.getRefBusinessDataInstance(bizDataName, processInstanceId);
            Class<?> bizClass = Thread.currentThread().getContextClassLoader().loadClass(refBusinessDataInstance.getDataClassName());
            return businessDataRepository.find(bizClass, refBusinessDataInstance.getDataId());
        } catch (SBonitaReadException e) {
            throw new SExpressionEvaluationException("Unable to retrieve business data instance with name " + bizDataName);
        } catch (Exception e) {
            throw new SExpressionEvaluationException(e);
        }
    }

    /**
     * protected for testing
     */
    protected long getProcessInstanceId(final long containerId, final String containerType) throws SExpressionEvaluationException {
        if (DataInstanceContainer.PROCESS_INSTANCE.name().equals(containerType)) {
            return containerId;
        } else if (DataInstanceContainer.ACTIVITY_INSTANCE.name().equals(containerType)) {
            SFlowNodeInstance flowNodeInstance;
            try {
                flowNodeInstance = flowNodeInstanceService.getFlowNodeInstance(containerId);
                return flowNodeInstance.getParentProcessInstanceId();
            } catch (Exception e) {
                throw new SExpressionEvaluationException("Process instance id not found in context");
            }
        }
        throw new SExpressionEvaluationException("Invalid container type");
    }

    @Override
    public List<Object> evaluate(final List<SExpression> expressions, final Map<String, Object> context, final Map<Integer, Object> resolvedExpressions)
            throws SExpressionDependencyMissingException, SExpressionEvaluationException {
        List<Object> bizDatas = new ArrayList<Object>(expressions.size());
        List<String> alreadyEvaluatedExpressionContent = new ArrayList<String>();
        for (SExpression expression : expressions) {
            if (!alreadyEvaluatedExpressionContent.contains(expression.getContent())) {
                bizDatas.add(evaluate(expression, context, resolvedExpressions));
                alreadyEvaluatedExpressionContent.add(expression.getContent());
            }
        }
        return bizDatas;
    }

    @Override
    public boolean mustPutEvaluatedExpressionInContext() {
        return true;
    }

}
