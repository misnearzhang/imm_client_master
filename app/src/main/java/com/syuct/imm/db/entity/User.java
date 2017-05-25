package com.syuct.imm.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;
@Entity
public class User {
    @Id(autoincrement = true)
    private Long id;
    @Unique
    @NotNull
    private String account;
    private String userName;
    private String headImageNet;
    private String HeadImageLocal;
    private String password;
    private String status;
    private Date addTime;
    @Generated(hash = 816856748)
    public User(Long id, @NotNull String account, String userName,
            String headImageNet, String HeadImageLocal, String password,
            String status, Date addTime) {
        this.id = id;
        this.account = account;
        this.userName = userName;
        this.headImageNet = headImageNet;
        this.HeadImageLocal = HeadImageLocal;
        this.password = password;
        this.status = status;
        this.addTime = addTime;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public String getHeadImageNet() {
        return this.headImageNet;
    }
    public void setHeadImageNet(String headImageNet) {
        this.headImageNet = headImageNet;
    }
    public String getHeadImageLocal() {
        return this.HeadImageLocal;
    }
    public void setHeadImageLocal(String HeadImageLocal) {
        this.HeadImageLocal = HeadImageLocal;
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
    public Date getAddTime() {
        return this.addTime;
    }
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}
