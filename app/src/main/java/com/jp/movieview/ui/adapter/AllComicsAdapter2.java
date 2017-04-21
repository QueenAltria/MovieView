package com.jp.movieview.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jp.movieview.R;
import com.jp.movieview.bean.ComicBean;
import com.jp.movieview.ui.activity.ComicInfoActivity;
import com.jp.movieview.utils.ImageLoader;
import com.jp.movieview.utils.LogUtils;
import com.jp.movieview.widget.RatioImageView;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.support.v4.content.ContextCompat.startActivity;
import static com.jp.movieview.R.id.content_text;

/**
 * Created by jp on 2017/4/5.
 */
public class AllComicsAdapter2 extends BaseQuickAdapter<ComicBean, BaseViewHolder> {
    public static final String TAG = "AllComicsAdapter2";

    public AllComicsAdapter2(List<ComicBean> data) {
        super(R.layout.comics_item, data);
        LogUtils.e(TAG,"构造1");
    }

    public AllComicsAdapter2() {
        super(R.layout.comics_item);
        LogUtils.e(TAG,"构造2 ");
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ComicBean item) {
        LogUtils.e(TAG,"开始处理 ");
        ImageView imageView = helper.getView(R.id.safe_img);
        TextView textView=helper.getView(R.id.content_text);
        //TODO 跑马灯未实现
        helper.setText(R.id.content_text,item.getTitle());
        //Glide.with(mContext).load(item.getSrc_url()).placeholder(R.drawable.white_bg).into(imageView);


        ImageLoader.load(mContext,item.getSrc_url(),imageView);


        helper.getView(R.id.card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, ComicInfoActivity.class);

//                Bitmap bm = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                Bitmap bmp = ((GlideBitmapDrawable)imageView.getDrawable().getCurrent()).getBitmap();
                ByteArrayOutputStream baos=new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte [] bitmapByte =baos.toByteArray();
                intent.putExtra("bitmap", bitmapByte);



                Pair<View,String> pair=Pair.create(imageView, "profile");
                intent.putExtra("url",item.getSrc_url());
                intent.putExtra("href_url",item.getHref_url());

                ActivityOptionsCompat options =
                        ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext,
                                pair);

                mContext.startActivity(intent, options.toBundle());

//                (Activity)mContext.startActivity(intent, ActivityOptionsCompat
//                        .makeSceneTransitionAnimation((Activity) mContext,
//                                Pair.create(imageView, "shareView_img"), Pair.create(textView, "shareView_name"))
//                        .toBundle());
            }
        });


        LogUtils.e(TAG,"name4"+item);

    }
}
