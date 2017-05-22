package com.syuct.imm.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;

import java.util.Date;
@Entity
public class User {
    @Id(autoincrement = true)
    private Long id;
    @Unique
    @NotNull
    private String account;
    private String userName;
    private String password;
    private String status;
    private java.util.Date addTime;
    @Generated(hash = 630061635)
    public User(Long id, @NotNull String account, String userName, String password,
            String status, java.util.Date addTime) {
        this.id = id;
        this.account = account;
        this.userName = userName;
        this.password = password;
        this.status = status;
        this.addTime = addTime;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public String getAccount() {
        return this.account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getStatus() {
        return this.status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public java.util.Date getAddTime() {
        return this.addTime;
    }
    public void setAddTime(java.util.Date addTime) {
        this.addTime = addTime;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
