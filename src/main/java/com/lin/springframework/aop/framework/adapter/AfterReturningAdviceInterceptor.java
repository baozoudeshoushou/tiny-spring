package com.lin.springframework.aop.framework.adapter;

import cn.hutool.core.lang.Assert;
import com.lin.springframework.aop.AfterReturningAdvice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @Author linjiayi5
 * @Date 2023/4/24 16:31:30
 */
public class AfterReturningAdviceInterceptor implements MethodInterceptor {

    private AfterReturningAdvice advice;

    public AfterReturningAdviceInterceptor() {

    }

    /**
     * Create a new AfterReturningAdviceInterceptor for the given advice.
     * @param advice the AfterReturningAdvice to wrap
     */
    public AfterReturningAdviceInterceptor(AfterReturningAdvice advice) {
        Assert.notNull(advice, "Advice must not be null");
        this.advice = advice;
    }


    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        Object retVal = mi.proceed();
        this.advice.afterReturning(retVal, mi.getMethod(), mi.getArguments(), mi.getThis());
        return retVal;
    }

}
