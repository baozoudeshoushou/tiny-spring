package com.lin.springframework.aop.framework;

import com.lin.springframework.aop.TargetSource;

/**
 * Interface to be implemented by classes that hold the configuration
 * of a factory of AOP proxies. This configuration includes the
 * Interceptors and other advice, Advisors, and the proxied interfaces.
 *
 * @Author linjiayi5
 * @Date 2023/4/13 15:20:38
 * @see com.lin.springframework.aop.framework.AdvisedSupport
 */
public interface Advised {

    /**
     * Change the {@code TargetSource} used by this {@code Advised} object.
     * <p>Only works if the configuration isn't {@linkplain #isFrozen frozen}.
     * @param targetSource new TargetSource to use
     */
    void setTargetSource(TargetSource targetSource);

    /**
     * Return the {@code TargetSource} used by this {@code Advised} object.
     */
    TargetSource getTargetSource();

}
