package com.syuct.imm.utils;

import com.google.gson.Gson;
import com.syuct.imm.core.constant.Constant;
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
    public static String generat_message(String content,String to,String uuid){
        Message message=new Message();
        Header header=new Header();
        UserMessage userMessage=new UserMessage();
        userMessage.setFrom("");
        userMessage.setTo(to);
        userMessage.setContent(content);
        userMessage.setSign("");
        userMessage.setType(MessageEnum.type.USER.getCode());
        header.setType(MessageEnum.type.USER.getCode());
        header.setStatus(MessageEnum.status.REQ.getCode());
        header.setUid(uuid);
        message.setHead(header);
        message.setBody(gson.toJson(userMessage));
        return gson.toJson(message);
    }

    public static String generta_response(String uuid){
        return "";
    }
}
