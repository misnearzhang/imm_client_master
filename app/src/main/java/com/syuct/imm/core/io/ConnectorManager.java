package com.syuct.imm.core.io;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.syuct.imm.core.io.exception.NetWorkDisableException;
import com.syuct.imm.core.io.exception.SessionDisableException;
import com.syuct.imm.core.io.exception.WriteToClosedSessionException;
import com.syuct.imm.core.protocol.Header;
import com.syuct.imm.core.protocol.Message;
import com.syuct.imm.core.protocol.MessageEnum;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.AttributeKey;
import io.netty.util.ReferenceCountUtil;

/**
 * 连接服务端管理，核心处理类，管理连接，以及消息处理
 */
@io.netty.channel.ChannelHandler.Sharable
public class ConnectorManager extends SimpleChannelInboundHandler<Object> {

	private final static String TAG = ConnectorManager.class.getSimpleName();
	private Channel channel;;

	Context context;

	Bootstrap bootstrap;
	EventLoopGroup loopGroup;
	static ConnectorManager manager;
	// 消息广播action
	public static final String ACTION_MESSAGE_RECEIVED = "com.oo.misnearzhang.MESSAGE_RECEIVED";

	// 发送sendbody失败广播
	public static final String ACTION_SENT_FAILED = "com.oo.misnearzhang..SENT_FAILED";

	// 发送sendbody成功广播
	public static final String ACTION_SENT_SUCCESS = "com.oo.misnearzhang.SENT_SUCCESS";

	// 发送message失败广播
	public static final String ACTION_MESSAGE_FAILED = "com.oo.misnearzhang.MESSAGE_FAILED";

	// 发送message成功广播
	public static final String ACTION_MESSAGE_SUCCESS = "com.oo.misnearzhang.MESSAGE_SUCCESS";

	// 发送RESPONSE失败广播
	public static final String ACTION_REPLY_FAILED = "com.oo.misnearzhang.RESPONSE_FAILED";

	// 发送RESPONSE成功广播
	public static final String ACTION_REPLY_SUCCESS = "com.oo.misnearzhang.RESPONSE_SUCCESS";

	// 发送系统消息广播
	public static final String ACTION_SYSTEM_RECEIVED= "com.oo.misnearzhang.SYSTEM_RECEIVED";

	// 链接意外关闭广播
	public static final String ACTION_CONNECTION_CLOSED = "com.oo.misnearzhang.CONNECTION_CLOSED";
	// 链接失败广播
	public static final String ACTION_CONNECTION_FAILED = "com.oo.misnearzhang.CONNECTION_FAILED";
	// 链接成功广播
	public static final String ACTION_CONNECTION_SUCCESS = "com.oo.misnearzhang.CONNECTION_SUCCESS";
	// 发送sendbody成功后获得replaybody回应广播
	public static final String ACTION_REPLY_RECEIVED = "com.oo.misnearzhang.REPLY_RECEIVED";
	// 网络变化广播
	public static final String ACTION_NETWORK_CHANGED = "android.net.conn.CONNECTIVITY_CHANGE";

	// 未知异常
	public static final String ACTION_UNCAUGHT_EXCEPTION = "com.oo.misnearzhang.UNCAUGHT_EXCEPTION";

	// CIM连接状态
	public static final String ACTION_CONNECTION_STATUS = "com.oo.misnearzhang.CONNECTION_STATUS";

	// 重试连接
	public final static String ACTION_CONNECTION_RECOVERY = "com.oo.misnearzhang.CONNECTION_RECOVERY";
	private ExecutorService executor;
	public static final String HEARTBEAT_PINGED = "HEARTBEAT_PINGED";
	// 连接空闲时间
	public static final int READ_IDLE_TIME = 180;// 秒
	// 心跳超时
	public static final int HEART_TIME_OUT = 180000;// 秒

	private static Gson gson=new Gson();

