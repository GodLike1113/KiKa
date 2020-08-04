package com.transsnet.kika.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Author:  zengfeng
 * Time  :  2020/8/4 15:34
 * Des   :  两个Fragment之间共享的ViewModel
 */
public class SeekBarViewModel extends ViewModel {
    MutableLiveData<Integer> seekBarLiveData ;

    public void setSeekBarLiveData(Integer integer){
        if(seekBarLiveData == null){
            seekBarLiveData = new MutableLiveData<>();
        }
        seekBarLiveData.setValue(integer);
    }
    public MutableLiveData<Integer> getSeekBarLiveData(){
        if(seekBarLiveData == null){
            seekBarLiveData = new MutableLiveData<>();
        }
        return seekBarLiveData;
    }
}
