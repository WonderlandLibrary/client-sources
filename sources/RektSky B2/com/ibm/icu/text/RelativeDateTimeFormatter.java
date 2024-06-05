package com.ibm.icu.text;

import java.util.*;
import java.text.*;
import com.ibm.icu.lang.*;
import com.ibm.icu.impl.*;
import com.ibm.icu.util.*;

public final class RelativeDateTimeFormatter
{
    private int[] styleToDateFormatSymbolsWidth;
    private final EnumMap<Style, EnumMap<AbsoluteUnit, EnumMap<Direction, String>>> qualitativeUnitMap;
    private final EnumMap<Style, EnumMap<RelativeUnit, String[][]>> patternMap;
    private final String combinedDateAndTime;
    private final PluralRules pluralRules;
    private final NumberFormat numberFormat;
    private final Style style;
    private final DisplayContext capitalizationContext;
    private final BreakIterator breakIterator;
    private final ULocale locale;
    private final DateFormatSymbols dateFormatSymbols;
    private static final Style[] fallbackCache;
    private static final Cache cache;
    
    public static RelativeDateTimeFormatter getInstance() {
        return getInstance(ULocale.getDefault(), null, Style.LONG, DisplayContext.CAPITALIZATION_NONE);
    }
    
    public static RelativeDateTimeFormatter getInstance(final ULocale locale) {
        return getInstance(locale, null, Style.LONG, DisplayContext.CAPITALIZATION_NONE);
    }
    
    public static RelativeDateTimeFormatter getInstance(final Locale locale) {
        return getInstance(ULocale.forLocale(locale));
    }
    
    public static RelativeDateTimeFormatter getInstance(final ULocale locale, final NumberFormat nf) {
        return getInstance(locale, nf, Style.LONG, DisplayContext.CAPITALIZATION_NONE);
    }
    
    public static RelativeDateTimeFormatter getInstance(final ULocale locale, NumberFormat nf, final Style style, final DisplayContext capitalizationContext) {
        final RelativeDateTimeFormatterData data = RelativeDateTimeFormatter.cache.get(locale);
        if (nf == null) {
            nf = NumberFormat.getInstance(locale);
        }
        else {
            nf = (NumberFormat)nf.clone();
        }
        return new RelativeDateTimeFormatter(data.qualitativeUnitMap, data.relUnitPatternMap, SimpleFormatterImpl.compileToStringMinMaxArguments(data.dateTimePattern, new StringBuilder(), 2, 2), PluralRules.forLocale(locale), nf, style, capitalizationContext, (capitalizationContext == DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE) ? BreakIterator.getSentenceInstance(locale) : null, locale);
    }
    
    public static RelativeDateTimeFormatter getInstance(final Locale locale, final NumberFormat nf) {
        return getInstance(ULocale.forLocale(locale), nf);
    }
    
    public String format(final double quantity, final Direction direction, final RelativeUnit unit) {
        if (direction != Direction.LAST && direction != Direction.NEXT) {
            throw new IllegalArgumentException("direction must be NEXT or LAST");
        }
        final int pastFutureIndex = (direction == Direction.NEXT) ? 1 : 0;
        final String result;
        synchronized (this.numberFormat) {
            final StringBuffer formatStr = new StringBuffer();
            final DontCareFieldPosition fieldPosition = DontCareFieldPosition.INSTANCE;
            final StandardPlural pluralForm = QuantityFormatter.selectPlural(quantity, this.numberFormat, this.pluralRules, formatStr, fieldPosition);
            final String formatter = this.getRelativeUnitPluralPattern(this.style, unit, pastFutureIndex, pluralForm);
            result = SimpleFormatterImpl.formatCompiledPattern(formatter, formatStr);
        }
        return this.adjustForContext(result);
    }
    
