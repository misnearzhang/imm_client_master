
package com.syuct.imm.core.constant;


/**
 * 常量
 */
public interface Constant {
	public static final int MessageID = 1;
	public static final int SentBodyID = 2;
	public static final int ReplyBodyID = 3;
	public static final int HeartBeatID = 4;
	public static final int SystemMessageID = 5;
	public static String TopicRequestType_LoadNewestTopic = "1001";

	public static String TopicRequestType_LoadMoreTopic = "1002";
	
	public static long RECONN_INTERVAL_TIME= 30 * 1000;
	
	
	public static class PlatformCode{
		public static String PLATFORM_TNBKHD = "TNBKHD";
		public static String PLATFORM_XCSHD = "XCSHD";
	}
	
	
	
	
	public static class ReturnCode {

		public static String CODE_401_1 = "401_1";

		public static String CODE_404 = "404";// 服务器内部错误

		public static String CODE_403 = "403";// 资源不可用

		public static String CODE_405 = "405";// 访问被拒绝

		public static String CODE_200 = "200";// 成功

		public static String CODE_200_01 = "200_01"; // 成功，暂无数据

		public static String CODE_206 = "206";

		public static String CODE_500 = "500";// 服务器内部错误

		public static String CODE_500_999 = "500_999"; // 非法请求

		public static String CODE_LOGIN_500_01 = "500_01";// 注册时失败

		public static String CODE_REGISTER_500_02 = "500_02";// 注册失败

		public static String CODE_FRIEND_500_03_01 = "500_03_01"; // 查找的用户不存在

		public static String CODE_FRIEND_500_03_02 = "500_03_02";// 无法重复添加

		// public static String CODE_FRIEND_500_03_03 = "500_03_03";//
		// 获取好友请求列表数据为null 及没有数据 取消 使用CODE_200_01

		public static String CODE_FRIEND_500_03_04 = "500_03_04";// 同意添加时发现数据库没有该条添加请求

		public static String CODE_FRIEND_500_03_05 = "500_03_05";// 同意请求非当前连接用户,不统一

		public static String CODE_TOPIC_500_04_01 = "500_04_01";// 评论的主题不存在
		
	}

	public static String UTF8 = "UTF-8";

	public static byte MESSAGE_SEPARATE = '\b';

	// flex客户端 安全策略验证时会收到<policy-file- request/>\0
	public static byte FLEX_DATA_SEPARATE = '\0';

	public static int CIM_DEFAULT_MESSAGE_ORDER = 1;

	public static final String SESSION_KEY = "account";

	public static final String HEARTBEAT_KEY = "heartbeat";

	/**
	 * FLEX 客户端socket请求发的安全策略请求，需要特殊处理，返回安全验证报文
	 */
	public static final String FLEX_POLICY_REQUEST = "<policy-file-request/>";

	public static final String FLEX_POLICY_RESPONSE = "<?xml version=\"1.0\"?><cross-domain-policy><site-control permitted-cross-domain-policies=\"all\"/><allow-access-from domain=\"*\" to-ports=\"*\"/></cross-domain-policy>\0";

/*	*//**
	 * 服务端心跳请求命令 cmd_server_hb_request
	 *//*
	public static final String CMD_HEARTBEAT_REQUEST = "S_H_RQ";
	*//**
	 * 客户端心跳响应命令 cmd_client_hb_response
	 *//*
	public static final String CMD_HEARTBEAT_RESPONSE = "C_H_RS";*/

	public static class SessionStatus {

		public static int STATUS_OK = 0;

		public static int STATUS_CLOSED = 1;

	}

	public static class MessageType {

		public static final String user="user";
		public static final String system="system";
		public static final String response="response";


		// 用户之间的普通消息
		public static final String TYPE_0 = "0";
		// 该类型消息为离线消息集合
		public static final String TYPE_0_ALL = "0_ALL";

		// 系统向用户发送的普通消息
		public static final String TYPE_2 = "2";

		// 账号在其他设备绑定时，会收到该类型消息,强制下线
		public static String TYPE_999 = "999";
		// 该类型消息为添加好友消息
		public static String TYPE_998 = "998";
		// 该类型消息为离线添加好友消息集合
		public static String TYPE_998_ALL = "998_ALL";

		// 该类型消息为对方同意添加消息
		public static String TYPE_997 = "997";
		// 该类型消息为离线 同意添加消息集合
		public static String TYPE_997_ALL = "997_ALL";

		// 该类型消息为获取好友列表
		public static String TYPE_996_ALL = "996_ALL";

		// 朋友圈有更新
		public static String TYPE_995 = "995";

