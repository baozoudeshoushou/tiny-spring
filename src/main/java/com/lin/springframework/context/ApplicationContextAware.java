package com.lin.springframework.context;

import com.lin.springframework.beans.BeansException;
import com.lin.springframework.beans.factory.Aware;

/**
 * Interface to be implemented by any object that wishes to be notified
 * of the {@link ApplicationContext} that it runs in.
 *
 * @Author linjiayi5
 * @Date 2023/4/10 20:20:01
 */
public interface ApplicationContextAware extends Aware {

    /**
     * Set the ApplicationContext that this object runs in.
     * Normally this call will be used to initialize the object.
     * <p>Invoked after population of normal bean properties but before an init callback such
     * as {@link com.lin.springframework.beans.factory.InitializingBean#afterPropertiesSet()}
     * or a custom init-method. Invoked after {@link ResourceLoaderAware#setResourceLoader},
     * {@link ApplicationEventPublisherAware#setApplicationEventPublisher} and
     * {@link MessageSourceAware}, if applicable.
     * @param applicationContext the ApplicationContext object to be used by this object
     * @throws BeansException if thrown by application context methods
     */
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;

}
