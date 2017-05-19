package com.jp.movieview.ui.adapter;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jp.movieview.R;
import com.jp.movieview.bean.CatalogBean;
import com.jp.movieview.ui.activity.ReadComicsActivity;

import java.util.List;

import static android.R.attr.data;

/**
 * Created by jp on 2017/4/21
 */
public class CatalogAdapter extends BaseQuickAdapter<CatalogBean,BaseViewHolder> {
    public static final String TAG = "CatalogAdapter";

    public CatalogAdapter(List<CatalogBean> data) {
        super(R.layout.catalog_item, data);
    }

    public CatalogAdapter() {
        super(R.layout.catalog_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, CatalogBean item) {
        helper.setText(R.id.title,item.getTitle());
        helper.getView(R.id.title).setOnClickListener(view -> {
            Intent intent=new Intent(mContext, ReadComicsActivity.class);
            intent.putExtra("url",item.getUrl());
            mContext.startActivity(intent);
        });
    }
}
