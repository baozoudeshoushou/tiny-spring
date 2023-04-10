package com.lin.springframework.context.support;

import com.lin.springframework.beans.BeansException;
import com.lin.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.lin.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.lin.springframework.beans.factory.config.BeanPostProcessor;
import com.lin.springframework.context.ConfigurableApplicationContext;
import com.lin.springframework.core.io.DefaultResourceLoader;

import java.util.Map;

/**
 * @Author linjiayi5
 * @Date 2023/4/7 21:12:51
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    @Override
    public void refresh() throws BeansException {
        // 1. 创建 Beanfactory，并加载 BeanDefinition
        refreshBeanFactory();

        // 2. 获取 BeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        // 3. 在 Bean 实例化之前执行 BeanFactoryPostProcessor
        invokeBeanFactoryPostProcessors(beanFactory);

        // 4. BeanPostProcessor 需要提前于其他 Bean 对象实例化之前执行注册操作
        registerBeanPostProcessors(beanFactory);

        // 5. 提前实例化单例 Bean 对象
        beanFactory.preInstantiateSingletons();

    }

    protected abstract void refreshBeanFactory() throws BeansException;

    protected abstract ConfigurableListableBeanFactory getBeanFactory();

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

}
