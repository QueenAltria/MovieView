package com.jp.movieview.injector.component;

import com.jp.movieview.injector.module.YandeModule;
import com.jp.movieview.ui.activity.YandeActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by jp on 2017/4/18
 */
@Singleton
@Component(modules = YandeModule.class)
public interface YandeComponent {

    void inject(YandeActivity activity);
}
