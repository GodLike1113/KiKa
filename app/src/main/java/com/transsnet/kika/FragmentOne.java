package com.transsnet.kika;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * Author:  zengfeng
 * Time  :  2020/8/4 15:17
 * Des   :
 */
public class FragmentOne extends Fragment {
    String TAG = "vivi";
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
        event.put(CalendarContract.Events.TITLE, "Palmcredit还钱提醒");
        event.put(CalendarContract.Events.DESCRIPTION, "今天到了还钱的时间了");
        //插入hoohbood@gmail.com这个账户
        event.put(CalendarContract.Events.CALENDAR_ID, calId);

//        Calendar mCalendar = Calendar.getInstance();
//        mCalendar.set(Calendar.HOUR_OF_DAY,11);
//        long start = mCalendar.getTime().getTime();
//        mCalendar.set(Calendar.HOUR_OF_DAY,12);
//        long end = mCalendar.getTime().getTime();
//        long start = 0, end = 0;
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Date parse = sdf.parse("2020-09-18 14:14:00");
//            start = parse.getTime();
//            end = start + 10 * 60 * 1000;
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }


        long start = 0, end = 0;
        start = new Date().getTime() + 10 * 60 * 1000;
        end = start + 10 * 60 * 1000;

        event.put(CalendarContract.Events.DTSTART, start);
        event.put(CalendarContract.Events.DTEND, end);
        event.put(CalendarContract.Events.HAS_ALARM, 1);
        event.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());
//        event.put("rrule", "FREQ=DAILY;COUNT=1");

        Uri newEvent = getActivity().getContentResolver().insert(Uri.parse(calanderEventURL), event);
        long id = Long.parseLong(newEvent.getLastPathSegment());
        ContentValues values = new ContentValues();
        values.put("event_id", id);
//        //提前10分钟有提醒
        values.put("minutes", 5);
        getActivity().getContentResolver().insert(Uri.parse(calanderRemiderURL), values);
        Toast.makeText(getActivity(), "插入事件成功!!!", Toast.LENGTH_LONG).show();

        getCalendarEvent();
    }


    private static String CALENDER_EVENT_URL = "content://com.android.calendar/events";

    //获得日历数据
    private void getCalendarEvent() {
        String startTime = "";
        String endTime = "";
        String eventTitle = "";
        String description = "";
        String location = "";

        long startEventTime;
        long currentTime;
        List<Long> listTime;
        listTime = new ArrayList<Long>();
        Map map = new HashMap();//创建Map 集合


        Cursor eventCursor = getActivity().getContentResolver().query(Uri.parse(CALENDER_EVENT_URL), null,
                null, null, null);
        while (eventCursor.moveToNext()) {
            Log.i(TAG, "·········································· ");
            eventTitle = eventCursor.getString(eventCursor.getColumnIndex("title"));    //获取日历事件 的标题
            description = eventCursor.getString(eventCursor.getColumnIndex("description"));  //获取日历事件 的描述
            location = eventCursor.getString(eventCursor.getColumnIndex("eventLocation"));  //获取日历事件 的地点
            startEventTime = Long.parseLong(eventCursor.getString(eventCursor.getColumnIndex("dtstart"))); //获取 日程 开始的时间
            Log.i(TAG, "startEventTime：  " + startEventTime);
            currentTime = Calendar.getInstance().getTimeInMillis();  //获取当前时间
            Log.i(TAG, "currentTime：  " + currentTime);
            if (startEventTime > currentTime) {  //当日历设定时间大于当前时间
                listTime.add(startEventTime);
                map.put(startEventTime, description);//存储键值
            }
            startTime = timeStamp2Date(startEventTime);
            endTime = timeStamp2Date(Long.parseLong(eventCursor.getString(eventCursor.getColumnIndex("dtend")))); //获取日程结束的时间
            Log.i(TAG, "eventTitle: " + eventTitle + "\n" +
                    "description: " + description + "\n" +
                    "location: " + location + "\n" +
                    "startTime: " + startTime + "\n" +
                    "endTime: " + endTime + "\n"
            );
        }
        Collections.sort(listTime); //将list 从小到大排序 根据时间大小 获取最近事件
        Object value = map.get(listTime.get(0)); //获取键所对应的值
        Log.i(TAG, "获取最近一次事件：" + value);
    }


    /**
     * 时间戳转换为字符串
     *
     * @param time:时间戳
     * @return
     */
    private static String timeStamp2Date(long time) {
        String format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(time));
    }

}
