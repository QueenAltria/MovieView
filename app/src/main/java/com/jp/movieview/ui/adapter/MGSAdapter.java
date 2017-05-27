package com.jp.movieview.ui.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jp.movieview.R;
import com.jp.movieview.bean.MySection;
import com.jp.movieview.model.MgsData;
import com.jp.movieview.model.MgsSection;
import com.jp.movieview.utils.LogUtils;
import com.jp.movieview.widget.RatioImageView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jp on 2017/4/5.
 */
public class MGSAdapter extends BaseSectionQuickAdapter<MgsSection, BaseViewHolder> {
    public  final String TAG =getClass().getName();

    public MGSAdapter(List<MgsSection> data) {
        super(R.layout.mgs_main_item, R.layout.def_section_head, data);
    }

    public MGSAdapter(int layoutResId, int sectionHeadResId, List<MgsSection> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, MgsSection item) {
        helper.setText(R.id.header,item.header);
        if(item.isMore()){
            helper.getView(R.id.more).setVisibility(View.VISIBLE);
        }else {
            helper.getView(R.id.more).setVisibility(View.GONE);

        }
    }

    @Override
    protected void convert(BaseViewHolder helper, MgsSection item) {
        RatioImageView view = helper.getView(R.id.preview_img);
        //view.setOriginalSize(ViewGroup.LayoutParams.MATCH_PARENT, 186);

        Glide.with(mContext).load(item.t.getImg_url()).placeholder(R.drawable.white_bg).into(view);
        //Glide.with(mContext).load(R.mipmap.ic_launcher2).placeholder(R.drawable.white_bg).into(view);
    }
}
