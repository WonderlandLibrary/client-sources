/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.CacheBase;
import com.ibm.icu.impl.FormattedStringBuilder;
import com.ibm.icu.impl.FormattedValueStringBuilderImpl;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.SimpleFormatterImpl;
import com.ibm.icu.impl.SoftCache;
import com.ibm.icu.impl.StandardPlural;
import com.ibm.icu.impl.UResource;
import com.ibm.icu.impl.number.DecimalQuantity;
import com.ibm.icu.impl.number.DecimalQuantity_DualStorageBCD;
import com.ibm.icu.impl.number.SimpleModifier;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.BreakIterator;
import com.ibm.icu.text.ConstrainedFieldPosition;
import com.ibm.icu.text.DateFormatSymbols;
import com.ibm.icu.text.DecimalFormat;
import com.ibm.icu.text.DisplayContext;
import com.ibm.icu.text.FormattedValue;
import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.text.PluralRules;
import com.ibm.icu.util.ICUException;
import com.ibm.icu.util.ICUUncheckedIOException;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.text.AttributedCharacterIterator;
import java.text.Format;
import java.util.EnumMap;
import java.util.Locale;

public final class RelativeDateTimeFormatter {
    private int[] styleToDateFormatSymbolsWidth = new int[]{1, 3, 2};
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
    private static final Style[] fallbackCache = new Style[3];
    private static final Cache cache = new Cache(null);

    public static RelativeDateTimeFormatter getInstance() {
        return RelativeDateTimeFormatter.getInstance(ULocale.getDefault(), null, Style.LONG, DisplayContext.CAPITALIZATION_NONE);
    }

    public static RelativeDateTimeFormatter getInstance(ULocale uLocale) {
        return RelativeDateTimeFormatter.getInstance(uLocale, null, Style.LONG, DisplayContext.CAPITALIZATION_NONE);
    }

    public static RelativeDateTimeFormatter getInstance(Locale locale) {
        return RelativeDateTimeFormatter.getInstance(ULocale.forLocale(locale));
    }

    public static RelativeDateTimeFormatter getInstance(ULocale uLocale, NumberFormat numberFormat) {
        return RelativeDateTimeFormatter.getInstance(uLocale, numberFormat, Style.LONG, DisplayContext.CAPITALIZATION_NONE);
    }

    public static RelativeDateTimeFormatter getInstance(ULocale uLocale, NumberFormat numberFormat, Style style, DisplayContext displayContext) {
        RelativeDateTimeFormatterData relativeDateTimeFormatterData = cache.get(uLocale);
        numberFormat = numberFormat == null ? NumberFormat.getInstance(uLocale) : (NumberFormat)numberFormat.clone();
        return new RelativeDateTimeFormatter(relativeDateTimeFormatterData.qualitativeUnitMap, relativeDateTimeFormatterData.relUnitPatternMap, SimpleFormatterImpl.compileToStringMinMaxArguments(relativeDateTimeFormatterData.dateTimePattern, new StringBuilder(), 2, 2), PluralRules.forLocale(uLocale), numberFormat, style, displayContext, displayContext == DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE ? BreakIterator.getSentenceInstance(uLocale) : null, uLocale);
    }

    public static RelativeDateTimeFormatter getInstance(Locale locale, NumberFormat numberFormat) {
        return RelativeDateTimeFormatter.getInstance(ULocale.forLocale(locale), numberFormat);
    }

    public String format(double d, Direction direction, RelativeUnit relativeUnit) {
        FormattedStringBuilder formattedStringBuilder = this.formatImpl(d, direction, relativeUnit);
        return this.adjustForContext(formattedStringBuilder.toString());
    }

    public FormattedRelativeDateTime formatToValue(double d, Direction direction, RelativeUnit relativeUnit) {
        this.checkNoAdjustForContext();
        return new FormattedRelativeDateTime(this.formatImpl(d, direction, relativeUnit), null);
    }

