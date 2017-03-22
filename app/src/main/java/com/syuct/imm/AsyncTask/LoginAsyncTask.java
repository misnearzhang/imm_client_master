package com.syuct.imm.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.syuct.imm.utils.GlobalData;
import com.syuct.imm.bean.Login;
import com.syuct.imm.bean.model.LoginResult;




/**
 * Created by zhanglong on 4/6/15.
 */
public class LoginAsyncTask extends AsyncTask<Login, Void, String> {
    Context context = GlobalData.getLoginContext();
    ProgressDialog dialog;
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
        //return http.Post("http://localhost:8080/message4U/api/user/login", "login", params[0]);
        return "";
    }
}
