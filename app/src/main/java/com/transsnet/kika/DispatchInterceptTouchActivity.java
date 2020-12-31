package com.transsnet.kika;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.transsnet.kika.util.LogUtils;
import com.transsnet.kika.view.TestLinearLayout;
import com.transsnet.kika.view.TestTextView;

/**
 * Author:  zengfeng
 * Time  :  2020/12/16 17:11
 * Des   :
 */
public class DispatchInterceptTouchActivity extends Activity implements View.OnClickListener, View.OnTouchListener {

    private TestTextView textView;
    private TestLinearLayout testLL;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intercepet_layout);

        initView();
    }

    private void initView() {
        textView = findViewById(R.id.text);
        testLL = findViewById(R.id.test_ll);
        textView.setOnClickListener(this);
        testLL.setOnClickListener(this);
        boldAndColorText();
        textView.setOnTouchListener(this);
        testLL.setOnTouchListener(this);
        LogUtils.d("textV ="+textView.isClickable()+",testLL ="+testLL.isClickable());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text:
                LogUtils.e("click TextView");
                break;
            case R.id.test_ll:
                LogUtils.e("click LinearLayout");
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.text:
                LogUtils.e("onTouch TextView ~");
                break;
            case R.id.test_ll:
                LogUtils.e("onTouch LinearLayout ~");
                break;
        }
        return false;
    }

    public void boldAndColorText(){
        SpannableString sStr = new SpannableString("还差2人成团");
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.color_302D58));
        sStr.setSpan(colorSpan, 2, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sStr.setSpan(new AbsoluteSizeSpan(17, true), 2, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sStr.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 2, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(sStr);
    }
}
