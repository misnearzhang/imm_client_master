
package com.syuct.imm.core.io;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.gson.Gson;
import com.syuct.imm.core.protocol.HandShakeMessage;
import com.syuct.imm.core.protocol.Header;
import com.syuct.imm.core.protocol.Message;
import com.syuct.imm.core.protocol.MessageEnum;
import com.syuct.imm.core.protocol.protocolbuf.Protoc;

import java.util.UUID;

/**
 * 功能接口
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
     * <p>
     * 用户唯一ID
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
                                       String password) {
        Gson gson = new Gson();
        CacheToolkit.getInstance(context).putBoolean(
                CacheToolkit.KEY_MANUAL_STOP, false);
        CacheToolkit.getInstance(context).putString(
                CacheToolkit.KEY_ACCOUNT, username);
        CacheToolkit.getInstance(context).putString(
                CacheToolkit.KEY_PASSWORD, password);
        String imei = ((TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        //构建握手信息
        Protoc.Message.Builder message = Protoc.Message.newBuilder();
        Protoc.Head.Builder header = Protoc.Head.newBuilder();
        HandShakeMessage handShakeMessage = new HandShakeMessage();
        handShakeMessage.setAccount(username);
        handShakeMessage.setPassword(password);
        header.setUid(UUID.randomUUID().toString());
        header.setStatus(Protoc.status.REQ);
        header.setType(Protoc.type.HANDSHAKE);
        header.setTime(System.currentTimeMillis());
        message.setHead(header);
        message.setBody(gson.toJson(handShakeMessage));
        sendMessage(context, message.build());
    }

    protected static boolean autoBindAccount(Context context) {

        String account = CacheToolkit.getInstance(context).getString(
                CacheToolkit.KEY_ACCOUNT);
        String password = CacheToolkit.getInstance(context).getString(
                CacheToolkit.KEY_PASSWORD);
        boolean isManualDestory = CacheToolkit.getInstance(context)
                .getBoolean(CacheToolkit.KEY_CIM_DESTROYED);
        boolean isManualStoped = CacheToolkit.getInstance(context)
                .getBoolean(CacheToolkit.KEY_MANUAL_STOP);
        if (isManualStoped || account == null || account.trim().length() == 0
                || isManualDestory) {
            return false;
        }
        sendBindRequest(context, account, password);
        return true;
    }

    /**
     * 发送一个消息
     *
     * @param context
     * @param message
     */
    public static void sendMessage(Context context, Protoc.Message message) {
		/*boolean isManualStop = CacheToolkit.getInstance(context).getBoolean(
				CacheToolkit.KEY_MANUAL_STOP);
		boolean isManualDestory = CacheToolkit.getInstance(context)
				.getBoolean(CacheToolkit.KEY_CIM_DESTROYED);
		if (isManualStop || isManualDestory) {
			return;
		}*/
        Intent serviceIntent = new Intent(context, PushService.class);
        serviceIntent.putExtra(KEY_MESSAGE_BODY, message.toByteArray());
        serviceIntent.setAction(ACTION_SEND_MESSAGE);
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