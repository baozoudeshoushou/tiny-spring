package com.lin.springframework.beans.factory;

import com.lin.springframework.beans.BeansException;

/**
 * The root interface for accessing a Spring bean container.
 * @Author linjiayi5
 * @Date 2023/4/4 16:14:56
 */
public interface BeanFactory {

    /**
     * Return an instance, which may be shared or independent, of the specified bean.
     * <p>This method allows a Spring BeanFactory to be used as a replacement for the
     * Singleton or Prototype design pattern. Callers may retain references to
     * returned objects in the case of Singleton beans.
     * <p>Translates aliases back to the corresponding canonical bean name.
     * <p>Will ask the parent factory if the bean cannot be found in this factory instance.
     * @param name the name of the bean to retrieve
     * @return an instance of the bean
     * @throws BeansException if the bean could not be obtained
     */
    Object getBean(String name) throws BeansException;

    /**
     * Return an instance, which may be shared or independent, of the specified bean.
     * @param name the name of the bean to retrieve
     * @return an instance of the bean
     * @throws BeansException if the bean could not be created
     */
    Object getBean(String name, Object... args) throws BeansException;

    /**
     * Return an instance, which may be shared or independent, of the specified bean.
     * @param name the name of the bean to retrieve
     * @param requiredType type the bean must match; can be an interface or superclass
     * @return an instance of the bean
     * @throws BeansException if the bean could not be created
     */
    <T> T getBean(String name, Class<T> requiredType) throws BeansException;

    /**
     * Return the bean instance that uniquely matches the given object type, if any.
     * <p>This method goes into {@link ListableBeanFactory} by-type lookup territory
     * but may also be translated into a conventional by-name lookup based on the name
     * of the given type. For more extensive retrieval operations across sets of beans,
     * use {@link ListableBeanFactory} and/or {@link BeanFactoryUtils}.
     * @param requiredType type the bean must match; can be an interface or superclass
     * @return an instance of the single bean matching the required type
     * @throws BeansException if the bean could not be created
     * @see ListableBeanFactory
     */
    <T> T getBean(Class<T> requiredType) throws BeansException;

}
