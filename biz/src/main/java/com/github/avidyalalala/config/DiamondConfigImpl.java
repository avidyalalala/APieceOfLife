package com.github.avidyalalala.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: lina.hou
 * Date: 14-7-29
 * Time: 下午1:52
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
public class DiamondConfigImpl implements InitializingBean {

    @Resource
    private AutoConfig autoConfig;

    private String dataId;
    private String group;

    private static HashMap<String, Method> dynamicPropertiesGetter =new HashMap<String,Method>();
    private static HashMap<String, Method> dynamicPropertiesSetter =new HashMap<String,Method>();

    static{

        List<Field> dynamicPropertiesList = Arrays.asList(AutoConfig.class.getDeclaredFields());

        for(Field field: dynamicPropertiesList){
            String key=field.getName();
            try{
                Method setter=AutoConfig.class.getMethod("set"+ StringUtils.capitalize(key),new Class[]{String.class});
                Method getter=AutoConfig.class.getMethod("get"+StringUtils.capitalize(key),(Class[]) null);
                dynamicPropertiesSetter.put(key,setter);
                dynamicPropertiesGetter.put(key,getter);

            } catch (NoSuchMethodException e) {
                log.error("cannot find the setter of {} in AutoConfig Class:{}", key,e.getMessage());
            }
         }

    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void handleConfig(String configInfo) {
        Properties properties = new Properties();

        if(StringUtils.isBlank(configInfo)) {
            log.info("Diamond: get config properity blank");
            return;
        }
        try {
            properties.load(new StringReader(configInfo));
            Enumeration<?> names = properties.propertyNames();
            while(names.hasMoreElements()){
                String key = (String) names.nextElement();

                String _new=(String)properties.get(key);

                if(dynamicPropertiesGetter.containsKey(key)){
                try {
                    String old = (String)dynamicPropertiesGetter.get(key).invoke(autoConfig, (Object[])null);
                    if(!StringUtils.equals(old,_new)){
                        dynamicPropertiesSetter.get(key).invoke(autoConfig, _new);
                        log.info("Diamond: {} setter success"+key);
                    }
                } catch (IllegalAccessException e) {
                    log.info(e.getMessage());
                } catch (InvocationTargetException e) {
                    log.info(e.getMessage());
                }
                }

            }

        }
        catch (IOException e) {
            log.error("load properties error：" + configInfo, e);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {

//        Diamond.addListener(dataId, group, new ManagerListenerAdapter() {
//
//            //根据目前的diamond的client(3.6.0)端代码，会在5s中轮询下服务器，
//            //diamond推送变更的数据后，理论推送新的数据应该最长5s中，就会同步到本机。
//            //Diamond内部会为内容生成MD5信息，比较MD5信息发现没有变化，则不会调用此回调函数
//            @Override
//            public void receiveConfigInfo(String configInfo) {
//                log.info("receiveConfigInfo called {}",configInfo);
//                handleConfig(configInfo);
//                log.info("end handle ConfigInfo");
//            }
//        });
//
//        handleConfig(Diamond.getConfig(dataId, group, 3000));
    }
}
