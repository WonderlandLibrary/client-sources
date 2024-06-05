package com.google.gson.internal;

import java.util.*;
import java.text.*;

public class PreJava9DateFormatProvider
{
    public static DateFormat getUSDateFormat(final int style) {
        return new SimpleDateFormat(getDateFormatPattern(style), Locale.US);
    }
    
    public static DateFormat getUSDateTimeFormat(final int dateStyle, final int timeStyle) {
        final String pattern = getDatePartOfDateTimePattern(dateStyle) + " " + getTimePartOfDateTimePattern(timeStyle);
        return new SimpleDateFormat(pattern, Locale.US);
    }
    
    private static String getDateFormatPattern(final int style) {
        switch (style) {
            case 3: {
                return "M/d/yy";
            }
            case 2: {
                return "MMM d, y";
            }
            case 1: {
                return "MMMM d, y";
            }
            case 0: {
                return "EEEE, MMMM d, y";
            }
            default: {
                throw new IllegalArgumentException("Unknown DateFormat style: " + style);
            }
        }
    }
    
    private static String getDatePartOfDateTimePattern(final int dateStyle) {
        switch (dateStyle) {
            case 3: {
                return "M/d/yy";
            }
            case 2: {
                return "MMM d, yyyy";
            }
            case 1: {
                return "MMMM d, yyyy";
            }
            case 0: {
                return "EEEE, MMMM d, yyyy";
            }
            default: {
                throw new IllegalArgumentException("Unknown DateFormat style: " + dateStyle);
            }
        }
    }
    
    private static String getTimePartOfDateTimePattern(final int timeStyle) {
        switch (timeStyle) {
            case 3: {
                return "h:mm a";
            }
            case 2: {
                return "h:mm:ss a";
            }
            case 0:
            case 1: {
                return "h:mm:ss a z";
            }
            default: {
                throw new IllegalArgumentException("Unknown DateFormat style: " + timeStyle);
            }
        }
    }
}
