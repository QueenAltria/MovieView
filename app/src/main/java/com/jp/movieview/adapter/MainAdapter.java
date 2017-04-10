package com.jp.movieview.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jp.movieview.R;
import com.jp.movieview.bean.MovieBean;
import com.jp.movieview.utils.ToastUtils;

import java.util.List;

/**
 * Created by jp on 2017/4/5.
 */
public class MainAdapter extends BaseQuickAdapter<MovieBean, BaseViewHolder> {
    public static final String TAG = "MainAdapter";

    public MainAdapter(List<MovieBean> data) {
        super(R.layout.movie_item, data);
    }

    public MainAdapter() {
        super(R.layout.movie_item);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final MovieBean item) {
        helper.setText(R.id.name, item.getMovieName())
                .setText(R.id.url, item.getMovieUrl())
                .setText(R.id.size,"大小:"+item.getMovieSize())
                .setText(R.id.hot,"访问热度:"+item.getMovieHot())
                .setText(R.id.time,"创建日期:"+item.getMovieTime())
                .setText(R.id.num,"文件数:"+item.getMovieNum());

        helper.getView(R.id.card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ToastUtils.showToast(mContext,helper.getLayoutPosition()+"");
                ToastUtils.showToast(mContext,item.getMovieMagnet());
            }
        });
    }

}
