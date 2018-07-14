package com.syuct.imm.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import com.google.gson.Gson;
import com.syuct.imm.adapter.FriendListAdapter;
import com.syuct.imm.db.entity.Friends;
import com.syuct.imm.ui.R;
import com.syuct.imm.ui.activity.ChattingActivity;
import com.syuct.imm.utils.ConstData;
import com.syuct.imm.utils.GlobalData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class FriendListFragment extends Fragment {
    private ListView friendlist;
    private Fragment sendmessagefragment;
    private View friendView;
    private FriendListAdapter adapter;
    private Button addFriend;
    private Gson gson = new Gson();
    //private static FriendsDao friendsDao = ApplicationInit.getInstances().getDaoSession().getFriendsDao();
    @Override
    public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
    }
    public static FriendListFragment newInstance() {
        //friendsDao.insert(new Friends((long) 1002, "1039075891", "xcnana", new Date(),new Date()));
        //friendsDao.insert(new Friends((long) 1003, "123456", "zhanglong", new Date(),new Date()));
        FriendListFragment fragment = new FriendListFragment();
        return fragment;
    }
    @Override
    public View onCreateView(
            final LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState){
        friendView = inflater.inflate(R.layout.friendlist_fragment, null);
        return friendView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initAdapter();
    }

    private void initAdapter() {
        adapter = new FriendListAdapter(getList(),getActivity());
        friendlist = friendView.findViewById(R.id.friendlist);
        friendlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view,
                                    int position,
                                    long id) {
                Intent intent_sendMessage = new Intent(getActivity(), ChattingActivity.class);
                Friends friends= (Friends) parent.getItemAtPosition(position);
                String name = friends.getFriendName();
                String friendAccount = friends.getFriendAccount();
                intent_sendMessage.putExtra("account", friendAccount);
                intent_sendMessage.putExtra("name", name);
                startActivity(intent_sendMessage);
            }
        });
        friendlist.setScrollingCacheEnabled(true);
        friendlist.setAdapter(adapter);
        friendlist.setOnScrollListener(new AbsListView.OnScrollListener() {
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
        registerForContextMenu(friendlist);
    }

    private List<Friends> getList() {
        List list  = new ArrayList();
        Friends friends = new Friends();
        friends.setAddTime(new Date());
        friends.setFriendName("zhanglong");
        friends.setFriendAccount("10");
        friends.setUpdateTime(new Date());
        list.add(friends);

        return list;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.friendlistmenu,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo=
                (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.delete:
                ConstData.deleteList(adapterContextMenuInfo.id);
                adapter.notifyDataSetChanged();
                GlobalData.showToast(getActivity().getApplicationContext(), "删除好友成功");
                break;
            case R.id.detail:
                Map<String,Object> map=ConstData.getList().get((int)adapterContextMenuInfo.id);
                String friendAccount=map.get("friendAccount").toString();//去除好友的账号

                //getActivity().overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_top);
                break;
        }
        return super.onContextItemSelected(item);
    }


    @Override
    public void onPause() {
        super.onPause();
    }
}
