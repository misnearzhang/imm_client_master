package com.syuct.imm.core.io;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

import com.google.protobuf.InvalidProtocolBufferException;
import com.syuct.imm.core.protocol.protocolbuf.Protoc;

/**
 * 与服务端连接服务
 * 
 */
public class PushService extends Service {

	ConnectorManager manager;
	WakeLock wakeLock;

	@Override
	public void onCreate() {
		manager = ConnectorManager.getManager(this.getApplicationContext());
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		if (intent == null) {
			return START_STICKY;
		}

		String action = intent.getAction();

		if (PushManager.ACTION_CREATE_IM_CONNECTION.equals(action)) {
			String host = CacheToolkit.getInstance(this).getString(
					CacheToolkit.KEY_CIM_SERVIER_HOST);
			int port = CacheToolkit.getInstance(this).getInt(
					CacheToolkit.KEY_CIM_SERVIER_PORT);
			manager.connect(host, port);
		}

		if (PushManager.ACTION_SEND_REQUEST.equals(action)) {
			byte[] msg =intent.getByteArrayExtra(PushManager.KEY_SEND_BODY);
			try {
				Protoc.Message message = Protoc.Message.parseFrom(msg);
				manager.sendMessage(message);
			} catch (InvalidProtocolBufferException e) {
				e.printStackTrace();
			}
		}
		if (PushManager.ACTION_SEND_MESSAGE.equals(action)) {
			byte[] msg =intent.getByteArrayExtra(PushManager.KEY_MESSAGE_BODY);
			try {
				Protoc.Message message = Protoc.Message.parseFrom(msg);
				manager.sendMessage(message);
			} catch (InvalidProtocolBufferException e) {
				e.printStackTrace();
			}
		}
		if (PushManager.ACTION_SEND_REPLY.equals(action)) {
			byte[] msg =intent.getByteArrayExtra(PushManager.KEY_REPLY_BODY);
			try {
				Protoc.Message message = Protoc.Message.parseFrom(msg);
				manager.sendMessage(message);
			} catch (InvalidProtocolBufferException e) {
				e.printStackTrace();
			}
		}

		if (PushManager.ACTION_DISCONNECTION.equals(action)) {
			manager.closeSession();
		}

		if (PushManager.ACTION_CLOSE_CIM_CONNECTION.equals(action)) {
			manager.closeSession();
		}

		if (PushManager.ACTION_DESTORY.equals(action)) {
			manager.destroy();
			this.stopSelf();
			android.os.Process.killProcess(android.os.Process.myPid());
		}

		if (PushManager.ACTION_ACTIVATE_PUSH_SERVICE.equals(action)
				&& !manager.isConnected()) {

			String host = CacheToolkit.getInstance(this).getString(
					CacheToolkit.KEY_CIM_SERVIER_HOST);
			int port = CacheToolkit.getInstance(this).getInt(
					CacheToolkit.KEY_CIM_SERVIER_PORT);
			manager.connect(host, port);
		}

		acquireWakeLock();
		return START_STICKY;
	}

	public void onDestroy() {
		super.onDestroy();
		releaseWakeLock();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	// 获取电源锁，保持该服务在屏幕熄灭时仍然获取CPU时，保持运行
	private void acquireWakeLock() {
		if (null == wakeLock) {
			PowerManager pm = (PowerManager) this
					.getSystemService(Context.POWER_SERVICE);
			wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK
					| PowerManager.ON_AFTER_RELEASE, PushService.class
					.getName());
		}
		if (!wakeLock.isHeld()) {
			this.wakeLock.acquire();
		}
	}

	// 释放设备电源锁
	private void releaseWakeLock() {
		if (null != wakeLock) {
			if (wakeLock.isHeld()) {
				this.wakeLock.release();
				wakeLock = null;
			}
		}

	}

}
