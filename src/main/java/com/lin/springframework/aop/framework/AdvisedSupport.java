package com.lin.springframework.aop.framework;

import com.lin.springframework.aop.MethodMatcher;
import com.lin.springframework.aop.TargetSource;
import org.aopalliance.intercept.MethodInterceptor;

/**
 * Base class for AOP proxy configuration managers.
 * These are not themselves AOP proxies, but subclasses of this class are
 * normally factories from which AOP proxy instances are obtained directly.
 *
 * @Author linjiayi5
 * @Date 2023/4/13 14:27:39
 * @see com.lin.springframework.aop.framework.AopProxy
 */
public class AdvisedSupport implements Advised {

    /** Proxy Config 是否开启 cglib 代理 */
    private boolean proxyTargetClass = false;

    /** 被代理的目标对象 */
    private TargetSource targetSource;

    /** 方法拦截器 */
    private MethodInterceptor methodInterceptor;

    /** 方法匹配器(检查目标方法是否符合通知条件) */
    private MethodMatcher methodMatcher;

    public boolean isProxyTargetClass() {
        return proxyTargetClass;
    }

    public void setProxyTargetClass(boolean proxyTargetClass) {
        this.proxyTargetClass = proxyTargetClass;
    }

    @Override
    public void setTargetSource(TargetSource targetSource) {
        this.targetSource = targetSource;
    }

    @Override
    public TargetSource getTargetSource() {
        return targetSource;
    }

    public MethodInterceptor getMethodInterceptor() {
        return methodInterceptor;
    }

    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }

    public MethodMatcher getMethodMatcher() {
        return methodMatcher;
    }

    public void setMethodMatcher(MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }

}
