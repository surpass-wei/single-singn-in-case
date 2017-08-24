package com.surpass.bean;

/**
 * 添加用户的请求响应对象
 * <p>
 * Created by surpass.wei@gmail.com on 2017/8/15.
 */
public class AddUserRes {
    private boolean success;
    private String msg;
    private Long userId;
    private String encryptPassword;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEncryptPassword() {
        return encryptPassword;
    }

    public void setEncryptPassword(String encryptPassword) {
        this.encryptPassword = encryptPassword;
    }
}
