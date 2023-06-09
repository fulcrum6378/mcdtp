package ir.mahdiparastesh.mcdtp.date;

import android.icu.util.Calendar;
import android.icu.util.TimeZone;

import java.util.Locale;

import ir.mahdiparastesh.mcdtp.FontCustomiser;

public interface DatePickerController<CAL extends Calendar> extends FontCustomiser {

    void onYearSelected(int year);

    void onDayOfMonthSelected(int year, int month, int day);

    void registerOnDateChangedListener(DatePickerDialog.OnDateChangedListener listener);

    @SuppressWarnings("unused")
    void unregisterOnDateChangedListener(DatePickerDialog.OnDateChangedListener listener);

    MonthAdapter.CalendarDay<CAL> getSelectedDay();

    boolean isHighlighted(int year, int month, int day);

    int getFirstDayOfWeek();

    int getMinYear();

    int getMaxYear();

    CAL getStartDate();

    CAL getEndDate();

    boolean isOutOfRange(int year, int month, int day);

    void tryVibrate();

    TimeZone getTimeZone();

    Locale getLocale();

    DatePickerDialog.Version getVersion();

    DatePickerDialog.ScrollOrientation getScrollOrientation();

    Class<CAL> getCalendarType();
}
