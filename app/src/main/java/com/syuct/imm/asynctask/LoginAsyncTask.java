package com.syuct.imm.asynctask;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.syuct.imm.core.io.CacheToolkit;
import com.syuct.imm.ui.activity.IndexActivity;
import com.syuct.imm.bean.Login;
import com.syuct.imm.utils.GlobalData;
import com.syuct.imm.utils.okhttp.OkHttpUtils;
import com.syuct.imm.utils.okhttp.builder.PostFormBuilder;
import com.syuct.imm.utils.okhttp.callback.Callback;
import com.syuct.imm.utils.okhttp.callback.StringCallback;

import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;


/**
 * Created by zhanglong on 4/6/15.
 */
public class LoginAsyncTask extends AsyncTask<Login, Void, Void> {
    public LoginAsyncTask(Context context) {
        this.context = context;
    }

    Context context;
    private Gson gson=new Gson();
    private boolean status=true;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void s) {
        //super.onPostExecute(s);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Void doInBackground(Login... params) {
        //return http.Post("http://localhost:8080/message4U/api/user/login", "login", params[0]);
        String account = params[0].getUserAccount();
        String password = params[0].getPassword();
        PostFormBuilder post = OkHttpUtils.post();
        post.url("http://xcnana.com:8080/imm/login.htm");
        post.addParams("account", account);
        post.addParams("password", password);
        CacheToolkit.getInstance(context).putString(CacheToolkit.KEY_ACCOUNT,account);
        CacheToolkit.getInstance(context).putString(CacheToolkit.KEY_PASSWORD,password);
        post.build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                Log.v("result",String.valueOf(response));
                Map map=gson.fromJson(String.valueOf(response), Map.class);
                String code=(String)map.get("status");
                Log.v("code",code);
                if("200".equals(code)){
                    Intent intentLogin = new Intent(context, IndexActivity.class);
                    intentLogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intentLogin);
                    }else{
                        GlobalData.showToast(context,"账号或者密码错误");
                    }
            }
        });
        return null;
    }
}