    private FormattedStringBuilder formatImpl(double d, Direction direction, RelativeUnit relativeUnit) {
        String string;
        Object object;
        if (direction != Direction.LAST && direction != Direction.NEXT) {
            throw new IllegalArgumentException("direction must be NEXT or LAST");
        }
        int n = direction == Direction.NEXT ? 1 : 0;
        FormattedStringBuilder formattedStringBuilder = new FormattedStringBuilder();
        if (this.numberFormat instanceof DecimalFormat) {
            object = new DecimalQuantity_DualStorageBCD(d);
            ((DecimalFormat)this.numberFormat).toNumberFormatter().formatImpl((DecimalQuantity)object, formattedStringBuilder);
            string = this.pluralRules.select((PluralRules.IFixedDecimal)object);
        } else {
            object = this.numberFormat.format(d);
            formattedStringBuilder.append((CharSequence)object, null);
            string = this.pluralRules.select(d);
        }
        object = StandardPlural.orOtherFromString(string);
        String string2 = this.getRelativeUnitPluralPattern(this.style, relativeUnit, n, (StandardPlural)((Object)object));
        SimpleModifier simpleModifier = new SimpleModifier(string2, Field.LITERAL, false);
        simpleModifier.formatAsPrefixSuffix(formattedStringBuilder, 0, formattedStringBuilder.length());
        return formattedStringBuilder;
    }

    public String formatNumeric(double d, RelativeDateTimeUnit relativeDateTimeUnit) {
        FormattedStringBuilder formattedStringBuilder = this.formatNumericImpl(d, relativeDateTimeUnit);
        return this.adjustForContext(formattedStringBuilder.toString());
    }

    public FormattedRelativeDateTime formatNumericToValue(double d, RelativeDateTimeUnit relativeDateTimeUnit) {
        this.checkNoAdjustForContext();
        return new FormattedRelativeDateTime(this.formatNumericImpl(d, relativeDateTimeUnit), null);
    }

