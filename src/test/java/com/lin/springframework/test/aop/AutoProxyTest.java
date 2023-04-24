package com.lin.springframework.test.aop;

import com.lin.springframework.context.support.ClassPathXmlApplicationContext;
import com.lin.springframework.test.service.WorldService;
import org.junit.Test;

/**
 * @Author linjiayi5
 * @Date 2023/4/24 16:51:37
 */
public class AutoProxyTest {


    @Test
    public void testAutoProxy() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:auto-proxy.xml");

        //获取代理对象
        WorldService worldService = applicationContext.getBean("worldService", WorldService.class);
        worldService.explode();
    }

    @Test
    public void testPopulateProxyBeanWithPropertyValues() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:populate-proxy-bean-with-property-values.xml");

        //获取代理对象
        WorldService worldService = applicationContext.getBean("worldService", WorldService.class);
        worldService.explode();
    }

}
