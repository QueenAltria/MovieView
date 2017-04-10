package com.jp.movieview.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jp.movieview.R;
import com.jp.movieview.adapter.YandeAdapter;
import com.jp.movieview.bean.YandeBean;
import com.jp.movieview.callback.JsonCallBack;
import com.jp.movieview.utils.LogUtils;
import com.lzy.okgo.OkGo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static android.nfc.tech.MifareUltralight.PAGE_SIZE;

public class YandeActivity extends AppCompatActivity implements BaseQuickAdapter.RequestLoadMoreListener{
    public static final String TAG="SafebooruActivity";

    RecyclerView mRecyclerView;
    YandeAdapter adapter;
    List<YandeBean> list=new ArrayList<>();
    int page=1;
    boolean isErr=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safebooru);

        mRecyclerView= (RecyclerView) findViewById(R.id.safe_recycler);
        adapter=new YandeAdapter();


        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        final StaggeredGridLayoutManager manager=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        LinearLayoutManager manager1=new LinearLayoutManager(this);
        //manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRecyclerView.setLayoutManager(manager);
        adapter.setOnLoadMoreListener(this,mRecyclerView);
        //adapter=new MainAdapter(list);
        adapter.setNewData(list);
        //adapter.openLoadAnimation(new RecyclerAnimation());
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        mRecyclerView.setAdapter(adapter);
//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//
//                super.onScrollStateChanged(recyclerView, newState);
//                manager.invalidateSpanAssignments();
//
//            }
//        });
        adapter.setEnableLoadMore(true);

        OkGo.get("https://yande.re/post?page="+page)
                .tag(this)
                .execute(new JsonCallBack<String>(YandeActivity.this) {
                    @Override
                    public void onSuccess(String result, Call call, Response response) {
                        Document doc = Jsoup.parse(result);
                        //Elements company = doc.select("a[href]");
                        Elements select = doc.select("div.inner");
                        Elements sizes = doc.select("span.directlink-res");
                        for (int i=0;i<select.size();i++){
                            // 跳转链接
                            String src_url = select.get(i).select("a").attr("href");
                            //  预览图
                            String preview_url = select.get(i).select("img").attr("src");
                            // UserName
                            String tags = select.get(i).select("img").attr("title");
                            int user = tags.indexOf("User");
                            String userName=tags.substring(user+5,tags.length());
                            // 尺寸
                            String size=sizes.get(i).text();
                            LogUtils.e(TAG,"----->"+size);

                            YandeBean safebooruBean=new YandeBean();
                            safebooruBean.setPreview_url(preview_url);
                            safebooruBean.setSize(size);
                            safebooruBean.setName(userName);
                            safebooruBean.setSrc_url(src_url);

                            list.add(safebooruBean);

                        }
                        adapter.notifyDataSetChanged();

                    }
                });


    }

    @Override
    public void onLoadMoreRequested() {

        if (adapter.getData().size() < 1) {
            adapter.loadMoreEnd(false);

        } else {
            if (1>2) {

//                    pullToRefreshAdapter.loadMoreEnd();//default visible
                adapter.loadMoreEnd(false);//true is gone,false is visible
            } else {
                if (isErr) {
                    page+=1;
                    final List<YandeBean> addList=new ArrayList<>();
                    OkGo.get("https://yande.re/post?page="+(page+2))
                            .tag(this)
                            .execute(new JsonCallBack<String>(YandeActivity.this) {
                                @Override
                                public void onSuccess(String result, Call call, Response response) {
                                    Document doc = Jsoup.parse(result);
                                    //Elements company = doc.select("a[href]");
                                    Elements select = doc.select("div.inner");
                                    Elements sizes = doc.select("span.directlink-res");
                                    for (int i=0;i<select.size();i++){
                                        // 跳转链接
                                        String src_url = select.get(i).select("a").attr("href");
                                        //  预览图
                                        String preview_url = select.get(i).select("img").attr("src");
                                        // UserName
                                        String tags = select.get(i).select("img").attr("title");
                                        int user = tags.indexOf("User");
                                        String userName=tags.substring(user+5,tags.length());
                                        // 尺寸
                                        String size=sizes.get(i).text();
                                        LogUtils.e(TAG,"----->"+size);

                                        YandeBean safebooruBean=new YandeBean();
                                        safebooruBean.setPreview_url(preview_url);
                                        safebooruBean.setSize(size);
                                        safebooruBean.setName(userName);
                                        safebooruBean.setSrc_url(src_url);

                                        addList.add(safebooruBean);

                                    }
                                    adapter.addData(addList);
                                    adapter.notifyDataSetChanged();
                                    adapter.loadMoreComplete();

                                }
                            });
                } else {
                    isErr = true;
                    Toast.makeText(YandeActivity.this, "请求失败", Toast.LENGTH_LONG).show();
                    adapter.loadMoreFail();

                }
            }
        }



    }
}
