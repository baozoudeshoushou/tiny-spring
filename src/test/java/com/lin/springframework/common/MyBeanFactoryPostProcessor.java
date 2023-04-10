package com.lin.springframework.common;

import com.lin.springframework.beans.BeansException;
import com.lin.springframework.beans.PropertyValue;
import com.lin.springframework.beans.PropertyValues;
import com.lin.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.lin.springframework.beans.factory.config.BeanDefinition;
import com.lin.springframework.beans.factory.config.BeanFactoryPostProcessor;

/**
 * @Author linjiayi5
 * @Date 2023/4/10 17:16:18
 */
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("userService");
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("company", "改为：字节跳动"));
    }

}
