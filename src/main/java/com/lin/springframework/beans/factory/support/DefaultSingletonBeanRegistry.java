package com.lin.springframework.beans.factory.support;

import cn.hutool.core.lang.Assert;
import com.lin.springframework.beans.BeansException;
import com.lin.springframework.beans.factory.DisposableBean;
import com.lin.springframework.beans.factory.ObjectFactory;
import com.lin.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author linjiayi5
 * @Date 2023/4/4 16:31:40
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    /**
     * 一级缓存，已创建的普通对象
     * Cache of singleton objects: bean name to bean instance.
     */
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>();

    /**
     * 二级缓存，没有完全初始化的对象，提前暴露
     * Cache of early singleton objects: bean name to bean instance.
     */
    private final Map<String, Object> earlySingletonObjects = new ConcurrentHashMap<>(16);

    /**
     * 三级缓存，存放代理对象
     * Cache of singleton factories: bean name to ObjectFactory.
     */
    private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>(16);

    /** Disposable bean instances: bean name to disposable instance. */
    private final Map<String, DisposableBean> disposableBeans = new LinkedHashMap<>();

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        Assert.notNull(beanName, "Bean name must not be null");
        Assert.notNull(singletonObject, "Singleton object must not be null");
        synchronized (this.singletonObjects) {
            Object oldObject = this.singletonObjects.get(beanName);
            if (oldObject != null) {
                throw new IllegalStateException("Could not register object [" + singletonObject +
                        "] under bean name '" + beanName + "': there is already object [" + oldObject + "] bound");
            }
            addSingleton(beanName, singletonObject);
        }
    }

    /**
     * add singleton, this method can be called by subclass
     * @param beanName
     * @param singletonObject
     */
    protected void addSingleton(String beanName, Object singletonObject) {
        synchronized (this.singletonObjects) {
            this.singletonObjects.put(beanName, singletonObject);

            this.singletonFactories.remove(beanName);
            this.earlySingletonObjects.remove(beanName);
        }
    }

    /**
     * Add the given singleton factory for building the specified singleton
     * if necessary.
     * <p>To be called for eager registration of singletons, e.g. to be able to
     * resolve circular references.
     * @param beanName the name of the bean
     * @param singletonFactory the factory for the singleton object
     */
    protected void addSingletonFactory(String beanName, ObjectFactory<?> singletonFactory) {
        Assert.notNull(singletonFactory, "Singleton factory must not be null");
        synchronized (this.singletonObjects) {
            if (!this.singletonObjects.containsKey(beanName)) {
                this.singletonFactories.put(beanName, singletonFactory);
                this.earlySingletonObjects.remove(beanName);
            }
        }
    }

    @Override
    public Object getSingleton(String beanName) {
        Object singletonObject = singletonObjects.get(beanName);
        if (singletonObject == null) {
            singletonObject = earlySingletonObjects.get(beanName);
            if (singletonObject == null) {
                ObjectFactory<?> singletonFactory = singletonFactories.get(beanName);
                if (singletonFactory != null) {
                    singletonObject = singletonFactory.getObject();
                    // 把三级缓存中的代理对象中的真实对象获取出来，放入二级缓存中
                    earlySingletonObjects.put(beanName, singletonObject);
                    singletonFactories.remove(beanName);
                }
            }
        }
        return singletonObject;
    }

    /**
     * Add the given bean to the list of disposable beans in this registry.
     * <p>Disposable beans usually correspond to registered singletons,
     * matching the bean name but potentially being a different instance
     * (for example, a DisposableBean adapter for a singleton that does not
     * naturally implement Spring's DisposableBean interface).
     * @param beanName the name of the bean
     * @param bean the bean instance
     */
    public void registerDisposableBean(String beanName, DisposableBean bean) {
        synchronized (this.disposableBeans) {
            this.disposableBeans.put(beanName, bean);
        }
    }

    public void destroySingletons() {
        Set<String> keySet = this.disposableBeans.keySet();
        Object[] disposableBeanNames = keySet.toArray();
        for (int i = 0; i < disposableBeanNames.length; i++) {
            Object beanName = disposableBeanNames[i];
            DisposableBean disposableBean = this.disposableBeans.remove(beanName);
            try {
                // TODO Destroy the given bean. Must destroy beans that depend on the given
                //	 * bean before the bean itself. Should not throw any exceptions.
                disposableBean.destroy();
            } catch (Exception e) {
                throw new BeansException("Destroy method on bean with name '" + beanName + "' threw an exception", e);
            }
        }
    }

}
