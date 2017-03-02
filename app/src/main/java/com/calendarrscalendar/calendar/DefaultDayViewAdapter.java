package com.calendarrscalendar.calendar;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.calendarrscalendar.R;

class DefaultDayViewAdapter implements DayViewAdapter {

    @Override
    public void makeCellView(CalendarCellView parent, boolean isAvailable) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_view, parent, false);
        TextView textView = (TextView) view.findViewById(R.id.day);
        View availableIndicator = view.findViewById(R.id.date_schedule_view);
        TextView monthLabel = (TextView) view.findViewById(R.id.month_name);
        availableIndicator.setVisibility(isAvailable ? View.VISIBLE : View.GONE);
        parent.addView(view);
        parent.setDayOfMonthTextView(textView);
        parent.setMonthLabelTextView(monthLabel);
    }
}