    public String formatNumeric(double offset, final RelativeDateTimeUnit unit) {
        RelativeUnit relunit = RelativeUnit.SECONDS;
        switch (unit) {
            case YEAR: {
                relunit = RelativeUnit.YEARS;
                break;
            }
            case QUARTER: {
                relunit = RelativeUnit.QUARTERS;
                break;
            }
            case MONTH: {
                relunit = RelativeUnit.MONTHS;
                break;
            }
            case WEEK: {
                relunit = RelativeUnit.WEEKS;
                break;
            }
            case DAY: {
                relunit = RelativeUnit.DAYS;
                break;
            }
            case HOUR: {
                relunit = RelativeUnit.HOURS;
                break;
            }
            case MINUTE: {
                relunit = RelativeUnit.MINUTES;
                break;
            }
            case SECOND: {
                break;
            }
            default: {
                throw new UnsupportedOperationException("formatNumeric does not currently support RelativeUnit.SUNDAY..SATURDAY");
            }
        }
        Direction direction = Direction.NEXT;
        if (Double.compare(offset, 0.0) < 0) {
            direction = Direction.LAST;
            offset = -offset;
        }
        final String result = this.format(offset, direction, relunit);
        return (result != null) ? result : "";
    }
    
    public String format(final Direction direction, final AbsoluteUnit unit) {
        if (unit == AbsoluteUnit.NOW && direction != Direction.PLAIN) {
            throw new IllegalArgumentException("NOW can only accept direction PLAIN.");
        }
        String result;
        if (direction == Direction.PLAIN && AbsoluteUnit.SUNDAY.ordinal() <= unit.ordinal() && unit.ordinal() <= AbsoluteUnit.SATURDAY.ordinal()) {
            final int dateSymbolsDayOrdinal = unit.ordinal() - AbsoluteUnit.SUNDAY.ordinal() + 1;
            final String[] dayNames = this.dateFormatSymbols.getWeekdays(1, this.styleToDateFormatSymbolsWidth[this.style.ordinal()]);
            result = dayNames[dateSymbolsDayOrdinal];
        }
        else {
            result = this.getAbsoluteUnitString(this.style, unit, direction);
        }
        return (result != null) ? this.adjustForContext(result) : null;
    }
    
    public String format(final double offset, final RelativeDateTimeUnit unit) {
        boolean useNumeric = true;
        Direction direction = Direction.THIS;
        if (offset > -2.1 && offset < 2.1) {
            final double offsetx100 = offset * 100.0;
            final int intoffsetx100 = (offsetx100 < 0.0) ? ((int)(offsetx100 - 0.5)) : ((int)(offsetx100 + 0.5));
            switch (intoffsetx100) {
                case -200: {
                    direction = Direction.LAST_2;
                    useNumeric = false;
                    break;
                }
                case -100: {
                    direction = Direction.LAST;
                    useNumeric = false;
                    break;
                }
                case 0: {
                    useNumeric = false;
                    break;
                }
                case 100: {
                    direction = Direction.NEXT;
                    useNumeric = false;
                    break;
                }
                case 200: {
                    direction = Direction.NEXT_2;
                    useNumeric = false;
                    break;
                }
            }
        }
        AbsoluteUnit absunit = AbsoluteUnit.NOW;
        switch (unit) {
            case YEAR: {
                absunit = AbsoluteUnit.YEAR;
                break;
            }
            case QUARTER: {
                absunit = AbsoluteUnit.QUARTER;
                break;
            }
            case MONTH: {
                absunit = AbsoluteUnit.MONTH;
                break;
            }
            case WEEK: {
                absunit = AbsoluteUnit.WEEK;
                break;
            }
            case DAY: {
                absunit = AbsoluteUnit.DAY;
                break;
            }
            case SUNDAY: {
                absunit = AbsoluteUnit.SUNDAY;
                break;
            }
            case MONDAY: {
                absunit = AbsoluteUnit.MONDAY;
                break;
            }
            case TUESDAY: {
                absunit = AbsoluteUnit.TUESDAY;
                break;
            }
            case WEDNESDAY: {
                absunit = AbsoluteUnit.WEDNESDAY;
                break;
            }
            case THURSDAY: {
                absunit = AbsoluteUnit.THURSDAY;
                break;
            }
            case FRIDAY: {
                absunit = AbsoluteUnit.FRIDAY;
                break;
            }
            case SATURDAY: {
                absunit = AbsoluteUnit.SATURDAY;
                break;
            }
            case SECOND: {
                if (direction == Direction.THIS) {
                    direction = Direction.PLAIN;
                    break;
                }
                useNumeric = true;
                break;
            }
            default: {
                useNumeric = true;
                break;
            }
        }
        if (!useNumeric) {
            final String result = this.format(direction, absunit);
            if (result != null && result.length() > 0) {
                return result;
            }
        }
        return this.formatNumeric(offset, unit);
    }
    
