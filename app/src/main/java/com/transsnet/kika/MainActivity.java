package com.transsnet.kika;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.transsnet.kika.R;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    private EditText et;
   MyHandler myHandler ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Message msg =Message.obtain();
        msg.arg1 =2;
        msg.what =1;
        msg.obj ="obj";
        myHandler =new MyHandler(new WeakReference<Activity>(this));


        mHander.sendMessageDelayed(msg,300000);
//        et = findViewById(R.id.et);
//
//
//        et.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                log("afterTextChanged - " + s.toString());
//            }
//        });
//
//
//        et.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                log("onKey-- "+event.getKeyCode());
//                return false;
//            }
//        });
    }

    public  void log(String log) {
        Log.d("vivi", log);
    }

    Handler mHander = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            log("hhhh");
        }
    };

    static class MyHandler  extends Handler{
       WeakReference<Activity> mWeakReference;
       Activity mActivity;
        public MyHandler(WeakReference<Activity> weakReference){
            mWeakReference = weakReference;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            mActivity= mWeakReference.get();
            if(mActivity!=null && mActivity instanceof MainActivity && !mActivity.isFinishing()){
               ((MainActivity) mActivity).log("ss");
            }
        }
    }
}
