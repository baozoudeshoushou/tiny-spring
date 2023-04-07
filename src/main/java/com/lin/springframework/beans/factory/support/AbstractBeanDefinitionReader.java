package com.lin.springframework.beans.factory.support;

import cn.hutool.core.lang.Assert;
import com.lin.springframework.core.io.DefaultResourceLoader;
import com.lin.springframework.core.io.ResourceLoader;

/**
 * @Author linjiayi5
 * @Date 2023/4/7 19:50:29
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

    private final BeanDefinitionRegistry registry;

    private ResourceLoader resourceLoader;

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this(registry, new DefaultResourceLoader());
    }

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        Assert.notNull(registry, "Registry must not be null");
        Assert.notNull(resourceLoader, "ResourceLoader must not be null");
        this.registry = registry;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public BeanDefinitionRegistry getRegistry() {
        return registry;
    }

    @Override
    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

}
