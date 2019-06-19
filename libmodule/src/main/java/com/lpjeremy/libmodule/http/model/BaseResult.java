package com.lpjeremy.libmodule.http.model;

/**
 * @desc:请求结果基础类
 * @date:2019/1/27 11:29
 * @auther:lp
 * @version:1.0
 */
public class BaseResult<T> {

    protected boolean success;

    protected int errorCode;

    protected String message;

    protected int count;

    private T data;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseResult{" +
                "success=" + success +
                ", errorCode=" + errorCode +
                ", message='" + message + '\'' +
                ", count=" + count +
                '}';
    }
}