    private FormattedStringBuilder formatNumericImpl(double d, RelativeDateTimeUnit relativeDateTimeUnit) {
        RelativeUnit relativeUnit = RelativeUnit.SECONDS;
        switch (relativeDateTimeUnit) {
            case YEAR: {
                relativeUnit = RelativeUnit.YEARS;
                break;
            }
            case QUARTER: {
                relativeUnit = RelativeUnit.QUARTERS;
                break;
            }
            case MONTH: {
                relativeUnit = RelativeUnit.MONTHS;
                break;
            }
            case WEEK: {
                relativeUnit = RelativeUnit.WEEKS;
                break;
            }
            case DAY: {
                relativeUnit = RelativeUnit.DAYS;
                break;
            }
            case HOUR: {
                relativeUnit = RelativeUnit.HOURS;
                break;
            }
            case MINUTE: {
                relativeUnit = RelativeUnit.MINUTES;
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
        if (Double.compare(d, 0.0) < 0) {
            direction = Direction.LAST;
            d = -d;
        }
        return this.formatImpl(d, direction, relativeUnit);
    }

    public String format(Direction direction, AbsoluteUnit absoluteUnit) {
        String string = this.formatAbsoluteImpl(direction, absoluteUnit);
        return string != null ? this.adjustForContext(string) : null;
    }

    public FormattedRelativeDateTime formatToValue(Direction direction, AbsoluteUnit absoluteUnit) {
        this.checkNoAdjustForContext();
        String string = this.formatAbsoluteImpl(direction, absoluteUnit);
        if (string == null) {
            return null;
        }
        FormattedStringBuilder formattedStringBuilder = new FormattedStringBuilder();
        formattedStringBuilder.append(string, Field.LITERAL);
        return new FormattedRelativeDateTime(formattedStringBuilder, null);
    }

    private String formatAbsoluteImpl(Direction direction, AbsoluteUnit absoluteUnit) {
        String string;
        if (absoluteUnit == AbsoluteUnit.NOW && direction != Direction.PLAIN) {
            throw new IllegalArgumentException("NOW can only accept direction PLAIN.");
        }
        if (direction == Direction.PLAIN && AbsoluteUnit.SUNDAY.ordinal() <= absoluteUnit.ordinal() && absoluteUnit.ordinal() <= AbsoluteUnit.SATURDAY.ordinal()) {
            int n = absoluteUnit.ordinal() - AbsoluteUnit.SUNDAY.ordinal() + 1;
            String[] stringArray = this.dateFormatSymbols.getWeekdays(1, this.styleToDateFormatSymbolsWidth[this.style.ordinal()]);
            string = stringArray[n];
        } else {
            string = this.getAbsoluteUnitString(this.style, absoluteUnit, direction);
        }
        return string;
    }

    public String format(double d, RelativeDateTimeUnit relativeDateTimeUnit) {
        return this.adjustForContext(this.formatRelativeImpl(d, relativeDateTimeUnit).toString());
    }

    public FormattedRelativeDateTime formatToValue(double d, RelativeDateTimeUnit relativeDateTimeUnit) {
        FormattedStringBuilder formattedStringBuilder;
        this.checkNoAdjustForContext();
        CharSequence charSequence = this.formatRelativeImpl(d, relativeDateTimeUnit);
        if (charSequence instanceof FormattedStringBuilder) {
            formattedStringBuilder = (FormattedStringBuilder)charSequence;
        } else {
            formattedStringBuilder = new FormattedStringBuilder();
            formattedStringBuilder.append(charSequence, Field.LITERAL);
        }
        return new FormattedRelativeDateTime(formattedStringBuilder, null);
    }

    private CharSequence formatRelativeImpl(double d, RelativeDateTimeUnit relativeDateTimeUnit) {
        String string;
        boolean bl = true;
        Direction direction = Direction.THIS;
        if (d > -2.1 && d < 2.1) {
            double d2 = d * 100.0;
            int n = d2 < 0.0 ? (int)(d2 - 0.5) : (int)(d2 + 0.5);
            switch (n) {
                case -200: {
                    direction = Direction.LAST_2;
                    bl = false;
                    break;
                }
                case -100: {
                    direction = Direction.LAST;
                    bl = false;
                    break;
                }
                case 0: {
                    bl = false;
                    break;
                }
                case 100: {
                    direction = Direction.NEXT;
                    bl = false;
                    break;
                }
                case 200: {
                    direction = Direction.NEXT_2;
                    bl = false;
                    break;
                }
            }
        }
        AbsoluteUnit absoluteUnit = AbsoluteUnit.NOW;
        switch (relativeDateTimeUnit) {
            case YEAR: {
                absoluteUnit = AbsoluteUnit.YEAR;
                break;
            }
            case QUARTER: {
                absoluteUnit = AbsoluteUnit.QUARTER;
                break;
            }
            case MONTH: {
                absoluteUnit = AbsoluteUnit.MONTH;
                break;
            }
            case WEEK: {
                absoluteUnit = AbsoluteUnit.WEEK;
                break;
            }
            case DAY: {
                absoluteUnit = AbsoluteUnit.DAY;
                break;
            }
            case SUNDAY: {
                absoluteUnit = AbsoluteUnit.SUNDAY;
                break;
            }
            case MONDAY: {
                absoluteUnit = AbsoluteUnit.MONDAY;
                break;
            }
            case TUESDAY: {
                absoluteUnit = AbsoluteUnit.TUESDAY;
                break;
            }
            case WEDNESDAY: {
                absoluteUnit = AbsoluteUnit.WEDNESDAY;
                break;
            }
            case THURSDAY: {
                absoluteUnit = AbsoluteUnit.THURSDAY;
                break;
            }
            case FRIDAY: {
                absoluteUnit = AbsoluteUnit.FRIDAY;
                break;
            }
            case SATURDAY: {
                absoluteUnit = AbsoluteUnit.SATURDAY;
                break;
            }
            case HOUR: {
                absoluteUnit = AbsoluteUnit.HOUR;
                break;
            }
            case MINUTE: {
                absoluteUnit = AbsoluteUnit.MINUTE;
                break;
            }
            case SECOND: {
                if (direction == Direction.THIS) {
                    direction = Direction.PLAIN;
                    break;
                }
                bl = true;
                break;
            }
            default: {
                bl = true;
            }
        }
        if (!bl && (string = this.formatAbsoluteImpl(direction, absoluteUnit)) != null && string.length() > 0) {
            return string;
        }
        return this.formatNumericImpl(d, relativeDateTimeUnit);
    }

    private String getAbsoluteUnitString(Style style, AbsoluteUnit absoluteUnit, Direction direction) {
        do {
            String string;
            EnumMap<Direction, String> enumMap;
            EnumMap<AbsoluteUnit, EnumMap<Direction, String>> enumMap2;
            if ((enumMap2 = this.qualitativeUnitMap.get((Object)style)) == null || (enumMap = enumMap2.get((Object)absoluteUnit)) == null || (string = enumMap.get((Object)direction)) == null) continue;
            return string;
        } while ((style = fallbackCache[style.ordinal()]) != null);
        return null;
    }

    public String combineDateAndTime(String string, String string2) {
        return SimpleFormatterImpl.formatCompiledPattern(this.combinedDateAndTime, string2, string);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public NumberFormat getNumberFormat() {
        NumberFormat numberFormat = this.numberFormat;
        synchronized (numberFormat) {
            return (NumberFormat)this.numberFormat.clone();
        }
    }

    public DisplayContext getCapitalizationContext() {
        return this.capitalizationContext;
    }

    public Style getFormatStyle() {
        return this.style;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private String adjustForContext(String string) {
        if (this.breakIterator == null || string.length() == 0 || !UCharacter.isLowerCase(UCharacter.codePointAt(string, 0))) {
            return string;
        }
        BreakIterator breakIterator = this.breakIterator;
        synchronized (breakIterator) {
            return UCharacter.toTitleCase(this.locale, string, this.breakIterator, 768);
        }
    }

    private void checkNoAdjustForContext() {
        if (this.breakIterator != null) {
            throw new UnsupportedOperationException("Capitalization context is not supported in formatV");
        }
    }

    private RelativeDateTimeFormatter(EnumMap<Style, EnumMap<AbsoluteUnit, EnumMap<Direction, String>>> enumMap, EnumMap<Style, EnumMap<RelativeUnit, String[][]>> enumMap2, String string, PluralRules pluralRules, NumberFormat numberFormat, Style style, DisplayContext displayContext, BreakIterator breakIterator, ULocale uLocale) {
        this.qualitativeUnitMap = enumMap;
        this.patternMap = enumMap2;
        this.combinedDateAndTime = string;
        this.pluralRules = pluralRules;
        this.numberFormat = numberFormat;
        this.style = style;
        if (displayContext.type() != DisplayContext.Type.CAPITALIZATION) {
            throw new IllegalArgumentException(displayContext.toString());
        }
        this.capitalizationContext = displayContext;
        this.breakIterator = breakIterator;
        this.locale = uLocale;
        this.dateFormatSymbols = new DateFormatSymbols(uLocale);
    }

    private String getRelativeUnitPluralPattern(Style style, RelativeUnit relativeUnit, int n, StandardPlural standardPlural) {
        String string;
        if (standardPlural != StandardPlural.OTHER && (string = this.getRelativeUnitPattern(style, relativeUnit, n, standardPlural)) != null) {
            return string;
        }
        return this.getRelativeUnitPattern(style, relativeUnit, n, StandardPlural.OTHER);
    }

    private String getRelativeUnitPattern(Style style, RelativeUnit relativeUnit, int n, StandardPlural standardPlural) {
        int n2 = standardPlural.ordinal();
        do {
            String[][] stringArray;
            EnumMap<RelativeUnit, String[][]> enumMap;
            if ((enumMap = this.patternMap.get((Object)style)) == null || (stringArray = enumMap.get((Object)relativeUnit)) == null || stringArray[n][n2] == null) continue;
            return stringArray[n][n2];
        } while ((style = fallbackCache[style.ordinal()]) != null);
        return null;
    }

    private static Direction keyToDirection(UResource.Key key) {
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

    static Direction access$100(UResource.Key key) {
        return RelativeDateTimeFormatter.keyToDirection(key);
    }

    static Style[] access$300() {
        return fallbackCache;
    }

    private static class Loader {
        private final ULocale ulocale;

        public Loader(ULocale uLocale) {
            this.ulocale = uLocale;
        }

        private String getDateTimePattern(ICUResourceBundle iCUResourceBundle) {
            String string;
            ICUResourceBundle iCUResourceBundle2;
            String string2 = iCUResourceBundle.getStringWithFallback("calendar/default");
            if (string2 == null || string2.equals("")) {
                string2 = "gregorian";
            }
            if ((iCUResourceBundle2 = iCUResourceBundle.findWithFallback(string = "calendar/" + string2 + "/DateTimePatterns")) == null && string2.equals("gregorian")) {
                iCUResourceBundle2 = iCUResourceBundle.findWithFallback("calendar/gregorian/DateTimePatterns");
            }
            if (iCUResourceBundle2 == null || iCUResourceBundle2.getSize() < 9) {
                return "{1} {0}";
            }
            int n = iCUResourceBundle2.get(8).getType();
            if (n == 8) {
                return iCUResourceBundle2.get(8).getString(0);
            }
            return iCUResourceBundle2.getString(8);
        }

        public RelativeDateTimeFormatterData load() {
            RelDateTimeDataSink relDateTimeDataSink = new RelDateTimeDataSink();
            ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", this.ulocale);
            iCUResourceBundle.getAllItemsWithFallback("fields", relDateTimeDataSink);
            for (Style style : Style.values()) {
                Style style2;
                Style style3 = RelativeDateTimeFormatter.access$300()[style.ordinal()];
                if (style3 == null || (style2 = RelativeDateTimeFormatter.access$300()[style3.ordinal()]) == null || RelativeDateTimeFormatter.access$300()[style2.ordinal()] == null) continue;
                throw new IllegalStateException("Style fallback too deep");
            }
            return new RelativeDateTimeFormatterData(relDateTimeDataSink.qualitativeUnitMap, relDateTimeDataSink.styleRelUnitPatterns, this.getDateTimePattern(iCUResourceBundle));
        }
    }

    private static final class RelDateTimeDataSink
    extends UResource.Sink {
        EnumMap<Style, EnumMap<AbsoluteUnit, EnumMap<Direction, String>>> qualitativeUnitMap = new EnumMap(Style.class);
        EnumMap<Style, EnumMap<RelativeUnit, String[][]>> styleRelUnitPatterns = new EnumMap(Style.class);
        StringBuilder sb = new StringBuilder();
        int pastFutureIndex;
        Style style;
        DateTimeUnit unit;

        private Style styleFromKey(UResource.Key key) {
            if (key.endsWith("-short")) {
                return Style.SHORT;
            }
            if (key.endsWith("-narrow")) {
                return Style.NARROW;
            }
            return Style.LONG;
        }

        private Style styleFromAlias(UResource.Value value) {
            String string = value.getAliasString();
            if (string.endsWith("-short")) {
                return Style.SHORT;
            }
            if (string.endsWith("-narrow")) {
                return Style.NARROW;
            }
            return Style.LONG;
        }

        private static int styleSuffixLength(Style style) {
            switch (style) {
                case SHORT: {
                    return 1;
                }
                case NARROW: {
                    return 0;
                }
            }
            return 1;
        }

        public void consumeTableRelative(UResource.Key key, UResource.Value value) {
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                if (value.getType() == 0) {
                    Object object;
                    String string = value.getString();
                    EnumMap<AbsoluteUnit, EnumMap<Direction, String>> enumMap = this.qualitativeUnitMap.get((Object)this.style);
                    if (this.unit.relUnit == RelativeUnit.SECONDS && key.contentEquals("0")) {
                        object = enumMap.get((Object)AbsoluteUnit.NOW);
                        if (object == null) {
                            object = new EnumMap(Direction.class);
                            enumMap.put(AbsoluteUnit.NOW, (EnumMap<Direction, String>)object);
                        }
                        if (((EnumMap)object).get((Object)Direction.PLAIN) == null) {
                            ((EnumMap)object).put(Direction.PLAIN, string);
                        }
                    } else {
                        AbsoluteUnit absoluteUnit;
                        object = RelativeDateTimeFormatter.access$100(key);
                        if (object != null && (absoluteUnit = this.unit.absUnit) != null) {
                            EnumMap<Direction, String> enumMap2;
                            if (enumMap == null) {
                                enumMap = new EnumMap(AbsoluteUnit.class);
                                this.qualitativeUnitMap.put(this.style, enumMap);
                            }
                            if ((enumMap2 = enumMap.get((Object)absoluteUnit)) == null) {
                                enumMap2 = new EnumMap(Direction.class);
                                enumMap.put(absoluteUnit, enumMap2);
                            }
                            if (enumMap2.get(object) == null) {
                                enumMap2.put((Direction)((Object)object), value.getString());
                            }
                        }
                    }
                }
                ++n;
            }
        }

        public void consumeTableRelativeTime(UResource.Key key, UResource.Value value) {
            if (this.unit.relUnit == null) {
                return;
            }
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                block7: {
                    block6: {
                        block5: {
                            if (!key.contentEquals("past")) break block5;
                            this.pastFutureIndex = 0;
                            break block6;
                        }
                        if (!key.contentEquals("future")) break block7;
                        this.pastFutureIndex = 1;
                    }
                    this.consumeTimeDetail(key, value);
                }
                ++n;
            }
        }

        public void consumeTimeDetail(UResource.Key key, UResource.Value value) {
            String[][] stringArray;
            UResource.Table table = value.getTable();
            EnumMap<RelativeUnit, Object> enumMap = this.styleRelUnitPatterns.get((Object)this.style);
            if (enumMap == null) {
                enumMap = new EnumMap(RelativeUnit.class);
                this.styleRelUnitPatterns.put(this.style, enumMap);
            }
            if ((stringArray = enumMap.get((Object)this.unit.relUnit)) == null) {
                stringArray = new String[2][StandardPlural.COUNT];
                enumMap.put(this.unit.relUnit, (String[][])stringArray);
            }
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                int n2;
                if (value.getType() == 0 && stringArray[this.pastFutureIndex][n2 = StandardPlural.indexFromString(key.toString())] == null) {
                    stringArray[this.pastFutureIndex][n2] = SimpleFormatterImpl.compileToStringMinMaxArguments(value.getString(), this.sb, 0, 1);
                }
                ++n;
            }
        }

        private void handlePlainDirection(UResource.Key key, UResource.Value value) {
            EnumMap<Direction, String> enumMap;
            AbsoluteUnit absoluteUnit = this.unit.absUnit;
            if (absoluteUnit == null) {
                return;
            }
            EnumMap<AbsoluteUnit, EnumMap<Direction, String>> enumMap2 = this.qualitativeUnitMap.get((Object)this.style);
            if (enumMap2 == null) {
                enumMap2 = new EnumMap(AbsoluteUnit.class);
                this.qualitativeUnitMap.put(this.style, enumMap2);
            }
            if ((enumMap = enumMap2.get((Object)absoluteUnit)) == null) {
                enumMap = new EnumMap(Direction.class);
                enumMap2.put(absoluteUnit, enumMap);
            }
            if (enumMap.get((Object)Direction.PLAIN) == null) {
                enumMap.put(Direction.PLAIN, value.toString());
            }
        }

        public void consumeTimeUnit(UResource.Key key, UResource.Value value) {
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                if (key.contentEquals("dn") && value.getType() == 0) {
                    this.handlePlainDirection(key, value);
                }
                if (value.getType() == 2) {
                    if (key.contentEquals("relative")) {
                        this.consumeTableRelative(key, value);
                    } else if (key.contentEquals("relativeTime")) {
                        this.consumeTableRelativeTime(key, value);
                    }
                }
                ++n;
            }
        }

        private void handleAlias(UResource.Key key, UResource.Value value, boolean bl) {
            Style style = this.styleFromKey(key);
            int n = key.length() - RelDateTimeDataSink.styleSuffixLength(style);
            DateTimeUnit dateTimeUnit = DateTimeUnit.access$200(key.substring(0, n));
            if (dateTimeUnit != null) {
                Style style2 = this.styleFromAlias(value);
                if (style == style2) {
                    throw new ICUException("Invalid style fallback from " + (Object)((Object)style) + " to itself");
                }
                if (RelativeDateTimeFormatter.access$300()[style.ordinal()] == null) {
                    RelativeDateTimeFormatter.access$300()[style.ordinal()] = style2;
                } else if (RelativeDateTimeFormatter.access$300()[style.ordinal()] != style2) {
                    throw new ICUException("Inconsistent style fallback for style " + (Object)((Object)style) + " to " + (Object)((Object)style2));
                }
                return;
            }
        }

        @Override
        public void put(UResource.Key key, UResource.Value value, boolean bl) {
            if (value.getType() == 3) {
                return;
            }
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                if (value.getType() == 3) {
                    this.handleAlias(key, value, bl);
                } else {
                    this.style = this.styleFromKey(key);
                    int n2 = key.length() - RelDateTimeDataSink.styleSuffixLength(this.style);
                    this.unit = DateTimeUnit.access$200(key.substring(0, n2));
                    if (this.unit != null) {
                        this.consumeTimeUnit(key, value);
                    }
                }
                ++n;
            }
        }

        RelDateTimeDataSink() {
        }

        private static enum DateTimeUnit {
            SECOND(RelativeUnit.SECONDS, null),
            MINUTE(RelativeUnit.MINUTES, AbsoluteUnit.MINUTE),
            HOUR(RelativeUnit.HOURS, AbsoluteUnit.HOUR),
            DAY(RelativeUnit.DAYS, AbsoluteUnit.DAY),
            WEEK(RelativeUnit.WEEKS, AbsoluteUnit.WEEK),
            MONTH(RelativeUnit.MONTHS, AbsoluteUnit.MONTH),
            QUARTER(RelativeUnit.QUARTERS, AbsoluteUnit.QUARTER),
            YEAR(RelativeUnit.YEARS, AbsoluteUnit.YEAR),
            SUNDAY(null, AbsoluteUnit.SUNDAY),
            MONDAY(null, AbsoluteUnit.MONDAY),
            TUESDAY(null, AbsoluteUnit.TUESDAY),
            WEDNESDAY(null, AbsoluteUnit.WEDNESDAY),
            THURSDAY(null, AbsoluteUnit.THURSDAY),
            FRIDAY(null, AbsoluteUnit.FRIDAY),
            SATURDAY(null, AbsoluteUnit.SATURDAY);

            RelativeUnit relUnit;
            AbsoluteUnit absUnit;

            private DateTimeUnit(RelativeUnit relativeUnit, AbsoluteUnit absoluteUnit) {
                this.relUnit = relativeUnit;
                this.absUnit = absoluteUnit;
            }

            private static final DateTimeUnit orNullFromString(CharSequence charSequence) {
                switch (charSequence.length()) {
                    case 3: {
                        if ("day".contentEquals(charSequence)) {
                            return DAY;
                        }
                        if ("sun".contentEquals(charSequence)) {
                            return SUNDAY;
                        }
                        if ("mon".contentEquals(charSequence)) {
                            return MONDAY;
                        }
                        if ("tue".contentEquals(charSequence)) {
                            return TUESDAY;
                        }
                        if ("wed".contentEquals(charSequence)) {
                            return WEDNESDAY;
                        }
                        if ("thu".contentEquals(charSequence)) {
                            return THURSDAY;
                        }
                        if ("fri".contentEquals(charSequence)) {
                            return FRIDAY;
                        }
                        if (!"sat".contentEquals(charSequence)) break;
                        return SATURDAY;
                    }
                    case 4: {
                        if ("hour".contentEquals(charSequence)) {
                            return HOUR;
                        }
                        if ("week".contentEquals(charSequence)) {
                            return WEEK;
                        }
                        if (!"year".contentEquals(charSequence)) break;
                        return YEAR;
                    }
                    case 5: {
                        if (!"month".contentEquals(charSequence)) break;
                        return MONTH;
                    }
                    case 6: {
                        if ("minute".contentEquals(charSequence)) {
                            return MINUTE;
                        }
                        if (!"second".contentEquals(charSequence)) break;
                        return SECOND;
                    }
                    case 7: {
                        if (!"quarter".contentEquals(charSequence)) break;
                        return QUARTER;
                    }
                }
                return null;
            }

            static DateTimeUnit access$200(CharSequence charSequence) {
                return DateTimeUnit.orNullFromString(charSequence);
            }
        }
    }

