package com.lin.springframework.context;

import java.time.Clock;
import java.util.EventObject;

/**
 * Class to be extended by all application events. Abstract as it
 * doesn't make sense for generic events to be published directly.
 *
 * @Author linjiayi5
 * @Date 2023/4/11 15:56:32
 * @see com.lin.springframework.context.ApplicationListener
 */
public abstract class ApplicationEvent extends EventObject {

    private final long timestamp;

    /**
     * Create a new {@code ApplicationEvent} with its {@link #getTimestamp() timestamp}
     * set to {@link System#currentTimeMillis()}.
     * @param source the object on which the event initially occurred or with
     * which the event is associated (never {@code null})
     * @see #ApplicationEvent(Object, Clock)
     */
    public ApplicationEvent(Object source) {
        super(source);
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * Create a new {@code ApplicationEvent} with its {@link #getTimestamp() timestamp}
     * set to the value returned by {@link Clock#millis()} in the provided {@link Clock}.
     * <p>This constructor is typically used in testing scenarios.
     * @param source the object on which the event initially occurred or with
     * which the event is associated (never {@code null})
     * @param clock a clock which will provide the timestamp
     * @see #ApplicationEvent(Object)
     */
    public ApplicationEvent(Object source, Clock clock) {
        super(source);
        this.timestamp = clock.millis();
    }

    /**
     * Return the time in milliseconds when the event occurred.
     * @see #ApplicationEvent(Object)
     * @see #ApplicationEvent(Object, Clock)
     */
    public final long getTimestamp() {
        return this.timestamp;
    }

}
