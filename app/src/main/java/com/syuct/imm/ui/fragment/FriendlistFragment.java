package com.syuct.imm.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.SimpleAdapter;


import com.google.gson.Gson;
import com.syuct.imm.adapter.FriendlistAdapter;
import com.syuct.imm.bean.model.Friends;
import com.syuct.imm.core.io.CacheToolkit;
import com.syuct.imm.ui.R;
import com.syuct.imm.ui.activity.ChattingActivity;
import com.syuct.imm.ui.activity.FriendDetailActivity;
import com.syuct.imm.ui.activity.IndexActivity;
import com.syuct.imm.utils.ConstData;
import com.syuct.imm.utils.GlobalData;
import com.syuct.imm.utils.okhttp.OkHttpUtils;
import com.syuct.imm.utils.okhttp.builder.PostFormBuilder;
import com.syuct.imm.utils.okhttp.callback.StringCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class FriendlistFragment extends Fragment {

    private ListView friendlist;
    private Fragment sendmessagefragment;
    private View friendView;
    private FriendlistAdapter adapter;
    private Button addFriend;
    private Gson gson = new Gson();
    @Override
    public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
    }
    public static FriendlistFragment newInstance() {
        FriendlistFragment fragment = new FriendlistFragment();
        return fragment;
    }
    @Override
    public View onCreateView(
            final LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState)
    {
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
                String name = friends.getNickname();
                String friendAccount = friends.getAccount();
                intent_sendMessage.putExtra("account", friendAccount);
                intent_sendMessage.putExtra("name", name);
                startActivity(intent_sendMessage);
                //getActivity().overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_top);
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

        List<Friends> friendsList=new ArrayList<>();
        String account = CacheToolkit.getInstance(getActivity()).getString(CacheToolkit.KEY_ACCOUNT);
        Log.v("account",account);
        if(account!=null&&"1065302407".equals(account)){
            friendsList.add(new Friends("1039075891","123456",""));
        }else{
            friendsList.add(new Friends("1065302407","123456",""));
        }
        return friendsList;
    }

    private List<Map<String, Object>> setData() {
        //List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("friendAccount", "[ 11160111 ]");
        map.put("name", "迷死你的儿张");
        map.put("signature", "龙岂池中物,乘风上青天");
        map.put("img", R.drawable.headimg);

        ConstData.addList(map);

        return ConstData.getList();
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
    private class GetDataTask extends AsyncTask<Void, Void, List<Map<String,Object>>> {
        private boolean isDropDown;
        public GetDataTask(boolean isDropDown){
            this.isDropDown = isDropDown;
        }
        @Override
        protected List<Map<String,Object>> doInBackground(Void... params) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                ;
            }
            return ConstData.getList();
        }

        @Override
        protected void onPostExecute(List<Map<String,Object>> result) {

            if (isDropDown) {
                setData();
                adapter.notifyDataSetChanged();
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");

            } else {
                adapter.notifyDataSetChanged();

            }
            super.onPostExecute(ConstData.getList());
        }
    }
}
