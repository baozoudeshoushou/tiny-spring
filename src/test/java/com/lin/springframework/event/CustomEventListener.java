package com.lin.springframework.event;

import com.lin.springframework.context.ApplicationListener;

import java.util.Date;

/**
 * @Author linjiayi5
 * @Date 2023/4/13 11:28:52
 */
public class CustomEventListener implements ApplicationListener<CustomEvent> {

    @Override
    public void onApplicationEvent(CustomEvent event) {
        System.out.println("收到：" + event.getSource() + "消息;时间：" + new Date());
        System.out.println("消息：" + event.getId() + ":" + event.getMessage());
    }

}
