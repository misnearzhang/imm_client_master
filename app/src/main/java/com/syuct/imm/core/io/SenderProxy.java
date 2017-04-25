package com.syuct.imm.core.io;

import com.syuct.imm.core.protocol.protocolbuf.Protoc;

import java.util.UUID;

/**
 * Created by zhanglong on 2017/4/23.
 */

public class SenderProxy{

    public static Protoc.Message factory(String message, Protoc.type _type, Protoc.status status){
        Protoc.Head.Builder headBuilder = Protoc.Head.newBuilder();
        Protoc.Message.Builder messageBuilder = Protoc.Message.newBuilder();
        switch (_type){
            case USER:
                headBuilder.setType(Protoc.type.USER);
                headBuilder.setStatus(status);
                headBuilder.setUid(UUID.randomUUID().toString());
                messageBuilder.setHead(headBuilder);
                messageBuilder.setBody(message);
                break;
            case SYSTEM:
                headBuilder.setType(Protoc.type.SYSTEM);
                headBuilder.setStatus(status);
                headBuilder.setUid(UUID.randomUUID().toString());
                messageBuilder.setHead(headBuilder);
                messageBuilder.setBody(message);
                break;
            case RESPONSE:
                headBuilder.setType(Protoc.type.RESPONSE);
                headBuilder.setStatus(status);
                headBuilder.setUid(UUID.randomUUID().toString());
                messageBuilder.setHead(headBuilder);
                messageBuilder.setBody(message);
                break;
            case PING:
                headBuilder.setType(Protoc.type.PING);
                headBuilder.setStatus(status);
                headBuilder.setUid(UUID.randomUUID().toString());
                messageBuilder.setHead(headBuilder);
                messageBuilder.setBody(message);
                break;
            case PONG:
                headBuilder.setType(Protoc.type.PONG);
                headBuilder.setStatus(status);
                headBuilder.setUid(UUID.randomUUID().toString());
                messageBuilder.setHead(headBuilder);
                messageBuilder.setBody(message);
                break;
            case HANDSHAKE:
                headBuilder.setType(Protoc.type.HANDSHAKE);
                headBuilder.setStatus(status);
                headBuilder.setUid(UUID.randomUUID().toString());
                messageBuilder.setHead(headBuilder);
                messageBuilder.setBody(message);
                break;
            default:
        }
        return messageBuilder.build();
    }

    public void sendUserMessage(){

    }
    public void sendSystemMessage(){

    }
    public void sendPing(){

    }
    public void sendPong(){

    }
    public static void getHandshake(String body){
        factory(body, Protoc.type.HANDSHAKE, Protoc.status.REQ);
    }
    public void sendResponse(){

    }
}
