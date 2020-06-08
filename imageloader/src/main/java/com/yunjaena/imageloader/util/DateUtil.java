package com.yunjaena.imageloader.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static String getCurrentDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        Date time = new Date();
        return format.format(time);
    }

    public static String getCurrentDate(Calendar calendar) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        Date time = new Date(calendar.getTimeInMillis());
        return format.format(time);
    }


    public static String getCurrentDateWithUnderBar() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd_HH:mm:ss");
        Date time = new Date();
        return format.format(time);
    }

    public static String getCurrentDateNoDot() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date time = new Date();
        return format.format(time);
    }

    public static String getCurrentDateWithDot() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
        Date time = new Date();
        return format.format(time);
    }
    public static String getCurrentDateWithOutTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        Date time = new Date();
        return format.format(time);
    }

    public static String getCurrentDateWithOutTimeWithOutDot() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date time = new Date();
        return format.format(time);
    }

    public static int dateCompare(String date1, String date2) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        Date day1 = null;
        Date day2 = null;
        try {
            day1 = format.parse(date1);
            day2 = format.parse(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day1.compareTo(day2);
    }
}
