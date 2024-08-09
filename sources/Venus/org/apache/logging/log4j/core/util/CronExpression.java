/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.util;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.TreeSet;

public final class CronExpression {
    protected static final int SECOND = 0;
    protected static final int MINUTE = 1;
    protected static final int HOUR = 2;
    protected static final int DAY_OF_MONTH = 3;
    protected static final int MONTH = 4;
    protected static final int DAY_OF_WEEK = 5;
    protected static final int YEAR = 6;
    protected static final int ALL_SPEC_INT = 99;
    protected static final int NO_SPEC_INT = 98;
    protected static final Integer ALL_SPEC = 99;
    protected static final Integer NO_SPEC = 98;
    protected static final Map<String, Integer> monthMap = new HashMap<String, Integer>(20);
    protected static final Map<String, Integer> dayMap = new HashMap<String, Integer>(60);
    private final String cronExpression;
    private TimeZone timeZone = null;
    protected transient TreeSet<Integer> seconds;
    protected transient TreeSet<Integer> minutes;
    protected transient TreeSet<Integer> hours;
    protected transient TreeSet<Integer> daysOfMonth;
    protected transient TreeSet<Integer> months;
    protected transient TreeSet<Integer> daysOfWeek;
    protected transient TreeSet<Integer> years;
    protected transient boolean lastdayOfWeek = false;
    protected transient int nthdayOfWeek = 0;
    protected transient boolean lastdayOfMonth = false;
    protected transient boolean nearestWeekday = false;
    protected transient int lastdayOffset = 0;
    protected transient boolean expressionParsed = false;
    public static final int MAX_YEAR;
    public static final Calendar MIN_CAL;
    public static final Date MIN_DATE;

    public CronExpression(String string) throws ParseException {
        if (string == null) {
            throw new IllegalArgumentException("cronExpression cannot be null");
        }
        this.cronExpression = string.toUpperCase(Locale.US);
        this.buildExpression(this.cronExpression);
    }

    public boolean isSatisfiedBy(Date date) {
        Calendar calendar = Calendar.getInstance(this.getTimeZone());
        calendar.setTime(date);
        calendar.set(14, 0);
        Date date2 = calendar.getTime();
        calendar.add(13, -1);
        Date date3 = this.getTimeAfter(calendar.getTime());
        return date3 != null && date3.equals(date2);
    }

    public Date getNextValidTimeAfter(Date date) {
        return this.getTimeAfter(date);
    }

    public Date getNextInvalidTimeAfter(Date date) {
        Date date2;
        long l = 1000L;
        Calendar calendar = Calendar.getInstance(this.getTimeZone());
        calendar.setTime(date);
        calendar.set(14, 0);
        Date date3 = calendar.getTime();
        while (l == 1000L && (date2 = this.getTimeAfter(date3)) != null) {
            l = date2.getTime() - date3.getTime();
            if (l != 1000L) continue;
            date3 = date2;
        }
        return new Date(date3.getTime() + 1000L);
    }

