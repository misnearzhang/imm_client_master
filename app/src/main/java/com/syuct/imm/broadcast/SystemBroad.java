package com.syuct.imm.broadcast;

import android.net.NetworkInfo;
import android.util.Log;

import com.syuct.imm.core.io.EventBroadcastReceiver;
import com.syuct.imm.core.io.PushManager;
import com.syuct.imm.utils.MessageGenerators;

/**
 * Created by zhanglong on 2017/3/19.
 */

public class SystemBroad extends EventBroadcastReceiver {
    @Override
    public void onMessageReceived(byte[] message) {
        String response= MessageGenerators.getResponse(null);
        PushManager.sendReply(super.context,response);
    }

    @Override
    public void onReplyReceived(String body) {
        Log.i("message",body);
    }

    @Override
    public void onNetworkChanged(NetworkInfo info) {
        Log.i("message","");
    }

    @Override
    public void onConnectionSuccessed(boolean hasAutoBind) {
        Log.i("message",Boolean.toString(hasAutoBind));
    }

    @Override
    public void onConnectionClosed() {
        Log.i("message","");
    }

    @Override
    public void onConnectionFailed(Exception e) {
        Log.i("message","");
    }

    @Override
    public void onMessageFailed(String message) {
        Log.i("message",message);
    }

    @Override
    public void onSentFailed(String body) {
        Log.i("message",body);
    }
}
