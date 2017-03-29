package com.syuct.imm.core.io;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;

import com.syuct.imm.core.constant.Constant;
import com.syuct.imm.core.io.exception.SessionDisableException;
import com.syuct.imm.core.io.exception.WriteToClosedSessionException;
import com.syuct.imm.core.protocol.Message;
import com.syuct.imm.utils.okhttp.OkHttpUtils;

/**
 * 消息入口，所有消息都会经过这里
 */
public abstract class EventBroadcastReceiver extends BroadcastReceiver
		implements EventListener {

	public Context context;

	@Override
	public void onReceive(Context ctx, Intent it) {

		context = ctx;

		/*
		 * 操作事件广播，用于提高service存活率
		 */
		if (it.getAction().equals(Intent.ACTION_USER_PRESENT)
				|| it.getAction().equals(Intent.ACTION_POWER_CONNECTED)
				|| it.getAction().equals(Intent.ACTION_POWER_DISCONNECTED)) {
			startPushService();
		}

		/*
		 * 设备网络状态变化事件
		 */
		if (it.getAction().equals(ConnectorManager.ACTION_NETWORK_CHANGED)) {
			Log.v("v","断网");
			ConnectivityManager connectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = connectivityManager
					.getActiveNetworkInfo();
			onDevicesNetworkChanged(info);
		}

		/*
		 * cim断开服务器事件
		 */
		if (it.getAction().equals(ConnectorManager.ACTION_CONNECTION_CLOSED)) {
			onInnerConnectionClosed();
		}

		/*
		 * cim连接服务器失败事件
		 */
		if (it.getAction().equals(ConnectorManager.ACTION_CONNECTION_FAILED)) {
			onInnerConnectionFailed((Exception) it
					.getSerializableExtra("exception"));
		}

		/*
		 * im连接服务器成功事件
		 */
		if (it.getAction()
				.equals(ConnectorManager.ACTION_CONNECTION_SUCCESS)) {
			onInnerConnectionSuccessed();
		}

		/*
		 * 收到消息事件
		 */
		if (it.getAction().equals(ConnectorManager.ACTION_MESSAGE_RECEIVED)) {
				Message message=(Message)it.getSerializableExtra("message");
				onInnerMessageReceived(message);
		}
		/*
		 * 发送消息成功事件
		 */
		if (it.getAction().equals(ConnectorManager.ACTION_MESSAGE_SUCCESS)) {
			Log.i("Login", "ACTION_MESSAGE_SUCCESS发送成功");
		}
		/*
		 * 发送消息失败事件
		 */
		if (it.getAction().equals(ConnectorManager.ACTION_MESSAGE_FAILED)) {
				Message message= (Message) it.getSerializableExtra("message");
				onInnerMessageReceived(message);
		}

		/*
		 * 获取收到replybody成功事件
		 *//*
		if (it.getAction().equals(ConnectorManager.ACTION_REPLY_RECEIVED)) {

			byte[] message=it.getByteArrayExtra("replyBody");
			onInnerMessageReceived(message);
		}

		*//*
		 * 获取sendbody发送失败事件
		 *//*
		if (it.getAction().equals(ConnectorManager.ACTION_SENT_FAILED)) {
			byte[] message=it.getByteArrayExtra("sentBody");
			onInnerMessageReceived(message);
		}

		*//*
		 * 获取sendbody发送成功事件
		 *//*
		if (it.getAction().equals(ConnectorManager.ACTION_SENT_SUCCESS)) {
			byte[] message=it.getByteArrayExtra("sentBody");
			onInnerMessageReceived(message);
		}

		if (it.getAction().equals(ConnectorManager.ACTION_REPLY_FAILED)) {
			byte[] message=it.getByteArrayExtra("replyBody");
			onInnerMessageReceived(message);
		}*/

		if (it.getAction().equals(ConnectorManager.ACTION_REPLY_SUCCESS)) {
		}
		/*
		 * 获取im数据传输异常事件
		 */
		if (it.getAction()
				.equals(ConnectorManager.ACTION_UNCAUGHT_EXCEPTION)) {
			onUncaughtException((Exception) it
					.getSerializableExtra("exception"));
		}

		/*
		 * 重新连接，如果断开的话
		 */
		if (it.getAction().equals(
				ConnectorManager.ACTION_CONNECTION_RECOVERY)) {
			PushManager.connect(context);
		}
	}

	private void startPushService() {
		Intent intent = new Intent(context, PushService.class);
		intent.setAction(PushManager.ACTION_ACTIVATE_PUSH_SERVICE);
		context.startService(intent);
	}

	private void onInnerConnectionClosed() {
		CacheToolkit.getInstance(context).putBoolean(
				CacheToolkit.KEY_CIM_CONNECTION_STATE, false);
		if (ConnectorManager.netWorkAvailable(context)) {
			PushManager.connect(context);
		}

		onConnectionClosed();
	}

	Handler connectionHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message message) {
			PushManager.connect(context);
		}
	};

	private void onInnerConnectionFailed(Exception e) {

		if (ConnectorManager.netWorkAvailable(context)) {
			connectionHandler.sendEmptyMessageDelayed(0,
					Constant.RECONN_INTERVAL_TIME);
		}

		onConnectionFailed(e);
	}

	private void onInnerConnectionSuccessed() {
		CacheToolkit.getInstance(context).putBoolean(
				CacheToolkit.KEY_CIM_CONNECTION_STATE, true);

		boolean autoBind = PushManager.autoBindAccount(context);
		onConnectionSuccessed(autoBind);
	}

	private void onUncaughtException(Throwable arg0) {
	}

	private void onDevicesNetworkChanged(NetworkInfo info) {

		if (info != null) {
			PushManager.connect(context);
		}

		onNetworkChanged(info);
	}

	private void onInnerMessageReceived(Message message) {
		/*if (Constant.MessageType.TYPE_999.equals(message)) {
			CacheToolkit.getInstance(context).putBoolean(
					CacheToolkit.KEY_MANUAL_STOP, true);
		}*/

		onMessageReceived(message);
	}

	private void onSentFailed(Exception e, String body) {

		// 与服务端端开链接，重新连接
		if (e instanceof SessionDisableException
				|| e instanceof WriteToClosedSessionException) {

			PushManager.connect(context);
		} else {
			// 发送失败 重新发送
			PushManager.sendRequest(context, body);
		}

	}

	private void onMessageFailed(Exception e, String message) {
		if (e instanceof SessionDisableException
				|| e instanceof WriteToClosedSessionException) {
			PushManager.connect(context);
		} else {
			// 发送失败 重新发送
			PushManager.sendMessage(context, message);
		}

	}

	private void onReplyFailed(Exception e, String replyBody) {
		if (e instanceof SessionDisableException
				|| e instanceof WriteToClosedSessionException) {
			PushManager.connect(context);
		} else {
			// 发送失败 重新发送
			PushManager.sendReply(context, replyBody);
		}

	}

	private void onSentSucceed(String body) {
	}

	@Override
	public abstract void onMessageReceived(Message message);

	@Override
	public abstract void onReplyReceived(String body);

	public abstract void onNetworkChanged(NetworkInfo info);

	public abstract void onConnectionFailed(Exception e);

}
