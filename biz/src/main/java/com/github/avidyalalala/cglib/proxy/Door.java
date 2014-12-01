package com.github.avidyalalala.cglib.proxy;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: lina.hou
 * Date: 14-10-11
 * Time: 下午2:11
 * To change this template use File | Settings | File Templates.
 */
public class Door {
    public static void main(String[] args) {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "/home/cglib");

        Enhancer eh=new Enhancer();
        eh.setSuperclass(SayHello.class);
        eh.setCallback(SayHelloProxy.init());
        SayHello sh= (SayHello)eh.create();

       // SayHello sh = new SayHello();
        Method[] ms=SayHello.class.getMethods();
        for (int i=0;i<ms.length;i++){
            System.out.println(ms[i]);

            if(ms[i].getParameterTypes().length==0){
                ms[i].setAccessible(true);
                try {
                    String re = (String)ms[i].invoke(sh, (Object[])null);
                    System.out.println(re);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (InvocationTargetException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }
        sh.morningGreeting();
    }
}
