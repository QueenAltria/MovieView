package com.jp.movieview.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jp.movieview.R;
import com.jp.movieview.utils.SPUtil;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class SplashActivity extends AppCompatActivity {
    Subscription subscribe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

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
                        Intent intent = new Intent(this, YandeActivity.class);
                        startActivity(intent);
                        finish();

                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscribe.unsubscribe();
    }
}
