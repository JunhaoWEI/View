package com.example.windowview;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by weijunhao on 2017/4/30.
 */

public class FloatWindowSmallView extends LinearLayout {

    //小浮窗宽度、高度和状态栏高度
    public static int viewWidth;
    public static int viewHeight;
    public static int statusBarHeight;

    //管理小浮窗
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mParams;

    //当前手指在屏幕上的位置
    private float xInScreen;
    private float yInScreen;

    //按下时手指在屏幕上的位置
    private float xDownInScreen;
    private float yDownInScreen;

    //按下时小浮窗在屏幕上的位置
    private float xInView;
    private float yInView;

    public FloatWindowSmallView(Context context) {
        super(context);
    }

    public FloatWindowSmallView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public FloatWindowSmallView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public FloatWindowSmallView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initView(Context context, AttributeSet attrs) {
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        LayoutInflater.from(context).inflate(R.layout.float_window_small, this);
        View view = findViewById(R.id.small_window_layout);
        viewWidth = view.getLayoutParams().width;
        viewHeight = view.getLayoutParams().height;

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(MyWindowManager.getUsedPercentValue(context));
    }

}
