package com.lin.springframework.beans.factory;

import com.lin.springframework.beans.BeansException;

/**
 * Interface to be implemented by beans that wish to be aware of their
 * owning {@link BeanFactory}.
 *
 * @Author linjiayi5
 * @Date 2023/4/10 20:15:00
 */
public interface BeanFactoryAware extends Aware {

    /**
     * Callback that supplies the owning factory to a bean instance.
     * <p>Invoked after the population of normal bean properties
     * but before an initialization callback such as
     * {@link InitializingBean#afterPropertiesSet()} or a custom init-method.
     * @param beanFactory owning BeanFactory (never {@code null}).
     * The bean can immediately call methods on the factory.
     * @throws BeansException in case of initialization errors
     */
    void setBeanFactory(BeanFactory beanFactory) throws BeansException;

}
