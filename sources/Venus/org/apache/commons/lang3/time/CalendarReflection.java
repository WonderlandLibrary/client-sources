/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.time;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.apache.commons.lang3.exception.ExceptionUtils;

class CalendarReflection {
    private static final Method IS_WEEK_DATE_SUPPORTED = CalendarReflection.getCalendarMethod("isWeekDateSupported", new Class[0]);
    private static final Method GET_WEEK_YEAR = CalendarReflection.getCalendarMethod("getWeekYear", new Class[0]);

    CalendarReflection() {
    }

    private static Method getCalendarMethod(String string, Class<?> ... classArray) {
        try {
            Method method = Calendar.class.getMethod(string, classArray);
            return method;
        } catch (Exception exception) {
            return null;
        }
    }

    static boolean isWeekDateSupported(Calendar calendar) {
        try {
            return IS_WEEK_DATE_SUPPORTED != null && (Boolean)IS_WEEK_DATE_SUPPORTED.invoke(calendar, new Object[0]) != false;
        } catch (Exception exception) {
            return (Boolean)ExceptionUtils.rethrow(exception);
        }
    }

    public static int getWeekYear(Calendar calendar) {
        try {
            if (CalendarReflection.isWeekDateSupported(calendar)) {
                return (Integer)GET_WEEK_YEAR.invoke(calendar, new Object[0]);
            }
        } catch (Exception exception) {
            return (Integer)ExceptionUtils.rethrow(exception);
        }
        int n = calendar.get(1);
        if (IS_WEEK_DATE_SUPPORTED == null && calendar instanceof GregorianCalendar) {
            switch (calendar.get(2)) {
                case 0: {
                    if (calendar.get(3) < 52) break;
                    --n;
                    break;
                }
                case 11: {
                    if (calendar.get(3) != 1) break;
                    ++n;
                }
            }
        }
        return n;
    }
}

