package ir.mahdiparastesh.mcdtp.date;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;

import java.util.Locale;

import ir.mahdiparastesh.mcdtp.McdtpUtils;

public class LocalDateFormat extends SimpleDateFormat {
    public LocalDateFormat(Context c, Class<? extends Calendar> calendarType, String pattern, Locale loc) {
        super(pattern, loc);
        calendar = McdtpUtils.createCalendar(calendarType);
        setDateFormatSymbols(McdtpUtils.localSymbols(c, calendarType, loc));
    }
}
