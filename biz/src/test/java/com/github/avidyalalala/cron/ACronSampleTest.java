package com.github.avidyalalala.cron;

import com.github.avidyalalala.config.AutoConfig;
import com.github.avidyalalala.dal.dao.TrafficInfoDAO;
import com.github.avidyalalala.pojo.TrafficInfo;
import com.github.avidyalalala.ftp.FtpWrapper;
import junit.framework.Assert;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: lina.hou
 * Date: 14-9-18
 * Time: 下午12:09
 * To change this template use File | Settings | File Templates.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({FileUtils.class})
public class ACronSampleTest {
    @InjectMocks
    private ACronSample aCronSample;
    @Mock
    public AutoConfig autoConfig;
    @Mock
    public TrafficInfoDAO trafficInfoDAO;
    @Mock
    public FtpWrapper aliTongXinSupport;

    private void mockFileUtilsWriteFile() throws IOException {
        PowerMockito.when(trafficInfoDAO.findRecordsByCreateTime(Mockito.any(Date.class),
                Mockito.any(Date.class), Mockito.anyInt(), Mockito.anyInt())
        ).thenReturn(new ArrayList<TrafficInfo>());

        PowerMockito.mockStatic(FileUtils.class);
        PowerMockito.doNothing().when(FileUtils.class);
        FileUtils.writeLines( Mockito.any(File.class),  Mockito.any(Collection.class), Mockito.anyBoolean());

    }

    @Test
    public void test_selectAndWriteToFile() throws Exception {

       mockFileUtilsWriteFile();

       File reFile = aCronSample.selectAndWriteToFile(new Date());
       Assert.assertEquals(0, reFile.length());
    }


    /**
     * 这是唯一有用的测试用例，防止cron表达式被干掉
     * @throws UnknownHostException
     * @throws NoSuchMethodException
     */
    @Test
    public void test_syncToAliTongxin_cronExpression() throws UnknownHostException, NoSuchMethodException {

        Method syncToAliTongxin = aCronSample.getClass().getMethod("syncToAliTongxin");
        String cronExpression=syncToAliTongxin.getAnnotation(Scheduled.class).cron();
        Assert.assertEquals("0 7 7 */1 * ?", cronExpression);
    }

    @Test
    public void test_syncToAliTongxin() throws IOException, NoSuchMethodException {
        mockFileUtilsWriteFile();
        //PowerMockito.mockStatic(InetAddress.class);
        String hostName=InetAddress.getLocalHost().getHostName();
        PowerMockito.when(autoConfig.getCronHostName()).thenReturn(hostName);

        PowerMockito.doNothing().when(aliTongXinSupport).upLoadToFtp(Mockito.any(File.class));

        aCronSample.createFileEveryMorning();
        //Assert.assertEquals("0 7 */1 * * ?", cronExpression);
    }
}
