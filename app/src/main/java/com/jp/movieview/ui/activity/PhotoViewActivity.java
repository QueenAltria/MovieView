package com.jp.movieview.ui.activity;

import android.graphics.drawable.Animatable;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.TranslateAnimation;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import com.jp.movieview.R;

import com.jp.movieview.service.DownloadService;
import com.jp.movieview.utils.DensityUtils;
import com.jp.movieview.utils.LogUtils;
import com.jp.movieview.utils.ToastUtils;
import com.jp.movieview.widget.SupportZoomWebView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import uk.co.senab.photoview.PhotoView;

import static com.jp.movieview.R.id.url;


public class PhotoViewActivity extends BaseMvpActivity {
    public final String TAG = getClass().getName();

    PhotoView mPhotoView;
    String filePath;
    Toolbar mToolbar;
    int flag;
    Transition mTransition;
    ArrayList<String> tags;

    ProgressBar progress;
    RelativeLayout layout;

    @BindView(R.id.web)
    SupportZoomWebView mWebView;


    String url;



    @Override
    protected void fetchData() {

    }

    @Override
    protected int initLayoutId() {
        flag=2;
        if(flag==0){
            mTransition=new Explode();
        }else if(flag==1){
            mTransition=new Slide();
        }else if(flag==2){
            mTransition=new Fade();
        }
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(mTransition);
        getWindow().setExitTransition(mTransition);
        return R.layout.activity_photo_view;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        mPhotoView = (PhotoView) findViewById(R.id.photo);
        mToolbar= (Toolbar) findViewById(R.id.toolbar);

        layout= (RelativeLayout) findViewById(R.id.activity_photo_view);


        progress=new ProgressBar(PhotoViewActivity.this,null,android.R.attr.progressBarStyleSmallInverse);

        progress.setVisibility(View.VISIBLE);
        progress.setScrollBarSize(DensityUtils.dip2px(50));

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);


