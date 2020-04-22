package com.transsnet.kika;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.transsnet.kika.custom.MyScrollView;
import com.transsnet.kika.custom.SoftKeyBoardListener;

/**
 * Author:  zengfeng
 * Time  :  2020/4/13 11:16
 * Des   :
 */
public class HomeActivity extends Activity implements View.OnClickListener {

    private MyScrollView scrollView;
    private EditText et1;
    private EditText et2;
    private EditText et3;
    private EditText et4;
    private EditText et5;
    private TextView et6;
    private EditText et7;
    private EditText et8;
    private EditText et9;
    private EditText et10;

    private int mScreenWidth, mScreenHeight, mSoftkeyboardWidth, mSoftKeyboardHeight, mLastScrollY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        initView();

        initListener();
    }

    private void initView() {
        scrollView = findViewById(R.id.scrollview);

        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        et3 = findViewById(R.id.et3);
        et4 = findViewById(R.id.et4);
        et5 = findViewById(R.id.et5);
        et6 = findViewById(R.id.et6);
        et7 = findViewById(R.id.et7);
        et8 = findViewById(R.id.et8);
        et9 = findViewById(R.id.et9);
        et10 = findViewById(R.id.et10);
    }

    private void initListener() {
        et1.setOnClickListener(this);
        et2.setOnClickListener(this);
        et3.setOnClickListener(this);
        et4.setOnClickListener(this);
        et5.setOnClickListener(this);
        et6.setOnClickListener(this);
        et7.setOnClickListener(this);
        et8.setOnClickListener(this);
        et9.setOnClickListener(this);
        et10.setOnClickListener(this);

        scrollView.setOnScrollChangeListener(new MyScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {
                log("l =" + l + ",mLastScrollY =" + t + ",oldl =" + oldl + ",oldt =" + oldt);
                mLastScrollY = t;
            }
        });

        mScreenWidth = getScreenWidth();
        mScreenHeight = getScreenHeight();

        log("屏幕宽：" + mScreenWidth + ",屏幕高：" + mScreenHeight);

        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                mSoftKeyboardHeight = height;
                log("键盘显示 高度" + height + "软键盘显示");
            }

            @Override
            public void keyBoardHide(int height) {
                mSoftKeyboardHeight = 0;
                log("键盘隐藏 高度" + height + "软键盘隐藏");
            }
        });
    }

    public void calculate(View v) {
        int[] screenLocation = getScreenLocation(v);
        int locationY = screenLocation[1];//滚动前控件在屏幕Y坐标
        int desHeight = (mScreenHeight - mSoftKeyboardHeight) / 2;
        log("desHeight = " + desHeight + ",locationY =" + locationY + ",滚动距离：" + (locationY - desHeight));
        if (locationY - desHeight >= 0) {
            scrollToPosition(0, locationY - desHeight+mLastScrollY+v.getHeight()/2);
        }else{
            scrollToPosition(0, locationY - desHeight+mLastScrollY+v.getHeight()/2);
        }
    }

    public int[] getScreenLocation(View v) {
        int[] loaction = new int[2];
        v.getLocationOnScreen(loaction);
//        log("x =" + loaction[0] + ", y =" + loaction[1]);
        log("l =" + loaction[0] + ", t =" + loaction[1] + ",r =" + (v.getRight() + loaction[0]) + ",b =" + (v.getBottom() + loaction[1]));
        return loaction;
    }

    public void scrollToPosition(int x, int y) {
        ObjectAnimator xTranslate = ObjectAnimator.ofInt(scrollView, "scrollX", x);
        ObjectAnimator yTranslate = ObjectAnimator.ofInt(scrollView, "scrollY", y);

        AnimatorSet animators = new AnimatorSet();
        animators.setDuration(500L);
        animators.playTogether(xTranslate, yTranslate);
        animators.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationRepeat(Animator arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animator arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationCancel(Animator arg0) {
                // TODO Auto-generated method stub

            }
        });
        animators.start();
    }


    public void log(String log) {
        Log.i("vivi", log);
    }

    public int getScreenWidth() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) {
            return getResources().getDisplayMetrics().widthPixels;
        }
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.x;
    }

    /**
     * Return the height of screen, in pixel.
     *
     * @return the height of screen, in pixel
     */
    public int getScreenHeight() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) {
            return getResources().getDisplayMetrics().heightPixels;
        }
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.y;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et1:
            case R.id.et2:
            case R.id.et3:
            case R.id.et4:
            case R.id.et5:
            case R.id.et6:
            case R.id.et7:
            case R.id.et8:
            case R.id.et9:
            case R.id.et10:
                calculate(v);
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
