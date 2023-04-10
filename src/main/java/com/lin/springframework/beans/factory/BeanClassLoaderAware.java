package com.lin.springframework.beans.factory;

/**
 * A marker superinterface indicating that a bean is eligible to be notified by the
 * Spring container of a particular framework object through a callback-style method.
 * The actual method signature is determined by individual subinterfaces but should
 * typically consist of just one void-returning method that accepts a single argument.
 *
 * <p>Note that merely implementing {@link Aware} provides no default functionality.
 * Rather, processing must be done explicitly, for example in a
 * {@link com.lin.springframework.beans.factory.config.BeanPostProcessor}.
 *
 * @Author linjiayi5
 * @Date 2023/4/10 20:14:48
 */
public interface BeanClassLoaderAware extends Aware {

}
