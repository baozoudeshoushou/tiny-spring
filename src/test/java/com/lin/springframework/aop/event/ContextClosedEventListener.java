package com.lin.springframework.aop.event;

import com.lin.springframework.context.ApplicationListener;
import com.lin.springframework.context.event.ContextClosedEvent;

/**
 * @Author linjiayi5
 * @Date 2023/4/13 11:32:42
 */
public class ContextClosedEventListener implements ApplicationListener<ContextClosedEvent> {

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println("关闭事件：" + this.getClass().getName());
    }

}
