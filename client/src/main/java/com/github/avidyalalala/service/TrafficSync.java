package com.github.avidyalalala.service;
/**
 * User: lina.hou
 * Date: 14-7-11
 * Time: 下午2:09
 */
public interface TrafficSync<T> {
    /**
     * 流量信息同步接口
     * @param json
     * @return
     */
    public T syncTrafficInfoViaJson(String json);
}