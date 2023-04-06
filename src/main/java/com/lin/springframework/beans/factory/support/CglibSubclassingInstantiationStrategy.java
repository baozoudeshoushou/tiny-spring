package com.lin.springframework.beans.factory.support;

import com.lin.springframework.beans.BeansException;
import com.lin.springframework.beans.factory.config.BeanDefinition;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

import java.lang.reflect.Constructor;

/**
 * Default object instantiation strategy
 * @Author linjiayi5
 * @Date 2023/4/6 15:39:49
 */
public class CglibSubclassingInstantiationStrategy implements InstantiationStrategy {

    @Override
    public Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor<?> ctor, Object[] args) throws BeansException {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(beanDefinition.getBeanClass());;
        enhancer.setCallback(new NoOp() {});
        if (ctor == null) {
            return enhancer.create();
        }

        return enhancer.create(ctor.getParameterTypes(), args);
    }

}
