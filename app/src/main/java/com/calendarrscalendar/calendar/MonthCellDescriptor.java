package com.calendarrscalendar.calendar;

import java.util.Date;

/** Describes the state of a particular date cell in a {@link MonthView}. */
class MonthCellDescriptor {
  enum RangeState {
    NONE, FIRST, MIDDLE, LAST
  }

  private final Date date;
  private final int value;
  private final boolean isCurrentMonth;
  private boolean isSelected;
  private final boolean isToday;
  private final boolean isSelectable;
  private boolean isHighlighted;
  private RangeState rangeState;

  MonthCellDescriptor(Date date, boolean currentMonth, boolean selectable, boolean selected,
                      boolean today, boolean highlighted, int value, RangeState rangeState) {
    this.date = date;
    isCurrentMonth = currentMonth;
    isSelectable = selectable;
    isHighlighted = highlighted;
    isSelected = selected;
    isToday = today;
    this.value = value;
    this.rangeState = rangeState;
  }

  Date getDate() {
    return date;
  }

  boolean isCurrentMonth() {
    return isCurrentMonth;
  }

  boolean isSelectable() {
    return isSelectable;
  }

  boolean isSelected() {
    return isSelected;
  }

  void setSelected(boolean selected) {
    isSelected = selected;
  }

  boolean isHighlighted() {
    return isHighlighted;
  }

  void setHighlighted(boolean highlighted) {
    isHighlighted = highlighted;
  }

  boolean isToday() {
    return isToday;
  }

  RangeState getRangeState() {
    return rangeState;
  }

  void setRangeState(RangeState rangeState) {
    this.rangeState = rangeState;
  }

  int getValue() {
    return value;
  }

  @Override
  public String toString() {
    return "MonthCellDescriptor{"
        + "date="
        + date
        + ", value="
        + value
        + ", isCurrentMonth="
        + isCurrentMonth
        + ", isSelected="
        + isSelected
        + ", isToday="
        + isToday
        + ", isSelectable="
        + isSelectable
        + ", isHighlighted="
        + isHighlighted
        + ", rangeState="
        + rangeState
        + '}';
  }
}
