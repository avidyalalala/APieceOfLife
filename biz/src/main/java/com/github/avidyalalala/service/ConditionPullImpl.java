package com.github.avidyalalala.service;

import com.github.avidyalalala.common.Result;
import com.github.avidyalalala.config.AutoConfig;
import com.github.avidyalalala.hsf.HsfService;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: lina.hou
 * Date: 14-7-16
 * Time: 下午12:43
 */

@HsfService(ConditionPull.class)
@Component("conditionPull")
public class ConditionPullImpl implements ConditionPull<Result<Map<String,List<String>>>> {

    private static final Logger log = LoggerFactory.getLogger("netLogger");
    @Resource
    private AutoConfig autoConfig;

    @Override
    public Result<Map<String,List<String>>> getAppList() {
        log.info("ConditionPull.getAppList .");
        Result<Map<String,List<String>>> re = new Result<Map<String,List<String>>>();
        Map<String,List<String>> map= new HashMap<String,List<String>>();

        String apps=autoConfig.getQualifiedAppList();

        List<String> appList = Arrays.asList(apps.split(";"));
        log.info("ConditionPull.getAppList . get appList: "+ ToStringBuilder.reflectionToString(appList));

        map.put("appList",appList);

        re.setModel(map);
        re.setSuccess(true);

        log.info("ConditionPull.getAppList . the response object is : "+ ToStringBuilder.reflectionToString(re));

        return re;
    }

}
