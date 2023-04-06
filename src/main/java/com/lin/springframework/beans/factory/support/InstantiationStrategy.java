package com.lin.springframework.beans.factory.support;

import com.lin.springframework.beans.BeansException;
import com.lin.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * Interface responsible for creating instances corresponding to a root bean definition.
 * @Author linjiayi5
 * @Date 2023/4/6 15:22:56
 */
public interface InstantiationStrategy {

    /**
     * Create and return an instance via the given constructor.
     * @param beanDefinition the bean definition
     * @param beanName the name of the bean when it is created in this context
     * @param ctor the constructor to use
     * @param args the constructor arguments to apply
     * @return a bean instance for this bean definition
     * @throws BeansException
     */
    Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor<?> ctor, Object[] args) throws BeansException;

}
