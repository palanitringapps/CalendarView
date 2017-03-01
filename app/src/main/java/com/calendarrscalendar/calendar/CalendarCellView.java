package com.calendarrscalendar.calendar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.calendarrscalendar.R;


public class CalendarCellView extends FrameLayout {
    private static final int[] STATE_SELECTABLE = {
            R.attr.tsquare_state_selectable
    };
    private static final int[] STATE_CURRENT_MONTH = {
            R.attr.tsquare_state_current_month
    };

    private static final int[] STATE_CURRENT_MONTH_LABLE = {
            R.attr.state_title
    };
    private static final int[] STATE_TODAY = {
            R.attr.tsquare_state_today
    };
    private static final int[] STATE_HIGHLIGHTED = {
            R.attr.tsquare_state_highlighted
    };
    private static final int[] STATE_RANGE_FIRST = {
            R.attr.tsquare_state_range_first
    };
    private static final int[] STATE_RANGE_MIDDLE = {
            R.attr.tsquare_state_range_middle
    };
    private static final int[] STATE_RANGE_LAST = {
            R.attr.tsquare_state_range_last
    };

    private boolean isSelectable = false;
    private boolean isCurrentMonth = false;
    private boolean isToday = false;
    private boolean isHighlighted = false;
    private boolean isTitleText = false;
    private MonthCellDescriptor.RangeState rangeState = MonthCellDescriptor.RangeState.NONE;
    private TextView dayOfMonthTextView;
    private View line;
    private View scheduledDateIndicator;

    @SuppressWarnings("UnusedDeclaration") //
    public CalendarCellView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setSelectable(boolean isSelectable) {
        if (this.isSelectable != isSelectable) {
            this.isSelectable = isSelectable;
            refreshDrawableState();
        }
    }

    public void setCurrentMonth(boolean isCurrentMonth) {
        if (this.isCurrentMonth != isCurrentMonth) {
            this.isCurrentMonth = isCurrentMonth;
            refreshDrawableState();
        }
    }

    public void setToday(boolean isToday) {
        if (this.isToday != isToday) {
            this.isToday = isToday;
            refreshDrawableState();
        }
    }

    public void setTitleValue(boolean isTitleText) {
        if (this.isTitleText != isTitleText) {
            if (dayOfMonthTextView != null) {
                dayOfMonthTextView.setTextSize(14);
                dayOfMonthTextView.setTextColor(getResources().getColor(R.color.title_month));
                this.line.setVisibility(GONE);
                this.scheduledDateIndicator.setVisibility(GONE);
            }
        }
    }

    public void setRangeState(MonthCellDescriptor.RangeState rangeState) {
        if (this.rangeState != rangeState) {
            this.rangeState = rangeState;
            refreshDrawableState();
        }
    }

    public void setHighlighted(boolean isHighlighted) {
        if (this.isHighlighted != isHighlighted) {
            this.isHighlighted = isHighlighted;
            refreshDrawableState();
        }
    }

    public boolean isCurrentMonth() {
        return isCurrentMonth;
    }

    public boolean isToday() {
        return isToday;
    }

    public boolean isSelectable() {
        return isSelectable;
    }

    public boolean isHighlighted() {
        return isHighlighted;
    }

    public MonthCellDescriptor.RangeState getRangeState() {
        return rangeState;
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 5);

        if (isSelectable) {
            mergeDrawableStates(drawableState, STATE_SELECTABLE);
        }

        if (isCurrentMonth) {
            mergeDrawableStates(drawableState, STATE_CURRENT_MONTH);
        }

    /*if (isToday) {
      mergeDrawableStates(drawableState, STATE_TODAY);
    }*/

        if (isHighlighted) {
            mergeDrawableStates(drawableState, STATE_HIGHLIGHTED);
        }

        if (rangeState == MonthCellDescriptor.RangeState.FIRST) {
            mergeDrawableStates(drawableState, STATE_RANGE_FIRST);
        } else if (rangeState == MonthCellDescriptor.RangeState.MIDDLE) {
            mergeDrawableStates(drawableState, STATE_RANGE_MIDDLE);
        } else if (rangeState == MonthCellDescriptor.RangeState.LAST) {
            mergeDrawableStates(drawableState, STATE_RANGE_LAST);
        }

        return drawableState;
    }

    public void setDayOfMonthTextView(TextView textView) {
        dayOfMonthTextView = textView;
    }

    public void setDashLineView(View dashLineView,View scheduleIndicator) {
        this.line = dashLineView;
        this.scheduledDateIndicator = scheduleIndicator;
    }


    public TextView getDayOfMonthTextView() {
        if (dayOfMonthTextView == null) {
            throw new IllegalStateException(
                    "You have to setDayOfMonthTextView in your custom DayViewAdapter."
            );
        }
        return dayOfMonthTextView;
    }
}
