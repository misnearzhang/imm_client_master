package com.syuct.zhanglong.http;

import android.util.Log;

import com.syuct.zhanglong.bean.Paremeter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;


/**
 * Created by xcnana on 2015/1/10.
 */
public class HttpUtils {
    public static final String user="user";
    public static final String system="system";
    public static final String message="message";
    String result="";

    public String Login(Url url,HttpParams hb){
        HttpClient httpClient= new DefaultHttpClient();
        HttpGet httpGet=new HttpGet(url.finUrl);
        try{
            HttpResponse response=httpClient.execute(httpGet);
            if(response.getStatusLine().equals("200")){
                HttpEntity entity=response.getEntity();
                result=EntityUtils.toString(entity,"utf-8");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            httpClient.getConnectionManager().closeExpiredConnections();
        }
        return result;
    }

    }
