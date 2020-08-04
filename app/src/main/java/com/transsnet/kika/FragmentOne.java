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
public class FragmentOne extends Fragment {

    private SeekBar mSeekBar1;
    private ViewModelProvider viewModelProvider;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment1_layout, null);
        mSeekBar1 = rootView.findViewById(R.id.seekbar1);

        mSeekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        initLiveData();
        return rootView;
    }

    private void initLiveData() {
        viewModelProvider = new ViewModelProvider(getActivity());
        SeekBarViewModel seekBarViewModel = viewModelProvider.get(SeekBarViewModel.class);
        seekBarViewModel.getSeekBarLiveData().observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                mSeekBar1.setProgress(integer.intValue());
            }
        });
    }


}
