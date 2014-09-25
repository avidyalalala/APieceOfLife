package com.github.avidyalalala.service;

import com.github.avidyalalala.common.Result;
import com.github.avidyalalala.config.AutoConfig;
import com.github.avidyalalala.dal.dao.TrafficInfoDAO;
import junit.framework.Assert;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: lina.hou
 * Date: 14-9-18
 * Time: 下午6:25
 * To change this template use File | Settings | File Templates.
 */
@RunWith(PowerMockRunner.class)
public class ConditionPullImplTest {
    @InjectMocks
    ConditionPullImpl conditionPull;

    @Mock
    AutoConfig autoConfig;

    @Test
    public void test_getAppList() {
        PowerMockito.when(autoConfig.getQualifiedAppList()).thenReturn("com.taobao.taobao;com.taobao.alipay");
        Result<Map<String, List<String>>> re = conditionPull.getAppList();
        Assert.assertEquals(true,re.isSuccess());
        Assert.assertEquals(2,re.getModel().get("appList").size());
    }
}
