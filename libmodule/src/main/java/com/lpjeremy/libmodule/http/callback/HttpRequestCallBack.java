package com.lpjeremy.libmodule.http.callback;

import com.lpjeremy.libmodule.http.exception.ApiException;

/**
 * @desc:网络请求回调
 * @date:2019/1/27 10:21
 * @auther:lp
 * @version:1.0
 */
public interface HttpRequestCallBack<T> {
    /**
     * 请求成功
     *
     * @param result
     */
    void onSuccess(T result);

    /**
     * 请求失败
     */
    void onFail(ApiException e);
}
