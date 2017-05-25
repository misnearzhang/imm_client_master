package com.syuct.imm.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by misnearzhang on 2017/5/25.
 */

@Entity
public class RecentlyChatting {
    @Id(autoincrement = true)
    private long id;
    private int type;//消息类型    朋友消息   广告消息
    private String account;//广告为广告url   朋友消息为朋友账户
    private String titleImageNet;
    private String titleImageLocal;
    private String name;
    private String summary;//消息摘要
    private Date date;
    @Generated(hash = 995012206)
    public RecentlyChatting(long id, int type, String account, String titleImageNet,
            String titleImageLocal, String name, String summary, Date date) {
        this.id = id;
        this.type = type;
        this.account = account;
        this.titleImageNet = titleImageNet;
        this.titleImageLocal = titleImageLocal;
        this.name = name;
        this.summary = summary;
        this.date = date;
    }
    @Generated(hash = 1438374229)
    public RecentlyChatting() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getAccount() {
        return this.account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public String getTitleImageNet() {
        return this.titleImageNet;
    }
    public void setTitleImageNet(String titleImageNet) {
        this.titleImageNet = titleImageNet;
    }
    public String getTitleImageLocal() {
        return this.titleImageLocal;
    }
    public void setTitleImageLocal(String titleImageLocal) {
        this.titleImageLocal = titleImageLocal;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSummary() {
        return this.summary;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }
    public Date getDate() {
        return this.date;
    }
    public void setDate(Date date) {
        this.date = date;
    }


}
