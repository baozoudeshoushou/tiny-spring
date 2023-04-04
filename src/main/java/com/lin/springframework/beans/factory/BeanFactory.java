package com.lin.springframework.beans.factory;

import com.lin.springframework.beans.BeansException;

/**
 * @Author linjiayi5
 * @Date 2023/4/4 16:14:56
 */
public interface BeanFactory {

    /**
     * get bean by beanName
     * @param name
     * @return
     * @throws BeansException
     */
    Object getBean(String name) throws BeansException;

}
