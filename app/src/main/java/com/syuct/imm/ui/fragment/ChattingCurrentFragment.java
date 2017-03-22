package com.syuct.imm.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.syuct.imm.adapter.ChatMsgViewAdapter;
import com.syuct.imm.bean.ChatMsgEntity;
import com.syuct.imm.ui.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class ChattingCurrentFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View chattingView;
    private EditText editText_ip=null;
    private EditText editSendMsg=null;
    private ListView lvFriendList=null;
    private Button btnConnect=null;
    private Button btnSend=null;
    private ImageView imgConnectStatus=null;

    private Button mBtnSend;// 发送btn
    private Button mBtnBack;// 返回btn
    private EditText mEditTextContent;
    private ListView mListView;
    private ChatMsgViewAdapter mAdapter;// 消息视图的Adapter
    private List<ChatMsgEntity> mDataArrays = new ArrayList<ChatMsgEntity>();// 消息对象数组
    //private InputMethodManager imm= (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
    // TODO: Rename and change types of parameters

    public ChattingCurrentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ChattingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChattingCurrentFragment newInstance() {
        ChattingCurrentFragment fragment = new ChattingCurrentFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        chattingView=inflater.inflate(R.layout.sendmessage_fragment,null);


        initView();// 初始化view

        initData();// 初始化数据
        mListView.setSelection(mAdapter.getCount() - 1);

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                }
            }
            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });
        return chattingView;

    }



    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * 初始化view
     */
    public void initView() {
        mListView = (ListView) chattingView.findViewById(R.id.listview);
        mBtnSend = (Button) chattingView.findViewById(R.id.btn_send);
        mBtnSend.setOnClickListener(this);
        mEditTextContent = (EditText) chattingView.findViewById(R.id.et_sendmessage);
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
                entity.setType(1);// 收到的消息
            } else {
                entity.setName("必败");
                entity.setType(2);// 自己发送的消息
            }
            entity.setMessage(msgArray[i]);
            mDataArrays.add(entity);
        }

        mAdapter = new ChatMsgViewAdapter(getActivity(), mDataArrays);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:// 发送按钮点击事件
                send();
                break;
        }
    }

    /**
     * 发送消息
     */
    private void send() {
        String contString = mEditTextContent.getText().toString();
        if (contString.length() > 0) {
            ChatMsgEntity entity = new ChatMsgEntity();
            entity.setName("必败");
            entity.setDate(getDate());
            entity.setMessage(contString);
            entity.setType(3);
            mDataArrays.add(entity);

            ChatMsgEntity entity1 = new ChatMsgEntity();
            entity1.setName("必败");
            entity1.setDate(getDate());
            entity1.setMessage(contString);
            entity1.setType(4);

            mDataArrays.add(entity1);
           /* mAdapter = new ChatMsgViewAdapter(getActivity(), mDataArrays);
            mListView.setAdapter(mAdapter);*/
            mAdapter.setDataList(mDataArrays);
            mAdapter.notifyDataSetChanged();
            mEditTextContent.setText("");// 清空编辑框数据

            mListView.setSelection(mAdapter.getCount() - 1);// 发送一条消息时，ListView显示选择最后一项
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
}
