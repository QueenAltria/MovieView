package com.jp.movieview.widget;

/**
 * Created by jp on 2017/4/21
 */

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Alex on 2016/9/18.
 * 用于预加载recyclerView下一张卡的layoutmanager
 */
public class AlxPreloadLinearManager extends LinearLayoutManager {
    private static final int DEFAULT_EXTRA_LAYOUT_SPACE = 1280;

    private int extraLayoutSpace = DEFAULT_EXTRA_LAYOUT_SPACE;

    public AlxPreloadLinearManager(Context context) {
        super(context);
    }

    public AlxPreloadLinearManager(Context context, int extraLayoutSpace) {
        super(context);
        this.extraLayoutSpace = extraLayoutSpace;
    }

    public AlxPreloadLinearManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public void setExtraLayoutSpace(int extraLayoutSpace) {
        this.extraLayoutSpace = extraLayoutSpace;
    }

    @Override
    protected int getExtraLayoutSpace(RecyclerView.State state) {
        if (extraLayoutSpace > 0) {
            return extraLayoutSpace;
        }
        return DEFAULT_EXTRA_LAYOUT_SPACE;
    }
}