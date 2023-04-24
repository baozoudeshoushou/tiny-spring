package com.lin.springframework.aop.framework;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;
import java.util.List;

/**
 * CGLIB-based {@link AopProxy} implementation for the Spring AOP framework.
 *
 * @Author linjiayi5
 * @Date 2023/4/13 14:24:00
 */
public class CglibAopProxy implements AopProxy {

    private final AdvisedSupport advised;

    public CglibAopProxy(AdvisedSupport advised) {
        this.advised = advised;
    }

    @Override
    public Object getProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(advised.getTargetSource().getTarget().getClass());
        enhancer.setInterfaces(advised.getTargetSource().getTargetClass());
        enhancer.setCallback(new DynamicAdvisedInterceptor(advised));
        return enhancer.create();
    }

    private static class DynamicAdvisedInterceptor implements MethodInterceptor {

        private final AdvisedSupport advised;

        public DynamicAdvisedInterceptor(AdvisedSupport advised) {
            this.advised = advised;
        }

        @Override
        public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            Object target = advised.getTargetSource().getTarget();
            Class<?> targetClass = target.getClass();

            Object retVal;

            // 当前方法的拦截器链
            List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);

            if (chain.isEmpty()) {
                retVal = method.invoke(target, args);
            }
            else {
                // 将拦截器封装在 ReflectiveMethodInvocation
                CglibMethodInvocation invocation = new CglibMethodInvocation(proxy, target, method, args, targetClass, chain, methodProxy);
                retVal = invocation.proceed();
            }

            return retVal;
        }
    }


    private static class CglibMethodInvocation extends ReflectiveMethodInvocation {
        private final MethodProxy methodProxy;

        public CglibMethodInvocation(Object proxy, Object target, Method method, Object[] arguments,
                                     Class<?> targetClass, List<Object> chain, MethodProxy methodProxy) {
            super(proxy, target, method, arguments, targetClass, chain);
            this.methodProxy = methodProxy;
        }

        @Override
        public Object proceed() throws Throwable {
            return super.proceed();
        }
    }

}