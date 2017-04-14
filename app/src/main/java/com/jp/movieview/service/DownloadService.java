package com.jp.movieview.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Parcelable;

import com.jp.movieview.bean.YandeBean;
import com.jp.movieview.ui.activity.PhotoViewActivity;
import com.jp.movieview.utils.ImageLoader;
import com.jp.movieview.utils.LogUtils;
import com.jp.movieview.utils.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by jp on 2017/4/11.
 */
public class DownloadService extends IntentService {
    public static final String TAG="DownloadService";
    public DownloadService() {
        super("");
    }


    public static void startService(Context context, String url,String id) {
        ToastUtils.showToast(context,"开始了4");
        Intent intent = new Intent(context, DownloadService.class);
        intent.putExtra("url", url);
        intent.putExtra("id", id);
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

        String url = intent.getStringExtra("url");
        String id = intent.getStringExtra("id");
        //String subtype = intent.getStringExtra("subtype");
        handleGirlItemData(url,id);
    }

    private void handleGirlItemData(final String url,String id) {

        OkGo.get(url)
                .tag(this)
                .execute(new FileCallback(Environment.getExternalStorageDirectory().getPath() + "/AView/Yande", id+".jpg") {  //文件下载时，可以指定下载的文件目录和文件名
                    @Override
                    public void onSuccess(File file, Call call, Response response) {
                        // file 即为文件数据，文件保存在指定目录
                        EventBus.getDefault().post("ok");
                        LogUtils.e("TAG","下载完成");

                    }

                    @Override
                    public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        //这里回调下载进度(该回调在主线程,可以直接更新ui)
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        LogUtils.e(TAG, "message" + e.getLocalizedMessage());
                    }
                });

    }
}
