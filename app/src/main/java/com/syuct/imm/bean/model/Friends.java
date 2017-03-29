package com.syuct.imm.bean.model;

import java.io.Serializable;

/**
 * Created by Misnearzhang on 2017/3/28.
 */
public class Friends implements Serializable{

    private String account;
    private String nickname;
    private String sex;

    public Friends(String account, String nickname, String sex) {
        this.account = account;
        this.nickname = nickname;
        this.sex = sex;
    }

    public String getAccount() {
        return account;
    }

    public Friends setAccount(String account) {
        this.account = account;
        return this;
    }

    public String getNickname() {
        return nickname;
    }

    public Friends setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getSex() {
        return sex;
    }

    public Friends setSex(String sex) {
        this.sex = sex;
        return this;
    }
}
