package com.jp.movieview;

import android.app.Application;
import android.content.Context;

import com.jp.movieview.utils.SPUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cookie.store.PersistentCookieStore;

/**
 * Created by jp on 2017/4/13
 */
public class ViewApplication extends Application{
    public  final String TAG = getClass().getName();
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();


        OkGo.init(this);
        OkGo.getInstance().setCookieStore(new PersistentCookieStore());


        mContext = getApplicationContext();
        SPUtil.init(mContext,"viewapp");

    }

    public static Context getContext() {
        return mContext;
    }

}
