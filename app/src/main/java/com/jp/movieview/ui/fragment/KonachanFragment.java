package com.jp.movieview.ui.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jp.movieview.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class KonachanFragment extends Fragment {


    @BindView(R.id.tablayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    Unbinder unbinder;

    public KonachanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_yande, container, false);
        unbinder = ButterKnife.bind(this, view);


        mTabLayout.addTab(mTabLayout.newTab().setText("Popular By Day"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Popular By Week"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Popular By Month"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Popular By Year"));


        ArrayList<Fragment> Views=new ArrayList<>();

        //存放标题的数组
        ArrayList<String> Titles=new ArrayList<>();
        Titles.add("Popular By Day");
        Titles.add("Popular By Week");
        Titles.add("Popular By Month");
        Titles.add("Popular By Year");

        Views.add(KonachanTimeFragment.newInstance(0));
        Views.add(KonachanTimeFragment.newInstance(1));
        Views.add(KonachanTimeFragment.newInstance(2));
        Views.add(KonachanTimeFragment.newInstance(3));



        //适配器类
        TabFragmentAdapter mAdapter=new TabFragmentAdapter(getChildFragmentManager(),Views,Titles);

        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(4);
        mTabLayout.setupWithViewPager(mViewPager);



        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    //ViewPager适配器
    public class TabFragmentAdapter extends FragmentStatePagerAdapter {

        private List<Fragment> mFragments;
        private List<String> mTitles;

        public TabFragmentAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
            super(fm);
            mFragments = fragments;
            mTitles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }

    }
}
