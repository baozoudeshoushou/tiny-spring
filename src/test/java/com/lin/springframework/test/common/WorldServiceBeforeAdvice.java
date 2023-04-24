package com.lin.springframework.test.common;

import com.lin.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * @author derekyi
 * @date 2020/12/6
 */
public class WorldServiceBeforeAdvice implements MethodBeforeAdvice {

	@Override
	public void before(Method method, Object[] args, Object target) throws Throwable {
		System.out.println("BeforeAdvice: do something before the earth explodes");
	}
}
