package com.syuct.zhanglong.bean;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by zhanglong on 5/9/15.
 */
public class Login {
    private ProgressDialog dialog;
    private String userAccount;
    private String password;

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ProgressDialog getDialog() {
        return dialog;
    }

    public void setDialog(ProgressDialog dialog) {
        this.dialog = dialog;
    }
}
