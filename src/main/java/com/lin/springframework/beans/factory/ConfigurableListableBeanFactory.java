package com.lin.springframework.beans.factory;

import com.lin.springframework.beans.BeansException;
import com.lin.springframework.beans.factory.config.AutowireCapableBeanFactory;
import com.lin.springframework.beans.factory.config.BeanDefinition;
import com.lin.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * @Author linjiayi5
 * @Date 2023/4/7 20:49:42
 */
public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {

    /**
     * Return the registered BeanDefinition for the specified bean, allowing access
     * to its property values and constructor argument value (which can be
     * modified during bean factory post-processing).
     *
     * @param beanName the name of the bean
     * @return the registered BeanDefinition
     * @throws BeansException if there is no bean with the given name
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * Ensure that all non-lazy-init singletons are instantiated, also considering
     * {@link org.springframework.beans.factory.FactoryBean FactoryBeans}.
     * Typically invoked at the end of factory setup, if desired.
     *
     * @throws BeansException if one of the singleton beans could not be created.
     */
    void preInstantiateSingletons() throws BeansException;

}
