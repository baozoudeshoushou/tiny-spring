package com.lin.springframework.aop.bean;

import com.lin.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * @Author linjiayi5
 * @Date 2023/4/13 21:37:30
 */
public class UserServiceBeforeAdvice implements MethodBeforeAdvice {

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("拦截方法" + method.getName());
    }
}
