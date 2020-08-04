package com.transsnet.kika.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Author:  zengfeng
 * Time  :  2020/7/29 16:07
 * Des   :
 */
public class UserModel extends ViewModel {
    MutableLiveData<User> mUserLiveData;

    public void setUserLiveData(User user) {
        if (mUserLiveData == null) {
            mUserLiveData = new MutableLiveData<>();
        }
        mUserLiveData.setValue(user);
    }

    public LiveData<User> getUserLiveData() {
        if (mUserLiveData == null) {
            mUserLiveData = new MutableLiveData<>();
        }
        return mUserLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d("vivi","UserModel onCleared");
    }
}
