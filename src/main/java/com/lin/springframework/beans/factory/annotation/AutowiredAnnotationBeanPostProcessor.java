package com.lin.springframework.beans.factory.annotation;

import cn.hutool.core.bean.BeanUtil;
import com.lin.springframework.beans.BeansException;
import com.lin.springframework.beans.PropertyValues;
import com.lin.springframework.beans.factory.BeanFactory;
import com.lin.springframework.beans.factory.BeanFactoryAware;
import com.lin.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.lin.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.lin.springframework.utils.ClassUtils;

import java.lang.reflect.Field;

/**
 * @Author linjiayi5
 * @Date 2023/4/18 11:26:55
 */
public class AutowiredAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        clazz = ClassUtils.isCglibProxyClass(clazz) ? clazz.getSuperclass() : clazz;

        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            // 处理注解 @Value
            Value valueAnnotation = field.getAnnotation(Value.class);
            if (valueAnnotation != null) {
                String value = valueAnnotation.value();
                value = beanFactory.resolveEmbeddedValue(value);
                BeanUtil.setFieldValue(bean, field.getName(), value);
            }

            // 处理注解 @Autowired
            Autowired autowire = field.getAnnotation(Autowired.class);
            if (autowire != null) {
                Class<?> fieldType = field.getType();
                String dependentBeanName;
                Object dependentBean;

                Qualifier qualifier = field.getAnnotation(Qualifier.class);
                if (qualifier != null) {
                    dependentBeanName = qualifier.value();
                    dependentBean = beanFactory.getBean(dependentBeanName);
                }
                else {
                    dependentBean = beanFactory.getBean(fieldType);
                }
                BeanUtil.setFieldValue(bean, field.getName(), dependentBean);
            }
        }

        return pvs;
    }

}
