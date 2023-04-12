package com.lin.springframework.context.event;

import com.lin.springframework.context.ApplicationEvent;
import com.lin.springframework.context.ApplicationListener;

/**
 * Interface to be implemented by objects that can manage a number of
 * {@link ApplicationListener} objects and publish events to them.
 *
 * @Author linjiayi5
 * @Date 2023/4/11 16:20:06
 */
public interface ApplicationEventMulticaster {

    /**
     * Add a listener to be notified of all events.
     * @param listener the listener to add
     */
    void addApplicationListener(ApplicationListener<?> listener);

    /**
     * Remove a listener from the notification list.
     * @param listener the listener to remove
     */
    void removeApplicationListener(ApplicationListener<?> listener);

    /**
     * Multicast the given application event to appropriate listeners.
     * @param event the event to multicast
     */
    void multicastEvent(ApplicationEvent event);

}
