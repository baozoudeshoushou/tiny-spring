package com.lin.springframework;

import com.lin.springframework.bean.UserService;
import com.lin.springframework.beans.factory.config.BeanDefinition;
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

        BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
        beanFactory.registerBeanDefinition("userService", beanDefinition);

        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.queryUserInfo();

        UserService singletonUserService = (UserService) beanFactory.getSingleton("userService");
        singletonUserService.queryUserInfo();
    }

    @Test
    public void testInstantiationStrategy() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
        beanFactory.registerBeanDefinition("userService", beanDefinition);

        UserService userService = (UserService) beanFactory.getBean("userService", "lin");
        userService.queryUserInfo();
    }

}
