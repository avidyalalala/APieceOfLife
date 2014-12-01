package com.github.avidyalalala.cglib.classGenerator;

import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.core.NamingPolicy;
import net.sf.cglib.core.Predicate;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: lina.hou
 * Date: 14-10-14
 * Time: 下午2:48
 * To change this template use File | Settings | File Templates.
 * copy and modify at http://stackoverflow.com/questions/5178391/create-simple-pojo-classes-bytecode-at-runtime-dynamically
 */
public class PojoCreateAndInitiate {
    public static Class<?> createBeanClass(
    /* fully qualified class name */
    final String className,
    /* bean properties, name -> type */
    final Map<String, Class<?>> properties){

        final BeanGenerator beanGenerator = new BeanGenerator();

        /* use our own hard coded class name instead of a real naming policy */
        beanGenerator.setNamingPolicy(new NamingPolicy(){
            @Override public String getClassName(final String prefix,
                                                 final String source, final Object key, final Predicate names){
                return className;
            }});
        BeanGenerator.addProperties(beanGenerator, properties);
        return (Class<?>) beanGenerator.createClass();
    }

    public static void main(String[] args) throws Exception{
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "/home/cglib");

        final Map<String, Class<?>> properties =
                new HashMap<String, Class<?>>();
        properties.put("helloWord", String.class);
        properties.put("bar", String.class);
        properties.put("baz", int[].class);

        final Class<?> beanClass =
                createBeanClass("some.ClassName", properties);
        System.out.println(beanClass);
        for(final Method method : beanClass.getDeclaredMethods()){
            System.out.println(method);
        }

        Object obj = beanClass.newInstance();
        Method m=beanClass.getMethod("setBaz",int[].class);
        m.invoke(obj, new Object[]{new int[]{0, 1, 2, 3}});
        Method getM=beanClass.getMethod("getBaz",null);
        int[] re=(int[])getM.invoke(obj, (Object[])null);

        System.out.println(Arrays.toString(re));
    }
}
