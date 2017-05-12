package com.jp.movieview.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Created by kf on 2016/6/22.
 */
public class AppManager {
    private static Stack<Activity> activityStack;
    private static AppManager instance;

    public static synchronized AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    /**
     * 添加activity到堆栈
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的activity
     *
     * @param activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的activity
     *
     * @param cls
     */
    public void finishActivity(Class<?> cls) {
        Activity removeactivity = null;
        if (activityStack != null) {
            if (activityStack.size() > 0) {
                for (Activity activity : activityStack) {
                    if (activity.getClass().equals(cls)) {
                        removeactivity = activity;
                        break;
                    }
                }
            }
            finishActivity(removeactivity);
        }
    }

    public boolean isHasClass(Class<?> cls) {
        if (activityStack != null)
            if (activityStack.size() > 0) {
                for (Activity activity : activityStack) {
                    if (activity.getClass().equals(cls)) {
                        return true;
                    }
                }
            }
        return false;
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0; i < activityStack.size(); i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishSameActivity(Class<?> cls) {
        List<Activity> list = new ArrayList<>();

        if (activityStack != null)
            if (activityStack.size() > 0) {
                for (Activity activity : activityStack) {
                    if (activity.getClass().equals(cls)) {
                        list.add(activity);
                    }
                }
                finishSanmeSameActivity(list);
            }
    }

    public void finishSanmeSameActivity(List<Activity> list) {
        List<Activity> copy = new LinkedList<>(list);
        if (list.size() > 0)
            for (Activity activity : copy) {
                activity.finish();
            }
    }
}
