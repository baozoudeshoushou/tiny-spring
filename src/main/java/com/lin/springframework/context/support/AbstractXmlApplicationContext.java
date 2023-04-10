package com.lin.springframework.context.support;

import com.lin.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.lin.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * @Author linjiayi5
 * @Date 2023/4/7 21:13:44
 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext {

    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
        // Create a new XmlBeanDefinitionReader for the given BeanFactory.
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        String[] configLocations = getConfigLocations();
        if (configLocations != null) {
            beanDefinitionReader.loadBeanDefinitions(configLocations);
        }
    }

    /**
     * Return an array of resource locations, referring to the XML bean definition
     * files that this context should be built with.
     * @return an array of resource locations, or {@code null} if none
     */
    protected abstract String[] getConfigLocations();

}
