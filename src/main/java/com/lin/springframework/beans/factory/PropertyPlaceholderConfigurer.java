package com.lin.springframework.beans.factory;

import com.lin.springframework.beans.BeansException;
import com.lin.springframework.beans.PropertyValue;
import com.lin.springframework.beans.PropertyValues;
import com.lin.springframework.beans.factory.config.BeanDefinition;
import com.lin.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.lin.springframework.core.io.DefaultResourceLoader;
import com.lin.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Properties;

/**
 * Class that resolves ${...} placeholders
 * @Author linjiayi5
 * @Date 2023/4/17 14:22:40
 */
public class PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor {

    /**
     * Default placeholder prefix: {@value}
     */
    public static final String DEFAULT_PLACEHOLDER_PREFIX = "${";

    /**
     * Default placeholder suffix: {@value}
     */
    public static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";

    private String location;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource(location);
        Properties properties = new Properties();
        try {
            properties.load(resource.getInputStream());
        } catch (IOException e) {
            throw new BeansException("Could not load properties", e);
        }
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        for (String beanName : beanDefinitionNames) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
            PropertyValues propertyValues = beanDefinition.getPropertyValues();
            for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                Object value = propertyValue.getValue();
                if (value instanceof String val) {
                    // 解析 ${token}
                    StringBuilder builder = new StringBuilder(val);
                    int startIdx = val.indexOf(DEFAULT_PLACEHOLDER_PREFIX);
                    int stopIdx = val.indexOf(DEFAULT_PLACEHOLDER_SUFFIX);
                    if (startIdx != -1 && stopIdx != -1 && startIdx < stopIdx) {
                        String propKey = val.substring(startIdx + 2, stopIdx);
                        String propVal = properties.getProperty(propKey);
                        builder.replace(startIdx, stopIdx + 1, propVal);
                        propertyValues.addPropertyValue(new PropertyValue(propertyValue.getName(), builder.toString()));
                    }
                }
            }
        }

    }

    public void setLocation(String location) {
        this.location = location;
    }

}
