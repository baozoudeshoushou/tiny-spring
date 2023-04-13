package com.lin.springframework.aop;

/**
 * Core Spring pointcut abstraction.
 *
 * @Author linjiayi5
 * @Date 2023/4/13 14:28:59
 * @see ClassFilter
 * @see MethodMatcher
 */
public interface Pointcut {

    /**
     * Return the ClassFilter for this pointcut.
     * @return the ClassFilter (never {@code null})
     */
    ClassFilter getClassFilter();

    /**
     * Return the MethodMatcher for this pointcut.
     * @return the MethodMatcher (never {@code null})
     */
    MethodMatcher getMethodMatcher();

}
