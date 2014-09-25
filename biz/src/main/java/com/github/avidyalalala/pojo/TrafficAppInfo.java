package com.github.avidyalalala.pojo;

import lombok.Data;


/**
 * User: lina.hou
 * Date: 14-7-16
 * Time: 下午2:18
 */
@Data
public class TrafficAppInfo {

    //APP 的包名
    private String appName;
    //流量以 byte 为单位
    private long trafficData;
}
