package com.github.avidyalalala.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.avidyalalala.common.ErrorCode;
import com.github.avidyalalala.common.TrafficSyncJson;
import com.github.avidyalalala.config.AutoConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: lina.hou
 * Date: 14-7-28
 * Time: 上午11:36
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
@Component("validationSupporter")
public class ValidationSupporter {

    @Resource
    public AutoConfig autoConfig;

    private static final String APP_SEPARATOR =";";

    //国内话费判断,NETWORD_ID
    private static final String IDF_GUONEI ="460";

    //中国联通分配给阿里通信的标志位
    private static final String IDF_ALITONGXIN ="405";
    private static final Pattern patternAliTongxin = Pattern.compile(".{8}(8"+IDF_ALITONGXIN+").*?");
    public static final String DATE_PATTERN="yyyy-MM-dd HH:mm:ss";
    private static final SimpleDateFormat dateFormatter =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * APP_INFO:[{APP_NAME:"1405581785072", TRAFFIC_DATA:"460025571200644"},{}]检查里面的每个APP_NAME 与 TRAFFIC_DATA 不能为空
     * @param appArr
     * @return
     */
    public ErrorCode checkEveryAppTraffic(JSONArray appArr){

        String apps = autoConfig.getQualifiedAppList();
        List<String> appList = Arrays.asList(apps.split(APP_SEPARATOR));

        for(int i=0;i<appArr.size();i++){
            JSONObject single = appArr.getJSONObject(i);

            String appName = single.getString(TrafficSyncJson.APP_NAME);

            if(StringUtils.isBlank(appName)||!appList.contains(appName)){
                return ErrorCode.FAIL_BIZ_APP_NAME_ILLEGAL;
            }
            if(0L== NumberUtils.toLong(single.getString(TrafficSyncJson.TRAFFIC_DATA))){
                return ErrorCode.FAIL_BIZ_TRAFFIC_DATA_ILLEGAL;
            }
        }
        return null;
    }

    /** ICCID编码规则为：
     8986+01（接入网 号）+Y1Y2（年份）+8+B1B2S（转售企业代码）+XXXXXXX（七位序列号）+C（1位卡商代码）
     其中的B1B2S：转售企业代码为三位数字，由中国联通统一分配，阿里通信的为405。据此可以判断为 阿里通信的SIM卡。**/
    public static boolean isICCIDLegal(String ICCID){
        if(!StringUtils.isBlank(ICCID)){
            Matcher matcher = patternAliTongxin.matcher(ICCID);
            return matcher.find();
        }
        return false;
    }

    /**
     * @param netWorkID
     * 是否是国内话费
     * @return
     */

    public static boolean isNetWorkIDLegal(String netWorkID){
        if(StringUtils.startsWith(netWorkID,IDF_GUONEI)){
            return true;
        }
        return false;
    }

    /**
     * 校验 日期格式是否正确，成功则返回 Date，失败返回null
     * @param date
     * @return
     */
    public static Date formateDate(String date){
        try{
            Date r = dateFormatter.parse(date);
            return r;
        }catch(Exception e){
            log.info(e.toString());
        }
        return null;
    }

    /**
     * 账单时间的格式 必须为 yyyy-MM-dd hh:mm:ss SSS, 且不能小于等于开始计费时间
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean isBillTimeLegal(Date beginTime, Date endTime){

        if (null != endTime) {
            long re = endTime.getTime() - beginTime.getTime();
            if (0 < re) {
                return true;
            }
        }
        return false;
    }



}
