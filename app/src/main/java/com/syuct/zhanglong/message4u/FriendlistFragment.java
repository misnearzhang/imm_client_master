package com.syuct.zhanglong.message4u;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


import com.syuct.zhanglong.Utils.ConstData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.trinea.android.common.view.DropDownListView;

public class FriendlistFragment extends Fragment {

    private DropDownListView friendlist;
    private View friendView;
    private SimpleAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        friendView = inflater.inflate(R.layout.friendlist_fragment, null);
        return friendView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initAdapter();

    }


    private void initAdapter() {

        adapter = new SimpleAdapter(getActivity().getApplicationContext(), ConstData.getList(), R.layout.friend_content_layout,
                new String[]{"name", "status", "img"},
                new int[]{R.id.friend_name, R.id.friend_status, R.id.friend_headimg});

        friendlist = (DropDownListView) friendView.findViewById(R.id.friendlist);

        friendlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast t = Toast.makeText(getActivity().getApplicationContext(), "点击" + position + " 名字叫" + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT);
                t.show();
            }
        });
        friendlist.setDropDownStyle(true);
        friendlist.setOnDropDownListener(new DropDownListView.OnDropDownListener() {
            @Override
            public void onDropDown() {
                new GetDataTask(true).execute();
            }
        });

        friendlist.setAdapter(adapter);
    }

    private List<Map<String, Object>> setData() {
        //List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "xcnana");
        map.put("status", "在线");
        map.put("img", R.drawable.headimg);

        ConstData.addList(map);

        return ConstData.getList();
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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            initAdapter();
            //相当于Fragment的onResume
        } else {
            //相当于Fragment的onPause
        }
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

                // should call onDropDownComplete function of DropDownListView at end of drop down complete.
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
                friendlist.onDropDownComplete(getString(R.string.update_at)
                        + dateFormat.format(new Date()));
            } else {
                adapter.notifyDataSetChanged();


                friendlist.onBottomComplete();
            }

            super.onPostExecute(ConstData.getList());
        }
    }
}
