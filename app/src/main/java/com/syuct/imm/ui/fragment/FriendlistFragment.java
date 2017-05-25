package com.syuct.imm.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.syuct.imm.adapter.FriendlistAdapter;
import com.syuct.imm.application.ApplicationInit;
import com.syuct.imm.core.io.CacheToolkit;
import com.syuct.imm.db.entity.Friends;
import com.syuct.imm.ui.R;
import com.syuct.imm.ui.activity.ChattingActivity;
import com.syuct.imm.ui.activity.FriendDetailActivity;
import com.syuct.imm.utils.ConstData;
import com.syuct.imm.utils.GlobalData;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class FriendlistFragment extends Fragment {
    private ListView friendlist;
    private Fragment sendmessagefragment;
    private View friendView;
    private FriendlistAdapter adapter;
    private Button addFriend;
    private Gson gson = new Gson();
    //private static FriendsDao friendsDao = ApplicationInit.getInstances().getDaoSession().getFriendsDao();
    @Override
    public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
    }
    public static FriendlistFragment newInstance() {
        //friendsDao.insert(new Friends((long) 1002, "1039075891", "xcnana", new Date(),new Date()));
        //friendsDao.insert(new Friends((long) 1003, "123456", "zhanglong", new Date(),new Date()));
        FriendlistFragment fragment = new FriendlistFragment();
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
        adapter = new FriendlistAdapter(getList(),getActivity());
        friendlist = (ListView)friendView.findViewById(R.id.friendlist);
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
        String account = CacheToolkit.getInstance(getActivity()).getString(CacheToolkit.KEY_ACCOUNT);
        Log.v("account",account);
        //return friendsDao.loadAll();
        return null;
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
                Intent friendDetailIntent = new Intent(getActivity().getApplicationContext(), FriendDetailActivity.class);
                friendDetailIntent.putExtra("friendAccount", friendAccount);
                startActivity(friendDetailIntent);
                //getActivity().overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_top);
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activity.getFragmentManager().findFragmentByTag("FriendlistFragment");

    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
