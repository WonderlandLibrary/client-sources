/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.number.AffixUtils;
import com.ibm.icu.impl.number.DecimalFormatProperties;
import com.ibm.icu.impl.number.Padder;
import com.ibm.icu.impl.number.PatternStringParser;
import com.ibm.icu.impl.number.PatternStringUtils;
import com.ibm.icu.impl.number.Properties;
import com.ibm.icu.impl.number.parse.NumberParserImpl;
import com.ibm.icu.impl.number.parse.ParsedNumber;
import com.ibm.icu.number.FormattedNumber;
import com.ibm.icu.number.LocalizedNumberFormatter;
import com.ibm.icu.number.NumberFormatter;
import com.ibm.icu.text.CurrencyPluralInfo;
import com.ibm.icu.text.DecimalFormatSymbols;
import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.text.PluralRules;
import com.ibm.icu.util.Currency;
import com.ibm.icu.util.CurrencyAmount;
import com.ibm.icu.util.ULocale;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.AttributedCharacterIterator;
import java.text.FieldPosition;
import java.text.ParsePosition;

public class DecimalFormat
extends NumberFormat {
    private static final long serialVersionUID = 864413376551465018L;
    private final int serialVersionOnStream = 5;
    transient DecimalFormatProperties properties;
    volatile transient DecimalFormatSymbols symbols;
    volatile transient LocalizedNumberFormatter formatter;
    volatile transient DecimalFormatProperties exportedProperties;
    volatile transient NumberParserImpl parser;
    volatile transient NumberParserImpl currencyParser;
    private transient int icuMathContextForm = 0;
    public static final int PAD_BEFORE_PREFIX = 0;
    public static final int PAD_AFTER_PREFIX = 1;
    public static final int PAD_BEFORE_SUFFIX = 2;
    public static final int PAD_AFTER_SUFFIX = 3;
    static final boolean $assertionsDisabled = !DecimalFormat.class.desiredAssertionStatus();

    public DecimalFormat() {
        ULocale uLocale = ULocale.getDefault(ULocale.Category.FORMAT);
        String string = DecimalFormat.getPattern(uLocale, 0);
        this.symbols = DecimalFormat.getDefaultSymbols();
        this.properties = new DecimalFormatProperties();
        this.exportedProperties = new DecimalFormatProperties();
        this.setPropertiesFromPattern(string, 1);
        this.refreshFormatter();
    }

    public DecimalFormat(String string) {
        this.symbols = DecimalFormat.getDefaultSymbols();
        this.properties = new DecimalFormatProperties();
        this.exportedProperties = new DecimalFormatProperties();
        this.setPropertiesFromPattern(string, 1);
        this.refreshFormatter();
    }

    public DecimalFormat(String string, DecimalFormatSymbols decimalFormatSymbols) {
        this.symbols = (DecimalFormatSymbols)decimalFormatSymbols.clone();
        this.properties = new DecimalFormatProperties();
        this.exportedProperties = new DecimalFormatProperties();
        this.setPropertiesFromPattern(string, 1);
        this.refreshFormatter();
    }

    public DecimalFormat(String string, DecimalFormatSymbols decimalFormatSymbols, CurrencyPluralInfo currencyPluralInfo, int n) {
        this(string, decimalFormatSymbols, n);
        this.properties.setCurrencyPluralInfo(currencyPluralInfo);
        this.refreshFormatter();
    }

    DecimalFormat(String string, DecimalFormatSymbols decimalFormatSymbols, int n) {
        this.symbols = (DecimalFormatSymbols)decimalFormatSymbols.clone();
        this.properties = new DecimalFormatProperties();
        this.exportedProperties = new DecimalFormatProperties();
        if (n == 1 || n == 5 || n == 7 || n == 8 || n == 9 || n == 6) {
            this.setPropertiesFromPattern(string, 2);
        } else {
            this.setPropertiesFromPattern(string, 1);
        }
        this.refreshFormatter();
    }

    private static DecimalFormatSymbols getDefaultSymbols() {
        return DecimalFormatSymbols.getInstance();
    }

    public synchronized void applyPattern(String string) {
        this.setPropertiesFromPattern(string, 0);
        this.properties.setPositivePrefix(null);
        this.properties.setNegativePrefix(null);
        this.properties.setPositiveSuffix(null);
        this.properties.setNegativeSuffix(null);
        this.properties.setCurrencyPluralInfo(null);
        this.refreshFormatter();
    }

    public synchronized void applyLocalizedPattern(String string) {
        String string2 = PatternStringUtils.convertLocalized(string, this.symbols, false);
        this.applyPattern(string2);
    }

    @Override
    public Object clone() {
        DecimalFormat decimalFormat = (DecimalFormat)super.clone();
        decimalFormat.symbols = (DecimalFormatSymbols)this.symbols.clone();
        decimalFormat.properties = this.properties.clone();
        decimalFormat.exportedProperties = new DecimalFormatProperties();
        decimalFormat.refreshFormatter();
        return decimalFormat;
    }

    private synchronized void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(0);
        objectOutputStream.writeObject(this.properties);
        objectOutputStream.writeObject(this.symbols);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        ObjectInputStream.GetField getField = objectInputStream.readFields();
        ObjectStreamField[] objectStreamFieldArray = getField.getObjectStreamClass().getFields();
        int n = getField.get("serialVersionOnStream", -1);
        if (n > 5) {
            throw new IOException("Cannot deserialize newer com.ibm.icu.text.DecimalFormat (v" + n + ")");
        }
        if (n == 5) {
            if (objectStreamFieldArray.length > 1) {
                throw new IOException("Too many fields when reading serial version 5");
            }
            objectInputStream.readInt();
            Object object = objectInputStream.readObject();
            this.properties = object instanceof DecimalFormatProperties ? (DecimalFormatProperties)object : ((Properties)object).getInstance();
            this.symbols = (DecimalFormatSymbols)objectInputStream.readObject();
            this.exportedProperties = new DecimalFormatProperties();
            this.refreshFormatter();
        } else {
            this.properties = new DecimalFormatProperties();
            String string = null;
            String string2 = null;
            String string3 = null;
            String string4 = null;
            String string5 = null;
            String string6 = null;
            String string7 = null;
            String string8 = null;
            for (ObjectStreamField objectStreamField : objectStreamFieldArray) {
                String string9 = objectStreamField.getName();
                if (string9.equals("decimalSeparatorAlwaysShown")) {
                    this.setDecimalSeparatorAlwaysShown(getField.get("decimalSeparatorAlwaysShown", true));
                    continue;
                }
                if (string9.equals("exponentSignAlwaysShown")) {
                    this.setExponentSignAlwaysShown(getField.get("exponentSignAlwaysShown", true));
                    continue;
                }
                if (string9.equals("formatWidth")) {
                    this.setFormatWidth(getField.get("formatWidth", 0));
                    continue;
                }
                if (string9.equals("groupingSize")) {
                    this.setGroupingSize(getField.get("groupingSize", (byte)3));
                    continue;
                }
                if (string9.equals("groupingSize2")) {
                    this.setSecondaryGroupingSize(getField.get("groupingSize2", (byte)0));
                    continue;
                }
                if (string9.equals("maxSignificantDigits")) {
                    this.setMaximumSignificantDigits(getField.get("maxSignificantDigits", 6));
                    continue;
                }
                if (string9.equals("minExponentDigits")) {
                    this.setMinimumExponentDigits(getField.get("minExponentDigits", (byte)0));
                    continue;
                }
                if (string9.equals("minSignificantDigits")) {
                    this.setMinimumSignificantDigits(getField.get("minSignificantDigits", 1));
                    continue;
                }
                if (string9.equals("multiplier")) {
                    this.setMultiplier(getField.get("multiplier", 1));
                    continue;
                }
                if (string9.equals("pad")) {
                    this.setPadCharacter(getField.get("pad", ' '));
                    continue;
                }
                if (string9.equals("padPosition")) {
                    this.setPadPosition(getField.get("padPosition", 0));
                    continue;
                }
                if (string9.equals("parseBigDecimal")) {
                    this.setParseBigDecimal(getField.get("parseBigDecimal", true));
                    continue;
                }
                if (string9.equals("parseRequireDecimalPoint")) {
                    this.setDecimalPatternMatchRequired(getField.get("parseRequireDecimalPoint", true));
                    continue;
                }
                if (string9.equals("roundingMode")) {
                    this.setRoundingMode(getField.get("roundingMode", 0));
                    continue;
                }
                if (string9.equals("useExponentialNotation")) {
                    this.setScientificNotation(getField.get("useExponentialNotation", true));
                    continue;
                }
                if (string9.equals("useSignificantDigits")) {
                    this.setSignificantDigitsUsed(getField.get("useSignificantDigits", true));
                    continue;
                }
                if (string9.equals("currencyPluralInfo")) {
                    this.setCurrencyPluralInfo((CurrencyPluralInfo)getField.get("currencyPluralInfo", null));
                    continue;
                }
                if (string9.equals("mathContext")) {
                    this.setMathContextICU((com.ibm.icu.math.MathContext)getField.get("mathContext", null));
                    continue;
                }
                if (string9.equals("negPrefixPattern")) {
                    string6 = (String)getField.get("negPrefixPattern", null);
                    continue;
                }
                if (string9.equals("negSuffixPattern")) {
                    string8 = (String)getField.get("negSuffixPattern", null);
                    continue;
                }
                if (string9.equals("negativePrefix")) {
                    string5 = (String)getField.get("negativePrefix", null);
                    continue;
                }
                if (string9.equals("negativeSuffix")) {
                    string7 = (String)getField.get("negativeSuffix", null);
                    continue;
                }
                if (string9.equals("posPrefixPattern")) {
                    string2 = (String)getField.get("posPrefixPattern", null);
                    continue;
                }
                if (string9.equals("posSuffixPattern")) {
                    string4 = (String)getField.get("posSuffixPattern", null);
                    continue;
                }
                if (string9.equals("positivePrefix")) {
                    string = (String)getField.get("positivePrefix", null);
                    continue;
                }
                if (string9.equals("positiveSuffix")) {
                    string3 = (String)getField.get("positiveSuffix", null);
                    continue;
                }
                if (string9.equals("roundingIncrement")) {
                    this.setRoundingIncrement((BigDecimal)getField.get("roundingIncrement", null));
                    continue;
                }
                if (!string9.equals("symbols")) continue;
                this.setDecimalFormatSymbols((DecimalFormatSymbols)getField.get("symbols", null));
            }
            if (string6 == null) {
                this.properties.setNegativePrefix(string5);
            } else {
                this.properties.setNegativePrefixPattern(string6);
            }
            if (string8 == null) {
                this.properties.setNegativeSuffix(string7);
            } else {
                this.properties.setNegativeSuffixPattern(string8);
            }
            if (string2 == null) {
                this.properties.setPositivePrefix(string);
            } else {
                this.properties.setPositivePrefixPattern(string2);
            }
            if (string4 == null) {
                this.properties.setPositiveSuffix(string3);
            } else {
                this.properties.setPositiveSuffixPattern(string4);
            }
            try {
                Object object = NumberFormat.class.getDeclaredField("groupingUsed");
                ((AccessibleObject)object).setAccessible(false);
                this.setGroupingUsed((Boolean)((Field)object).get(this));
                object = NumberFormat.class.getDeclaredField("parseIntegerOnly");
                ((AccessibleObject)object).setAccessible(false);
                this.setParseIntegerOnly((Boolean)((Field)object).get(this));
                object = NumberFormat.class.getDeclaredField("maximumIntegerDigits");
                ((AccessibleObject)object).setAccessible(false);
                this.setMaximumIntegerDigits((Integer)((Field)object).get(this));
                object = NumberFormat.class.getDeclaredField("minimumIntegerDigits");
                ((AccessibleObject)object).setAccessible(false);
                this.setMinimumIntegerDigits((Integer)((Field)object).get(this));
                object = NumberFormat.class.getDeclaredField("maximumFractionDigits");
                ((AccessibleObject)object).setAccessible(false);
                this.setMaximumFractionDigits((Integer)((Field)object).get(this));
                object = NumberFormat.class.getDeclaredField("minimumFractionDigits");
                ((AccessibleObject)object).setAccessible(false);
                this.setMinimumFractionDigits((Integer)((Field)object).get(this));
                object = NumberFormat.class.getDeclaredField("currency");
                ((AccessibleObject)object).setAccessible(false);
                this.setCurrency((Currency)((Field)object).get(this));
                object = NumberFormat.class.getDeclaredField("parseStrict");
                ((AccessibleObject)object).setAccessible(false);
                this.setParseStrict((Boolean)((Field)object).get(this));
            } catch (IllegalArgumentException illegalArgumentException) {
                throw new IOException(illegalArgumentException);
            } catch (IllegalAccessException illegalAccessException) {
                throw new IOException(illegalAccessException);
            } catch (NoSuchFieldException noSuchFieldException) {
                throw new IOException(noSuchFieldException);
            } catch (SecurityException securityException) {
                throw new IOException(securityException);
            }
            if (this.symbols == null) {
                this.symbols = DecimalFormat.getDefaultSymbols();
            }
            this.exportedProperties = new DecimalFormatProperties();
            this.refreshFormatter();
        }
    }

    @Override
    public StringBuffer format(double d, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        FormattedNumber formattedNumber = this.formatter.format(d);
        DecimalFormat.fieldPositionHelper(formattedNumber, fieldPosition, stringBuffer.length());
        formattedNumber.appendTo(stringBuffer);
        return stringBuffer;
    }

    @Override
    public StringBuffer format(long l, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        FormattedNumber formattedNumber = this.formatter.format(l);
        DecimalFormat.fieldPositionHelper(formattedNumber, fieldPosition, stringBuffer.length());
        formattedNumber.appendTo(stringBuffer);
        return stringBuffer;
    }

    @Override
    public StringBuffer format(BigInteger bigInteger, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        FormattedNumber formattedNumber = this.formatter.format(bigInteger);
        DecimalFormat.fieldPositionHelper(formattedNumber, fieldPosition, stringBuffer.length());
        formattedNumber.appendTo(stringBuffer);
        return stringBuffer;
    }

    @Override
    public StringBuffer format(BigDecimal bigDecimal, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        FormattedNumber formattedNumber = this.formatter.format(bigDecimal);
        DecimalFormat.fieldPositionHelper(formattedNumber, fieldPosition, stringBuffer.length());
        formattedNumber.appendTo(stringBuffer);
        return stringBuffer;
    }

    @Override
    public StringBuffer format(com.ibm.icu.math.BigDecimal bigDecimal, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        FormattedNumber formattedNumber = this.formatter.format(bigDecimal);
        DecimalFormat.fieldPositionHelper(formattedNumber, fieldPosition, stringBuffer.length());
        formattedNumber.appendTo(stringBuffer);
        return stringBuffer;
    }

    @Override
    public AttributedCharacterIterator formatToCharacterIterator(Object object) {
        if (!(object instanceof Number)) {
            throw new IllegalArgumentException();
        }
        Number number = (Number)object;
        FormattedNumber formattedNumber = this.formatter.format(number);
        return formattedNumber.toCharacterIterator();
    }

    @Override
    public StringBuffer format(CurrencyAmount currencyAmount, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        FormattedNumber formattedNumber = this.formatter.format(currencyAmount);
        DecimalFormat.fieldPositionHelper(formattedNumber, fieldPosition, stringBuffer.length());
        formattedNumber.appendTo(stringBuffer);
        return stringBuffer;
    }

    @Override
    public Number parse(String string, ParsePosition parsePosition) {
        if (string == null) {
            throw new IllegalArgumentException("Text cannot be null");
        }
        if (parsePosition == null) {
            parsePosition = new ParsePosition(0);
        }
        if (parsePosition.getIndex() < 0) {
            throw new IllegalArgumentException("Cannot start parsing at a negative offset");
        }
        if (parsePosition.getIndex() >= string.length()) {
            return null;
        }
        ParsedNumber parsedNumber = new ParsedNumber();
        int n = parsePosition.getIndex();
        NumberParserImpl numberParserImpl = this.getParser();
        numberParserImpl.parse(string, n, true, parsedNumber);
        if (parsedNumber.success()) {
            parsePosition.setIndex(parsedNumber.charEnd);
            Number number = parsedNumber.getNumber(numberParserImpl.getParseFlags());
            if (number instanceof BigDecimal) {
                number = this.safeConvertBigDecimal((BigDecimal)number);
            }
            return number;
        }
        parsePosition.setErrorIndex(n + parsedNumber.charEnd);
        return null;
    }

    @Override
    public CurrencyAmount parseCurrency(CharSequence charSequence, ParsePosition parsePosition) {
        if (charSequence == null) {
            throw new IllegalArgumentException("Text cannot be null");
        }
        if (parsePosition == null) {
            parsePosition = new ParsePosition(0);
        }
        if (parsePosition.getIndex() < 0) {
            throw new IllegalArgumentException("Cannot start parsing at a negative offset");
        }
        if (parsePosition.getIndex() >= charSequence.length()) {
            return null;
        }
        ParsedNumber parsedNumber = new ParsedNumber();
        int n = parsePosition.getIndex();
        NumberParserImpl numberParserImpl = this.getCurrencyParser();
        numberParserImpl.parse(charSequence.toString(), n, true, parsedNumber);
        if (parsedNumber.success()) {
            parsePosition.setIndex(parsedNumber.charEnd);
            Number number = parsedNumber.getNumber(numberParserImpl.getParseFlags());
            if (number instanceof BigDecimal) {
                number = this.safeConvertBigDecimal((BigDecimal)number);
            }
            Currency currency = Currency.getInstance(parsedNumber.currencyCode);
            return new CurrencyAmount(number, currency);
        }
        parsePosition.setErrorIndex(n + parsedNumber.charEnd);
        return null;
    }

    public synchronized DecimalFormatSymbols getDecimalFormatSymbols() {
        return (DecimalFormatSymbols)this.symbols.clone();
    }

    public synchronized void setDecimalFormatSymbols(DecimalFormatSymbols decimalFormatSymbols) {
        this.symbols = (DecimalFormatSymbols)decimalFormatSymbols.clone();
        this.refreshFormatter();
    }

    public synchronized String getPositivePrefix() {
        return this.formatter.getAffixImpl(true, true);
    }

    public synchronized void setPositivePrefix(String string) {
        if (string == null) {
            throw new NullPointerException();
        }
        this.properties.setPositivePrefix(string);
        this.refreshFormatter();
    }

    public synchronized String getNegativePrefix() {
        return this.formatter.getAffixImpl(true, false);
    }

    public synchronized void setNegativePrefix(String string) {
        if (string == null) {
            throw new NullPointerException();
        }
        this.properties.setNegativePrefix(string);
        this.refreshFormatter();
    }

    public synchronized String getPositiveSuffix() {
        return this.formatter.getAffixImpl(false, true);
    }

    public synchronized void setPositiveSuffix(String string) {
        if (string == null) {
            throw new NullPointerException();
        }
        this.properties.setPositiveSuffix(string);
        this.refreshFormatter();
    }

    public synchronized String getNegativeSuffix() {
        return this.formatter.getAffixImpl(false, false);
    }

    public synchronized void setNegativeSuffix(String string) {
        if (string == null) {
            throw new NullPointerException();
        }
        this.properties.setNegativeSuffix(string);
        this.refreshFormatter();
    }

    public synchronized boolean isSignAlwaysShown() {
        return this.properties.getSignAlwaysShown();
    }

    public synchronized void setSignAlwaysShown(boolean bl) {
        this.properties.setSignAlwaysShown(bl);
        this.refreshFormatter();
    }

    public synchronized int getMultiplier() {
        if (this.properties.getMultiplier() != null) {
            return this.properties.getMultiplier().intValue();
        }
        return (int)Math.pow(10.0, this.properties.getMagnitudeMultiplier());
    }

    public synchronized void setMultiplier(int n) {
        if (n == 0) {
            throw new IllegalArgumentException("Multiplier must be nonzero.");
        }
        int n2 = 0;
        int n3 = n;
        while (n3 != 1) {
            ++n2;
            int n4 = n3 / 10;
            if (n4 * 10 != n3) {
                n2 = -1;
                break;
            }
            n3 = n4;
        }
        if (n2 != -1) {
            this.properties.setMagnitudeMultiplier(n2);
            this.properties.setMultiplier(null);
        } else {
            this.properties.setMagnitudeMultiplier(0);
            this.properties.setMultiplier(BigDecimal.valueOf(n));
        }
        this.refreshFormatter();
    }

    public synchronized BigDecimal getRoundingIncrement() {
        return this.exportedProperties.getRoundingIncrement();
    }

    public synchronized void setRoundingIncrement(BigDecimal bigDecimal) {
        if (bigDecimal != null && bigDecimal.compareTo(BigDecimal.ZERO) == 0) {
            this.properties.setMaximumFractionDigits(Integer.MAX_VALUE);
            return;
        }
        this.properties.setRoundingIncrement(bigDecimal);
        this.refreshFormatter();
    }

    public synchronized void setRoundingIncrement(com.ibm.icu.math.BigDecimal bigDecimal) {
        BigDecimal bigDecimal2 = bigDecimal == null ? null : bigDecimal.toBigDecimal();
        this.setRoundingIncrement(bigDecimal2);
    }

    public synchronized void setRoundingIncrement(double d) {
        if (d == 0.0) {
            this.setRoundingIncrement((BigDecimal)null);
        } else {
            BigDecimal bigDecimal = BigDecimal.valueOf(d);
            this.setRoundingIncrement(bigDecimal);
        }
    }

    @Override
    public synchronized int getRoundingMode() {
        RoundingMode roundingMode = this.exportedProperties.getRoundingMode();
        return roundingMode == null ? 0 : roundingMode.ordinal();
    }

    @Override
    public synchronized void setRoundingMode(int n) {
        this.properties.setRoundingMode(RoundingMode.valueOf(n));
        this.refreshFormatter();
    }

    public synchronized MathContext getMathContext() {
        MathContext mathContext = this.exportedProperties.getMathContext();
        if (!$assertionsDisabled && mathContext == null) {
            throw new AssertionError();
        }
        return mathContext;
    }

    public synchronized void setMathContext(MathContext mathContext) {
        this.properties.setMathContext(mathContext);
        this.refreshFormatter();
    }

    public synchronized com.ibm.icu.math.MathContext getMathContextICU() {
        MathContext mathContext = this.getMathContext();
        return new com.ibm.icu.math.MathContext(mathContext.getPrecision(), this.icuMathContextForm, false, mathContext.getRoundingMode().ordinal());
    }

    public synchronized void setMathContextICU(com.ibm.icu.math.MathContext mathContext) {
        this.icuMathContextForm = mathContext.getForm();
        MathContext mathContext2 = mathContext.getLostDigits() ? new MathContext(mathContext.getDigits(), RoundingMode.UNNECESSARY) : new MathContext(mathContext.getDigits(), RoundingMode.valueOf(mathContext.getRoundingMode()));
        this.setMathContext(mathContext2);
    }

    @Override
    public synchronized int getMinimumIntegerDigits() {
        return this.exportedProperties.getMinimumIntegerDigits();
    }

    @Override
    public synchronized void setMinimumIntegerDigits(int n) {
        int n2 = this.properties.getMaximumIntegerDigits();
        if (n2 >= 0 && n2 < n) {
            this.properties.setMaximumIntegerDigits(n);
        }
        this.properties.setMinimumIntegerDigits(n);
        this.refreshFormatter();
    }

    @Override
    public synchronized int getMaximumIntegerDigits() {
        return this.exportedProperties.getMaximumIntegerDigits();
    }

    @Override
    public synchronized void setMaximumIntegerDigits(int n) {
        int n2 = this.properties.getMinimumIntegerDigits();
        if (n2 >= 0 && n2 > n) {
            this.properties.setMinimumIntegerDigits(n);
        }
        this.properties.setMaximumIntegerDigits(n);
        this.refreshFormatter();
    }

    @Override
    public synchronized int getMinimumFractionDigits() {
        return this.exportedProperties.getMinimumFractionDigits();
    }

    @Override
    public synchronized void setMinimumFractionDigits(int n) {
        int n2 = this.properties.getMaximumFractionDigits();
        if (n2 >= 0 && n2 < n) {
            this.properties.setMaximumFractionDigits(n);
        }
        this.properties.setMinimumFractionDigits(n);
        this.refreshFormatter();
    }

    @Override
    public synchronized int getMaximumFractionDigits() {
        return this.exportedProperties.getMaximumFractionDigits();
    }

    @Override
    public synchronized void setMaximumFractionDigits(int n) {
        int n2 = this.properties.getMinimumFractionDigits();
        if (n2 >= 0 && n2 > n) {
            this.properties.setMinimumFractionDigits(n);
        }
        this.properties.setMaximumFractionDigits(n);
        this.refreshFormatter();
    }

    public synchronized boolean areSignificantDigitsUsed() {
        return this.properties.getMinimumSignificantDigits() != -1 || this.properties.getMaximumSignificantDigits() != -1;
    }

    public synchronized void setSignificantDigitsUsed(boolean bl) {
        int n = this.properties.getMinimumSignificantDigits();
        int n2 = this.properties.getMaximumSignificantDigits();
        if (bl ? n != -1 || n2 != -1 : n == -1 && n2 == -1) {
            return;
        }
        int n3 = bl ? 1 : -1;
        int n4 = bl ? 6 : -1;
        this.properties.setMinimumSignificantDigits(n3);
        this.properties.setMaximumSignificantDigits(n4);
        this.refreshFormatter();
    }

    public synchronized int getMinimumSignificantDigits() {
        return this.exportedProperties.getMinimumSignificantDigits();
    }

    public synchronized void setMinimumSignificantDigits(int n) {
        int n2 = this.properties.getMaximumSignificantDigits();
        if (n2 >= 0 && n2 < n) {
            this.properties.setMaximumSignificantDigits(n);
        }
        this.properties.setMinimumSignificantDigits(n);
        this.refreshFormatter();
    }

    public synchronized int getMaximumSignificantDigits() {
        return this.exportedProperties.getMaximumSignificantDigits();
    }

    public synchronized void setMaximumSignificantDigits(int n) {
        int n2 = this.properties.getMinimumSignificantDigits();
        if (n2 >= 0 && n2 > n) {
            this.properties.setMinimumSignificantDigits(n);
        }
        this.properties.setMaximumSignificantDigits(n);
        this.refreshFormatter();
    }

    public synchronized int getFormatWidth() {
        return this.properties.getFormatWidth();
    }

    public synchronized void setFormatWidth(int n) {
        this.properties.setFormatWidth(n);
        this.refreshFormatter();
    }

    public synchronized char getPadCharacter() {
        String string = this.properties.getPadString();
        if (string == null) {
            return " ".charAt(0);
        }
        return string.charAt(0);
    }

    public synchronized void setPadCharacter(char c) {
        this.properties.setPadString(Character.toString(c));
        this.refreshFormatter();
    }

    public synchronized int getPadPosition() {
        Padder.PadPosition padPosition = this.properties.getPadPosition();
        return padPosition == null ? 0 : padPosition.toOld();
    }

    public synchronized void setPadPosition(int n) {
        this.properties.setPadPosition(Padder.PadPosition.fromOld(n));
        this.refreshFormatter();
    }

    public synchronized boolean isScientificNotation() {
        return this.properties.getMinimumExponentDigits() != -1;
    }

    public synchronized void setScientificNotation(boolean bl) {
        if (bl) {
            this.properties.setMinimumExponentDigits(1);
        } else {
            this.properties.setMinimumExponentDigits(-1);
        }
        this.refreshFormatter();
    }

    public synchronized byte getMinimumExponentDigits() {
        return (byte)this.properties.getMinimumExponentDigits();
    }

    public synchronized void setMinimumExponentDigits(byte by) {
        this.properties.setMinimumExponentDigits(by);
        this.refreshFormatter();
    }

    public synchronized boolean isExponentSignAlwaysShown() {
        return this.properties.getExponentSignAlwaysShown();
    }

    public synchronized void setExponentSignAlwaysShown(boolean bl) {
        this.properties.setExponentSignAlwaysShown(bl);
        this.refreshFormatter();
    }

    @Override
    public synchronized boolean isGroupingUsed() {
        return this.properties.getGroupingUsed();
    }

    @Override
    public synchronized void setGroupingUsed(boolean bl) {
        this.properties.setGroupingUsed(bl);
        this.refreshFormatter();
    }

    public synchronized int getGroupingSize() {
        if (this.properties.getGroupingSize() < 0) {
            return 1;
        }
        return this.properties.getGroupingSize();
    }

    public synchronized void setGroupingSize(int n) {
        this.properties.setGroupingSize(n);
        this.refreshFormatter();
    }

    public synchronized int getSecondaryGroupingSize() {
        int n = this.properties.getSecondaryGroupingSize();
        if (n < 0) {
            return 1;
        }
        return n;
    }

    public synchronized void setSecondaryGroupingSize(int n) {
        this.properties.setSecondaryGroupingSize(n);
        this.refreshFormatter();
    }

    public synchronized int getMinimumGroupingDigits() {
        if (this.properties.getMinimumGroupingDigits() > 0) {
            return this.properties.getMinimumGroupingDigits();
        }
        return 0;
    }

    public synchronized void setMinimumGroupingDigits(int n) {
        this.properties.setMinimumGroupingDigits(n);
        this.refreshFormatter();
    }

    public synchronized boolean isDecimalSeparatorAlwaysShown() {
        return this.properties.getDecimalSeparatorAlwaysShown();
    }

    public synchronized void setDecimalSeparatorAlwaysShown(boolean bl) {
        this.properties.setDecimalSeparatorAlwaysShown(bl);
        this.refreshFormatter();
    }

    @Override
    public synchronized Currency getCurrency() {
        return this.exportedProperties.getCurrency();
    }

    @Override
    public synchronized void setCurrency(Currency currency) {
        this.properties.setCurrency(currency);
        if (currency != null) {
            this.symbols.setCurrency(currency);
            String string = currency.getName(this.symbols.getULocale(), 0, null);
            this.symbols.setCurrencySymbol(string);
        }
        this.refreshFormatter();
    }

    public synchronized Currency.CurrencyUsage getCurrencyUsage() {
        Currency.CurrencyUsage currencyUsage = this.properties.getCurrencyUsage();
        if (currencyUsage == null) {
            currencyUsage = Currency.CurrencyUsage.STANDARD;
        }
        return currencyUsage;
    }

    public synchronized void setCurrencyUsage(Currency.CurrencyUsage currencyUsage) {
        this.properties.setCurrencyUsage(currencyUsage);
        this.refreshFormatter();
    }

    public synchronized CurrencyPluralInfo getCurrencyPluralInfo() {
        return this.properties.getCurrencyPluralInfo();
    }

    public synchronized void setCurrencyPluralInfo(CurrencyPluralInfo currencyPluralInfo) {
        this.properties.setCurrencyPluralInfo(currencyPluralInfo);
        this.refreshFormatter();
    }

    public synchronized boolean isParseBigDecimal() {
        return this.properties.getParseToBigDecimal();
    }

    public synchronized void setParseBigDecimal(boolean bl) {
        this.properties.setParseToBigDecimal(bl);
        this.refreshFormatter();
    }

    @Deprecated
    public int getParseMaxDigits() {
        return 1;
    }

    @Deprecated
    public void setParseMaxDigits(int n) {
    }

    @Override
    public synchronized boolean isParseStrict() {
        return this.properties.getParseMode() == DecimalFormatProperties.ParseMode.STRICT;
    }

    @Override
    public synchronized void setParseStrict(boolean bl) {
        DecimalFormatProperties.ParseMode parseMode = bl ? DecimalFormatProperties.ParseMode.STRICT : DecimalFormatProperties.ParseMode.LENIENT;
        this.properties.setParseMode(parseMode);
        this.refreshFormatter();
    }

    @Deprecated
    public synchronized void setParseStrictMode(DecimalFormatProperties.ParseMode parseMode) {
        this.properties.setParseMode(parseMode);
        this.refreshFormatter();
    }

    @Override
    public synchronized boolean isParseIntegerOnly() {
        return this.properties.getParseIntegerOnly();
    }

    @Override
    public synchronized void setParseIntegerOnly(boolean bl) {
        this.properties.setParseIntegerOnly(bl);
        this.refreshFormatter();
    }

    public synchronized boolean isDecimalPatternMatchRequired() {
        return this.properties.getDecimalPatternMatchRequired();
    }

    public synchronized void setDecimalPatternMatchRequired(boolean bl) {
        this.properties.setDecimalPatternMatchRequired(bl);
        this.refreshFormatter();
    }

    public synchronized boolean isParseNoExponent() {
        return this.properties.getParseNoExponent();
    }

    public synchronized void setParseNoExponent(boolean bl) {
        this.properties.setParseNoExponent(bl);
        this.refreshFormatter();
    }

    public synchronized boolean isParseCaseSensitive() {
        return this.properties.getParseCaseSensitive();
    }

    public synchronized void setParseCaseSensitive(boolean bl) {
        this.properties.setParseCaseSensitive(bl);
        this.refreshFormatter();
    }

    @Override
    public synchronized boolean equals(Object object) {
        if (object == null) {
            return true;
        }
        if (object == this) {
            return false;
        }
        if (!(object instanceof DecimalFormat)) {
            return true;
        }
        DecimalFormat decimalFormat = (DecimalFormat)object;
        return this.properties.equals(decimalFormat.properties) && this.symbols.equals(decimalFormat.symbols);
    }

    @Override
    public synchronized int hashCode() {
        return this.properties.hashCode() ^ this.symbols.hashCode();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getName());
        stringBuilder.append("@");
        stringBuilder.append(Integer.toHexString(this.hashCode()));
        stringBuilder.append(" { symbols@");
        stringBuilder.append(Integer.toHexString(this.symbols.hashCode()));
        DecimalFormat decimalFormat = this;
        synchronized (decimalFormat) {
            this.properties.toStringBare(stringBuilder);
        }
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }

    public synchronized String toPattern() {
        boolean bl;
        DecimalFormatProperties decimalFormatProperties = new DecimalFormatProperties().copyFrom(this.properties);
        boolean bl2 = bl = decimalFormatProperties.getCurrency() != null || decimalFormatProperties.getCurrencyPluralInfo() != null || decimalFormatProperties.getCurrencyUsage() != null || AffixUtils.hasCurrencySymbols(decimalFormatProperties.getPositivePrefixPattern()) || AffixUtils.hasCurrencySymbols(decimalFormatProperties.getPositiveSuffixPattern()) || AffixUtils.hasCurrencySymbols(decimalFormatProperties.getNegativePrefixPattern()) || AffixUtils.hasCurrencySymbols(decimalFormatProperties.getNegativeSuffixPattern());
        if (bl) {
            decimalFormatProperties.setMinimumFractionDigits(this.exportedProperties.getMinimumFractionDigits());
            decimalFormatProperties.setMaximumFractionDigits(this.exportedProperties.getMaximumFractionDigits());
            decimalFormatProperties.setRoundingIncrement(this.exportedProperties.getRoundingIncrement());
        }
        return PatternStringUtils.propertiesToPatternString(decimalFormatProperties);
    }

    public synchronized String toLocalizedPattern() {
        String string = this.toPattern();
        return PatternStringUtils.convertLocalized(string, this.symbols, true);
    }

    public LocalizedNumberFormatter toNumberFormatter() {
        return this.formatter;
    }

    @Deprecated
    public PluralRules.IFixedDecimal getFixedDecimal(double d) {
        return this.formatter.format(d).getFixedDecimal();
    }

    void refreshFormatter() {
        if (this.exportedProperties == null) {
            return;
        }
        ULocale uLocale = this.getLocale(ULocale.ACTUAL_LOCALE);
        if (uLocale == null) {
            uLocale = this.symbols.getLocale(ULocale.ACTUAL_LOCALE);
        }
        if (uLocale == null) {
            uLocale = this.symbols.getULocale();
        }
        if (!$assertionsDisabled && uLocale == null) {
            throw new AssertionError();
        }
        this.formatter = NumberFormatter.fromDecimalFormat(this.properties, this.symbols, this.exportedProperties).locale(uLocale);
        this.parser = null;
        this.currencyParser = null;
    }

    NumberParserImpl getParser() {
        if (this.parser == null) {
            this.parser = NumberParserImpl.createParserFromProperties(this.properties, this.symbols, false);
        }
        return this.parser;
    }

    NumberParserImpl getCurrencyParser() {
        if (this.currencyParser == null) {
            this.currencyParser = NumberParserImpl.createParserFromProperties(this.properties, this.symbols, true);
        }
        return this.currencyParser;
    }

    private Number safeConvertBigDecimal(BigDecimal bigDecimal) {
        try {
            return new com.ibm.icu.math.BigDecimal(bigDecimal);
        } catch (NumberFormatException numberFormatException) {
            if (bigDecimal.signum() > 0 && bigDecimal.scale() < 0) {
                return Double.POSITIVE_INFINITY;
            }
            if (bigDecimal.scale() < 0) {
                return Double.NEGATIVE_INFINITY;
            }
            if (bigDecimal.signum() < 0) {
                return -0.0;
            }
            return 0.0;
        }
    }

    void setPropertiesFromPattern(String string, int n) {
        if (string == null) {
            throw new NullPointerException();
        }
        PatternStringParser.parseToExistingProperties(string, this.properties, n);
    }

    static void fieldPositionHelper(FormattedNumber formattedNumber, FieldPosition fieldPosition, int n) {
        fieldPosition.setBeginIndex(0);
        fieldPosition.setEndIndex(0);
        boolean bl = formattedNumber.nextFieldPosition(fieldPosition);
        if (bl && n != 0) {
            fieldPosition.setBeginIndex(fieldPosition.getBeginIndex() + n);
            fieldPosition.setEndIndex(fieldPosition.getEndIndex() + n);
        }
    }

    @Deprecated
    public synchronized void setProperties(PropertySetter propertySetter) {
        propertySetter.set(this.properties);
        this.refreshFormatter();
    }

    @Deprecated
    public static interface PropertySetter {
        @Deprecated
        public void set(DecimalFormatProperties var1);
    }
}

