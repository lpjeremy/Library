package com.lpjeremy.libmodule.http;


import android.annotation.SuppressLint;

import com.blankj.utilcode.util.NetworkUtils;
import com.lpjeremy.libmodule.http.callback.HttpRequestCallBack;
import com.lpjeremy.libmodule.http.exception.ApiException;
import com.lpjeremy.libmodule.http.model.BaseResult;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class HttpPresenter {
    private static final ObservableTransformer observableTransformer = new ObservableTransformer() {
        @Override
        public ObservableSource apply(Observable upstream) {
            return upstream.subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    };
    private static final Function<Throwable, BaseResult> errFunction = new Function<Throwable, BaseResult>() {
        @Override
        public BaseResult apply(Throwable throwable) throws Exception {
            BaseResult result = new BaseResult();
            result.setSuccess(false);
            result.setMessage(throwable.getMessage());
            return result;
        }
    };

    /**
     * 接口返回为一个对象数据，的统一处理
     */
    @SuppressLint("MissingPermission")
    protected static <T> void execute(Observable<BaseResult<T>> observable, final HttpRequestCallBack<T> callBack) {
        if (!NetworkUtils.isConnected()) {
            if (callBack!= null) {
                callBack.onFail(new ApiException(ApiCode.Request.NETWORK_ERROR, "网络连接异常"));
            }
            return;
        }
        observable.compose(observableTransformer)
                .onErrorReturn(errFunction)
                .subscribe(new Observer<BaseResult<T>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<T> t) {
                        if (t == null) {
                            //抛出错误
                            if (callBack != null) {
                                callBack.onFail(new ApiException(ApiCode.Response.HTTP_NULL, "未返回任何数据"));
                            }
                        } else {
                            if (t.isSuccess()) {
                                //成功
                                if (callBack != null) {
                                    callBack.onSuccess(t.getData());
                                }
                            } else {
                                //失败
                                if (callBack != null) {
                                    callBack.onFail(new ApiException(t.getErrorCode(), t.getMessage()));
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (callBack != null) {
                            callBack.onFail(new ApiException(e, ApiCode.Request.UNKNOWN));
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @SuppressLint("MissingPermission")
    protected static <T> void execute(Observable<BaseResult<T>> observable, Observer<BaseResult<T>> observer) {
        if (!NetworkUtils.isConnected()) {
            observer.onError(new ApiException(ApiCode.Request.NETWORK_ERROR, "网络连接异常"));
            return;
        }
        observable.compose(observableTransformer)
                .onErrorReturn(errFunction).subscribe(observer);
    }

}
