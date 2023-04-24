package com.lin.springframework.aop.framework;

import com.lin.springframework.aop.Advisor;
import com.lin.springframework.aop.Pointcut;
import com.lin.springframework.aop.PointcutAdvisor;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author linjiayi5
 * @Date 2023/4/24 11:36:59
 */
public class DefaultAdvisorChainFactory implements AdvisorChainFactory {

    @Override
    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(AdvisedSupport config, Method method, Class<?> targetClass) {
        Class<?> actualClass = (targetClass != null ? targetClass : method.getDeclaringClass());
        Advisor[] advisors = config.getAdvisors().toArray(new Advisor[0]);
        List<Object> interceptorList = new ArrayList<>(advisors.length);
        for (Advisor advisor : advisors) {
            if (advisor instanceof PointcutAdvisor pointcutAdvisor) {
                Pointcut pointcut = pointcutAdvisor.getPointcut();
                if (!pointcut.getClassFilter().matches(actualClass)) {
                    continue;
                }
                if (!pointcut.getMethodMatcher().matches(method, actualClass)) {
                    continue;
                }
                MethodInterceptor interceptor = (MethodInterceptor) advisor.getAdvice();
                interceptorList.add(interceptor);
            }
        }
        return interceptorList;
    }

}