	private ConnectorManager(Context ctx) {
		context = ctx;
		executor = Executors.newFixedThreadPool(3);
		bootstrap = new Bootstrap();
		loopGroup = new NioEventLoopGroup();
		bootstrap.group(loopGroup);
		bootstrap.channel(NioSocketChannel.class);
		bootstrap.option(ChannelOption.TCP_NODELAY, true);
		bootstrap.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			public void initChannel(SocketChannel ch) throws Exception {

				ChannelPipeline pipeline = ch.pipeline();
				ByteBuf delimiter = Unpooled.copiedBuffer("\r\n".getBytes());
				ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
				// 设置带长度编码器
				ch.pipeline().addLast(new StringDecoder());
				ch.pipeline().addLast(new LineBasedFrameDecoder(1024*5));
				pipeline.addLast("idleStateHandler", new IdleStateHandler(
						0, 0, READ_IDLE_TIME));
				pipeline.addLast(ConnectorManager.this);

			}
		});

	}

	public synchronized static ConnectorManager getManager(Context context) {
		if (manager == null) {
			manager = new ConnectorManager(context);
		}
		return manager;

	}

	private synchronized void syncConnection(final String imServerHost,
			final int cimServerPort) {
		try {

			if (isConnected()) {
				return;
			}
			ChannelFuture channelFuture = bootstrap.connect(
					new InetSocketAddress(imServerHost, cimServerPort)).sync(); // 这里的IP和端口，根据自己情况修改
			channel = channelFuture.channel();
		} catch (Exception e) {
			Intent intent = new Intent();
			intent.setAction(ACTION_CONNECTION_FAILED);
			intent.putExtra("exception", e);
			context.sendBroadcast(intent);
			Log.e(TAG, "******************IM连接服务器失败  " + imServerHost + ":"
					+ cimServerPort);
		}

	}

	public void connect(final String imServerHost, final int imServerPort) {

		if (!netWorkAvailable(context)) {

			Intent intent = new Intent();
			intent.setAction(ACTION_CONNECTION_FAILED);
			intent.putExtra("exception", new NetWorkDisableException());
			context.sendBroadcast(intent);
			return;
		}

		Future<?> future = executor.submit(new Runnable() {
			@Override
			public void run() {
				syncConnection(imServerHost, imServerPort);
			}
		});
		/*
		 * try { if (future.get() != null) { connect(cimServerHost,
		 * cimServerPort); } } catch (Exception e) {
		 * 
		 * connect(cimServerHost, cimServerPort); e.printStackTrace(); }
		 */
	}

	/*
		发送信息 系统验证和退出等消息
	 */
	public void send(final String body) {
		final ByteBuf mesbuf=Unpooled.copiedBuffer((body+"\r\n").getBytes());
		executor.execute(new Runnable() {
			@Override
			public void run() {
				if (channel != null && channel.isActive()) {
					boolean isDone = channel.writeAndFlush(mesbuf)
							.awaitUninterruptibly(5000);
					if (!isDone) {
						Intent intent = new Intent();
						intent.setAction(ACTION_SENT_FAILED);
						intent.putExtra("exception",
								new WriteToClosedSessionException());
						intent.putExtra("system", body.getBytes());
						context.sendBroadcast(intent);
					} else {
						Intent intent = new Intent();
						intent.setAction(ACTION_SENT_SUCCESS);
						intent.putExtra("system", body.getBytes());
						context.sendBroadcast(intent);
					}
				} else {

					Intent intent = new Intent();
					intent.setAction(ACTION_SENT_FAILED);
					intent.putExtra("exception",
							new SessionDisableException());
					intent.putExtra("system", body.getBytes());
					context.sendBroadcast(intent);
				}
			}
		});
	}

	/*
		发送信息
	 */
	public void sendMessage(final String message) {
		final ByteBuf mesbuf=Unpooled.copiedBuffer((message+"\r\n").getBytes());
		executor.execute(new Runnable() {
			@Override
			public void run() {
				if (channel != null && channel.isActive()) {
					boolean isDone = channel.writeAndFlush(mesbuf)
							.awaitUninterruptibly(5000);
					if (!isDone) {
						Intent intent = new Intent();
						intent.setAction(ACTION_MESSAGE_FAILED);
						intent.putExtra("message", message.getBytes());
						intent.putExtra("exception",
								new WriteToClosedSessionException());
						context.sendBroadcast(intent);
					} else {
						Intent intent = new Intent();
						intent.setAction(ACTION_MESSAGE_SUCCESS);
						intent.putExtra("message", message.getBytes());
						context.sendBroadcast(intent);
					}
				} else {
					Intent intent = new Intent();
					intent.setAction(ACTION_MESSAGE_FAILED);
					intent.putExtra("exception",
							new SessionDisableException());
					intent.putExtra("message", message.getBytes());
					context.sendBroadcast(intent);
				}
			}
		});
	}

	/*
		发送响应
	 */
	public void sendReply(final String replyBody) {
		final ByteBuf response=Unpooled.copiedBuffer((replyBody+"\r\n").getBytes());
		executor.execute(new Runnable() {
			@Override
			public void run() {
				Log.e(TAG, "response");
				if (channel != null && channel.isActive()) {
					boolean isDone = channel.writeAndFlush(response)
							.awaitUninterruptibly(5000);
					if (!isDone) {
						Intent intent = new Intent();
						intent.setAction(ACTION_REPLY_FAILED);
						intent.putExtra("response", replyBody.getBytes());
						intent.putExtra("exception",
								new WriteToClosedSessionException());
						context.sendBroadcast(intent);
					} else {
						Intent intent = new Intent();
						intent.setAction(ACTION_REPLY_SUCCESS);
						intent.putExtra("response", replyBody.toString());
						context.sendBroadcast(intent);
					}

				} else {
					Intent intent = new Intent();
					intent.setAction(ACTION_REPLY_FAILED);
					intent.putExtra("exception",
							new SessionDisableException());
					intent.putExtra("response", replyBody.toString());
					context.sendBroadcast(intent);
				}
			}
		});
	}

	public void destroy() {
		if (manager.channel != null) {
			manager.channel.close();
		}
		loopGroup.shutdownGracefully();
		manager = null;
	}

	public boolean isConnected() {
		if (channel == null) {
			return false;
		}
		return channel.isActive();
	}

	public void closeSession() {
		if (channel != null) {
			channel.close();
		}
	}

	// 检测到连接空闲事件，发送心跳请求命令

	/**
	 * 检测到连接空闲事件，发送心跳请求命令
	 */
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
			throws Exception {
		if (evt instanceof IdleStateEvent){
			if(((IdleStateEvent) evt).isFirst()){
				System.out.println("first");
			}
		}
	}

	private void onReaderIdeled(Channel channel) {
		Long lastTime = (Long) channel.attr(
				AttributeKey.valueOf(HEARTBEAT_PINGED)).get();
		if (lastTime != null
				&& (System.currentTimeMillis() - lastTime) > HEART_TIME_OUT) {
			channel.close();
		}
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {

		ctx.channel().attr(AttributeKey.valueOf(HEARTBEAT_PINGED)).set(
				System.currentTimeMillis());

		Intent intent = new Intent();
		intent.setAction(ACTION_CONNECTION_SUCCESS);
		context.sendBroadcast(intent);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {

		Log.e(TAG, "******************close IM与服务器断开连接:"
				+ ctx.channel().localAddress());
		ctx.channel().close();
		if (channel.id().asLongText().equals(ctx.channel().id().asLongText())) {
			Intent intent = new Intent();
			intent.setAction(ACTION_CONNECTION_CLOSED);
			context.sendBroadcast(intent);

		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("错误日志：" + cause.getMessage() + "\n");
		sb.append("错误堆栈：\n");
		for (int i = 0; i < cause.getStackTrace().length; i++) {
			sb.append(cause.getStackTrace()[i] + "\n");
		}
		Log.e(TAG, sb.toString());
		Intent intent = new Intent();
		intent.setAction(ACTION_UNCAUGHT_EXCEPTION);
		intent.putExtra("exception", cause.getCause());
		context.sendBroadcast(intent);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		try {
			if (msg instanceof String) {
				Intent intent = new Intent();
				Log.e(TAG, "msg==" + msg.toString());
				String message_string= (String) msg;
				Message request=gson.fromJson(message_string,Message.class);
				String head=request.getHead();
				Header header=gson.fromJson(head,Header.class);
				String type=header.getType();
				if(MessageEnum.type.PING.getCode().equals(type)){
					//ping 不发广播  直接连接层处理
					Header header1 = new Header();
					header1.setUid(header.getUid());
					header1.setStatus("200");
					header1.setType(MessageEnum.type.PONG.getCode());
					request.setHead(gson.toJson(header1));
					String send = gson.toJson(request);
					send += "\r\n";
					ByteBuf buf = Unpooled.copiedBuffer(send.getBytes());
					ctx.channel().writeAndFlush(buf);
				}else {
					intent.setAction(ConnectorManager.ACTION_MESSAGE_RECEIVED);
					Bundle bundle = new Bundle();
					bundle.putSerializable("message", request);
					intent.putExtras(bundle);
					context.sendBroadcast(intent);
				}
			} else {
				System.out.println("接收到对象:" + msg.toString());
			}

		} finally {
			ReferenceCountUtil.release(msg);
		}

	}

	public static boolean netWorkAvailable(Context context) {
		try {
			ConnectivityManager nw = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = nw.getActiveNetworkInfo();
			return networkInfo != null;
		} catch (Exception e) {
		}

		return false;
	}

}