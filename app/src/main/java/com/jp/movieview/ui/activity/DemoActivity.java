package com.jp.movieview.ui.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jp.movieview.R;
import com.jp.movieview.utils.DensityUtils;
import com.jp.movieview.utils.LogUtils;
import com.jp.movieview.widget.GuideViewPager;
import com.jp.movieview.widget.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.button;

public class DemoActivity extends AppCompatActivity {
    public static final String TAG="DemoActivity";
    ViewPager viewPager;
    GuideViewPager guide;
    LinearLayout linearLayout;
    View red;
    private List<View> imageviewlist;
    private int mPointWidth;// 圆点间的距离
    private static final int[] imgs = new int[]{R.mipmap.a2, R.mipmap.a3, R.mipmap.a4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_demo);
        viewPager = (ViewPager) findViewById(R.id.guide_viewpager);

        linearLayout = (LinearLayout) findViewById(R.id.guide_group);
        red = findViewById(R.id.guidepoint);

        guide= (GuideViewPager) findViewById(R.id.guide);

        guide.setData(this);

        initViews();
        initData();

    }


    private void initData() {


        viewPager.setAdapter(new GuideAdapter());

        // 获取视图树, 对layout结束事件进行监听
        linearLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                linearLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mPointWidth = linearLayout.getChildAt(1).getLeft() - linearLayout.getChildAt(0).getLeft();
                LogUtils.e(TAG, "圆点距离:" + mPointWidth);
            }
        });
        viewPager.setOnPageChangeListener(new GuidePageListener());
    }


    class GuidePageListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // System.out.println("当前位置:" + position + ";百分比:" + positionOffset
            // + ";移动距离:" + positionOffsetPixels);
//            int len = (int) (mPointWidth * positionOffset) + position * mPointWidth;
//            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) red.getLayoutParams();// 获取当前红点的布局参数
//            params.leftMargin = len;// 设置左边距
//            red.setLayoutParams(params);// 重新给小红点设置布局参数

            red.setVisibility(View.GONE);




        }

        @Override
        public void onPageSelected(int position) {
            if (imageviewlist.size() == position + 1) {

            } else {

            }

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

    private void initViews() {
        imageviewlist = new ArrayList<>();
        for (int i = 0; i < imgs.length; i++) {
            ImageView im = new ImageView(this);
            im.setBackgroundResource(imgs[i]);
            imageviewlist.add(im);
        }
        for (int i = 0; i < imgs.length; i++) {
            View point = new View(this);
            point.setBackgroundResource(R.drawable.guide_normal_cirlce_shape);
            point.setTag(i);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtils.dip2px(this, 10), DensityUtils.dip2px(this, 10));
            if (i > 0) {
                params.leftMargin = DensityUtils.dip2px(this, 10);
            }
            point.setLayoutParams(params);

            linearLayout.addView(point);


        }

        linearLayout.getChildAt(0).setBackgroundResource(R.drawable.guide_red_circle_shape);

    }

}
