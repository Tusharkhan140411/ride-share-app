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

    public static final DateFormat apiDateFormatter = new SimpleDateFormat("MM-DD-YYYY HH:mm:ss");
    public static final DateFormat dbDateFormatter = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");


    /*private LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public String getResponseDateFormat(Date dateToConvert){
        LocalDate ld = convertToLocalDateViaInstant(dateToConvert);
        return DateTimeFormatter.ofPattern(API_DATE_FORMAT).format(ld);
    }*/

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