    private static class Cache {
        private final CacheBase<String, RelativeDateTimeFormatterData, ULocale> cache = new SoftCache<String, RelativeDateTimeFormatterData, ULocale>(this){
            final Cache this$0;
            {
                this.this$0 = cache;
            }

            @Override
            protected RelativeDateTimeFormatterData createInstance(String string, ULocale uLocale) {
                return new Loader(uLocale).load();
            }

            @Override
            protected Object createInstance(Object object, Object object2) {
                return this.createInstance((String)object, (ULocale)object2);
            }
        };

        private Cache() {
        }

        public RelativeDateTimeFormatterData get(ULocale uLocale) {
            String string = uLocale.toString();
            return this.cache.getInstance(string, uLocale);
        }

        Cache(1 var1_1) {
            this();
        }
    }

    private static class RelativeDateTimeFormatterData {
        public final EnumMap<Style, EnumMap<AbsoluteUnit, EnumMap<Direction, String>>> qualitativeUnitMap;
        EnumMap<Style, EnumMap<RelativeUnit, String[][]>> relUnitPatternMap;
        public final String dateTimePattern;

        public RelativeDateTimeFormatterData(EnumMap<Style, EnumMap<AbsoluteUnit, EnumMap<Direction, String>>> enumMap, EnumMap<Style, EnumMap<RelativeUnit, String[][]>> enumMap2, String string) {
            this.qualitativeUnitMap = enumMap;
            this.relUnitPatternMap = enumMap2;
            this.dateTimePattern = string;
        }
    }

