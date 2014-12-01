package com.github.avidyalalala.opStack;

/**
 * Created with IntelliJ IDEA.
 * User: lina.hou
 * Date: 14-10-28
 * Time: 下午6:04
 * To change this template use File | Settings | File Templates.
 */
public class theFrame {
    public static void main(String[] args) {
        Thread curThread = Thread.currentThread();
        System.out.println(curThread.getContextClassLoader());
    }
}
