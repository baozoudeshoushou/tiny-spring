package com.lin.springframework.beans.factory.support;

import com.lin.springframework.beans.BeansException;
import com.lin.springframework.core.io.Resource;
import com.lin.springframework.core.io.ResourceLoader;

/**
 * Simple interface for bean definition readers that specifies load methods with
 * {@link Resource} and {@link String} location parameters.
 *
 * @Author linjiayi5
 * @Date 2023/4/7 19:42:45
 */
public interface BeanDefinitionReader {

    /**
     * Return the bean factory to register the bean definitions with.
     * @return the bean factory to register the bean definitions with.
     */
    BeanDefinitionRegistry getRegistry();

    /**
     * Return the {@link ResourceLoader} to use for resource locations.
     * @return the resourceLoader to use for resource locations.
     */
    ResourceLoader getResourceLoader();

    /**
     * Load bean definitions from the specified resource.
     * @param resource the resource descriptor
     * @return the number of bean definitions found
     * @throws BeansException in case of loading or parsing errors
     */
    int loadBeanDefinitions(Resource resource) throws BeansException;

    /**
     * Load bean definitions from the specified resources.
     * @param resources the resource descriptors
     * @return the number of bean definitions found
     * @throws BeansException in case of loading or parsing errors
     */
    int loadBeanDefinitions(Resource... resources) throws BeansException;

    /**
     * Load bean definitions from the specified resource location.
     * @param location the resource location, to be loaded with the {@code ResourceLoader}
     * @return the number of bean definitions found
     * @throws BeansException in case of loading or parsing errors
     */
    int loadBeanDefinitions(String location) throws BeansException;

    /**
     * Load bean definitions from the specified resource locations.
     * @param locations the resource locations, to be loaded with the {@code ResourceLoader}
     * @return the number of bean definitions found
     * @throws BeansException in case of loading or parsing errors
     */
    int loadBeanDefinitions(String... locations) throws BeansException;
}
