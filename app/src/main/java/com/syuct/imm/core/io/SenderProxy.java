package com.syuct.imm.core.io;

import com.syuct.imm.core.protocol.protocolbuf.Protoc;

import java.util.UUID;

/**
 * Created by zhanglong on 2017/4/23.
 */

public class SenderProxy implements ISenderProxy{

    public void send(String message, Protoc.Message.type _type, Protoc.Message.status status){
        Protoc.Message.Head.Builder headBuilder = Protoc.Message.Head.newBuilder();
        Protoc.Message.Builder messageBuilder = Protoc.Message.newBuilder();
        switch (_type){
            case USER:
                headBuilder.setType(Protoc.Message.type.USER);
                headBuilder.setStatus(status);
                headBuilder.setUid(UUID.randomUUID().toString());
                messageBuilder.setHead(headBuilder);
                messageBuilder.setBody(message);
                break;
            case SYSTEM:
                headBuilder.setType(Protoc.Message.type.SYSTEM);
                headBuilder.setStatus(status);
                headBuilder.setUid(UUID.randomUUID().toString());
                messageBuilder.setHead(headBuilder);
                messageBuilder.setBody(message);
                break;
            case RESPONSE:
                headBuilder.setType(Protoc.Message.type.RESPONSE);
                headBuilder.setStatus(status);
                headBuilder.setUid(UUID.randomUUID().toString());
                messageBuilder.setHead(headBuilder);
                messageBuilder.setBody(message);
                break;
            case PING:
                headBuilder.setType(Protoc.Message.type.PING);
                headBuilder.setStatus(status);
                headBuilder.setUid(UUID.randomUUID().toString());
                messageBuilder.setHead(headBuilder);
                messageBuilder.setBody(message);
                break;
            case PONG:
                headBuilder.setType(Protoc.Message.type.PONG);
                headBuilder.setStatus(status);
                headBuilder.setUid(UUID.randomUUID().toString());
                messageBuilder.setHead(headBuilder);
                messageBuilder.setBody(message);
                break;
            case HANDSHAKE:
                headBuilder.setType(Protoc.Message.type.HANDSHAKE);
                headBuilder.setStatus(status);
                headBuilder.setUid(UUID.randomUUID().toString());
                messageBuilder.setHead(headBuilder);
                messageBuilder.setBody(message);
                break;
            default:
        }
    }

    public void sendUserMessage(){

    }
    public void sendSystemMessage(){

    }
    public void sendPing(){

    }
    public void sendPong(){

    }
    public void sendHandshake(){

    }
    public void sendResponse(){

    }
}
