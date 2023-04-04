package com.lin.springframework.beans.factory.support;

import com.lin.springframework.beans.BeansException;
import com.lin.springframework.beans.factory.BeanFactory;
import com.lin.springframework.beans.factory.config.BeanDefinition;

/**
 * @Author linjiayi5
 * @Date 2023/4/4 16:40:57
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    @Override
    public Object getBean(String name) {
        Object bean = getSingleton(name);
        if (bean != null) {
            return bean;
        }
        BeanDefinition beanDefinition = getBeanDefinition(name);
        return createBean(name, beanDefinition);
    }

    /**
     * Return the bean definition for the given bean name.
     * @param beanName the name of the bean to find a definition for
     * @return the BeanDefinition for this prototype name
     * @throws BeansException
     */
    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * Create a bean instance for the given merged bean definition
     * @param beanName the name of the bean
     * @param beanDefinition the bean definition for the bean
     * @return a new instance of the bean
     * @throws BeansException
     */
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition)
            throws BeansException;
}
