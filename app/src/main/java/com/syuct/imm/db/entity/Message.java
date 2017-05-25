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
    private String type;//文字 图片 附件
    private String imageUri;
    private String imageUriLocal;
    private Long addTime;
    @Generated(hash = 2097285350)
    public Message(String uuid, String userAccount, String friendId,
            String friendAccount, String status, String type, String imageUri,
            String imageUriLocal, Long addTime) {
        this.uuid = uuid;
        this.userAccount = userAccount;
        this.friendId = friendId;
        this.friendAccount = friendAccount;
        this.status = status;
        this.type = type;
        this.imageUri = imageUri;
        this.imageUriLocal = imageUriLocal;
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
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Long getAddTime() {
        return this.addTime;
    }
    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }
    public String getImageUri() {
        return this.imageUri;
    }
    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
    public String getImageUriLocal() {
        return this.imageUriLocal;
    }
    public void setImageUriLocal(String imageUriLocal) {
        this.imageUriLocal = imageUriLocal;
    }

}
