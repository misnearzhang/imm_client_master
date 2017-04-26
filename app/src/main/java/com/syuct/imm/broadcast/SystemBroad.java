package com.syuct.imm.broadcast;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.gson.Gson;
import com.syuct.imm.core.io.EventBroadcastReceiver;
import com.syuct.imm.core.io.PushManager;
import com.syuct.imm.core.protocol.Header;
import com.syuct.imm.core.protocol.Message;
import com.syuct.imm.core.protocol.MessageEnum;
import com.syuct.imm.core.protocol.protocolbuf.Protoc;
import com.syuct.imm.ui.R;
import com.syuct.imm.ui.activity.DataConfig;
import com.syuct.imm.ui.activity.NomalMessage;
import com.syuct.imm.utils.GlobalData;
import com.syuct.imm.utils.MessageGenerators;

import org.greenrobot.eventbus.EventBus;

import static android.media.SoundPool.*;

/**
 * Created by zhanglong on 2017/3/19.
 */

public class SystemBroad extends EventBroadcastReceiver {
    @Override
    public void onMessageReceived(Protoc.Message message) {
        /*NotificationManager manger = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification.Builder(context)
                .setContentTitle("新消息")
                .setContentText(string.getBody()).setSmallIcon(R.drawable.ic_launcher).build();
        notification.defaults=Notification.DEFAULT_SOUND;
        manger.notify(1, notification);*/
        Log.v("收到消息",message.toString());
        Protoc.Head head = message.getHead();
        NomalMessage push = new NomalMessage();
        push.wht = DataConfig.SendMessage_data;
        push.obj = message;
        switch (head.getType()){
            case RESPONSE:
                EventBus.getDefault().post(push);
                break;
            case SYSTEM:
                break;
            case USER:
                break;
            case HANDSHAKERESPONSE:
                break;
            default:
                Log.v("未知消息类型","no such method");
        }
        //判断消息类型
        /*string.getHead();
        if(MessageEnum.type.RESPONSE.getCode().equals(header.getType())){
            //response
            if("200".equals(header.getStatus())){
                //
                Log.v("response status","200");
                GlobalData.showToast(context,"登陆成功");
                String uid=header.getUid();
                //取到uid  发送的消息取消进度 握手信息则提示成功
            }else if("201".equals(header.getStatus())){
                Log.v("收到消息","你发送的用户暂时没有在线 等候将重新发送消息");
            }
        }else if(MessageEnum.type.USER.getCode().equals(header.getType())){
            //user
        }else if(MessageEnum.type.SYSTEM.getCode().equals(header.getType())){
            //system push
        }*/
        //String response= MessageGenerators.getResponse(null);
        //PushManager.sendReply(super.context,response);
    }

    @Override
    public void onReplyReceived(Protoc.Message body) {
        Log.i("message",body.getBody());
    }

    @Override
    public void onNetworkChanged(NetworkInfo info) {
        Log.v("网络邮编","123");
        GlobalData.showToast(context,"网络断开!!!");
    }

    @Override
    public void onConnectionSuccessed(boolean hasAutoBind) {
        //GlobalData.showToast(context,"链接成功!!!");
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
    public void onMessageFailed(Protoc.Message message) {
        Log.i("message",message.getBody());
    }

    @Override
    public void onSentFailed(Protoc.Message body) {
        Log.i("message",body.getBody());
    }
}
