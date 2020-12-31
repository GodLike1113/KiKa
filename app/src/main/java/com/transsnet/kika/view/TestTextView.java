package com.transsnet.kika.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.transsnet.kika.util.LogUtils;


/**
 * Author:  zengfeng
 * Time  :  2020/12/18 13:51
 * Des   :
 */
public class TestTextView extends TextView {
    public TestTextView(Context context) {
        super(context);
    }

    public TestTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        LogUtils.d("TestTextView  dispatchTouchEvent"+event.getAction());
        getParent().requestDisallowInterceptTouchEvent(false);
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtils.i("TestTextView  onTouchEvent"+event.getAction());
        return true;
    }
}
