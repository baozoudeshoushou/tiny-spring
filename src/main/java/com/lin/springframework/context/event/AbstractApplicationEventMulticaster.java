package com.lin.springframework.context.event;

import com.lin.springframework.beans.BeansException;
import com.lin.springframework.beans.factory.BeanFactory;
import com.lin.springframework.beans.factory.BeanFactoryAware;
import com.lin.springframework.context.ApplicationEvent;
import com.lin.springframework.context.ApplicationListener;
import com.lin.springframework.utils.ClassUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Abstract implementation of the {@link ApplicationEventMulticaster} interface,
 * providing the basic listener registration facility.
 *
 * @Author linjiayi5
 * @Date 2023/4/11 16:34:51
 */
public abstract class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster, BeanFactoryAware {

    public final Set<ApplicationListener<ApplicationEvent>> applicationListeners = new LinkedHashSet<>();

    private BeanFactory beanFactory;

    @Override
    public void addApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.add((ApplicationListener<ApplicationEvent>) listener);
    }
    @Override
    public void removeApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.remove(listener);
    }


    @Override
    public final void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * Return a Collection of ApplicationListeners matching the given
     * event type. Non-matching listeners get excluded early.
     * @param event the event to be propagated. Allows for excluding
     * non-matching listeners early, based on cached matching information.
     * @return a Collection of ApplicationListeners
     * @see com.lin.springframework.context.ApplicationListener
     */
    protected Collection<ApplicationListener<?>> getApplicationListeners(ApplicationEvent event) {
        List<ApplicationListener<?>> allListeners = new LinkedList<>();
        for (ApplicationListener<ApplicationEvent> listener : applicationListeners) {
            if (supportsEvent(listener, event)) {
                allListeners.add(listener);
            }
        }
        return allListeners;
    }

    /**
     * 监听器是否对该事件感兴趣
     */
    protected boolean supportsEvent(ApplicationListener<ApplicationEvent> applicationListener, ApplicationEvent event) {
        Class<? extends ApplicationListener> listenerClass = applicationListener.getClass();

        // 按照 CglibSubclassingInstantiationStrategy、SimpleInstantiationStrategy 不同的实例化类型，需要判断后获取目标 class
        Class<?> targetClass = ClassUtils.isCglibProxyClass(listenerClass) ? listenerClass.getSuperclass() :listenerClass;
        Type genericInterface = targetClass.getGenericInterfaces()[0];
        Type actualTypeArgument = ((ParameterizedType) genericInterface).getActualTypeArguments()[0];
        String className = actualTypeArgument.getTypeName();

        Class<?> eventClassName;
        try {
            eventClassName = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new BeansException("wrong event class name: " + className);
        }

        // 判定此 eventClassName 对象所表示的类或接口与指定的 event.getClass() 参数所表示的类或接口是否相同，或是否是其超类或超接口。
        // isAssignableFrom 是用来判断子类和父类的关系的，或者接口的实现类和接口的关系的，默认所有的类的终极父类都是 Object。
        // 如果 A.isAssignableFrom(B)结果是 true，证明 B 可以转换成为 A,也就是 A 可以由 B 转换而来。
        return eventClassName.isAssignableFrom(event.getClass());
    }

}
