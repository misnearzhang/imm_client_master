package com.syuct.zhanglong.http;

import com.syuct.zhanglong.bean.Login;
import com.syuct.zhanglong.bean.User;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpUtil {
    public String Get(String url) {
        String result = null;
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        HttpResponse response = null;
        try {
            response = client.execute(get);
            if (response.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(response.getEntity(), "utf-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public String Post(String url, String type, Object entity) {
        String result = "";
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        switch (type) {
            case "login":
                Login login = (Login) entity;
                params.add(new BasicNameValuePair("userAccount", login.getUserAccount()));
                params.add(new BasicNameValuePair("password", login.getPassword()));
                break;
            case "registe":
                User user = (User) entity;
                params.add(new BasicNameValuePair("userName", user.getUserName()));
                params.add(new BasicNameValuePair("password", user.getPassword()));
                params.add(new BasicNameValuePair("birthday", user.getBirthday() + ""));
                params.add(new BasicNameValuePair("phoneNumber", user.getPhoneNumber()));
        }


        try {
            post.setEntity(new UrlEncodedFormEntity(params, "utf-8"));

            HttpResponse response = client.execute(post);
            if (response.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(response.getEntity());
            } else {
                result = "{'status':'err','msg':'somethingWrong'}";
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
}
