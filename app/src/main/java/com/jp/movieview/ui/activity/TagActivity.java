package com.jp.movieview.ui.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jp.movieview.R;
import com.jp.movieview.bean.YandeBean;
import com.jp.movieview.presenter.YandeItemPresenter;
import com.jp.movieview.ui.adapter.YandeAdapter;
import com.jp.movieview.view.YandeItemView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TagActivity extends BaseActivity implements YandeItemView{
    public String TAG = getClass().getName();

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.tag_recycler)
    RecyclerView mTagRecycler;

    YandeItemPresenter mPresenter;
    YandeAdapter adapter;

    int page=1;

    String tag;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_img_tag;
    }

    @Override
    protected void initView() {
        mToolbar.setTitle("TAG:\t" + getIntent().getStringExtra("tag"));

        mToolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorWhite));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        mToolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    @Override
    protected void initData() {
        tag = getIntent().getStringExtra("tag");

        mPresenter=new YandeItemPresenter(this);
        mPresenter.getTagItemData(""+page,tag);
        adapter=new YandeAdapter();


        mTagRecycler.setItemAnimator(new DefaultItemAnimator());
        final StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        //manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mTagRecycler.setLayoutManager(manager);
        //adapter.setOnLoadMoreListener(this,mRecyclerView);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        mTagRecycler.setAdapter(adapter);
        adapter.setEnableLoadMore(false);
    }

    @Override
    public void onSuccess(int type, List<YandeBean> data) {
        adapter.setNewData(data);
    }

    @Override
    public void onError() {

    }
}
