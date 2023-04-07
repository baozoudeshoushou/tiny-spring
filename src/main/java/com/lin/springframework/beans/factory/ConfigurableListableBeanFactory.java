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

    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

}
