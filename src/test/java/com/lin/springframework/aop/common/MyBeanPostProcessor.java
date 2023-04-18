package com.lin.springframework.aop.common;

import com.lin.springframework.aop.bean.UserService;
import com.lin.springframework.beans.BeansException;
import com.lin.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @Author linjiayi5
 * @Date 2023/4/10 17:18:07
 */
public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if ("userService".equals(beanName)) {
            UserService userService = (UserService) bean;
            userService.setLocation("改为：北京");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

}
