package com.example.weijunhao.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by weijunhao on 2017/4/13.
 */

public class MyView extends View {
    private Context context;
    public MyView(Context context) {
        super(context);
        Log.i("wjh", "一个参数构造方法");
        this.context = context;
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.i("wjh", "二个参数构造方法");
        this.context = context;
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.i("wjh", "三个参数构造方法");
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        Log.i("wjh", "四个参数构造方法");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //setMeasuredDimension(measureHeight(widthMeasureSpec), measureHeight(heightMeasureSpec));
        int width = getMySize(120, widthMeasureSpec);
        Log.i("wjh", "~~~~~~~~~~~~~~~~~~~~~~~~~~");
        int height = getMySize(dip2px(context, 50), heightMeasureSpec);

        if (width < height) {
            Log.i("wjh-height大", "1111");
            height = width;
        } else {
            Log.i("wjh-width大", "2222");
            width = height;
        }

        setMeasuredDimension(width, height);
    }
    private int measureHeight(int heightMeasureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (specMode) {
            case MeasureSpec.AT_MOST:
                Log.i("wjh", "子容器可以是声明大小内的任意大小");
                Log.i("wjh", "大小为" + specSize);
                result = specSize;
                break;
            case MeasureSpec.EXACTLY:
                Log.i("wjh", "父容器已经为子容器设置了尺寸,子容器应当服从这些边界,不论子容器想要多大的空间");
                Log.i("wjh","大小为"+specSize);
                result = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                Log.i("wjh", "父容器对于子容器没有任何限制,子容器想要多大就多大");
                Log.i("wjh", "大小为" + specSize);
                result = 1500;
                break;
            default:
                break;
        }
        return result;
    }
    private int getMySize(int defaultSize, int measureSpec) {
        int mySize = defaultSize;

        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
                mySize = defaultSize;
                Log.i("wjh-mode", "UNSPECIFIED");
                break;
            case MeasureSpec.AT_MOST:
                mySize = size;
                Log.i("wjh-mode", "AT_MOST");
                break;
            case MeasureSpec.EXACTLY:
                mySize = size;
                Log.i("wjh-mode", "EXACTLY");
                break;
        }
        Log.i("wjh-mysize-dp", px2dip(context, mySize) + "dp");
        return mySize;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i("wjh-onDraw","onDraw");
        int r = getMeasuredWidth() / 2;//也可以是getMeasuredHeight()/2,本例中我们已经将宽高设置相等了
        //圆心的横坐标为当前的View的左边起始位置+半径
        int centerX = getLeft() + r;
        //圆心的纵坐标为当前的View的顶部起始位置+半径
        int centerY = getTop() + r;

        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        //开始绘制
        canvas.drawCircle(centerX, centerY, r, paint);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public  int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public  int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
