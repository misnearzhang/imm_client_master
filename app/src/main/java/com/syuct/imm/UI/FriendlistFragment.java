package com.syuct.imm.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;


import com.syuct.imm.utils.ConstData;
import com.syuct.imm.utils.GlobalData;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FriendlistFragment extends Fragment {

    private ListView friendlist;
    private View friendView;
    private SimpleAdapter adapter;
    private Button addFriend;
    @Override
    public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
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
        addFriend = (Button) friendView.findViewById(R.id.addFriend);
        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Add_Intent = new Intent(getActivity().getApplicationContext(), AddFriendActivity.class);
                startActivity(Add_Intent);
                //getActivity().overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_top);
            }
        });
        initAdapter();

    }



    private void initAdapter() {

        adapter = new SimpleAdapter(getActivity().getApplicationContext(),
                                    ConstData.getList(),
                                    R.layout.friend_content_layout,
                                    new String[]{"name","friendAccount", "signature", "img"},
                                    new int[]{R.id.friend_name,
                                              R.id.friend_account,
                                              R.id.friend_signature,
                                              R .id.friend_headimg
                                              }
                                    );

        friendlist = (ListView)friendView.findViewById(R.id.friendlist);


        friendlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view,
                                    int position,
                                    long id) {
                //Intent SendMessage2 = new Intent(getActivity().getApplicationContext());
                /*Map<String, Object> friendMap = (Map) parent.getItemAtPosition(position);
                String name = friendMap.get("name").toString();
                String friendAccount = friendMap.get("friendAccount").toString();
                SendMessage2.putExtra("friendAccount", friendAccount);
                SendMessage2.putExtra("name", name);
                startActivity(SendMessage2);*/
                //getActivity().overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_top);
            }
        });

        friendlist.setScrollingCacheEnabled(true);


        friendlist.setAdapter(adapter);

        registerForContextMenu(friendlist);
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
