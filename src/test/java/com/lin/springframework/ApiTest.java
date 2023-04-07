package com.lin.springframework;

import com.lin.springframework.bean.UserDao;
import com.lin.springframework.bean.UserService;
import com.lin.springframework.beans.PropertyValue;
import com.lin.springframework.beans.PropertyValues;
import com.lin.springframework.beans.factory.config.BeanDefinition;
import com.lin.springframework.beans.factory.config.BeanReference;
import com.lin.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.lin.springframework.beans.factory.support.SimpleInstantiationStrategy;
import org.junit.Test;

/**
 * @Author linjiayi5
 * @Date 2023/4/4 16:19:05
 */
public class ApiTest {

    @Test
    public void testBeanFactory() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        beanFactory.registerBeanDefinition("userDao", new BeanDefinition(UserDao.class));

        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("uId", "10001"));
        propertyValues.addPropertyValue(new PropertyValue("userDao", new BeanReference("userDao")));

        beanFactory.registerBeanDefinition("userService", new BeanDefinition(UserService.class, propertyValues));

        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.queryUserInfo();
    }


}
