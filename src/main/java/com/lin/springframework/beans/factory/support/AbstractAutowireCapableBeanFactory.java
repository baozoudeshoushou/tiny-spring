package com.lin.springframework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import com.lin.springframework.beans.BeansException;
import com.lin.springframework.beans.PropertyValue;
import com.lin.springframework.beans.PropertyValues;
import com.lin.springframework.beans.factory.config.AutowireCapableBeanFactory;
import com.lin.springframework.beans.factory.config.BeanDefinition;
import com.lin.springframework.beans.factory.config.BeanPostProcessor;
import com.lin.springframework.beans.factory.config.BeanReference;

import java.lang.reflect.Constructor;

/**
 * Abstract bean factory superclass that implements default bean creation
 * @Author linjiayi5
 * @Date 2023/4/4 17:09:33
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    private InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException {
        Object bean;
        try {
            bean = createBeanInstance(beanName, beanDefinition, args);
            // 给 Bean 填充属性
            applyPropertyValues(beanName, bean, beanDefinition);
            // 执行 Bean 的初始化方法和 BeanPostProcessor 的前置和后置处理方法
            Object o = initializeBean(beanName, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }
        addSingleton(beanName, bean);
        return bean;
    }

    /**
     * Create a new instance for the specified bean, using an appropriate instantiation strategy:
     * factory method, constructor autowiring, or simple instantiation.
     * @param beanName beanName the name of the bean
     * @param beanDefinition the bean definition for the bean
     * @param args explicit arguments to use for constructor or factory method invocation
     * @return
     */
    protected Object createBeanInstance(String beanName, BeanDefinition beanDefinition, Object[] args) {
        Constructor<?> constructorToUse = null;
        Class<?> beanClass = beanDefinition.getBeanClass();
        if (args != null) {
            Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();
            for (Constructor<?> ctor : declaredConstructors) {
                if (ctor.getParameterCount() == args.length) {
                    constructorToUse = ctor;
                    break;
                }
            }

        }
        return getInstantiationStrategy().instantiate(beanDefinition, beanName,constructorToUse, args);
    }

    /**
     * pulate the bean instance with the property values from the bean definition.
     * @param beanName the name of the bean
     * @param bean the bean
     * @param beanDefinition the bean definition for the bean
     */
    protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
            String name = propertyValue.getName();
            Object value = propertyValue.getValue();
            if (value instanceof BeanReference beanReference) {
                value = getBean(beanReference.getBeanName());
            }
            BeanUtil.setFieldValue(bean, name, value);
        }
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }

    /**
     * Initialize the given bean instance, applying factory callbacks
     * as well as init methods and bean post processors.
     * @param beanName the bean name in the factory (for debugging purposes)
     * @param bean the new bean instance we may need to initialize
     * @param beanDefinition the bean definition that the bean was created with
     * @return the initialized bean instance (potentially wrapped)
     */
    private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {
        Object wrappedBean = bean;

        wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);

        invokeInitMethods(beanName, wrappedBean, beanDefinition);

        wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);

        return wrappedBean;
    }


    private void invokeInitMethods(String beanName, Object wrappedBean, BeanDefinition beanDefinition) {
        // TODO
    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            Object current = beanPostProcessor.postProcessBeforeInitialization(result, beanName);
            if (current == null) {
                return result;
            }
            result = current;
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            Object current = beanPostProcessor.postProcessAfterInitialization(result, beanName);
            if (current == null) {
                return result;
            }
            result = current;
        }
        return result;
    }

}
