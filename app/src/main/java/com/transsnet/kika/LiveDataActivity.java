package com.transsnet.kika;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.transsnet.kika.viewmodel.SeekBarViewModel;

/**
 * Author:  zengfeng
 * Time  :  2020/7/29 17:18
 * Des   :
 */
public class LiveDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.livedata_layout);

        initView();

//        initData();
    }

    private void initView() {
        FragmentOne fragment1 = new FragmentOne();
        FragmentTwo fragment2 = new FragmentTwo();

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.container1,fragment1);
        transaction.add(R.id.container2,fragment2);
        transaction.commit();
    }

//    private void initData() {
//        initLiveData();
//    }
//
//    private void initLiveData() {
//        ViewModelProvider  viewModelProvider = new ViewModelProvider(this);
//        SeekBarViewModel seekBarViewModel = viewModelProvider.get(SeekBarViewModel.class);
//        seekBarViewModel.getSeekBarLiveData().observe(this, new Observer<Integer>() {
//            @Override
//            public void onChanged(Integer integer) {
//                Log.d("vivi","home:"+integer);
//            }
//        });
//    }

}