    public TimeZone getTimeZone() {
        if (this.timeZone == null) {
            this.timeZone = TimeZone.getDefault();
        }
        return this.timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public String toString() {
        return this.cronExpression;
    }

    public static boolean isValidExpression(String string) {
        try {
            new CronExpression(string);
        } catch (ParseException parseException) {
            return true;
        }
        return false;
    }

    public static void validateExpression(String string) throws ParseException {
        new CronExpression(string);
    }

    protected void buildExpression(String string) throws ParseException {
        this.expressionParsed = true;
        try {
            boolean bl;
            TreeSet<Integer> treeSet;
            Object object;
            int n;
            if (this.seconds == null) {
                this.seconds = new TreeSet();
            }
            if (this.minutes == null) {
                this.minutes = new TreeSet();
            }
            if (this.hours == null) {
                this.hours = new TreeSet();
            }
            if (this.daysOfMonth == null) {
                this.daysOfMonth = new TreeSet();
            }
            if (this.months == null) {
                this.months = new TreeSet();
            }
            if (this.daysOfWeek == null) {
                this.daysOfWeek = new TreeSet();
            }
            if (this.years == null) {
                this.years = new TreeSet();
            }
            StringTokenizer stringTokenizer = new StringTokenizer(string, " \t", false);
            for (n = 0; stringTokenizer.hasMoreTokens() && n <= 6; ++n) {
                object = stringTokenizer.nextToken().trim();
                if (n == 3 && ((String)object).indexOf(76) != -1 && ((String)object).length() > 1 && ((String)object).contains(",")) {
                    throw new ParseException("Support for specifying 'L' and 'LW' with other days of the month is not implemented", -1);
                }
                if (n == 5 && ((String)object).indexOf(76) != -1 && ((String)object).length() > 1 && ((String)object).contains(",")) {
                    throw new ParseException("Support for specifying 'L' with other days of the week is not implemented", -1);
                }
                if (n == 5 && ((String)object).indexOf(35) != -1 && ((String)object).indexOf(35, ((String)object).indexOf(35) + 1) != -1) {
                    throw new ParseException("Support for specifying multiple \"nth\" days is not implemented.", -1);
                }
                treeSet = new StringTokenizer((String)object, ",");
                while (((StringTokenizer)((Object)treeSet)).hasMoreTokens()) {
                    String string2 = ((StringTokenizer)((Object)treeSet)).nextToken();
                    this.storeExpressionVals(0, string2, n);
                }
            }
            if (n <= 5) {
                throw new ParseException("Unexpected end of expression.", string.length());
            }
            if (n <= 6) {
                this.storeExpressionVals(0, "*", 6);
            }
            object = this.getSet(5);
            treeSet = this.getSet(3);
            boolean bl2 = !treeSet.contains(NO_SPEC);
            boolean bl3 = bl = !((TreeSet)object).contains(NO_SPEC);
            if (!(bl2 && !bl || bl && !bl2)) {
                throw new ParseException("Support for specifying both a day-of-week AND a day-of-month parameter is not implemented.", 0);
            }
        } catch (ParseException parseException) {
            throw parseException;
        } catch (Exception exception) {
            throw new ParseException("Illegal cron expression format (" + exception.toString() + ")", 0);
        }
    }

    protected int storeExpressionVals(int n, String string, int n2) throws ParseException {
        int n3;
        int n4 = 0;
        int n5 = this.skipWhiteSpace(n, string);
        if (n5 >= string.length()) {
            return n5;
        }
        char c = string.charAt(n5);
        if (!(c < 'A' || c > 'Z' || string.equals("L") || string.equals("LW") || string.matches("^L-[0-9]*[W]?"))) {
            int n6;
            int n7;
            block47: {
                String string2 = string.substring(n5, n5 + 3);
                n7 = -1;
                n6 = -1;
                if (n2 == 4) {
                    n7 = this.getMonthNumber(string2) + 1;
                    if (n7 <= 0) {
                        throw new ParseException("Invalid Month value: '" + string2 + "'", n5);
                    }
                    if (string.length() > n5 + 3 && (c = string.charAt(n5 + 3)) == '-' && (n6 = this.getMonthNumber(string2 = string.substring(n5 += 4, n5 + 3)) + 1) <= 0) {
                        throw new ParseException("Invalid Month value: '" + string2 + "'", n5);
                    }
                } else if (n2 == 5) {
                    n7 = this.getDayOfWeekNumber(string2);
                    if (n7 < 0) {
                        throw new ParseException("Invalid Day-of-Week value: '" + string2 + "'", n5);
                    }
                    if (string.length() > n5 + 3) {
                        c = string.charAt(n5 + 3);
                        if (c == '-') {
                            if ((n6 = this.getDayOfWeekNumber(string2 = string.substring(n5 += 4, n5 + 3))) < 0) {
                                throw new ParseException("Invalid Day-of-Week value: '" + string2 + "'", n5);
                            }
                        } else {
                            if (c == '#') {
                                try {
                                    this.nthdayOfWeek = Integer.parseInt(string.substring(n5 += 4));
                                    if (this.nthdayOfWeek < 1 || this.nthdayOfWeek > 5) {
                                        throw new Exception();
                                    }
                                    break block47;
                                } catch (Exception exception) {
                                    throw new ParseException("A numeric value between 1 and 5 must follow the '#' option", n5);
                                }
                            }
                            if (c == 'L') {
                                this.lastdayOfWeek = true;
                                ++n5;
                            }
                        }
                    }
                } else {
                    throw new ParseException("Illegal characters for this position: '" + string2 + "'", n5);
                }
            }
            if (n6 != -1) {
                n4 = 1;
            }
            this.addToSet(n7, n6, n4, n2);
            return n5 + 3;
        }
        if (c == '?') {
            int n8;
            if (++n5 + 1 < string.length() && string.charAt(n5) != ' ' && string.charAt(n5 + 1) != '\t') {
                throw new ParseException("Illegal character after '?': " + string.charAt(n5), n5);
            }
            if (n2 != 5 && n2 != 3) {
                throw new ParseException("'?' can only be specfied for Day-of-Month or Day-of-Week.", n5);
            }
            if (n2 == 5 && !this.lastdayOfMonth && (n8 = this.daysOfMonth.last().intValue()) == 98) {
                throw new ParseException("'?' can only be specfied for Day-of-Month -OR- Day-of-Week.", n5);
            }
            this.addToSet(98, -1, 0, n2);
            return n5;
        }
        if (c == '*' || c == '/') {
            if (c == '*' && n5 + 1 >= string.length()) {
                this.addToSet(99, -1, n4, n2);
                return n5 + 1;
            }
            if (c == '/' && (n5 + 1 >= string.length() || string.charAt(n5 + 1) == ' ' || string.charAt(n5 + 1) == '\t')) {
                throw new ParseException("'/' must be followed by an integer.", n5);
            }
            if (c == '*') {
                ++n5;
            }
            if ((c = string.charAt(n5)) == '/') {
                if (++n5 >= string.length()) {
                    throw new ParseException("Unexpected end of string.", n5);
                }
                n4 = this.getNumericValue(string, n5);
                ++n5;
                if (n4 > 10) {
                    ++n5;
                }
                if (n4 > 59 && (n2 == 0 || n2 == 1)) {
                    throw new ParseException("Increment > 60 : " + n4, n5);
                }
                if (n4 > 23 && n2 == 2) {
                    throw new ParseException("Increment > 24 : " + n4, n5);
                }
                if (n4 > 31 && n2 == 3) {
                    throw new ParseException("Increment > 31 : " + n4, n5);
                }
                if (n4 > 7 && n2 == 5) {
                    throw new ParseException("Increment > 7 : " + n4, n5);
                }
                if (n4 > 12 && n2 == 4) {
                    throw new ParseException("Increment > 12 : " + n4, n5);
                }
            } else {
                n4 = 1;
            }
            this.addToSet(99, -1, n4, n2);
            return n5;
        }
        if (c == 'L') {
            ++n5;
            if (n2 == 3) {
                this.lastdayOfMonth = true;
            }
            if (n2 == 5) {
                this.addToSet(7, 7, 0, n2);
            }
            if (n2 == 3 && string.length() > n5) {
                c = string.charAt(n5);
                if (c == '-') {
                    ValueSet valueSet = this.getValue(0, string, n5 + 1);
                    this.lastdayOffset = valueSet.value;
                    if (this.lastdayOffset > 30) {
                        throw new ParseException("Offset from last day must be <= 30", n5 + 1);
                    }
                    n5 = valueSet.pos;
                }
                if (string.length() > n5 && (c = string.charAt(n5)) == 'W') {
                    this.nearestWeekday = true;
                    ++n5;
                }
            }
            return n5;
        }
        if (c >= '0' && c <= '9') {
            n3 = Integer.parseInt(String.valueOf(c));
            if (++n5 < string.length()) {
                c = string.charAt(n5);
                if (c >= '0' && c <= '9') {
                    ValueSet valueSet = this.getValue(n3, string, n5);
                    n3 = valueSet.value;
                    n5 = valueSet.pos;
                }
                n5 = this.checkNext(n5, string, n3, n2);
                return n5;
            }
        } else {
            throw new ParseException("Unexpected character: " + c, n5);
        }
        this.addToSet(n3, -1, -1, n2);
        return n5;
    }

    protected int checkNext(int n, String string, int n2, int n3) throws ParseException {
        int n4 = -1;
        int n5 = n;
        if (n5 >= string.length()) {
            this.addToSet(n2, n4, -1, n3);
            return n5;
        }
        char c = string.charAt(n);
        if (c == 'L') {
            if (n3 == 5) {
                if (n2 < 1 || n2 > 7) {
                    throw new ParseException("Day-of-Week values must be between 1 and 7", -1);
                }
            } else {
                throw new ParseException("'L' option is not valid here. (pos=" + n5 + ")", n5);
            }
            this.lastdayOfWeek = true;
            TreeSet<Integer> treeSet = this.getSet(n3);
            treeSet.add(n2);
            return ++n5;
        }
        if (c == 'W') {
            if (n3 != 3) {
                throw new ParseException("'W' option is not valid here. (pos=" + n5 + ")", n5);
            }
            this.nearestWeekday = true;
            if (n2 > 31) {
                throw new ParseException("The 'W' option does not make sense with values larger than 31 (max number of days in a month)", n5);
            }
            TreeSet<Integer> treeSet = this.getSet(n3);
            treeSet.add(n2);
            return ++n5;
        }
        if (c == '#') {
            if (n3 != 5) {
                throw new ParseException("'#' option is not valid here. (pos=" + n5 + ")", n5);
            }
            ++n5;
            try {
                this.nthdayOfWeek = Integer.parseInt(string.substring(n5));
                if (this.nthdayOfWeek < 1 || this.nthdayOfWeek > 5) {
                    throw new Exception();
                }
            } catch (Exception exception) {
                throw new ParseException("A numeric value between 1 and 5 must follow the '#' option", n5);
            }
            TreeSet<Integer> treeSet = this.getSet(n3);
            treeSet.add(n2);
            return ++n5;
        }
        if (c == '-') {
            int n6;
            c = string.charAt(++n5);
            n4 = n6 = Integer.parseInt(String.valueOf(c));
            if (++n5 >= string.length()) {
                this.addToSet(n2, n4, 1, n3);
                return n5;
            }
            c = string.charAt(n5);
            if (c >= '0' && c <= '9') {
                ValueSet valueSet = this.getValue(n6, string, n5);
                n4 = valueSet.value;
                n5 = valueSet.pos;
            }
            if (n5 < string.length() && (c = string.charAt(n5)) == '/') {
                c = string.charAt(++n5);
                int n7 = Integer.parseInt(String.valueOf(c));
                if (++n5 >= string.length()) {
                    this.addToSet(n2, n4, n7, n3);
                    return n5;
                }
                c = string.charAt(n5);
                if (c >= '0' && c <= '9') {
                    ValueSet valueSet = this.getValue(n7, string, n5);
                    int n8 = valueSet.value;
                    this.addToSet(n2, n4, n8, n3);
                    n5 = valueSet.pos;
                    return n5;
                }
                this.addToSet(n2, n4, n7, n3);
                return n5;
            }
            this.addToSet(n2, n4, 1, n3);
            return n5;
        }
        if (c == '/') {
            c = string.charAt(++n5);
            int n9 = Integer.parseInt(String.valueOf(c));
            if (++n5 >= string.length()) {
                this.addToSet(n2, n4, n9, n3);
                return n5;
            }
            c = string.charAt(n5);
            if (c >= '0' && c <= '9') {
                ValueSet valueSet = this.getValue(n9, string, n5);
                int n10 = valueSet.value;
                this.addToSet(n2, n4, n10, n3);
                n5 = valueSet.pos;
                return n5;
            }
            throw new ParseException("Unexpected character '" + c + "' after '/'", n5);
        }
        this.addToSet(n2, n4, 0, n3);
        return ++n5;
    }

    public String getCronExpression() {
        return this.cronExpression;
    }

    public String getExpressionSummary() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("seconds: ");
        stringBuilder.append(this.getExpressionSetSummary(this.seconds));
        stringBuilder.append("\n");
        stringBuilder.append("minutes: ");
        stringBuilder.append(this.getExpressionSetSummary(this.minutes));
        stringBuilder.append("\n");
        stringBuilder.append("hours: ");
        stringBuilder.append(this.getExpressionSetSummary(this.hours));
        stringBuilder.append("\n");
        stringBuilder.append("daysOfMonth: ");
        stringBuilder.append(this.getExpressionSetSummary(this.daysOfMonth));
        stringBuilder.append("\n");
        stringBuilder.append("months: ");
        stringBuilder.append(this.getExpressionSetSummary(this.months));
        stringBuilder.append("\n");
        stringBuilder.append("daysOfWeek: ");
        stringBuilder.append(this.getExpressionSetSummary(this.daysOfWeek));
        stringBuilder.append("\n");
        stringBuilder.append("lastdayOfWeek: ");
        stringBuilder.append(this.lastdayOfWeek);
        stringBuilder.append("\n");
        stringBuilder.append("nearestWeekday: ");
        stringBuilder.append(this.nearestWeekday);
        stringBuilder.append("\n");
        stringBuilder.append("NthDayOfWeek: ");
        stringBuilder.append(this.nthdayOfWeek);
        stringBuilder.append("\n");
        stringBuilder.append("lastdayOfMonth: ");
        stringBuilder.append(this.lastdayOfMonth);
        stringBuilder.append("\n");
        stringBuilder.append("years: ");
        stringBuilder.append(this.getExpressionSetSummary(this.years));
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    protected String getExpressionSetSummary(Set<Integer> set) {
        if (set.contains(NO_SPEC)) {
            return "?";
        }
        if (set.contains(ALL_SPEC)) {
            return "*";
        }
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<Integer> iterator2 = set.iterator();
        boolean bl = true;
        while (iterator2.hasNext()) {
            Integer n = iterator2.next();
            String string = n.toString();
            if (!bl) {
                stringBuilder.append(",");
            }
            stringBuilder.append(string);
            bl = false;
        }
        return stringBuilder.toString();
    }

    protected String getExpressionSetSummary(ArrayList<Integer> arrayList) {
        if (arrayList.contains(NO_SPEC)) {
            return "?";
        }
        if (arrayList.contains(ALL_SPEC)) {
            return "*";
        }
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<Integer> iterator2 = arrayList.iterator();
        boolean bl = true;
        while (iterator2.hasNext()) {
            Integer n = iterator2.next();
            String string = n.toString();
            if (!bl) {
                stringBuilder.append(",");
            }
            stringBuilder.append(string);
            bl = false;
        }
        return stringBuilder.toString();
    }

    protected int skipWhiteSpace(int n, String string) {
        while (n < string.length() && (string.charAt(n) == ' ' || string.charAt(n) == '\t')) {
            ++n;
        }
        return n;
    }

    protected int findNextWhiteSpace(int n, String string) {
        while (n < string.length() && (string.charAt(n) != ' ' || string.charAt(n) != '\t')) {
            ++n;
        }
        return n;
    }

    protected void addToSet(int n, int n2, int n3, int n4) throws ParseException {
        TreeSet<Integer> treeSet = this.getSet(n4);
        if (n4 == 0 || n4 == 1) {
            if ((n < 0 || n > 59 || n2 > 59) && n != 99) {
                throw new ParseException("Minute and Second values must be between 0 and 59", -1);
            }
        } else if (n4 == 2) {
            if ((n < 0 || n > 23 || n2 > 23) && n != 99) {
                throw new ParseException("Hour values must be between 0 and 23", -1);
            }
        } else if (n4 == 3) {
            if ((n < 1 || n > 31 || n2 > 31) && n != 99 && n != 98) {
                throw new ParseException("Day of month values must be between 1 and 31", -1);
            }
        } else if (n4 == 4) {
            if ((n < 1 || n > 12 || n2 > 12) && n != 99) {
                throw new ParseException("Month values must be between 1 and 12", -1);
            }
        } else if (n4 == 5 && (n == 0 || n > 7 || n2 > 7) && n != 99 && n != 98) {
            throw new ParseException("Day-of-Week values must be between 1 and 7", -1);
        }
        if ((n3 == 0 || n3 == -1) && n != 99) {
            if (n != -1) {
                treeSet.add(n);
            } else {
                treeSet.add(NO_SPEC);
            }
            return;
        }
        int n5 = n;
        int n6 = n2;
        if (n == 99 && n3 <= 0) {
            n3 = 1;
            treeSet.add(ALL_SPEC);
        }
        if (n4 == 0 || n4 == 1) {
            if (n6 == -1) {
                n6 = 59;
            }
            if (n5 == -1 || n5 == 99) {
                n5 = 0;
            }
        } else if (n4 == 2) {
            if (n6 == -1) {
                n6 = 23;
            }
            if (n5 == -1 || n5 == 99) {
                n5 = 0;
            }
        } else if (n4 == 3) {
            if (n6 == -1) {
                n6 = 31;
            }
            if (n5 == -1 || n5 == 99) {
                n5 = 1;
            }
        } else if (n4 == 4) {
            if (n6 == -1) {
                n6 = 12;
            }
            if (n5 == -1 || n5 == 99) {
                n5 = 1;
            }
        } else if (n4 == 5) {
            if (n6 == -1) {
                n6 = 7;
            }
            if (n5 == -1 || n5 == 99) {
                n5 = 1;
            }
        } else if (n4 == 6) {
            if (n6 == -1) {
                n6 = MAX_YEAR;
            }
            if (n5 == -1 || n5 == 99) {
                n5 = 1970;
            }
        }
        int n7 = -1;
        if (n6 < n5) {
            switch (n4) {
                case 0: {
                    n7 = 60;
                    break;
                }
                case 1: {
                    n7 = 60;
                    break;
                }
                case 2: {
                    n7 = 24;
                    break;
                }
                case 4: {
                    n7 = 12;
                    break;
                }
                case 5: {
                    n7 = 7;
                    break;
                }
                case 3: {
                    n7 = 31;
                    break;
                }
                case 6: {
                    throw new IllegalArgumentException("Start year must be less than stop year");
                }
                default: {
                    throw new IllegalArgumentException("Unexpected type encountered");
                }
            }
            n6 += n7;
        }
        for (int i = n5; i <= n6; i += n3) {
            if (n7 == -1) {
                treeSet.add(i);
                continue;
            }
            int n8 = i % n7;
            if (n8 == 0 && (n4 == 4 || n4 == 5 || n4 == 3)) {
                n8 = n7;
            }
            treeSet.add(n8);
        }
    }

    TreeSet<Integer> getSet(int n) {
        switch (n) {
            case 0: {
                return this.seconds;
            }
            case 1: {
                return this.minutes;
            }
            case 2: {
                return this.hours;
            }
            case 3: {
                return this.daysOfMonth;
            }
            case 4: {
                return this.months;
            }
            case 5: {
                return this.daysOfWeek;
            }
            case 6: {
                return this.years;
            }
        }
        return null;
    }

    protected ValueSet getValue(int n, String string, int n2) {
        char c = string.charAt(n2);
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(n));
        while (c >= '0' && c <= '9') {
            stringBuilder.append(c);
            if (++n2 >= string.length()) break;
            c = string.charAt(n2);
        }
        ValueSet valueSet = new ValueSet(this, null);
        valueSet.pos = n2 < string.length() ? n2 : n2 + 1;
        valueSet.value = Integer.parseInt(stringBuilder.toString());
        return valueSet;
    }

