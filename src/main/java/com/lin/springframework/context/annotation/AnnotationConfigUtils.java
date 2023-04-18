package com.lin.springframework.context.annotation;

import com.lin.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.lin.springframework.beans.factory.config.BeanDefinition;
import com.lin.springframework.beans.factory.support.BeanDefinitionRegistry;

/**
 * Utility class that allows for convenient registration of common
 * {@link com.lin.springframework.beans.factory.config.BeanPostProcessor} and
 * {@link com.lin.springframework.beans.factory.config.BeanFactoryPostProcessor}
 * definitions for annotation-based configuration. Also registers a common
 * {@link com.lin.springframework.beans.factory.support.AutowireCandidateResolver}.
 *
 * @Author linjiayi5
 * @Date 2023/4/18 16:21:02
 */
public class AnnotationConfigUtils {

    /**
     * The bean name of the internally managed Autowired annotation processor.
     */
    public static final String AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME =
            "org.springframework.context.annotation.internalAutowiredAnnotationProcessor";

    /**
     * Register all relevant annotation post processors in the given registry.
     * @param registry the registry to operate on
     */
    public static void registerAnnotationConfigProcessors(BeanDefinitionRegistry registry) {
        if (!registry.containsBeanDefinition(AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME)) {
            BeanDefinition def = new BeanDefinition(AutowiredAnnotationBeanPostProcessor.class);
            registry.registerBeanDefinition(AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME, def);
        }
    }

}
