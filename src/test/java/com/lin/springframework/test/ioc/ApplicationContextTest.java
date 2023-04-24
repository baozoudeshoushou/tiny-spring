package com.lin.springframework.test.ioc;

import com.lin.springframework.context.support.ClassPathXmlApplicationContext;
import com.lin.springframework.test.bean.Person;
import org.junit.Assert;
import org.junit.Test;

/**
 * @Author linjiayi5
 * @Date 2023/4/24 17:28:59
 */
public class ApplicationContextTest {

    @Test
    public void testAutowired() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:autowired.xml");
        Person person = applicationContext.getBean("person", Person.class);
        Assert.assertNotNull(person.getCar());
        System.out.println(person.getCar().getBrand());
    }

}
