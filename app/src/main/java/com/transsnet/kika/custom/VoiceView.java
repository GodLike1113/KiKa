package com.transsnet.kika.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Author:  zengfeng
 * Time  :  2020/4/16 17:02
 * Des   :  声音的自定义控件
 */
public class VoiceView extends LinearLayout {

    private Paint mPaint;

    public VoiceView(Context context) {
        super(context);
        init();
    }

    public VoiceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VoiceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        log("父l ="+l+",t ="+t+",r = "+r+",b = "+b);
        int childCount = getChildCount();
        int top = t; //子控件距离顶端20pix
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            child.layout(l, top, childWidth + l, top + childHeight);
            top = top + childHeight;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);

        log("measureWidth ="+measureWidth+",measureHeight ="+measureHeight);
        int childCount = getChildCount();
        int width = 0;
        int height = 0;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);

            int childMeasuredWidth = child.getMeasuredWidth();
            int childMeasuredHeight = child.getMeasuredHeight();
            height = height + childMeasuredHeight;

            width = Math.max(width, childMeasuredWidth);
        }

        int realMeasureWidth = measureWidthMode == MeasureSpec.EXACTLY ? measureWidth : width;
        int realMeasureHeight = measureHeightMode == MeasureSpec.EXACTLY ? measureHeight : height;
        setMeasuredDimension(realMeasureWidth, realMeasureHeight);
    }

    private void init() {
//        mPaint = new Paint();
//        mPaint.setColor(Color.parseColor("#302D58"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        canvas.drawCircle(50,50,40,mPaint);
        canvas.drawRect(50,50,100,100,mPaint);

//        RectF rectF = new RectF()
//        canvas.drawColor(Color.parseColor("#302D58"));
    }

    public void log(String log){
        Log.i("vivi",log);
    }
}
