package com.lin.springframework.aop;

import java.lang.reflect.Method;

/**
 * @Author linjiayi5
 * @Date 2023/4/24 16:29:18
 */
public interface AfterReturningAdvice extends AfterAdvice {

    /**
     * Callback after a given method successfully returned.
     * @param returnValue the value returned by the method, if any
     * @param method the method being invoked
     * @param args the arguments to the method
     * @param target the target of the method invocation. May be {@code null}.
     * @throws Throwable if this object wishes to abort the call.
     * Any exception thrown will be returned to the caller if it's
     * allowed by the method signature. Otherwise the exception
     * will be wrapped as a runtime exception.
     */
    void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable;

}