		// 有朋友删除了一条说说
		public static String TYPE_994 = "994";
		// 有朋友删除了一条评论
		public static String TYPE_993 = "993";
		// 有一条关于你的评论
		public static String TYPE_992 = "992";
		// 获取某个好友的说说
		public static String TYPE_991_ALL = "991_ALL";

	}

	public static class RequestKey {

		public static String CLIENT_BIND = "client_bind";
		public static String KEY_CLIENT_CIMSESSION_CLOSED = "client_cimsession_closed";

		public static String CLIENT_REGISTER = "client_register";

		public static String CLIENT_LOGIN = "client_login";
		
		public static String CLIENT_BIND_LABEL = "client_bind_label";

		public static String CLIENT_HEARTBEAT = "client_heartbeat";

		public static String CLIENT_LOGOUT = "client_logout";

		public static String CLIENT_DIY = "client_diy";

		public static String CLIENT_OFFLINE_MESSAGE = "client_get_offline_message";

		public static String CLIENT_FIND_FRIEND = "client_find_friend";

		public static String CLIENT_ADD_FRIEND = "client_add_friend";

		public static String CLIENT_ACCEPT_ADD_FRIEND = "client_accept_add_friend";

		public static String CLIENT_OFFLINE_ROSTER = "client_get_offline_roster";

		public static String CLIENT_GET_ROSTER = "client_get_roster";

		/**
		 * 发布一条说说
		 */
		public static String CLIENT_RELEASE_TOPIC = "client_release_topic";

		/**
		 * 删除一条说说
		 */
		public static String CLIENT_DELETE_TOPIC = "client_delete_topic";
		/**
		 * 发表一条评论
		 */
		public static String CLIENT_COMMENT_TOPIC = "client_comment_topic";
		/**
		 * 删除一条评论
		 */
		public static String CLIENT_DELETE_COMMENT = "client_delete_comment";
		/**
		 * 获取一个好友的朋友圈
		 */
		public static String CLIENT_GET_USER_TOPIC = "client_get_user_topic";
		/**
		 * 获取朋友圈动态
		 */
		public static String CLIENT_GET_MYFRIEND_TOPIC = "client_get_myfriend_topic";
		/**
		 * 获取推荐好友
		 */
		public static String CLIENT_GET_RANDOM_FRIEND = "client_get_random_friend";

	}

	public static class ReplyKey {

		public static String CLIENT_ADDROSTER_REPLY = "client_add_friend_reply";

		public static String CLIENT_MESSAGE_REPLY = "client_messge_reply";
		
		public static String CLIENT_HEARTBEAT = "client_heartbeat";

	}

	/**
	 * 朋友圈常量
	 * 
	 * @author Administrator
	 * 
	 */
	public static class WorldAction {
		// 转发
		public static final String ACTION_TYPE_FORWARD = "forward";
		// 发布
		public static final String ACTION_TYPE_PUBLISHER = "publisher";
	}

	public static class AllState {
		public static final String STATE_01 = "1"; // 可用的
		public static final String STATE_02 = "2"; // 废弃的
		public static final String STATE_03 = "3"; // 可用而未被使用的
		public static final String STATE_04 = "4";// 被使用的

	}

	public static class WorldContentFormat {
		// 文本
		public static final String CONTENT_FORMAT_TXT = "txt";
		// 图文
		public static final String CONTENT_FORMAT_FILE = "file";
		// 连接
		public static final String CONTENT_FORMAT_URL = "url";
	}

	public static class WorldFileType {
		// 文本
		public static final String FILE_TYPE_IMAGE = "image";
	}

	public static class WorldCommentType {
		// 文本
		public static final String COMMENT_TYPE = "1001";

		public static final String ZAN_TYPE = "1002";
	}

	public static class WorldPermission {

		public static final int PERMISSION_PUBLIC = 1;

		public static final int PERMISSION_PRIVATE = 2;

		public static final int PERMISSION_PART_LOOK = 3;

		public static final int PERMISSION_PART_N0T_LOOK = 4;
	}
	
	public static class MessageFormat {

		public static final String FORMAT_TXT = "txt";

		public static final String FORMAT_JSON = "json";

		public static final String FORMAT_JSON_LIST = "jsonList";

		public static final String FORMAT_XML = "xml";

		public static final String FORMAT_FILE = "file";

		public static final String FORMAT_PRODUCT_URL = "url";

	}

	public static class FileType {

		public static final String FILE_TYPE_IMAGE = "image";

		public static final String FILE_TYPE_VOIDE = "voide";

		public static final String FILE_TYPE_MEDIA = "media";
	}

	public static class LoginType {
		public static final String ORIGINAL_LOGIN = "1001";
		public static final String OTHER_LOGIN = "1002";
	}

}