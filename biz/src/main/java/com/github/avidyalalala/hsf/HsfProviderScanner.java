package com.github.avidyalalala.hsf;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.beans.Introspector;
import java.util.Set;

/**
 * 扫描注解定义HSF 服务
 */
public class HsfProviderScanner extends ClassPathScanningCandidateComponentProvider implements BeanFactoryPostProcessor {
    private String basePackage;
    private String serviceVersion;
    private Long clientTimeout;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        addIncludeFilter(new AnnotationTypeFilter(HsfService.class));
        Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
        if (!(beanFactory instanceof BeanDefinitionRegistry)) {
            throw new IllegalStateException();
        }

        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
        for (BeanDefinition definition : candidates) {
            Class<?> type;
            try {
                type = Class.forName(definition.getBeanClassName());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            String beanName = Introspector.decapitalize(AnnotationUtils.getAnnotation(type, HsfService.class).value().getSimpleName());
            //AnnotationUtils 的使用，第一个参数必须是 Class，看type的赋值，明白此Class 是从bean上获得的，
            // 则明白HSFProviderScanner 之所能扫到加了注释的类，是因为此类必须首先成为一个spring容器管理
            // 的bean
            BeanDefinition providerDefinition = new GenericBeanDefinition();
            providerDefinition.setBeanClassName("com.taobao.hsf.app.spring.util.HSFSpringProviderBean");
            providerDefinition.getPropertyValues().add("serviceInterface", AnnotationUtils.getAnnotation(type, HsfService.class).value().getName());
            providerDefinition.getPropertyValues().add("target", new RuntimeBeanReference(beanName));
            providerDefinition.getPropertyValues().add("serviceVersion", serviceVersion);
            providerDefinition.getPropertyValues().add("clientTimeout", clientTimeout);
            registry.registerBeanDefinition(beanName + "-HSF-PROVIDER", providerDefinition);
        }
    }

    public HsfProviderScanner() {
        super(false);
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getServiceVersion() {
        return serviceVersion;
    }

    public void setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
    }

    public Long getClientTimeout() {
        return clientTimeout;
    }

    public void setClientTimeout(Long clientTimeout) {
        this.clientTimeout = clientTimeout;
    }

}
