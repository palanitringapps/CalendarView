package com.calendarrscalendar.calendar;

/** Adapter used to provide a layout for {@link CalendarCellView}.*/
public interface DayViewAdapter {
  void makeCellView(CalendarCellView parent,boolean available);
}
