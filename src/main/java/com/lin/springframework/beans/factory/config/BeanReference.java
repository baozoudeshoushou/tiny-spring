package com.lin.springframework.beans.factory.config;

/**
 * Interface that exposes a reference to a bean name in an abstract fashion.
 * @Author linjiayi5
 * @Date 2023/4/6 20:15:37
 */
public class BeanReference {

    private final String beanName;

    public BeanReference(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }

}
