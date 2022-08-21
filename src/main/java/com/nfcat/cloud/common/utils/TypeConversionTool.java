package com.nfcat.cloud.common.utils;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class TypeConversionTool {
    public static String string(Object obj) {
        return string(obj, "");
    }

    public static String string(Object obj, @NotNull String def) {
        return obj == null ? def : obj.toString();
    }

    public static int toInt(Object obj) {
        return toInt(obj, 0);
    }

    public static int toInt(Object obj, int def) {
        try {
            return obj == null || isBlank(string(obj)) ? def : Integer.parseInt(string(obj));
        } catch (NumberFormatException nfe) {
            return def;
        }
    }

    public static long toLong(Object obj) {
        return toLong(obj, 0L);
    }

    public static long toLong(Object obj, Long def) {
        try {
            return obj == null || isBlank(string(obj)) ? def : Long.parseLong(string(obj));
        } catch (NumberFormatException nfe) {
            return def;
        }
    }


    public static Date stringToDate(String str) {
        Date dt = new Date();
        return stringToDate(str, dt, "yyyy-MM-dd HH:mm:ss");
    }


    public static Date stringToDate(String str, Date def) {
        return stringToDate(str, def, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date stringToDate(String str, String tf) {
        Date dt = new Date();
        return stringToDate(str, dt, tf);
    }

    public static Date stringToDate(String str, Date def, String tf) {
        DateFormat format1 = new SimpleDateFormat(tf);
        Date date;
        try {
            date = format1.parse(str);
        } catch (ParseException e) {
            date = def;
        }
        return date;
    }
}
