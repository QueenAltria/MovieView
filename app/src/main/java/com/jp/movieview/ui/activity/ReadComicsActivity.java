package com.jp.movieview.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.jp.movieview.R;
import com.jp.movieview.callback.JsonCallBack;
import com.jp.movieview.ui.adapter.ReadComicsAdapter;
import com.jp.movieview.ui.fragment.IndexCoimcsFragment;
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

import static android.media.CamcorderProfile.get;

public class ReadComicsActivity extends AppCompatActivity {
    public static final String TAG="ReadComicsActivity";
    RecyclerView mRecyclerView;
    TextView mTextView;

    String url;
    ReadComicsAdapter adapter;

    String attr;
    int allPages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_comics);

        mRecyclerView= (RecyclerView) findViewById(R.id.recycler);
        mTextView= (TextView) findViewById(R.id.page);

        url=getIntent().getStringExtra("url");
        adapter=new ReadComicsAdapter();

        List<String> list=new ArrayList<>();

        HttpUrl httpUrl = HttpUrl.parse("http://www.2animx.com");
        Cookie.Builder builder = new Cookie.Builder();
        Cookie cookie = builder.name("isAdult").value("1").domain(httpUrl.host()).build();
        CookieStore cookieStore = OkGo.getInstance().getCookieJar().getCookieStore();
        cookieStore.saveCookie(httpUrl, cookie);
        OkGo.getInstance().setCookieStore(cookieStore);




        OkGo.get(url)
                .tag(this)
                .execute(new JsonCallBack<String>(this) {
                    @Override
                    public void onSuccess(String result, Call call, Response response) {
                        //LogUtils.e(TAG,result.toString());
                        Document doc = Jsoup.parse(result);
                        Elements li = doc.select("[id^=img_ad_img]");
                        Elements pages = doc.select("span.lookpage").select("a");
                        Elements before = doc.select("div.c").select("a.p").select("a.zhangjie");
                        String beforeZhang=before.select("a").attr("href");
                        LogUtils.e(TAG,"before"+beforeZhang);
                        Elements after = doc.select("div.c").select("a.n").select("a.zhangjie");
                        String afterZhang=after.select("a").attr("href");
                        LogUtils.e(TAG,"after"+afterZhang);


                        for (int i=0;i<pages.size();i++){
                            String page=pages.get(i).text();
                            LogUtils.e(TAG,"page:"+page);
                            allPages=Integer.parseInt(page);
                        }

                        for (int i=0;i<li.size();i++){
                            attr = li.get(i).select("img").attr("src");


                            //LogUtils.e(TAG,attr);


                        }

                        for (int i=1;i<(allPages+1);i++){

                            String end=attr.substring(0,attr.length()-5)+i+attr.substring(attr.length()-4,attr.length());
                            list.add(end);
                            LogUtils.e(TAG,end);
                        }

                        mTextView.setText("1/"+allPages);


                        PagerSnapHelper snapHelper = new PagerSnapHelper();
                        snapHelper.attachToRecyclerView(mRecyclerView);



                        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        final LinearLayoutManager manager = new LinearLayoutManager(ReadComicsActivity.this);
                        //manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
                        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                        manager.setItemPrefetchEnabled(true);
                        manager.setInitialPrefetchItemCount(3);

                        mRecyclerView.setItemViewCacheSize(3);
                        mRecyclerView.setLayoutManager(manager);
                        //mRecyclerView.setHorizontalScrollBarEnabled(true);
                        //adapter.setOnLoadMoreListener(this,mRecyclerView);
                        //adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);

                        adapter.setNewData(list);
                        mRecyclerView.setAdapter(adapter);

                       // mTextView.setText((manager.findLastVisibleItemPosition()-manager.findFirstVisibleItemPosition())+"");

                        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                            //用来标记是否正在向最后一个滑动
                            boolean isSlidingToLast = false;
                            @Override
                            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                super.onScrolled(recyclerView, dx, dy);
                                //dx用来判断横向滑动方向，dy用来判断纵向滑动方向

                                if (dx > 0) {
                                    //大于0表示正在向右滚动
                                    isSlidingToLast = true;
                                } else {
                                    //小于等于0表示停止或向左滚动
                                    isSlidingToLast = false;
                                }
                            }

                            @Override
                            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                                super.onScrollStateChanged(recyclerView, newState);
                                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                                // 当不滚动时
                                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                                    //获取最后一个完全显示的ItemPosition
                                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                                    int totalItemCount = manager.getItemCount();

                                    mTextView.setText((lastVisibleItem+1)+"/"+allPages);

                                    // 判断是否滚动到底部，并且是向右滚动
                                    if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast) {
                                        //加载更多功能的代码
                                    }
                                }

                            }
                        });



                        adapter.setCallBack(new ReadComicsAdapter.PositionCallBack() {
                            @Override
                            public void getPosition(int position) {
                                //mTextView.setText(position+"--");
                            }
                        });

                    }
                });

    }
}
