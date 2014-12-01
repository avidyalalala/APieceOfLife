package com.github.avidyalalala.cglib.proxy;

/**
 * Created with IntelliJ IDEA.
 * 寒暄
 * User: lina.hou
 * Date: 14-10-11
 * Time: 下午2:07
 * To change this template use File | Settings | File Templates.
 */
public class SayHello {
    public String word="good morning";
    public String morningGreeting(){
        System.out.println(word);
        return word;
    }
}
