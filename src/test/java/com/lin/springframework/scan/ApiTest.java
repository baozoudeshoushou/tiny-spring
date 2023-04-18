package com.lin.springframework.scan;

import com.lin.springframework.context.support.ClassPathXmlApplicationContext;
import com.lin.springframework.scan.bean.IUserService;
import org.junit.Test;

/**
 * @Author linjiayi5
 * @Date 2023/4/17 18:00:55
 */
public class ApiTest {

    @Test
    public void test_property() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:scan/spring-property.xml");
        IUserService userService = applicationContext.getBean("userService", IUserService.class);
        System.out.println("测试结果：" + userService);
    }

    @Test
    public void test_scan() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:scan/spring-scan.xml");
        IUserService userService = applicationContext.getBean("userService", IUserService.class);
        System.out.println("测试结果：" + userService.queryUserInfo());
    }

}
