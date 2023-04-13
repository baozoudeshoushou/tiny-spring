package com.lin.springframework.context.event;

import com.lin.springframework.beans.factory.BeanFactory;
import com.lin.springframework.context.ApplicationEvent;
import com.lin.springframework.context.ApplicationListener;

/**
 * @Author linjiayi5
 * @Date 2023/4/13 11:08:14
 */
public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {

    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }

    @Override
    public void multicastEvent(ApplicationEvent event) {
        for (ApplicationListener applicationListener : getApplicationListeners(event)) {
            applicationListener.onApplicationEvent(event);
        }
    }
}
