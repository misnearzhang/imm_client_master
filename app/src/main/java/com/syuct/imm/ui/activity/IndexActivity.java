package com.syuct.imm.ui.activity;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;

import android.view.View;

import android.widget.ImageButton;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.syuct.imm.core.io.CacheToolkit;
import com.syuct.imm.core.io.PushManager;
import com.syuct.imm.core.io.PushService;
import com.syuct.imm.ui.R;
import com.syuct.imm.ui.fragment.FriendListFragment;
import com.syuct.imm.ui.fragment.LeftFragment;
import com.syuct.imm.ui.fragment.MessageFragment;
import com.syuct.imm.utils.GlobalData;

import java.util.List;

/**
 *
 */
public class IndexActivity extends SlidingFragmentActivity implements View.OnClickListener{//这里继承的是SlidingFragmentActivity

    private SlidingMenu sm;//滑动菜单
    private Fragment leftFragment;//左侧视图
    static long back_pressed;
    private Fragment currentFragment;
    private Fragment messageFragment;
    private ImageButton topbutton;
    private ImageButton btnRecent;
    private ImageButton btnFriendList;
    private ImageButton btnTimeLine;
    private Intent serviceIntent;

    SoundPool soundPool;
    @Override
    public void onCreate(Bundle savedInstanceState) {//将protected变成public
        super.onCreate(savedInstanceState);
        SoundPool.Builder builder = new SoundPool.Builder();
        builder.setMaxStreams(2);//传入音频数量
        //AudioAttributes是一个封装音频各种属性的方法
        AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
        attrBuilder.setLegacyStreamType(AudioManager.STREAM_MUSIC);//设置音频流的合适的属性
        builder.setAudioAttributes(attrBuilder.build());//加载一个AudioAttributes
        soundPool = builder.build();
        soundPool.load(this, R.raw.system,1);

        CacheToolkit.getInstance(this).putString(CacheToolkit.KEY_CIM_SERVIER_HOST,"45.32.10.203");
        CacheToolkit.getInstance(this).putString(CacheToolkit.KEY_CIM_SERVIER_PORT,"3000");
        serviceIntent= new Intent(this, PushService.class);
        serviceIntent.setAction(PushManager.ACTION_CREATE_IM_CONNECTION);
        startService(serviceIntent);
        setContentView(R.layout.activity_index);
        setBehindContentView(R.layout.menu);//设置SlidingMenu的layout
        topbutton=(ImageButton) findViewById(R.id.topbutton);
        topbutton.setOnClickListener(this);
        btnRecent=(ImageButton)findViewById(R.id.BtnRecentChatting);
        btnRecent.setOnClickListener(this);
        btnFriendList=(ImageButton)findViewById(R.id.BtnFriendList);
        btnFriendList.setOnClickListener(this);
        btnTimeLine=(ImageButton)findViewById(R.id.BtnTimeLine);
        btnTimeLine.setOnClickListener(this);
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
        messageFragment=new MessageFragment();
        FriendListFragment friendlistfragment=FriendListFragment.newInstance();
        friendlistfragment.setTargetFragment(friendlistfragment,1);
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
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            GlobalData.setUUID("2");
            final FragmentManager fragmentManager = getFragmentManager();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                List<Fragment> fragments = fragmentManager.getFragments();
                fragments.forEach(item->{
                    fragmentManager.beginTransaction().remove(item);
                });

            }
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
        serviceIntent.setAction(PushManager.ACTION_CREATE_IM_CONNECTION);
        startService(serviceIntent);
        super.onResume();

    }
    @Override
    protected void onStop() {
        /*this.unregisterReceiver(receiver);*/
        super.onStop();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.topbutton:
                soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                    @Override
                    public void onLoadComplete(SoundPool soundPool, int i, int i1) {
                        soundPool.play(1,1, 1, 0, 0, 1);
                    }
                });
                toggle();
                break;
            case R.id.BtnRecentChatting:
                break;
            case R.id.BtnFriendList:
                break;
            case R.id.BtnTimeLine:
                switchContent(currentFragment,messageFragment);
                break;
        }
    }
}