    public static class FormattedRelativeDateTime
    implements FormattedValue {
        private final FormattedStringBuilder string;

        private FormattedRelativeDateTime(FormattedStringBuilder formattedStringBuilder) {
            this.string = formattedStringBuilder;
        }

        @Override
        public String toString() {
            return this.string.toString();
        }

        @Override
        public int length() {
            return this.string.length();
        }

        @Override
        public char charAt(int n) {
            return this.string.charAt(n);
        }

        @Override
        public CharSequence subSequence(int n, int n2) {
            return this.string.subString(n, n2);
        }

        @Override
        public <A extends Appendable> A appendTo(A a) {
            try {
                a.append(this.string);
            } catch (IOException iOException) {
                throw new ICUUncheckedIOException(iOException);
            }
            return a;
        }

        @Override
        public boolean nextPosition(ConstrainedFieldPosition constrainedFieldPosition) {
            return FormattedValueStringBuilderImpl.nextPosition(this.string, constrainedFieldPosition, Field.NUMERIC);
        }

        @Override
        public AttributedCharacterIterator toCharacterIterator() {
            return FormattedValueStringBuilderImpl.toCharacterIterator(this.string, Field.NUMERIC);
        }

        FormattedRelativeDateTime(FormattedStringBuilder formattedStringBuilder, 1 var2_2) {
            this(formattedStringBuilder);
        }
    }

    public static class Field
    extends Format.Field {
        private static final long serialVersionUID = -5327685528663492325L;
        public static final Field LITERAL = new Field("literal");
        public static final Field NUMERIC = new Field("numeric");

        private Field(String string) {
            super(string);
        }

        @Override
        protected Object readResolve() throws InvalidObjectException {
            if (this.getName().equals(LITERAL.getName())) {
                return LITERAL;
            }
            if (this.getName().equals(NUMERIC.getName())) {
                return NUMERIC;
            }
            throw new InvalidObjectException("An invalid object.");
        }
    }

    public static enum RelativeDateTimeUnit {
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

    public static enum Direction {
        LAST_2,
        LAST,
        THIS,
        NEXT,
        NEXT_2,
        PLAIN;

    }

    public static enum AbsoluteUnit {
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
        QUARTER,
        HOUR,
        MINUTE;

    }

    public static enum RelativeUnit {
        SECONDS,
        MINUTES,
        HOURS,
        DAYS,
        WEEKS,
        MONTHS,
        YEARS,
        QUARTERS;

    }

    public static enum Style {
        LONG,
        SHORT,
        NARROW;

        private static final int INDEX_COUNT = 3;
    }
}

