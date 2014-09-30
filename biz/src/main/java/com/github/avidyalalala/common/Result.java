package com.github.avidyalalala.common;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: lina.hou
 * Date: 14-9-25
 * Time: 下午12:51
 * To change this template use File | Settings | File Templates.
 */
@Data
public class Result<T> {

    private T model;
    private boolean success;
    private String msgCode;
    private String msgInfo;

}
