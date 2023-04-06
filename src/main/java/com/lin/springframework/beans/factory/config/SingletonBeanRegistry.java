package com.lin.springframework.beans.factory.config;

/**
 * Interface that defines a registry for shared bean instances.
 * @Author linjiayi5
 * @Date 2023/4/4 16:30:05
 */
public interface SingletonBeanRegistry {

    /**
     * Register the given existing object as singleton in the bean registry, under the given bean name.
     * @param beanName the name of the bean
     * @param singletonObject the existing singleton object
     */
    void registerSingleton(String beanName, Object singletonObject);

    /**
     * Return the (raw) singleton object registered under the given name.
     * @param beanName
     * @return
     */
    Object getSingleton(String beanName);

}
