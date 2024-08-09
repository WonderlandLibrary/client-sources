/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.gson.internal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class PreJava9DateFormatProvider {
    public static DateFormat getUSDateFormat(int n) {
        return new SimpleDateFormat(PreJava9DateFormatProvider.getDateFormatPattern(n), Locale.US);
    }

    public static DateFormat getUSDateTimeFormat(int n, int n2) {
        String string = PreJava9DateFormatProvider.getDatePartOfDateTimePattern(n) + " " + PreJava9DateFormatProvider.getTimePartOfDateTimePattern(n2);
        return new SimpleDateFormat(string, Locale.US);
    }

    private static String getDateFormatPattern(int n) {
        switch (n) {
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
        }
        throw new IllegalArgumentException("Unknown DateFormat style: " + n);
    }

    private static String getDatePartOfDateTimePattern(int n) {
        switch (n) {
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
        }
        throw new IllegalArgumentException("Unknown DateFormat style: " + n);
    }

    private static String getTimePartOfDateTimePattern(int n) {
        switch (n) {
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
        }
        throw new IllegalArgumentException("Unknown DateFormat style: " + n);
    }
}

