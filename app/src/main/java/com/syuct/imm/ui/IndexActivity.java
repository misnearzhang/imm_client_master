package com.syuct.imm.ui;


import android.app.Fragment;
import android.app.FragmentManager;
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
public class IndexActivity extends SlidingFragmentActivity implements View.OnClickListener{//这里继承的是SlidingFragmentActivity

    static String name;
    private TextView title;
    private SlidingMenu sm;//滑动菜单
    private Fragment leftFragment;//左侧视图
    static long back_pressed;
    private Fragment currentFragment;
    private Fragment mContent;
    private ImageButton topbutton;
    private BroadcastReceiver receiver;


    @Override
    public void onCreate(Bundle savedInstanceState) {//将protected变成public
        super.onCreate(savedInstanceState);
        receiver=new SystemBroad();
        setContentView(R.layout.activity_index);
        setBehindContentView(R.layout.menu);//设置SlidingMenu的layout

        topbutton=(ImageButton) findViewById(R.id.topbutton);
        topbutton.setOnClickListener(this);
        sm = getSlidingMenu();
        sm.setMode(SlidingMenu.LEFT);//设置SlidingMenu可以从左右两侧都可以滑出
        //左边的菜单
//		sm.setMenu(R.layout.left_menu_layout);//设置SlidingMenu显示的内容
        sm.setShadowDrawable(R.drawable.shadow);// 设置SlidingMenu和主页面交界部分的阴影图片
        sm.setTouchModeAbove(SlidingMenu.LEFT);//设置滑出slidingmenu范围
        sm.setShadowWidthRes(R.dimen.shadow_width);// 设置阴影部分的宽度
        sm.setBehindOffsetRes(R.dimen.main_width);//设置主界面的宽度
        leftFragment = new LeftFragment();//SlidingMenu需要显示的Fragment的实例
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        ChattingCurrentFragment chattingCurrentfragment=ChattingCurrentFragment.newInstance();
        FriendlistFragment friendlistfragment=FriendlistFragment.newInstance();
        transaction.replace(R.id.container, friendlistfragment).addToBackStack("friendlistfragment").commit();
                //通过Fragment的管理器就可以切换Fragment
        fm.beginTransaction()//fragment的事物管理
                .replace(R.id.menu_frame, leftFragment)//参数1：layout的id，参数2：要显示的Fragment实例
                .commit();
    }
    public void switchContent(Fragment from, Fragment to) {
        if (currentFragment != to) {
            currentFragment = to;
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            if (!to.isAdded()) {	// 先判断是否被add过
                transaction.hide(from).add(R.id.container, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //1.bundle
        //2.存放的ID
        //3.当前要保存的fragment的实例
        this.getFragmentManager().putFragment(outState,
                                              "contentFragment",
                currentFragment);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.topbutton:
                toggle();
        }
    }
}

