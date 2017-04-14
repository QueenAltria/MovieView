package com.jp.movieview;

import android.app.Application;
import android.content.Context;

import com.jp.movieview.utils.SPUtil;

/**
 * Created by jp on 2017/4/13
 */
public class ViewApplication extends Application{
    public static final String TAG = "ViewApplication";
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();
        SPUtil.init(mContext,"viewapp");

    }

    public static Context getContext() {
        return mContext;
    }

}