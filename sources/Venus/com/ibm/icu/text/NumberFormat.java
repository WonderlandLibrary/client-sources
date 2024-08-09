/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.text.CurrencyPluralInfo;
import com.ibm.icu.text.DecimalFormat;
import com.ibm.icu.text.DecimalFormatSymbols;
import com.ibm.icu.text.DisplayContext;
import com.ibm.icu.text.NumberingSystem;
import com.ibm.icu.text.RuleBasedNumberFormat;
import com.ibm.icu.text.UFormat;
import com.ibm.icu.util.Currency;
import com.ibm.icu.util.CurrencyAmount;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Collections;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Set;

public abstract class NumberFormat
extends UFormat {
    public static final int NUMBERSTYLE = 0;
    public static final int CURRENCYSTYLE = 1;
    public static final int PERCENTSTYLE = 2;
    public static final int SCIENTIFICSTYLE = 3;
    public static final int INTEGERSTYLE = 4;
    public static final int ISOCURRENCYSTYLE = 5;
    public static final int PLURALCURRENCYSTYLE = 6;
    public static final int ACCOUNTINGCURRENCYSTYLE = 7;
    public static final int CASHCURRENCYSTYLE = 8;
    public static final int STANDARDCURRENCYSTYLE = 9;
    public static final int INTEGER_FIELD = 0;
    public static final int FRACTION_FIELD = 1;
    private static NumberFormatShim shim;
    private static final char[] doubleCurrencySign;
    private static final String doubleCurrencyStr;
    private boolean groupingUsed = true;
    private byte maxIntegerDigits = (byte)40;
    private byte minIntegerDigits = 1;
    private byte maxFractionDigits = (byte)3;
    private byte minFractionDigits = 0;
    private boolean parseIntegerOnly = false;
    private int maximumIntegerDigits = 40;
    private int minimumIntegerDigits = 1;
    private int maximumFractionDigits = 3;
    private int minimumFractionDigits = 0;
    private Currency currency;
    static final int currentSerialVersion = 2;
    private int serialVersionOnStream = 2;
    private static final long serialVersionUID = -2308460125733713944L;
    private boolean parseStrict;
    private DisplayContext capitalizationSetting = DisplayContext.CAPITALIZATION_NONE;
    static final boolean $assertionsDisabled;

    @Override
    public StringBuffer format(Object object, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        if (object instanceof Long) {
            return this.format((Long)object, stringBuffer, fieldPosition);
        }
        if (object instanceof BigInteger) {
            return this.format((BigInteger)object, stringBuffer, fieldPosition);
        }
        if (object instanceof BigDecimal) {
            return this.format((BigDecimal)object, stringBuffer, fieldPosition);
        }
        if (object instanceof com.ibm.icu.math.BigDecimal) {
            return this.format((com.ibm.icu.math.BigDecimal)object, stringBuffer, fieldPosition);
        }
        if (object instanceof CurrencyAmount) {
            return this.format((CurrencyAmount)object, stringBuffer, fieldPosition);
        }
        if (object instanceof Number) {
            return this.format(((Number)object).doubleValue(), stringBuffer, fieldPosition);
        }
        throw new IllegalArgumentException("Cannot format given Object as a Number");
    }

    @Override
    public final Object parseObject(String string, ParsePosition parsePosition) {
        return this.parse(string, parsePosition);
    }

    public final String format(double d) {
        return this.format(d, new StringBuffer(), new FieldPosition(0)).toString();
    }

    public final String format(long l) {
        StringBuffer stringBuffer = new StringBuffer(19);
        FieldPosition fieldPosition = new FieldPosition(0);
        this.format(l, stringBuffer, fieldPosition);
        return stringBuffer.toString();
    }

    public final String format(BigInteger bigInteger) {
        return this.format(bigInteger, new StringBuffer(), new FieldPosition(0)).toString();
    }

    public final String format(BigDecimal bigDecimal) {
        return this.format(bigDecimal, new StringBuffer(), new FieldPosition(0)).toString();
    }

    public final String format(com.ibm.icu.math.BigDecimal bigDecimal) {
        return this.format(bigDecimal, new StringBuffer(), new FieldPosition(0)).toString();
    }

    public final String format(CurrencyAmount currencyAmount) {
        return this.format(currencyAmount, new StringBuffer(), new FieldPosition(0)).toString();
    }

    public abstract StringBuffer format(double var1, StringBuffer var3, FieldPosition var4);

    public abstract StringBuffer format(long var1, StringBuffer var3, FieldPosition var4);

    public abstract StringBuffer format(BigInteger var1, StringBuffer var2, FieldPosition var3);

    public abstract StringBuffer format(BigDecimal var1, StringBuffer var2, FieldPosition var3);

    public abstract StringBuffer format(com.ibm.icu.math.BigDecimal var1, StringBuffer var2, FieldPosition var3);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public StringBuffer format(CurrencyAmount currencyAmount, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        NumberFormat numberFormat = this;
        synchronized (numberFormat) {
            Currency currency = this.getCurrency();
            Currency currency2 = currencyAmount.getCurrency();
            boolean bl = currency2.equals(currency);
            if (!bl) {
                this.setCurrency(currency2);
            }
            this.format(currencyAmount.getNumber(), stringBuffer, fieldPosition);
            if (!bl) {
                this.setCurrency(currency);
            }
        }
        return stringBuffer;
    }

    public abstract Number parse(String var1, ParsePosition var2);

    public Number parse(String string) throws ParseException {
        ParsePosition parsePosition = new ParsePosition(0);
        Number number = this.parse(string, parsePosition);
        if (parsePosition.getIndex() == 0) {
            throw new ParseException("Unparseable number: \"" + string + '\"', parsePosition.getErrorIndex());
        }
        return number;
    }

    public CurrencyAmount parseCurrency(CharSequence charSequence, ParsePosition parsePosition) {
        Number number = this.parse(charSequence.toString(), parsePosition);
        return number == null ? null : new CurrencyAmount(number, this.getEffectiveCurrency());
    }

    public boolean isParseIntegerOnly() {
        return this.parseIntegerOnly;
    }

    public void setParseIntegerOnly(boolean bl) {
        this.parseIntegerOnly = bl;
    }

    public void setParseStrict(boolean bl) {
        this.parseStrict = bl;
    }

    public boolean isParseStrict() {
        return this.parseStrict;
    }

    public void setContext(DisplayContext displayContext) {
        if (displayContext.type() == DisplayContext.Type.CAPITALIZATION) {
            this.capitalizationSetting = displayContext;
        }
    }

    public DisplayContext getContext(DisplayContext.Type type) {
        return type == DisplayContext.Type.CAPITALIZATION && this.capitalizationSetting != null ? this.capitalizationSetting : DisplayContext.CAPITALIZATION_NONE;
    }

    public static final NumberFormat getInstance() {
        return NumberFormat.getInstance(ULocale.getDefault(ULocale.Category.FORMAT), 0);
    }

    public static NumberFormat getInstance(Locale locale) {
        return NumberFormat.getInstance(ULocale.forLocale(locale), 0);
    }

    public static NumberFormat getInstance(ULocale uLocale) {
        return NumberFormat.getInstance(uLocale, 0);
    }

    public static final NumberFormat getInstance(int n) {
        return NumberFormat.getInstance(ULocale.getDefault(ULocale.Category.FORMAT), n);
    }

    public static NumberFormat getInstance(Locale locale, int n) {
        return NumberFormat.getInstance(ULocale.forLocale(locale), n);
    }

    public static final NumberFormat getNumberInstance() {
        return NumberFormat.getInstance(ULocale.getDefault(ULocale.Category.FORMAT), 0);
    }

    public static NumberFormat getNumberInstance(Locale locale) {
        return NumberFormat.getInstance(ULocale.forLocale(locale), 0);
    }

    public static NumberFormat getNumberInstance(ULocale uLocale) {
        return NumberFormat.getInstance(uLocale, 0);
    }

    public static final NumberFormat getIntegerInstance() {
        return NumberFormat.getInstance(ULocale.getDefault(ULocale.Category.FORMAT), 4);
    }

    public static NumberFormat getIntegerInstance(Locale locale) {
        return NumberFormat.getInstance(ULocale.forLocale(locale), 4);
    }

    public static NumberFormat getIntegerInstance(ULocale uLocale) {
        return NumberFormat.getInstance(uLocale, 4);
    }

    public static final NumberFormat getCurrencyInstance() {
        return NumberFormat.getInstance(ULocale.getDefault(ULocale.Category.FORMAT), 1);
    }

    public static NumberFormat getCurrencyInstance(Locale locale) {
        return NumberFormat.getInstance(ULocale.forLocale(locale), 1);
    }

    public static NumberFormat getCurrencyInstance(ULocale uLocale) {
        return NumberFormat.getInstance(uLocale, 1);
    }

    public static final NumberFormat getPercentInstance() {
        return NumberFormat.getInstance(ULocale.getDefault(ULocale.Category.FORMAT), 2);
    }

    public static NumberFormat getPercentInstance(Locale locale) {
        return NumberFormat.getInstance(ULocale.forLocale(locale), 2);
    }

    public static NumberFormat getPercentInstance(ULocale uLocale) {
        return NumberFormat.getInstance(uLocale, 2);
    }

    public static final NumberFormat getScientificInstance() {
        return NumberFormat.getInstance(ULocale.getDefault(ULocale.Category.FORMAT), 3);
    }

    public static NumberFormat getScientificInstance(Locale locale) {
        return NumberFormat.getInstance(ULocale.forLocale(locale), 3);
    }

    public static NumberFormat getScientificInstance(ULocale uLocale) {
        return NumberFormat.getInstance(uLocale, 3);
    }

    private static NumberFormatShim getShim() {
        if (shim == null) {
            try {
                Class<?> clazz = Class.forName("com.ibm.icu.text.NumberFormatServiceShim");
                shim = (NumberFormatShim)clazz.newInstance();
            } catch (MissingResourceException missingResourceException) {
                throw missingResourceException;
            } catch (Exception exception) {
                throw new RuntimeException(exception.getMessage());
            }
        }
        return shim;
    }

    public static Locale[] getAvailableLocales() {
        if (shim == null) {
            return ICUResourceBundle.getAvailableLocales();
        }
        return NumberFormat.getShim().getAvailableLocales();
    }

    public static ULocale[] getAvailableULocales() {
        if (shim == null) {
            return ICUResourceBundle.getAvailableULocales();
        }
        return NumberFormat.getShim().getAvailableULocales();
    }

    public static Object registerFactory(NumberFormatFactory numberFormatFactory) {
        if (numberFormatFactory == null) {
            throw new IllegalArgumentException("factory must not be null");
        }
        return NumberFormat.getShim().registerFactory(numberFormatFactory);
    }

    public static boolean unregister(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("registryKey must not be null");
        }
        if (shim == null) {
            return true;
        }
        return shim.unregister(object);
    }

    public int hashCode() {
        return this.maximumIntegerDigits * 37 + this.maxFractionDigits;
    }

    public boolean equals(Object object) {
        if (object == null) {
            return true;
        }
        if (this == object) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return true;
        }
        NumberFormat numberFormat = (NumberFormat)object;
        return this.maximumIntegerDigits == numberFormat.maximumIntegerDigits && this.minimumIntegerDigits == numberFormat.minimumIntegerDigits && this.maximumFractionDigits == numberFormat.maximumFractionDigits && this.minimumFractionDigits == numberFormat.minimumFractionDigits && this.groupingUsed == numberFormat.groupingUsed && this.parseIntegerOnly == numberFormat.parseIntegerOnly && this.parseStrict == numberFormat.parseStrict && this.capitalizationSetting == numberFormat.capitalizationSetting;
    }

    @Override
    public Object clone() {
        NumberFormat numberFormat = (NumberFormat)super.clone();
        return numberFormat;
    }

    public boolean isGroupingUsed() {
        return this.groupingUsed;
    }

    public void setGroupingUsed(boolean bl) {
        this.groupingUsed = bl;
    }

    public int getMaximumIntegerDigits() {
        return this.maximumIntegerDigits;
    }

    public void setMaximumIntegerDigits(int n) {
        this.maximumIntegerDigits = Math.max(0, n);
        if (this.minimumIntegerDigits > this.maximumIntegerDigits) {
            this.minimumIntegerDigits = this.maximumIntegerDigits;
        }
    }

    public int getMinimumIntegerDigits() {
        return this.minimumIntegerDigits;
    }

    public void setMinimumIntegerDigits(int n) {
        this.minimumIntegerDigits = Math.max(0, n);
        if (this.minimumIntegerDigits > this.maximumIntegerDigits) {
            this.maximumIntegerDigits = this.minimumIntegerDigits;
        }
    }

    public int getMaximumFractionDigits() {
        return this.maximumFractionDigits;
    }

    public void setMaximumFractionDigits(int n) {
        this.maximumFractionDigits = Math.max(0, n);
        if (this.maximumFractionDigits < this.minimumFractionDigits) {
            this.minimumFractionDigits = this.maximumFractionDigits;
        }
    }

    public int getMinimumFractionDigits() {
        return this.minimumFractionDigits;
    }

    public void setMinimumFractionDigits(int n) {
        this.minimumFractionDigits = Math.max(0, n);
        if (this.maximumFractionDigits < this.minimumFractionDigits) {
            this.maximumFractionDigits = this.minimumFractionDigits;
        }
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    @Deprecated
    protected Currency getEffectiveCurrency() {
        Currency currency = this.getCurrency();
        if (currency == null) {
            ULocale uLocale = this.getLocale(ULocale.VALID_LOCALE);
            if (uLocale == null) {
                uLocale = ULocale.getDefault(ULocale.Category.FORMAT);
            }
            currency = Currency.getInstance(uLocale);
        }
        return currency;
    }

    public int getRoundingMode() {
        throw new UnsupportedOperationException("getRoundingMode must be implemented by the subclass implementation.");
    }

    public void setRoundingMode(int n) {
        throw new UnsupportedOperationException("setRoundingMode must be implemented by the subclass implementation.");
    }

    public static NumberFormat getInstance(ULocale uLocale, int n) {
        if (n < 0 || n > 9) {
            throw new IllegalArgumentException("choice should be from NUMBERSTYLE to STANDARDCURRENCYSTYLE");
        }
        return NumberFormat.getShim().createInstance(uLocale, n);
    }

    static NumberFormat createInstance(ULocale uLocale, int n) {
        Object object;
        Object object2;
        Object object3;
        Object object4;
        String string = NumberFormat.getPattern(uLocale, n);
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(uLocale);
        if ((n == 1 || n == 5 || n == 7 || n == 8 || n == 9) && (object4 = decimalFormatSymbols.getCurrencyPattern()) != null) {
            string = object4;
        }
        if (n == 5) {
            string = string.replace("\u00a4", doubleCurrencyStr);
        }
        if ((object4 = NumberingSystem.getInstance(uLocale)) == null) {
            return null;
        }
        if (object4 != null && ((NumberingSystem)object4).isAlgorithmic()) {
            ULocale uLocale2;
            Object object5;
            Object object6;
            int n2 = 4;
            object3 = ((NumberingSystem)object4).getDescription();
            int n3 = ((String)object3).indexOf("/");
            int n4 = ((String)object3).lastIndexOf("/");
            if (n4 > n3) {
                object6 = ((String)object3).substring(0, n3);
                object2 = ((String)object3).substring(n3 + 1, n4);
                object5 = ((String)object3).substring(n4 + 1);
                uLocale2 = new ULocale((String)object6);
                if (((String)object2).equals("SpelloutRules")) {
                    n2 = 1;
                }
            } else {
                uLocale2 = uLocale;
                object5 = object3;
            }
            object6 = new RuleBasedNumberFormat(uLocale2, n2);
            ((RuleBasedNumberFormat)object6).setDefaultRuleSet((String)object5);
            object = object6;
        } else {
            object3 = new DecimalFormat(string, decimalFormatSymbols, n);
            if (n == 4) {
                ((DecimalFormat)object3).setMaximumFractionDigits(0);
                ((DecimalFormat)object3).setDecimalSeparatorAlwaysShown(true);
                ((DecimalFormat)object3).setParseIntegerOnly(false);
            }
            if (n == 8) {
                ((DecimalFormat)object3).setCurrencyUsage(Currency.CurrencyUsage.CASH);
            }
            if (n == 6) {
                ((DecimalFormat)object3).setCurrencyPluralInfo(CurrencyPluralInfo.getInstance(uLocale));
            }
            object = object3;
        }
        object3 = decimalFormatSymbols.getLocale(ULocale.VALID_LOCALE);
        object2 = decimalFormatSymbols.getLocale(ULocale.ACTUAL_LOCALE);
        ((UFormat)object).setLocale((ULocale)object3, (ULocale)object2);
        return object;
    }

    @Deprecated
    protected static String getPattern(Locale locale, int n) {
        return NumberFormat.getPattern(ULocale.forLocale(locale), n);
    }

    protected static String getPattern(ULocale uLocale, int n) {
        return NumberFormat.getPatternForStyle(uLocale, n);
    }

    @Deprecated
    public static String getPatternForStyle(ULocale uLocale, int n) {
        NumberingSystem numberingSystem = NumberingSystem.getInstance(uLocale);
        String string = numberingSystem.getName();
        return NumberFormat.getPatternForStyleAndNumberingSystem(uLocale, string, n);
    }

    @Deprecated
    public static String getPatternForStyleAndNumberingSystem(ULocale uLocale, String string, int n) {
        Object object;
        String string2 = null;
        switch (n) {
            case 0: 
            case 4: 
            case 6: {
                string2 = "decimalFormat";
                break;
            }
            case 1: {
                object = uLocale.getKeywordValue("cf");
                string2 = object != null && ((String)object).equals("account") ? "accountingFormat" : "currencyFormat";
                break;
            }
            case 5: 
            case 8: 
            case 9: {
                string2 = "currencyFormat";
                break;
            }
            case 2: {
                string2 = "percentFormat";
                break;
            }
            case 3: {
                string2 = "scientificFormat";
                break;
            }
            case 7: {
                string2 = "accountingFormat";
                break;
            }
            default: {
                if (!$assertionsDisabled) {
                    throw new AssertionError();
                }
                string2 = "decimalFormat";
            }
        }
        object = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", uLocale);
        String string3 = ((ICUResourceBundle)object).findStringWithFallback("NumberElements/" + string + "/patterns/" + string2);
        if (string3 == null) {
            string3 = ((ICUResourceBundle)object).getStringWithFallback("NumberElements/latn/patterns/" + string2);
        }
        return string3;
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        if (this.serialVersionOnStream < 1) {
            this.maximumIntegerDigits = this.maxIntegerDigits;
            this.minimumIntegerDigits = this.minIntegerDigits;
            this.maximumFractionDigits = this.maxFractionDigits;
            this.minimumFractionDigits = this.minFractionDigits;
        }
        if (this.serialVersionOnStream < 2) {
            this.capitalizationSetting = DisplayContext.CAPITALIZATION_NONE;
        }
        if (this.minimumIntegerDigits > this.maximumIntegerDigits || this.minimumFractionDigits > this.maximumFractionDigits || this.minimumIntegerDigits < 0 || this.minimumFractionDigits < 0) {
            throw new InvalidObjectException("Digit count range invalid");
        }
        this.serialVersionOnStream = 2;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        this.maxIntegerDigits = (byte)(this.maximumIntegerDigits > 127 ? 127 : (byte)this.maximumIntegerDigits);
        this.minIntegerDigits = (byte)(this.minimumIntegerDigits > 127 ? 127 : (byte)this.minimumIntegerDigits);
        this.maxFractionDigits = (byte)(this.maximumFractionDigits > 127 ? 127 : (byte)this.maximumFractionDigits);
        this.minFractionDigits = (byte)(this.minimumFractionDigits > 127 ? 127 : (byte)this.minimumFractionDigits);
        objectOutputStream.defaultWriteObject();
    }

    static {
        $assertionsDisabled = !NumberFormat.class.desiredAssertionStatus();
        doubleCurrencySign = new char[]{'\u00a4', '\u00a4'};
        doubleCurrencyStr = new String(doubleCurrencySign);
    }

    public static class Field
    extends Format.Field {
        static final long serialVersionUID = -4516273749929385842L;
        public static final Field SIGN = new Field("sign");
        public static final Field INTEGER = new Field("integer");
        public static final Field FRACTION = new Field("fraction");
        public static final Field EXPONENT = new Field("exponent");
        public static final Field EXPONENT_SIGN = new Field("exponent sign");
        public static final Field EXPONENT_SYMBOL = new Field("exponent symbol");
        public static final Field DECIMAL_SEPARATOR = new Field("decimal separator");
        public static final Field GROUPING_SEPARATOR = new Field("grouping separator");
        public static final Field PERCENT = new Field("percent");
        public static final Field PERMILLE = new Field("per mille");
        public static final Field CURRENCY = new Field("currency");
        public static final Field MEASURE_UNIT = new Field("measure unit");
        public static final Field COMPACT = new Field("compact");

        protected Field(String string) {
            super(string);
        }

        @Override
        protected Object readResolve() throws InvalidObjectException {
            if (this.getName().equals(INTEGER.getName())) {
                return INTEGER;
            }
            if (this.getName().equals(FRACTION.getName())) {
                return FRACTION;
            }
            if (this.getName().equals(EXPONENT.getName())) {
                return EXPONENT;
            }
            if (this.getName().equals(EXPONENT_SIGN.getName())) {
                return EXPONENT_SIGN;
            }
            if (this.getName().equals(EXPONENT_SYMBOL.getName())) {
                return EXPONENT_SYMBOL;
            }
            if (this.getName().equals(CURRENCY.getName())) {
                return CURRENCY;
            }
            if (this.getName().equals(DECIMAL_SEPARATOR.getName())) {
                return DECIMAL_SEPARATOR;
            }
            if (this.getName().equals(GROUPING_SEPARATOR.getName())) {
                return GROUPING_SEPARATOR;
            }
            if (this.getName().equals(PERCENT.getName())) {
                return PERCENT;
            }
            if (this.getName().equals(PERMILLE.getName())) {
                return PERMILLE;
            }
            if (this.getName().equals(SIGN.getName())) {
                return SIGN;
            }
            if (this.getName().equals(MEASURE_UNIT.getName())) {
                return MEASURE_UNIT;
            }
            if (this.getName().equals(COMPACT.getName())) {
                return COMPACT;
            }
            throw new InvalidObjectException("An invalid object.");
        }
    }

    static abstract class NumberFormatShim {
        NumberFormatShim() {
        }

        abstract Locale[] getAvailableLocales();

        abstract ULocale[] getAvailableULocales();

        abstract Object registerFactory(NumberFormatFactory var1);

        abstract boolean unregister(Object var1);

        abstract NumberFormat createInstance(ULocale var1, int var2);
    }

    public static abstract class SimpleNumberFormatFactory
    extends NumberFormatFactory {
        final Set<String> localeNames;
        final boolean visible;

        public SimpleNumberFormatFactory(Locale locale) {
            this(locale, true);
        }

        public SimpleNumberFormatFactory(Locale locale, boolean bl) {
            this.localeNames = Collections.singleton(ULocale.forLocale(locale).getBaseName());
            this.visible = bl;
        }

        public SimpleNumberFormatFactory(ULocale uLocale) {
            this(uLocale, true);
        }

        public SimpleNumberFormatFactory(ULocale uLocale, boolean bl) {
            this.localeNames = Collections.singleton(uLocale.getBaseName());
            this.visible = bl;
        }

        @Override
        public final boolean visible() {
            return this.visible;
        }

        @Override
        public final Set<String> getSupportedLocaleNames() {
            return this.localeNames;
        }
    }

    public static abstract class NumberFormatFactory {
        public static final int FORMAT_NUMBER = 0;
        public static final int FORMAT_CURRENCY = 1;
        public static final int FORMAT_PERCENT = 2;
        public static final int FORMAT_SCIENTIFIC = 3;
        public static final int FORMAT_INTEGER = 4;

        public boolean visible() {
            return false;
        }

        public abstract Set<String> getSupportedLocaleNames();

        public NumberFormat createFormat(ULocale uLocale, int n) {
            return this.createFormat(uLocale.toLocale(), n);
        }

        public NumberFormat createFormat(Locale locale, int n) {
            return this.createFormat(ULocale.forLocale(locale), n);
        }

        protected NumberFormatFactory() {
        }
    }
}

