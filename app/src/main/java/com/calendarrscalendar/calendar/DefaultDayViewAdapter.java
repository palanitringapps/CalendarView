package com.calendarrscalendar.calendar;

import android.graphics.Color;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.calendarrscalendar.R;

public class DefaultDayViewAdapter implements DayViewAdapter {
    @Override
    public void makeCellView(CalendarCellView parent,boolean isAvailable) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_view, parent, false);
        TextView textView = (TextView) view.findViewById(R.id.day);
        View availableIndicator = view.findViewById(R.id.date_schedule_view);
        availableIndicator.setVisibility(isAvailable ? View.VISIBLE : View.GONE);
        parent.addView(view);

        parent.setDayOfMonthTextView(textView);
    }
}
