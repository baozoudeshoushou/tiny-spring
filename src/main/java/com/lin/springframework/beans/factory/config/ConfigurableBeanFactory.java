package com.lin.springframework.beans.factory.config;

import com.lin.springframework.beans.factory.HierarchicalBeanFactory;

/**
 * @Author linjiayi5
 * @Date 2023/4/7 11:41:59
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {

    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

}
