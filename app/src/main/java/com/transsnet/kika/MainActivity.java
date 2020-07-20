package com.transsnet.kika;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProvider;

import com.transsnet.kika.custom.SingVoiceView;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    private EditText et;
   MyHandler myHandler ;
    private LinearLayout container;
    private SingVoiceView voiceView;
    private SingVoiceView voiceView1;
    private SingVoiceView voiceView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Message msg =Message.obtain();
        msg.arg1 =2;
        msg.what =1;
        msg.obj ="obj";
        myHandler =new MyHandler(new WeakReference<Activity>(this));
        myHandler.sendMessage(msg);
//        ViewModelProvider viewModelProvider =  new ViewModelProviders(this);

//        mHander.sendMessageDelayed(msg,300000);
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

        container = findViewById(R.id.container);
        test();
    }

    public  void log(String log) {
        Log.d("vivi", log);
    }

//    Handler mHander = new Handler(){
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            super.handleMessage(msg);
//            log("hhhh");
//        }
//    };


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


    public void test(){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,80);
        voiceView = new SingVoiceView(this);
        voiceView.setColor(Color.GREEN);
        voiceView.setLayoutParams(params);
        voiceView1 = new SingVoiceView(this);
        voiceView1.setColor(Color.BLUE);
        voiceView1.setLayoutParams(params);
        voiceView2 = new SingVoiceView(this);
        voiceView2.setColor(Color.BLACK);
        voiceView2.setLayoutParams(params);

        container.addView(voiceView);
        container.addView(voiceView1);
        container.addView(voiceView2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        voiceView.releaseView();
        voiceView = null;
        voiceView1.releaseView();
        voiceView1 = null;
        voiceView2.releaseView();
        voiceView2 = null;

    }
}
