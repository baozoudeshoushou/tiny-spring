package com.lin.springframework.aop.framework.autoproxy;

import com.lin.springframework.aop.Advisor;
import com.lin.springframework.aop.ClassFilter;
import com.lin.springframework.aop.Pointcut;
import com.lin.springframework.aop.TargetSource;
import com.lin.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import com.lin.springframework.aop.framework.AdvisedSupport;
import com.lin.springframework.aop.framework.ProxyFactory;
import com.lin.springframework.beans.BeansException;
import com.lin.springframework.beans.factory.BeanFactory;
import com.lin.springframework.beans.factory.BeanFactoryAware;
import com.lin.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.lin.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import com.lin.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author linjiayi5
 * @Date 2023/4/13 16:48:29
 */
public class DefaultAdvisorAutoProxyCreator implements SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private DefaultListableBeanFactory beanFactory;

    private final Map<Object, Object> earlyProxyReferences = new ConcurrentHashMap<>(16);

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean != null) {
            if (this.earlyProxyReferences.remove(beanName) != bean) {
                return wrapIfNecessary(bean, beanName);
            }
        }
        return bean;
    }

    @Override
    public Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {
        this.earlyProxyReferences.put(beanName, bean);
        return wrapIfNecessary(bean, beanName);
    }

    protected Object wrapIfNecessary(Object bean, String beanName) {
        // AOP 基础类跳过，避免死循环
        if (isInfrastructureClass(bean.getClass())) {
            return bean;
        }

        Collection<AspectJExpressionPointcutAdvisor> advisors = beanFactory.getBeansOfType(AspectJExpressionPointcutAdvisor.class).values();

        ProxyFactory proxyFactory = new ProxyFactory();

        for (AspectJExpressionPointcutAdvisor advisor : advisors) {
            Pointcut pointcut = advisor.getPointcut();
            if (!pointcut.getClassFilter().matches(bean.getClass())) {
                continue;
            }

            TargetSource targetSource = new TargetSource(bean);
            proxyFactory.setTargetSource(targetSource);
            proxyFactory.addAdvisor(advisor);
            proxyFactory.setProxyTargetClass(false);
        }

        // 返回代理对象
        if (!proxyFactory.getAdvisors().isEmpty()) {
            return proxyFactory.getProxy();
        }

        return bean;
    }

    /**
     * 是否是 AOP 相关的基础类
     */
    private boolean isInfrastructureClass(Class<?> beanClass) {
        return Advice.class.isAssignableFrom(beanClass)
                || Pointcut.class.isAssignableFrom(beanClass)
                || Advisor.class.isAssignableFrom(beanClass);
    }

}
