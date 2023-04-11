package com.lin.springframework.beans.factory;

/**
 * Callback that allows a bean to be aware of the bean
 * {@link ClassLoader class loader}; that is, the class loader used by the
 * present bean factory to load bean classes.
 *
 * <p>Note that merely implementing {@link Aware} provides no default functionality.
 * Rather, processing must be done explicitly, for example in a
 * {@link com.lin.springframework.beans.factory.config.BeanPostProcessor}.
 *
 * @Author linjiayi5
 * @Date 2023/4/10 20:14:48
 */
public interface BeanClassLoaderAware extends Aware {

    /**
     * Callback that supplies the bean {@link ClassLoader class loader} to
     * a bean instance.
     * <p>Invoked <i>after</i> the population of normal bean properties but
     * <i>before</i> an initialization callback such as
     * {@link InitializingBean InitializingBean's}
     * {@link InitializingBean#afterPropertiesSet()}
     * method or a custom init-method.
     * @param classLoader the owning class loader
     */
    void setBeanClassLoader(ClassLoader classLoader);

}
