package com.github.avidyalalala.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created with IntelliJ IDEA.
 * User: lina.hou
 * Date: 14-7-29
 * Time: 下午4:10
 * To change this template use File | Settings | File Templates.
 */
@RunWith(MockitoJUnitRunner.class)
public class DiamondConfigImplTest {
    @InjectMocks
    DiamondConfigImpl diamondConfig;
    @Mock
    AutoConfig autoConfig;

    @Test
    public void test_handleConfig(){

        Mockito.when(autoConfig.getQualifiedAppList()).thenReturn("qualifiedApplist_lalala");
        Mockito.when(autoConfig.getCronHostName()).thenReturn("lalala");

        String configInfo="cronHostName=v101105145.sqa.zmf.tbsite.net\nqualifiedAppList=com.taobao.taobao;com.eg.android.AlipayGphone;alicom.palm.android;com.rytong.bankps;com.rytong.bankps_bj";
        diamondConfig.handleConfig(configInfo);

    }

}
