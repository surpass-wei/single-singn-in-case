package com.surpass.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 基本的请求响应对象
 * <p>
 * Created by surpass.wei@gmail.com on 2016/12/20.
 */
public class CommonRes implements Serializable {
    private boolean success;// 是否成功
    private String msg;// 提示信息
    private Map<String, Object> data = new HashMap<>();    //  数据集

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

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
