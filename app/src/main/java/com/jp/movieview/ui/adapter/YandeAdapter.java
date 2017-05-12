package com.jp.movieview.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
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
import com.jp.movieview.utils.ToastUtils;
import com.jp.movieview.widget.RatioImageView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jp on 2017/4/5.
 */
public class YandeAdapter extends BaseQuickAdapter<YandeBean, BaseViewHolder> {
    public static final String TAG = "SafebooruAdapter";

    private Map<Integer, Integer> imageHeightMap = new HashMap<>();
    private int imageWidth;
    private String url;
    private int position;


    public YandeAdapter(List<YandeBean> data) {
        super(R.layout.safebooru_item, data);
    }

    public YandeAdapter() {
        super(R.layout.safebooru_item);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final YandeBean item) {

        helper.setIsRecyclable(false);
        helper.setText(R.id.content_text, item.getName() + "\n" + item.getSize());
        RatioImageView view = helper.getView(R.id.safe_img);
        url = item.getPreview_url();

        view.setOriginalSize(item.getWidth(), item.getHeight());
        //Glide.with(mContext).load("http:"+url).placeholder(R.drawable.white_bg).into(view);
        Glide.with(mContext).load(url).placeholder(R.drawable.white_bg).into(view);
        //LogUtils.e(TAG,url);

        helper.getConvertView().setOnClickListener(view1 -> {
                    //ToastUtils.showToast(mContext, item.getSrc_url());
                    Intent intent = new Intent(mContext, PhotoViewActivity.class);
                    intent.putExtra("url", item.getRealUrl());
                    LogUtils.e(TAG,item.getRealUrl());
                    String[] split = item.getSrc_url().split("/");
                    String id = split[split.length - 1];
                    intent.putExtra("id", id);
                    intent.putStringArrayListExtra("tags",item.getTagList());


                    mContext.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext).toBundle());
                }
        );

        position = helper.getLayoutPosition();

//        if (!this.imageHeightMap.containsKey(position)) {
//            //当首次加载图片时，调用 loadImageFirst()，保存图片高度
//            loadImageFirst(view, position);
//
//        } else {
//            //非首次加载，直接根据保存的长宽，获取图片
//            Glide.with(this.mContext)
//                    .load(url)
//                    .crossFade()
//                    .override(imageWidth,imageHeightMap.get(position))
//                    .placeholder(R.drawable.white_bg)
//                    .into(view);
//            card.setVisibility(View.VISIBLE);
//        }
    }

    //不确定尺寸的时候
    public void loadImageFirst(View view, final int position) {
        //构造方法中参数view,就是回调方法中的this.view
        ViewTarget<View, Bitmap> target = new ViewTarget<View, Bitmap>(view) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                //加载图片成功后调用
                float scaleType = ((float) resource.getHeight()) / resource.getWidth();
                imageWidth = view.getMeasuredWidth();
                int imageHeight = (int) (imageWidth * scaleType);
                //获取图片高度，保存在Map中
                imageHeightMap.put(position, imageHeight);
                //设置图片布局的长宽，Glide会根据布局的自动加载适应大小的图片
                ViewGroup.LayoutParams lp = this.view.getLayoutParams();
                lp.width = imageWidth;
                lp.height = imageHeight;
                this.view.setLayoutParams(lp);
                //resource就是加载成功后的图片资源
                ((ImageView) view).setImageBitmap(resource);
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                //加载图片失败后调用
                super.onLoadFailed(e, errorDrawable);
                int imageHeight = imageWidth;
                imageHeightMap.put(position, imageHeight);
                ViewGroup.LayoutParams lp = this.view.getLayoutParams();
                lp.width = imageWidth;
                lp.height = imageHeight;
                this.view.setLayoutParams(lp);
                ((ImageView) view).setImageResource(R.drawable.white_bg);
            }
        };
        Glide.with(this.mContext)
                .load(url)
                .asBitmap() //作为Bitmap加载，对应onResourceReady回调中第一个参数的类型
                .placeholder(R.drawable.white_bg)
                .into(target);
    }
}
