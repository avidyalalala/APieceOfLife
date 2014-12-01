package com.github.avidyalalala.regx;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: lina.hou
 * Date: 14-11-25
 * Time: 下午4:24
 * To change this template use File | Settings | File Templates.
 */
public class PatternGroup {
    public static void main(String[] args) {

        String entSinaURL1 = "http://ent.sina.com.cn/m/c/2014-11-24/23244246070.shtml";
        String entSinaURL2 = "http://ent.sina.com.cn/s/h/2014-11-24/doc-iavxeafr5097985.shtml";
        String entSinaURL3 = "http://ent.sina.com.cn/music/zy/2014-11-24/doc-icczmvun0272338.shtml";
        String entSinaURL4 = "http://ent.sina.com.cn/music/zy/2014-11-25/doc-icczmvun0272338.shtml";

        String[] url_arr = new String[]{entSinaURL1, entSinaURL2, entSinaURL3, entSinaURL4};
        //ent\.sina\.com\.cn(.*?)
        //(http://|https://){1}[//w//.//-/:]+
        Pattern pattern = Pattern.compile("ent\\.sina\\.com\\.cn([a-zA-Z]?)([0-9]{4}-[0-9]{2}-[0-9]{2})");
        for (int i = 0; i < url_arr.length; i++) {
            System.out.println(url_arr[i]);
            Matcher m = pattern.matcher(url_arr[i]);
            while(m.find()) {
                System.out.println(m.groupCount());
                System.out.println(m.group());
            }
        }
    }
}
