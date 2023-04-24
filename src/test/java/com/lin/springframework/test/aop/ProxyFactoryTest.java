package com.lin.springframework.test.aop;

import com.lin.springframework.aop.TargetSource;
import com.lin.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import com.lin.springframework.aop.framework.ProxyFactory;
import com.lin.springframework.aop.framework.adapter.AfterReturningAdviceInterceptor;
import com.lin.springframework.aop.framework.adapter.MethodBeforeAdviceInterceptor;
import com.lin.springframework.test.common.WorldServiceAfterReturnAdvice;
import com.lin.springframework.test.common.WorldServiceBeforeAdvice;
import com.lin.springframework.test.service.WorldService;
import com.lin.springframework.test.service.WorldServiceImpl;
import org.junit.Test;

/**
 * @Author linjiayi5
 * @Date 2023/4/24 16:02:18
 */
public class ProxyFactoryTest {

    @Test
    public void testAdvisor() throws Exception {
        WorldService worldService = new WorldServiceImpl();

        // Advisor 是 Pointcut 和 Advice 的组合
        String expression = "execution(* com.lin.springframework.test.service.WorldService.explode(..))";

        //第一个切面
        AspectJExpressionPointcutAdvisor advisor1 = new AspectJExpressionPointcutAdvisor();
        advisor1.setExpression(expression);
        MethodBeforeAdviceInterceptor methodInterceptor = new MethodBeforeAdviceInterceptor(new WorldServiceBeforeAdvice());
        advisor1.setAdvice(methodInterceptor);

        //第二个切面
        AspectJExpressionPointcutAdvisor advisor2 = new AspectJExpressionPointcutAdvisor();
        advisor2.setExpression(expression);
        AfterReturningAdviceInterceptor afterReturningAdviceInterceptor = new AfterReturningAdviceInterceptor(new WorldServiceAfterReturnAdvice());
        advisor2.setAdvice(afterReturningAdviceInterceptor);

        //通过ProxyFactory来获得代理
        ProxyFactory factory = new ProxyFactory();
        TargetSource targetSource = new TargetSource(worldService);
        factory.setTargetSource(targetSource);
        factory.setProxyTargetClass(true);
        factory.addAdvisor(advisor1);
        factory.addAdvisor(advisor2);
        WorldService proxy = (WorldService) factory.getProxy();
        proxy.explode();
    }

}
