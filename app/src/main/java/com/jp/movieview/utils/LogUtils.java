package com.jp.movieview.utils;

import android.util.Log;

/**
 * Created by jp on 2016/9/21.
 */
public class LogUtils {
    public static final String TAG = "LogUtils";
    public static final boolean DEBUG = true;

    public static void e(String tag,String msg){
        if(DEBUG){
            Log.e(tag+"------------->",msg);
        }
    }
}
