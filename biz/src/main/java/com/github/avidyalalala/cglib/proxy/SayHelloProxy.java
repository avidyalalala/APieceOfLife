package com.github.avidyalalala.cglib.proxy;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 * User: lina.hou
 * Date: 14-10-11
 * Time: 下午2:19
 * To change this template use File | Settings | File Templates.
 */
public class SayHelloProxy implements MethodInterceptor {
    private static SayHelloProxy instance=null;
    private SayHelloProxy(){}

    public static SayHelloProxy init(){
        return null==instance?new SayHelloProxy():instance;
    }

    public Object intercept(Object obj, java.lang.reflect.Method method, Object[] args,
                            MethodProxy proxy) throws Throwable{
        Object re = new Object();
        if(method.getName().equals("morningGreeting")){

            Field field=obj.getClass().getField("word");
            field.setAccessible(true);
            field.set(obj, "guten Tag");
            re=proxy.invokeSuper(obj,args);
        }
        //proxy.invoke(obj, )
        return re ;

    }
}
