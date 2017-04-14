package com.jp.movieview.ui.activity;

import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jp.movieview.R;
import com.jp.movieview.bean.YandeBean;
import com.jp.movieview.constant.Code;
import com.jp.movieview.presenter.YandeItemPresenter;
import com.jp.movieview.ui.adapter.YandeAdapter;
import com.jp.movieview.utils.LogUtils;
import com.jp.movieview.utils.ToastUtils;
import com.jp.movieview.view.YandeItemView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;
import java.util.List;

import static android.R.attr.progressBarStyleSmallTitle;

public class YandeActivity extends BaseMvpActivity
        implements BaseQuickAdapter.RequestLoadMoreListener,YandeItemView,SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = "YandeActivity";

    YandeItemPresenter presenter;
    RecyclerView mRecyclerView;
    YandeAdapter adapter;

    Toolbar mToolbar;
    SwipeRefreshLayout mRefresh;
    LinearLayout mLeft;
    LinearLayout mRight;

    Calendar mCalendar;
    int year;
    int month;
    int day;
    String now;


    public String Months[] = {"January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"};

    @Override
    protected void fetchData() {
        presenter=new YandeItemPresenter(this);
        presenter.getGirlItemData(String.valueOf(day),String.valueOf(month+1),String.valueOf(year));
    }

    @Override
    protected int initLayoutId() {
        return R.layout.activity_safebooru;
    }

    @Override
    protected void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.safe_recycler);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mLeft = (LinearLayout) findViewById(R.id.left);
        mRight = (LinearLayout) findViewById(R.id.right);
        mRefresh= (SwipeRefreshLayout) findViewById(R.id.refresh);

    }

    @Override
    protected void initData() {
        adapter = new YandeAdapter();
        mCalendar = Calendar.getInstance();
        mCalendar.add(Calendar.DATE, -1);

        year = mCalendar.get(Calendar.YEAR);//获取年份
        month = mCalendar.get(Calendar.MONTH);//获取月份
        day = mCalendar.get(Calendar.DATE);//获取日

        now = Months[month] + getResources().getString(R.string.space) + day + "," + year;

        mToolbar.setTitle(Months[month] + getResources().getString(R.string.space) + day + "," + year);
        mToolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorWhite));
        setSupportActionBar(mToolbar);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        final StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        //manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRecyclerView.setLayoutManager(manager);
        //adapter.setOnLoadMoreListener(this,mRecyclerView);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        mRecyclerView.setAdapter(adapter);
        adapter.setEnableLoadMore(false);

        mRefresh.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
        mRefresh.setOnRefreshListener(this);
        //实现首次自动显示加载提示
        mRefresh.post(() -> mRefresh.setRefreshing(true));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);

        mRight.setOnClickListener(view -> {
            if (mToolbar.getTitle().toString().equals(now)) {
                ToastUtils.showToast(this, "没有了~~");
            } else {
                mRefresh.post(() -> mRefresh.setRefreshing(true));
                mCalendar.add(Calendar.DATE, 1);
                year = mCalendar.get(Calendar.YEAR);//获取年份
                month = mCalendar.get(Calendar.MONTH);//获取月份
                day = mCalendar.get(Calendar.DATE);//获取日
                presenter.getGirlItemData(String.valueOf(day),String.valueOf(month+1),String.valueOf(year));
            }
        });

        mLeft.setOnClickListener(view -> {
            mRefresh.post(() -> mRefresh.setRefreshing(true));
            mCalendar.add(Calendar.DATE, -1);
            year = mCalendar.get(Calendar.YEAR);//获取年份
            month = mCalendar.get(Calendar.MONTH);//获取月份
            day= mCalendar.get(Calendar.DATE);//获取日
            presenter.getGirlItemData(String.valueOf(day),String.valueOf(month +1),String.valueOf(year));

        });
    }


    //加载更多
    @Override
    public void onLoadMoreRequested() {

    }

    //接收Service传回数据
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void dataEvent(List<YandeBean> datas) {
        LogUtils.e(TAG, "第一个接收");
        //adapter.setNewData(data);
        adapter.setNewData(datas);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onSuccess(int type,List<YandeBean> data) {
        if(type== Code.YANDE_CODE){
            //DataService.startService(this,data);
            mRefresh.post(() -> mRefresh.setRefreshing(false));
            adapter.setNewData(data);
            adapter.notifyDataSetChanged();
            mToolbar.setTitle(Months[month] + getResources().getString(R.string.space) + day + "," + year);
        }
    }

    //TODO 错误处理 待改进
    @Override
    public void onError() {
        mRefresh.post(()->mRefresh.setRefreshing(false));
        View foot= LayoutInflater.from(YandeActivity.this).inflate(R.layout.load_failed_layout,null);
        adapter.setEmptyView(foot);
        foot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRefresh.post(()->mRefresh.setRefreshing(true));
                onRefresh();
            }
        });
    }

    @Override
    public void onRefresh() {
        fetchData();
    }
}
