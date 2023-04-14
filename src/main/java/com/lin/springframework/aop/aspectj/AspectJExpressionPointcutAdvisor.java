package com.lin.springframework.aop.aspectj;

import com.lin.springframework.aop.Pointcut;
import com.lin.springframework.aop.PointcutAdvisor;
import org.aopalliance.aop.Advice;

/**
 * Spring AOP Advisor that can be used for any AspectJ pointcut expression.
 *
 * @Author linjiayi5
 * @Date 2023/4/13 16:45:16
 */
public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {

    /** 切面 */
    private AspectJExpressionPointcut pointcut;

    /** 具体的拦截方法 */
    private Advice advice;

    /** 表达式 */
    private String expression;

    @Override
    public Advice getAdvice() {
        return advice;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }

    @Override
    public Pointcut getPointcut() {
        if (pointcut == null) {
            pointcut = new AspectJExpressionPointcut(expression);
        }
        return pointcut;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

}
