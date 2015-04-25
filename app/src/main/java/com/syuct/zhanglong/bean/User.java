package com.syuct.zhanglong.bean;

import com.google.gson.Gson;

/**
 * Created by zhanglong on 2015/3/22.
 */
public class User {
    private String name;
    private String password;

    public User parse(String json){
        User user=new User();
        Gson gson=new Gson();
        user=gson.fromJson(json,User.class);
        return user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
