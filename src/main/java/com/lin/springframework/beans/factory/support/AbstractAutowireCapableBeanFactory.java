package com.lin.springframework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.lin.springframework.beans.BeansException;
import com.lin.springframework.beans.PropertyValue;
import com.lin.springframework.beans.PropertyValues;
import com.lin.springframework.beans.factory.*;
import com.lin.springframework.beans.factory.config.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

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
            // AOP
            // TODO 这里怎么注入依赖的 bean？
            bean = resolveBeforeInstantiation(beanName, beanDefinition);
            if (bean != null) {
                // 被代理了直接返回
                return bean;
            }

            // TODO 循环依赖
            // 实例化 bean
            bean = createBeanInstance(beanName, beanDefinition, args);
            // 在设置 Bean 属性之前，允许 BeanPostProcessor 修改属性值
            applyBeanPostProcessorsBeforeApplyingPropertyValues(beanName, bean, beanDefinition);
            // 给 Bean 填充属性
            applyPropertyValues(beanName, bean, beanDefinition);
            // 执行 Bean 的初始化方法和 BeanPostProcessor 的前置和后置处理方法
            bean = initializeBean(beanName, bean, beanDefinition);
        }
        catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }
        // 注册实现了 DisposableBean 接口的 Bean 对象
        registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);

        if (beanDefinition.isSingleton()) {
            addSingleton(beanName, bean);
        }

        return bean;
    }

    protected Object resolveBeforeInstantiation(String beanName, BeanDefinition beanDefinition) {
        Object bean = applyBeanPostProcessorsBeforeInstantiation(beanDefinition.getBeanClass(), beanName);
        if (bean != null) {
            bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
        }
        return bean;
    }

    protected Object applyBeanPostProcessorsBeforeInstantiation(Class<?> beanClass, String beanName) {
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor instantiationAwareBeanPostProcessor) {
                Object result = instantiationAwareBeanPostProcessor.postProcessBeforeInstantiation(beanClass, beanName);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
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

    void applyBeanPostProcessorsBeforeApplyingPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor bp) {
                PropertyValues beanPvs = beanDefinition.getPropertyValues();
                PropertyValues pvs = bp.postProcessProperties(beanPvs, bean, beanName);
                if (pvs != null) {
                    for (PropertyValue pv : pvs.getPropertyValues()) {
                        beanPvs.addPropertyValue(pv);
                    }
                }
            }
        }

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

    /**
     * Initialize the given bean instance, applying factory callbacks
     * as well as init methods and bean post processors.
     * @param beanName the bean name in the factory (for debugging purposes)
     * @param bean the new bean instance we may need to initialize
     * @param beanDefinition the bean definition that the bean was created with
     * @return the initialized bean instance (potentially wrapped)
     */
    private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {
        invokeAwareMethods(beanName, bean);

        Object wrappedBean = bean;

        wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);

        try {
            invokeInitMethods(beanName, wrappedBean, beanDefinition);
        }
        catch (Exception e) {
            throw new BeansException("Invocation of init method of bean[" + beanName + "] failed", e);
        }

        wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);

        return wrappedBean;
    }

    private void invokeAwareMethods(String beanName, Object bean) {
        if (bean instanceof Aware) {
            if (bean instanceof BeanNameAware beanNameAware) {
                beanNameAware.setBeanName(beanName);
            }
            if (bean instanceof BeanClassLoaderAware beanClassLoaderAware) {
                beanClassLoaderAware.setBeanClassLoader(getBeanClassLoader());
            }
            if (bean instanceof BeanFactoryAware beanFactoryAware) {
                beanFactoryAware.setBeanFactory(this);
            }
        }
    }

    private void invokeInitMethods(String beanName, Object bean, BeanDefinition beanDefinition) throws Exception {
        // invoke InitializingBean.afterPropertiesSet()
        if (bean instanceof InitializingBean initializingBean) {
            initializingBean.afterPropertiesSet();
        }

        // invoke init-method
        String initMethodName = beanDefinition.getInitMethodName();
        if (StrUtil.isNotBlank(initMethodName)) {
            Method initMethod = beanDefinition.getBeanClass().getMethod(initMethodName);
            initMethod.invoke(bean);
        }
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

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }

}
