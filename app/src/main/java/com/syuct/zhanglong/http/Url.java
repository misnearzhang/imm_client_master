package com.syuct.zhanglong.http;

import com.syuct.zhanglong.bean.Paremeter;

import java.util.List;

/**
 * Created by zhanglong on 4/12/15.
 */
public class Url extends UrlBase{
    String requestUrl=Url+"8080/message4U/api/";
    String finUrl="";

    public void setPackege(String packege){
        finUrl=requestUrl+packege+"/";
    }
    public void setRequestMethod(String method){
        finUrl=requestUrl+method+"?";
    }
    public String setParameter(List<Paremeter> parameter){
        StringBuilder sb=new StringBuilder(finUrl);
        for (Paremeter p:parameter) {
            //StringBuilder sb=new StringBuilder(finUrl);
            sb.append(p.getKey());
            sb.append("=");
            sb.append(p.getValue().toString());
            sb.append("&");
        }
        finUrl=sb.toString();
        return finUrl;
    }


}
