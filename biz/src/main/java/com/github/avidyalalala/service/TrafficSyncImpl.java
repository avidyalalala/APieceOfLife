package com.github.avidyalalala.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.avidyalalala.common.ErrorCode;
import com.github.avidyalalala.common.Result;
import com.github.avidyalalala.dal.dao.TrafficInfoDAO;
import com.github.avidyalalala.hsf.HsfService;
import com.github.avidyalalala.pojo.TrafficInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * User: lina.hou
 * Date: 14-7-11
 * Time: 下午2:11
 * {ICCID:"123456788405I",IMSI:"GGG",NETWORK_ID:"460000",START_TIME:"2014-07-18 18:16:30 111",BILL_TIME:"2014-07-18 18:17:30 111",TOTAL_TRAFFIC_DATA:"666",APP_INFO:[{APP_NAME:"33", TRAFFIC_DATA:"555"}]}
 *
 */

@HsfService(TrafficSync.class)
@Component("trafficSync")
public class TrafficSyncImpl implements TrafficSync {
    private static final Logger log = LoggerFactory.getLogger("netLogger");
    @Resource
    public TrafficInfoDAO trafficInfoDAO;

    @Resource
    public ValidationSupporter validationSupporter;

    @Override
    public Result<String> syncTrafficInfoViaJson(String json) {

        log.info("TrafficSync.syncTrafficInfoViaJson  json:{}"+json);
        Result<String> result = new Result<String>();
        result.setSuccess(true);

        Result<TrafficInfo> vaRe = validate(json);

        result.setMsgCode(vaRe.getMsgCode());
        result.setMsgInfo(vaRe.getMsgInfo());
        result.setSuccess(vaRe.isSuccess());

        if(vaRe.isSuccess()){

            TrafficInfo ti=vaRe.getModel();
            log.info("prepare to insert into Database"+ ToStringBuilder.reflectionToString(ti));
            JSONArray taiArr = ti.getAPP_INFO();
           for(int i=0;i<taiArr.size();i++){
               JSONObject tai = taiArr.getJSONObject(i);
               ti.setAppName("name");
              // ti.setTrafficData("trafficData");
               //恶心的逻辑，由于客户端获取不到上传详单本身app的流量，所以我们上传详单的应用的第一个app上加1KB
               //或者增加一个上传流量的app，将流量设置为 1K
               if(i==0){ti.setTrafficData(ti.getTrafficData()+1);}

               try{
               int re = trafficInfoDAO.insert(ti);
               if(0L == re){
                   log.info("insert database: has duplicate PrimaryKey ,id:"+ti.getId()+" IMSI:"+ti.getImsi()+",appName: "+ti.getAppName());
               }else{
                   log.info("insert database success ,id:"+ti.getId()+"  IMSI:"+ti.getImsi()+",appName: "+ti.getAppName());
               }
               }catch (Exception e){
                   log.info("insert database failed , IMSI:"+ti.getImsi()+",appName: "+ti.getAppName());
                   setResultErrorInfo(result, ErrorCode.FAIL_BIZ_DATABASE_ERROR);
               }
           }

        }

        log.info("TrafficSync.syncTrafficInfoViaJson . the response object is : "+ToStringBuilder.reflectionToString(result));
        return result;
    }

    /**
     * 校验json串，并将整理好的结果作为result.getModel()返回
     * @param json
     * @return
     */
    public Result<TrafficInfo> validate(String json){

        Result<TrafficInfo> result = new Result<TrafficInfo>();
        result.setSuccess(true);
        try{

            TrafficInfo trafficInfo = JSONObject.parseObject(json, TrafficInfo.class);
            result.setModel(trafficInfo);

            log.info("Parse Json success.start to validation: "+trafficInfo.getImsi());
            /**验证IMSI是否为空**/
            if(StringUtils.isBlank(trafficInfo.getImsi())){
                setResultErrorInfo(result, ErrorCode.FAIL_BIZ_IMSI_BLANK_ERROR);
                return result;
            }

            /** ICCID 不能为空 **/
            if(StringUtils.isBlank(trafficInfo.getIccid())){
                setResultErrorInfo(result, ErrorCode.FAIL_BIZ_ICCID_BLANK_ERROR);
                return result;
            }

            /** ICCID 不合法 **/
            if(!validationSupporter.isICCIDLegal(trafficInfo.getIccid())){
                setResultErrorInfo(result, ErrorCode.FAIL_BIZ_ICCID_ILLEGAL);
                return result;
            }


            /** NETWORK_ID 不能为空**/
            if(StringUtils.isBlank(trafficInfo.getNetworkId())){
                setResultErrorInfo(result,ErrorCode.FAIL_BIZ_NETWORK_ID_BLANK_ERROR);
                return result;
            }

            /** NETWORK_ID 不合法,必须为国内话费 **/
            if(!validationSupporter.isNetWorkIDLegal(trafficInfo.getNetworkId())){
                setResultErrorInfo(result,ErrorCode.FAIL_BIZ_NETWORK_ID_ILLEGAL);
                return result;
            }

            /** 账单开始计费时间不能为空**/
            Date startTime = DateUtils.parseDate(trafficInfo.getSTART_TIME(), new String[]{validationSupporter.DATE_PATTERN});
            trafficInfo.setStartTime(startTime);

            if(null==startTime ){
                setResultErrorInfo(result,ErrorCode.FAIL_BIZ_START_TIME_ILLEGAL);
                return result;
            }

            Date billTime = validationSupporter.formateDate(trafficInfo.getBILL_TIME());
            trafficInfo.setBillTime(billTime);

            if(!validationSupporter.isBillTimeLegal(startTime, billTime)){
                setResultErrorInfo(result,ErrorCode.FAIL_BIZ_BILL_TIME_ILLEGAL);
                return result;
            }

            /** 此月开始产生的总流量不能为空，0，或 不能转成数字的字符串 **/
            if(0L==trafficInfo.getTotalTrafficData()){
                setResultErrorInfo(result,ErrorCode.FAIL_BIZ_TOTAL_TRAFFIC_DATA_ILLEGAL);
                return result;
            }

            /**APP_INFO 不能为空**/
            if(null==trafficInfo.getAPP_INFO() || 0==trafficInfo.getAPP_INFO().size()){
                setResultErrorInfo(result,ErrorCode.FAIL_BIZ_APP_INFO_BLANK_ERROR);
                return result;
            }

            /**校验每个app 的名称和流量 **/
            ErrorCode appRe=validationSupporter.checkEveryAppTraffic(trafficInfo.getAPP_INFO());
            if(null!=appRe){
                setResultErrorInfo(result, appRe);
                return result;
            }

        }catch (Exception e){
            log.info(e.getMessage());
            setResultErrorInfo(result, ErrorCode.FAIL_BIZ_JSON_PARSE_ERROR);
        }
        return result;
    }


    private void setResultErrorInfo(Result result, ErrorCode errorCode){
        result.setSuccess(false);
        result.setMsgCode(errorCode.toString());
        result.setMsgInfo(errorCode.getDesc());
        log.info("validation can not pass: " + result.getMsgCode());
    }
}
