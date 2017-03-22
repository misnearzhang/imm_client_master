package com.syuct.imm.ui;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.syuct.imm.broadcast.SystemBroad;
import com.syuct.imm.core.io.ConnectorManager;
import com.syuct.imm.utils.GlobalData;

/**
 *
 */
public class IndexActivity extends SlidingFragmentActivity {//这里继承的是SlidingFragmentActivity

    static String name;
    private TextView title;
    private SlidingMenu sm;//滑动菜单
    private Fragment leftFragment;//左侧视图
    static long back_pressed;
    private Fragment contentFragment;
    private Fragment mContent;
    private ImageButton topbutton;
    private BroadcastReceiver receiver;
    private Button reload;


    @Override
    public void onCreate(Bundle savedInstanceState) {//将protected变成public
        super.onCreate(savedInstanceState);
        receiver=new SystemBroad();
        Bundle data=getIntent().getExtras();
        name=data.getString("name");
        setContentView(R.layout.activity_index);
        reload = (Button) this.findViewById(R.id.reload);

        title=(TextView)findViewById(R.id.tv_top_center);
        title.setText(name);
        final Intent NewsDetail = new Intent(this,NewsDetailActivity.class);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(NewsDetail);
                //overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_fade_out);
            }
        });
        setBehindContentView(R.layout.menu);//设置SlidingMenu的layout

        topbutton = (ImageButton) this.findViewById(R.id.topbutton);
        topbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });
        //设置默认的Fragment
        if (savedInstanceState == null) {
            contentFragment = GlobalData.friend;
        } else {
            //取出之前保存的contentFragment
            contentFragment = this.getFragmentManager().getFragment(savedInstanceState, "contentFragment");
        }
        this.getFragmentManager()
                .beginTransaction()
                .replace(R.id.main_frame, contentFragment)
                .commit();


        sm = getSlidingMenu();
        sm.setMode(SlidingMenu.LEFT);//设置SlidingMenu可以从左右两侧都可以滑出

        //左边的菜单
//		sm.setMenu(R.layout.left_menu_layout);//设置SlidingMenu显示的内容
        sm.setShadowDrawable(R.drawable.shadow);// 设置SlidingMenu和主页面交界部分的阴影图片


        sm.setTouchModeAbove(SlidingMenu.LEFT);//设置滑出slidingmenu范围
        sm.setShadowWidthRes(R.dimen.shadow_width);// 设置阴影部分的宽度
        sm.setBehindOffsetRes(R.dimen.main_width);//设置主界面的宽度


        leftFragment = new LeftFragment();//SlidingMenu需要显示的Fragment的实例

        this.getFragmentManager()//拿到Fragment的管理器
                //通过Fragment的管理器就可以切换Fragment
                .beginTransaction()//fragment的事物管理
                .replace(R.id.menu_frame, leftFragment)//参数1：layout的id，参数2：要显示的Fragment实例
                .commit();

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //1.bundle
        //2.存放的ID
        //3.当前要保存的fragment的实例
        this.getFragmentManager().putFragment(outState,
                                              "contentFragment",
                                              contentFragment);
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            GlobalData.setUUID("2");
            super.onBackPressed();
        }
        else {
            Toast toast = Toast.makeText(IndexActivity.this,
                                       "再按一下退出",
                                        Toast.LENGTH_SHORT);
            toast.show();
            back_pressed = System.currentTimeMillis();
        }
    }

    @Override
    protected void onResume() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectorManager.ACTION_MESSAGE_RECEIVED);
        intentFilter.addAction(ConnectorManager.ACTION_SENT_FAILED);
        intentFilter.addAction(ConnectorManager.ACTION_SENT_SUCCESS);
        intentFilter.addAction(ConnectorManager.ACTION_MESSAGE_FAILED);
        intentFilter.addAction(ConnectorManager.ACTION_MESSAGE_SUCCESS);
        intentFilter.addAction(ConnectorManager.ACTION_REPLY_FAILED);
        intentFilter.addAction(ConnectorManager.ACTION_REPLY_SUCCESS);
        intentFilter.addAction(ConnectorManager.ACTION_SYSTEM_RECEIVED);
        intentFilter.addAction(ConnectorManager.ACTION_CONNECTION_CLOSED);
        intentFilter.addAction(ConnectorManager.ACTION_CONNECTION_FAILED);
        intentFilter.addAction(ConnectorManager.ACTION_CONNECTION_SUCCESS);
        intentFilter.addAction(ConnectorManager.ACTION_REPLY_RECEIVED);
        intentFilter.addAction(ConnectorManager.ACTION_NETWORK_CHANGED);
        intentFilter.addAction(ConnectorManager.ACTION_UNCAUGHT_EXCEPTION);
        intentFilter.addAction(ConnectorManager.ACTION_CONNECTION_STATUS);
        intentFilter.addAction(ConnectorManager.ACTION_CONNECTION_RECOVERY);
        this.registerReceiver(receiver, intentFilter);
        super.onResume();

    }
        @Override
        protected void onStop() {
            this.unregisterReceiver(receiver);
            super.onStop();
        }

}

