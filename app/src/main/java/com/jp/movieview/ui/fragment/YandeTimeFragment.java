package com.jp.movieview.ui.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jp.movieview.R;
import com.jp.movieview.bean.YandeBean;
import com.jp.movieview.constant.Code;
import com.jp.movieview.presenter.YandeItemPresenter;
import com.jp.movieview.ui.adapter.YandeAdapter;
import com.jp.movieview.utils.ToastUtils;
import com.jp.movieview.view.YandeItemView;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * A simple {@link Fragment} subclass.
 */
public class YandeTimeFragment extends Fragment implements YandeItemView {


    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;

    Unbinder unbinder;

    YandeAdapter adapter;
    Calendar mCalendar;
    int year, month, day;

    int timeType;

    public String Months[] = {"January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"};

    TextView mDateText;

    ImageButton mBeforeBtn;

    ImageButton mAfterBtn;

    String space;

    @Override
    public void onStart() {
        super.onStart();
        space=getResources().getString(R.string.space);
    }

    public YandeTimeFragment() {
        // Required empty public constructor
    }

    public static YandeTimeFragment newInstance(int timeType) {
        YandeTimeFragment newFragment = new YandeTimeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("timeType", timeType);
        newFragment.setArguments(bundle);
        return newFragment;
    }


    @SuppressLint("ValidFragment")
    public YandeTimeFragment(int timeType) {
        // Required empty public constructor
        this.timeType = timeType;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_yande_time, container, false);
        unbinder = ButterKnife.bind(this, view);

        View headView=inflater.inflate(R.layout.time_head_view, container, false);
        mDateText= (TextView) headView.findViewById(R.id.date_text);
        mBeforeBtn= (ImageButton) headView.findViewById(R.id.before_btn);
        mAfterBtn= (ImageButton) headView.findViewById(R.id.after_btn);

        Bundle args = getArguments();
        if (args != null) {
            timeType = args.getInt("timeType");
        }



        adapter = new YandeAdapter();
        adapter.addHeaderView(headView);
        mCalendar = Calendar.getInstance();
        mCalendar.add(Calendar.DATE, -1);

        year = mCalendar.get(Calendar.YEAR);//获取年份
        month = mCalendar.get(Calendar.MONTH);//获取月份
        day = mCalendar.get(Calendar.DATE);//获取日

        mDateText.setText(Months[month] + "\t" + day + "," + year);

        YandeItemPresenter presenter = new YandeItemPresenter(this);
        switch (timeType) {
            case 0:

                presenter.getDayItemData(String.valueOf(day), String.valueOf(month + 1), String.valueOf(year));

                break;
            case 1:
                presenter.getWeekItemData(String.valueOf(day), String.valueOf(month + 1), String.valueOf(year));

                break;
            case 2:
                presenter.getMonthItemData(String.valueOf(day), String.valueOf(month + 1), String.valueOf(year));

                break;
            case 3:
                presenter.getYearItemData("1y");

                break;
        }



        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        final StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        //manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRecyclerView.setLayoutManager(manager);
        //adapter.setOnLoadMoreListener(this,mRecyclerView);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        mRecyclerView.setAdapter(adapter);
        adapter.setEnableLoadMore(false);









        mBeforeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showToast(getActivity(),"点击了");
                switch (timeType) {
                    case 0:
                        mCalendar.add(Calendar.DATE, -1);
                        year = mCalendar.get(Calendar.YEAR);//获取年份
                        month = mCalendar.get(Calendar.MONTH);//获取月份
                        day = mCalendar.get(Calendar.DATE);//获取日
                        presenter.getDayItemData(String.valueOf(day), String.valueOf(month + 1), String.valueOf(year));

                        break;
                    case 1:

                        mCalendar.add(Calendar.WEEK_OF_MONTH, -1);
                        year = mCalendar.get(Calendar.YEAR);//获取年份
                        month = mCalendar.get(Calendar.MONTH);//获取月份
                        day = mCalendar.get(Calendar.DATE);//获取日
                        presenter.getWeekItemData(String.valueOf(day), String.valueOf(month + 1), String.valueOf(year));

                        break;
                    case 2:
                        mCalendar.add(Calendar.MONTH, -1);
                        year = mCalendar.get(Calendar.YEAR);//获取年份
                        month = mCalendar.get(Calendar.MONTH);//获取月份
                        day = mCalendar.get(Calendar.DATE);//获取日
                        presenter.getMonthItemData(String.valueOf(day), String.valueOf(month + 1), String.valueOf(year));

                        break;
                    case 3:
                        ToastUtils.showToast(getActivity(), "没有了");

                        break;
                }
            }
        });


        mAfterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showToast(getActivity(),"点击了");
                switch (timeType) {

                    case 0:
                        mCalendar.add(Calendar.DATE, 1);
                        year = mCalendar.get(Calendar.YEAR);//获取年份
                        month = mCalendar.get(Calendar.MONTH);//获取月份
                        day = mCalendar.get(Calendar.DATE);//获取日
                        presenter.getDayItemData(String.valueOf(day), String.valueOf(month + 1), String.valueOf(year));

                        break;
                    case 1:

                        mCalendar.add(Calendar.WEEK_OF_MONTH, 1);
                        year = mCalendar.get(Calendar.YEAR);//获取年份
                        month = mCalendar.get(Calendar.MONTH);//获取月份
                        day = mCalendar.get(Calendar.DATE);//获取日
                        presenter.getWeekItemData(String.valueOf(day), String.valueOf(month + 1), String.valueOf(year));

                        break;
                    case 2:
                        mCalendar.add(Calendar.MONTH, 1);
                        year = mCalendar.get(Calendar.YEAR);//获取年份
                        month = mCalendar.get(Calendar.MONTH);//获取月份
                        day = mCalendar.get(Calendar.DATE);//获取日
                        presenter.getMonthItemData(String.valueOf(day), String.valueOf(month + 1), String.valueOf(year));

                        break;
                    case 3:
                        ToastUtils.showToast(getActivity(), "没有了");

                        break;
                }
            }
        });
















        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onSuccess(int type, List<YandeBean> data) {
        if (type == Code.YANDE_CODE) {
            //DataService.startService(this,data);
            adapter.setNewData(data);
            adapter.notifyDataSetChanged();
        }
        mDateText.setText(Months[month] + "\t" + day + "," + year);
    }

    @Override
    public void onError() {

    }
}
