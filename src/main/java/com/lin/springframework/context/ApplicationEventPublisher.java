package com.lin.springframework.context;

/**
 * Interface that encapsulates event publication functionality.
 *
 * @Author linjiayi5
 * @Date 2023/4/11 15:58:57
 */
public interface ApplicationEventPublisher {

    /**
     * Notify all <strong>matching</strong> listeners registered with this
     * application of an application event. Events may be framework events
     * (such as ContextRefreshedEvent) or application-specific events.
     * <p>Such an event publication step is effectively a hand-off to the
     * multicaster and does not imply synchronous/asynchronous execution
     * or even immediate execution at all. Event listeners are encouraged
     * to be as efficient as possible, individually using asynchronous
     * execution for longer-running and potentially blocking operations.
     * @param event the event to publish
     * @see com.lin.springframework.context.event.ContextRefreshedEvent
     * @see com.lin.springframework.context.event.ContextClosedEvent
     */
    void publishEvent(ApplicationEvent event);

}
