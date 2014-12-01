package com.github.avidyalalala;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created with IntelliJ IDEA.
 * User: lina.hou
 * Date: 14-11-19
 * Time: 下午5:24
 * To change this template use File | Settings | File Templates.
 */
public class Temp {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String re = URLDecoder.decode("xxxxxtestst%E4%B8%AD%E6%96%87", "UTF-8");
        System.out.println(re);
    }
}
