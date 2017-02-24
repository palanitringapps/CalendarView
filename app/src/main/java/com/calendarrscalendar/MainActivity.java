package com.calendarrscalendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.calendarrscalendar.calendar.CalendarPickerView;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    CalendarPickerView dialogView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialogView = (CalendarPickerView)findViewById(R.id.calendar_view);
        Calendar calendar = Calendar.getInstance();
        final Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 100);
        dialogView.init(calendar.getTime(), nextYear.getTime()) //
                .withSelectedDate(new Date());
    }
}
