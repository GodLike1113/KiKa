package com.transsnet.kika;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.transsnet.kika.viewmodel.SeekBarViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

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
                if (integer.intValue() == 100) {
                    System.out.println("进度条100");
                    insertCalendar();
                }
            }
        });
    }

    String calanderURL = "content://com.android.calendar/calendars";
    String calanderEventURL = "content://com.android.calendar/events";
    String calanderRemiderURL = "content://com.android.calendar/reminders";

    public void insertCalendar() {
        String calId = "";
        Cursor userCursor = getActivity().getContentResolver().query(Uri.parse(calanderURL), null,
                null, null, null);
        if (userCursor.getCount() > 0) {
            userCursor.moveToFirst();
            calId = userCursor.getString(userCursor.getColumnIndex("_id"));

        }
        ContentValues event = new ContentValues();
        event.put(CalendarContract.Events.TITLE, "标题");
        event.put(CalendarContract.Events.DESCRIPTION, "描述~~~~~~~~");
        //插入hoohbood@gmail.com这个账户
        event.put(CalendarContract.Events.CALENDAR_ID, calId);

//        Calendar mCalendar = Calendar.getInstance();
//        mCalendar.set(Calendar.HOUR_OF_DAY,11);
//        long start = mCalendar.getTime().getTime();
//        mCalendar.set(Calendar.HOUR_OF_DAY,12);
//        long end = mCalendar.getTime().getTime();
        long start = 0, end = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date parse = sdf.parse("2020-09-18 11:39:00");
            start = parse.getTime();
            end = start + 10 * 60 * 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }


        event.put(CalendarContract.Events.DTSTART, start);
        event.put(CalendarContract.Events.DTEND, end);
        event.put(CalendarContract.Events.HAS_ALARM, 1);
        event.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());

        Uri newEvent = getActivity().getContentResolver().insert(Uri.parse(calanderEventURL), event);
        long id = Long.parseLong(newEvent.getLastPathSegment());
        ContentValues values = new ContentValues();
        values.put("event_id", id);
        //提前10分钟有提醒
        values.put("minutes", 5);
        getActivity().getContentResolver().insert(Uri.parse(calanderRemiderURL), values);
        Toast.makeText(getActivity(), "插入事件成功!!!", Toast.LENGTH_LONG).show();
    }

}
