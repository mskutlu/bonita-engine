/**
 * Copyright (C) 2015 BonitaSoft S.A.
 * BonitaSoft, 32 rue Gustave Eiffel - 38000 Grenoble
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation
 * version 2.1 of the License.
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License along with this
 * program; if not, write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth
 * Floor, Boston, MA 02110-1301, USA.
 **/
package org.bonitasoft.engine.core.process.instance.model.archive.impl;

import org.bonitasoft.engine.core.process.definition.model.SFlowNodeType;
import org.bonitasoft.engine.core.process.instance.model.SAutomaticTaskInstance;
import org.bonitasoft.engine.core.process.instance.model.archive.SAAutomaticTaskInstance;
import org.bonitasoft.engine.persistence.PersistentObject;

/**
 * @author Baptiste Mesta
 * @author Matthieu Chaffotte
 */
public class SAAutomaticTaskInstanceImpl extends SAActivityInstanceImpl implements SAAutomaticTaskInstance {

    private static final long serialVersionUID = -2264363298375029324L;

    public SAAutomaticTaskInstanceImpl() {
        super();
    }

    public SAAutomaticTaskInstanceImpl(final SAutomaticTaskInstance sAutomaticTaskInstance) {
        super(sAutomaticTaskInstance);
    }

    @Override
    public SFlowNodeType getType() {
        return SFlowNodeType.AUTOMATIC_TASK;
    }

    @Override
    public String getDiscriminator() {
        return SAAutomaticTaskInstanceImpl.class.getName();
    }

    @Override
    public String getKind() {
        return "auto";
    }

    @Override
    public Class<? extends PersistentObject> getPersistentObjectInterface() {
        return SAutomaticTaskInstance.class;
    }

}
