package com.syuct.zhanglong.AsyncTask;

import android.os.AsyncTask;

/**
 * Created by zhanglong on 2015/3/24.
 */
public class MyAsyncTask extends AsyncTask<String,Long,String> {

    @Override
    protected String doInBackground(String... params) {
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected void onProgressUpdate(Long... values) {
        super.onProgressUpdate(values);
    }
}
