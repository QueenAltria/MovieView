package com.jp.movieview.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jp.movieview.R;
import com.jp.movieview.callback.JsonCallBack;
import com.jp.movieview.ui.activity.MainActivity;
import com.jp.movieview.ui.adapter.AllComicsAdapter;
import com.jp.movieview.ui.adapter.ListDropDownAdapter;
import com.jp.movieview.utils.LogUtils;
import com.jp.movieview.utils.ToastUtils;
import com.jp.movieview.widget.DropDownMenu;
import com.lzy.okgo.OkGo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static android.nfc.tech.MifareUltralight.PAGE_SIZE;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllComicsFragment extends Fragment implements BaseQuickAdapter.RequestLoadMoreListener{
    String TAG="AllComicsFragment";

    AllComicsAdapter adapter;
    int page=2;

    private boolean isErr=true;

    private String headers[] = {"按狀態","按分類"};
    private String statues[] = {"全部","連載","完結"};
    private String category[] = {"全部","科幻魔幻","少年熱血",
            "東方同人","少女愛情","武俠格鬥",
            "爆笑喜劇","其它漫畫","競技體育",
            "偵探推理","恐怖靈異","BL",
            "其它類型","艦娘同人","love live同人"};
    private List<View> popupViews = new ArrayList<>();


    public AllComicsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_all_comics, container, false);
        DropDownMenu mDropDownMenu = (DropDownMenu) view.findViewById(R.id.dropDownMenu);
        final ListView sexView = new ListView(this.getActivity());
        sexView.setDividerHeight(0);
        ListDropDownAdapter sexAdapter = new ListDropDownAdapter(this.getActivity(), Arrays.asList(statues));
        sexView.setAdapter(sexAdapter);


        final ListView categoryView = new ListView(this.getActivity());
        categoryView.setDividerHeight(0);
        ListDropDownAdapter categoryAdapter = new ListDropDownAdapter(this.getActivity(), Arrays.asList(category));
        categoryView.setAdapter(categoryAdapter);


        popupViews.add(sexView);
        popupViews.add(categoryView);
        TextView contentView = new TextView(this.getActivity());
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contentView.setText("内容显示区域");
        contentView.setGravity(Gravity.CENTER);
        contentView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

        TextView text1= new TextView(this.getActivity());




        View content=inflater.inflate(R.layout.recycler_view, container, false);
        RecyclerView mRecyclerView= (RecyclerView) content.findViewById(R.id.recycler_view);
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, mRecyclerView);

        adapter=new AllComicsAdapter();
        adapter.setOnLoadMoreListener(this,mRecyclerView);
        List<String> srsc=new ArrayList<>();
        OkGo.get("http://www.2animx.com/index-html")
                .tag(this)
                .execute(new JsonCallBack<String>(this.getActivity()) {
                    @Override
                    public void onSuccess(String result, Call call, Response response) {
                        Document doc = Jsoup.parse(result);
                        Elements select = doc.select("ul.liemh").select("ul.htmls").select("ul.indliemh");
                        Elements li = select.select("li");
                        for (int i=0;i<li.size();i++){
                            //url
                            String href_url = li.get(i).select("a").attr("href");
                            String title = li.get(i).select("a").attr("title");
                            String src = li.get(i).select("img").attr("src");



                            String font = li.get(i).select("font").text();
                            String em = li.get(i).select("em").text();

                            srsc.add("http://www.2animx.com/"+src);

                            LogUtils.e(TAG,"name1---->"+src);
                        }


                        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        final GridLayoutManager manager = new GridLayoutManager(AllComicsFragment.this.getActivity(),3);
                        //manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
                        mRecyclerView.setLayoutManager(manager);
                        //adapter.setOnLoadMoreListener(this,mRecyclerView);
                        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
                        adapter.setNewData(srsc);
                        mRecyclerView.setAdapter(adapter);
                        adapter.setEnableLoadMore(true);
                    }
                });



        return view;
    }

    @Override
    public void onLoadMoreRequested() {


            if (page>3) {
//                    pullToRefreshAdapter.loadMoreEnd();//default visible
                adapter.loadMoreEnd(false);//true is gone,false is visible
            } else {
                if (isErr) {
                    page+=1;
                    List<String> lists=new ArrayList<>();
                    OkGo.get(" http://www.2animx.com/index.php?s=%2Findex-html-status-0-typeid-0-sort-&page="+page)
                            .tag(this)
                            .execute(new JsonCallBack<String>(this.getActivity()) {
                                @Override
                                public void onSuccess(String result, Call call, Response response) {
                                    Document doc = Jsoup.parse(result);
                                    Elements select = doc.select("ul.liemh").select("ul.htmls").select("ul.indliemh");
                                    Elements li = select.select("li");
                                    for (int i=0;i<li.size();i++){
                                        //url
                                        String href_url = li.get(i).select("a").attr("href");
                                        String title = li.get(i).select("a").attr("title");
                                        String src = li.get(i).select("img").attr("src");



                                        String font = li.get(i).select("font").text();
                                        String em = li.get(i).select("em").text();

                                        lists.add("http://www.2animx.com/"+src);

                                    }

                                    adapter.addData(lists);
                                    adapter.loadMoreComplete();
                                }

                                @Override
                                public void onError(Call call, Response response, Exception e) {
                                    super.onError(call, response, e);
                                    isErr = true;
                                    adapter.loadMoreFail();
                                }
                            });
                } else {
                    isErr = true;
                    ToastUtils.showToast(this.getActivity(),"失败");
                    adapter.loadMoreFail();
                }
            }




    }
}