    private String getAbsoluteUnitString(Style style, final AbsoluteUnit unit, final Direction direction) {
        do {
            final EnumMap<AbsoluteUnit, EnumMap<Direction, String>> unitMap = this.qualitativeUnitMap.get(style);
            if (unitMap != null) {
                final EnumMap<Direction, String> dirMap = unitMap.get(unit);
                if (dirMap == null) {
                    continue;
                }
                final String result = dirMap.get(direction);
                if (result != null) {
                    return result;
                }
                continue;
            }
        } while ((style = RelativeDateTimeFormatter.fallbackCache[style.ordinal()]) != null);
        return null;
    }
    
    public String combineDateAndTime(final String relativeDateString, final String timeString) {
        return SimpleFormatterImpl.formatCompiledPattern(this.combinedDateAndTime, timeString, relativeDateString);
    }
    
    public NumberFormat getNumberFormat() {
        synchronized (this.numberFormat) {
            return (NumberFormat)this.numberFormat.clone();
        }
    }
    
    public DisplayContext getCapitalizationContext() {
        return this.capitalizationContext;
    }
    
    public Style getFormatStyle() {
        return this.style;
    }
    
    private String adjustForContext(final String originalFormattedString) {
        if (this.breakIterator == null || originalFormattedString.length() == 0 || !UCharacter.isLowerCase(UCharacter.codePointAt(originalFormattedString, 0))) {
            return originalFormattedString;
        }
        synchronized (this.breakIterator) {
            return UCharacter.toTitleCase(this.locale, originalFormattedString, this.breakIterator, 768);
        }
    }
    
    private RelativeDateTimeFormatter(final EnumMap<Style, EnumMap<AbsoluteUnit, EnumMap<Direction, String>>> qualitativeUnitMap, final EnumMap<Style, EnumMap<RelativeUnit, String[][]>> patternMap, final String combinedDateAndTime, final PluralRules pluralRules, final NumberFormat numberFormat, final Style style, final DisplayContext capitalizationContext, final BreakIterator breakIterator, final ULocale locale) {
        this.styleToDateFormatSymbolsWidth = new int[] { 1, 3, 2 };
        this.qualitativeUnitMap = qualitativeUnitMap;
        this.patternMap = patternMap;
        this.combinedDateAndTime = combinedDateAndTime;
        this.pluralRules = pluralRules;
        this.numberFormat = numberFormat;
        this.style = style;
        if (capitalizationContext.type() != DisplayContext.Type.CAPITALIZATION) {
            throw new IllegalArgumentException(capitalizationContext.toString());
        }
        this.capitalizationContext = capitalizationContext;
        this.breakIterator = breakIterator;
        this.locale = locale;
        this.dateFormatSymbols = new DateFormatSymbols(locale);
    }
    
    private String getRelativeUnitPluralPattern(final Style style, final RelativeUnit unit, final int pastFutureIndex, final StandardPlural pluralForm) {
        if (pluralForm != StandardPlural.OTHER) {
            final String formatter = this.getRelativeUnitPattern(style, unit, pastFutureIndex, pluralForm);
            if (formatter != null) {
                return formatter;
            }
        }
        return this.getRelativeUnitPattern(style, unit, pastFutureIndex, StandardPlural.OTHER);
    }
    
    private String getRelativeUnitPattern(Style style, final RelativeUnit unit, final int pastFutureIndex, final StandardPlural pluralForm) {
        final int pluralIndex = pluralForm.ordinal();
        do {
            final EnumMap<RelativeUnit, String[][]> unitMap = this.patternMap.get(style);
            if (unitMap != null) {
                final String[][] spfCompiledPatterns = unitMap.get(unit);
                if (spfCompiledPatterns != null && spfCompiledPatterns[pastFutureIndex][pluralIndex] != null) {
                    return spfCompiledPatterns[pastFutureIndex][pluralIndex];
                }
                continue;
            }
        } while ((style = RelativeDateTimeFormatter.fallbackCache[style.ordinal()]) != null);
        return null;
    }
    
