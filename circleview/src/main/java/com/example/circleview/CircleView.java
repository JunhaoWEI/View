package com.example.circleview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by weijunhao on 2017/5/1.
 */

public class CircleView extends View {

    //设置画笔变量
    Paint mPaint;

    int mColor;

    /**
     * 自定义view有四个构造函数
     * 如果View是在java代码里面new的，则调用第一个构造函
     *
     * @param context
     */
    public CircleView(Context context) {
        super(context);

        //在构造函数里初始化画笔
        init();
    }

    /**
     * 如果view是在.xml文件里申明的，则调用此构造函数
     * 自定义属性是从attrs参数传进来的
     *
     * @param context
     * @param attrs
     */
    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    /**
     * 不会自动调用
     * 一般是在第二个构造函数里主动调用，如果view有style属性时
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //加载自定义属性集合CircleView
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.CircleView);

        mColor = a.getColor(R.styleable.CircleView_cicle_color, Color.RED);

        //解析后释放资源
        a.recycle();
        init();
    }

    /**
     * API21后才调用
     * 不会自动调用
     * 一般是在第二个构造函数里主动调用，如果view有style属性时
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     * @param defStyleRes
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init() {
        //创建画笔
        mPaint = new Paint();
        mPaint.setColor(mColor);
        mPaint.setStrokeWidth(5f);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        //获取传入的padding值
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        //获取控件的高度和宽度
        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;


        //设置圆的半径 = 宽，高最小值的1/2
        int r = Math.min(width, height) / 2;

        //画圆

        //圆心= 控件的中央， 半径 = 宽，高最小值的1/2
        canvas.drawCircle(paddingLeft + width / 2, paddingTop + height / 2, r, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //获取宽 - 测量规则的模式和大小
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        //获取高- 测量规则的模式和大小
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //设置默认宽 / 高值
        //默认宽/高的设定并无固定依据， 根据需要

        int mWidth = 400;
        int mHeight = 400;

        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mWidth, mHeight);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mWidth, heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, mHeight);
        }

    }
}


