package com.lin.springframework.beans.factory;

import com.lin.springframework.beans.BeansException;

import java.util.Map;

/**
 * @Author linjiayi5
 * @Date 2023/4/7 20:48:21
 */
public interface ListableBeanFactory extends BeanFactory {

    /**
     * 按照类型返回 Bean 实例
     * @param type
     * @param <T>
     * @return
     * @throws BeansException
     */
    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;

    /**
     * Return the names of all beans defined in this registry.
     *
     * 返回注册表中所有的Bean名称
     */
    String[] getBeanDefinitionNames();

}
