package com.jp.movieview.utils;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

import com.chad.library.adapter.base.animation.BaseAnimation;

/**
 * Created by jp on 2016/12/8.
 */
public class RecyclerAnimation implements BaseAnimation{
    public static final String TAG = "RecyclerAnimation";

    @Override
    public Animator[] getAnimators(View view) {
//        return new Animator[]{
//                ObjectAnimator.ofFloat(view, "scaleY", 1, 1.1f, 1),
//                ObjectAnimator.ofFloat(view, "scaleX", 1, 1.1f, 1)
//        };
        return new Animator[]{};
    }
}
