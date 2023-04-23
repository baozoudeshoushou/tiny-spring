package com.lin.springframework.circular.bean;

import com.lin.springframework.beans.factory.annotation.Autowired;
import com.lin.springframework.beans.factory.annotation.Qualifier;
import com.lin.springframework.stereotype.Component;

@Component
public class Wife {

    @Autowired
    private Husband husband;

    @Autowired
    @Qualifier("husbandMother")
    private IMother mother; // 婆婆

    public String queryHusband() {
        return "Wife.husband、Mother.callMother：" + mother.callMother();
//        return "Wife.husband、Mother.callMother：";
    }

    public Husband getHusband() {
        return husband;
    }

    public void setHusband(Husband husband) {
        this.husband = husband;
    }

    public IMother getMother() {
        return mother;
    }

    public void setMother(IMother mother) {
        this.mother = mother;
    }

}
