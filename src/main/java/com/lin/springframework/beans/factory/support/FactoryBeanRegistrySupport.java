package com.lin.springframework.beans.factory.support;

import com.lin.springframework.beans.BeansException;
import com.lin.springframework.beans.factory.FactoryBean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author linjiayi5
 * @Date 2023/4/11 12:28:56
 */
public class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {

    /** Cache of singleton objects created by FactoryBeans: FactoryBean name to object. */
    private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>(16);

    protected Class<?> getTypeForFactoryBean(FactoryBean<?> factoryBean) {
        try {
            return factoryBean.getObjectType();
        }
        catch (Throwable ex) {
            // Thrown from the FactoryBean's getObjectType implementation.
            return null;
        }
    }

    /**
     * Obtain an object to expose from the given FactoryBean, if available
     * in cached form. Quick check for minimal synchronization.
     * @param beanName the name of the bean
     * @return the object obtained from the FactoryBean,
     * or {@code null} if not available
     */
    protected Object getCachedObjectForFactoryBean(String beanName) {
        return this.factoryBeanObjectCache.get(beanName);
    }

    /**
     * Obtain an object to expose from the given FactoryBean.
     * @param factory the FactoryBean instance
     * @param beanName the name of the bean
     * @return the object obtained from the FactoryBean
     * @see com.lin.springframework.beans.factory.FactoryBean#getObject()
     */
    protected Object getObjectFromFactoryBean(FactoryBean<?> factory, String beanName) {
        if (factory.isSingleton()) {
            Object object = this.factoryBeanObjectCache.get(beanName);
            if (object == null) {
                object = doGetObjectFromFactoryBean(factory, beanName);
            }
            this.factoryBeanObjectCache.put(beanName, object);
            return object;
        }
        else {
            return doGetObjectFromFactoryBean(factory, beanName);
        }
    }

    /**
     * Obtain an object to expose from the given FactoryBean.
     * @param factory the FactoryBean instance
     * @param beanName the name of the bean
     * @return the object obtained from the FactoryBean
     * @see com.lin.springframework.beans.factory.FactoryBean#getObject()
     */
    private Object doGetObjectFromFactoryBean(final FactoryBean<?> factory, final String beanName){
        try {
            return factory.getObject();
        }
        catch (Exception e) {
            throw new BeansException("FactoryBean threw exception on object[" + beanName + "] creation", e);
        }
    }

}