        layout.addView(progress,params);
    }

    @Override
    protected void initData() {
        url = getIntent().getStringExtra("url");
        String id = getIntent().getStringExtra("id");
        tags=getIntent().getStringArrayListExtra("tags");
        filePath=getIntent().getStringExtra("id");
        mToolbar.setTitle("Yande:"+id);

        mToolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.colorWhite));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        mToolbar.setNavigationOnClickListener(view -> onBackPressed());

        mWebView.loadUrl(url);
        mWebView.setBackgroundColor(ContextCompat.getColor(this,R.color.colorTrans));
        //mWebView.setVisibility(View.GONE);
        WebSettings settings = mWebView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);//显示放大缩小 controler
        settings.setSupportZoom(true);


        if(check(id)){
            File file=new File(Environment.getExternalStorageDirectory().getPath() + "/AView/Yande/"+id+".jpg");
            ToastUtils.showToast(this,"开始了1");
            Glide.with(this)
                    .load(file)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)//让Glide既缓存全尺寸图片，下次在任何ImageView中加载图片的时候，全尺寸的图片将从缓存中取出，重新调整大小，然后缓存
                    .crossFade()
                    //.listener(listener1)
                    .into(mPhotoView);
        }else {
            File file=new File(Environment.getExternalStorageDirectory().getPath() + "/AView/Yande/");
            if(file.exists()){
                ToastUtils.showToast(this,"开始了2");
                DownloadService.startService(this,url,id);
            }else {
                ToastUtils.showToast(this,"开始了3");
                try {
                    file.mkdirs();
                    DownloadService.startService(this,url,id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        //ToastUtils.showToast(this, url);

        RequestListener<String, GlideDrawable> listener = new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                Log.e(TAG, "onException: " + e.toString() + "  model:" + model + " isFirstResource: " + isFirstResource);
                LogUtils.e(TAG,"exception"+e.getLocalizedMessage());
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                Log.e(TAG, "isFromMemoryCache:" + isFromMemoryCache + "  model:" + model + " isFirstResource: " + isFirstResource);
                return false;
            }
        };





        RequestListener<GlideUrl, GlideDrawable> listener1 = new RequestListener<GlideUrl, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, GlideUrl model, Target<GlideDrawable> target, boolean isFirstResource) {
                Log.e(TAG, "onException: " + e.toString() + "  model:" + model + " isFirstResource: " + isFirstResource);
                LogUtils.e(TAG, "Exception" + e.getLocalizedMessage());
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, GlideUrl model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                Log.e(TAG, "isFromMemoryCache:" + isFromMemoryCache + "  model:" + model + " isFirstResource: " + isFirstResource);
                return false;
            }
        };

        GlideUrl glideUrl = new GlideUrl(url.replace("https","http"), new LazyHeaders.Builder()
//                .addHeader("yande.re", "ZUE2Wm4wQURnbmZFRitrZ2twOFNtbTZxblpTU0g0citJVjQzdDY3RUdNMWNIQWd4ajFONzM2Uk84N1BybWg0VndhN25TaGMyK3ZMUGtpejIxZmZtTDFsa1RIb2lrSkk0WHpKM0ZYa1E3Q1ZreGR4TlVxZ0dHYkVjUDlpV0NPYkRsS1pGWHFIQldNQWdPNGlzUDlaM2lraHVEWWozeHBJZElGaUdWSzVHa2R3VzdaaERzSzVQdHNVSHJ2NkxWcnZILS1xdXZWb09laXJudGQ3bzJCdnBXMFV3PT0")
//                .addHeader("forum_post_last_read_at", "%222017-04-14T04%3A50%3A34.103%2B02%3A00%22")
//                .addHeader("country", "CN")
//                .addHeader("Cookie", "ZUE2Wm4wQURnbmZFRitrZ2twOFNtbTZxblpTU0g0citJVjQzdDY3RUdNMWNIQWd4ajFONzM2Uk84N1BybWg0VndhN25TaGMyK3ZMUGtpejIxZmZtTDFsa1RIb2lrSkk0WHpKM0ZYa1E3Q1ZreGR4TlVxZ0dHYkVjUDlpV0NPYkRsS1pGWHFIQldNQWdPNGlzUDlaM2lraHVEWWozeHBJZElGaUdWSzVHa2R3VzdaaERzSzVQdHNVSHJ2NkxWcnZILS1xdXZWb09laXJudGQ3bzJCdnBXMFV3PT0")
//                .addHeader("Cookie", "%222017-04-14T04%3A50%3A34.103%2B02%3A00%22")
//                .addHeader("Cookie", "CN")
//                .addHeader("Referer", "https://files.yande.re")
                //.addHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36")

               .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
        .addHeader("Accept-Language", "zh-CN,zh;q=0.8")
        //.addHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.0.3) Gecko/2008092417 Firefox/3.0.3")
        .addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36")
                .addHeader("Cookie", "__utma=5621947.1557461779.1488939097.1492137131.1492160002.19; __utmz=5621947.1491811948.2.2.utmcsr=google|utmccn=(organic)|utmcmd=organic|utmctr=(not%20provided)")
        .addHeader("Keep-Alive", "300")
        .addHeader("Connection", "Keep-Alive")
        .addHeader("Cache-Control", "max-age=0")
                .addHeader("Host","files.yande.re")
                .addHeader("If-None-Match","59126425-8baba")
                .addHeader("Upgrade-Insecure-Requests","1")



        .build());



//        Glide.with(this)
//                .load(glideUrl)
//                .diskCacheStrategy(DiskCacheStrategy.RESULT)//让Glide既缓存全尺寸图片，下次在任何ImageView中加载图片的时候，全尺寸的图片将从缓存中取出，重新调整大小，然后缓存
//                .crossFade()
//                .listener(listener1)
//                .into(mPhotoView);
    }

    public boolean check(String srcPath){
        File file=new File(Environment.getExternalStorageDirectory().getPath() + "/AView/Yande/"+srcPath+".jpg");
        if(file.exists()){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.somemenu, menu);
        for (int i=0;i<tags.size();i++){
            menu.add(tags.get(i));
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ToastUtils.showToast(this,item.getTitle().toString());
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(String backStr){
        if(backStr.equals("ok")){
            File file=new File(Environment.getExternalStorageDirectory().getPath() + "/AView/Yande/"+filePath+".jpg");

            Glide.with(this)
                    .load(file)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)//让Glide既缓存全尺寸图片，下次在任何ImageView中加载图片的时候，全尺寸的图片将从缓存中取出，重新调整大小，然后缓存
                    .crossFade()
                    //.listener(listener1)
                    .into(mPhotoView);
        }
    }
}
