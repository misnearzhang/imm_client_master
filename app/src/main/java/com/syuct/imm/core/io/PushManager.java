
package com.syuct.imm.core.io;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;

/**
 *  功能接口
 */
public class PushManager {

	public static String ACTION_ACTIVATE_PUSH_SERVICE = "ACTION_ACTIVATE_PUSH_SERVICE";

	public static String ACTION_CREATE_IM_CONNECTION = "ACTION_CREATE_CIM_CONNECTION";

	public static String ACTION_CLOSE_CIM_CONNECTION = "ACTION_CLOSE_CIM_CONNECTION";

	public static String ACTION_SEND_REQUEST = "ACTION_SEND_REQUEST";

	public static String ACTION_SEND_MESSAGE = "ACTION_SEND_MESSAGE";

	public static String ACTION_SEND_REPLY = "ACTION_SEND_REPLY";

	public static String ACTION_DISCONNECTION = "ACTION_DISSENDREQUEST";

	public static String ACTION_DESTORY = "ACTION_DESTORY";

	public static String KEY_SEND_BODY = "KEY_SEND_BODY";

	public static String KEY_MESSAGE_BODY = "KEY_MESSAGE_BODY";

	public static String KEY_REPLY_BODY = "KEY_REPLY_BODY";

	// 被销毁的destroy()
	public static final int STATE_DESTROYED = 0x0000DE;
	// 被销停止的 stop()
	public static final int STATE_STOPED = 0x0000EE;

	public static final int STATE_NORMAL = 0x000000;

	/**
	 * 初始化,连接服务端，在程序启动页或者 在Application里调用
	 * 
	 * @param context
	 * @param port
	 */
	public static void connect(Context context, String host, int port) {

		connect(context, host, port, false);

	}

	private static void connect(Context context, String ip, int port,
			boolean autoBind) {

		CacheToolkit.getInstance(context).putBoolean(
				CacheToolkit.KEY_CIM_DESTROYED, false);
		CacheToolkit.getInstance(context).putBoolean(
				CacheToolkit.KEY_MANUAL_STOP, false);

		CacheToolkit.getInstance(context).putString(
				CacheToolkit.KEY_CIM_SERVIER_HOST, ip);
		CacheToolkit.getInstance(context).putInt(
				CacheToolkit.KEY_CIM_SERVIER_PORT, port);

		if (!autoBind) {
			CacheToolkit.getInstance(context).remove(
					CacheToolkit.KEY_ACCOUNT);
		}
		Intent serviceIntent = new Intent(context, PushService.class);
		serviceIntent.putExtra(CacheToolkit.KEY_CIM_SERVIER_HOST, ip);
		serviceIntent.putExtra(CacheToolkit.KEY_CIM_SERVIER_PORT, port);
		serviceIntent.setAction(ACTION_CREATE_IM_CONNECTION);
		context.startService(serviceIntent);

	}

	protected static void connect(Context context) {

		boolean isManualStop = CacheToolkit.getInstance(context).getBoolean(
				CacheToolkit.KEY_MANUAL_STOP);
		boolean isManualDestory = CacheToolkit.getInstance(context)
				.getBoolean(CacheToolkit.KEY_CIM_DESTROYED);

		if (isManualStop || isManualDestory) {
			return;
		}

		String host = CacheToolkit.getInstance(context).getString(
				CacheToolkit.KEY_CIM_SERVIER_HOST);
		int port = CacheToolkit.getInstance(context).getInt(
				CacheToolkit.KEY_CIM_SERVIER_PORT);

		connect(context, host, port, true);

	}

	/**
	 * 设置一个账号登录到服务端
	 * 
	 *            用户唯一ID
	 */
	/*
	 * public static void bindAccount(Context context,String account){
	 * 
	 * 
	 * boolean isManualDestory =
	 * CacheToolkit.getInstance(context).getBoolean(
	 * CacheToolkit.KEY_CIM_DESTROYED); if(isManualDestory || account==null
	 * || account.trim().length()==0) { return ; }
	 * //sendBindRequest(context,account);
	 * 
	 * }
	 */

