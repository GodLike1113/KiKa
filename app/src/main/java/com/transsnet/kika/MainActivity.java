package com.transsnet.kika;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.transsnet.kika.custom.SingVoiceView;
import com.transsnet.kika.custom.WatchView;
import com.transsnet.kika.glide.GlideUtil;
import com.transsnet.kika.util.OkhttpDownloadFileService;
import com.transsnet.kika.viewmodel.User;
import com.transsnet.kika.viewmodel.UserModel;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

   MyHandler myHandler ;
    private LinearLayout container;
    private SingVoiceView voiceView;
    private SingVoiceView voiceView1;
    private SingVoiceView voiceView2;
    private WatchView wv;

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

        container = findViewById(R.id.container);
        ImageView imageView = findViewById(R.id.imageview);
        wv = findViewById(R.id.wv);
        wv.start();
        String url ="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1595409534317&di=ba00cb4898785f627331cdada1386897&imgtype=0&src=http%3A%2F%2Fattachments.gfan.com%2Fforum%2F201604%2F23%2F002205xqdkj84gnw4oi85v.jpg";
        GlideUtil.loadPicture(this,url,imageView);
        test();

        Intent intent = new Intent(this, OkhttpDownloadFileService.class);
        startService(intent);

        setData();

    }

    public  void log(String log) {
        Log.d("vivi", log);
    }


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
        if(voiceView!=null){
            voiceView.releaseView();
            voiceView = null;
        }
        if(voiceView1!=null)
        voiceView1.releaseView();
        voiceView1 = null;
        if(voiceView2!=null)
        voiceView2.releaseView();
        voiceView2 = null;

        wv.recycleMemory();

    }


    private void setData() {
        User user  = new User();
        user.setAge(18);
        user.setName("lyq");
        UserModel userModel = new ViewModelProvider(this).get(UserModel.class);
        userModel.setUserLiveData(user);
    }
}
