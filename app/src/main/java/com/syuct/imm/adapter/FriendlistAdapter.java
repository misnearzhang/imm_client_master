package com.syuct.imm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.syuct.imm.bean.model.Friends;
import com.syuct.imm.ui.R;

import java.util.List;

/**
 * Created by Misnearzhang on 2017/3/29.
 */

public class FriendlistAdapter extends BaseAdapter {
    private List<Friends> list;// 消息对象数组
    private LayoutInflater mInflater;

    public FriendlistAdapter(List<Friends> list, Context context) {
        mInflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Friends entity = list.get(position);
        FriendlistAdapter.friendsHolder viewHolder = null;
        if (convertView == null) {
            convertView=mInflater.inflate(R.layout.friendlist_item,null);
            viewHolder = new FriendlistAdapter.friendsHolder();
            viewHolder.imageView_headimg = (ImageView) convertView
                    .findViewById(R.id.iv_userhead);
            viewHolder.textView_userName = (TextView) convertView
                    .findViewById(R.id.tv_username);
            viewHolder.textView_recent_message = (TextView) convertView
                    .findViewById(R.id.tv_recent_message);
            viewHolder.imageView_status = (ImageView) convertView.findViewById(R.id.iv_status);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (FriendlistAdapter.friendsHolder) convertView.getTag();
        }
        viewHolder.textView_userName.setText(entity.getNickname());
        viewHolder.textView_recent_message.setText("");
        return convertView;
    }

    static class friendsHolder{
        ImageView imageView_headimg;
        TextView textView_userName;
        TextView textView_recent_message;
        ImageView imageView_status;
    }
}
