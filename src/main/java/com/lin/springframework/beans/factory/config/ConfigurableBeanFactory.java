package com.lin.springframework.beans.factory.config;

import com.lin.springframework.beans.factory.HierarchicalBeanFactory;

/**
 * @Author linjiayi5
 * @Date 2023/4/7 11:41:59
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {

    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    /**
     * Add a new BeanPostProcessor that will get applied to beans created
     * by this factory. To be invoked during factory configuration.
     * @param beanPostProcessor the post-processor to register
     */
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    /**
     * Destroy all singleton beans in this factory, including inner beans that have
     * been registered as disposable. To be called on shutdown of a factory.
     * <p>Any exception that arises during destruction should be caught
     * and logged instead of propagated to the caller of this method.
     */
    void destroySingletons();

    /**
     * Set the class loader to use for loading bean classes.
     * Default is the thread context class loader.
     * <p>Note that this class loader will only apply to bean definitions
     * that do not carry a resolved bean class yet. This is the case as of
     * Spring 2.0 by default: Bean definitions only carry bean class names,
     * to be resolved once the factory processes the bean definition.
     * @param beanClassLoader the class loader to use,
     * or {@code null} to suggest the default class loader
     */
    void setBeanClassLoader(ClassLoader beanClassLoader);

    /**
     * Return this factory's class loader for loading bean classes
     * (only {@code null} if even the system ClassLoader isn't accessible).
     */
    ClassLoader getBeanClassLoader();

}
