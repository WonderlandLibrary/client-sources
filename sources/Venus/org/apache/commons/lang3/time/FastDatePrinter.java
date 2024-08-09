/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.time;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.text.DateFormatSymbols;
import java.text.FieldPosition;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.time.CalendarReflection;
import org.apache.commons.lang3.time.DatePrinter;

public class FastDatePrinter
implements DatePrinter,
Serializable {
    private static final long serialVersionUID = 1L;
    public static final int FULL = 0;
    public static final int LONG = 1;
    public static final int MEDIUM = 2;
    public static final int SHORT = 3;
    private final String mPattern;
    private final TimeZone mTimeZone;
    private final Locale mLocale;
    private transient Rule[] mRules;
    private transient int mMaxLengthEstimate;
    private static final int MAX_DIGITS = 10;
    private static final ConcurrentMap<TimeZoneDisplayKey, String> cTimeZoneDisplayCache = new ConcurrentHashMap<TimeZoneDisplayKey, String>(7);

    protected FastDatePrinter(String string, TimeZone timeZone, Locale locale) {
        this.mPattern = string;
        this.mTimeZone = timeZone;
        this.mLocale = locale;
        this.init();
    }

    private void init() {
        List<Rule> list = this.parsePattern();
        this.mRules = list.toArray(new Rule[list.size()]);
        int n = 0;
        int n2 = this.mRules.length;
        while (--n2 >= 0) {
            n += this.mRules[n2].estimateLength();
        }
        this.mMaxLengthEstimate = n;
    }

    protected List<Rule> parsePattern() {
        DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(this.mLocale);
        ArrayList<Rule> arrayList = new ArrayList<Rule>();
        String[] stringArray = dateFormatSymbols.getEras();
        String[] stringArray2 = dateFormatSymbols.getMonths();
        String[] stringArray3 = dateFormatSymbols.getShortMonths();
        String[] stringArray4 = dateFormatSymbols.getWeekdays();
        String[] stringArray5 = dateFormatSymbols.getShortWeekdays();
        String[] stringArray6 = dateFormatSymbols.getAmPmStrings();
        int n = this.mPattern.length();
        int[] nArray = new int[1];
        for (int i = 0; i < n; ++i) {
            Rule rule;
            nArray[0] = i;
            String string = this.parseToken(this.mPattern, nArray);
            i = nArray[0];
            int n2 = string.length();
            if (n2 == 0) break;
            char c = string.charAt(0);
            switch (c) {
                case 'G': {
                    rule = new TextField(0, stringArray);
                    break;
                }
                case 'Y': 
                case 'y': {
                    rule = n2 == 2 ? TwoDigitYearField.INSTANCE : this.selectNumberRule(1, n2 < 4 ? 4 : n2);
                    if (c != 'Y') break;
                    rule = new WeekYear((NumberRule)rule);
                    break;
                }
                case 'M': {
                    if (n2 >= 4) {
                        rule = new TextField(2, stringArray2);
                        break;
                    }
                    if (n2 == 3) {
                        rule = new TextField(2, stringArray3);
                        break;
                    }
                    if (n2 == 2) {
                        rule = TwoDigitMonthField.INSTANCE;
                        break;
                    }
                    rule = UnpaddedMonthField.INSTANCE;
                    break;
                }
                case 'd': {
                    rule = this.selectNumberRule(5, n2);
                    break;
                }
                case 'h': {
                    rule = new TwelveHourField(this.selectNumberRule(10, n2));
                    break;
                }
                case 'H': {
                    rule = this.selectNumberRule(11, n2);
                    break;
                }
                case 'm': {
                    rule = this.selectNumberRule(12, n2);
                    break;
                }
                case 's': {
                    rule = this.selectNumberRule(13, n2);
                    break;
                }
                case 'S': {
                    rule = this.selectNumberRule(14, n2);
                    break;
                }
                case 'E': {
                    rule = new TextField(7, n2 < 4 ? stringArray5 : stringArray4);
                    break;
                }
                case 'u': {
                    rule = new DayInWeekField(this.selectNumberRule(7, n2));
                    break;
                }
                case 'D': {
                    rule = this.selectNumberRule(6, n2);
                    break;
                }
                case 'F': {
                    rule = this.selectNumberRule(8, n2);
                    break;
                }
                case 'w': {
                    rule = this.selectNumberRule(3, n2);
                    break;
                }
                case 'W': {
                    rule = this.selectNumberRule(4, n2);
                    break;
                }
                case 'a': {
                    rule = new TextField(9, stringArray6);
                    break;
                }
                case 'k': {
                    rule = new TwentyFourHourField(this.selectNumberRule(11, n2));
                    break;
                }
                case 'K': {
                    rule = this.selectNumberRule(10, n2);
                    break;
                }
                case 'X': {
                    rule = Iso8601_Rule.getRule(n2);
                    break;
                }
                case 'z': {
                    if (n2 >= 4) {
                        rule = new TimeZoneNameRule(this.mTimeZone, this.mLocale, 1);
                        break;
                    }
                    rule = new TimeZoneNameRule(this.mTimeZone, this.mLocale, 0);
                    break;
                }
                case 'Z': {
                    if (n2 == 1) {
                        rule = TimeZoneNumberRule.INSTANCE_NO_COLON;
                        break;
                    }
                    if (n2 == 2) {
                        rule = Iso8601_Rule.ISO8601_HOURS_COLON_MINUTES;
                        break;
                    }
                    rule = TimeZoneNumberRule.INSTANCE_COLON;
                    break;
                }
                case '\'': {
                    String string2 = string.substring(1);
                    if (string2.length() == 1) {
                        rule = new CharacterLiteral(string2.charAt(0));
                        break;
                    }
                    rule = new StringLiteral(string2);
                    break;
                }
                default: {
                    throw new IllegalArgumentException("Illegal pattern component: " + string);
                }
            }
            arrayList.add(rule);
        }
        return arrayList;
    }

    protected String parseToken(String string, int[] nArray) {
        int n;
        StringBuilder stringBuilder = new StringBuilder();
        int n2 = string.length();
        char c = string.charAt(n);
        if (c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z') {
            char c2;
            stringBuilder.append(c);
            while (n + 1 < n2 && (c2 = string.charAt(n + 1)) == c) {
                stringBuilder.append(c);
                ++n;
            }
        } else {
            stringBuilder.append('\'');
            boolean bl = false;
            for (n = nArray[0]; n < n2; ++n) {
                c = string.charAt(n);
                if (c == '\'') {
                    if (n + 1 < n2 && string.charAt(n + 1) == '\'') {
                        ++n;
                        stringBuilder.append(c);
                        continue;
                    }
                    bl = !bl;
                    continue;
                }
                if (bl || (c < 'A' || c > 'Z') && (c < 'a' || c > 'z')) {
                    stringBuilder.append(c);
                    continue;
                }
                break;
            }
        }
        nArray[0] = --n;
        return stringBuilder.toString();
    }

    protected NumberRule selectNumberRule(int n, int n2) {
        switch (n2) {
            case 1: {
                return new UnpaddedNumberField(n);
            }
            case 2: {
                return new TwoDigitNumberField(n);
            }
        }
        return new PaddedNumberField(n, n2);
    }

    @Override
    @Deprecated
    public StringBuffer format(Object object, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        if (object instanceof Date) {
            return this.format((Date)object, stringBuffer);
        }
        if (object instanceof Calendar) {
            return this.format((Calendar)object, stringBuffer);
        }
        if (object instanceof Long) {
            return this.format((long)((Long)object), stringBuffer);
        }
        throw new IllegalArgumentException("Unknown class: " + (object == null ? "<null>" : object.getClass().getName()));
    }

    String format(Object object) {
        if (object instanceof Date) {
            return this.format((Date)object);
        }
        if (object instanceof Calendar) {
            return this.format((Calendar)object);
        }
        if (object instanceof Long) {
            return this.format((Long)object);
        }
        throw new IllegalArgumentException("Unknown class: " + (object == null ? "<null>" : object.getClass().getName()));
    }

    @Override
    public String format(long l) {
        Calendar calendar = this.newCalendar();
        calendar.setTimeInMillis(l);
        return this.applyRulesToString(calendar);
    }

    private String applyRulesToString(Calendar calendar) {
        return this.applyRules(calendar, new StringBuilder(this.mMaxLengthEstimate)).toString();
    }

    private Calendar newCalendar() {
        return Calendar.getInstance(this.mTimeZone, this.mLocale);
    }

    @Override
    public String format(Date date) {
        Calendar calendar = this.newCalendar();
        calendar.setTime(date);
        return this.applyRulesToString(calendar);
    }

    @Override
    public String format(Calendar calendar) {
        return this.format(calendar, new StringBuilder(this.mMaxLengthEstimate)).toString();
    }

    @Override
    public StringBuffer format(long l, StringBuffer stringBuffer) {
        Calendar calendar = this.newCalendar();
        calendar.setTimeInMillis(l);
        return this.applyRules(calendar, (Appendable)stringBuffer);
    }

    @Override
    public StringBuffer format(Date date, StringBuffer stringBuffer) {
        Calendar calendar = this.newCalendar();
        calendar.setTime(date);
        return this.applyRules(calendar, (Appendable)stringBuffer);
    }

    @Override
    public StringBuffer format(Calendar calendar, StringBuffer stringBuffer) {
        return this.format(calendar.getTime(), stringBuffer);
    }

    @Override
    public <B extends Appendable> B format(long l, B b) {
        Calendar calendar = this.newCalendar();
        calendar.setTimeInMillis(l);
        return this.applyRules(calendar, b);
    }

    @Override
    public <B extends Appendable> B format(Date date, B b) {
        Calendar calendar = this.newCalendar();
        calendar.setTime(date);
        return this.applyRules(calendar, b);
    }

    @Override
    public <B extends Appendable> B format(Calendar calendar, B b) {
        if (!calendar.getTimeZone().equals(this.mTimeZone)) {
            calendar = (Calendar)calendar.clone();
            calendar.setTimeZone(this.mTimeZone);
        }
        return this.applyRules(calendar, b);
    }

    @Deprecated
    protected StringBuffer applyRules(Calendar calendar, StringBuffer stringBuffer) {
        return this.applyRules(calendar, (Appendable)stringBuffer);
    }

    private <B extends Appendable> B applyRules(Calendar calendar, B b) {
        try {
            for (Rule rule : this.mRules) {
                rule.appendTo(b, calendar);
            }
        } catch (IOException iOException) {
            ExceptionUtils.rethrow(iOException);
        }
        return b;
    }

    @Override
    public String getPattern() {
        return this.mPattern;
    }

    @Override
    public TimeZone getTimeZone() {
        return this.mTimeZone;
    }

    @Override
    public Locale getLocale() {
        return this.mLocale;
    }

    public int getMaxLengthEstimate() {
        return this.mMaxLengthEstimate;
    }

    public boolean equals(Object object) {
        if (!(object instanceof FastDatePrinter)) {
            return true;
        }
        FastDatePrinter fastDatePrinter = (FastDatePrinter)object;
        return this.mPattern.equals(fastDatePrinter.mPattern) && this.mTimeZone.equals(fastDatePrinter.mTimeZone) && this.mLocale.equals(fastDatePrinter.mLocale);
    }

    public int hashCode() {
        return this.mPattern.hashCode() + 13 * (this.mTimeZone.hashCode() + 13 * this.mLocale.hashCode());
    }

    public String toString() {
        return "FastDatePrinter[" + this.mPattern + "," + this.mLocale + "," + this.mTimeZone.getID() + "]";
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.init();
    }

    private static void appendDigits(Appendable appendable, int n) throws IOException {
        appendable.append((char)(n / 10 + 48));
        appendable.append((char)(n % 10 + 48));
    }

    private static void appendFullDigits(Appendable appendable, int n, int n2) throws IOException {
        if (n < 10000) {
            int n3 = 4;
            if (n < 1000) {
                --n3;
                if (n < 100) {
                    --n3;
                    if (n < 10) {
                        --n3;
                    }
                }
            }
            for (int i = n2 - n3; i > 0; --i) {
                appendable.append('0');
            }
            switch (n3) {
                case 4: {
                    appendable.append((char)(n / 1000 + 48));
                    n %= 1000;
                }
                case 3: {
                    if (n >= 100) {
                        appendable.append((char)(n / 100 + 48));
                        n %= 100;
                    } else {
                        appendable.append('0');
                    }
                }
                case 2: {
                    if (n >= 10) {
                        appendable.append((char)(n / 10 + 48));
                        n %= 10;
                    } else {
                        appendable.append('0');
                    }
                }
                case 1: {
                    appendable.append((char)(n + 48));
                }
            }
        } else {
            char[] cArray = new char[10];
            int n4 = 0;
            while (n != 0) {
                cArray[n4++] = (char)(n % 10 + 48);
                n /= 10;
            }
            while (n4 < n2) {
                appendable.append('0');
                --n2;
            }
            while (--n4 >= 0) {
                appendable.append(cArray[n4]);
            }
        }
    }

    static String getTimeZoneDisplay(TimeZone timeZone, boolean bl, int n, Locale locale) {
        String string;
        TimeZoneDisplayKey timeZoneDisplayKey = new TimeZoneDisplayKey(timeZone, bl, n, locale);
        String string2 = (String)cTimeZoneDisplayCache.get(timeZoneDisplayKey);
        if (string2 == null && (string = cTimeZoneDisplayCache.putIfAbsent(timeZoneDisplayKey, string2 = timeZone.getDisplayName(bl, n, locale))) != null) {
            string2 = string;
        }
        return string2;
    }

    static void access$000(Appendable appendable, int n) throws IOException {
        FastDatePrinter.appendDigits(appendable, n);
    }

    static void access$100(Appendable appendable, int n, int n2) throws IOException {
        FastDatePrinter.appendFullDigits(appendable, n, n2);
    }

    private static class TimeZoneDisplayKey {
        private final TimeZone mTimeZone;
        private final int mStyle;
        private final Locale mLocale;

        TimeZoneDisplayKey(TimeZone timeZone, boolean bl, int n, Locale locale) {
            this.mTimeZone = timeZone;
            this.mStyle = bl ? n | Integer.MIN_VALUE : n;
            this.mLocale = locale;
        }

        public int hashCode() {
            return (this.mStyle * 31 + this.mLocale.hashCode()) * 31 + this.mTimeZone.hashCode();
        }

        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (object instanceof TimeZoneDisplayKey) {
                TimeZoneDisplayKey timeZoneDisplayKey = (TimeZoneDisplayKey)object;
                return this.mTimeZone.equals(timeZoneDisplayKey.mTimeZone) && this.mStyle == timeZoneDisplayKey.mStyle && this.mLocale.equals(timeZoneDisplayKey.mLocale);
            }
            return true;
        }
    }

    private static class Iso8601_Rule
    implements Rule {
        static final Iso8601_Rule ISO8601_HOURS = new Iso8601_Rule(3);
        static final Iso8601_Rule ISO8601_HOURS_MINUTES = new Iso8601_Rule(5);
        static final Iso8601_Rule ISO8601_HOURS_COLON_MINUTES = new Iso8601_Rule(6);
        final int length;

        static Iso8601_Rule getRule(int n) {
            switch (n) {
                case 1: {
                    return ISO8601_HOURS;
                }
                case 2: {
                    return ISO8601_HOURS_MINUTES;
                }
                case 3: {
                    return ISO8601_HOURS_COLON_MINUTES;
                }
            }
            throw new IllegalArgumentException("invalid number of X");
        }

        Iso8601_Rule(int n) {
            this.length = n;
        }

        @Override
        public int estimateLength() {
            return this.length;
        }

        @Override
        public void appendTo(Appendable appendable, Calendar calendar) throws IOException {
            int n = calendar.get(15) + calendar.get(16);
            if (n == 0) {
                appendable.append("Z");
                return;
            }
            if (n < 0) {
                appendable.append('-');
                n = -n;
            } else {
                appendable.append('+');
            }
            int n2 = n / 3600000;
            FastDatePrinter.access$000(appendable, n2);
            if (this.length < 5) {
                return;
            }
            if (this.length == 6) {
                appendable.append(':');
            }
            int n3 = n / 60000 - 60 * n2;
            FastDatePrinter.access$000(appendable, n3);
        }
    }

    private static class TimeZoneNumberRule
    implements Rule {
        static final TimeZoneNumberRule INSTANCE_COLON = new TimeZoneNumberRule(true);
        static final TimeZoneNumberRule INSTANCE_NO_COLON = new TimeZoneNumberRule(false);
        final boolean mColon;

        TimeZoneNumberRule(boolean bl) {
            this.mColon = bl;
        }

        @Override
        public int estimateLength() {
            return 0;
        }

        @Override
        public void appendTo(Appendable appendable, Calendar calendar) throws IOException {
            int n = calendar.get(15) + calendar.get(16);
            if (n < 0) {
                appendable.append('-');
                n = -n;
            } else {
                appendable.append('+');
            }
            int n2 = n / 3600000;
            FastDatePrinter.access$000(appendable, n2);
            if (this.mColon) {
                appendable.append(':');
            }
            int n3 = n / 60000 - 60 * n2;
            FastDatePrinter.access$000(appendable, n3);
        }
    }

    private static class TimeZoneNameRule
    implements Rule {
        private final Locale mLocale;
        private final int mStyle;
        private final String mStandard;
        private final String mDaylight;

        TimeZoneNameRule(TimeZone timeZone, Locale locale, int n) {
            this.mLocale = locale;
            this.mStyle = n;
            this.mStandard = FastDatePrinter.getTimeZoneDisplay(timeZone, false, n, locale);
            this.mDaylight = FastDatePrinter.getTimeZoneDisplay(timeZone, true, n, locale);
        }

        @Override
        public int estimateLength() {
            return Math.max(this.mStandard.length(), this.mDaylight.length());
        }

        @Override
        public void appendTo(Appendable appendable, Calendar calendar) throws IOException {
            TimeZone timeZone = calendar.getTimeZone();
            if (calendar.get(16) != 0) {
                appendable.append(FastDatePrinter.getTimeZoneDisplay(timeZone, true, this.mStyle, this.mLocale));
            } else {
                appendable.append(FastDatePrinter.getTimeZoneDisplay(timeZone, false, this.mStyle, this.mLocale));
            }
        }
    }

    private static class WeekYear
    implements NumberRule {
        private final NumberRule mRule;

        WeekYear(NumberRule numberRule) {
            this.mRule = numberRule;
        }

        @Override
        public int estimateLength() {
            return this.mRule.estimateLength();
        }

        @Override
        public void appendTo(Appendable appendable, Calendar calendar) throws IOException {
            this.mRule.appendTo(appendable, CalendarReflection.getWeekYear(calendar));
        }

        @Override
        public void appendTo(Appendable appendable, int n) throws IOException {
            this.mRule.appendTo(appendable, n);
        }
    }

    private static class DayInWeekField
    implements NumberRule {
        private final NumberRule mRule;

        DayInWeekField(NumberRule numberRule) {
            this.mRule = numberRule;
        }

        @Override
        public int estimateLength() {
            return this.mRule.estimateLength();
        }

        @Override
        public void appendTo(Appendable appendable, Calendar calendar) throws IOException {
            int n = calendar.get(7);
            this.mRule.appendTo(appendable, n != 1 ? n - 1 : 7);
        }

        @Override
        public void appendTo(Appendable appendable, int n) throws IOException {
            this.mRule.appendTo(appendable, n);
        }
    }

    private static class TwentyFourHourField
    implements NumberRule {
        private final NumberRule mRule;

        TwentyFourHourField(NumberRule numberRule) {
            this.mRule = numberRule;
        }

        @Override
        public int estimateLength() {
            return this.mRule.estimateLength();
        }

        @Override
        public void appendTo(Appendable appendable, Calendar calendar) throws IOException {
            int n = calendar.get(11);
            if (n == 0) {
                n = calendar.getMaximum(11) + 1;
            }
            this.mRule.appendTo(appendable, n);
        }

        @Override
        public void appendTo(Appendable appendable, int n) throws IOException {
            this.mRule.appendTo(appendable, n);
        }
    }

    private static class TwelveHourField
    implements NumberRule {
        private final NumberRule mRule;

        TwelveHourField(NumberRule numberRule) {
            this.mRule = numberRule;
        }

        @Override
        public int estimateLength() {
            return this.mRule.estimateLength();
        }

        @Override
        public void appendTo(Appendable appendable, Calendar calendar) throws IOException {
            int n = calendar.get(10);
            if (n == 0) {
                n = calendar.getLeastMaximum(10) + 1;
            }
            this.mRule.appendTo(appendable, n);
        }

        @Override
        public void appendTo(Appendable appendable, int n) throws IOException {
            this.mRule.appendTo(appendable, n);
        }
    }

    private static class TwoDigitMonthField
    implements NumberRule {
        static final TwoDigitMonthField INSTANCE = new TwoDigitMonthField();

        TwoDigitMonthField() {
        }

        @Override
        public int estimateLength() {
            return 1;
        }

        @Override
        public void appendTo(Appendable appendable, Calendar calendar) throws IOException {
            this.appendTo(appendable, calendar.get(2) + 1);
        }

        @Override
        public final void appendTo(Appendable appendable, int n) throws IOException {
            FastDatePrinter.access$000(appendable, n);
        }
    }

    private static class TwoDigitYearField
    implements NumberRule {
        static final TwoDigitYearField INSTANCE = new TwoDigitYearField();

        TwoDigitYearField() {
        }

        @Override
        public int estimateLength() {
            return 1;
        }

        @Override
        public void appendTo(Appendable appendable, Calendar calendar) throws IOException {
            this.appendTo(appendable, calendar.get(1) % 100);
        }

        @Override
        public final void appendTo(Appendable appendable, int n) throws IOException {
            FastDatePrinter.access$000(appendable, n);
        }
    }

    private static class TwoDigitNumberField
    implements NumberRule {
        private final int mField;

        TwoDigitNumberField(int n) {
            this.mField = n;
        }

        @Override
        public int estimateLength() {
            return 1;
        }

        @Override
        public void appendTo(Appendable appendable, Calendar calendar) throws IOException {
            this.appendTo(appendable, calendar.get(this.mField));
        }

        @Override
        public final void appendTo(Appendable appendable, int n) throws IOException {
            if (n < 100) {
                FastDatePrinter.access$000(appendable, n);
            } else {
                FastDatePrinter.access$100(appendable, n, 2);
            }
        }
    }

    private static class PaddedNumberField
    implements NumberRule {
        private final int mField;
        private final int mSize;

        PaddedNumberField(int n, int n2) {
            if (n2 < 3) {
                throw new IllegalArgumentException();
            }
            this.mField = n;
            this.mSize = n2;
        }

        @Override
        public int estimateLength() {
            return this.mSize;
        }

        @Override
        public void appendTo(Appendable appendable, Calendar calendar) throws IOException {
            this.appendTo(appendable, calendar.get(this.mField));
        }

        @Override
        public final void appendTo(Appendable appendable, int n) throws IOException {
            FastDatePrinter.access$100(appendable, n, this.mSize);
        }
    }

    private static class UnpaddedMonthField
    implements NumberRule {
        static final UnpaddedMonthField INSTANCE = new UnpaddedMonthField();

        UnpaddedMonthField() {
        }

        @Override
        public int estimateLength() {
            return 1;
        }

        @Override
        public void appendTo(Appendable appendable, Calendar calendar) throws IOException {
            this.appendTo(appendable, calendar.get(2) + 1);
        }

        @Override
        public final void appendTo(Appendable appendable, int n) throws IOException {
            if (n < 10) {
                appendable.append((char)(n + 48));
            } else {
                FastDatePrinter.access$000(appendable, n);
            }
        }
    }

    private static class UnpaddedNumberField
    implements NumberRule {
        private final int mField;

        UnpaddedNumberField(int n) {
            this.mField = n;
        }

        @Override
        public int estimateLength() {
            return 1;
        }

        @Override
        public void appendTo(Appendable appendable, Calendar calendar) throws IOException {
            this.appendTo(appendable, calendar.get(this.mField));
        }

        @Override
        public final void appendTo(Appendable appendable, int n) throws IOException {
            if (n < 10) {
                appendable.append((char)(n + 48));
            } else if (n < 100) {
                FastDatePrinter.access$000(appendable, n);
            } else {
                FastDatePrinter.access$100(appendable, n, 1);
            }
        }
    }

    private static class TextField
    implements Rule {
        private final int mField;
        private final String[] mValues;

        TextField(int n, String[] stringArray) {
            this.mField = n;
            this.mValues = stringArray;
        }

        @Override
        public int estimateLength() {
            int n = 0;
            int n2 = this.mValues.length;
            while (--n2 >= 0) {
                int n3 = this.mValues[n2].length();
                if (n3 <= n) continue;
                n = n3;
            }
            return n;
        }

        @Override
        public void appendTo(Appendable appendable, Calendar calendar) throws IOException {
            appendable.append(this.mValues[calendar.get(this.mField)]);
        }
    }

    private static class StringLiteral
    implements Rule {
        private final String mValue;

        StringLiteral(String string) {
            this.mValue = string;
        }

        @Override
        public int estimateLength() {
            return this.mValue.length();
        }

        @Override
        public void appendTo(Appendable appendable, Calendar calendar) throws IOException {
            appendable.append(this.mValue);
        }
    }

    private static class CharacterLiteral
    implements Rule {
        private final char mValue;

        CharacterLiteral(char c) {
            this.mValue = c;
        }

        @Override
        public int estimateLength() {
            return 0;
        }

        @Override
        public void appendTo(Appendable appendable, Calendar calendar) throws IOException {
            appendable.append(this.mValue);
        }
    }

    private static interface NumberRule
    extends Rule {
        public void appendTo(Appendable var1, int var2) throws IOException;
    }

    private static interface Rule {
        public int estimateLength();

        public void appendTo(Appendable var1, Calendar var2) throws IOException;
    }
}

