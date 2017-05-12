package com.jp.movieview.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;
import android.widget.ZoomButtonsController;

import java.lang.reflect.Method;

/**
 * 去掉webview当中放大缩小控件，并且保留效果
 */
public class SupportZoomWebView extends WebView {
    // Webview内部的按钮控制对象
    private ZoomButtonsController zoomController = null;

    public SupportZoomWebView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        disableZoomController();
    }

    public SupportZoomWebView(Context context, AttributeSet attrs,
                              int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        disableZoomController();
    }

    public SupportZoomWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        disableZoomController();
    }

    // 使得控制按钮不可用
    @SuppressLint("NewApi")
    private void disableZoomController() {
        // API version 大于11的时候，SDK提供了屏蔽缩放按钮的方法
        if (android.os.Build.VERSION.SDK_INT >= 11) {
            this.getSettings().setBuiltInZoomControls(true);
            this.getSettings().setDisplayZoomControls(false);
        } else {
            // 如果是11- 的版本使用JAVA中的映射的办法
            getControlls();
        }
    }

    private void getControlls() {
        try {
            Class webview = Class.forName("android.webkit.WebView");
            Method method = webview.getMethod("getZoomButtonsController");
            zoomController = (ZoomButtonsController) method.invoke(this, new Object[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        super.onTouchEvent(ev);
        if (zoomController != null) {
            // 隐藏按钮
            zoomController.setVisible(false);
        }
        return true;
    }
}
