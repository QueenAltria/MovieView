package com.jp.movieview.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by jp on 2016/9/18.
 */
public class ToastUtils {

    private static Toast mToast;

    public static  void showCustomToast(String toastMsg){
        //设置内容
        //Toast toast=Toast.makeText(MyApplication.singleton,toastMsg,Toast.LENGTH_SHORT);

        if (mToast == null) {
            //mToast = Toast.makeText(App.getInstance(),toastMsg,Toast.LENGTH_SHORT);
        } else {
            mToast.setText(toastMsg);
        }
        mToast.setGravity(Gravity.CENTER , 0, 0);
        mToast.show();
    }


    public static void showToast(Context context, String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }
}
