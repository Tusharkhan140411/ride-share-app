package com.mlab.rideshare.util;

import lombok.experimental.UtilityClass;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

@UtilityClass
public class DateTimeUtils {

    public static final DateFormat apiDateFormatter = new SimpleDateFormat("MM-dd-YYYY HH:mm:ss");
    public static final DateFormat dbDateFormatter = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");


    public static String formatDateToDBFormat(Date date) {
        return date == null ? "" : dbDateFormatter.format(date);
    }

    public static String formatDateToApiFormat(Date date) {
        return date == null ? "" : apiDateFormatter.format(date);
    }

    public static int convertToMilli(int unitValue, int calenderFlag) {
        if(Calendar.HOUR == calenderFlag)
            return 1000 * 60 * 60 * unitValue;
        if(Calendar.MINUTE == calenderFlag)
            return 1000 * 60 * unitValue;
        return 0;
    }
}
