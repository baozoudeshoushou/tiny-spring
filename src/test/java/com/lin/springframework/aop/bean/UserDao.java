package com.lin.springframework.aop.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author linjiayi5
 * @Date 2023/4/7 11:11:19
 */
public class UserDao {

    private static Map<String, String> hashMap = new HashMap<>();

    static {
        hashMap.put("10001", "user1");
        hashMap.put("10002", "user2");
        hashMap.put("10003", "user3");
    }

    public void initData() {
        System.out.println("执行：init-method");
        hashMap.put("10001", "user1");
        hashMap.put("10002", "user2");
        hashMap.put("10003", "user3");
    }

    public void destroyData(){
        System.out.println("执行：destroy-method");
        hashMap.clear();
    }

    public String queryUserName(String uId) {
        return hashMap.get(uId);
    }

}