    private static Direction keyToDirection(final UResource.Key key) {
        if (key.contentEquals("-2")) {
            return Direction.LAST_2;
        }
        if (key.contentEquals("-1")) {
            return Direction.LAST;
        }
        if (key.contentEquals("0")) {
            return Direction.THIS;
        }
        if (key.contentEquals("1")) {
            return Direction.NEXT;
        }
        if (key.contentEquals("2")) {
            return Direction.NEXT_2;
        }
        return null;
    }
    
    static {
        fallbackCache = new Style[3];
        cache = new Cache();
    }
    
    public enum Style
    {
        LONG, 
        SHORT, 
        NARROW;
        
        private static final int INDEX_COUNT = 3;
    }
    
    public enum RelativeUnit
    {
        SECONDS, 
        MINUTES, 
        HOURS, 
        DAYS, 
        WEEKS, 
        MONTHS, 
        YEARS, 
        @Deprecated
        QUARTERS;
    }
    
    public enum AbsoluteUnit
    {
        SUNDAY, 
        MONDAY, 
        TUESDAY, 
        WEDNESDAY, 
        THURSDAY, 
        FRIDAY, 
        SATURDAY, 
        DAY, 
        WEEK, 
        MONTH, 
        YEAR, 
        NOW, 
        @Deprecated
        QUARTER;
    }
    
    public enum Direction
    {
        LAST_2, 
        LAST, 
        THIS, 
        NEXT, 
        NEXT_2, 
        PLAIN;
    }
    
    public enum RelativeDateTimeUnit
    {
        YEAR, 
        QUARTER, 
        MONTH, 
        WEEK, 
        DAY, 
        HOUR, 
        MINUTE, 
        SECOND, 
        SUNDAY, 
        MONDAY, 
        TUESDAY, 
        WEDNESDAY, 
        THURSDAY, 
        FRIDAY, 
        SATURDAY;
    }
    
    private static class RelativeDateTimeFormatterData
    {
        public final EnumMap<Style, EnumMap<AbsoluteUnit, EnumMap<Direction, String>>> qualitativeUnitMap;
        EnumMap<Style, EnumMap<RelativeUnit, String[][]>> relUnitPatternMap;
        public final String dateTimePattern;
        
        public RelativeDateTimeFormatterData(final EnumMap<Style, EnumMap<AbsoluteUnit, EnumMap<Direction, String>>> qualitativeUnitMap, final EnumMap<Style, EnumMap<RelativeUnit, String[][]>> relUnitPatternMap, final String dateTimePattern) {
            this.qualitativeUnitMap = qualitativeUnitMap;
            this.relUnitPatternMap = relUnitPatternMap;
            this.dateTimePattern = dateTimePattern;
        }
    }
    
    private static class Cache
    {
        private final CacheBase<String, RelativeDateTimeFormatterData, ULocale> cache;
        
        private Cache() {
            this.cache = new SoftCache<String, RelativeDateTimeFormatterData, ULocale>() {
                @Override
                protected RelativeDateTimeFormatterData createInstance(final String key, final ULocale locale) {
                    return new Loader(locale).load();
                }
            };
        }
        
        public RelativeDateTimeFormatterData get(final ULocale locale) {
            final String key = locale.toString();
            return this.cache.getInstance(key, locale);
        }
    }
    
    private static final class RelDateTimeDataSink extends UResource.Sink
    {
        EnumMap<Style, EnumMap<AbsoluteUnit, EnumMap<Direction, String>>> qualitativeUnitMap;
        EnumMap<Style, EnumMap<RelativeUnit, String[][]>> styleRelUnitPatterns;
        StringBuilder sb;
        int pastFutureIndex;
        Style style;
        DateTimeUnit unit;
        
        private Style styleFromKey(final UResource.Key key) {
            if (key.endsWith("-short")) {
                return Style.SHORT;
            }
            if (key.endsWith("-narrow")) {
                return Style.NARROW;
            }
            return Style.LONG;
        }
        
        private Style styleFromAlias(final UResource.Value value) {
            final String s = value.getAliasString();
            if (s.endsWith("-short")) {
                return Style.SHORT;
            }
            if (s.endsWith("-narrow")) {
                return Style.NARROW;
            }
            return Style.LONG;
        }
        
        private static int styleSuffixLength(final Style style) {
            switch (style) {
                case SHORT: {
                    return 6;
                }
                case NARROW: {
                    return 7;
                }
                default: {
                    return 0;
                }
            }
        }
        
