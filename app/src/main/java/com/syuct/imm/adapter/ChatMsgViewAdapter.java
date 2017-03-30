package com.syuct.imm.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.syuct.imm.bean.ChatMsgEntity;
import com.syuct.imm.ui.R;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 消息ListView的Adapter
 * 
 * @author way
 */
public class ChatMsgViewAdapter extends BaseAdapter {

	public interface IMsgViewType {
		int IMVT_COM_MSG = 0;// 收到对方的消息
		int IMVT_TO_MSG = 1;// 自己发送出去的消息
	}

	private static final int ITEMCOUNT = 2;// 消息类型的总数
	private List<ChatMsgEntity> coll;// 消息对象数组
	private LayoutInflater mInflater;
	private  Context context;

	public void setDataList(List<ChatMsgEntity> data){
		this.coll=data;
	}
	public ChatMsgViewAdapter(Context context, List<ChatMsgEntity> coll) {
		this.coll = coll;
		this.context =context;
		mInflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return coll.size();
	}

	public Object getItem(int position) {
		return coll.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	/**
	 * 得到Item的类型，是对方发过来的消息，还是自己发送出去的
	 */
	public int getItemViewType(int position) {
		ChatMsgEntity entity = coll.get(position);

		if (entity.getType() == 0) {//收到的消息
			return IMsgViewType.IMVT_COM_MSG;
		} else {//自己发送的消息
			return IMsgViewType.IMVT_TO_MSG;
		}
	}

	/**
	 * Item类型的总数
	 */
	public int getViewTypeCount() {
		return ITEMCOUNT;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		ChatMsgEntity entity = coll.get(position);
		int isComMsg = entity.getType();

		ViewHolder viewHolder = null;
		if (convertView == null) {
			switch (isComMsg){
				case 0:
					convertView = mInflater.inflate(
							R.layout.chatting_item_msg_text_left, null);
					break;
				case 1:
					convertView = mInflater.inflate(
							R.layout.chatting_item_msg_text_right, null);
					break;
				default:
			}
			viewHolder = new ViewHolder();
			viewHolder.tvSendTime = (TextView) convertView
					.findViewById(R.id.tv_sendtime);
			viewHolder.tvUserName = (TextView) convertView
					.findViewById(R.id.tv_username);
			viewHolder.tvContent = (TextView) convertView
					.findViewById(R.id.tv_chatcontent);
			viewHolder.status = (ProgressBar)convertView.findViewById(R.id.pb_sending);
			viewHolder.isComMsg = isComMsg;

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		int screenWidth = dm.widthPixels;//屏幕宽度
		viewHolder.tvContent.setMaxWidth(screenWidth*3/4);
		viewHolder.tvSendTime.setText(entity.getDate());
		viewHolder.tvUserName.setText(entity.getName());
		viewHolder.tvContent.setText(entity.getMessage());
		if(entity.isStatus()){
			viewHolder.status.setVisibility(View.INVISIBLE);
		}else{
			viewHolder.status.setVisibility(View.VISIBLE);
		}

		return convertView;
	}

	public View getView(String uuid){

		return null;
	}

	static class ViewHolder {
		public TextView tvSendTime;
		public TextView tvUserName;
		public TextView tvContent;
		public Integer isComMsg;
		public ProgressBar status;
	}

}
