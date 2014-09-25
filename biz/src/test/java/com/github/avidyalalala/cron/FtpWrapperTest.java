package com.github.avidyalalala.cron;

import com.github.avidyalalala.config.AutoConfig;
import com.github.avidyalalala.utils.FtpUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: lina.hou
 * Date: 14-9-18
 * Time: 下午5:35
 * To change this template use File | Settings | File Templates.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({FtpUtils.class})
public class FtpWrapperTest {
    @InjectMocks
    private FtpWrapper ftpWrapper;
    @Mock
    public AutoConfig autoConfig;

    @Test
    public void test_upLoadToFtp(){
        PowerMockito.mockStatic(FtpUtils.class);
        PowerMockito.when(FtpUtils.uploadFile(Mockito.any(File.class), Mockito.anyString(),
                Mockito.anyString(), Mockito.anyString(),Mockito.anyString()))
                .thenReturn(true);
        ftpWrapper.upLoadToFtp(new File(""));

    }
}
