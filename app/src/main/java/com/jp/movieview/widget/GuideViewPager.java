package com.jp.movieview.widget;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jp.movieview.R;
import com.jp.movieview.utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jp on 2017/4/26
 */
public class GuideViewPager extends RelativeLayout{
    public static final String TAG = "GuideViewPager";
    //指示器的数量
    private int pointSize=0;
    List<View> imageviewlist;
    LinearLayout linearLayout;
    ViewPager mViewPager;

    private static final int[] imgs = new int[]{R.mipmap.a2, R.mipmap.a3, R.mipmap.a4};

    public GuideViewPager(Context context) {
        super(context);
        mViewPager=new ViewPager(context);
        linearLayout=new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(params);
        linearLayout.setGravity(Gravity.CENTER);


        RelativeLayout.LayoutParams params1=new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params1.setMargins(0,0,0,40);
        addView(mViewPager);
        addView(linearLayout,params1);

    }

    public GuideViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mViewPager=new ViewPager(context);
        linearLayout=new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(params);
        linearLayout.setGravity(Gravity.CENTER);


        RelativeLayout.LayoutParams params1=new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params1.setMargins(0,0,0,40);
        addView(mViewPager);
        addView(linearLayout,params1);
    }



    public void setPointSize(int pointSize) {
        this.pointSize = pointSize;
    }



    public void setData(Context context){


        imageviewlist = new ArrayList<>();
        for (int i = 0; i < imgs.length; i++) {
            ImageView im = new ImageView(context);
            im.setImageResource(imgs[i]);
            im.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageviewlist.add(im);
        }
        for (int i = 0; i < imgs.length; i++) {
            View point = new View(context);
            point.setBackgroundResource(R.drawable.guide_normal_cirlce_shape);
            point.setTag(i);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtils.dip2px(context, 10), DensityUtils.dip2px(context, 10));
            if (i > 0) {
                params.leftMargin = DensityUtils.dip2px(context, 10);
            }
            point.setLayoutParams(params);

            linearLayout.addView(point);

        }

        linearLayout.getChildAt(0).setBackgroundResource(R.drawable.guide_red_circle_shape);

        mViewPager.setPageTransformer(true,new ZoomOutPageTransformer());
        mViewPager.setAdapter(new GuideAdapter());
        mViewPager.addOnPageChangeListener(new GuidePageListener());
    }

    class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imgs.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imageviewlist.get(position));
            return imageviewlist.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


    class GuidePageListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            View point=linearLayout.getChildAt(position);

            for (int i=0;i<linearLayout.getChildCount();i++){
                if(i==position){
                    point=linearLayout.getChildAt(i);
                    point.setBackgroundResource(R.drawable.guide_red_circle_shape);
                }else {
                    point=linearLayout.getChildAt(i);
                    point.setBackgroundResource(R.drawable.guide_normal_cirlce_shape);
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }




}
