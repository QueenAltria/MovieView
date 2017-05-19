package com.jp.movieview.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * Created by jp on 2017/5/17
 */
public class MyScroll extends LinearLayout{
    public static final String TAG = "MyScroll";

    public MyScroll(Context context) {
        super(context);
    }

    public MyScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScroll(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(ev.getActionMasked() == MotionEvent.ACTION_MOVE) {
            return true;
        }
        return super.onInterceptTouchEvent(ev);

    }
}
