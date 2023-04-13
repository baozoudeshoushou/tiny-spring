package com.lin.springframework.aop;

/**
 * Filter that restricts matching of a pointcut or introduction to
 * a given set of target classes.
 *
 * @Author linjiayi5
 * @Date 2023/4/13 14:28:12
 */
public interface ClassFilter {

    /**
     * Should the pointcut apply to the given interface or target class?
     * @param clazz the candidate target class
     * @return whether the advice should apply to the given target class
     */
    boolean matches(Class<?> clazz);

}
