package com.transsnet.kika;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.transsnet.kika.viewmodel.SeekBarViewModel;

/**
 * Author:  zengfeng
 * Time  :  2020/8/4 15:17
 * Des   :
 */
public class FragmentTwo extends Fragment {

    private SeekBar mSeekBar2;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment2_layout, null);
        mSeekBar2= rootView.findViewById(R.id.seekbar2);

        viewModelProvider = new ViewModelProvider(getActivity());
        SeekBarViewModel seekBarViewModel = viewModelProvider.get(SeekBarViewModel.class);

        mSeekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBarViewModel.setSeekBarLiveData(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        return rootView;
    }

    ViewModelProvider viewModelProvider;
//    private void initLiveData() {
//
//        seekBarViewModel.getSeekBarLiveData().observe(this, new Observer<Integer>() {
//            @Override
//            public void onChanged(Integer integer) {
//                mSeekBar1.setProgress(integer.intValue());
//            }
//        });
//    }


//    private void initLiveData() {
//        ViewModelProvider  viewModelProvider = new ViewModelProvider(this);
//        SeekBarViewModel seekBarViewModel = viewModelProvider.get(SeekBarViewModel.class);
//        seekBarViewModel.getSeekBarLiveData().observe(this, new Observer<Integer>() {
//            @Override
//            public void onChanged(Integer integer) {
//                Log.d("vivi","two:"+integer);
//            }
//        });
//    }
}
