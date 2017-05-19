/*
 * Copyright 2015 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jp.movieview.ui.activity;

import android.app.Activity;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AlignmentSpan;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.jp.movieview.R;
import com.jp.movieview.utils.LogUtils;
import com.jp.movieview.widget.CircleTransform;
import com.jp.movieview.widget.ElasticDragDismissFrameLayout;
import com.jp.movieview.widget.InkPageIndicator;

import java.security.InvalidParameterException;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * About screen. This displays 3 pages in a ViewPager:
 * – About Plaid
 * – Credit Roman for the awesome icon
 * – Credit libraries
 */
public class AboutActivity extends Activity {

    @BindView(R.id.draggable_frame)
    ElasticDragDismissFrameLayout draggableFrame;
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.indicator2)
    InkPageIndicator pageIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        //getWindow().setBackgroundDrawable(null);

        pager.setAdapter(new AboutPagerAdapter(AboutActivity.this));
        pager.setPageMargin(9);
        pageIndicator.setViewPager(pager);


        draggableFrame.addListener(
                new ElasticDragDismissFrameLayout.SystemChromeFader(this) {
                    @Override
                    public void onDragDismissed() {
                        // if we drag dismiss downward then the default reversal of the enter
                        // transition would slide content upward which looks weird. So reverse it.
                        Log.e("jp", draggableFrame.getTranslationY() + "Y距离");
                        if (draggableFrame.getTranslationY() > 0) {
                            getWindow().setReturnTransition(
                                    TransitionInflater.from(AboutActivity.this)
                                            .inflateTransition(R.transition.about_return_downward));
                        }
                        finishAfterTransition();
                    }
                });
    }

    static class AboutPagerAdapter extends PagerAdapter {

        private View aboutPlaid;

        private View aboutIcon;

        private View aboutLibs;


        private final LayoutInflater layoutInflater;

        private final Activity host;
        private final Resources resources;

        AboutPagerAdapter(@NonNull Activity host) {
            this.host = host;
            resources = host.getResources();
            layoutInflater = LayoutInflater.from(host);

        }

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
            View layout = getPage(position, collection);
            collection.addView(layout);
            return layout;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        private View getPage(int position, ViewGroup parent) {
            switch (position) {
                case 0:
                    if (aboutPlaid == null) {
                        aboutPlaid = layoutInflater.inflate(R.layout.activity_tag, null);


                        WebView mWebView = (WebView) aboutPlaid.findViewById(R.id.testweb);
                        ScrollView mScrollView = (ScrollView) aboutPlaid.findViewById(R.id.scroll);


                        mWebView.loadUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495589946&di=ad29d35984f2797ada93bc8112ee8b02&imgtype=jpg&er=1&src=http%3A%2F%2Fpic.90sjimg.com%2Fback_pic%2Fqk%2Fback_origin_pic%2F00%2F03%2F17%2Fab86e8375d26a35c49aa0427c848f105.jpg");
                        //mWebView.loadUrl("file:///android_asset/index.html");

                        //mWebView.setBackgroundColor(ContextCompat.getColor(AboutActivity.this,R.color.colorTrans));
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
                                ViewGroup viewGroup = (ViewGroup) view.getParent();
                                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                                    LogUtils.e("jp", "事件1");
                                    viewGroup.requestDisallowInterceptTouchEvent(true);
                                } else {
                                    LogUtils.e("jp", "事件2");
                                    viewGroup.requestDisallowInterceptTouchEvent(false);
                                }
                                return false;
                            }
                        });


                    }
                    return aboutPlaid;
                case 1:
                    if (aboutIcon == null) {
                        aboutIcon = layoutInflater.inflate(R.layout.activity_tag, parent, false);

                    }
                    return aboutIcon;
                case 2:
                    if (aboutLibs == null) {
                        aboutLibs = layoutInflater.inflate(R.layout.activity_tag, parent, false);

                    }
                    return aboutLibs;
            }
            throw new InvalidParameterException();
        }
    }


}
