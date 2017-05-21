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
    @Id
    private Long id;
    @Index(unique = true)
    private String friendAccount;
    private String friendName;
    private Date addTime;
    private Date updateTime;
    @Generated(hash = 1330653055)
    public Friends(Long id, String friendAccount, String friendName, Date addTime,
            Date updateTime) {
        this.id = id;
        this.friendAccount = friendAccount;
        this.friendName = friendName;
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

