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
import com.lin.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import java.util.Collection;

/**
 * @Author linjiayi5
 * @Date 2023/4/13 16:48:29
 */
public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private DefaultListableBeanFactory beanFactory;

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
        if (isInfrastructureClass(bean.getClass())) {
            return bean;
        }

        Collection<AspectJExpressionPointcutAdvisor> advisors = beanFactory.getBeansOfType(AspectJExpressionPointcutAdvisor.class).values();
        for (AspectJExpressionPointcutAdvisor advisor : advisors) {
            Pointcut pointcut = advisor.getPointcut();
            ClassFilter classFilter = pointcut.getClassFilter();
            if (!classFilter.matches(bean.getClass())) {
                continue;
            }

            AdvisedSupport advisedSupport = new AdvisedSupport();

            TargetSource targetSource = new TargetSource(bean);
            advisedSupport.setTargetSource(targetSource);
            advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
            advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
            advisedSupport.setProxyTargetClass(false);
            // 返回代理对象
            return new ProxyFactory(advisedSupport).getProxy();
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
