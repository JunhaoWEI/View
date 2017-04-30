package com.example.windowview;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.IntDef;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class FloatWindowService extends Service {
    private Handler mHandler = new Handler();

    //定时器，定时进行检查当前应该创建还是移除悬浮窗
    private Timer timer;

    public FloatWindowService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //开启定时器，每隔5秒刷新一次
        if (timer == null) {
            timer = new Timer();
            timer.scheduleAtFixedRate(new RefreshTask(), 0, 500);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        //service被终止的同时也停止定时器继续进行
        timer.cancel();
        timer = null;
        super.onDestroy();
    }

    class RefreshTask extends TimerTask {

        @Override
        public void run() {
            //当前界面是桌面,且没有悬浮窗显示，则创建悬浮窗
            //当前界面不是桌面，且有悬浮窗显示，则移除悬浮窗
            //当前界面是桌面，且有悬浮窗显示，则更新内存数据
            // TODO: 2017/4/29
            if (isHome() && !MyWindowManager.isWindowShowing()) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        MyWindowManager.createSmallWindow(getApplicationContext());
                    }
                });
            } else if (!isHome() && MyWindowManager.isWindowShowing()) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        MyWindowManager.removeSmallWindow(getApplicationContext());
                        MyWindowManager.removeBigWindow(getApplicationContext());
                    }
                });
            } else if (isHome() && MyWindowManager.isWindowShowing()) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        MyWindowManager.updateUsedPercent(getApplicationContext());
                    }
                });

            }
        }
    }

    /**
     * 判断当前应用示是否是桌面
     *
     * @return
     */
    private boolean isHome() {
        return false;
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> rti = activityManager.getRunningTasks(1);
        return getHomes().contains(rti.get(0).topActivity.getPackageName());
    }

    /**
     * 获取属于桌面应用的应用包名称
     *
     * @return 返回包含所有包名的字符串列表
     */
    private List<String> getHomes() {
        List<String> names = new ArrayList<>();
        PackageManager packageManager = this.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent,
                packageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo ri : resolveInfos) {
            names.add(ri.activityInfo.packageName);
        }
        return names;
    }


}
