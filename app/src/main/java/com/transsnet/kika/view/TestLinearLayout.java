package com.transsnet.kika.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.transsnet.kika.util.LogUtils;

/**
 * Author:  zengfeng
 * Time  :  2020/12/18 13:58
 * Des   :
 */
public class TestLinearLayout extends LinearLayout {
    public TestLinearLayout(Context context) {
        super(context);
    }

    public TestLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtils.d("TestLinearLayout  dispatchTouchEvent"+ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        LogUtils.i("TestLinearLayout  onInterceptTouchEvent"+ev.getAction());
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtils.v("TestLinearLayout  onTouchEvent"+event.getAction());
        return super.onTouchEvent(event);
    }
}
