package com.bonitasoft.engine.bdm.server;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.io.File;

import org.junit.Test;

import com.bonitasoft.engine.bdm.AbstractBDMJarBuilder;
import com.bonitasoft.engine.bdm.BDMCompiler;
import com.bonitasoft.engine.bdm.BusinessObjectModel;
import com.bonitasoft.engine.io.IOUtils;

public class ServerBDMJarBuilderTest {

    @Test
    public void testGetPersistenceFileContentFor() throws Exception {
        // // given
        // final byte[] bomZip = "bomZip".getBytes();
        // final BDMCompiler compiler = mock(BDMCompiler.class);
        // final AbstractBDMJarBuilder builder = new ServerBDMJarBuilder(compiler);
        // final AbstractBDMJarBuilder spyBuilder = spy(builder);
        // final BusinessObjectModel model = new BusinessObjectModel();
        // final File tmpDir = IOUtils.createTempDirectory("bdm");
        // final byte[] jar = "jar".getBytes();
        // doReturn(model).when((AbstractBDMJarBuilder)spyBuilder).getBOM(bomZip);
        // doReturn(tmpDir).when(spyBuilder).createBDMTmpDir();
        // doNothing().when(spyBuilder).generateJavaFiles(model, tmpDir);
        // doReturn(jar).when(spyBuilder).generateJar(tmpDir);
        //
        // // when
        // spyBuilder.build(bomZip);
        //
        // // verify
        // verify(spyBuilder).getBOM(bomZip);
        // verify(spyBuilder).createBDMTmpDir();
        // verify(spyBuilder).generateJavaFiles(model, tmpDir);
        // verify(compiler).compile(tmpDir);
        // verify(spyBuilder).generateJar(tmpDir);
        // verify(spyBuilder).addPersistenceFile(tmpDir, model);
    }

}
