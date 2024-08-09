/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec;

import io.netty.util.AsciiString;
import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.internal.ObjectUtil;
import java.util.BitSet;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public final class DateFormatter {
    private static final BitSet DELIMITERS;
    private static final String[] DAY_OF_WEEK_TO_SHORT_NAME;
    private static final String[] CALENDAR_MONTH_TO_SHORT_NAME;
    private static final FastThreadLocal<DateFormatter> INSTANCES;
    private final GregorianCalendar cal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
    private final StringBuilder sb = new StringBuilder(29);
    private boolean timeFound;
    private int hours;
    private int minutes;
    private int seconds;
    private boolean dayOfMonthFound;
    private int dayOfMonth;
    private boolean monthFound;
    private int month;
    private boolean yearFound;
    private int year;

    public static Date parseHttpDate(CharSequence charSequence) {
        return DateFormatter.parseHttpDate(charSequence, 0, charSequence.length());
    }

    public static Date parseHttpDate(CharSequence charSequence, int n, int n2) {
        int n3 = n2 - n;
        if (n3 == 0) {
            return null;
        }
        if (n3 < 0) {
            throw new IllegalArgumentException("Can't have end < start");
        }
        if (n3 > 64) {
            throw new IllegalArgumentException("Can't parse more than 64 chars,looks like a user error or a malformed header");
        }
        return DateFormatter.formatter().parse0(ObjectUtil.checkNotNull(charSequence, "txt"), n, n2);
    }

    public static String format(Date date) {
        return DateFormatter.formatter().format0(ObjectUtil.checkNotNull(date, "date"));
    }

    public static StringBuilder append(Date date, StringBuilder stringBuilder) {
        return DateFormatter.formatter().append0(ObjectUtil.checkNotNull(date, "date"), ObjectUtil.checkNotNull(stringBuilder, "sb"));
    }

    private static DateFormatter formatter() {
        DateFormatter dateFormatter = INSTANCES.get();
        dateFormatter.reset();
        return dateFormatter;
    }

    private static boolean isDelim(char c) {
        return DELIMITERS.get(c);
    }

    private static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private static int getNumericalValue(char c) {
        return c - 48;
    }

    private DateFormatter() {
        this.reset();
    }

    public void reset() {
        this.timeFound = false;
        this.hours = -1;
        this.minutes = -1;
        this.seconds = -1;
        this.dayOfMonthFound = false;
        this.dayOfMonth = -1;
        this.monthFound = false;
        this.month = -1;
        this.yearFound = false;
        this.year = -1;
        this.cal.clear();
        this.sb.setLength(0);
    }

    private boolean tryParseTime(CharSequence charSequence, int n, int n2) {
        int n3 = n2 - n;
        if (n3 < 5 || n3 > 8) {
            return true;
        }
        int n4 = -1;
        int n5 = -1;
        int n6 = -1;
        int n7 = 0;
        int n8 = 0;
        int n9 = 0;
        for (int i = n; i < n2; ++i) {
            char c = charSequence.charAt(i);
            if (DateFormatter.isDigit(c)) {
                n8 = n8 * 10 + DateFormatter.getNumericalValue(c);
                if (++n9 <= 2) continue;
                return true;
            }
            if (c == ':') {
                if (n9 == 0) {
                    return true;
                }
                switch (n7) {
                    case 0: {
                        n4 = n8;
                        break;
                    }
                    case 1: {
                        n5 = n8;
                        break;
                    }
                    default: {
                        return true;
                    }
                }
                n8 = 0;
                ++n7;
                n9 = 0;
                continue;
            }
            return true;
        }
        if (n9 > 0) {
            n6 = n8;
        }
        if (n4 >= 0 && n5 >= 0 && n6 >= 0) {
            this.hours = n4;
            this.minutes = n5;
            this.seconds = n6;
            return false;
        }
        return true;
    }

    private boolean tryParseDayOfMonth(CharSequence charSequence, int n, int n2) {
        int n3 = n2 - n;
        if (n3 == 1) {
            char c = charSequence.charAt(n);
            if (DateFormatter.isDigit(c)) {
                this.dayOfMonth = DateFormatter.getNumericalValue(c);
                return false;
            }
        } else if (n3 == 2) {
            char c = charSequence.charAt(n);
            char c2 = charSequence.charAt(n + 1);
            if (DateFormatter.isDigit(c) && DateFormatter.isDigit(c2)) {
                this.dayOfMonth = DateFormatter.getNumericalValue(c) * 10 + DateFormatter.getNumericalValue(c2);
                return false;
            }
        }
        return true;
    }

    private static boolean matchMonth(String string, CharSequence charSequence, int n) {
        return AsciiString.regionMatchesAscii(string, true, 0, charSequence, n, 3);
    }

    private boolean tryParseMonth(CharSequence charSequence, int n, int n2) {
        int n3 = n2 - n;
        if (n3 != 3) {
            return true;
        }
        if (DateFormatter.matchMonth("Jan", charSequence, n)) {
            this.month = 0;
        } else if (DateFormatter.matchMonth("Feb", charSequence, n)) {
            this.month = 1;
        } else if (DateFormatter.matchMonth("Mar", charSequence, n)) {
            this.month = 2;
        } else if (DateFormatter.matchMonth("Apr", charSequence, n)) {
            this.month = 3;
        } else if (DateFormatter.matchMonth("May", charSequence, n)) {
            this.month = 4;
        } else if (DateFormatter.matchMonth("Jun", charSequence, n)) {
            this.month = 5;
        } else if (DateFormatter.matchMonth("Jul", charSequence, n)) {
            this.month = 6;
        } else if (DateFormatter.matchMonth("Aug", charSequence, n)) {
            this.month = 7;
        } else if (DateFormatter.matchMonth("Sep", charSequence, n)) {
            this.month = 8;
        } else if (DateFormatter.matchMonth("Oct", charSequence, n)) {
            this.month = 9;
        } else if (DateFormatter.matchMonth("Nov", charSequence, n)) {
            this.month = 10;
        } else if (DateFormatter.matchMonth("Dec", charSequence, n)) {
            this.month = 11;
        } else {
            return true;
        }
        return false;
    }

    private boolean tryParseYear(CharSequence charSequence, int n, int n2) {
        int n3 = n2 - n;
        if (n3 == 2) {
            char c = charSequence.charAt(n);
            char c2 = charSequence.charAt(n + 1);
            if (DateFormatter.isDigit(c) && DateFormatter.isDigit(c2)) {
                this.year = DateFormatter.getNumericalValue(c) * 10 + DateFormatter.getNumericalValue(c2);
                return false;
            }
        } else if (n3 == 4) {
            char c = charSequence.charAt(n);
            char c3 = charSequence.charAt(n + 1);
            char c4 = charSequence.charAt(n + 2);
            char c5 = charSequence.charAt(n + 3);
            if (DateFormatter.isDigit(c) && DateFormatter.isDigit(c3) && DateFormatter.isDigit(c4) && DateFormatter.isDigit(c5)) {
                this.year = DateFormatter.getNumericalValue(c) * 1000 + DateFormatter.getNumericalValue(c3) * 100 + DateFormatter.getNumericalValue(c4) * 10 + DateFormatter.getNumericalValue(c5);
                return false;
            }
        }
        return true;
    }

    private boolean parseToken(CharSequence charSequence, int n, int n2) {
        if (!this.timeFound) {
            this.timeFound = this.tryParseTime(charSequence, n, n2);
            if (this.timeFound) {
                return this.dayOfMonthFound && this.monthFound && this.yearFound;
            }
        }
        if (!this.dayOfMonthFound) {
            this.dayOfMonthFound = this.tryParseDayOfMonth(charSequence, n, n2);
            if (this.dayOfMonthFound) {
                return this.timeFound && this.monthFound && this.yearFound;
            }
        }
        if (!this.monthFound) {
            this.monthFound = this.tryParseMonth(charSequence, n, n2);
            if (this.monthFound) {
                return this.timeFound && this.dayOfMonthFound && this.yearFound;
            }
        }
        if (!this.yearFound) {
            this.yearFound = this.tryParseYear(charSequence, n, n2);
        }
        return this.timeFound && this.dayOfMonthFound && this.monthFound && this.yearFound;
    }

    private Date parse0(CharSequence charSequence, int n, int n2) {
        boolean bl = this.parse1(charSequence, n, n2);
        return bl && this.normalizeAndValidate() ? this.computeDate() : null;
    }

    private boolean parse1(CharSequence charSequence, int n, int n2) {
        int n3 = -1;
        for (int i = n; i < n2; ++i) {
            char c = charSequence.charAt(i);
            if (DateFormatter.isDelim(c)) {
                if (n3 == -1) continue;
                if (this.parseToken(charSequence, n3, i)) {
                    return false;
                }
                n3 = -1;
                continue;
            }
            if (n3 != -1) continue;
            n3 = i;
        }
        return n3 != -1 && this.parseToken(charSequence, n3, charSequence.length());
    }

    private boolean normalizeAndValidate() {
        if (this.dayOfMonth < 1 || this.dayOfMonth > 31 || this.hours > 23 || this.minutes > 59 || this.seconds > 59) {
            return true;
        }
        if (this.year >= 70 && this.year <= 99) {
            this.year += 1900;
        } else if (this.year >= 0 && this.year < 70) {
            this.year += 2000;
        } else if (this.year < 1601) {
            return true;
        }
        return false;
    }

    private Date computeDate() {
        this.cal.set(5, this.dayOfMonth);
        this.cal.set(2, this.month);
        this.cal.set(1, this.year);
        this.cal.set(11, this.hours);
        this.cal.set(12, this.minutes);
        this.cal.set(13, this.seconds);
        return this.cal.getTime();
    }

    private String format0(Date date) {
        this.append0(date, this.sb);
        return this.sb.toString();
    }

    private StringBuilder append0(Date date, StringBuilder stringBuilder) {
        this.cal.setTime(date);
        stringBuilder.append(DAY_OF_WEEK_TO_SHORT_NAME[this.cal.get(7) - 1]).append(", ");
        stringBuilder.append(this.cal.get(5)).append(' ');
        stringBuilder.append(CALENDAR_MONTH_TO_SHORT_NAME[this.cal.get(2)]).append(' ');
        stringBuilder.append(this.cal.get(1)).append(' ');
        DateFormatter.appendZeroLeftPadded(this.cal.get(11), stringBuilder).append(':');
        DateFormatter.appendZeroLeftPadded(this.cal.get(12), stringBuilder).append(':');
        return DateFormatter.appendZeroLeftPadded(this.cal.get(13), stringBuilder).append(" GMT");
    }

    private static StringBuilder appendZeroLeftPadded(int n, StringBuilder stringBuilder) {
        if (n < 10) {
            stringBuilder.append('0');
        }
        return stringBuilder.append(n);
    }

    DateFormatter(1 var1_1) {
        this();
    }

    static {
        int n;
        DELIMITERS = new BitSet();
        DELIMITERS.set(9);
        for (n = 32; n <= 47; n = (int)((char)(n + 1))) {
            DELIMITERS.set(n);
        }
        for (n = 59; n <= 64; n = (int)((char)(n + 1))) {
            DELIMITERS.set(n);
        }
        for (n = 91; n <= 96; n = (int)((char)(n + 1))) {
            DELIMITERS.set(n);
        }
        for (n = 123; n <= 126; n = (int)((char)(n + 1))) {
            DELIMITERS.set(n);
        }
        DAY_OF_WEEK_TO_SHORT_NAME = new String[]{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        CALENDAR_MONTH_TO_SHORT_NAME = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        INSTANCES = new FastThreadLocal<DateFormatter>(){

            @Override
            protected DateFormatter initialValue() {
                return new DateFormatter(null);
            }

            @Override
            protected Object initialValue() throws Exception {
                return this.initialValue();
            }
        };
    }
}

