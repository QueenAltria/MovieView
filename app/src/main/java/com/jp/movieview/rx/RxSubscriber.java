package com.jp.movieview.rx;

import android.content.Context;

import com.jp.movieview.ViewApplication;
import com.jp.movieview.utils.LogUtils;
import com.jp.movieview.utils.ToastUtils;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Author: Othershe
 * Time:  2016/8/11 17:45
 */
public abstract class RxSubscriber<T> extends Subscriber<T> {
    public final String TAG=getClass().getName();

    private Context mContext;
    private boolean mIsShowLoading;//是否显示加载loading

    public RxSubscriber(boolean isShowLoading) {
        mContext = ViewApplication.getContext();
        mIsShowLoading = isShowLoading;
    }

    @Override
    public void onCompleted() {
        cancelLoading();
    }

    @Override
    public void onError(Throwable e) {
        //统一处理请求异常的情况
        if (e instanceof IOException) {
            ToastUtils.showToast(mContext, "网络连接异常");
        } else if(e instanceof HttpException){
            HttpException exception= (HttpException) e;
            int code=exception.response().code();
            ToastUtils.showToast(mContext, e.getMessage());
            LogUtils.e(TAG,e.getClass().getName()+"-----"+e.getLocalizedMessage());
        } else {
            ToastUtils.showToast(mContext, e.getLocalizedMessage());
            LogUtils.e(TAG,e.getClass().getName()+"-----"+e.getLocalizedMessage());
        }

        _onError();

        cancelLoading();
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    @Override
    public void onStart() {
        super.onStart();
        showLoading();
    }

    /**
     * 可在此处统一显示loading view
     */
    private void showLoading() {
        if (mIsShowLoading) {

        }
    }

    private void cancelLoading() {

    }

    protected abstract void _onNext(T t);

    protected abstract void _onError();

}
