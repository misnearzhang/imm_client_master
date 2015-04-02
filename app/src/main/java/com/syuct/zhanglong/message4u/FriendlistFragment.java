package com.syuct.zhanglong.message4u;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FriendlistFragment extends Fragment {

    private ListView friendlist;
    private View friendView;

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

        SimpleAdapter adapter = new SimpleAdapter(getActivity().getApplicationContext(), getData(), R.layout.friend_content_layout,
                new String[]{"name", "status", "img"},
                new int[]{R.id.friend_name, R.id.friend_status, R.id.friend_headimg});

        friendlist = (ListView) friendView.findViewById(R.id.friendlist);
        friendlist.setAdapter(adapter);
        friendlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast t = Toast.makeText(getActivity().getApplicationContext(), "点击" + position + " 名字叫" + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT);
                t.show();
            }
        });
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "张龙");
        map.put("status", "在线");
        map.put("img", R.drawable.headimg);
        list.add(map);
        map.put("name", "张龙");
        map.put("status", "在线");
        map.put("img", R.drawable.headimg);
        list.add(map);
        map.put("name", "张龙");
        map.put("status", "在线");
        map.put("img", R.drawable.headimg);
        list.add(map);
        map.put("name", "张龙");
        map.put("status", "在线");
        map.put("img", R.drawable.headimg);
        list.add(map);
        map.put("name", "张龙");
        map.put("status", "在线");
        map.put("img", R.drawable.headimg);
        list.add(map);
        map.put("name", "张龙");
        map.put("status", "在线");
        map.put("img", R.drawable.headimg);
        list.add(map);
        map.put("name", "张龙");
        map.put("status", "在线");
        map.put("img", R.drawable.headimg);
        list.add(map);
        map.put("name", "张龙");
        map.put("status", "在线");
        map.put("img", R.drawable.headimg);
        list.add(map);
        map.put("name", "张龙");
        map.put("status", "在线");
        map.put("img", R.drawable.headimg);
        list.add(map);
        map.put("name", "张龙");
        map.put("status", "在线");
        map.put("img", R.drawable.headimg);
        list.add(map);
        map.put("name", "张龙");
        map.put("status", "在线");
        map.put("img", R.drawable.headimg);
        list.add(map);
        map.put("name", "张龙");
        map.put("status", "在线");
        map.put("img", R.drawable.headimg);
        list.add(map);
        map.put("name", "张龙");
        map.put("status", "在线");
        map.put("img", R.drawable.headimg);
        list.add(map);
        map.put("name", "张龙");
        map.put("status", "在线");
        map.put("img", R.drawable.headimg);
        list.add(map);
        map.put("name", "张龙");
        map.put("status", "在线");
        map.put("img", R.drawable.headimg);
        list.add(map);
        map.put("name", "张龙");
        map.put("status", "在线");
        map.put("img", R.drawable.headimg);
        list.add(map);
        map.put("name", "张龙");
        map.put("status", "在线");
        map.put("img", R.drawable.headimg);
        list.add(map);
        map.put("name", "张龙");
        map.put("status", "在线");
        map.put("img", R.drawable.headimg);
        list.add(map);
        map.put("name", "张龙");
        map.put("status", "在线");
        map.put("img", R.drawable.headimg);
        list.add(map);
        map.put("name", "张龙");
        map.put("status", "在线");
        map.put("img", R.drawable.headimg);
        list.add(map);
        map.put("name", "张龙");
        map.put("status", "在线");
        map.put("img", R.drawable.headimg);
        list.add(map);
        map.put("name", "张龙");
        map.put("status", "在线");
        map.put("img", R.drawable.headimg);
        list.add(map);
        map.put("name", "张龙");
        map.put("status", "在线");
        map.put("img", R.drawable.headimg);
        list.add(map);
        map.put("name", "张龙");
        map.put("status", "在线");
        map.put("img", R.drawable.headimg);
        list.add(map);
        map.put("name", "张龙");
        map.put("status", "在线");
        map.put("img", R.drawable.headimg);
        list.add(map);
        map.put("name", "张龙");
        map.put("status", "在线");
        map.put("img", R.drawable.headimg);
        list.add(map);
        map.put("name", "张龙");
        map.put("status", "在线");
        map.put("img", R.drawable.headimg);
        list.add(map);
        map.put("name", "张龙");
        map.put("status", "在线");
        map.put("img", R.drawable.headimg);
        list.add(map);
        map.put("name", "张龙");
        map.put("status", "在线");
        map.put("img", R.drawable.headimg);
        list.add(map);
        map.put("name", "张龙");
        map.put("status", "在线");
        map.put("img", R.drawable.headimg);
        list.add(map);
        map.put("name", "张龙");
        map.put("status", "在线");
        map.put("img", R.drawable.headimg);
        list.add(map);
        map.put("name", "张龙");
        map.put("status", "在线");
        map.put("img", R.drawable.headimg);
        list.add(map);
        return list;
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
}
