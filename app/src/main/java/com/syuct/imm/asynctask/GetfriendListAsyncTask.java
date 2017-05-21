package com.syuct.imm.asynctask;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.syuct.imm.bean.Login;
import com.syuct.imm.core.io.CacheToolkit;
import com.syuct.imm.ui.activity.IndexActivity;
import com.syuct.imm.utils.GlobalData;
import com.syuct.imm.utils.okhttp.OkHttpUtils;
import com.syuct.imm.utils.okhttp.builder.PostFormBuilder;
import com.syuct.imm.utils.okhttp.callback.StringCallback;

import java.util.List;
import java.util.Map;

import okhttp3.Call;


/**
 * Created by zhanglong on 4/6/15.
 */
public class GetfriendListAsyncTask extends AsyncTask<Void, Void, Void> {
    public GetfriendListAsyncTask(Context context) {
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
    protected Void doInBackground(Void... params) {
        //return http.Post("http://localhost:8080/message4U/api/user/login", "login", params[0]);
        PostFormBuilder post = OkHttpUtils.post();
        post.url("http://45.32.10.203:8080/imm/getFriendsList.htm");
        String account=CacheToolkit.getInstance(context).getString(CacheToolkit.KEY_ACCOUNT);
        post.addParams("account", account);
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
                    //{"data":[{"account":"1065302407","nickname":"1039075891","sex":"123456"},{"account":"1065302407","nickname":"123456","sex":"123456"}],"status":"200","desc":"OK"}
                    List<Map> data=(List<Map>) map.get("data");

                    }else{
                        GlobalData.showToast(context,"账号或者密码错误");
                    }
            }
        });
        return null;
    }
}
