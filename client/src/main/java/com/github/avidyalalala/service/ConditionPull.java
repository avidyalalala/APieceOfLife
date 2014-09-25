package com.github.avidyalalala.service;

/**
 * User: lina.hou
 * Date: 14-7-16
 * Time: 下午12:39
 */
public interface ConditionPull <T>{
    /**
     * 获取最新的app白名单
     * @return
     */
    T getAppList();
}
