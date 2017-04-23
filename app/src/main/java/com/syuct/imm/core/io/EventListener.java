package com.syuct.imm.core.io;


import android.net.NetworkInfo;

import com.syuct.imm.core.protocol.protocolbuf.Protoc;


/**
 * 主要事件接口
 */
public interface EventListener
{


    /**
     * 当收到服务端推送过来的消息时调用
     * @param message
     */
    public abstract void onMessageReceived(Protoc.Message message);

    /**
     * 当调用CIMPushManager.sendRequest()向服务端发送请求，获得相应时调用
     * @param replybody
     */
    public abstract void onReplyReceived(Protoc.Message replybody);

    /**
     * 当手机网络发生变化时调用
     * @param networkinfo
     */
    public abstract void onNetworkChanged(NetworkInfo networkinfo);
    
    
    /**
     * 当连接服务器成功时回调
     * @param hasAutoBind  : true 已经自动绑定账号到服务器了，不需要再手动调用bindAccount
     */
    public abstract void onConnectionSuccessed(boolean hasAutoBind);
    
    /**
     * 当断开服务器连接的时候回调
     */
    public abstract void onConnectionClosed();
    
    /**
     * 当服务器连接失败的时候回调
     * 
     */
    public abstract void onConnectionFailed(Exception e);
    
	/**
	 * 消息发送失败时
	 */
	public abstract void onMessageFailed(Protoc.Message message);
	
	/**
	 * 请求发送失败时
	 */
	public abstract void onSentFailed(Protoc.Message body);
}

