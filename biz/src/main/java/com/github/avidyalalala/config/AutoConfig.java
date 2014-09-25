package com.github.avidyalalala.config;


import lombok.Data;

@Data
public class AutoConfig {
    /** app白名单 */
    private String qualifiedAppList;

    /** 执行定时任务机器host */
    private String cronHostName;

    /****/
    private String aliTongXinFtpServer;
    /****/
    private String aliTongXinFtpUserName;
    /****/
    private String aliTongXinFtpPassWord;
    /****/
    private String aliTongXinFtpDirectory;
}
