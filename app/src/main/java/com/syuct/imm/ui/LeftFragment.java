package com.syuct.imm.ui;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.syuct.imm.utils.CircleImageDrawable;
import com.syuct.imm.utils.GlobalData;

import java.util.ArrayList;
import java.util.List;

public class LeftFragment extends Fragment {

    private View rootView;
    private ImageView headImage;
    private Dialog dialog;
    private Button dialogYes;
    private Button dialogNo;
    private View dialogview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);


    }

    /**
     * 相当于Activity的setContentView()
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.left_menu_layout, null);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.headimage);
        bitmap = small(bitmap, 0.2f);
        headImage = (ImageView) rootView.findViewById(R.id.myHead);
        headImage.setImageDrawable(new CircleImageDrawable(bitmap));
        final Intent IntentUserDetail = new Intent(getActivity(), UserDetailActivity.class);
        headImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent UserDetailIntent=new Intent(getActivity().getApplication(),UserDetailActivity.class);
                startActivity(IntentUserDetail);
                //getActivity().overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_fade_out);
            }
        });

        /*
        一下为左侧菜单的Adapter
         */
        ListView leftList = (ListView) rootView.findViewById(R.id.left_list);
        leftList.setAlwaysDrawnWithCacheEnabled(true);
        leftList.setBackgroundColor(Color.GRAY);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                initData());

        leftList.setAdapter(adapter);

        leftList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //Do someThing
            }
        });
    }

    private static Bitmap small(Bitmap bitmap, float size) {
        Matrix matrix = new Matrix();
        matrix.postScale(size, size); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }

    private List<String> initData() {

        List<String> list = new ArrayList<String>();
        list.add("个人中心");
        list.add("设置");
        list.add("退出");

        return list;
    }
}
