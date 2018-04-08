package com.zhou.xin.bean;

/**
 * Created by zhou on 2017/10/22.
 */

public class UserInfo {

    /**
     * uid : DB1BC4A09027FAE4
     * accountNumber : 13631782148
     * error : -1
     * token : be0a74237f644437a31546b60cc7f4b6
     * expires_in : 172800
     * code : 1
     * msg : 请设置个人信息
     */

    private String uid;
    private String accountNumber;
    private String error;
    private String token;
    private int expires_in;
    private String code;
    private String msg;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "uid='" + uid + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", error='" + error + '\'' +
                ", token='" + token + '\'' +
                ", expires_in=" + expires_in +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
