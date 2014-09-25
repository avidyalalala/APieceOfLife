package com.github.avidyalalala.pojo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.github.avidyalalala.common.TrafficSyncJson;
import lombok.Data;

import java.util.Date;

/**
 * User: lina.hou
 * Date: 14-7-16
 * Time: 下午2:18
 */
@Data
public class TrafficInfo implements Cloneable {

    private long id;
    //sim卡唯一标示
    @JSONField(name= TrafficSyncJson.IMSI)
    private String imsi;
    //集成电路卡识别码
    @JSONField(name= TrafficSyncJson.ICCID)
    private String iccid;
    //网络类型id
    @JSONField(name = TrafficSyncJson.NETWORK_ID)
    private String networkId;
    //计费开始时间
    private Date startTime;
    //计费结束时间
    private Date billTime;
    //APP 的包名
    private String appName;
    //流量 单位:byte
    private long trafficData;
    //此月的总流量, 单位:byte
    @JSONField(name=TrafficSyncJson.TOTAL_TRAFFIC_DATA)
    private long totalTrafficData;
    //手机uuid
    @JSONField(name=TrafficSyncJson.UUID)
    private String uuid;
    //手机imei
    @JSONField(name = TrafficSyncJson.IMEI)
    private String imei;
    //用户kp
    @JSONField(name = TrafficSyncJson.KP)
    private String kp;
    private Date gmtCreate;
    private Date gmtModified;

    //此属性用来保存客户端传至服务端的json串，与数据库里字段无对应
    private JSONArray APP_INFO;
    //Json 串专用属性
    private String START_TIME;
    private String BILL_TIME;

    public static void main(String[] args) {

        String jsonStr="{KP:\"YUNOS LOGIN\",UUID:\"IT IS UUID\",IMEI:\"I AM IMAI\",ICCID:\"123456788405I\", IMSI:\"GGG\",NETWORK_ID:\"460\",UPLOAD_TIME:\"冗余项\"," +
                "BILL_TIME:\"2014-07-18 18:17:30 111\",START_TIME:\"2014-07-18 18:16:30 111\",TOTAL_TRAFFIC_DATA:\"666\"," +
                "APP_INFO:[{APP_NAME:\"com.taobao.taobao\", TRAFFIC_DATA:\"555\"}]}";

        TrafficInfo re = JSONObject.parseObject(jsonStr, TrafficInfo.class);
        System.out.println(re.getIccid());
        System.out.println(re.getUuid());
        System.out.println(re.getSTART_TIME());
    }
}
