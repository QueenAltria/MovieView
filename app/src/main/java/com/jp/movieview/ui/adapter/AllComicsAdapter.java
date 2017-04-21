package com.jp.movieview.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jp.movieview.R;
import com.jp.movieview.bean.YandeBean;
import com.jp.movieview.ui.activity.PhotoViewActivity;
import com.jp.movieview.utils.LogUtils;
import com.jp.movieview.widget.RatioImageView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jp on 2017/4/5.
 */
public class AllComicsAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public static final String TAG = "AllComicsAdapter";

    private Map<Integer, Integer> imageHeightMap = new HashMap<>();
    private int imageWidth;
    private String url;
    private int position;


    public AllComicsAdapter(List<String> data) {
        super(R.layout.safebooru_item, data);
    }

    public AllComicsAdapter() {
        super(R.layout.safebooru_item);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final String item) {

        RatioImageView view = helper.getView(R.id.safe_img);
        view.setOriginalSize(ViewGroup.LayoutParams.MATCH_PARENT, 186);

        Glide.with(mContext).load(item).placeholder(R.drawable.white_bg).into(view);

        LogUtils.e(TAG,item);


        position = helper.getLayoutPosition();
    }
}
