package com.example.ppchart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import java.util.List;


/**
 * Created by weijunhao on 2017/4/30.
 */

public class PPChart extends View {
    Context mContext;
    Paint mPaint;
    private int mXDown, mLastX;
    //最短滑动距离
    float a = 0;

    float startX = 0;
    float lastStartX = 0;//抬起手指后，当前控件最左边X的坐标
    float cellCountW = 9.5f;//一个屏幕的宽度会显示的格子数
    float cellCountH = 12.5f;//整个控件的高度会显示的格子数


    float cellH, cellW;
    float topPadding = 0.25f;

    PathEffect mEffect = new CornerPathEffect(20);//平滑过渡的角度

    int state = -100;
    int lineWidth;

    private List<dataObject> data;

    public void setData(List data) {
        this.data = data;

        state = -100;
        Log.i("wjh", "setData: " + this.data == null ? "null" : this.data.size() + "");
        postInvalidate();
    }

    public PPChart(Context context) {
        super(context);
        mContext = context;
    }

    public PPChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        a = DensityUtils.px2dp(context, ViewConfiguration.get(context).getScaledDoubleTapSlop());
        lineWidth = DensityUtils.dp2px(context, 1);
        setClickable(true);
    }

    public PPChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PPChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        //线的颜色
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initPaint();
        cellH = getHeight() / cellCountH;
        cellW = getWidth() / cellCountW;

        //画底部背景
        mPaint.setColor(0xff44b391);
        canvas.drawRect(0, ((int) (cellCountH - 1) + topPadding) * cellH,
                getWidth(), cellCountH * cellH, mPaint);

        DrawAbscissaLines(canvas);
        /*if (data == null || data.size() == 0) {
            Log.i("wjh", "setData: " + this.data == null ? "null" : "？？？");
            return;
        }*/


        //DrawOrdinate(canvas);

        //到此背景结束

        canvas.saveLayer(0, 0, getWidth(), getHeight(), mPaint, Canvas.ALL_SAVE_FLAG);
        DrawDataBackground(canvas);
        canvas.restore();

        canvas.saveLayer(0, 0, getWidth(), getHeight(), mPaint, Canvas.ALL_SAVE_FLAG);
        DrawDataLine(canvas);
        canvas.restore();

        canvas.saveLayer(0, 0, getWidth(), getHeight(), mPaint, Canvas.ALL_SAVE_FLAG);
        DrawAbscissa(canvas);
        canvas.restore();
    }

    /**
     * 画横坐标
     *
     * @param canvas
     */
    private void DrawOrdinate(Canvas canvas) {
        mPaint.reset();

        float i = 0.5f;
        for (dataObject tmp : data) {
            mPaint.setColor(0xffb4e1d3);
            mPaint.setTextSize(getWidth() / cellCountW / 3.2f);
            dataObject tmp2 = getDataByX(mLastX);

            //选中的那一项需要加深
            if (tmp2 != null && tmp2.getHappenTime().equals(tmp.getHappenTime())
                    && state == MotionEvent.ACTION_UP && Math.abs(mLastX - mXDown) < a) {
                mPaint.setColor(0xffffffff);
            } else {
                mPaint.setColor(0xffb4e1d3);
            }

            String str1 = tmp.getHappenTime().split("-")[0];
            canvas.drawText(str1,
                    startX + cellW * i - mPaint.measureText(str1) / 2,
                    (((int) cellCountW - 1) + topPadding + cellCountH) / 2 * cellH,
                    mPaint);
            mPaint.setTextSize(getWidth() / cellCountW / 3.5f);
            String str2 = tmp.getHappenTime().split("-")[1] + "." +
                    tmp.getHappenTime().split("-")[2];
            canvas.drawText(str2, startX + cellW * i - mPaint.measureText(str2) / 2,
                    (((int) cellCountH - 1) + topPadding + cellCountH) / 2 * cellH - 1.5f * (mPaint.ascent() + mPaint.descent()),
                    mPaint);

            //画背景竖线
            mPaint.setColor(0xff92dac4);
            canvas.drawLine(startX + cellW * i,
                    topPadding * cellH,
                    startX + cellW * i,
                    (topPadding + 10.5f) * cellH,
                    mPaint);
            i++;
        }

        mPaint.setColor(0xffb4e1d3);
        mPaint.setTextSize(getWidth() / cellCountW / 3f);
        canvas.drawText("end",
                startX + cellW * i - mPaint.measureText("end") / 2,
                (((int) cellCountH - 1) + topPadding + cellCountH) / 2 * cellH - (mPaint.ascent() + mPaint.descent()) / 2,
                mPaint);


    }


    /**
     * 画纵坐标
     *
     * @param canvas
     */
    private void DrawAbscissaLines(Canvas canvas) {
        Log.i("wjh", "DrawAbscissaLines: ");
        mPaint.setColor(0xff92dac4);
        mPaint.setStrokeWidth(5);


        //画背景横线
        for (int i = 0; i < 11; i++) {
            Log.d("wjh", "DrawAbscissaLines: " + i);
            canvas.drawLine(0, (topPadding + i + 1) * cellH,
                    cellW * 9.5f, (topPadding + i + 1) * cellH, mPaint);
        }

    }

    //画纵坐标
    private void DrawAbscissa(Canvas canvas) {
        mPaint.reset();
       /* mPaint.setColor(mContext.getResources().getColor(R.color.colorPrimary));
        //画纵坐标背景9.51 = 10 - 0.5（i的起步） + 0.01(把最后一条线露出来
        canvas.drawRect(cellW * ((int) cellCountW - 0.5f + 0.01f), 0,
                cellW * ((int) cellCountW + 1), 11.2f * cellH, mPaint);
*/
        mPaint.setColor(0xffb6e6d7);
        mPaint.setTextSize(getWidth() / cellCountW / 3);

        canvas.drawText("100%", cellW * (int) cellCountW - mPaint.measureText("100%") / 2,
                topPadding * cellH - (mPaint.ascent() + mPaint.descent()) / 2,
                mPaint);

        canvas.drawText("80%",
                cellW * (int) cellCountW - mPaint.measureText("80%") / 2,
                (topPadding + 2) * cellH - (mPaint.ascent() + mPaint.descent()) / 2,
                mPaint);
        canvas.drawText("60%",
                cellW * (int) cellCountW - mPaint.measureText("60%") / 2,
                (topPadding + 4) * cellH - (mPaint.ascent() + mPaint.descent()) / 2,
                mPaint);

        canvas.drawText("40%",
                cellW * (int) cellCountW - mPaint.measureText("40%") / 2,
                (topPadding + 6) * cellH - (mPaint.ascent() + mPaint.descent()) / 2,
                mPaint);

        canvas.drawText("20%",
                cellW * (int) cellCountW - mPaint.measureText("20%") / 2,
                (topPadding + 8) * cellH - (mPaint.ascent() + mPaint.descent()) / 2,
                mPaint);

        canvas.drawText("0%",
                cellW * (int) cellCountW - mPaint.measureText("0%") / 2,
                (topPadding + 10) * cellH - (mPaint.ascent() + mPaint.descent()) / 2,
                mPaint);
    }

    /**
     * 画渐变背景
     *
     * @param canvas
     */
    private void DrawDataBackground(Canvas canvas) {

        Log.d("wjh", "DrawDataBackground: ");
        LinearGradient lg = new LinearGradient(getWidth() / 2, topPadding * cellH,
                getWidth() / 2, (topPadding + 10) * cellH, 0xaaffffff, 0xaa61ccab, Shader.TileMode.CLAMP);
        mPaint.setShader(lg);

        float i = 0.5f;
        Path path = new Path();

        path.moveTo(startX + cellW * i, (topPadding + 10) * cellH);
        path.lineTo(startX + cellW * i, (topPadding + 10) * cellH);
        path.lineTo(startX + cellW * i, getHByValue(90));
        path.lineTo(startX + cellW * i, getHByValue(80));


        path.lineTo(startX + cellW * (i - 1), (topPadding + 10) * cellH - 1);
        path.lineTo(startX + cellW * (i - 1), (topPadding + 10) * cellH);
        path.close();
        mPaint.setPathEffect(mEffect);
        canvas.drawPath(path, mPaint);

    }

    /**
     * 画数据线
     *
     * @param canvas
     */
    private void DrawDataLine(Canvas canvas) {
        float i = 0.5f;
        mPaint.reset();
        mPaint.setStrokeWidth(lineWidth);
        mPaint.setColor(0xffffffff);

        Path path = new Path();
        path.moveTo(startX + cellW * i -1, getHByValue(90));
        path.lineTo(startX + cellW * i, getHByValue(90));
        path.lineTo(startX + cellW * i, getHByValue(90));
        path.lineTo(startX + cellW * i, getHByValue(80));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setPathEffect(mEffect);
        canvas.drawPath(path, mPaint);

    }

    private float getHByValue(float value) {
        return (topPadding + 10) * cellH - (cellH * 10) * value / 100;
    }

    /**
     * 通过坐标，获得附近的点
     *
     * @param pointX
     * @return
     */
    private dataObject getDataByX(int pointX) {
        float i = 0.5f;
        dataObject result = null;
        for (dataObject tmp : data) {
            float x = startX + cellW * i;
            if (Math.abs(x - pointX) < cellW / 2) {
                result = tmp;
                return result;
            }
            i++;
        }
        return result;
    }
}
