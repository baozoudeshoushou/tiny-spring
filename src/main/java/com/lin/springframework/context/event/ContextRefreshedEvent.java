package com.lin.springframework.context.event;

/**
 * Event raised when an {@code ApplicationContext} gets initialized or refreshed.
 *
 * @Author linjiayi5
 * @Date 2023/4/11 16:19:20
 */
public class ContextRefreshedEvent extends ApplicationContextEvent {

    public ContextRefreshedEvent(Object source) {
        super(source);
    }

}
