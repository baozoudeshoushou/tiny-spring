package com.lin.springframework.beans.factory.config;

/**
 * @Author linjiayi5
 * @Date 2023/4/4 16:30:05
 */
public interface SingletonBeanRegistry {

    /**
     * Return the (raw) singleton object registered under the given name.
     * @param beanName
     * @return
     */
    Object getSingleton(String beanName);

}