	public static void sendBindRequest(Context context, String username,
			String password, String currentAddress, String currtenPoint,
			String nickname, String headurl, String sex, String declaration,
			String area,String platform) {

		CacheToolkit.getInstance(context).putBoolean(
				CacheToolkit.KEY_MANUAL_STOP, false);
		CacheToolkit.getInstance(context).putString(
				CacheToolkit.KEY_ACCOUNT, username);
		CacheToolkit.getInstance(context).putString(
				CacheToolkit.KEY_PASSWORD, password);
		CacheToolkit.getInstance(context).putString(
				CacheToolkit.KEY_ADDRESS, currentAddress);
		CacheToolkit.getInstance(context).putString(
				CacheToolkit.KEY_POINT, currtenPoint);
		CacheToolkit.getInstance(context).putString(
				CacheToolkit.KEY_NICKNAME, nickname);
		CacheToolkit.getInstance(context).putString(
				CacheToolkit.KEY_HEAD_URL, headurl);
		CacheToolkit.getInstance(context).putString(CacheToolkit.KEY_SEX,
				sex);
		CacheToolkit.getInstance(context).putString(
				CacheToolkit.KEY_DECLARATION, declaration);
		CacheToolkit.getInstance(context).putString(
				CacheToolkit.KEY_AREA, area);
		CacheToolkit.getInstance(context).putString(
				CacheToolkit.KEY_PLATFORM, platform);
		
		String imei = ((TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
		imei += context.getPackageName();
		/*SentBody.Builder sentBody = SentBody.newBuilder();
		sentBody.setKey(Constant.RequestKey.CLIENT_LOGIN);
		sentBody.addData(username);
		sentBody.addData(password);
		sentBody.addData(currentAddress == null ? "" : currentAddress);
		sentBody.addData(currtenPoint == null ? "" : currtenPoint);
		sentBody.addData("1001");
		sentBody.addData(android.os.Build.MODEL);
		sentBody.addData(nickname == null ? "" : nickname);
		sentBody.addData(headurl == null ? "" : headurl);
		sentBody.addData(imei == null ? "" : imei);
		sentBody.addData(sex == null ? "" : sex);
		sentBody.addData(declaration == null ? "" : declaration);
		sentBody.addData(area == null ? "" : area);
		sentBody.addData(platform);
		sentBody.setTimestamp(System.currentTimeMillis());*/
		sendRequest(context, "");
	}

	protected static boolean autoBindAccount(Context context) {

		String account = CacheToolkit.getInstance(context).getString(
				CacheToolkit.KEY_ACCOUNT);
		String password = CacheToolkit.getInstance(context).getString(
				CacheToolkit.KEY_PASSWORD);
		String address = CacheToolkit.getInstance(context).getString(
				CacheToolkit.KEY_ADDRESS);
		String point = CacheToolkit.getInstance(context).getString(
				CacheToolkit.KEY_POINT);
		String nickname = CacheToolkit.getInstance(context).getString(
				CacheToolkit.KEY_NICKNAME);
		String headurl = CacheToolkit.getInstance(context).getString(
				CacheToolkit.KEY_HEAD_URL);
		String sex = CacheToolkit.getInstance(context).getString(
				CacheToolkit.KEY_SEX);
		String declaration = CacheToolkit.getInstance(context).getString(
				CacheToolkit.KEY_DECLARATION);

		String area = CacheToolkit.getInstance(context).getString(
				CacheToolkit.KEY_AREA);
		
		String platform = CacheToolkit.getInstance(context).getString(
				CacheToolkit.KEY_PLATFORM);
		boolean isManualDestory = CacheToolkit.getInstance(context)
				.getBoolean(CacheToolkit.KEY_CIM_DESTROYED);
		boolean isManualStoped = CacheToolkit.getInstance(context)
				.getBoolean(CacheToolkit.KEY_MANUAL_STOP);
		if (isManualStoped || account == null || account.trim().length() == 0
				|| isManualDestory) {
			return false;
		}

		sendBindRequest(context, account, password, address, point, nickname,
				headurl, sex, declaration, area,platform);

		return true;
	}

	/**
	 * 发送一个CIM请求
	 * 
	 * @param context
	 * @body
	 */
	public static void sendRequest(Context context, String body) {

		boolean isManualStop = CacheToolkit.getInstance(context).getBoolean(
				CacheToolkit.KEY_MANUAL_STOP);
		boolean isManualDestory = CacheToolkit.getInstance(context)
				.getBoolean(CacheToolkit.KEY_CIM_DESTROYED);

		if (isManualStop || isManualDestory) {
			return;
		}
		Intent serviceIntent = new Intent(context, PushService.class);
		serviceIntent.putExtra(KEY_SEND_BODY, body.getBytes());
		serviceIntent.setAction(ACTION_SEND_REQUEST);
		context.startService(serviceIntent);

	}

	/**
	 * 发送一个消息
	 * 
	 * @param context
	 * @param message
	 */
	public static void sendMessage(Context context, String message) {
		boolean isManualStop = CacheToolkit.getInstance(context).getBoolean(
				CacheToolkit.KEY_MANUAL_STOP);
		boolean isManualDestory = CacheToolkit.getInstance(context)
				.getBoolean(CacheToolkit.KEY_CIM_DESTROYED);
		if (isManualStop || isManualDestory) {
			return;
		}

		Intent serviceIntent = new Intent(context, PushService.class);
		serviceIntent.putExtra(KEY_MESSAGE_BODY, message.getBytes());
		serviceIntent.setAction(ACTION_SEND_MESSAGE);
		context.startService(serviceIntent);

	}

	/**
	 * 发送一个 应答包
	 * 
	 * @param context
	 */
	public static void sendReply(Context context, String replyBody) {
		boolean isManualStop = CacheToolkit.getInstance(context).getBoolean(
				CacheToolkit.KEY_MANUAL_STOP);
		boolean isManualDestory = CacheToolkit.getInstance(context)
				.getBoolean(CacheToolkit.KEY_CIM_DESTROYED);
		if (isManualStop || isManualDestory) {
			return;
		}
		Intent serviceIntent = new Intent(context, PushService.class);
		serviceIntent.putExtra(KEY_REPLY_BODY, replyBody.getBytes());
		serviceIntent.setAction(ACTION_SEND_REPLY);
		context.startService(serviceIntent);

	}

	/**
	 * 停止接受推送，将会退出当前账号登录，端口与服务端的连接
	 * 
	 * @param context
	 */
	public static void stop(Context context) {

		boolean isManualDestory = CacheToolkit.getInstance(context)
				.getBoolean(CacheToolkit.KEY_CIM_DESTROYED);
		if (isManualDestory) {
			return;
		}

		CacheToolkit.getInstance(context).putBoolean(
				CacheToolkit.KEY_MANUAL_STOP, true);

		Intent serviceIntent = new Intent(context, PushService.class);
		serviceIntent.setAction(ACTION_CLOSE_CIM_CONNECTION);
		context.startService(serviceIntent);

	}

	/**
	 * 完全销毁CIM，一般用于完全退出程序，调用resume将不能恢复
	 * 
	 * @param context
	 */
	public static void destroy(Context context) {

		CacheToolkit.getInstance(context).putBoolean(
				CacheToolkit.KEY_CIM_DESTROYED, true);
		CacheToolkit.getInstance(context)
				.remove(CacheToolkit.KEY_ACCOUNT);

		Intent serviceIntent = new Intent(context, PushService.class);
		serviceIntent.setAction(ACTION_DESTORY);
		context.startService(serviceIntent);

	}

	/**
	 * 重新恢复接收推送，重新连接服务端，并登录当前账号如果aotuBind == true
	 * 
	 * @param context
	 */
	public static void resume(Context context) {

		boolean isManualDestory = CacheToolkit.getInstance(context)
				.getBoolean(CacheToolkit.KEY_CIM_DESTROYED);
		if (isManualDestory) {
			return;
		}

		autoBindAccount(context);
	}

	public static boolean isConnected(Context context) {
		return CacheToolkit.getInstance(context).getBoolean(
				CacheToolkit.KEY_CIM_CONNECTION_STATE);
	}

	public static int getState(Context context) {
		boolean isManualDestory = CacheToolkit.getInstance(context)
				.getBoolean(CacheToolkit.KEY_CIM_DESTROYED);
		if (isManualDestory) {
			return STATE_DESTROYED;
		}

		boolean isManualStop = CacheToolkit.getInstance(context).getBoolean(
				CacheToolkit.KEY_MANUAL_STOP);
		if (isManualStop) {
			return STATE_STOPED;
		}

		return STATE_NORMAL;
	}

	private static String getVersionName(Context context) {
		String versionName = null;
		try {
			PackageInfo mPackageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			versionName = mPackageInfo.versionName;
		} catch (NameNotFoundException e) {
		}
		return versionName;
	}

}