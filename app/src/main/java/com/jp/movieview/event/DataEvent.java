package com.jp.movieview.event;

import com.jp.movieview.bean.YandeBean;

import java.util.List;

/**
 * Created by jp on 2017/4/11.
 */
public class DataEvent {
    public static final String TAG = "DataEvent";
    List<YandeBean> mList;

    public List<YandeBean> getList() {
        return mList;
    }

    public void setList(List<YandeBean> list) {
        mList = list;
    }

    public DataEvent(List<YandeBean> list) {
        mList = list;
    }
}
