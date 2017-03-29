package com.syuct.imm.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.syuct.imm.core.constant.Constant;
import com.syuct.imm.core.io.CacheToolkit;
import com.syuct.imm.core.protocol.Header;
import com.syuct.imm.core.protocol.Message;
import com.syuct.imm.core.protocol.MessageEnum;
import com.syuct.imm.core.protocol.UserMessage;

import java.util.UUID;

/**
 * Created by zhanglong on 2017/3/19.
 */

public class MessageGenerators {
    private static Gson gson=new Gson();
    public static String generta_response(String uuid){
        return "";
    }

    public static String generat_message(Context context,String content, String to, String uuid){
        Message message=new Message();
        Header header=new Header();
        UserMessage userMessage=new UserMessage();
        userMessage.setFrom(CacheToolkit.getInstance(context).getString(CacheToolkit.KEY_ACCOUNT));
        userMessage.setTo(to);
        userMessage.setContent(content);
        userMessage.setSign("");
        userMessage.setType(MessageEnum.type.USER.getCode());
        header.setType(MessageEnum.type.USER.getCode());
        header.setStatus(MessageEnum.status.REQ.getCode());
        header.setUid(uuid);
        message.setHead(header);
        message.setBody(gson.toJson(userMessage));
        String m=gson.toJson(message);
        return m;
    }
}
