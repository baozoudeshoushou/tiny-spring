package com.lin.springframework.beans.factory.config;

import com.lin.springframework.beans.BeansException;
import com.lin.springframework.beans.factory.BeanFactory;

/**
 * @Author linjiayi5
 * @Date 2023/4/7 20:51:29
 */
public interface AutowireCapableBeanFactory extends BeanFactory {

    /**
     * Apply {@link BeanPostProcessor BeanPostProcessors} to the given existing bean
     * instance, invoking their {@code postProcessBeforeInitialization} methods.
     * The returned bean instance may be a wrapper around the original.
     * @param existingBean the existing bean instance
     * @param beanName the name of the bean, to be passed to it if necessary
     * @return the bean instance to use, either the original or a wrapped one
     * @throws BeansException if any post-processing failed
     * @see BeanPostProcessor#postProcessBeforeInitialization
     */
    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException;

    /**
     * Apply {@link BeanPostProcessor BeanPostProcessors} to the given existing bean
     * instance, invoking their {@code postProcessAfterInitialization} methods.
     * The returned bean instance may be a wrapper around the original.
     * @param existingBean the existing bean instance
     * @param beanName the name of the bean, to be passed to it if necessary
     * @return the bean instance to use, either the original or a wrapped one
     * @throws BeansException if any post-processing failed
     * @see BeanPostProcessor#postProcessAfterInitialization
     */
    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException;

}
