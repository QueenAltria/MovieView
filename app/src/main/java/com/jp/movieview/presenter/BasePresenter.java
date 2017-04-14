package com.jp.movieview.presenter;

import com.jp.movieview.view.IBaseView;
import rx.Subscription;

/**
 * Author: JP
 * Time:  2017/4/13 11:17
 */
public class BasePresenter<V extends IBaseView> {
    public V mView;
    //Subscription接口,可以用来取消订阅
    protected Subscription mSubscription;

    public BasePresenter(V view){
        mView = view;
    }

    public void detach() {
        mView = null;
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }
}
