package com.lin.springframework.beans.factory.support;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.lin.springframework.beans.BeansException;
import com.lin.springframework.beans.factory.DisposableBean;
import com.lin.springframework.beans.factory.FactoryBean;
import com.lin.springframework.beans.factory.config.BeanDefinition;
import com.lin.springframework.beans.factory.config.BeanPostProcessor;
import com.lin.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.lin.springframework.utils.ClassUtils;
import com.lin.springframework.utils.StringValueResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author linjiayi5
 * @Date 2023/4/4 16:40:57
 */
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {

    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

    /** BeanPostProcessors to apply. */
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    /** String resolvers to apply e.g. to annotation attribute values. */
    private final List<StringValueResolver> embeddedValueResolvers = new CopyOnWriteArrayList<>();

    //---------------------------------------------------------------------
    // Implementation of BeanFactory interface
    //---------------------------------------------------------------------

    @Override
    public Object getBean(String name) throws BeansException {
        return doGetBean(name, null, null);
    }

    @Override
    public Object getBean(String name, Object... args) {
        return doGetBean(name, null, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return doGetBean(name, requiredType, null);
    }

    protected <T> T doGetBean(final String name, Class<T> requiredType, final Object[] args) {
        // Get from cache
        Object sharedInstance = getSingleton(name);
        if (sharedInstance != null) {
            // 可能是 bean，也可能是 factoryBean
            return (T) getObjectForBeanInstance(sharedInstance, name);
        }

        BeanDefinition beanDefinition = getBeanDefinition(name);
        // Create bean instance.
        Object bean = createBean(name, beanDefinition, args);
        return (T) getObjectForBeanInstance(bean, name);
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
     * @param args explicit arguments to use for constructor or factory method invocation
     * @return a new instance of the bean
     * @throws BeansException
     */
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args)
            throws BeansException;

    /**
     * Return the list of BeanPostProcessors that will get applied
     * to beans created with this factory.
     */
    public List<BeanPostProcessor> getBeanPostProcessors() {
        return beanPostProcessors;
    }

    /**
     * Get the object for the given bean instance, either the bean
     * instance itself or its created object in case of a FactoryBean.
     * @param beanInstance the shared bean instance
     * @param beanName the canonical bean name
     * @return the object to expose for the bean
     */
    protected Object getObjectForBeanInstance(Object beanInstance, String beanName) {
        if (!(beanInstance instanceof FactoryBean<?>)) {
            return beanInstance;
        }
        Object object = getCachedObjectForFactoryBean(beanName);
        if (object == null) {
            FactoryBean<?> factoryBean = (FactoryBean<?>) beanInstance;
            object = getObjectFromFactoryBean(factoryBean, beanName);
        }
        return object;
    }

    /**
     * Add the given bean to the list of disposable beans in this factory,
     * registering its DisposableBean interface and/or the given destroy method
     * to be called on factory shutdown (if applicable). Only applies to singletons.
     * @param beanName the name of the bean
     * @param bean the bean instance
     * @param beanDefinition the bean definition for the bean
     * @see #registerDisposableBean
     */
    protected void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
        if (!beanDefinition.isSingleton()) {
            return;
        }
        if (bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())) {
            registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
        }
    }

    //---------------------------------------------------------------------
    // Implementation of ConfigurableBeanFactory interface
    //---------------------------------------------------------------------

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        Assert.notNull(beanPostProcessor, "BeanPostProcessor must not be null");
        synchronized (this.beanPostProcessors) {
            // Remove from old position, if any
            this.beanPostProcessors.remove(beanPostProcessor);
            // Add to end of list
            this.beanPostProcessors.add(beanPostProcessor);
        }
    }

    @Override
    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = (beanClassLoader != null ? beanClassLoader : ClassUtils.getDefaultClassLoader());
    }

    @Override
    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }

    @Override
    public void addEmbeddedValueResolver(StringValueResolver valueResolver) {
        Assert.notNull(valueResolver, "StringValueResolver must not be null");
        this.embeddedValueResolvers.add(valueResolver);
    }

    @Override
    public boolean hasEmbeddedValueResolver() {
        return !this.embeddedValueResolvers.isEmpty();
    }

    @Override
    public String resolveEmbeddedValue(String value) {
        String result = value;
        for (StringValueResolver resolver : this.embeddedValueResolvers) {
            result = resolver.resolveStringValue(result);
        }
        return result;
    }

}
