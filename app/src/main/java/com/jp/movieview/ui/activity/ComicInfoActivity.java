package com.jp.movieview.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jp.movieview.R;
import com.jp.movieview.bean.CatalogBean;
import com.jp.movieview.callback.JsonCallBack;
import com.jp.movieview.rx.RxBus;
import com.jp.movieview.ui.adapter.CatalogAdapter;
import com.jp.movieview.utils.LogUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cookie.store.CookieStore;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.HttpUrl;
import okhttp3.Response;

import static android.R.id.list;
import static com.jp.movieview.R.id.imageView;

public class ComicInfoActivity extends AppCompatActivity {
    public static final String TAG="ComicInfoActivity";

    ImageView mImageView;
    RelativeLayout rl;
    TextView mTitle;

    String href_url;


    RecyclerView mRecyclerView;
    CatalogAdapter adapter;
    List<CatalogBean> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_info);
        mImageView = (ImageView) findViewById(R.id.srcimg);
        rl = (RelativeLayout) findViewById(R.id.relative1);
        mTitle= (TextView) findViewById(R.id.title);
        mRecyclerView= (RecyclerView) findViewById(R.id.catalog);


        href_url = getIntent().getStringExtra("href_url");
        mTitle.setText(href_url);

        // Glide.with(this).load(getIntent().getStringExtra("url")).into(mImageView);

        byte[] bis = getIntent().getByteArrayExtra("bitmap");
        Bitmap bitmap = BitmapFactory.decodeByteArray(bis, 0, bis.length);

        mImageView.setImageBitmap(bitmap);



        adapter=new CatalogAdapter();

        HttpUrl httpUrl = HttpUrl.parse("http://www.2animx.com");
        Cookie.Builder builder = new Cookie.Builder();
        Cookie cookie = builder.name("isAdult").value("1").domain(httpUrl.host()).build();
        CookieStore cookieStore = OkGo.getInstance().getCookieJar().getCookieStore();
        cookieStore.saveCookie(httpUrl, cookie);
        OkGo.getInstance().setCookieStore(cookieStore);


        OkGo.get(href_url)
                .tag(this)
                .execute(new JsonCallBack<String>(this) {
                    @Override
                    public void onSuccess(String result, Call call, Response response) {
                        Document doc = Jsoup.parse(result);
                        //2 倒序  1 正序
                        Elements select = doc.select("div.vol").select("[id^=oneCon2]");
                        Elements li = select.select("li");
                        for (int i=0;i<li.size();i++){
                            String attr = li.get(i).select("a").attr("title");

                            String text = li.get(i).text();

                            String href=li.get(i).select("a").attr("href");

                            LogUtils.e(TAG,text);

                            CatalogBean bean=new CatalogBean();
                            bean.setTitle(text);
                            bean.setUrl(href);
                            list.add(bean);




                        }

                        RxBus.getDefault().post("我接收到消息了");

                        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        final GridLayoutManager manager = new GridLayoutManager(ComicInfoActivity.this,3);
                        //manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
                       mRecyclerView.setNestedScrollingEnabled(false);
                        mRecyclerView.setLayoutManager(manager);
                        //mRecyclerView.setHorizontalScrollBarEnabled(true);
                        //adapter.setOnLoadMoreListener(this,mRecyclerView);
                        //adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);

                        adapter.setNewData(list);
                        mRecyclerView.setAdapter(adapter);
                    }
                });


        Transition transition = getWindow().getSharedElementEnterTransition();
        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                performCircleReveal();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });





        //Glide.with(this).load(bitmap).into(mImageView);


    }

    private void performCircleReveal() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final float finalRadius = (float) Math.hypot(rl.getWidth(), rl.getHeight());
            Animator anim = ViewAnimationUtils.createCircularReveal(
                    rl,
                    (mImageView.getLeft() + mImageView.getRight()) / 2,
                    (mImageView.getTop() + mImageView.getBottom()) / 2,
                    (float) mImageView.getWidth() / 2,
                    finalRadius);
            anim.setDuration(300);
            anim.setInterpolator(new AccelerateDecelerateInterpolator());
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    Log.e("---", "start anim");
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    Log.e("---", "end anim");
                    //show views
                }
            });
            anim.start();
        }
    }
}
