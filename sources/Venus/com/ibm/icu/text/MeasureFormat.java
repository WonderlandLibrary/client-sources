/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.DontCareFieldPosition;
import com.ibm.icu.impl.FormattedStringBuilder;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.SimpleCache;
import com.ibm.icu.impl.SimpleFormatterImpl;
import com.ibm.icu.impl.number.LongNameHandler;
import com.ibm.icu.impl.number.RoundingUtils;
import com.ibm.icu.number.FormattedNumber;
import com.ibm.icu.number.IntegerWidth;
import com.ibm.icu.number.LocalizedNumberFormatter;
import com.ibm.icu.number.NumberFormatter;
import com.ibm.icu.number.Precision;
import com.ibm.icu.text.CurrencyFormat;
import com.ibm.icu.text.DecimalFormat;
import com.ibm.icu.text.ListFormatter;
import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.text.NumberingSystem;
import com.ibm.icu.text.PluralRules;
import com.ibm.icu.text.TimeUnitFormat;
import com.ibm.icu.text.UFormat;
import com.ibm.icu.util.Currency;
import com.ibm.icu.util.ICUUncheckedIOException;
import com.ibm.icu.util.Measure;
import com.ibm.icu.util.MeasureUnit;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import java.io.Externalizable;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;
import java.math.RoundingMode;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.concurrent.ConcurrentHashMap;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class MeasureFormat
extends UFormat {
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
    private transient NumberFormatterCacheEntry formatter1 = null;
    private transient NumberFormatterCacheEntry formatter2 = null;
    private transient NumberFormatterCacheEntry formatter3 = null;
    private static final Map<ULocale, String> localeIdToRangeFormat;
    static final boolean $assertionsDisabled;

    public static MeasureFormat getInstance(ULocale uLocale, FormatWidth formatWidth) {
        return MeasureFormat.getInstance(uLocale, formatWidth, NumberFormat.getInstance(uLocale));
    }

    public static MeasureFormat getInstance(Locale locale, FormatWidth formatWidth) {
        return MeasureFormat.getInstance(ULocale.forLocale(locale), formatWidth);
    }

    public static MeasureFormat getInstance(ULocale uLocale, FormatWidth formatWidth, NumberFormat numberFormat) {
        return new MeasureFormat(uLocale, formatWidth, numberFormat, null, null);
    }

    public static MeasureFormat getInstance(Locale locale, FormatWidth formatWidth, NumberFormat numberFormat) {
        return MeasureFormat.getInstance(ULocale.forLocale(locale), formatWidth, numberFormat);
    }

    @Override
    public StringBuffer format(Object object, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        int n = stringBuffer.length();
        fieldPosition.setBeginIndex(0);
        fieldPosition.setEndIndex(0);
        if (object instanceof Collection) {
            Collection collection = (Collection)object;
            Measure[] measureArray = new Measure[collection.size()];
            int n2 = 0;
            for (Object e : collection) {
                if (!(e instanceof Measure)) {
                    throw new IllegalArgumentException(object.toString());
                }
                measureArray[n2++] = (Measure)e;
            }
            this.formatMeasuresInternal(stringBuffer, fieldPosition, measureArray);
        } else if (object instanceof Measure[]) {
            this.formatMeasuresInternal(stringBuffer, fieldPosition, (Measure[])object);
        } else if (object instanceof Measure) {
            FormattedNumber formattedNumber = this.formatMeasure((Measure)object);
            formattedNumber.nextFieldPosition(fieldPosition);
            formattedNumber.appendTo(stringBuffer);
        } else {
            throw new IllegalArgumentException(object.toString());
        }
        if (n > 0 && fieldPosition.getEndIndex() != 0) {
            fieldPosition.setBeginIndex(fieldPosition.getBeginIndex() + n);
            fieldPosition.setEndIndex(fieldPosition.getEndIndex() + n);
        }
        return stringBuffer;
    }

    @Override
    public Measure parseObject(String string, ParsePosition parsePosition) {
        throw new UnsupportedOperationException();
    }

    public final String formatMeasures(Measure ... measureArray) {
        return this.formatMeasures(new StringBuilder(), DontCareFieldPosition.INSTANCE, measureArray).toString();
    }

    public StringBuilder formatMeasurePerUnit(Measure measure, MeasureUnit measureUnit, StringBuilder stringBuilder, FieldPosition fieldPosition) {
        FormattedNumber formattedNumber = this.getUnitFormatterFromCache(1, measure.getUnit(), measureUnit).format(measure.getNumber());
        DecimalFormat.fieldPositionHelper(formattedNumber, fieldPosition, stringBuilder.length());
        formattedNumber.appendTo(stringBuilder);
        return stringBuilder;
    }

    public StringBuilder formatMeasures(StringBuilder stringBuilder, FieldPosition fieldPosition, Measure ... measureArray) {
        int n = stringBuilder.length();
        this.formatMeasuresInternal(stringBuilder, fieldPosition, measureArray);
        if (n > 0 && fieldPosition.getEndIndex() > 0) {
            fieldPosition.setBeginIndex(fieldPosition.getBeginIndex() + n);
            fieldPosition.setEndIndex(fieldPosition.getEndIndex() + n);
        }
        return stringBuilder;
    }

    private void formatMeasuresInternal(Appendable appendable, FieldPosition fieldPosition, Measure ... measureArray) {
        Object object;
        if (measureArray.length == 0) {
            return;
        }
        if (measureArray.length == 1) {
            FormattedNumber formattedNumber = this.formatMeasure(measureArray[0]);
            formattedNumber.nextFieldPosition(fieldPosition);
            formattedNumber.appendTo(appendable);
            return;
        }
        if (this.formatWidth == FormatWidth.NUMERIC && (object = MeasureFormat.toHMS(measureArray)) != null) {
            this.formatNumeric((Number[])object, appendable);
            return;
        }
        object = ListFormatter.getInstance(this.getLocale(), this.formatWidth.getListFormatterStyle());
        if (fieldPosition != DontCareFieldPosition.INSTANCE) {
            this.formatMeasuresSlowTrack((ListFormatter)object, appendable, fieldPosition, measureArray);
            return;
        }
        String[] stringArray = new String[measureArray.length];
        for (int i = 0; i < measureArray.length; ++i) {
            stringArray[i] = i == measureArray.length - 1 ? this.formatMeasure(measureArray[i]).toString() : this.formatMeasureInteger(measureArray[i]).toString();
        }
        ListFormatter.FormattedListBuilder formattedListBuilder = ((ListFormatter)object).format(Arrays.asList(stringArray), -1);
        formattedListBuilder.appendTo(appendable);
    }

    public String getUnitDisplayName(MeasureUnit measureUnit) {
        return LongNameHandler.getUnitDisplayName(this.getLocale(), measureUnit, this.formatWidth.unitWidth);
    }

    public final boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof MeasureFormat)) {
            return true;
        }
        MeasureFormat measureFormat = (MeasureFormat)object;
        return this.getWidth() == measureFormat.getWidth() && this.getLocale().equals(measureFormat.getLocale()) && this.getNumberFormatInternal().equals(measureFormat.getNumberFormatInternal());
    }

    public final int hashCode() {
        return (this.getLocale().hashCode() * 31 + this.getNumberFormatInternal().hashCode()) * 31 + this.getWidth().hashCode();
    }

    public FormatWidth getWidth() {
        if (this.formatWidth == FormatWidth.DEFAULT_CURRENCY) {
            return FormatWidth.WIDE;
        }
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

    public static MeasureFormat getCurrencyFormat(ULocale uLocale) {
        return new CurrencyFormat(uLocale);
    }

    public static MeasureFormat getCurrencyFormat(Locale locale) {
        return MeasureFormat.getCurrencyFormat(ULocale.forLocale(locale));
    }

    public static MeasureFormat getCurrencyFormat() {
        return MeasureFormat.getCurrencyFormat(ULocale.getDefault(ULocale.Category.FORMAT));
    }

    MeasureFormat withLocale(ULocale uLocale) {
        return MeasureFormat.getInstance(uLocale, this.getWidth());
    }

    MeasureFormat withNumberFormat(NumberFormat numberFormat) {
        return new MeasureFormat(this.getLocale(), this.formatWidth, numberFormat, this.rules, this.numericFormatters);
    }

    MeasureFormat(ULocale uLocale, FormatWidth formatWidth) {
        this(uLocale, formatWidth, null, null, null);
    }

    private MeasureFormat(ULocale uLocale, FormatWidth formatWidth, NumberFormat numberFormat, PluralRules pluralRules, NumericFormatters numericFormatters) {
        this.setLocale(uLocale, uLocale);
        this.formatWidth = formatWidth;
        if (pluralRules == null) {
            pluralRules = PluralRules.forLocale(uLocale);
        }
        this.rules = pluralRules;
        numberFormat = numberFormat == null ? NumberFormat.getInstance(uLocale) : (NumberFormat)numberFormat.clone();
        this.numberFormat = numberFormat;
        if (numericFormatters == null && formatWidth == FormatWidth.NUMERIC && (numericFormatters = localeToNumericDurationFormatters.get(uLocale)) == null) {
            numericFormatters = MeasureFormat.loadNumericFormatters(uLocale);
            localeToNumericDurationFormatters.put(uLocale, numericFormatters);
        }
        this.numericFormatters = numericFormatters;
        if (!(numberFormat instanceof DecimalFormat)) {
            throw new IllegalArgumentException();
        }
        this.numberFormatter = (LocalizedNumberFormatter)((DecimalFormat)numberFormat).toNumberFormatter().unitWidth(formatWidth.unitWidth);
    }

    MeasureFormat(ULocale uLocale, FormatWidth formatWidth, NumberFormat numberFormat, PluralRules pluralRules) {
        this(uLocale, formatWidth, numberFormat, pluralRules, null);
        if (formatWidth == FormatWidth.NUMERIC) {
            throw new IllegalArgumentException("The format width 'numeric' is not allowed by this constructor");
        }
    }

    private static NumericFormatters loadNumericFormatters(ULocale uLocale) {
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b/unit", uLocale);
        return new NumericFormatters(MeasureFormat.loadNumericDurationFormat(iCUResourceBundle, "hm"), MeasureFormat.loadNumericDurationFormat(iCUResourceBundle, "ms"), MeasureFormat.loadNumericDurationFormat(iCUResourceBundle, "hms"));
    }

    private synchronized LocalizedNumberFormatter getUnitFormatterFromCache(int n, MeasureUnit measureUnit, MeasureUnit measureUnit2) {
        LocalizedNumberFormatter localizedNumberFormatter;
        if (this.formatter1 != null) {
            if (this.formatter1.type == n && this.formatter1.unit == measureUnit && this.formatter1.perUnit == measureUnit2) {
                return this.formatter1.formatter;
            }
            if (this.formatter2 != null) {
                if (this.formatter2.type == n && this.formatter2.unit == measureUnit && this.formatter2.perUnit == measureUnit2) {
                    return this.formatter2.formatter;
                }
                if (this.formatter3 != null && this.formatter3.type == n && this.formatter3.unit == measureUnit && this.formatter3.perUnit == measureUnit2) {
                    return this.formatter3.formatter;
                }
            }
        }
        if (n == 1) {
            localizedNumberFormatter = (LocalizedNumberFormatter)((LocalizedNumberFormatter)((LocalizedNumberFormatter)this.getNumberFormatter().unit(measureUnit)).perUnit(measureUnit2)).unitWidth(this.formatWidth.unitWidth);
        } else if (n == 2) {
            localizedNumberFormatter = (LocalizedNumberFormatter)((LocalizedNumberFormatter)((LocalizedNumberFormatter)NumberFormatter.withLocale(this.getLocale()).unit(measureUnit)).perUnit(measureUnit2)).unitWidth(this.formatWidth.currencyWidth);
        } else {
            if (!$assertionsDisabled && n != 3) {
                throw new AssertionError();
            }
            localizedNumberFormatter = (LocalizedNumberFormatter)((LocalizedNumberFormatter)((LocalizedNumberFormatter)((LocalizedNumberFormatter)this.getNumberFormatter().unit(measureUnit)).perUnit(measureUnit2)).unitWidth(this.formatWidth.unitWidth)).precision(Precision.integer().withMode(RoundingUtils.mathContextUnlimited(RoundingMode.DOWN)));
        }
        this.formatter3 = this.formatter2;
        this.formatter2 = this.formatter1;
        this.formatter1 = new NumberFormatterCacheEntry();
        this.formatter1.type = n;
        this.formatter1.unit = measureUnit;
        this.formatter1.perUnit = measureUnit2;
        this.formatter1.formatter = localizedNumberFormatter;
        return localizedNumberFormatter;
    }

    synchronized void clearCache() {
        this.formatter1 = null;
        this.formatter2 = null;
        this.formatter3 = null;
    }

    LocalizedNumberFormatter getNumberFormatter() {
        return this.numberFormatter;
    }

    private FormattedNumber formatMeasure(Measure measure) {
        MeasureUnit measureUnit = measure.getUnit();
        if (measureUnit instanceof Currency) {
            return this.getUnitFormatterFromCache(2, measureUnit, null).format(measure.getNumber());
        }
        return this.getUnitFormatterFromCache(1, measureUnit, null).format(measure.getNumber());
    }

    private FormattedNumber formatMeasureInteger(Measure measure) {
        return this.getUnitFormatterFromCache(3, measure.getUnit(), null).format(measure.getNumber());
    }

    private void formatMeasuresSlowTrack(ListFormatter listFormatter, Appendable appendable, FieldPosition fieldPosition, Measure ... measureArray) {
        String[] stringArray = new String[measureArray.length];
        FieldPosition fieldPosition2 = new FieldPosition(fieldPosition.getFieldAttribute(), fieldPosition.getField());
        int n = -1;
        for (int i = 0; i < measureArray.length; ++i) {
            FormattedNumber formattedNumber = i == measureArray.length - 1 ? this.formatMeasure(measureArray[i]) : this.formatMeasureInteger(measureArray[i]);
            if (n == -1) {
                formattedNumber.nextFieldPosition(fieldPosition2);
                if (fieldPosition2.getEndIndex() != 0) {
                    n = i;
                }
            }
            stringArray[i] = formattedNumber.toString();
        }
        ListFormatter.FormattedListBuilder formattedListBuilder = listFormatter.format(Arrays.asList(stringArray), n);
        if (formattedListBuilder.getOffset() != -1) {
            fieldPosition.setBeginIndex(fieldPosition2.getBeginIndex() + formattedListBuilder.getOffset());
            fieldPosition.setEndIndex(fieldPosition2.getEndIndex() + formattedListBuilder.getOffset());
        }
        formattedListBuilder.appendTo(appendable);
    }

    private static String loadNumericDurationFormat(ICUResourceBundle iCUResourceBundle, String string) {
        iCUResourceBundle = iCUResourceBundle.getWithFallback(String.format("durationUnits/%s", string));
        return iCUResourceBundle.getString().replace("h", "H");
    }

    private static Number[] toHMS(Measure[] measureArray) {
        Number[] numberArray = new Number[3];
        int n = -1;
        for (Measure measure : measureArray) {
            if (measure.getNumber().doubleValue() < 0.0) {
                return null;
            }
            Integer n2 = hmsTo012.get(measure.getUnit());
            if (n2 == null) {
                return null;
            }
            int n3 = n2;
            if (n3 <= n) {
                return null;
            }
            n = n3;
            numberArray[n3] = measure.getNumber();
        }
        return numberArray;
    }

    private void formatNumeric(Number[] numberArray, Appendable appendable) {
        String string;
        if (numberArray[0] != null && numberArray[5] != null) {
            string = this.numericFormatters.getHourMinuteSecond();
            if (numberArray[5] == null) {
                numberArray[1] = 0;
            }
            numberArray[1] = Math.floor(numberArray[5].doubleValue());
            numberArray[0] = Math.floor(numberArray[0].doubleValue());
        } else if (numberArray[0] != null && numberArray[5] != null) {
            string = this.numericFormatters.getHourMinute();
            numberArray[0] = Math.floor(numberArray[0].doubleValue());
        } else if (numberArray[5] != null && numberArray[5] != null) {
            string = this.numericFormatters.getMinuteSecond();
            numberArray[1] = Math.floor(numberArray[5].doubleValue());
        } else {
            throw new IllegalStateException();
        }
        LocalizedNumberFormatter localizedNumberFormatter = (LocalizedNumberFormatter)this.numberFormatter.integerWidth(IntegerWidth.zeroFillTo(2));
        FormattedStringBuilder formattedStringBuilder = new FormattedStringBuilder();
        boolean bl = false;
        block11: for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            Number number = 0;
            switch (c) {
                case 'H': {
                    number = numberArray[0];
                    break;
                }
                case 'm': {
                    number = numberArray[5];
                    break;
                }
                case 's': {
                    number = numberArray[5];
                }
            }
            switch (c) {
                case 'H': 
                case 'm': 
                case 's': {
                    if (bl) {
                        formattedStringBuilder.appendChar16(c, null);
                        continue block11;
                    }
                    if (i + 1 < string.length() && string.charAt(i + 1) == c) {
                        formattedStringBuilder.append(localizedNumberFormatter.format(number), null);
                        ++i;
                        continue block11;
                    }
                    formattedStringBuilder.append(this.numberFormatter.format(number), null);
                    continue block11;
                }
                case '\'': {
                    if (i + 1 < string.length() && string.charAt(i + 1) == c) {
                        formattedStringBuilder.appendChar16(c, null);
                        ++i;
                        continue block11;
                    }
                    bl = !bl;
                    continue block11;
                }
                default: {
                    formattedStringBuilder.appendChar16(c, null);
                }
            }
        }
        try {
            appendable.append(formattedStringBuilder);
        } catch (IOException iOException) {
            throw new ICUUncheckedIOException(iOException);
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

    private static FormatWidth fromFormatWidthOrdinal(int n) {
        FormatWidth[] formatWidthArray = FormatWidth.values();
        if (n < 0 || n >= formatWidthArray.length) {
            return FormatWidth.SHORT;
        }
        return formatWidthArray[n];
    }

    @Deprecated
    public static String getRangeFormat(ULocale uLocale, FormatWidth formatWidth) {
        if (uLocale.getLanguage().equals("fr")) {
            return MeasureFormat.getRangeFormat(ULocale.ROOT, formatWidth);
        }
        String string = localeIdToRangeFormat.get(uLocale);
        if (string == null) {
            ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", uLocale);
            ULocale uLocale2 = iCUResourceBundle.getULocale();
            if (!uLocale.equals(uLocale2) && (string = localeIdToRangeFormat.get(uLocale)) != null) {
                localeIdToRangeFormat.put(uLocale, string);
                return string;
            }
            NumberingSystem numberingSystem = NumberingSystem.getInstance(uLocale);
            String string2 = null;
            try {
                string2 = iCUResourceBundle.getStringWithFallback("NumberElements/" + numberingSystem.getName() + "/miscPatterns/range");
            } catch (MissingResourceException missingResourceException) {
                string2 = iCUResourceBundle.getStringWithFallback("NumberElements/latn/patterns/range");
            }
            string = SimpleFormatterImpl.compileToStringMinMaxArguments(string2, new StringBuilder(), 2, 2);
            localeIdToRangeFormat.put(uLocale, string);
            if (!uLocale.equals(uLocale2)) {
                localeIdToRangeFormat.put(uLocale2, string);
            }
        }
        return string;
    }

    @Override
    public Object parseObject(String string, ParsePosition parsePosition) {
        return this.parseObject(string, parsePosition);
    }

    static FormatWidth access$000(int n) {
        return MeasureFormat.fromFormatWidthOrdinal(n);
    }

    static {
        $assertionsDisabled = !MeasureFormat.class.desiredAssertionStatus();
        localeToNumericDurationFormatters = new SimpleCache();
        hmsTo012 = new HashMap<MeasureUnit, Integer>();
        hmsTo012.put(MeasureUnit.HOUR, 0);
        hmsTo012.put(MeasureUnit.MINUTE, 1);
        hmsTo012.put(MeasureUnit.SECOND, 2);
        localeIdToRangeFormat = new ConcurrentHashMap<ULocale, String>();
    }

    static class MeasureProxy
    implements Externalizable {
        private static final long serialVersionUID = -6033308329886716770L;
        private ULocale locale;
        private FormatWidth formatWidth;
        private NumberFormat numberFormat;
        private int subClass;
        private HashMap<Object, Object> keyValues;

        public MeasureProxy(ULocale uLocale, FormatWidth formatWidth, NumberFormat numberFormat, int n) {
            this.locale = uLocale;
            this.formatWidth = formatWidth;
            this.numberFormat = numberFormat;
            this.subClass = n;
            this.keyValues = new HashMap();
        }

        public MeasureProxy() {
        }

        @Override
        public void writeExternal(ObjectOutput objectOutput) throws IOException {
            objectOutput.writeByte(0);
            objectOutput.writeUTF(this.locale.toLanguageTag());
            objectOutput.writeByte(this.formatWidth.ordinal());
            objectOutput.writeObject(this.numberFormat);
            objectOutput.writeByte(this.subClass);
            objectOutput.writeObject(this.keyValues);
        }

        @Override
        public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
            objectInput.readByte();
            this.locale = ULocale.forLanguageTag(objectInput.readUTF());
            this.formatWidth = MeasureFormat.access$000(objectInput.readByte() & 0xFF);
            this.numberFormat = (NumberFormat)objectInput.readObject();
            if (this.numberFormat == null) {
                throw new InvalidObjectException("Missing number format.");
            }
            this.subClass = objectInput.readByte() & 0xFF;
            this.keyValues = (HashMap)objectInput.readObject();
            if (this.keyValues == null) {
                throw new InvalidObjectException("Missing optional values map.");
            }
        }

        private TimeUnitFormat createTimeUnitFormat() throws InvalidObjectException {
            int n;
            if (this.formatWidth == FormatWidth.WIDE) {
                n = 0;
            } else if (this.formatWidth == FormatWidth.SHORT) {
                n = 1;
            } else {
                throw new InvalidObjectException("Bad width: " + (Object)((Object)this.formatWidth));
            }
            TimeUnitFormat timeUnitFormat = new TimeUnitFormat(this.locale, n);
            timeUnitFormat.setNumberFormat(this.numberFormat);
            return timeUnitFormat;
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
            }
            throw new InvalidObjectException("Unknown subclass: " + this.subClass);
        }
    }

    static class NumberFormatterCacheEntry {
        int type;
        MeasureUnit unit;
        MeasureUnit perUnit;
        LocalizedNumberFormatter formatter;

        NumberFormatterCacheEntry() {
        }
    }

    static class NumericFormatters {
        private String hourMinute;
        private String minuteSecond;
        private String hourMinuteSecond;

        public NumericFormatters(String string, String string2, String string3) {
            this.hourMinute = string;
            this.minuteSecond = string2;
            this.hourMinuteSecond = string3;
        }

        public String getHourMinute() {
            return this.hourMinute;
        }

        public String getMinuteSecond() {
            return this.minuteSecond;
        }

        public String getHourMinuteSecond() {
            return this.hourMinuteSecond;
        }
    }

    public static enum FormatWidth {
        WIDE(ListFormatter.Style.UNIT, NumberFormatter.UnitWidth.FULL_NAME, NumberFormatter.UnitWidth.FULL_NAME),
        SHORT(ListFormatter.Style.UNIT_SHORT, NumberFormatter.UnitWidth.SHORT, NumberFormatter.UnitWidth.ISO_CODE),
        NARROW(ListFormatter.Style.UNIT_NARROW, NumberFormatter.UnitWidth.NARROW, NumberFormatter.UnitWidth.SHORT),
        NUMERIC(ListFormatter.Style.UNIT_NARROW, NumberFormatter.UnitWidth.NARROW, NumberFormatter.UnitWidth.SHORT),
        DEFAULT_CURRENCY(ListFormatter.Style.UNIT, NumberFormatter.UnitWidth.FULL_NAME, NumberFormatter.UnitWidth.SHORT);

        private final ListFormatter.Style listFormatterStyle;
        final NumberFormatter.UnitWidth unitWidth;
        final NumberFormatter.UnitWidth currencyWidth;

        private FormatWidth(ListFormatter.Style style, NumberFormatter.UnitWidth unitWidth, NumberFormatter.UnitWidth unitWidth2) {
            this.listFormatterStyle = style;
            this.unitWidth = unitWidth;
            this.currencyWidth = unitWidth2;
        }

        ListFormatter.Style getListFormatterStyle() {
            return this.listFormatterStyle;
        }
    }
}

