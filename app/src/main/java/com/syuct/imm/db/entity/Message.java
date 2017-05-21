package com.syuct.imm.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Message {

    @Id
    private String uuid;
    private String userAccount;
    private String friendId;
    private String friendAccount;
    private String status;
    private Long addTime;
    @Generated(hash = 1118384895)
    public Message(String uuid, String userAccount, String friendId,
            String friendAccount, String status, Long addTime) {
        this.uuid = uuid;
        this.userAccount = userAccount;
        this.friendId = friendId;
        this.friendAccount = friendAccount;
        this.status = status;
        this.addTime = addTime;
    }
    @Generated(hash = 637306882)
    public Message() {
    }
    public String getUuid() {
        return this.uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public String getUserAccount() {
        return this.userAccount;
    }
    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }
    public String getFriendId() {
        return this.friendId;
    }
    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }
    public String getFriendAccount() {
        return this.friendAccount;
    }
    public void setFriendAccount(String friendAccount) {
        this.friendAccount = friendAccount;
    }
    public String getStatus() {
        return this.status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Long getAddTime() {
        return this.addTime;
    }
    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }
}