    protected int getNumericValue(String string, int n) {
        int n2 = this.findNextWhiteSpace(n, string);
        String string2 = string.substring(n, n2);
        return Integer.parseInt(string2);
    }

    protected int getMonthNumber(String string) {
        Integer n = monthMap.get(string);
        if (n == null) {
            return 1;
        }
        return n;
    }

    protected int getDayOfWeekNumber(String string) {
        Integer n = dayMap.get(string);
        if (n == null) {
            return 1;
        }
        return n;
    }

    public Date getTimeAfter(Date date) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(this.getTimeZone());
        date = new Date(date.getTime() + 1000L);
        gregorianCalendar.setTime(date);
        gregorianCalendar.set(14, 0);
        boolean bl = false;
        while (!bl) {
            int n;
            int n2;
            boolean bl2;
            if (gregorianCalendar.get(1) > 2999) {
                return null;
            }
            SortedSet<Integer> sortedSet = null;
            int n3 = 0;
            int n4 = gregorianCalendar.get(13);
            int n5 = gregorianCalendar.get(12);
            sortedSet = this.seconds.tailSet(n4);
            if (sortedSet != null && sortedSet.size() != 0) {
                n4 = sortedSet.first();
            } else {
                n4 = this.seconds.first();
                gregorianCalendar.set(12, ++n5);
            }
            gregorianCalendar.set(13, n4);
            n5 = gregorianCalendar.get(12);
            int n6 = gregorianCalendar.get(11);
            n3 = -1;
            sortedSet = this.minutes.tailSet(n5);
            if (sortedSet != null && sortedSet.size() != 0) {
                n3 = n5;
                n5 = sortedSet.first();
            } else {
                n5 = this.minutes.first();
                ++n6;
            }
            if (n5 != n3) {
                gregorianCalendar.set(13, 0);
                gregorianCalendar.set(12, n5);
                this.setCalendarHour(gregorianCalendar, n6);
                continue;
            }
            gregorianCalendar.set(12, n5);
            n6 = gregorianCalendar.get(11);
            int n7 = gregorianCalendar.get(5);
            n3 = -1;
            sortedSet = this.hours.tailSet(n6);
            if (sortedSet != null && sortedSet.size() != 0) {
                n3 = n6;
                n6 = sortedSet.first();
            } else {
                n6 = this.hours.first();
                ++n7;
            }
            if (n6 != n3) {
                gregorianCalendar.set(13, 0);
                gregorianCalendar.set(12, 0);
                gregorianCalendar.set(5, n7);
                this.setCalendarHour(gregorianCalendar, n6);
                continue;
            }
            gregorianCalendar.set(11, n6);
            n7 = gregorianCalendar.get(5);
            int n8 = gregorianCalendar.get(2) + 1;
            n3 = -1;
            int n9 = n8;
            boolean bl3 = !this.daysOfMonth.contains(NO_SPEC);
            boolean bl4 = bl2 = !this.daysOfWeek.contains(NO_SPEC);
            if (bl3 && !bl2) {
                sortedSet = this.daysOfMonth.tailSet(n7);
                if (this.lastdayOfMonth) {
                    if (!this.nearestWeekday) {
                        n3 = n7;
                        n7 = this.getLastDayOfMonth(n8, gregorianCalendar.get(1));
                        if (n3 > (n7 -= this.lastdayOffset)) {
                            if (++n8 > 12) {
                                n8 = 1;
                                n9 = 3333;
                                ((Calendar)gregorianCalendar).add(1, 1);
                            }
                            n7 = 1;
                        }
                    } else {
                        n3 = n7;
                        n7 = this.getLastDayOfMonth(n8, gregorianCalendar.get(1));
                        Calendar calendar = Calendar.getInstance(this.getTimeZone());
                        calendar.set(13, 0);
                        calendar.set(12, 0);
                        calendar.set(11, 0);
                        calendar.set(5, n7 -= this.lastdayOffset);
                        calendar.set(2, n8 - 1);
                        calendar.set(1, gregorianCalendar.get(1));
                        n2 = this.getLastDayOfMonth(n8, gregorianCalendar.get(1));
                        n = calendar.get(7);
                        if (n == 7 && n7 == 1) {
                            n7 += 2;
                        } else if (n == 7) {
                            --n7;
                        } else if (n == 1 && n7 == n2) {
                            n7 -= 2;
                        } else if (n == 1) {
                            ++n7;
                        }
                        calendar.set(13, n4);
                        calendar.set(12, n5);
                        calendar.set(11, n6);
                        calendar.set(5, n7);
                        calendar.set(2, n8 - 1);
                        Date date2 = calendar.getTime();
                        if (date2.before(date)) {
                            n7 = 1;
                            ++n8;
                        }
                    }
                } else if (this.nearestWeekday) {
                    n3 = n7;
                    n7 = this.daysOfMonth.first();
                    Calendar calendar = Calendar.getInstance(this.getTimeZone());
                    calendar.set(13, 0);
                    calendar.set(12, 0);
                    calendar.set(11, 0);
                    calendar.set(5, n7);
                    calendar.set(2, n8 - 1);
                    calendar.set(1, gregorianCalendar.get(1));
                    n2 = this.getLastDayOfMonth(n8, gregorianCalendar.get(1));
                    n = calendar.get(7);
                    if (n == 7 && n7 == 1) {
                        n7 += 2;
                    } else if (n == 7) {
                        --n7;
                    } else if (n == 1 && n7 == n2) {
                        n7 -= 2;
                    } else if (n == 1) {
                        ++n7;
                    }
                    calendar.set(13, n4);
                    calendar.set(12, n5);
                    calendar.set(11, n6);
                    calendar.set(5, n7);
                    calendar.set(2, n8 - 1);
                    Date date3 = calendar.getTime();
                    if (date3.before(date)) {
                        n7 = this.daysOfMonth.first();
                        ++n8;
                    }
                } else if (sortedSet != null && sortedSet.size() != 0) {
                    int n10;
                    n3 = n7;
                    n7 = sortedSet.first();
                    if (n7 > (n10 = this.getLastDayOfMonth(n8, gregorianCalendar.get(1)))) {
                        n7 = this.daysOfMonth.first();
                        ++n8;
                    }
                } else {
                    n7 = this.daysOfMonth.first();
                    ++n8;
                }
                if (n7 != n3 || n8 != n9) {
                    gregorianCalendar.set(13, 0);
                    gregorianCalendar.set(12, 0);
                    gregorianCalendar.set(11, 0);
                    gregorianCalendar.set(5, n7);
                    gregorianCalendar.set(2, n8 - 1);
                    continue;
                }
            } else if (bl2 && !bl3) {
                if (this.lastdayOfWeek) {
                    int n11;
                    int n12 = this.daysOfWeek.first();
                    n2 = gregorianCalendar.get(7);
                    n = 0;
                    if (n2 < n12) {
                        n = n12 - n2;
                    }
                    if (n2 > n12) {
                        n = n12 + (7 - n2);
                    }
                    if (n7 + n > (n11 = this.getLastDayOfMonth(n8, gregorianCalendar.get(1)))) {
                        gregorianCalendar.set(13, 0);
                        gregorianCalendar.set(12, 0);
                        gregorianCalendar.set(11, 0);
                        gregorianCalendar.set(5, 1);
                        gregorianCalendar.set(2, n8);
                        continue;
                    }
                    while (n7 + n + 7 <= n11) {
                        n += 7;
                    }
                    n7 += n;
                    if (n > 0) {
                        gregorianCalendar.set(13, 0);
                        gregorianCalendar.set(12, 0);
                        gregorianCalendar.set(11, 0);
                        gregorianCalendar.set(5, n7);
                        gregorianCalendar.set(2, n8 - 1);
                        continue;
                    }
                } else if (this.nthdayOfWeek != 0) {
                    int n13 = this.daysOfWeek.first();
                    n2 = gregorianCalendar.get(7);
                    n = 0;
                    if (n2 < n13) {
                        n = n13 - n2;
                    } else if (n2 > n13) {
                        n = n13 + (7 - n2);
                    }
                    boolean bl5 = false;
                    if (n > 0) {
                        bl5 = true;
                    }
                    int n14 = (n7 += n) / 7;
                    if (n7 % 7 > 0) {
                        ++n14;
                    }
                    if ((n = (this.nthdayOfWeek - n14) * 7) < 0 || (n7 += n) > this.getLastDayOfMonth(n8, gregorianCalendar.get(1))) {
                        gregorianCalendar.set(13, 0);
                        gregorianCalendar.set(12, 0);
                        gregorianCalendar.set(11, 0);
                        gregorianCalendar.set(5, 1);
                        gregorianCalendar.set(2, n8);
                        continue;
                    }
                    if (n > 0 || bl5) {
                        gregorianCalendar.set(13, 0);
                        gregorianCalendar.set(12, 0);
                        gregorianCalendar.set(11, 0);
                        gregorianCalendar.set(5, n7);
                        gregorianCalendar.set(2, n8 - 1);
                        continue;
                    }
                } else {
                    int n15;
                    int n16 = gregorianCalendar.get(7);
                    n2 = this.daysOfWeek.first();
                    sortedSet = this.daysOfWeek.tailSet(n16);
                    if (sortedSet != null && sortedSet.size() > 0) {
                        n2 = sortedSet.first();
                    }
                    n = 0;
                    if (n16 < n2) {
                        n = n2 - n16;
                    }
                    if (n16 > n2) {
                        n = n2 + (7 - n16);
                    }
                    if (n7 + n > (n15 = this.getLastDayOfMonth(n8, gregorianCalendar.get(1)))) {
                        gregorianCalendar.set(13, 0);
                        gregorianCalendar.set(12, 0);
                        gregorianCalendar.set(11, 0);
                        gregorianCalendar.set(5, 1);
                        gregorianCalendar.set(2, n8);
                        continue;
                    }
                    if (n > 0) {
                        gregorianCalendar.set(13, 0);
                        gregorianCalendar.set(12, 0);
                        gregorianCalendar.set(11, 0);
                        gregorianCalendar.set(5, n7 + n);
                        gregorianCalendar.set(2, n8 - 1);
                        continue;
                    }
                }
            } else {
                throw new UnsupportedOperationException("Support for specifying both a day-of-week AND a day-of-month parameter is not implemented.");
            }
            gregorianCalendar.set(5, n7);
            n8 = gregorianCalendar.get(2) + 1;
            int n17 = gregorianCalendar.get(1);
            n3 = -1;
            if (n17 > MAX_YEAR) {
                return null;
            }
            sortedSet = this.months.tailSet(n8);
            if (sortedSet != null && sortedSet.size() != 0) {
                n3 = n8;
                n8 = sortedSet.first();
            } else {
                n8 = this.months.first();
                ++n17;
            }
            if (n8 != n3) {
                gregorianCalendar.set(13, 0);
                gregorianCalendar.set(12, 0);
                gregorianCalendar.set(11, 0);
                gregorianCalendar.set(5, 1);
                gregorianCalendar.set(2, n8 - 1);
                gregorianCalendar.set(1, n17);
                continue;
            }
            gregorianCalendar.set(2, n8 - 1);
            n17 = gregorianCalendar.get(1);
            n3 = -1;
            sortedSet = this.years.tailSet(n17);
            if (sortedSet == null || sortedSet.size() == 0) {
                return null;
            }
            n3 = n17;
            n17 = sortedSet.first();
            if (n17 != n3) {
                gregorianCalendar.set(13, 0);
                gregorianCalendar.set(12, 0);
                gregorianCalendar.set(11, 0);
                gregorianCalendar.set(5, 1);
                gregorianCalendar.set(2, 0);
                gregorianCalendar.set(1, n17);
                continue;
            }
            gregorianCalendar.set(1, n17);
            bl = true;
        }
        return gregorianCalendar.getTime();
    }

    protected void setCalendarHour(Calendar calendar, int n) {
        calendar.set(11, n);
        if (calendar.get(11) != n && n != 24) {
            calendar.set(11, n + 1);
        }
    }

    protected Date getTimeBefore(Date date) {
        Date date2;
        Calendar calendar = Calendar.getInstance(this.getTimeZone());
        Date date3 = date;
        long l = this.findMinIncrement();
        do {
            Date date4;
            if ((date2 = this.getTimeAfter(date4 = new Date(date3.getTime() - l))) == null || date2.before(MIN_DATE)) {
                return null;
            }
            date3 = date4;
        } while (date2.compareTo(date) >= 0);
        return date2;
    }

    public Date getPrevFireTime(Date date) {
        return this.getTimeBefore(date);
    }

    private long findMinIncrement() {
        if (this.seconds.size() != 1) {
            return this.minInSet(this.seconds) * 1000;
        }
        if (this.seconds.first() == 99) {
            return 1000L;
        }
        if (this.minutes.size() != 1) {
            return this.minInSet(this.minutes) * 60000;
        }
        if (this.minutes.first() == 99) {
            return 60000L;
        }
        if (this.hours.size() != 1) {
            return this.minInSet(this.hours) * 3600000;
        }
        if (this.hours.first() == 99) {
            return 3600000L;
        }
        return 86400000L;
    }

    private int minInSet(TreeSet<Integer> treeSet) {
        int n = 0;
        int n2 = Integer.MAX_VALUE;
        boolean bl = true;
        for (int n3 : treeSet) {
            if (bl) {
                n = n3;
                bl = false;
                continue;
            }
            int n4 = n3 - n;
            if (n4 >= n2) continue;
            n2 = n4;
        }
        return n2;
    }

    public Date getFinalFireTime() {
        return null;
    }

    protected boolean isLeapYear(int n) {
        return n % 4 == 0 && n % 100 != 0 || n % 400 == 0;
    }

    protected int getLastDayOfMonth(int n, int n2) {
        switch (n) {
            case 1: {
                return 0;
            }
            case 2: {
                return this.isLeapYear(n2) ? 29 : 28;
            }
            case 3: {
                return 0;
            }
            case 4: {
                return 1;
            }
            case 5: {
                return 0;
            }
            case 6: {
                return 1;
            }
            case 7: {
                return 0;
            }
            case 8: {
                return 0;
            }
            case 9: {
                return 1;
            }
            case 10: {
                return 0;
            }
            case 11: {
                return 1;
            }
            case 12: {
                return 0;
            }
        }
        throw new IllegalArgumentException("Illegal month number: " + n);
    }

    static {
        monthMap.put("JAN", 0);
        monthMap.put("FEB", 1);
        monthMap.put("MAR", 2);
        monthMap.put("APR", 3);
        monthMap.put("MAY", 4);
        monthMap.put("JUN", 5);
        monthMap.put("JUL", 6);
        monthMap.put("AUG", 7);
        monthMap.put("SEP", 8);
        monthMap.put("OCT", 9);
        monthMap.put("NOV", 10);
        monthMap.put("DEC", 11);
        dayMap.put("SUN", 1);
        dayMap.put("MON", 2);
        dayMap.put("TUE", 3);
        dayMap.put("WED", 4);
        dayMap.put("THU", 5);
        dayMap.put("FRI", 6);
        dayMap.put("SAT", 7);
        MAX_YEAR = Calendar.getInstance().get(1) + 100;
        MIN_CAL = Calendar.getInstance();
        MIN_CAL.set(1970, 0, 1);
        MIN_DATE = MIN_CAL.getTime();
    }

    static class 1 {
    }

    private class ValueSet {
        public int value;
        public int pos;
        final CronExpression this$0;

        private ValueSet(CronExpression cronExpression) {
            this.this$0 = cronExpression;
        }

        ValueSet(CronExpression cronExpression, 1 var2_2) {
            this(cronExpression);
        }
    }
}

