package com.syuct.imm.eventbus;

import com.syuct.imm.core.protocol.protocolbuf.Protoc;

/**
 * Created by zhanglong on 2017/4/26.
 */

public class NomalMessage {

    public int wht;
    public Protoc.Message obj;

    public int getWht() {
        return wht;
    }

    public void setWht(int wht) {
        this.wht = wht;
    }

    public Protoc.Message getObj() {
        return obj;
    }

    public void setObj(Protoc.Message obj) {
        this.obj = obj;
    }
}
