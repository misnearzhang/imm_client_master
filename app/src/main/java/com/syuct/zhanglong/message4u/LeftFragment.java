package com.syuct.zhanglong.message4u;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.syuct.zhanglong.Utils.CircleImageDrawable;

import java.util.ArrayList;
import java.util.List;

public class LeftFragment extends Fragment {

	private View rootView;
    private ImageView headImage;
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
        bitmap=small(bitmap,0.2f);
        headImage=(ImageView)rootView.findViewById(R.id.myHead);
        headImage.setImageDrawable(new CircleImageDrawable(bitmap));
        final Intent IntentUserDetail = new Intent(getActivity(), LoginActivity.class);
        headImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(IntentUserDetail);
                //overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_fade_out);
            }
        });

        /*
        一下为左侧菜单的Adapter
         */
		ListView leftList = (ListView) rootView.findViewById(R.id.left_list);
		leftList.setAlwaysDrawnWithCacheEnabled(true);

		ArrayAdapter<String> adapter=new ArrayAdapter<String>(
				getActivity(), 
				android.R.layout.simple_list_item_1, 
				android.R.id.text1,
				initData());
	
		leftList.setAdapter(adapter);
		
		leftList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

                /*
                获取当前可见的fragment
                 */

               // Fragment f1=getFragmentManager().findFragmentByTag(mTabHost.getCurrentTabTag());
                //Fragment f1=getFragmentManager().findFragmentByTag("findFragmentByTag   ");
				Fragment f=null;
				
				switch (position) {
				case 0:
					f=new FriendlistFragment();
					break;
				case 1:
					f=new MessageFragment();
					break;
				case 2:
					f=new AssistantFragment();
					break;
				case 3:
					f=new UsercenterFragment();
					break;
				case 4:
					f=new SettingFragment();
					break;
                default:
                    //dialog();//弹出对话框待定
                    System.exit(0);

				}
				
				switchFragment(f);
			}

			
		});
	}
	
	private void switchFragment(Fragment f2) {
		//通知主界面切换Fragment
		if(f2!=null){
			if(getActivity() instanceof IndexActivity){
                IndexActivity mainActivity=(IndexActivity) getActivity();
				mainActivity.switchFragment(f2);
			}
		}
		
	}
    private static Bitmap small(Bitmap bitmap,float size) {
        Matrix matrix = new Matrix();
        matrix.postScale(size,size); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        return resizeBmp;
    }

	private List<String> initData(){
		
		List<String> list=new ArrayList<String>();
		list.add("好友列表");
		list.add("查看留言");
		list.add("生活助手");
        list.add("个人中心");
        list.add("设置");
		list.add("退出");
		
		return list;
	}
    protected void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity().getApplicationContext());
        builder.setMessage("确认退出吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                getActivity().finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }
}
