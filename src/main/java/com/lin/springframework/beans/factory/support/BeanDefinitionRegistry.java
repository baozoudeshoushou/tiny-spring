package com.lin.springframework.beans.factory.support;

import com.lin.springframework.beans.factory.config.BeanDefinition;

/**
 * Interface for registries that hold bean definitions
 * @Author linjiayi5
 * @Date 2023/4/4 17:27:46
 */
public interface BeanDefinitionRegistry {

    /**
     * Register a new bean definition with this registry.
     * @param beanName the name of the bean instance to register
     * @param beanDefinition definition of the bean instance to register
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

}
