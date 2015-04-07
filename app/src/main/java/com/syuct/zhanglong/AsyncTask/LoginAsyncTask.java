package com.syuct.zhanglong.AsyncTask;

import android.os.AsyncTask;

import cn.trinea.android.common.entity.HttpResponse;
import cn.trinea.android.common.util.HttpUtils;

/**
 * Created by zhanglong on 4/6/15.
 */
public class LoginAsyncTask extends AsyncTask<String,Void,String>{

    @Override
    protected String doInBackground(String... params) {
        HttpResponse response=HttpUtils.httpGet(params.toString());

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
