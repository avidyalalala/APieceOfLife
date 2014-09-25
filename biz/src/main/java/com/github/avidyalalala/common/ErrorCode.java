package com.github.avidyalalala.common;

/**
 * User: lina.hou       wenan
 * Date: 14-7-16
 * Time: 下午12:03
 */
public enum  ErrorCode {
    FAIL_BIZ_JSON_PARSE_ERROR (1000,"JSON 解析错误"),
    FAIL_BIZ_IMSI_BLANK_ERROR (2001,"IMSI 不能为空"),
    FAIL_BIZ_ICCID_BLANK_ERROR(2002,"ICCID 不能为空"),
    FAIL_BIZ_NETWORK_ID_BLANK_ERROR(2003,"NETWORK_ID 不能为空"),

    FAIL_BIZ_APP_INFO_BLANK_ERROR(2006,"APP_INFO 里 不能为空"),

    FAIL_BIZ_ICCID_ILLEGAL(3002,"ICCID 不符合规定"),
    FAIL_BIZ_NETWORK_ID_ILLEGAL(3003,"NETWORK_ID 不符合规定"),

    FAIL_BIZ_START_TIME_ILLEGAL(3004,"开始时间 格式不符合规定"),
    FAIL_BIZ_BILL_TIME_ILLEGAL(3005,"账单时间 格式不符合规定"),

    FAIL_BIZ_TOTAL_TRAFFIC_DATA_ILLEGAL(3006,"TOTAL_TRAFFIC_DATA 格式不符合规定"),
    FAIL_BIZ_APP_NAME_ILLEGAL(3007,"APP_NAME 格式不符合规定"),
    FAIL_BIZ_TRAFFIC_DATA_ILLEGAL(3008,"TRAFFIC_DATA 格式不符合规定"),

    FAIL_BIZ_DATABASE_ERROR(9000, "数据库插入失败");

    private int no;
    private String desc;

    public int getNo() {
        return no;
    }

    public String getDesc() {
        return desc;
    }

    public String toString(){
        return this.name();
    }

    private ErrorCode(int no, String desc){
        this.no=no;
        this.desc=desc;
    }
    public static void main(String[] args) {
        System.out.println(ErrorCode.FAIL_BIZ_IMSI_BLANK_ERROR);
    }
}
