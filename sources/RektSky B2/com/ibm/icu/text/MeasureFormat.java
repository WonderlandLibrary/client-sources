package com.ibm.icu.text;

import com.ibm.icu.impl.number.*;
import com.ibm.icu.number.*;
import java.math.*;
import java.text.*;
import com.ibm.icu.util.*;
import com.ibm.icu.impl.*;
import java.util.*;
import java.util.concurrent.*;
import java.io.*;

public class MeasureFormat extends UFormat
{
    static final long serialVersionUID = -7182021401701778240L;
    private final transient FormatWidth formatWidth;
    private final transient PluralRules rules;
    private final transient NumericFormatters numericFormatters;
    private final transient NumberFormat numberFormat;
    private final transient LocalizedNumberFormatter numberFormatter;
    private static final SimpleCache<ULocale, NumericFormatters> localeToNumericDurationFormatters;
    private static final Map<MeasureUnit, Integer> hmsTo012;
    private static final int MEASURE_FORMAT = 0;
    private static final int TIME_UNIT_FORMAT = 1;
    private static final int CURRENCY_FORMAT = 2;
    static final int NUMBER_FORMATTER_STANDARD = 1;
    static final int NUMBER_FORMATTER_CURRENCY = 2;
    static final int NUMBER_FORMATTER_INTEGER = 3;
    private transient NumberFormatterCacheEntry formatter1;
    private transient NumberFormatterCacheEntry formatter2;
    private transient NumberFormatterCacheEntry formatter3;
    private static final Map<ULocale, String> localeIdToRangeFormat;
    
    public static MeasureFormat getInstance(final ULocale locale, final FormatWidth formatWidth) {
        return getInstance(locale, formatWidth, NumberFormat.getInstance(locale));
    }
    
    public static MeasureFormat getInstance(final Locale locale, final FormatWidth formatWidth) {
        return getInstance(ULocale.forLocale(locale), formatWidth);
    }
    
    public static MeasureFormat getInstance(final ULocale locale, final FormatWidth formatWidth, final NumberFormat format) {
        return new MeasureFormat(locale, formatWidth, format, null, null);
    }
    
    public static MeasureFormat getInstance(final Locale locale, final FormatWidth formatWidth, final NumberFormat format) {
        return getInstance(ULocale.forLocale(locale), formatWidth, format);
    }
    
    @Override
    public StringBuffer format(final Object obj, final StringBuffer toAppendTo, final FieldPosition fpos) {
        final int prevLength = toAppendTo.length();
        fpos.setBeginIndex(0);
        fpos.setEndIndex(0);
        if (obj instanceof Collection) {
            final Collection<?> coll = (Collection<?>)obj;
            final Measure[] measures = new Measure[coll.size()];
            int idx = 0;
            for (final Object o : coll) {
                if (!(o instanceof Measure)) {
                    throw new IllegalArgumentException(obj.toString());
                }
                measures[idx++] = (Measure)o;
            }
            this.formatMeasuresInternal(toAppendTo, fpos, measures);
        }
        else if (obj instanceof Measure[]) {
            this.formatMeasuresInternal(toAppendTo, fpos, (Measure[])obj);
        }
        else {
            if (!(obj instanceof Measure)) {
                throw new IllegalArgumentException(obj.toString());
            }
            final FormattedNumber result = this.formatMeasure((Measure)obj);
            result.populateFieldPosition(fpos);
            result.appendTo(toAppendTo);
        }
        if (prevLength > 0 && fpos.getEndIndex() != 0) {
            fpos.setBeginIndex(fpos.getBeginIndex() + prevLength);
            fpos.setEndIndex(fpos.getEndIndex() + prevLength);
        }
        return toAppendTo;
    }
    
    @Override
    public Measure parseObject(final String source, final ParsePosition pos) {
        throw new UnsupportedOperationException();
    }
    
    public final String formatMeasures(final Measure... measures) {
        return this.formatMeasures(new StringBuilder(), DontCareFieldPosition.INSTANCE, measures).toString();
    }
    
    public StringBuilder formatMeasurePerUnit(final Measure measure, final MeasureUnit perUnit, final StringBuilder appendTo, final FieldPosition pos) {
        final FormattedNumber result = this.getUnitFormatterFromCache(1, measure.getUnit(), perUnit).format(measure.getNumber());
        DecimalFormat.fieldPositionHelper(result, pos, appendTo.length());
        result.appendTo(appendTo);
        return appendTo;
    }
    
