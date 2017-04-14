package com.jp.movieview.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Parcelable;

import com.jp.movieview.bean.YandeBean;
import com.jp.movieview.utils.ImageLoader;
import com.jp.movieview.utils.LogUtils;
import com.jp.movieview.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jp on 2017/4/11.
 */
public class DataService extends IntentService {
    public static final String TAG="DataService";
    public DataService() {
        super("");
    }

    public static void startService(Context context, List<YandeBean> datas, String subtype) {
        Intent intent = new Intent(context, DataService.class);
        intent.putParcelableArrayListExtra("data", (ArrayList<? extends Parcelable>) datas);
        intent.putExtra("subtype", subtype);
        context.startService(intent);
    }


    public static void startService(Context context, List<YandeBean> datas) {
        Intent intent = new Intent(context, DataService.class);
        intent.putParcelableArrayListExtra("data", (ArrayList<? extends Parcelable>) datas);
        LogUtils.e(TAG,"发送了1");
        context.startService(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent == null) {
            return;
        }

        List<YandeBean> datas = intent.getParcelableArrayListExtra("data");
        //String subtype = intent.getStringExtra("subtype");
        handleGirlItemData(datas);
        LogUtils.e(TAG,"发送了2");
    }

    private void handleGirlItemData(final List<YandeBean> datas) {
        if (datas.size() == 0) {
            EventBus.getDefault().post("finish");
            ToastUtils.showToast(this,"发送了3");
            return;
        }
        for (final YandeBean data : datas) {
            Bitmap bitmap = ImageLoader.load(this, data.getPreview_url());
            if (bitmap != null) {
                data.setWidth(bitmap.getWidth());
                data.setHeight(bitmap.getHeight());
            }
            //data.setSubtype(subtpe);
        }
        EventBus.getDefault().post(datas);
        LogUtils.e(TAG,"发送了4");
    }
}
