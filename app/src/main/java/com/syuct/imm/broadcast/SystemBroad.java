package com.syuct.imm.broadcast;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.NetworkInfo;
import android.util.Log;

import com.syuct.imm.core.io.EventBroadcastReceiver;
import com.syuct.imm.core.io.PushManager;
import com.syuct.imm.core.protocol.Message;
import com.syuct.imm.ui.R;
import com.syuct.imm.utils.MessageGenerators;

import static android.media.SoundPool.*;

/**
 * Created by zhanglong on 2017/3/19.
 */

public class SystemBroad extends EventBroadcastReceiver {
    @Override
    public void onMessageReceived(Message message) {
        NotificationManager manger = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification.Builder(context)
                .setContentTitle("新消息")
                .setContentText(message.getBody()).setSmallIcon(R.drawable.ic_launcher).build();
        notification.defaults=Notification.DEFAULT_SOUND;
        manger.notify(1, notification);
        Log.v("收到消息",message.toString());
        //String response= MessageGenerators.getResponse(null);
        //PushManager.sendReply(super.context,response);
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
