package com.jp.movieview.ui.fragment;


import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jp.movieview.R;
import com.jp.movieview.callback.JsonCallBack;
import com.jp.movieview.model.MgsData;
import com.jp.movieview.model.MgsSection;
import com.jp.movieview.ui.activity.MainActivity;
import com.jp.movieview.ui.adapter.MGSAdapter;
import com.jp.movieview.utils.LogUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cookie.store.CookieStore;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.HttpUrl;
import okhttp3.Response;

import static android.media.CamcorderProfile.get;

/**
 * A simple {@link Fragment} subclass.
 */
public class MGSFragment extends Fragment {
    public String TAG=getClass().getName();


    @BindView(R.id.mgs_recycler)
    RecyclerView mRecyclerView;


    MGSAdapter adapter;
    String end="";

    List<MgsData> list;

    List<MgsSection> data=new ArrayList<>();



    public MGSFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_mg, container, false);
        ButterKnife.bind(this,view);


        list=new ArrayList<>();
        adapter=new MGSAdapter(data);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        final StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        //manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);

        //LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
        //adapter.setOnLoadMoreListener(this,mRecyclerView);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        mRecyclerView.setAdapter(adapter);
        adapter.setEnableLoadMore(false);



        HttpUrl httpUrl = HttpUrl.parse("http://mgstage.com");
        Cookie.Builder builder = new Cookie.Builder();
        Cookie cookie = builder.name("ageConf").value("1").domain(httpUrl.host()).build();
        Cookie cookie2 = builder.name("adc").value("1").domain(httpUrl.host()).build();
        CookieStore cookieStore = OkGo.getInstance().getCookieJar().getCookieStore();
        cookieStore.saveCookie(httpUrl, cookie);
        cookieStore.saveCookie(httpUrl, cookie2);
        OkGo.getInstance().setCookieStore(cookieStore);


        String out = readFromAsset("out.html");

        Document doc = Jsoup.parse(out);
        //Elements company = doc.select("a[href]");
        Elements select = doc.select("ul.top_photo_list");
        Elements sizes = doc.select("span.directlink-res");
        Elements real_size_url=doc.select("a.directlink");

        Elements select1 = doc.select("[src$=.jpg");


        List<String> titles=new ArrayList<String>();

        Elements select2 = doc.select("h2.clearfix");
        for (int i=0;i<select2.size();i++){
            String title=select2.get(i).text().toString();
            titles.add(title);
            LogUtils.e(TAG,title);
        }

        Elements moreUrls=doc.select("div.more");
        for (int i=0;i<moreUrls.size();i++){
            String url=moreUrls.get(i).select("a").attr("href");
            LogUtils.e(TAG,url);
        }


        Elements li = select.select("li");
        for (int i=0;i<li.size();i++){
            String href = li.get(i).select("a").attr("href");
            String img_src = li.get(i).select("img").attr("src");
            String alt = li.get(i).select("img").attr("alt");


            if(i%4==0){
                data.add(new MgsSection(true,titles.get(i/4),true));
                LogUtils.e(TAG,i+titles.get(i/4));
            }

            MgsData mgs=new MgsData();
            mgs.setHref_url(href);
            mgs.setImg_url(img_src);
            mgs.setName(alt);

            list.add(mgs);

            data.add(new MgsSection(mgs));
        }
        adapter.setNewData(data);








        OkGo.get("http://sp.mgstage.com")
                .tag(this)
                .execute(new JsonCallBack<String>(getActivity()) {
                    @Override
                    public void onSuccess(String result, Call call, Response response) {
                        LogUtils.e(TAG,"mgs"+result);
                        //mTextView.setText(result);

                        Document doc = Jsoup.parse(result);
                        //Elements company = doc.select("a[href]");
                        Elements select = doc.select("ul.top_photo_list");
                        Elements sizes = doc.select("span.directlink-res");
                        Elements real_size_url=doc.select("a.directlink");

                        Elements select1 = doc.select("[src$=.jpg");


                        List<String> titles=new ArrayList<String>();

                        Elements select2 = doc.select("h2.clearfix");
                        for (int i=0;i<select2.size();i++){
                            String title=select2.get(i).text().toString();
                            titles.add(title);
                            LogUtils.e(TAG,title);
                        }

                        Elements moreUrls=doc.select("div.more");
                        for (int i=0;i<moreUrls.size();i++){
                            String url=moreUrls.get(i).select("a").attr("href");
                            LogUtils.e(TAG,url);
                        }


                        Elements li = select.select("li");
                        for (int i=0;i<li.size();i++){
                            String href = li.get(i).select("a").attr("href");
                            String img_src = li.get(i).select("img").attr("src");
                            String alt = li.get(i).select("img").attr("alt");


                            if(i%4==0){
                                data.add(new MgsSection(true,titles.get(i/4),true));
                                LogUtils.e(TAG,i+titles.get(i/4));
                            }

                            MgsData mgs=new MgsData();
                            mgs.setHref_url(href);
                            mgs.setImg_url(img_src);
                            mgs.setName(alt);

                            list.add(mgs);

                            data.add(new MgsSection(mgs));







                        }









                          /* 写入Txt文件 */
                        File file=new File(Environment.getExternalStorageDirectory().getPath() + "/AView/Yande/"+"out.html");
                        try {
                            //file.createNewFile(); // 创建新文件
                            BufferedWriter out = new BufferedWriter(new FileWriter(file));
                            out.write(result); // \r\n即为换行
                            out.flush(); // 把缓存区内容压入文件
                            out.close(); // 最后记得关闭文件

                        } catch (IOException e) {
                            e.printStackTrace();
                        }



                        adapter.setNewData(data);






                    }
                });


        return view;
    }

    public String readFromAsset(String fileName) {
        String text = "";
        try {
            InputStream in = getResources().getAssets().open(fileName);
            int length = in.available();
            byte[] buffer = new byte[length];
            in.read(buffer);
            in.close();
            text = new String(buffer, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }


}