        public void consumeTableRelative(final UResource.Key key, final UResource.Value value) {
            final UResource.Table unitTypesTable = value.getTable();
            for (int i = 0; unitTypesTable.getKeyAndValue(i, key, value); ++i) {
                if (value.getType() == 0) {
                    final String valueString = value.getString();
                    EnumMap<AbsoluteUnit, EnumMap<Direction, String>> absMap = this.qualitativeUnitMap.get(this.style);
                    if (this.unit.relUnit == RelativeUnit.SECONDS && key.contentEquals("0")) {
                        EnumMap<Direction, String> unitStrings = absMap.get(AbsoluteUnit.NOW);
                        if (unitStrings == null) {
                            unitStrings = new EnumMap<Direction, String>(Direction.class);
                            absMap.put(AbsoluteUnit.NOW, unitStrings);
                        }
                        if (unitStrings.get(Direction.PLAIN) == null) {
                            unitStrings.put(Direction.PLAIN, valueString);
                        }
                    }
                    else {
                        final Direction keyDirection = keyToDirection(key);
                        if (keyDirection != null) {
                            final AbsoluteUnit absUnit = this.unit.absUnit;
                            if (absUnit != null) {
                                if (absMap == null) {
                                    absMap = new EnumMap<AbsoluteUnit, EnumMap<Direction, String>>(AbsoluteUnit.class);
                                    this.qualitativeUnitMap.put(this.style, absMap);
                                }
                                EnumMap<Direction, String> dirMap = absMap.get(absUnit);
                                if (dirMap == null) {
                                    dirMap = new EnumMap<Direction, String>(Direction.class);
                                    absMap.put(absUnit, dirMap);
                                }
                                if (dirMap.get(keyDirection) == null) {
                                    dirMap.put(keyDirection, value.getString());
                                }
                            }
                        }
                    }
                }
            }
        }
        
        public void consumeTableRelativeTime(final UResource.Key key, final UResource.Value value) {
            if (this.unit.relUnit == null) {
                return;
            }
            final UResource.Table unitTypesTable = value.getTable();
            for (int i = 0; unitTypesTable.getKeyAndValue(i, key, value); ++i) {
                if (key.contentEquals("past")) {
                    this.pastFutureIndex = 0;
                }
                else {
                    if (!key.contentEquals("future")) {
                        continue;
                    }
                    this.pastFutureIndex = 1;
                }
                this.consumeTimeDetail(key, value);
            }
        }
        
        public void consumeTimeDetail(final UResource.Key key, final UResource.Value value) {
            final UResource.Table unitTypesTable = value.getTable();
            EnumMap<RelativeUnit, String[][]> unitPatterns = this.styleRelUnitPatterns.get(this.style);
            if (unitPatterns == null) {
                unitPatterns = new EnumMap<RelativeUnit, String[][]>(RelativeUnit.class);
                this.styleRelUnitPatterns.put(this.style, unitPatterns);
            }
            String[][] patterns = unitPatterns.get(this.unit.relUnit);
            if (patterns == null) {
                patterns = new String[2][StandardPlural.COUNT];
                unitPatterns.put(this.unit.relUnit, patterns);
            }
            for (int i = 0; unitTypesTable.getKeyAndValue(i, key, value); ++i) {
                if (value.getType() == 0) {
                    final int pluralIndex = StandardPlural.indexFromString(key.toString());
                    if (patterns[this.pastFutureIndex][pluralIndex] == null) {
                        patterns[this.pastFutureIndex][pluralIndex] = SimpleFormatterImpl.compileToStringMinMaxArguments(value.getString(), this.sb, 0, 1);
                    }
                }
            }
        }
        
        private void handlePlainDirection(final UResource.Key key, final UResource.Value value) {
            final AbsoluteUnit absUnit = this.unit.absUnit;
            if (absUnit == null) {
                return;
            }
            EnumMap<AbsoluteUnit, EnumMap<Direction, String>> unitMap = this.qualitativeUnitMap.get(this.style);
            if (unitMap == null) {
                unitMap = new EnumMap<AbsoluteUnit, EnumMap<Direction, String>>(AbsoluteUnit.class);
                this.qualitativeUnitMap.put(this.style, unitMap);
            }
            EnumMap<Direction, String> dirMap = unitMap.get(absUnit);
            if (dirMap == null) {
                dirMap = new EnumMap<Direction, String>(Direction.class);
                unitMap.put(absUnit, dirMap);
            }
            if (dirMap.get(Direction.PLAIN) == null) {
                dirMap.put(Direction.PLAIN, value.toString());
            }
        }
        
