package com.jp.movieview.view;

import com.jp.movieview.bean.YandeBean;

import java.util.List;

import static android.R.attr.data;

/**
 * Created by jp on 2017/4/13
 */
public interface YandeItemView extends IBaseView{
    void onSuccess(int type,List<YandeBean> data);
}
