package com.calendarrscalendar.calendar;

import java.util.Date;

class MonthDescriptor {
  private final int month;
  private final int year;
  private final Date date;
  private String label;

  MonthDescriptor(int month, int year, Date date, String label) {
    this.month = month;
    this.year = year;
    this.date = date;
    this.label = label;
  }

  int getMonth() {
    return month;
  }

  int getYear() {
    return year;
  }

  Date getDate() {
    return date;
  }

  String getLabel() {
    return label.substring(0,3).toUpperCase();
  }

  void setLabel(String label) {
    this.label = label;
  }

  @Override
  public String toString() {
    return "MonthDescriptor{"
        + "label='"
        + label
        + '\''
        + ", month="
        + month
        + '}';
  }
}
