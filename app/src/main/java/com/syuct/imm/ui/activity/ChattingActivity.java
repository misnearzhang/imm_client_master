package com.syuct.imm.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.gson.Gson;
import com.syuct.imm.adapter.ChatMsgViewAdapter;
import com.syuct.imm.bean.ChatMsgEntity;
import com.syuct.imm.core.io.CacheToolkit;
import com.syuct.imm.core.io.PushManager;
import com.syuct.imm.core.protocol.MessageEnum;
import com.syuct.imm.core.protocol.UserMessage;
import com.syuct.imm.core.protocol.protocolbuf.Protoc;
import com.syuct.imm.eventbus.DataConfig;
import com.syuct.imm.eventbus.NomalMessage;
import com.syuct.imm.ui.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ChattingActivity extends Activity implements View.OnClickListener{
    private Button mBtnSend;// 发送btn
    private ImageButton mBtnBack;// 返回btn
    private EditText mEditTextContent;
    private ListView mListView;
    private ChatMsgViewAdapter mAdapter;// 消息视图的Adapter
    private List<ChatMsgEntity> mDataArrays = new ArrayList<ChatMsgEntity>();// 消息对象数组
    private Gson gson = new Gson();
    private String to;
    String account = "";
    public ChattingActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        Bundle bundle=this.getIntent().getExtras();
        to=bundle.getString("account");
        Log.v("给用户发消息",to);
        initView();// 初始化view
        initData();// 初始化数据
        //注册eventbus
        EventBus.getDefault().register(this);

        account = CacheToolkit.getInstance(this).getString(CacheToolkit.KEY_ACCOUNT);

        mListView.setSelection(mAdapter.getCount() - 1);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(absListView.getWindowToken(), 0);
                }
            }
            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });
    }

    /**
     * 消息处理器
     * @param message
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(NomalMessage message){
        if (message.wht == DataConfig.SendMessage_data) {
            String uuid = message.getObj().getHead().getUid();
            Log.v("uuid",uuid);
            for (ChatMsgEntity entity:mDataArrays){
                if(uuid.equals(entity.getUuid())){
                    entity.setStatus(true);
                }
            }
            mAdapter.setDataList(mDataArrays);
            mAdapter.notifyDataSetChanged();
            //Toast.makeText(this, message.obj.getBody(), Toast.LENGTH_LONG).show();
        }else if(message.wht == DataConfig.ReceveMessage_data){
            String uuid = message.getObj().getHead().getUid();
            ChatMsgEntity entity1 = new ChatMsgEntity();
            UserMessage message1 = gson.fromJson(message.getObj().getBody(),UserMessage.class);
            entity1.setName(message1.getFrom());
            entity1.setDate(getDate());
            UserMessage userMessage = gson.fromJson(message.getObj().getBody(),UserMessage.class);
            entity1.setMessage(userMessage.getContent());
            entity1.setType(0);
            entity1.setUuid(uuid);
            entity1.setStatus(true);
            mDataArrays.add(entity1);
            mAdapter.setDataList(mDataArrays);
            mAdapter.notifyDataSetChanged();
            mListView.setSelection(mAdapter.getCount() - 1);
        }
    }
    public void initView() {
        mListView = (ListView) findViewById(R.id.listview);
        mBtnSend = (Button) findViewById(R.id.btn_send);
        mBtnSend.setOnClickListener(this);
        mBtnBack = (ImageButton) findViewById(R.id.topbutton_back);
        mBtnBack.setOnClickListener(this);

        mEditTextContent = (EditText)findViewById(R.id.et_sendmessage);
    }

    private String[] msgArray = new String[] { "有大吗", "有！你呢？", "我也有", "那上吧",
            "打啊！你放大啊！", "你TM咋不放大呢？留大抢人头啊？CAO！你个菜B", "2B不解释", "尼滚...",
            "今晚去网吧包夜吧？", "有毛片吗？", "种子一大堆啊~还怕没片？", "OK,搞起！！" };

    private String[] dataArray = new String[] { "2012-09-22 18:00:02",
            "2012-09-22 18:10:22", "2012-09-22 18:11:24",
            "2012-09-22 18:20:23", "2012-09-22 18:30:31",
            "2012-09-22 18:35:37", "2012-09-22 18:40:13",
            "2012-09-22 18:50:26", "2012-09-22 18:52:57",
            "2012-09-22 18:55:11", "2012-09-22 18:56:45",
            "2012-09-22 18:57:33", };
    private final static int COUNT = 12;// 初始化数组总数

    /**
     * 模拟加载消息历史，实际开发可以从数据库中读出
     */
    public void initData() {
        for (int i = 0; i < COUNT; i++) {
            ChatMsgEntity entity = new ChatMsgEntity();
            entity.setDate(dataArray[i]);
            if (i % 2 == 0) {
                entity.setName("肖B");
                entity.setType(0);// 收到的消息
            } else {
                entity.setName("必败");
                entity.setType(1);// 自己发送的消息
            }
            entity.setUuid(UUID.randomUUID().toString());
            entity.setStatus(true);
            entity.setMessage(msgArray[i]);
            mDataArrays.add(entity);
        }

        mAdapter = new ChatMsgViewAdapter(this, mDataArrays);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:// 发送按钮点击事件
                send();
                break;
            case R.id.topbutton_back:
                super.onBackPressed();
                break;
        }
    }

    /**
     * 发送消息
     */
    private void send() {
        String contString = mEditTextContent.getText().toString();
        String uuid= UUID.randomUUID().toString();
        if (contString.length() > 0) {
            if(contString.length()>400){
                ArrayList<String> strings = getSplitMessage(contString,400);
                //分割字符串
                for(String send : strings){
                    ChatMsgEntity entity1 = new ChatMsgEntity();
                    entity1.setName("必败");
                    entity1.setDate(getDate());
                    entity1.setMessage(send);
                    entity1.setType(1);
                    entity1.setUuid(uuid);
                    entity1.setStatus(false);
                    mDataArrays.add(entity1);
                    mAdapter.setDataList(mDataArrays);
                    mAdapter.notifyDataSetChanged();
                    mEditTextContent.setText("");// 清空编辑框数据
                    mListView.setSelection(mAdapter.getCount() - 1);// 发送一条消息时，ListView显示选择最后一项

                    Protoc.Message.Builder send_build = Protoc.Message.newBuilder();
                    Protoc.Head.Builder head_build  = Protoc.Head.newBuilder();
                    head_build.setTime(System.currentTimeMillis());
                    head_build.setStatus(Protoc.status.REQ);
                    head_build.setUid(uuid);
                    head_build.setType(Protoc.type.USER);
                    UserMessage message = new UserMessage();
                    message.setContent(send);
                    message.setFrom(account);
                    message.setSign(null);
                    message.setTo(to);
                    message.setType(MessageEnum.type.USER.getCode());
                    send_build.setHead(head_build);
                    send_build.setBody(gson.toJson(message));
                    Log.v("发送的消息",send_build.build().toString());
                    PushManager.sendMessage(this,send_build.build());
                }
            }else{
                ChatMsgEntity entity1 = new ChatMsgEntity();
                entity1.setName("必败");
                entity1.setDate(getDate());
                entity1.setMessage(contString);
                entity1.setType(1);
                entity1.setUuid(uuid);
                entity1.setStatus(false);
                mDataArrays.add(entity1);
                mAdapter.setDataList(mDataArrays);
                mAdapter.notifyDataSetChanged();
                mEditTextContent.setText("");// 清空编辑框数据
                mListView.setSelection(mAdapter.getCount() - 1);// 发送一条消息时，ListView显示选择最后一项

                Protoc.Message.Builder send_build = Protoc.Message.newBuilder();
                Protoc.Head.Builder head_build  = Protoc.Head.newBuilder();
                head_build.setTime(System.currentTimeMillis());
                head_build.setStatus(Protoc.status.REQ);
                head_build.setUid(uuid);
                head_build.setType(Protoc.type.USER);
                UserMessage message = new UserMessage();
                message.setContent(contString);
                message.setFrom(account);
                message.setSign(null);
                message.setTo(to);
                message.setType(MessageEnum.type.USER.getCode());
                send_build.setHead(head_build);
                send_build.setBody(gson.toJson(message));
                Log.v("发送的消息",send_build.build().toString());
                PushManager.sendMessage(this,send_build.build());
            }
        }
    }

    /**
     * 发送消息时，获取当前事件
     *
     * @return 当前时间
     */
    private String getDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return format.format(new Date());
    }
    private static ArrayList<String> getSplitMessage(String message,int len){
        ArrayList<String> strs = new ArrayList<String>();
        int strlength = message.length();
        for(int i=0;i<strlength;){
            String msg = "";
            if(strlength-i>len){
                msg = message.substring(i,i+len);
            }else{
                msg = message.substring(i,strlength);
            }
            strs.add(msg);
            i+=len;
        }
        return strs;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销eventbus
        EventBus.getDefault().unregister(this);
    }
}
