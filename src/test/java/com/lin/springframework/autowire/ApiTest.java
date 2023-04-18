package com.lin.springframework.autowire;

import com.lin.springframework.autowire.bean.UserService;
import com.lin.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;

/**
 * @Author linjiayi5
 * @Date 2023/4/18 15:48:16
 */
public class ApiTest {

    @Test
    public void test_scan() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:autowire/spring.xml");
        UserService userService = applicationContext.getBean("userService", UserService.class);
        System.out.println("测试结果：" + userService.queryUserInfo());
    }

}
