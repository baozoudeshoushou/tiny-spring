package com.lin.springframework.autowire.bean;

import com.lin.springframework.beans.factory.annotation.Autowired;
import com.lin.springframework.beans.factory.annotation.Value;
import com.lin.springframework.stereotype.Component;

import java.util.Random;

/**
 * @Author linjiayi5
 * @Date 2023/4/18 15:44:44
 */
@Component
public class UserService {

    @Value("${token}")
    private String token;

    @Autowired
    private UserDao userDao;

    public String queryUserInfo() {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        return userDao.queryUserName("10001") + "，" + token;
    }

}
