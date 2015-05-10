package com.syuct.zhanglong.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.syuct.zhanglong.Utils.GlobalData;
import com.syuct.zhanglong.bean.Login;
import com.syuct.zhanglong.bean.model.LoginResult;
import com.syuct.zhanglong.http.HttpUtil;
import com.syuct.zhanglong.message4u.IndexActivity;
import com.syuct.zhanglong.message4u.LoginActivity;
import com.syuct.zhanglong.message4u.R;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.net.HttpURLConnection;

import cn.trinea.android.common.entity.HttpResponse;


/**
 * Created by zhanglong on 4/6/15.
 */
public class LoginAsyncTask extends AsyncTask<Login, Void, String> {
    Context context = GlobalData.getLoginContext();
    ProgressDialog dialog;
    HttpUtil http = new HttpUtil();
    LoginResult result = new LoginResult();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(context);
        dialog.setTitle("正在登录");
        dialog.setMessage("请稍候...");
        dialog.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Gson gson = new Gson();
        result = gson.fromJson(s, LoginResult.class);
        if (result != null && !result.equals("")) {
            if (result.getCode() == 0) {
                dialog.dismiss();
            }
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected String doInBackground(Login... params) {
        return http.Post("http://localhost:8080/message4U/api/user/login", "login", params[0]);

    }
}
