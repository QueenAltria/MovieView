package com.jp.movieview.ui.activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.jp.movieview.R;
import com.jp.movieview.utils.SPUtil;
import com.jp.movieview.utils.customtabs.CustomTabActivityHelper;

import java.util.concurrent.TimeUnit;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class SplashActivity extends Activity {
    Subscription subscribe;

    @BindView(R.id.activity_splash)
    RelativeLayout mRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        subscribe = Observable.timer(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    Boolean isFirst = (Boolean) SPUtil.get("isfirst", true);
                    if (isFirst) {
                        Intent intent = new Intent(this, GuideActivity.class);
                        SPUtil.save("isfirst", false);
                        startActivity(intent);
                        finish();
                    } else {
//                        Intent intent = new Intent(this, YandeActivity.class);
//                        startActivity(intent);
//                        finish();
                        startActivity(new Intent(SplashActivity.this, AboutActivity.class),
                                ActivityOptions.makeSceneTransitionAnimation(this).toBundle());

                    }
                });


        mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SplashActivity.this, AboutActivity.class),
                        ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this).toBundle());

                CustomTabActivityHelper.openCustomTab(
                        SplashActivity.this,
                        new CustomTabsIntent.Builder()
                                .setToolbarColor(ContextCompat.getColor(SplashActivity.this, R.color.colorAccent))
                                .addDefaultShareMenuItem()
                                .build(),
                        Uri.parse("file:///android_asset/index.html"));
            }
        });

       // file:///android_asset/index.html   https://github.com/QueenAltria
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscribe.unsubscribe();
    }


    @Override
    public void finish() {
        super.finish();
        //TODO 从全屏切换到非全屏  存在卡顿问题
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
    }
}
