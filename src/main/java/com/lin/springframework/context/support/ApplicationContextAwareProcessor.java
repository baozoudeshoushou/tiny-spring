package com.lin.springframework.context.support;

import com.lin.springframework.beans.BeansException;
import com.lin.springframework.beans.factory.config.BeanPostProcessor;
import com.lin.springframework.context.ApplicationContext;
import com.lin.springframework.context.ApplicationContextAware;

/**
 * @Author linjiayi5
 * @Date 2023/4/11 10:48:38
 */
public class ApplicationContextAwareProcessor implements BeanPostProcessor {

    private final ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ApplicationContextAware applicationContextAware) {
            applicationContextAware.setApplicationContext(applicationContext);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

}
