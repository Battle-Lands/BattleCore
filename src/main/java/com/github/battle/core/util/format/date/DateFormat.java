package com.github.battle.core.util.format.date;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateFormat {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    public static final TimeZone TIMEZONE = TimeZone.getTimeZone("America/Sao_Paulo");

    public static String formatDate(Calendar calendar) {
        if (calendar == null) return DATE_FORMAT.format(GregorianCalendar.getInstance(TIMEZONE).getTime());
        return DATE_FORMAT.format(calendar.getTime());
    }

    public static String formatDate(long time) {
        return DATE_FORMAT.format(GregorianCalendar.getInstance(TIMEZONE).getTime());
    }

    public static long getTime() {
        return getDate().getTime();
    }

    public static Date getDate() {
        return Calendar.getInstance(TIMEZONE).getTime();
    }

    public static Timestamp getTimestamp() {
        return new Timestamp(getTime());
    }

}
