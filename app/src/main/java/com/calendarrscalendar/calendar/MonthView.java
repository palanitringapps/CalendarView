// Copyright 2012 Square, Inc.
package com.calendarrscalendar.calendar;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.calendarrscalendar.R;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MonthView extends LinearLayout {
    TextView title;
    CalendarGridView grid;
    //CalendarGridView titleGrid;
    private Listener listener;
    private List<CalendarCellDecorator> decorators;
    private boolean isRtl;
    private Locale locale;

    public static MonthView create(ViewGroup parent, LayoutInflater inflater,
                                   DateFormat weekdayNameFormat, Listener listener, Calendar today, int dividerColor,
                                   int dayBackgroundResId, int dayTextColorResId, int titleTextColor, boolean displayHeader,
                                   int headerTextColor, Locale locale, DayViewAdapter adapter) {
        return create(parent, inflater, weekdayNameFormat, listener, today, dividerColor,
                dayBackgroundResId, dayTextColorResId, titleTextColor, displayHeader, headerTextColor, null,
                locale, adapter, 2);
    }

    public static MonthView create(ViewGroup parent, LayoutInflater inflater,
                                   DateFormat weekdayNameFormat, Listener listener, Calendar today, int dividerColor,
                                   int dayBackgroundResId, int dayTextColorResId, int titleTextColor, boolean displayHeader,
                                   int headerTextColor, List<CalendarCellDecorator> decorators, Locale locale,
                                   DayViewAdapter adapter, int dateScheduled) {
        final MonthView view = (MonthView) inflater.inflate(R.layout.month, parent, false);
        view.setDayViewAdapter(adapter, dateScheduled);
        view.setDividerColor(dividerColor);
        view.setDayTextColor(dayTextColorResId);
        view.setTitleTextColor(titleTextColor);
        view.setDisplayHeader(displayHeader);
        view.setHeaderTextColor(headerTextColor);

        if (dayBackgroundResId != 0) {
            view.setDayBackground(dayBackgroundResId);
        }

        final int originalDayOfWeek = today.get(Calendar.DAY_OF_WEEK);

        view.isRtl = isRtl(locale);
        view.locale = locale;
        int firstDayOfWeek = today.getFirstDayOfWeek();
        final CalendarRowView headerRow = (CalendarRowView) view.grid.getChildAt(0);
        for (int offset = 0; offset < 7; offset++) {
            today.set(Calendar.DAY_OF_WEEK, getDayOfWeek(firstDayOfWeek, offset, view.isRtl));
            //final TextView textView = (TextView) headerRow.getChildAt(offset);
            //textView.setText(weekdayNameFormat.format(today.getTime()));
        }
        today.set(Calendar.DAY_OF_WEEK, originalDayOfWeek);
        view.listener = listener;
        view.decorators = decorators;
        return view;
    }

    private static int getDayOfWeek(int firstDayOfWeek, int offset, boolean isRtl) {
        int dayOfWeek = firstDayOfWeek + offset;
        if (isRtl) {
            return 8 - dayOfWeek;
        }
        return dayOfWeek;
    }

    private static boolean isRtl(Locale locale) {
        // TODO convert the build to gradle and use getLayoutDirection instead of this (on 17+)?
        final int directionality = Character.getDirectionality(locale.getDisplayName(locale).charAt(0));
        return directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT
                || directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC;
    }

    public MonthView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setDecorators(List<CalendarCellDecorator> decorators) {
        this.decorators = decorators;
    }

    public List<CalendarCellDecorator> getDecorators() {
        return decorators;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        title = (TextView) findViewById(R.id.title);
        grid = (CalendarGridView) findViewById(R.id.calendar_grid);
        //titleGrid = ((CalendarGridView) findViewById(R.id.calendar_title_row));
    }


    /*public void initTitle(MonthDescriptor month, List<List<MonthCellDescriptor>> cells,
                          boolean displayOnly, Typeface title, Typeface dateTypeface) {
        titleGrid.setNumRows(0);
        CalendarRowView titleView = (CalendarRowView) titleGrid.getChildAt(0);
        List<MonthCellDescriptor> weekRow = cells.get(0);
        for (int c = 0; c < weekRow.size(); c++) {
            MonthCellDescriptor cell = weekRow.get(isRtl ? 6 - c : c);
            CalendarCellView cellView = (CalendarCellView) titleView.getChildAt(c);
            if (cell.isCurrentMonth()) {
                cellView.getDayOfMonthTextView().setText(month.getLabel());
                return;
            } else {
                cellView.setVisibility(INVISIBLE);
            }
        }
    }*/


    public void init(MonthDescriptor month, List<List<MonthCellDescriptor>> cells,
                     boolean displayOnly, Typeface titleTypeface, Typeface dateTypeface) {
        Logr.d("Initializing MonthView (%d) for %s", System.identityHashCode(this), month);
        long start = System.currentTimeMillis();
        title.setText(month.getLabel());
        NumberFormat numberFormatter = NumberFormat.getInstance(locale);

        final int numRows = cells.size() + 1;
        grid.setNumRows(numRows);
        /*for (int i = 0; i < 6; i++)*/
        for (int i = 0; i < 7; i++) {
            CalendarRowView weekRow = (CalendarRowView) grid.getChildAt(i + 1);
            weekRow.setListener(listener);
            if (i < numRows) {
                weekRow.setVisibility(VISIBLE);
                if (i == 0) {
                    List<MonthCellDescriptor> monthTitle = cells.get(0);
                    boolean isFirstTime = true;
                    for (int c = 0; c < monthTitle.size(); c++) {
                        MonthCellDescriptor cell = monthTitle.get(isRtl ? 6 - c : c);
                        CalendarCellView cellView = (CalendarCellView) weekRow.getChildAt(c);
                        if (cell.isCurrentMonth() && isFirstTime) {
                            cellView.getDayOfMonthTextView().setText(month.getLabel());
                            cellView.setVisibility(cell.isCurrentMonth() ? VISIBLE : INVISIBLE);
                            cellView.setClickable(false);
                            cellView.setSelectable(false);
                            cellView.setSelected(false);
                            cellView.setCurrentMonth(cell.isCurrentMonth());
                            cellView.setRangeState(cell.getRangeState());
                            cellView.setHighlighted(cell.isHighlighted());
                            cellView.setTag(cell);
                            cellView.setTitleValue(true);
                            isFirstTime = false;
                            if (null != decorators) {
                                for (CalendarCellDecorator decorator : decorators) {
                                    decorator.decorate(cellView, cell.getDate());
                                }
                            }
                        } else {
                            cellView.setVisibility(INVISIBLE);
                            cellView.setTag(cell);
                        }
                    }
                } else {
                    List<MonthCellDescriptor> week = cells.get(i - 1);
                    for (int c = 0; c < week.size(); c++) {
                        MonthCellDescriptor cell = week.get(isRtl ? 6 - c : c);
                        CalendarCellView cellView = (CalendarCellView) weekRow.getChildAt(c);

                        String cellDate = numberFormatter.format(cell.getValue());
                        if (!cellView.getDayOfMonthTextView().getText().equals(cellDate)) {
                            cellView.getDayOfMonthTextView().setText(cellDate);
                        }
                        cellView.setEnabled(cell.isCurrentMonth());
                        cellView.setVisibility(cell.isCurrentMonth() ? VISIBLE : INVISIBLE);
                        cellView.setClickable(!displayOnly);

                        cellView.setSelectable(cell.isSelectable());
                        cellView.setSelected(cell.isSelected());
                        cellView.setCurrentMonth(cell.isCurrentMonth());
                        cellView.setToday(cell.isToday());
                        cellView.setTitleValue(false);
                        cellView.setRangeState(cell.getRangeState());
                        cellView.setHighlighted(cell.isHighlighted());
                        cellView.setTag(cell);

                        if (null != decorators) {
                            for (CalendarCellDecorator decorator : decorators) {
                                decorator.decorate(cellView, cell.getDate());
                            }
                        }
                    }
                }
            } else {
                weekRow.setVisibility(GONE);
            }
        }

        if (titleTypeface != null) {
            title.setTypeface(titleTypeface);
        }
        if (dateTypeface != null) {
            grid.setTypeface(dateTypeface);
        }

        Logr.d("MonthView.init took %d ms", System.currentTimeMillis() - start);
    }

    public void setDividerColor(int color) {
        grid.setDividerColor(color);
    }

    public void setDayBackground(int resId) {
        grid.setDayBackground(resId);
    }

    public void setDayTextColor(int resId) {
        grid.setDayTextColor(resId);
    }

    public void setDayViewAdapter(DayViewAdapter adapter, int scheduleddate) {
        grid.setDayViewAdapter(adapter, scheduleddate);
        //titleGrid.setDayViewAdapter(adapter, scheduleddate);
    }

    public void setTitleTextColor(int color) {
        title.setTextColor(color);
    }

    public void setDisplayHeader(boolean displayHeader) {
        grid.setDisplayHeader(displayHeader);
    }

    public void setHeaderTextColor(int color) {
        grid.setHeaderTextColor(color);
    }

    public interface Listener {
        void handleClick(MonthCellDescriptor cell);
    }
}
