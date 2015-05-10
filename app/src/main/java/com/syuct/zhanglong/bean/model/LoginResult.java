package com.syuct.zhanglong.bean.model;

/**
 * Created by zhanglong on 5/9/15.
 */
public class LoginResult {
    private Integer code;
    private String msg;
    private String result;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
