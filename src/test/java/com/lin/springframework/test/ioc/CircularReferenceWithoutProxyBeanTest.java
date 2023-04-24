package com.lin.springframework.test.ioc;

import com.lin.springframework.context.support.ClassPathXmlApplicationContext;
import com.lin.springframework.test.bean.A;
import com.lin.springframework.test.bean.B;
import org.junit.Assert;
import org.junit.Test;

/**
 * @Author linjiayi5
 * @Date 2023/4/24 17:17:58
 */
public class CircularReferenceWithoutProxyBeanTest {

    @Test
    public void testCircularReference() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:circular-reference-without-proxy-bean.xml");
        A a = applicationContext.getBean("a", A.class);
        B b = applicationContext.getBean("b", B.class);
        Assert.assertSame(a.getB(), b);
        Assert.assertSame(b.getA(), a);
    }

}