    public StringBuilder formatMeasures(final StringBuilder appendTo, final FieldPosition fpos, final Measure... measures) {
        final int prevLength = appendTo.length();
        this.formatMeasuresInternal(appendTo, fpos, measures);
        if (prevLength > 0 && fpos.getEndIndex() > 0) {
            fpos.setBeginIndex(fpos.getBeginIndex() + prevLength);
            fpos.setEndIndex(fpos.getEndIndex() + prevLength);
        }
        return appendTo;
    }
    
    private void formatMeasuresInternal(final Appendable appendTo, final FieldPosition fieldPosition, final Measure... measures) {
        if (measures.length == 0) {
            return;
        }
        if (measures.length == 1) {
            final FormattedNumber result = this.formatMeasure(measures[0]);
            result.populateFieldPosition(fieldPosition);
            result.appendTo(appendTo);
            return;
        }
        if (this.formatWidth == FormatWidth.NUMERIC) {
            final Number[] hms = toHMS(measures);
            if (hms != null) {
                this.formatNumeric(hms, appendTo);
                return;
            }
        }
        final ListFormatter listFormatter = ListFormatter.getInstance(this.getLocale(), this.formatWidth.getListFormatterStyle());
        if (fieldPosition != DontCareFieldPosition.INSTANCE) {
            this.formatMeasuresSlowTrack(listFormatter, appendTo, fieldPosition, measures);
            return;
        }
        final String[] results = new String[measures.length];
        for (int i = 0; i < measures.length; ++i) {
            if (i == measures.length - 1) {
                results[i] = this.formatMeasure(measures[i]).toString();
            }
            else {
                results[i] = this.formatMeasureInteger(measures[i]).toString();
            }
        }
        final ListFormatter.FormattedListBuilder builder = listFormatter.format(Arrays.asList(results), -1);
        builder.appendTo(appendTo);
    }
    
    public String getUnitDisplayName(final MeasureUnit unit) {
        return LongNameHandler.getUnitDisplayName(this.getLocale(), unit, this.formatWidth.unitWidth);
    }
    
