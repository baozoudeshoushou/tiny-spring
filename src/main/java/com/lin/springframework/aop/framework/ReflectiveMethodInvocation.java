package com.lin.springframework.aop.framework;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @Author linjiayi5
 * @Date 2023/4/13 14:24:55
 */
public class ReflectiveMethodInvocation implements MethodInvocation {

    protected final Object proxy;

    /** 目标对象 */
    protected final Object target;

    /** 方法 */
    protected final Method method;

    /** 入参 */
    protected final Object[] arguments;

    protected final Class<?> targetClass;

    /**
     * List of MethodInterceptor and InterceptorAndDynamicMethodMatcher
     * that need dynamic checks.
     */
    protected final List<Object> interceptorsAndDynamicMethodMatchers;

    /**
     * Index from 0 of the current interceptor we're invoking.
     * -1 until we invoke: then the current interceptor.
     */
    private int currentInterceptorIndex = -1;

    public ReflectiveMethodInvocation(Object proxy, Object target, Method method, Object[] arguments,
                                      Class<?> targetClass, List<Object> chain) {
        this.proxy = proxy;
        this.target = target;
        this.method = method;
        this.arguments = arguments;
        this.targetClass = targetClass;
        this.interceptorsAndDynamicMethodMatchers = chain;
    }

    @Override
    public Object proceed() throws Throwable {
        // 执行完所有增强后，执行切点方法 (每调用一次 proceed, currentInterceptorIndex++ )
        if (this.currentInterceptorIndex == this.interceptorsAndDynamicMethodMatchers.size() - 1) {
            return method.invoke(target, arguments);
        }

        // 链式调用
        Object interceptorOrInterceptionAdvice = this.interceptorsAndDynamicMethodMatchers.get(++this.currentInterceptorIndex);
        return ((MethodInterceptor) interceptorOrInterceptionAdvice).invoke(this);
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public Object[] getArguments() {
        return arguments;
    }

    @Override
    public Object getThis() {
        return target;
    }

    @Override
    public AccessibleObject getStaticPart() {
        return method;
    }

}
