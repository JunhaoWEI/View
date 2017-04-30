package com.example.windowview;

import android.app.ActivityManager;
import android.content.Context;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * Created by weijunhao on 2017/4/29.
 */

public class MyWindowManager {

    private static FloatWindowSmallView smallWindow;
    private static ViewGroup.LayoutParams smallWindowParams;
    private static WindowManager mWindowManager;
    private static ActivityManager mActivityManager;


    public static boolean isWindowShowing() {
        return false;
    }

    /**
     * 创建小悬浮窗，初始位置为屏幕的右部中间位置
     *
     * @param applicationContext
     */
    public static void createSmallWindow(Context applicationContext) {
        WindowManager windowManager = getWindowManager(applicationContext);
        int screenWidth = mWindowManager.getDefaultDisplay().getWidth();
        int screenheight = mWindowManager.getDefaultDisplay().getHeight();
        if (smallWindow == null) {
            smallWindow = new FloatWindowSmallView(applicationContext);
            if (smallWindowParams == null) {
                smallWindowParams = new ViewGroup.LayoutParams()
            }
        }
    }

    public static void removeSmallWindow(Context applicationContext) {

    }

    public static void removeBigWindow(Context applicationContext) {

    }

    public static void updateUsedPercent(Context applicationContext) {

    }

    public static int getUsedPercentValue(Context context) {
        return 0;
    }

    /**
     * 创建windowMaager
     *
     * @param applicationContext
     * @return
     */
    private static WindowManager getWindowManager(Context applicationContext) {
        if (mWindowManager == null) {
            mWindowManager = (WindowManager) applicationContext.getSystemService(Context.WINDOW_SERVICE);
        }
        return mWindowManager;
    }

}
