package com.syuct.imm.core.protocol;

import java.io.Serializable;

/**接收消息抽象
 * Created by zhanglong on 2017/2/25.
 */
public class Message implements Serializable{
    private Header head;//Header
    private String body;//Body

    public Header getHead() {
        return head;
    }

    public void setHead(Header head) {
        this.head = head;
    }

    public String getBody() {
        return body;
    }

    public Message setBody(String body) {
        this.body = body;
        return this;
    }

    @Override
    public String toString() {
        return "Message{" +
                "head='" + head + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
