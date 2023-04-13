package com.lin.springframework.aop;

import java.lang.reflect.Method;

/**
 * Part of a {@link Pointcut}: Checks whether the target method is eligible for advice.
 *
 * @Author linjiayi5
 * @Date 2023/4/13 14:28:41
 */
public interface MethodMatcher {

    /**
     * Perform static checking whether the given method matches.
     * @param method the candidate method
     * @param targetClass the target class
     * @return whether this method matches statically
     */
    boolean matches(Method method, Class<?> targetClass);

}
