package com.jp.movieview.ui.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jp.movieview.R;
import com.jp.movieview.bean.ComicBean;
import com.jp.movieview.callback.JsonCallBack;
import com.jp.movieview.ui.activity.ComicInfoActivity;
import com.jp.movieview.ui.adapter.AllComicsAdapter;
import com.jp.movieview.ui.adapter.AllComicsAdapter2;
import com.jp.movieview.utils.LogUtils;
import com.lzy.okgo.OkGo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static com.jp.movieview.R.id.imageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndexCoimcsFragment extends Fragment {
    public static final String TAG="IndexCoimcsFragment";
    AllComicsAdapter2 adapter;
    AppCompatSpinner mSpinner;
    RecyclerView mRecyclerView;
    public IndexCoimcsFragment() {
        // Required empty public constructor
    }

    List<ComicBean> comic1=new ArrayList<>();
    List<ComicBean> comic2=new ArrayList<>();
    List<ComicBean> comic3=new ArrayList<>();
    List<ComicBean> comic4=new ArrayList<>();
    List<ComicBean> comic5=new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //View view=inflater.inflate(R.layout.fragment_index_coimcs, container, false);
        View view=inflater.inflate(R.layout.fragment_index_coimcs, container, false);
        mRecyclerView= (RecyclerView) view.findViewById(R.id.hot_recycler);
        mSpinner= (AppCompatSpinner) view.findViewById(R.id.spinner);
        List<ComicBean> srcs=new ArrayList<>();
        mRecyclerView.setNestedScrollingEnabled(false);
        adapter=new AllComicsAdapter2();
        String[] mItems = {"熱門連載","經典完結","最新上架","全彩精選","熱門完結"};
// 建立Adapter并且绑定数据源
        ArrayAdapter<String> spiineradapter=new ArrayAdapter<>(this.getActivity(),android.R.layout.simple_spinner_item, mItems);
        spiineradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinner.setAdapter(spiineradapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i){
                    case 0:
                        adapter.setNewData(comic1);
                        break;
                    case 1:
                        adapter.setNewData(comic2);
                        break;
                    case 2:
                        adapter.setNewData(comic3);
                        break;
                    case 3:
                        adapter.setNewData(comic4);
                        break;
                    case 4:
                        adapter.setNewData(comic5);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        OkGo.get("http://www.2animx.com/")
                .tag(this)
                .execute(new JsonCallBack<String>(this.getActivity()) {
                    @Override
                    public void onSuccess(String result, Call call, Response response) {


                        comic1=getListData(result,"[id^=c1_1]");
                        comic2=getListData(result,"[id^=c1_2]");
                        comic3=getListData(result,"[id^=c1_3]");
                        comic4=getListData(result,"[id^=c1_4]");
                        comic5=getListData(result,"[id^=c1_5]");



                        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        final GridLayoutManager manager = new GridLayoutManager(IndexCoimcsFragment.this.getActivity(),4);
                        //manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
                        mRecyclerView.setLayoutManager(manager);
                        //adapter.setOnLoadMoreListener(this,mRecyclerView);
                        //adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);

                        adapter.setNewData(comic1);
                        mRecyclerView.setAdapter(adapter);

//                        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                                Intent intent=new Intent(getActivity(), ComicInfoActivity.class);
//
//                                Pair<View,String> pair=Pair.create(view.findViewById(R.id.safe_img), "profile");
//                                intent.putExtra("url",srcs.get(position).getSrc_url());
//
//                                ActivityOptionsCompat options =
//                                        ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) getActivity(),
//                                                pair);
//
//                                getActivity().startActivity(intent, options.toBundle());
//                            }
//                        });
                    }
                });
        return view;
    }

    List<ComicBean> getListData(String result,String id){
        List<ComicBean> comicBeanList=new ArrayList<>();
        Document doc = Jsoup.parse(result);
        Elements select = doc.select("div.scroll-pane").select(id);
        Elements li = select.select("li");
        for (int i=0;i<li.size();i++){
            //url
            String href_url = li.get(i).select("a").attr("href");
            String title = li.get(i).select("a").attr("title");
            String src = li.get(i).select("img").attr("src");


            String em = li.get(i).select("em").text();

            ComicBean bean=new ComicBean();
            bean.setEm(em);
            bean.setHref_url(href_url);
            bean.setTitle(title);
            bean.setSrc_url("http://www.2animx.com/"+src);

            comicBeanList.add(bean);
        }
        return comicBeanList;
    }

}
