package com.jp.movieview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by jp on 2017/4/26
 */
public class ViewPagerIndicator extends View{

    private float mPositionOffset = 0;
    private int mPosition;

    private int mItemCount;
    private int mItemWidth;
    private int mItemHeight;
    private int mItemGap;

    private Drawable mItemDrawable;
    private Drawable mItemDrawableSelected;

    int mWidth = 0;  //指示器的总宽度

    public ViewPagerIndicator(Context context) {
        super(context);
        init(context,null);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (mItemCount > 1) {
            mWidth = (mItemWidth + mItemGap) * mItemCount - mItemGap;
        } else if(mItemCount == 1) {
            mWidth = mItemWidth;
        } else {
            mWidth = 0;
        }
        setMeasuredDimension(mWidth, mItemHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int flags = Canvas.MATRIX_SAVE_FLAG | Canvas.CLIP_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG | Canvas.FULL_COLOR_LAYER_SAVE_FLAG | Canvas
                .CLIP_TO_LAYER_SAVE_FLAG;
        int sc = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, flags);

        int wg = mItemWidth + mItemGap;
        int x = (getWidth() - mWidth) / 2;
        int y = (getHeight() - mItemHeight) / 2;

        mItemDrawable.setBounds(0, 0, mItemWidth, mItemHeight);   //setBounds设置绘制范围
        mItemDrawableSelected.setBounds(0, 0, mItemWidth, mItemHeight);

        for (int i = 0; i < mItemCount; i++) {
            //save：用来保存Canvas的状态。save之后，可以调用Canvas的平移、放缩、旋转、错切、裁剪等操作
            //restore：用来恢复Canvas之前保存的状态。防止save后对Canvas执行的操作对后续的绘制有影响
            canvas.save();
            canvas.translate(x + i * wg, y);   //translate平移
            mItemDrawable.draw(canvas);

            canvas.restore();
        }

        canvas.save();
        canvas.translate(x + (mPosition + mPositionOffset) * wg, y);
        mItemDrawableSelected.draw(canvas);
        canvas.restore();

        canvas.restoreToCount(sc);
    }

    public void setupWithViewPager(ViewPager pager) {
        mItemCount = pager.getAdapter().getCount();
        pager.removeOnPageChangeListener(onPageChangeListener);
        pager.addOnPageChangeListener(onPageChangeListener);
        requestLayout();
    }

    public void setPosition(int position) {
        mPosition = position;
        invalidate();
    }


    public ViewPagerIndicator setItemSize(int width, int height) {
        this.mItemWidth = width;
        this.mItemHeight = height;
        return this;
    }

    public ViewPagerIndicator setItemGap(int gap) {
        this.mItemGap = gap;
        return this;
    }

    public ViewPagerIndicator setItemColor(@ColorInt int color, @ColorInt int selected) {
        this.mItemDrawable = genDrawable(color);
        this.mItemDrawableSelected = genDrawable(selected);
        return this;
    }

    public ViewPagerIndicator setItemDrawable(@NonNull Drawable normal, @NonNull Drawable selected) {
        this.mItemDrawable = normal;
        this.mItemDrawableSelected = selected;
        return this;
    }

    Drawable genDrawable(int color) {
        ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
        drawable.getPaint().setColor(color);
        drawable.getPaint().setAntiAlias(true);
        return drawable;
    }

    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            mPosition = position;
            mPositionOffset = positionOffset;
            invalidate();
        }

        @Override
        public void onPageSelected(int position) {
            mPosition = position;
            mPositionOffset = 0;
            invalidate();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
