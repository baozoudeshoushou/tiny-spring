package com.lin.springframework.beans.factory.config;

import com.lin.springframework.beans.BeansException;
import com.lin.springframework.beans.PropertyValues;

/**
 * Subinterface of {@link BeanPostProcessor} that adds a before-instantiation callback,
 * and a callback after instantiation but before explicit properties are set or
 * autowiring occurs.
 *
 * <p>Typically used to suppress default instantiation for specific target beans,
 * for example to create proxies with special TargetSources (pooling targets,
 * lazily initializing targets, etc), or to implement additional injection strategies
 * such as field injection.
 *
 * @Author linjiayi5
 * @Date 2023/4/13 21:06:30
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

    /**
     * Apply this BeanPostProcessor <i>before the target bean gets instantiated</i>.
     * The returned bean object may be a proxy to use instead of the target bean,
     * effectively suppressing default instantiation of the target bean.
     * <p>If a non-null object is returned by this method, the bean creation process
     * will be short-circuited. The only further processing applied is the
     * {@link #postProcessAfterInitialization} callback from the configured
     * {@link BeanPostProcessor BeanPostProcessors}.
     * <p>The default implementation returns {@code null}.
     * @param beanClass the class of the bean to be instantiated
     * @param beanName the name of the bean
     * @return the bean object to expose instead of a default instance of the target bean,
     * or {@code null} to proceed with default instantiation
     * @throws BeansException in case of errors
     */
    default Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    /**
     * Post-process the given property values before the factory applies them
     * to the given bean.
     * <p>The default implementation returns the given {@code pvs} as-is.
     * @param pvs the property values that the factory is about to apply (never {@code null})
     * @param bean the bean instance created, but whose properties have not yet been set
     * @param beanName the name of the bean
     * @return the actual property values to apply to the given bean (can be the passed-in
     * PropertyValues instance), or {@code null} to skip property population
     * @throws com.lin.springframework.beans.BeansException in case of errors
     */
    default PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName)
            throws BeansException {

        return pvs;
    }

}
