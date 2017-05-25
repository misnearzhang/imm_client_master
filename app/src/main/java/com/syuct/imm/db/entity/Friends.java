package com.syuct.imm.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Index;

/**
 * Created by misne on 2017/5/20.
 */

@Entity
public class Friends {
    @Id(autoincrement = true)
    private Long id;
    @Index(unique = true)
    private String friendAccount;
    private String friendName;
    private String headImageNet;//头像网络地址
    private String headImageLocal;//头像本地地址
    private Date addTime;
    private Date updateTime;
    @Generated(hash = 1101975472)
    public Friends(Long id, String friendAccount, String friendName,
            String headImageNet, String headImageLocal, Date addTime,
            Date updateTime) {
        this.id = id;
        this.friendAccount = friendAccount;
        this.friendName = friendName;
        this.headImageNet = headImageNet;
        this.headImageLocal = headImageLocal;
        this.addTime = addTime;
        this.updateTime = updateTime;
    }
    @Generated(hash = 823074882)
    public Friends() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFriendAccount() {
        return this.friendAccount;
    }
    public void setFriendAccount(String friendAccount) {
        this.friendAccount = friendAccount;
    }
    public String getFriendName() {
        return this.friendName;
    }
    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }
    public String getHeadImageNet() {
        return this.headImageNet;
    }
    public void setHeadImageNet(String headImageNet) {
        this.headImageNet = headImageNet;
    }
    public String getHeadImageLocal() {
        return this.headImageLocal;
    }
    public void setHeadImageLocal(String headImageLocal) {
        this.headImageLocal = headImageLocal;
    }
    public Date getAddTime() {
        return this.addTime;
    }
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
    public Date getUpdateTime() {
        return this.updateTime;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}

