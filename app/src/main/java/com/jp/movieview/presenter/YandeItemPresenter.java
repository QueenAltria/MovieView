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

    public void getDayItemData(String day, String month, String year) {
        mSubscription = RxManager.getInstance()
                .doSubscribe(ApiService.getInstance().initService(YandeService.class).getPopularDayData(day,month,year),
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

    public void getWeekItemData(String day, String month,String year) {
        mSubscription = RxManager.getInstance()
                .doSubscribe(ApiService.getInstance().initService(YandeService.class).getPopularWeekData(day,month,year),
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

    public void getMonthItemData(String day, String month,String year) {
        mSubscription = RxManager.getInstance()
                .doSubscribe(ApiService.getInstance().initService(YandeService.class).getPopularMonthData(day,month,year),
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

    public void getYearItemData(String period) {
        mSubscription = RxManager.getInstance()
                .doSubscribe(ApiService.getInstance().initService(YandeService.class).getPopularYearData(period),
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


    public void getTagItemData(String page,String tags) {
        mSubscription = RxManager.getInstance()
                .doSubscribe(ApiService.getInstance().initService(YandeService.class).getTagData(page,tags),
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
