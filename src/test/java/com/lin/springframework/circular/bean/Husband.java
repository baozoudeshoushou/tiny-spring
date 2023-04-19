package com.lin.springframework.circular.bean;

import com.lin.springframework.beans.factory.annotation.Autowired;
import com.lin.springframework.stereotype.Component;

@Component
public class Husband {

    @Autowired
    private Wife wife;

    public String queryWife(){
        return "Husband.wife";
    }

    public Wife getWife() {
        return wife;
    }

    public void setWife(Wife wife) {
        this.wife = wife;
    }

}