        public void consumeTimeUnit(final UResource.Key key, final UResource.Value value) {
            final UResource.Table unitTypesTable = value.getTable();
            for (int i = 0; unitTypesTable.getKeyAndValue(i, key, value); ++i) {
                if (key.contentEquals("dn") && value.getType() == 0) {
                    this.handlePlainDirection(key, value);
                }
                if (value.getType() == 2) {
                    if (key.contentEquals("relative")) {
                        this.consumeTableRelative(key, value);
                    }
                    else if (key.contentEquals("relativeTime")) {
                        this.consumeTableRelativeTime(key, value);
                    }
                }
            }
        }
        
        private void handleAlias(final UResource.Key key, final UResource.Value value, final boolean noFallback) {
            final Style sourceStyle = this.styleFromKey(key);
            final int limit = key.length() - styleSuffixLength(sourceStyle);
            final DateTimeUnit unit = orNullFromString(key.substring(0, limit));
            if (unit == null) {
                return;
            }
            final Style targetStyle = this.styleFromAlias(value);
            if (sourceStyle == targetStyle) {
                throw new ICUException("Invalid style fallback from " + sourceStyle + " to itself");
            }
            if (RelativeDateTimeFormatter.fallbackCache[sourceStyle.ordinal()] == null) {
                RelativeDateTimeFormatter.fallbackCache[sourceStyle.ordinal()] = targetStyle;
            }
            else if (RelativeDateTimeFormatter.fallbackCache[sourceStyle.ordinal()] != targetStyle) {
                throw new ICUException("Inconsistent style fallback for style " + sourceStyle + " to " + targetStyle);
            }
        }
        
        @Override
        public void put(final UResource.Key key, final UResource.Value value, final boolean noFallback) {
            if (value.getType() == 3) {
                return;
            }
            final UResource.Table table = value.getTable();
            for (int i = 0; table.getKeyAndValue(i, key, value); ++i) {
                if (value.getType() == 3) {
                    this.handleAlias(key, value, noFallback);
                }
                else {
                    this.style = this.styleFromKey(key);
                    final int limit = key.length() - styleSuffixLength(this.style);
                    this.unit = orNullFromString(key.substring(0, limit));
                    if (this.unit != null) {
                        this.consumeTimeUnit(key, value);
                    }
                }
            }
        }
        
        RelDateTimeDataSink() {
            this.qualitativeUnitMap = new EnumMap<Style, EnumMap<AbsoluteUnit, EnumMap<Direction, String>>>(Style.class);
            this.styleRelUnitPatterns = new EnumMap<Style, EnumMap<RelativeUnit, String[][]>>(Style.class);
            this.sb = new StringBuilder();
        }
        
        private enum DateTimeUnit
        {
            SECOND(RelativeUnit.SECONDS, (AbsoluteUnit)null), 
            MINUTE(RelativeUnit.MINUTES, (AbsoluteUnit)null), 
            HOUR(RelativeUnit.HOURS, (AbsoluteUnit)null), 
            DAY(RelativeUnit.DAYS, AbsoluteUnit.DAY), 
            WEEK(RelativeUnit.WEEKS, AbsoluteUnit.WEEK), 
            MONTH(RelativeUnit.MONTHS, AbsoluteUnit.MONTH), 
            QUARTER(RelativeUnit.QUARTERS, AbsoluteUnit.QUARTER), 
            YEAR(RelativeUnit.YEARS, AbsoluteUnit.YEAR), 
            SUNDAY((RelativeUnit)null, AbsoluteUnit.SUNDAY), 
            MONDAY((RelativeUnit)null, AbsoluteUnit.MONDAY), 
            TUESDAY((RelativeUnit)null, AbsoluteUnit.TUESDAY), 
            WEDNESDAY((RelativeUnit)null, AbsoluteUnit.WEDNESDAY), 
            THURSDAY((RelativeUnit)null, AbsoluteUnit.THURSDAY), 
            FRIDAY((RelativeUnit)null, AbsoluteUnit.FRIDAY), 
            SATURDAY((RelativeUnit)null, AbsoluteUnit.SATURDAY);
            
