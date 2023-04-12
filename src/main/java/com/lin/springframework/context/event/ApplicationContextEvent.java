package com.lin.springframework.context.event;

import com.lin.springframework.context.ApplicationContext;
import com.lin.springframework.context.ApplicationEvent;

/**
 * Base class for events raised for an {@code ApplicationContext}.
 *
 * @Author linjiayi5
 * @Date 2023/4/11 16:13:15
 */
public class ApplicationContextEvent extends ApplicationEvent {

    public ApplicationContextEvent(Object source) {
        super(source);
    }

    /**
     * Get the {@code ApplicationContext} that the event was raised for.
     */
    public final ApplicationContext getApplicationContext() {
        return (ApplicationContext) getSource();
    }

}
