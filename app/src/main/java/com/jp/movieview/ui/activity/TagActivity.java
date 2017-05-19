package com.jp.movieview.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.TransitionInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.widget.ScrollView;

import com.jp.movieview.R;
import com.jp.movieview.utils.LogUtils;
import com.jp.movieview.widget.ElasticDragDismissFrameLayout;
import com.jp.movieview.widget.SupportZoomWebView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TagActivity extends Activity {
    public String TAG=getClass().getName();

    @BindView(R.id.testweb)
    SupportZoomWebView mWebView;

    @BindView(R.id.scroll)
    ScrollView mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
//        getWindow().setEnterTransition(new Fade());
//        getWindow().setExitTransition(new Fade());
        setContentView(R.layout.activity_tag);
        ButterKnife.bind(this);

        LogUtils.e(TAG,"here");

        mWebView.loadUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495589946&di=ad29d35984f2797ada93bc8112ee8b02&imgtype=jpg&er=1&src=http%3A%2F%2Fpic.90sjimg.com%2Fback_pic%2Fqk%2Fback_origin_pic%2F00%2F03%2F17%2Fab86e8375d26a35c49aa0427c848f105.jpg");
        //mWebView.loadUrl("file:///android_asset/index.html");
        mWebView.setFocusable(false);
        mWebView.setClickable(false);
        mWebView.setBackgroundColor(ContextCompat.getColor(this,R.color.colorTrans));
        mWebView.setVerticalScrollBarEnabled(false); //垂直不显示
        mWebView.setHorizontalScrollBarEnabled(false);
        //mWebView.setVisibility(View.GONE);
        WebSettings settings = mWebView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(false);//显示放大缩小 controler
        settings.setSupportZoom(false);

        mWebView.setDrawingCacheEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        mWebView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP)
                    mScrollView.requestDisallowInterceptTouchEvent(false);
                else
                    mScrollView.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

//        mActivityTag.addListener(
//                new ElasticDragDismissFrameLayout.SystemChromeFader(this) {
//                    @Override
//                    public void onDragDismissed() {
//                        // if we drag dismiss downward then the default reversal of the enter
//                        // transition would slide content upward which looks weird. So reverse it.
//                        LogUtils.e(TAG,mActivityTag.getTranslationY()+"Y");
//                        if (mActivityTag.getTranslationY() > 0) {
//                            getWindow().setReturnTransition(
//                                    TransitionInflater.from(TagActivity.this)
//                                            .inflateTransition(R.transition.about_return_downward));
//                        }
//                        finishAfterTransition();
//                    }
//
//                    @Override
//                    public void onDrag(float elasticOffset, float elasticOffsetPixels, float rawOffset, float rawOffsetPixels) {
//                        super.onDrag(elasticOffset, elasticOffsetPixels, rawOffset, rawOffsetPixels);
//                        LogUtils.e(TAG,elasticOffset+"elasticOffset");
//                    }
//                });
    }
}
