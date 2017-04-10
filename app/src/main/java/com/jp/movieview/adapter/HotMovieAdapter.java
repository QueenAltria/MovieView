package com.jp.movieview.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jp.movieview.R;
import com.jp.movieview.bean.HotMovie;
import com.jp.movieview.utils.ToastUtils;

import java.util.List;

/**
 * Created by jp on 2017/4/5.
 */
public class HotMovieAdapter extends BaseQuickAdapter<HotMovie, BaseViewHolder> {
    public static final String TAG = "HotMovieAdapter";

    public HotMovieAdapter(List<HotMovie> data) {
        super(R.layout.hotmovie_item, data);
    }

    public HotMovieAdapter() {
        super(R.layout.hotmovie_item);
    }

    @Override
    protected void convert(final BaseViewHolder helper, HotMovie item) {
        helper.setText(R.id.name, item.getName());

        helper.getView(R.id.card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showToast(mContext,helper.getLayoutPosition()+"");
            }
        });

        Glide.with(mContext).load(item.getImg_url()).into((ImageView)helper.getView(R.id.movie_img));
    }
}
