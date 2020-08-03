package com.transsnet.kika.viewmodel;

import java.io.Serializable;

/**
 * Author:  zengfeng
 * Time  :  2020/7/29 16:06
 * Des   :
 */
public class User implements Serializable {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
