/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.util.datetime;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.core.util.datetime.DateParser;

public class FastDateParser
implements DateParser,
Serializable {
    private static final long serialVersionUID = 3L;
    static final Locale JAPANESE_IMPERIAL = new Locale("ja", "JP", "JP");
    private final String pattern;
    private final TimeZone timeZone;
    private final Locale locale;
    private final int century;
    private final int startYear;
    private transient List<StrategyAndWidth> patterns;
    private static final Comparator<String> LONGER_FIRST_LOWERCASE = new Comparator<String>(){

        @Override
        public int compare(String string, String string2) {
            return string2.compareTo(string);
        }

        @Override
        public int compare(Object object, Object object2) {
            return this.compare((String)object, (String)object2);
        }
    };
    private static final ConcurrentMap<Locale, Strategy>[] caches = new ConcurrentMap[17];
    private static final Strategy ABBREVIATED_YEAR_STRATEGY = new NumberStrategy(1){

        @Override
        int modify(FastDateParser fastDateParser, int n) {
            return n < 100 ? FastDateParser.access$700(fastDateParser, n) : n;
        }
    };
    private static final Strategy NUMBER_MONTH_STRATEGY = new NumberStrategy(2){

        @Override
        int modify(FastDateParser fastDateParser, int n) {
            return n - 1;
        }
    };
    private static final Strategy LITERAL_YEAR_STRATEGY = new NumberStrategy(1);
    private static final Strategy WEEK_OF_YEAR_STRATEGY = new NumberStrategy(3);
    private static final Strategy WEEK_OF_MONTH_STRATEGY = new NumberStrategy(4);
    private static final Strategy DAY_OF_YEAR_STRATEGY = new NumberStrategy(6);
    private static final Strategy DAY_OF_MONTH_STRATEGY = new NumberStrategy(5);
    private static final Strategy DAY_OF_WEEK_STRATEGY = new NumberStrategy(7){

        @Override
        int modify(FastDateParser fastDateParser, int n) {
            return n != 7 ? n + 1 : 1;
        }
    };
    private static final Strategy DAY_OF_WEEK_IN_MONTH_STRATEGY = new NumberStrategy(8);
    private static final Strategy HOUR_OF_DAY_STRATEGY = new NumberStrategy(11);
    private static final Strategy HOUR24_OF_DAY_STRATEGY = new NumberStrategy(11){

        @Override
        int modify(FastDateParser fastDateParser, int n) {
            return n == 24 ? 0 : n;
        }
    };
    private static final Strategy HOUR12_STRATEGY = new NumberStrategy(10){

        @Override
        int modify(FastDateParser fastDateParser, int n) {
            return n == 12 ? 0 : n;
        }
    };
    private static final Strategy HOUR_STRATEGY = new NumberStrategy(10);
    private static final Strategy MINUTE_STRATEGY = new NumberStrategy(12);
    private static final Strategy SECOND_STRATEGY = new NumberStrategy(13);
    private static final Strategy MILLISECOND_STRATEGY = new NumberStrategy(14);

    protected FastDateParser(String string, TimeZone timeZone, Locale locale) {
        this(string, timeZone, locale, null);
    }

    protected FastDateParser(String string, TimeZone timeZone, Locale locale, Date date) {
        int n;
        this.pattern = string;
        this.timeZone = timeZone;
        this.locale = locale;
        Calendar calendar = Calendar.getInstance(timeZone, locale);
        if (date != null) {
            calendar.setTime(date);
            n = calendar.get(1);
        } else if (locale.equals(JAPANESE_IMPERIAL)) {
            n = 0;
        } else {
            calendar.setTime(new Date());
            n = calendar.get(1) - 80;
        }
        this.century = n / 100 * 100;
        this.startYear = n - this.century;
        this.init(calendar);
    }

    private void init(Calendar calendar) {
        StrategyAndWidth strategyAndWidth;
        this.patterns = new ArrayList<StrategyAndWidth>();
        StrategyParser strategyParser = new StrategyParser(this, calendar);
        while ((strategyAndWidth = strategyParser.getNextStrategy()) != null) {
            this.patterns.add(strategyAndWidth);
        }
    }

    private static boolean isFormatLetter(char c) {
        return c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z';
    }

    @Override
    public String getPattern() {
        return this.pattern;
    }

    @Override
    public TimeZone getTimeZone() {
        return this.timeZone;
    }

    @Override
    public Locale getLocale() {
        return this.locale;
    }

    public boolean equals(Object object) {
        if (!(object instanceof FastDateParser)) {
            return true;
        }
        FastDateParser fastDateParser = (FastDateParser)object;
        return this.pattern.equals(fastDateParser.pattern) && this.timeZone.equals(fastDateParser.timeZone) && this.locale.equals(fastDateParser.locale);
    }

    public int hashCode() {
        return this.pattern.hashCode() + 13 * (this.timeZone.hashCode() + 13 * this.locale.hashCode());
    }

    public String toString() {
        return "FastDateParser[" + this.pattern + "," + this.locale + "," + this.timeZone.getID() + "]";
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        Calendar calendar = Calendar.getInstance(this.timeZone, this.locale);
        this.init(calendar);
    }

    @Override
    public Object parseObject(String string) throws ParseException {
        return this.parse(string);
    }

    @Override
    public Date parse(String string) throws ParseException {
        ParsePosition parsePosition = new ParsePosition(0);
        Date date = this.parse(string, parsePosition);
        if (date == null) {
            if (this.locale.equals(JAPANESE_IMPERIAL)) {
                throw new ParseException("(The " + this.locale + " locale does not support dates before 1868 AD)\n" + "Unparseable date: \"" + string, parsePosition.getErrorIndex());
            }
            throw new ParseException("Unparseable date: " + string, parsePosition.getErrorIndex());
        }
        return date;
    }

    @Override
    public Object parseObject(String string, ParsePosition parsePosition) {
        return this.parse(string, parsePosition);
    }

    @Override
    public Date parse(String string, ParsePosition parsePosition) {
        Calendar calendar = Calendar.getInstance(this.timeZone, this.locale);
        calendar.clear();
        return this.parse(string, parsePosition, calendar) ? calendar.getTime() : null;
    }

    @Override
    public boolean parse(String string, ParsePosition parsePosition, Calendar calendar) {
        ListIterator<StrategyAndWidth> listIterator2 = this.patterns.listIterator();
        while (listIterator2.hasNext()) {
            StrategyAndWidth strategyAndWidth = listIterator2.next();
            int n = strategyAndWidth.getMaxWidth(listIterator2);
            if (strategyAndWidth.strategy.parse(this, calendar, string, parsePosition, n)) continue;
            return true;
        }
        return false;
    }

    private static StringBuilder simpleQuote(StringBuilder stringBuilder, String string) {
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            switch (c) {
                case '$': 
                case '(': 
                case ')': 
                case '*': 
                case '+': 
                case '.': 
                case '?': 
                case '[': 
                case '\\': 
                case '^': 
                case '{': 
                case '|': {
                    stringBuilder.append('\\');
                }
            }
            stringBuilder.append(c);
        }
        return stringBuilder;
    }

    private static Map<String, Integer> appendDisplayNames(Calendar calendar, Locale locale, int n, StringBuilder stringBuilder) {
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        Map<String, Integer> map = calendar.getDisplayNames(n, 0, locale);
        TreeSet<String> treeSet = new TreeSet<String>(LONGER_FIRST_LOWERCASE);
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String string = entry.getKey().toLowerCase(locale);
            if (!treeSet.add(string)) continue;
            hashMap.put(string, entry.getValue());
        }
        for (String string : treeSet) {
            FastDateParser.simpleQuote(stringBuilder, string).append('|');
        }
        return hashMap;
    }

    private int adjustYear(int n) {
        int n2 = this.century + n;
        return n >= this.startYear ? n2 : n2 + 100;
    }

    private Strategy getStrategy(char c, int n, Calendar calendar) {
        switch (c) {
            default: {
                throw new IllegalArgumentException("Format '" + c + "' not supported");
            }
            case 'D': {
                return DAY_OF_YEAR_STRATEGY;
            }
            case 'E': {
                return this.getLocaleSpecificStrategy(7, calendar);
            }
            case 'F': {
                return DAY_OF_WEEK_IN_MONTH_STRATEGY;
            }
            case 'G': {
                return this.getLocaleSpecificStrategy(0, calendar);
            }
            case 'H': {
                return HOUR_OF_DAY_STRATEGY;
            }
            case 'K': {
                return HOUR_STRATEGY;
            }
            case 'M': {
                return n >= 3 ? this.getLocaleSpecificStrategy(2, calendar) : NUMBER_MONTH_STRATEGY;
            }
            case 'S': {
                return MILLISECOND_STRATEGY;
            }
            case 'W': {
                return WEEK_OF_MONTH_STRATEGY;
            }
            case 'a': {
                return this.getLocaleSpecificStrategy(9, calendar);
            }
            case 'd': {
                return DAY_OF_MONTH_STRATEGY;
            }
            case 'h': {
                return HOUR12_STRATEGY;
            }
            case 'k': {
                return HOUR24_OF_DAY_STRATEGY;
            }
            case 'm': {
                return MINUTE_STRATEGY;
            }
            case 's': {
                return SECOND_STRATEGY;
            }
            case 'u': {
                return DAY_OF_WEEK_STRATEGY;
            }
            case 'w': {
                return WEEK_OF_YEAR_STRATEGY;
            }
            case 'Y': 
            case 'y': {
                return n > 2 ? LITERAL_YEAR_STRATEGY : ABBREVIATED_YEAR_STRATEGY;
            }
            case 'X': {
                return ISO8601TimeZoneStrategy.getStrategy(n);
            }
            case 'Z': {
                if (n != 2) break;
                return ISO8601TimeZoneStrategy.access$400();
            }
            case 'z': 
        }
        return this.getLocaleSpecificStrategy(15, calendar);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static ConcurrentMap<Locale, Strategy> getCache(int n) {
        ConcurrentMap<Locale, Strategy>[] concurrentMapArray = caches;
        synchronized (caches) {
            if (caches[n] == null) {
                FastDateParser.caches[n] = new ConcurrentHashMap<Locale, Strategy>(3);
            }
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return caches[n];
        }
    }

    private Strategy getLocaleSpecificStrategy(int n, Calendar calendar) {
        Strategy strategy;
        ConcurrentMap<Locale, Strategy> concurrentMap = FastDateParser.getCache(n);
        Strategy strategy2 = (Strategy)concurrentMap.get(this.locale);
        if (strategy2 == null && (strategy = concurrentMap.putIfAbsent(this.locale, strategy2 = n == 15 ? new TimeZoneStrategy(this.locale) : new CaseInsensitiveTextStrategy(n, calendar, this.locale))) != null) {
            return strategy;
        }
        return strategy2;
    }

    static String access$000(FastDateParser fastDateParser) {
        return fastDateParser.pattern;
    }

    static boolean access$100(char c) {
        return FastDateParser.isFormatLetter(c);
    }

    static Strategy access$200(FastDateParser fastDateParser, char c, int n, Calendar calendar) {
        return fastDateParser.getStrategy(c, n, calendar);
    }

    static Map access$600(Calendar calendar, Locale locale, int n, StringBuilder stringBuilder) {
        return FastDateParser.appendDisplayNames(calendar, locale, n, stringBuilder);
    }

    static int access$700(FastDateParser fastDateParser, int n) {
        return fastDateParser.adjustYear(n);
    }

    static Comparator access$800() {
        return LONGER_FIRST_LOWERCASE;
    }

    static StringBuilder access$900(StringBuilder stringBuilder, String string) {
        return FastDateParser.simpleQuote(stringBuilder, string);
    }

    private static class ISO8601TimeZoneStrategy
    extends PatternStrategy {
        private static final Strategy ISO_8601_1_STRATEGY = new ISO8601TimeZoneStrategy("(Z|(?:[+-]\\d{2}))");
        private static final Strategy ISO_8601_2_STRATEGY = new ISO8601TimeZoneStrategy("(Z|(?:[+-]\\d{2}\\d{2}))");
        private static final Strategy ISO_8601_3_STRATEGY = new ISO8601TimeZoneStrategy("(Z|(?:[+-]\\d{2}(?::)\\d{2}))");

        ISO8601TimeZoneStrategy(String string) {
            super(null);
            this.createPattern(string);
        }

        @Override
        void setCalendar(FastDateParser fastDateParser, Calendar calendar, String string) {
            if (string.equals("Z")) {
                calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
            } else {
                calendar.setTimeZone(TimeZone.getTimeZone("GMT" + string));
            }
        }

        static Strategy getStrategy(int n) {
            switch (n) {
                case 1: {
                    return ISO_8601_1_STRATEGY;
                }
                case 2: {
                    return ISO_8601_2_STRATEGY;
                }
                case 3: {
                    return ISO_8601_3_STRATEGY;
                }
            }
            throw new IllegalArgumentException("invalid number of X");
        }

        static Strategy access$400() {
            return ISO_8601_3_STRATEGY;
        }
    }

    static class TimeZoneStrategy
    extends PatternStrategy {
        private static final String RFC_822_TIME_ZONE = "[+-]\\d{4}";
        private static final String GMT_OPTION = "GMT[+-]\\d{1,2}:\\d{2}";
        private final Locale locale;
        private final Map<String, TzInfo> tzNames = new HashMap<String, TzInfo>();
        private static final int ID = 0;

        TimeZoneStrategy(Locale locale) {
            super(null);
            this.locale = locale;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("((?iu)[+-]\\d{4}|GMT[+-]\\d{1,2}:\\d{2}");
            TreeSet<String> treeSet = new TreeSet<String>(FastDateParser.access$800());
            String[][] stringArray = DateFormatSymbols.getInstance(locale).getZoneStrings();
            for (String[] stringArray2 : stringArray) {
                TzInfo tzInfo;
                String string = stringArray2[0];
                if (string.equalsIgnoreCase("GMT")) continue;
                TimeZone timeZone = TimeZone.getTimeZone(string);
                TzInfo tzInfo2 = tzInfo = new TzInfo(timeZone, false);
                for (int i = 1; i < stringArray2.length; ++i) {
                    String string2;
                    switch (i) {
                        case 3: {
                            tzInfo2 = new TzInfo(timeZone, true);
                            break;
                        }
                        case 5: {
                            tzInfo2 = tzInfo;
                        }
                    }
                    if (stringArray2[i] == null || !treeSet.add(string2 = stringArray2[i].toLowerCase(locale))) continue;
                    this.tzNames.put(string2, tzInfo2);
                }
            }
            for (String string : treeSet) {
                FastDateParser.access$900(stringBuilder.append('|'), string);
            }
            stringBuilder.append(")");
            this.createPattern(stringBuilder);
        }

        @Override
        void setCalendar(FastDateParser fastDateParser, Calendar calendar, String string) {
            if (string.charAt(0) == '+' || string.charAt(0) == '-') {
                TimeZone timeZone = TimeZone.getTimeZone("GMT" + string);
                calendar.setTimeZone(timeZone);
            } else if (string.regionMatches(true, 0, "GMT", 0, 0)) {
                TimeZone timeZone = TimeZone.getTimeZone(string.toUpperCase());
                calendar.setTimeZone(timeZone);
            } else {
                TzInfo tzInfo = this.tzNames.get(string.toLowerCase(this.locale));
                calendar.set(16, tzInfo.dstOffset);
                calendar.set(15, tzInfo.zone.getRawOffset());
            }
        }

        private static class TzInfo {
            TimeZone zone;
            int dstOffset;

            TzInfo(TimeZone timeZone, boolean bl) {
                this.zone = timeZone;
                this.dstOffset = bl ? timeZone.getDSTSavings() : 0;
            }
        }
    }

    private static class NumberStrategy
    extends Strategy {
        private final int field;

        NumberStrategy(int n) {
            super(null);
            this.field = n;
        }

        @Override
        boolean isNumber() {
            return false;
        }

        @Override
        boolean parse(FastDateParser fastDateParser, Calendar calendar, String string, ParsePosition parsePosition, int n) {
            int n2;
            int n3;
            int n4 = string.length();
            if (n == 0) {
                for (n3 = parsePosition.getIndex(); n3 < n4 && Character.isWhitespace((char)(n2 = (int)string.charAt(n3))); ++n3) {
                }
                parsePosition.setIndex(n3);
            } else {
                n2 = n3 + n;
                if (n4 > n2) {
                    n4 = n2;
                }
            }
            while (n3 < n4 && Character.isDigit((char)(n2 = (int)string.charAt(n3)))) {
                ++n3;
            }
            if (parsePosition.getIndex() == n3) {
                parsePosition.setErrorIndex(n3);
                return true;
            }
            n2 = Integer.parseInt(string.substring(parsePosition.getIndex(), n3));
            parsePosition.setIndex(n3);
            calendar.set(this.field, this.modify(fastDateParser, n2));
            return false;
        }

        int modify(FastDateParser fastDateParser, int n) {
            return n;
        }
    }

    private static class CaseInsensitiveTextStrategy
    extends PatternStrategy {
        private final int field;
        final Locale locale;
        private final Map<String, Integer> lKeyValues;

        CaseInsensitiveTextStrategy(int n, Calendar calendar, Locale locale) {
            super(null);
            this.field = n;
            this.locale = locale;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("((?iu)");
            this.lKeyValues = FastDateParser.access$600(calendar, locale, n, stringBuilder);
            stringBuilder.setLength(stringBuilder.length() - 1);
            stringBuilder.append(")");
            this.createPattern(stringBuilder);
        }

        @Override
        void setCalendar(FastDateParser fastDateParser, Calendar calendar, String string) {
            Integer n = this.lKeyValues.get(string.toLowerCase(this.locale));
            calendar.set(this.field, n);
        }
    }

    private static class CopyQuotedStrategy
    extends Strategy {
        private final String formatField;

        CopyQuotedStrategy(String string) {
            super(null);
            this.formatField = string;
        }

        @Override
        boolean isNumber() {
            return true;
        }

        @Override
        boolean parse(FastDateParser fastDateParser, Calendar calendar, String string, ParsePosition parsePosition, int n) {
            for (int i = 0; i < this.formatField.length(); ++i) {
                int n2 = i + parsePosition.getIndex();
                if (n2 == string.length()) {
                    parsePosition.setErrorIndex(n2);
                    return true;
                }
                if (this.formatField.charAt(i) == string.charAt(n2)) continue;
                parsePosition.setErrorIndex(n2);
                return true;
            }
            parsePosition.setIndex(this.formatField.length() + parsePosition.getIndex());
            return false;
        }
    }

    private static abstract class PatternStrategy
    extends Strategy {
        private Pattern pattern;

        private PatternStrategy() {
            super(null);
        }

        void createPattern(StringBuilder stringBuilder) {
            this.createPattern(stringBuilder.toString());
        }

        void createPattern(String string) {
            this.pattern = Pattern.compile(string);
        }

        @Override
        boolean isNumber() {
            return true;
        }

        @Override
        boolean parse(FastDateParser fastDateParser, Calendar calendar, String string, ParsePosition parsePosition, int n) {
            Matcher matcher = this.pattern.matcher(string.substring(parsePosition.getIndex()));
            if (!matcher.lookingAt()) {
                parsePosition.setErrorIndex(parsePosition.getIndex());
                return true;
            }
            parsePosition.setIndex(parsePosition.getIndex() + matcher.end(1));
            this.setCalendar(fastDateParser, calendar, matcher.group(1));
            return false;
        }

        abstract void setCalendar(FastDateParser var1, Calendar var2, String var3);

        PatternStrategy(1 var1_1) {
            this();
        }
    }

    private static abstract class Strategy {
        private Strategy() {
        }

        boolean isNumber() {
            return true;
        }

        abstract boolean parse(FastDateParser var1, Calendar var2, String var3, ParsePosition var4, int var5);

        Strategy(1 var1_1) {
            this();
        }
    }

    private class StrategyParser {
        private final Calendar definingCalendar;
        private int currentIdx;
        final FastDateParser this$0;

        StrategyParser(FastDateParser fastDateParser, Calendar calendar) {
            this.this$0 = fastDateParser;
            this.definingCalendar = calendar;
        }

        StrategyAndWidth getNextStrategy() {
            if (this.currentIdx >= FastDateParser.access$000(this.this$0).length()) {
                return null;
            }
            char c = FastDateParser.access$000(this.this$0).charAt(this.currentIdx);
            if (FastDateParser.access$100(c)) {
                return this.letterPattern(c);
            }
            return this.literal();
        }

        private StrategyAndWidth letterPattern(char c) {
            int n = this.currentIdx;
            while (++this.currentIdx < FastDateParser.access$000(this.this$0).length() && FastDateParser.access$000(this.this$0).charAt(this.currentIdx) == c) {
            }
            int n2 = this.currentIdx - n;
            return new StrategyAndWidth(FastDateParser.access$200(this.this$0, c, n2, this.definingCalendar), n2);
        }

        private StrategyAndWidth literal() {
            boolean bl = false;
            StringBuilder stringBuilder = new StringBuilder();
            while (this.currentIdx < FastDateParser.access$000(this.this$0).length()) {
                char c = FastDateParser.access$000(this.this$0).charAt(this.currentIdx);
                if (!bl && FastDateParser.access$100(c)) break;
                if (c == '\'' && (++this.currentIdx == FastDateParser.access$000(this.this$0).length() || FastDateParser.access$000(this.this$0).charAt(this.currentIdx) != '\'')) {
                    bl = !bl;
                    continue;
                }
                ++this.currentIdx;
                stringBuilder.append(c);
            }
            if (bl) {
                throw new IllegalArgumentException("Unterminated quote");
            }
            String string = stringBuilder.toString();
            return new StrategyAndWidth(new CopyQuotedStrategy(string), string.length());
        }
    }

    private static class StrategyAndWidth {
        final Strategy strategy;
        final int width;

        StrategyAndWidth(Strategy strategy, int n) {
            this.strategy = strategy;
            this.width = n;
        }

        int getMaxWidth(ListIterator<StrategyAndWidth> listIterator2) {
            if (!this.strategy.isNumber() || !listIterator2.hasNext()) {
                return 1;
            }
            Strategy strategy = listIterator2.next().strategy;
            listIterator2.previous();
            return strategy.isNumber() ? this.width : 0;
        }
    }
}

