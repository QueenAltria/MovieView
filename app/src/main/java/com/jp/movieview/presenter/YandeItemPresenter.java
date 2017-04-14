package com.jp.movieview.presenter;

import com.jp.movieview.api.YandeService;
import com.jp.movieview.constant.Code;
import com.jp.movieview.net.ApiService;
import com.jp.movieview.rx.RxManager;
import com.jp.movieview.rx.RxSubscriber;
import com.jp.movieview.utils.JsoupUtil;
import com.jp.movieview.view.YandeItemView;

/**
 * Author: JP
 * Time: 2016/8/12 14:29
 */
public class YandeItemPresenter extends BasePresenter<YandeItemView> {

    public YandeItemPresenter(YandeItemView view) {
        super(view);
    }

    public void getGirlItemData(String day, String month,String year) {
        mSubscription = RxManager.getInstance()
                .doSubscribe(ApiService.getInstance().initService(YandeService.class).getYandePopularData(day,month,year),
                        new RxSubscriber<String>(false) {
                            @Override
                            protected void _onNext(String s) {
                                mView.onSuccess(Code.YANDE_CODE,JsoupUtil.parseYandeData(s));
                            }

                            @Override
                            protected void _onError() {
                                mView.onError();
                            }
                        });
    }
}
