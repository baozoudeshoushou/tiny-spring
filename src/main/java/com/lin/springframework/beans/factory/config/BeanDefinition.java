package com.lin.springframework.beans.factory.config;

/**
 * @Author linjiayi5
 * @Date 2023/4/4 16:14:01
 */
public class BeanDefinition {

    private Class beanClass;

    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
    }

}
