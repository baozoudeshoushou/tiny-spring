package com.lin.springframework.aop;

import org.aopalliance.aop.Advice;

/**
 * Advisor 承担了 Pointcut 和 Advice 的组合，Pointcut 用于获取 JoinPoint，
 * 而 Advice 决定于 JoinPoint 执行什么操作。
 * Base interface holding AOP <b>advice</b> (action to take at a joinpoint)
 * and a filter determining the applicability of the advice (such as
 * a pointcut). <i>This interface is not for use by Spring users, but to
 * allow for commonality in support for different types of advice.</i>
 *
 * @Author linjiayi5
 * @Date 2023/4/13 16:44:45
 */
public interface Advisor {

    /**
     * Return the advice part of this aspect. An advice may be an
     * interceptor, a before advice, a throws advice, etc.
     * @return the advice that should apply if the pointcut matches
     * @see org.aopalliance.intercept.MethodInterceptor
     * @see BeforeAdvice
     * @see ThrowsAdvice
     * @see AfterReturningAdvice
     */
    Advice getAdvice();

}
