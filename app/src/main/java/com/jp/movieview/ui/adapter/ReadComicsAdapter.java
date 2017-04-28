package com.jp.movieview.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jp.movieview.R;
import com.jp.movieview.bean.ComicBean;
import com.jp.movieview.ui.activity.ComicInfoActivity;
import com.jp.movieview.utils.ImageLoader;
import com.jp.movieview.utils.LogUtils;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Created by jp on 2017/4/5.
 */
public class ReadComicsAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public static final String TAG = "ReadComicsAdapter";

    PositionCallBack mCallBack;

    public ReadComicsAdapter(List<String> data) {
        super(R.layout.read_item, data);
    }

    public ReadComicsAdapter() {
        super(R.layout.read_item);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final String item) {
        ImageView imageView = helper.getView(R.id.safe_img);
        //LogUtils.e(TAG,item);
//        DrawableRequestBuilder requestBuilder = Glide.with(mContext).load(item);
//        requestBuilder.into(imageView);
//        requestBuilder.downloadOnly();

        TextView textView=helper.getView(R.id.page);
        textView.setText(item);

        mCallBack.getPosition(helper.getAdapterPosition()+1);
        ImageLoader.load(mContext,item,imageView);


    }



    public void setCallBack(PositionCallBack callBack) {
        mCallBack = callBack;
    }

    public interface PositionCallBack{
        void getPosition(int position);
    }
}
