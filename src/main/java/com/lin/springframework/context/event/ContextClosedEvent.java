package com.lin.springframework.context.event;

/**
 * Event raised when an {@code ApplicationContext} gets closed.
 *
 * @Author linjiayi5
 * @Date 2023/4/11 16:15:32
 */
public class ContextClosedEvent extends ApplicationContextEvent {

    public ContextClosedEvent(Object source) {
        super(source);
    }

}
