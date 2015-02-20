/*
 * Copyright (C) 2015 BonitaSoft S.A.
 * BonitaSoft, 32 rue Gustave Eiffel - 38000 Grenoble
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2.0 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.bonitasoft.engine.api.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.bonitasoft.engine.core.form.FormMappingService;
import org.bonitasoft.engine.core.form.impl.SFormMappingImpl;
import org.bonitasoft.engine.form.mapping.FormMapping;
import org.bonitasoft.engine.form.mapping.FormMappingType;
import org.bonitasoft.engine.service.TenantServiceAccessor;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProcessConfigurationAPIImplTest {

    private static final long PROCESS_DEF_ID = 158l;
    private static final long FORM_MAPPING_ID = 458l;
    @Mock
    public FormMappingService formMappingService;
    @Mock
    public TenantServiceAccessor tenantServiceAccessor;
    @Spy
    public ProcessConfigurationAPIImpl processConfigurationAPI;

    @Before
    public void setUp() throws Exception {
        doReturn(tenantServiceAccessor).when(processConfigurationAPI).getTenantAccessor();
        doReturn(formMappingService).when(tenantServiceAccessor).getFormMappingService();

    }

    @Ignore
    @Test
    public void testSearchFormMappings() throws Exception {
    }


    @Test
    public void testGetProcessStartForm() throws Exception {
        //given
        SFormMappingImpl sFormMapping = createSFormMapping(FormMappingType.PROCESS_START.name(), FORM_MAPPING_ID, false, "myForm", PROCESS_DEF_ID);
        doReturn(sFormMapping).when(formMappingService).get(PROCESS_DEF_ID, FormMappingType.PROCESS_START.name());
        //when
        FormMapping processStartForm = processConfigurationAPI.getProcessStartForm(PROCESS_DEF_ID);
        //then
        verify(formMappingService, times(1)).get(PROCESS_DEF_ID, FormMappingType.PROCESS_START.name());
        assertThat(processStartForm.getProcessDefinitionId()).isEqualTo(PROCESS_DEF_ID);
        assertThat(processStartForm.getType()).isEqualTo(FormMappingType.PROCESS_START);
        assertThat(processStartForm.isExternal()).isFalse();
        assertThat(processStartForm.getForm()).isEqualTo("myForm");
        assertThat(processStartForm.getId()).isEqualTo(FORM_MAPPING_ID);
    }

    SFormMappingImpl createSFormMapping(String name, long formMappingId, boolean isExternal, String myForm1, long processDefinitionId) {
        SFormMappingImpl sFormMapping = new SFormMappingImpl();
        sFormMapping.setId(formMappingId);
        sFormMapping.setProcessDefinitionId(processDefinitionId);
        sFormMapping.setType(name);
        sFormMapping.setIsExternal(isExternal);
        sFormMapping.setForm(myForm1);
        return sFormMapping;
    }

    @Ignore
    @Test
    public void testGetProcessOverviewForm() throws Exception {

    }

    @Ignore
    @Test
    public void testGetHumanTaskForm() throws Exception {

    }

    @Ignore
    @Test
    public void testUpdateFormMapping() throws Exception {

    }
}
