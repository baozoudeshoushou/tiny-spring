package com.lin.springframework.circular.bean;

import com.lin.springframework.beans.factory.FactoryBean;
import com.lin.springframework.stereotype.Component;

import java.lang.reflect.Proxy;

/**
 * 代理类
 */
@Component
public class HusbandMother implements FactoryBean<IMother> {

    @Override
    public IMother getObject() throws Exception {
        IMother iMother = (IMother) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{IMother.class}, (proxy, method, args) -> "婚后媳妇妈妈的职责被婆婆代理了！" + method.getName());
//        System.out.println(IMother.class.isAssignableFrom(iMother.getClass()));
        return iMother;
    }

    @Override
    public Class<?> getObjectType() {
        return IMother.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
