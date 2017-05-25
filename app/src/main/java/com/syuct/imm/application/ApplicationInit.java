package com.syuct.imm.application;

import android.app.Application;

/**
 * Created by misne on 2017/5/20.
 */

public class ApplicationInit extends Application{
    /*private DaoMaster.DevOpenHelper mHelper;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    public static ApplicationInit instances;
    @Override
    public void onCreate() {
        super.onCreate();
        instances = this;
        setDatabase();
    }
    public static ApplicationInit getInstances(){
        return instances;
    }

    *//**
     * 设置greenDao
     *//*
    private void setDatabase() {
        mHelper = new DaoMaster.DevOpenHelper(this, "imm-db", null);
        mDaoMaster = new DaoMaster(mHelper.getWritableDatabase());
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoSession = mDaoMaster.newSession();
    }
    public DaoSession getDaoSession() {
        return mDaoSession;
    }*/
}
