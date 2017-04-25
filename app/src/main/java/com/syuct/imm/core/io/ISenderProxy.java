package com.syuct.imm.core.io;

import com.syuct.imm.core.protocol.protocolbuf.Protoc;

/**
 * Created by zhanglong on 2017/4/23.
 */

public interface ISenderProxy {
    void send(String message, Protoc.type _type, Protoc.status status);
    void sendUserMessage();
    void sendSystemMessage();
    void sendPing();
    void sendHandshake();
    void sendResponse();
}