    @Override
    public final boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof MeasureFormat)) {
            return false;
        }
        final MeasureFormat rhs = (MeasureFormat)other;
        return this.getWidth() == rhs.getWidth() && this.getLocale().equals(rhs.getLocale()) && this.getNumberFormatInternal().equals(rhs.getNumberFormatInternal());
    }
    
    @Override
    public final int hashCode() {
        return (this.getLocale().hashCode() * 31 + this.getNumberFormatInternal().hashCode()) * 31 + this.getWidth().hashCode();
    }
    
    public FormatWidth getWidth() {
        return this.formatWidth;
    }
    
    public final ULocale getLocale() {
        return this.getLocale(ULocale.VALID_LOCALE);
    }
    
    public NumberFormat getNumberFormat() {
        return (NumberFormat)this.numberFormat.clone();
    }
    
    NumberFormat getNumberFormatInternal() {
        return this.numberFormat;
    }
    
    public static MeasureFormat getCurrencyFormat(final ULocale locale) {
        return new CurrencyFormat(locale);
    }
    
    public static MeasureFormat getCurrencyFormat(final Locale locale) {
        return getCurrencyFormat(ULocale.forLocale(locale));
    }
    
    public static MeasureFormat getCurrencyFormat() {
        return getCurrencyFormat(ULocale.getDefault(ULocale.Category.FORMAT));
    }
    
    MeasureFormat withLocale(final ULocale locale) {
        return getInstance(locale, this.getWidth());
    }
    
    MeasureFormat withNumberFormat(final NumberFormat format) {
        return new MeasureFormat(this.getLocale(), this.formatWidth, format, this.rules, this.numericFormatters);
    }
    
    MeasureFormat(final ULocale locale, final FormatWidth formatWidth) {
        this(locale, formatWidth, null, null, null);
    }
    
    private MeasureFormat(final ULocale locale, final FormatWidth formatWidth, NumberFormat numberFormat, PluralRules rules, NumericFormatters formatters) {
        this.formatter1 = null;
        this.formatter2 = null;
        this.formatter3 = null;
        this.setLocale(locale, locale);
        this.formatWidth = formatWidth;
        if (rules == null) {
            rules = PluralRules.forLocale(locale);
        }
        this.rules = rules;
        if (numberFormat == null) {
            numberFormat = NumberFormat.getInstance(locale);
        }
        else {
            numberFormat = (NumberFormat)numberFormat.clone();
        }
        this.numberFormat = numberFormat;
        if (formatters == null && formatWidth == FormatWidth.NUMERIC) {
            formatters = MeasureFormat.localeToNumericDurationFormatters.get(locale);
            if (formatters == null) {
                formatters = loadNumericFormatters(locale);
                MeasureFormat.localeToNumericDurationFormatters.put(locale, formatters);
            }
        }
        this.numericFormatters = formatters;
        if (!(numberFormat instanceof DecimalFormat)) {
            throw new IllegalArgumentException();
        }
        this.numberFormatter = ((DecimalFormat)numberFormat).toNumberFormatter().unitWidth(formatWidth.unitWidth);
    }
    
    MeasureFormat(final ULocale locale, final FormatWidth formatWidth, final NumberFormat numberFormat, final PluralRules rules) {
        this(locale, formatWidth, numberFormat, rules, null);
        if (formatWidth == FormatWidth.NUMERIC) {
            throw new IllegalArgumentException("The format width 'numeric' is not allowed by this constructor");
        }
    }
    
    private static NumericFormatters loadNumericFormatters(final ULocale locale) {
        final ICUResourceBundle r = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b/unit", locale);
        return new NumericFormatters(loadNumericDurationFormat(r, "hm"), loadNumericDurationFormat(r, "ms"), loadNumericDurationFormat(r, "hms"));
    }
    
    private synchronized LocalizedNumberFormatter getUnitFormatterFromCache(final int type, final MeasureUnit unit, final MeasureUnit perUnit) {
        if (this.formatter1 != null) {
            if (this.formatter1.type == type && this.formatter1.unit == unit && this.formatter1.perUnit == perUnit) {
                return this.formatter1.formatter;
            }
            if (this.formatter2 != null) {
                if (this.formatter2.type == type && this.formatter2.unit == unit && this.formatter2.perUnit == perUnit) {
                    return this.formatter2.formatter;
                }
                if (this.formatter3 != null && this.formatter3.type == type && this.formatter3.unit == unit && this.formatter3.perUnit == perUnit) {
                    return this.formatter3.formatter;
                }
            }
        }
        LocalizedNumberFormatter formatter;
        if (type == 1) {
            formatter = this.getNumberFormatter().unit(unit).perUnit(perUnit).unitWidth(this.formatWidth.unitWidth);
        }
        else if (type == 2) {
            formatter = NumberFormatter.withLocale(this.getLocale()).unit(unit).perUnit(perUnit).unitWidth(this.formatWidth.currencyWidth);
        }
        else {
            assert type == 3;
            formatter = this.getNumberFormatter().unit(unit).perUnit(perUnit).unitWidth(this.formatWidth.unitWidth).rounding(Precision.integer().withMode(RoundingMode.DOWN));
        }
        this.formatter3 = this.formatter2;
        this.formatter2 = this.formatter1;
        this.formatter1 = new NumberFormatterCacheEntry();
        this.formatter1.type = type;
        this.formatter1.unit = unit;
        this.formatter1.perUnit = perUnit;
        return this.formatter1.formatter = formatter;
    }
    
    synchronized void clearCache() {
        this.formatter1 = null;
        this.formatter2 = null;
        this.formatter3 = null;
    }
    
    LocalizedNumberFormatter getNumberFormatter() {
        return this.numberFormatter;
    }
    
    private FormattedNumber formatMeasure(final Measure measure) {
        final MeasureUnit unit = measure.getUnit();
        if (unit instanceof Currency) {
            return this.getUnitFormatterFromCache(2, unit, null).format(measure.getNumber());
        }
        return this.getUnitFormatterFromCache(1, unit, null).format(measure.getNumber());
    }
    
    private FormattedNumber formatMeasureInteger(final Measure measure) {
        return this.getUnitFormatterFromCache(3, measure.getUnit(), null).format(measure.getNumber());
    }
    
    private void formatMeasuresSlowTrack(final ListFormatter listFormatter, final Appendable appendTo, final FieldPosition fieldPosition, final Measure... measures) {
        final String[] results = new String[measures.length];
        final FieldPosition fpos = new FieldPosition(fieldPosition.getFieldAttribute(), fieldPosition.getField());
        int fieldPositionFoundIndex = -1;
        for (int i = 0; i < measures.length; ++i) {
            FormattedNumber result;
            if (i == measures.length - 1) {
                result = this.formatMeasure(measures[i]);
            }
            else {
                result = this.formatMeasureInteger(measures[i]);
            }
            if (fieldPositionFoundIndex == -1) {
                result.populateFieldPosition(fpos);
                if (fpos.getEndIndex() != 0) {
                    fieldPositionFoundIndex = i;
                }
            }
            results[i] = result.toString();
        }
        final ListFormatter.FormattedListBuilder builder = listFormatter.format(Arrays.asList(results), fieldPositionFoundIndex);
        if (builder.getOffset() != -1) {
            fieldPosition.setBeginIndex(fpos.getBeginIndex() + builder.getOffset());
            fieldPosition.setEndIndex(fpos.getEndIndex() + builder.getOffset());
        }
        builder.appendTo(appendTo);
    }
    
    private static DateFormat loadNumericDurationFormat(ICUResourceBundle r, final String type) {
        r = r.getWithFallback(String.format("durationUnits/%s", type));
        final DateFormat result = new SimpleDateFormat(r.getString().replace("h", "H"));
        result.setTimeZone(TimeZone.GMT_ZONE);
        return result;
    }
    
    private static Number[] toHMS(final Measure[] measures) {
        final Number[] result = new Number[3];
        int lastIdx = -1;
        for (final Measure m : measures) {
            if (m.getNumber().doubleValue() < 0.0) {
                return null;
            }
            final Integer idxObj = MeasureFormat.hmsTo012.get(m.getUnit());
            if (idxObj == null) {
                return null;
            }
            final int idx = idxObj;
            if (idx <= lastIdx) {
                return null;
            }
            lastIdx = idx;
            result[idx] = m.getNumber();
        }
        return result;
    }
    
    private void formatNumeric(final Number[] hms, final Appendable appendable) {
        int startIndex = -1;
        int endIndex = -1;
        for (int i = 0; i < hms.length; ++i) {
            if (hms[i] != null) {
                endIndex = i;
                if (startIndex == -1) {
                    startIndex = endIndex;
                }
            }
            else {
                hms[i] = 0;
            }
        }
        final long millis = (long)(((Math.floor(hms[0].doubleValue()) * 60.0 + Math.floor(hms[1].doubleValue())) * 60.0 + Math.floor(hms[2].doubleValue())) * 1000.0);
        final Date d = new Date(millis);
        if (startIndex == 0 && endIndex == 2) {
            this.formatNumeric(d, this.numericFormatters.getHourMinuteSecond(), DateFormat.Field.SECOND, hms[endIndex], appendable);
        }
        else if (startIndex == 1 && endIndex == 2) {
            this.formatNumeric(d, this.numericFormatters.getMinuteSecond(), DateFormat.Field.SECOND, hms[endIndex], appendable);
        }
        else {
            if (startIndex != 0 || endIndex != 1) {
                throw new IllegalStateException();
            }
            this.formatNumeric(d, this.numericFormatters.getHourMinute(), DateFormat.Field.MINUTE, hms[endIndex], appendable);
        }
    }
    
    private void formatNumeric(final Date duration, final DateFormat formatter, final DateFormat.Field smallestField, final Number smallestAmount, final Appendable appendTo) {
        final FieldPosition intFieldPosition = new FieldPosition(0);
        final FormattedNumber result = this.getNumberFormatter().format(smallestAmount);
        result.populateFieldPosition(intFieldPosition);
        final String smallestAmountFormatted = result.toString();
        if (intFieldPosition.getBeginIndex() == 0 && intFieldPosition.getEndIndex() == 0) {
            throw new IllegalStateException();
        }
        final FieldPosition smallestFieldPosition = new FieldPosition(smallestField);
        final String draft;
        synchronized (formatter) {
            draft = formatter.format(duration, new StringBuffer(), smallestFieldPosition).toString();
        }
        try {
            if (smallestFieldPosition.getBeginIndex() != 0 || smallestFieldPosition.getEndIndex() != 0) {
                appendTo.append(draft, 0, smallestFieldPosition.getBeginIndex());
                appendTo.append(smallestAmountFormatted, 0, intFieldPosition.getBeginIndex());
                appendTo.append(draft, smallestFieldPosition.getBeginIndex(), smallestFieldPosition.getEndIndex());
                appendTo.append(smallestAmountFormatted, intFieldPosition.getEndIndex(), smallestAmountFormatted.length());
                appendTo.append(draft, smallestFieldPosition.getEndIndex(), draft.length());
            }
            else {
                appendTo.append(draft);
            }
        }
        catch (IOException e) {
            throw new ICUUncheckedIOException(e);
        }
    }
    
    Object toTimeUnitProxy() {
        return new MeasureProxy(this.getLocale(), this.formatWidth, this.getNumberFormatInternal(), 1);
    }
    
    Object toCurrencyProxy() {
        return new MeasureProxy(this.getLocale(), this.formatWidth, this.getNumberFormatInternal(), 2);
    }
    
    private Object writeReplace() throws ObjectStreamException {
        return new MeasureProxy(this.getLocale(), this.formatWidth, this.getNumberFormatInternal(), 0);
    }
    
    private static FormatWidth fromFormatWidthOrdinal(final int ordinal) {
        final FormatWidth[] values = FormatWidth.values();
        if (ordinal < 0 || ordinal >= values.length) {
            return FormatWidth.SHORT;
        }
        return values[ordinal];
    }
    
    @Deprecated
    public static String getRangeFormat(final ULocale forLocale, final FormatWidth width) {
        if (forLocale.getLanguage().equals("fr")) {
            return getRangeFormat(ULocale.ROOT, width);
        }
        String result = MeasureFormat.localeIdToRangeFormat.get(forLocale);
        if (result == null) {
            final ICUResourceBundle rb = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b", forLocale);
            final ULocale realLocale = rb.getULocale();
            if (!forLocale.equals(realLocale)) {
                result = MeasureFormat.localeIdToRangeFormat.get(forLocale);
                if (result != null) {
                    MeasureFormat.localeIdToRangeFormat.put(forLocale, result);
                    return result;
                }
            }
            final NumberingSystem ns = NumberingSystem.getInstance(forLocale);
            String resultString = null;
            try {
                resultString = rb.getStringWithFallback("NumberElements/" + ns.getName() + "/miscPatterns/range");
            }
            catch (MissingResourceException ex) {
                resultString = rb.getStringWithFallback("NumberElements/latn/patterns/range");
            }
            result = SimpleFormatterImpl.compileToStringMinMaxArguments(resultString, new StringBuilder(), 2, 2);
            MeasureFormat.localeIdToRangeFormat.put(forLocale, result);
            if (!forLocale.equals(realLocale)) {
                MeasureFormat.localeIdToRangeFormat.put(realLocale, result);
            }
        }
        return result;
    }
    
    static {
        localeToNumericDurationFormatters = new SimpleCache<ULocale, NumericFormatters>();
        (hmsTo012 = new HashMap<MeasureUnit, Integer>()).put(MeasureUnit.HOUR, 0);
        MeasureFormat.hmsTo012.put(MeasureUnit.MINUTE, 1);
        MeasureFormat.hmsTo012.put(MeasureUnit.SECOND, 2);
        localeIdToRangeFormat = new ConcurrentHashMap<ULocale, String>();
    }
    
    public enum FormatWidth
    {
        WIDE(ListFormatter.Style.DURATION, NumberFormatter.UnitWidth.FULL_NAME, NumberFormatter.UnitWidth.FULL_NAME), 
        SHORT(ListFormatter.Style.DURATION_SHORT, NumberFormatter.UnitWidth.SHORT, NumberFormatter.UnitWidth.ISO_CODE), 
        NARROW(ListFormatter.Style.DURATION_NARROW, NumberFormatter.UnitWidth.NARROW, NumberFormatter.UnitWidth.SHORT), 
        NUMERIC(ListFormatter.Style.DURATION_NARROW, NumberFormatter.UnitWidth.NARROW, NumberFormatter.UnitWidth.SHORT), 
        @Deprecated
        DEFAULT_CURRENCY(ListFormatter.Style.DURATION, NumberFormatter.UnitWidth.FULL_NAME, NumberFormatter.UnitWidth.SHORT);
        
        private final ListFormatter.Style listFormatterStyle;
        final NumberFormatter.UnitWidth unitWidth;
        final NumberFormatter.UnitWidth currencyWidth;
        
        private FormatWidth(final ListFormatter.Style style, final NumberFormatter.UnitWidth unitWidth, final NumberFormatter.UnitWidth currencyWidth) {
            this.listFormatterStyle = style;
            this.unitWidth = unitWidth;
            this.currencyWidth = currencyWidth;
        }
        
        ListFormatter.Style getListFormatterStyle() {
            return this.listFormatterStyle;
        }
    }
    
    static class NumericFormatters
    {
        private DateFormat hourMinute;
        private DateFormat minuteSecond;
        private DateFormat hourMinuteSecond;
        
        public NumericFormatters(final DateFormat hourMinute, final DateFormat minuteSecond, final DateFormat hourMinuteSecond) {
            this.hourMinute = hourMinute;
            this.minuteSecond = minuteSecond;
            this.hourMinuteSecond = hourMinuteSecond;
        }
        
        public DateFormat getHourMinute() {
            return this.hourMinute;
        }
        
        public DateFormat getMinuteSecond() {
            return this.minuteSecond;
        }
        
        public DateFormat getHourMinuteSecond() {
            return this.hourMinuteSecond;
        }
    }
    
    static class NumberFormatterCacheEntry
    {
        int type;
        MeasureUnit unit;
        MeasureUnit perUnit;
        LocalizedNumberFormatter formatter;
    }
    
    static class MeasureProxy implements Externalizable
    {
        private static final long serialVersionUID = -6033308329886716770L;
        private ULocale locale;
        private FormatWidth formatWidth;
        private NumberFormat numberFormat;
        private int subClass;
        private HashMap<Object, Object> keyValues;
        
        public MeasureProxy(final ULocale locale, final FormatWidth width, final NumberFormat numberFormat, final int subClass) {
            this.locale = locale;
            this.formatWidth = width;
            this.numberFormat = numberFormat;
            this.subClass = subClass;
            this.keyValues = new HashMap<Object, Object>();
        }
        
        public MeasureProxy() {
        }
        
        @Override
        public void writeExternal(final ObjectOutput out) throws IOException {
            out.writeByte(0);
            out.writeUTF(this.locale.toLanguageTag());
            out.writeByte(this.formatWidth.ordinal());
            out.writeObject(this.numberFormat);
            out.writeByte(this.subClass);
            out.writeObject(this.keyValues);
        }
        
        @Override
        public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
            in.readByte();
            this.locale = ULocale.forLanguageTag(in.readUTF());
            this.formatWidth = fromFormatWidthOrdinal(in.readByte() & 0xFF);
            this.numberFormat = (NumberFormat)in.readObject();
            if (this.numberFormat == null) {
                throw new InvalidObjectException("Missing number format.");
            }
            this.subClass = (in.readByte() & 0xFF);
            this.keyValues = (HashMap<Object, Object>)in.readObject();
            if (this.keyValues == null) {
                throw new InvalidObjectException("Missing optional values map.");
            }
        }
        
        private TimeUnitFormat createTimeUnitFormat() throws InvalidObjectException {
            int style;
            if (this.formatWidth == FormatWidth.WIDE) {
                style = 0;
            }
            else {
                if (this.formatWidth != FormatWidth.SHORT) {
                    throw new InvalidObjectException("Bad width: " + this.formatWidth);
                }
                style = 1;
            }
            final TimeUnitFormat result = new TimeUnitFormat(this.locale, style);
            result.setNumberFormat(this.numberFormat);
            return result;
        }
        
        private Object readResolve() throws ObjectStreamException {
            switch (this.subClass) {
                case 0: {
                    return MeasureFormat.getInstance(this.locale, this.formatWidth, this.numberFormat);
                }
                case 1: {
                    return this.createTimeUnitFormat();
                }
                case 2: {
                    return MeasureFormat.getCurrencyFormat(this.locale);
                }
                default: {
                    throw new InvalidObjectException("Unknown subclass: " + this.subClass);
                }
            }
        }
    }
}
