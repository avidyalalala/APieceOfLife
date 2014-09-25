package com.github.avidyalalala.service;

import com.alibaba.fastjson.JSONArray;
import com.github.avidyalalala.common.ErrorCode;
import com.github.avidyalalala.config.AutoConfig;
import junit.framework.Assert;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: lina.hou
 * Date: 14-7-28
 * Time: 下午12:02
 * To change this template use File | Settings | File Templates.
 */
@RunWith(MockitoJUnitRunner.class)
public class validationSupporterTest {

    @InjectMocks
    public ValidationSupporter validationSupporter;
    @Mock
    AutoConfig autoConfig;

    @Test
    public void test_isICCIDLegal(){
        String ICCID="123456788405LLLLLL";
        boolean re = validationSupporter.isICCIDLegal(ICCID);
        Assert.assertEquals(re, true);

        String wrongICCID="898608405SSXXXXXXXXP";
        boolean wrong = validationSupporter.isICCIDLegal(wrongICCID);
        Assert.assertEquals(wrong,false);

    }

    @Test
    public void test_formateDate(){
        String dateStr="2014-09-19 09:09:09";
        Date date = validationSupporter.formateDate(dateStr);
        Assert.assertEquals(dateStr,DateFormatUtils.format(date,ValidationSupporter.DATE_PATTERN));
    }

    @Test
    public void test_isNetWorkIDLegal(){
        String netWorkId="460uffda";
        boolean re = validationSupporter.isNetWorkIDLegal(netWorkId);
        Assert.assertEquals(re,true);
        boolean wrong = validationSupporter.isNetWorkIDLegal("408fda");
        Assert.assertEquals(wrong,false);
    }

    @Test
    public void test_isBillTimeLegal(){
        Date start = new Date();
        Date end = DateUtils.addHours(start, 1);
        boolean re = validationSupporter.isBillTimeLegal(start, end);
        Assert.assertEquals(re, true);
    }

    @Test
    public void test_checkEveryAppTraffic(){

        Mockito.when(autoConfig.getQualifiedAppList()).thenReturn("com.taobao.taobao;com.eg.android.AlipayGphone;alicom.palm.android;com.rytong.bankps;com.rytong.bankps_bj");

        String appArrStr="[{APP_NAME:\"com.taobao.taobao\", TRAFFIC_DATA:\"555\"}," +
                "{APP_NAME:\"com.taobao.taobao\", TRAFFIC_DATA:\"888\"}]";
        JSONArray ja = JSONArray.parseArray(appArrStr);
        ErrorCode re = validationSupporter.checkEveryAppTraffic(ja);
        Assert.assertEquals(re,null);

        String appArrStr_wrong="[{APP_NAME:\"com.taobao.taobao\", TRAFFIC_DATA:\"555\"}," +
                "{APP_NAME:\"com.taobao\", TRAFFIC_DATA:\"\"}]";
        JSONArray jaWrong = JSONArray.parseArray(appArrStr_wrong);
        ErrorCode reWrong = validationSupporter.checkEveryAppTraffic(jaWrong);
        Assert.assertEquals(ErrorCode.FAIL_BIZ_APP_NAME_ILLEGAL, reWrong);

        String appArrStr_blank="[{APP_NAME:\"com.taobao.taobao\", TRAFFIC_DATA:\"\"}]";
        JSONArray jaBlank = JSONArray.parseArray(appArrStr_blank);
        ErrorCode reBlank = validationSupporter.checkEveryAppTraffic(jaBlank);
        Assert.assertEquals(reBlank,ErrorCode.FAIL_BIZ_TRAFFIC_DATA_ILLEGAL);

    }

}
