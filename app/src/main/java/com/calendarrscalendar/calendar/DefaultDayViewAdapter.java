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
    /*@Override
    public void makeCellView(CalendarCellView parent,boolean isAvailable,boolean isTitle) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_view, parent, false);
        TextView textView = (TextView) view.findViewById(R.id.day);
        View availableIndicator = view.findViewById(R.id.date_schedule_view);
        View dashView = view.findViewById(R.id.vi_dash);
        if(isTitle)
        {
            dashView.setVisibility(View.GONE);
            textView.setTextColor(view.getResources().getColor(R.color.title_month));
            textView.setTextSize(15);
        }
        availableIndicator.setVisibility(isAvailable ? View.VISIBLE : View.GONE);
        parent.addView(view);

        parent.setDayOfMonthTextView(textView);
    }*/

    @Override
    public void makeCellView(CalendarCellView parent, boolean isAvailable) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_view, parent, false);
        TextView textView = (TextView) view.findViewById(R.id.day);
        View availableIndicator = view.findViewById(R.id.date_schedule_view);
        View dashView = view.findViewById(R.id.vi_dash);
        TextView monthLabel = (TextView) view.findViewById(R.id.month_name);
        availableIndicator.setVisibility(isAvailable ? View.VISIBLE : View.GONE);
        parent.addView(view);

        parent.setDayOfMonthTextView(textView);
        parent.setDashLineView(dashView, availableIndicator);
        parent.setMonthLabelTextView(monthLabel);
    }
}
