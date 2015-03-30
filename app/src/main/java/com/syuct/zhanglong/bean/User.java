package com.syuct.zhanglong.bean;

import com.google.gson.Gson;

/**
 * Created by zhanglong on 2015/3/22.
 */
public class User {
    private String name;
    private String password;
    private String school;

    public User parse(String json){
        User user=new User();
        Gson gson=new Gson();
        user=gson.fromJson(json,User.class);
        return user;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
