package com.transsnet.kika.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Author:  zengfeng
 * Time  :  2020/7/20 14:10
 * Des   :  单个音轨View
 */
public class SingVoiceView extends View {
    public SingVoiceView(Context context) {
        super(context);
        move();
    }

    public SingVoiceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        measureWidth = widthSize;
        measureHeight = heightSize;
        int realWidth = 0, realHeight = 0;
        log("measureWidth =" + widthSize + ",measureHeight =" + heightSize);
        if (widthMode == MeasureSpec.EXACTLY) { //MatchParent,100dp
            realWidth = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {//wrap_content
            realWidth = getMeasuredWidth();
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            realHeight = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            realHeight = getMeasuredHeight();
        }

        setMeasuredDimension(realWidth, realHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        int left =0;
//        int top =measureHeight / 2 - 20;
//        int right = measureWidth - 200;
//        int bottom = measureHeight / 2 + 20;
        mRect.set(0, measureHeight / 2 - 20, measureWidth, measureHeight / 2 + 20);
        canvas.drawRect(mRect, paint);
    }

    int measureWidth = 0, measureHeight = 0;
    Paint paint = new Paint();
    Rect mRect = new Rect(0, 0, 0, 0);
    Random random = new Random();

    public void log(String log) {
        Log.i("vivi", log);
    }


    Timer timer = new Timer();

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            measureWidth = random.nextInt(360);
            postInvalidate();
        }
    };

    private void move() {
        timer.schedule(task, 0, 200);
    }

    public void setColor(int color){
        paint.setColor(color);
    }

    private void initUI() {
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        move();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initUI();
    }

    public void releaseView() {
        if (task != null) {
            task.cancel();
            task = null;
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