            RelativeUnit relUnit;
            AbsoluteUnit absUnit;
            
            private DateTimeUnit(final RelativeUnit relUnit, final AbsoluteUnit absUnit) {
                this.relUnit = relUnit;
                this.absUnit = absUnit;
            }
            
            private static final DateTimeUnit orNullFromString(final CharSequence keyword) {
                switch (keyword.length()) {
                    case 3: {
                        if ("day".contentEquals(keyword)) {
                            return DateTimeUnit.DAY;
                        }
                        if ("sun".contentEquals(keyword)) {
                            return DateTimeUnit.SUNDAY;
                        }
                        if ("mon".contentEquals(keyword)) {
                            return DateTimeUnit.MONDAY;
                        }
                        if ("tue".contentEquals(keyword)) {
                            return DateTimeUnit.TUESDAY;
                        }
                        if ("wed".contentEquals(keyword)) {
                            return DateTimeUnit.WEDNESDAY;
                        }
                        if ("thu".contentEquals(keyword)) {
                            return DateTimeUnit.THURSDAY;
                        }
                        if ("fri".contentEquals(keyword)) {
                            return DateTimeUnit.FRIDAY;
                        }
                        if ("sat".contentEquals(keyword)) {
                            return DateTimeUnit.SATURDAY;
                        }
                        break;
                    }
                    case 4: {
                        if ("hour".contentEquals(keyword)) {
                            return DateTimeUnit.HOUR;
                        }
                        if ("week".contentEquals(keyword)) {
                            return DateTimeUnit.WEEK;
                        }
                        if ("year".contentEquals(keyword)) {
                            return DateTimeUnit.YEAR;
                        }
                        break;
                    }
                    case 5: {
                        if ("month".contentEquals(keyword)) {
                            return DateTimeUnit.MONTH;
                        }
                        break;
                    }
                    case 6: {
                        if ("minute".contentEquals(keyword)) {
                            return DateTimeUnit.MINUTE;
                        }
                        if ("second".contentEquals(keyword)) {
                            return DateTimeUnit.SECOND;
                        }
                        break;
                    }
                    case 7: {
                        if ("quarter".contentEquals(keyword)) {
                            return DateTimeUnit.QUARTER;
                        }
                        break;
                    }
                }
                return null;
            }
        }
    }
    
    private static class Loader
    {
        private final ULocale ulocale;
        
        public Loader(final ULocale ulocale) {
            this.ulocale = ulocale;
        }
        
        private String getDateTimePattern(final ICUResourceBundle r) {
            String calType = r.getStringWithFallback("calendar/default");
            if (calType == null || calType.equals("")) {
                calType = "gregorian";
            }
            final String resourcePath = "calendar/" + calType + "/DateTimePatterns";
            ICUResourceBundle patternsRb = r.findWithFallback(resourcePath);
            if (patternsRb == null && calType.equals("gregorian")) {
                patternsRb = r.findWithFallback("calendar/gregorian/DateTimePatterns");
            }
            if (patternsRb == null || patternsRb.getSize() < 9) {
                return "{1} {0}";
            }
            final int elementType = patternsRb.get(8).getType();
            if (elementType == 8) {
                return patternsRb.get(8).getString(0);
            }
            return patternsRb.getString(8);
        }
        
        public RelativeDateTimeFormatterData load() {
            final RelDateTimeDataSink sink = new RelDateTimeDataSink();
            final ICUResourceBundle r = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b", this.ulocale);
            r.getAllItemsWithFallback("fields", sink);
            for (final Style testStyle : Style.values()) {
                final Style newStyle1 = RelativeDateTimeFormatter.fallbackCache[testStyle.ordinal()];
                if (newStyle1 != null) {
                    final Style newStyle2 = RelativeDateTimeFormatter.fallbackCache[newStyle1.ordinal()];
                    if (newStyle2 != null && RelativeDateTimeFormatter.fallbackCache[newStyle2.ordinal()] != null) {
                        throw new IllegalStateException("Style fallback too deep");
                    }
                }
            }
            return new RelativeDateTimeFormatterData(sink.qualitativeUnitMap, sink.styleRelUnitPatterns, this.getDateTimePattern(r));
        }
    }
}
