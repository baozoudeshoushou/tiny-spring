package com.lin.springframework.beans.factory;

import com.lin.springframework.beans.BeansException;

/**
 * The root interface for accessing a Spring bean container.
 * @Author linjiayi5
 * @Date 2023/4/4 16:14:56
 */
public interface BeanFactory {

    Object getBean(String name) throws BeansException;

    /**
     * Return an instance
     * @param name
     * @return
     * @throws BeansException
     */
    Object getBean(String name, Object... args) throws BeansException;

    <T> T getBean(String name, Class<T> requiredType) throws BeansException;
}
