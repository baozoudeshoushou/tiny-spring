package com.lin.springframework.context.support;

import com.lin.springframework.beans.BeansException;
import com.lin.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.lin.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.lin.springframework.beans.factory.config.BeanPostProcessor;
import com.lin.springframework.context.ApplicationEvent;
import com.lin.springframework.context.ApplicationListener;
import com.lin.springframework.context.ConfigurableApplicationContext;
import com.lin.springframework.context.event.ApplicationEventMulticaster;
import com.lin.springframework.context.event.ContextClosedEvent;
import com.lin.springframework.context.event.ContextRefreshedEvent;
import com.lin.springframework.context.event.SimpleApplicationEventMulticaster;
import com.lin.springframework.core.io.DefaultResourceLoader;

import java.util.Collection;
import java.util.Map;

/**
 * @Author linjiayi5
 * @Date 2023/4/7 21:12:51
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

    private ApplicationEventMulticaster applicationEventMulticaster;

    @Override
    public void refresh() throws BeansException {
        // 创建 BeanFactory，加载 BeanDefinition
        ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();

        // Prepare the bean factory for use in this context. 对 BeanFactory 进行各种功能填充
        prepareBeanFactory(beanFactory);

        // 在 Bean 实例化之前执行 BeanFactoryPostProcessor
        invokeBeanFactoryPostProcessors(beanFactory);

        // BeanPostProcessor 需要提前于其他 Bean 对象实例化之前执行注册操作
        registerBeanPostProcessors(beanFactory);

        // 初始化事件发布者
        initApplicationEventMulticaster();

        // 注册事件监听器
        registerListeners();

        // 提前实例化单例 Bean 对象
        finishBeanFactoryInitialization(beanFactory);

        // 发布容器刷新完成事件
        finishRefresh();
    }

    /**
     * Tell the subclass to refresh the internal bean factory.
     * @return the fresh BeanFactory instance
     * @see #refreshBeanFactory()
     * @see #getBeanFactory()
     */
    protected ConfigurableListableBeanFactory obtainFreshBeanFactory() {
        refreshBeanFactory();
        return getBeanFactory();
    }

    /**
     * Subclasses must implement this method to perform the actual configuration load.
     * The method is invoked by {@link #refresh()} before any other initialization work.
     * @throws BeansException
     */
    protected abstract void refreshBeanFactory() throws BeansException;

    /**
     * Subclasses must return their internal bean factory here. They should implement the
     * lookup efficiently, so that it can be called repeatedly without a performance penalty.
     * @return this application context's internal bean factory (never {@code null})
     */
    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    /**
     * Configure the factory's standard context characteristics,
     * such as the context's ClassLoader and post-processors.
     * @param beanFactory the BeanFactory to configure
     */
    protected void prepareBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));
    }

    /**
     * Instantiate and invoke all registered BeanFactoryPostProcessor beans,
     * respecting explicit order if given.
     * <p>Must be called before singleton instantiation.
     */
    protected void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()) {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
    }

    /**
     * Instantiate and register all BeanPostProcessor beans,
     * respecting explicit order if given.
     * <p>Must be called before any instantiation of application beans.
     */
    private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }

    /**
     * Initialize the ApplicationEventMulticaster.
     */
    protected void initApplicationEventMulticaster() {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, applicationEventMulticaster);
    }

    /**
     * Add beans that implement ApplicationListener as listeners.
     * Doesn't affect other listeners, which can be added without being beans.
     */
    protected void registerListeners() {
        Collection<ApplicationListener> applicationListeners = getBeansOfType(ApplicationListener.class).values();
        for (ApplicationListener applicationListener : applicationListeners) {
            applicationEventMulticaster.addApplicationListener(applicationListener);
        }
    }

    /**
     * Finish the initialization of this context's bean factory,
     * initializing all remaining singleton beans.
     */
    protected void finishBeanFactoryInitialization(ConfigurableListableBeanFactory beanFactory) {
        beanFactory.preInstantiateSingletons();
    }

    /**
     * Finish the refresh of this context, invoking the LifecycleProcessor's
     * onRefresh() method and publishing the
     * {@link com.lin.springframework.context.event.ContextRefreshedEvent}.
     */
    @SuppressWarnings("deprecation")
    protected void finishRefresh() {
        publishEvent(new ContextRefreshedEvent(this));
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        applicationEventMulticaster.multicastEvent(event);
    }

    @Override
    public void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void close() {
        // 发布容器关闭事件
        publishEvent(new ContextClosedEvent(this));

        // 执行单例 bean 的销毁方法
        getBeanFactory().destroySingletons();
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return getBeanFactory().getBean(name, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(name, requiredType);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

}
