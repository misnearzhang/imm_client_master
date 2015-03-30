package com.syuct.zhanglong.http;

/**
 * Created by xcnana on 2015/1/10.
 */
public class HttpRequest {
    public Boolean login(String userAccountOrPhoneNumber,String Password){
        if(userAccountOrPhoneNumber.equals("123")){
            return false;
        }
        return true;
    }

    }
