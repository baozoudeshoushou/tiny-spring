package com.lin.springframework.aop.event;

import com.lin.springframework.context.ApplicationListener;
import com.lin.springframework.context.event.ContextRefreshedEvent;

/**
 * @Author linjiayi5
 * @Date 2023/4/13 11:33:24
 */
public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("刷新事件：" + this.getClass().getName());
    }

}