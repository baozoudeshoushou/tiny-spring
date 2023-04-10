package com.lin.springframework.beans.factory.support;

import cn.hutool.core.util.StrUtil;
import com.lin.springframework.beans.factory.DisposableBean;
import com.lin.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Method;

/**
 * 因为销毁方法有两种甚至多种方式，所以需要一个适配器类
 * Adapter that implements the {@link DisposableBean} and {@link Runnable}
 * interfaces performing various destruction steps on a given bean instance:
 *
 * @Author linjiayi5
 * @Date 2023/4/10 18:47:47
 */
public class DisposableBeanAdapter implements DisposableBean {

    private final Object bean;

    private final String beanName;

    private String destroyMethodName;

    public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
        this.bean = bean;
        this.beanName = beanName;
        this.destroyMethodName = beanDefinition.getDestroyMethodName();
    }

    @Override
    public void destroy() throws Exception {
        if (bean instanceof DisposableBean disposableBean) {
            disposableBean.destroy();
        }

        if (StrUtil.isNotBlank(destroyMethodName) && !(bean instanceof DisposableBean && "destroy".equals(destroyMethodName))) {
            Method method = bean.getClass().getMethod(destroyMethodName);
            method.invoke(bean);
        }

    }

}
