package com.jp.movieview.injector.module;

import com.jp.movieview.presenter.YandeItemPresenter;
import com.jp.movieview.ui.activity.YandeActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jp on 2017/4/18
 */
@Module
public class YandeModule {
    public static final String TAG = "YandeModel";

    YandeItemPresenter mPresenter;

    public YandeModule(YandeActivity activity) {
        mPresenter = new YandeItemPresenter(activity);
    }

    @Provides
    @Singleton
    YandeItemPresenter providesMainPresenter() {
        return mPresenter;
    }
}
