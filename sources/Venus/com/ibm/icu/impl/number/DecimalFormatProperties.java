/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.number;

import com.ibm.icu.impl.number.Padder;
import com.ibm.icu.text.CompactDecimalFormat;
import com.ibm.icu.text.CurrencyPluralInfo;
import com.ibm.icu.text.PluralRules;
import com.ibm.icu.util.Currency;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Map;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DecimalFormatProperties
implements Cloneable,
Serializable {
    private static final DecimalFormatProperties DEFAULT = new DecimalFormatProperties();
    private static final long serialVersionUID = 4095518955889349243L;
    private transient Map<String, Map<String, String>> compactCustomData;
    private transient CompactDecimalFormat.CompactStyle compactStyle;
    private transient Currency currency;
    private transient CurrencyPluralInfo currencyPluralInfo;
    private transient Currency.CurrencyUsage currencyUsage;
    private transient boolean decimalPatternMatchRequired;
    private transient boolean decimalSeparatorAlwaysShown;
    private transient boolean exponentSignAlwaysShown;
    private transient int formatWidth;
    private transient int groupingSize;
    private transient boolean groupingUsed;
    private transient int magnitudeMultiplier;
    private transient MathContext mathContext;
    private transient int maximumFractionDigits;
    private transient int maximumIntegerDigits;
    private transient int maximumSignificantDigits;
    private transient int minimumExponentDigits;
    private transient int minimumFractionDigits;
    private transient int minimumGroupingDigits;
    private transient int minimumIntegerDigits;
    private transient int minimumSignificantDigits;
    private transient BigDecimal multiplier;
    private transient String negativePrefix;
    private transient String negativePrefixPattern;
    private transient String negativeSuffix;
    private transient String negativeSuffixPattern;
    private transient Padder.PadPosition padPosition;
    private transient String padString;
    private transient boolean parseCaseSensitive;
    private transient boolean parseIntegerOnly;
    private transient ParseMode parseMode;
    private transient boolean parseNoExponent;
    private transient boolean parseToBigDecimal;
    private transient PluralRules pluralRules;
    private transient String positivePrefix;
    private transient String positivePrefixPattern;
    private transient String positiveSuffix;
    private transient String positiveSuffixPattern;
    private transient BigDecimal roundingIncrement;
    private transient RoundingMode roundingMode;
    private transient int secondaryGroupingSize;
    private transient boolean signAlwaysShown;

    public DecimalFormatProperties() {
        this.clear();
    }

    private DecimalFormatProperties _clear() {
        this.compactCustomData = null;
        this.compactStyle = null;
        this.currency = null;
        this.currencyPluralInfo = null;
        this.currencyUsage = null;
        this.decimalPatternMatchRequired = false;
        this.decimalSeparatorAlwaysShown = false;
        this.exponentSignAlwaysShown = false;
        this.formatWidth = -1;
        this.groupingSize = -1;
        this.groupingUsed = true;
        this.magnitudeMultiplier = 0;
        this.mathContext = null;
        this.maximumFractionDigits = -1;
        this.maximumIntegerDigits = -1;
        this.maximumSignificantDigits = -1;
        this.minimumExponentDigits = -1;
        this.minimumFractionDigits = -1;
        this.minimumGroupingDigits = -1;
        this.minimumIntegerDigits = -1;
        this.minimumSignificantDigits = -1;
        this.multiplier = null;
        this.negativePrefix = null;
        this.negativePrefixPattern = null;
        this.negativeSuffix = null;
        this.negativeSuffixPattern = null;
        this.padPosition = null;
        this.padString = null;
        this.parseCaseSensitive = false;
        this.parseIntegerOnly = false;
        this.parseMode = null;
        this.parseNoExponent = false;
        this.parseToBigDecimal = false;
        this.pluralRules = null;
        this.positivePrefix = null;
        this.positivePrefixPattern = null;
        this.positiveSuffix = null;
        this.positiveSuffixPattern = null;
        this.roundingIncrement = null;
        this.roundingMode = null;
        this.secondaryGroupingSize = -1;
        this.signAlwaysShown = false;
        return this;
    }

    private DecimalFormatProperties _copyFrom(DecimalFormatProperties decimalFormatProperties) {
        this.compactCustomData = decimalFormatProperties.compactCustomData;
        this.compactStyle = decimalFormatProperties.compactStyle;
        this.currency = decimalFormatProperties.currency;
        this.currencyPluralInfo = decimalFormatProperties.currencyPluralInfo;
        this.currencyUsage = decimalFormatProperties.currencyUsage;
        this.decimalPatternMatchRequired = decimalFormatProperties.decimalPatternMatchRequired;
        this.decimalSeparatorAlwaysShown = decimalFormatProperties.decimalSeparatorAlwaysShown;
        this.exponentSignAlwaysShown = decimalFormatProperties.exponentSignAlwaysShown;
        this.formatWidth = decimalFormatProperties.formatWidth;
        this.groupingSize = decimalFormatProperties.groupingSize;
        this.groupingUsed = decimalFormatProperties.groupingUsed;
        this.magnitudeMultiplier = decimalFormatProperties.magnitudeMultiplier;
        this.mathContext = decimalFormatProperties.mathContext;
        this.maximumFractionDigits = decimalFormatProperties.maximumFractionDigits;
        this.maximumIntegerDigits = decimalFormatProperties.maximumIntegerDigits;
        this.maximumSignificantDigits = decimalFormatProperties.maximumSignificantDigits;
        this.minimumExponentDigits = decimalFormatProperties.minimumExponentDigits;
        this.minimumFractionDigits = decimalFormatProperties.minimumFractionDigits;
        this.minimumGroupingDigits = decimalFormatProperties.minimumGroupingDigits;
        this.minimumIntegerDigits = decimalFormatProperties.minimumIntegerDigits;
        this.minimumSignificantDigits = decimalFormatProperties.minimumSignificantDigits;
        this.multiplier = decimalFormatProperties.multiplier;
        this.negativePrefix = decimalFormatProperties.negativePrefix;
        this.negativePrefixPattern = decimalFormatProperties.negativePrefixPattern;
        this.negativeSuffix = decimalFormatProperties.negativeSuffix;
        this.negativeSuffixPattern = decimalFormatProperties.negativeSuffixPattern;
        this.padPosition = decimalFormatProperties.padPosition;
        this.padString = decimalFormatProperties.padString;
        this.parseCaseSensitive = decimalFormatProperties.parseCaseSensitive;
        this.parseIntegerOnly = decimalFormatProperties.parseIntegerOnly;
        this.parseMode = decimalFormatProperties.parseMode;
        this.parseNoExponent = decimalFormatProperties.parseNoExponent;
        this.parseToBigDecimal = decimalFormatProperties.parseToBigDecimal;
        this.pluralRules = decimalFormatProperties.pluralRules;
        this.positivePrefix = decimalFormatProperties.positivePrefix;
        this.positivePrefixPattern = decimalFormatProperties.positivePrefixPattern;
        this.positiveSuffix = decimalFormatProperties.positiveSuffix;
        this.positiveSuffixPattern = decimalFormatProperties.positiveSuffixPattern;
        this.roundingIncrement = decimalFormatProperties.roundingIncrement;
        this.roundingMode = decimalFormatProperties.roundingMode;
        this.secondaryGroupingSize = decimalFormatProperties.secondaryGroupingSize;
        this.signAlwaysShown = decimalFormatProperties.signAlwaysShown;
        return this;
    }

    private boolean _equals(DecimalFormatProperties decimalFormatProperties) {
        boolean bl = true;
        bl = bl && this._equalsHelper(this.compactCustomData, decimalFormatProperties.compactCustomData);
        bl = bl && this._equalsHelper((Object)this.compactStyle, (Object)decimalFormatProperties.compactStyle);
        bl = bl && this._equalsHelper(this.currency, decimalFormatProperties.currency);
        bl = bl && this._equalsHelper(this.currencyPluralInfo, decimalFormatProperties.currencyPluralInfo);
        bl = bl && this._equalsHelper((Object)this.currencyUsage, (Object)decimalFormatProperties.currencyUsage);
        bl = bl && this._equalsHelper(this.decimalPatternMatchRequired, decimalFormatProperties.decimalPatternMatchRequired);
        bl = bl && this._equalsHelper(this.decimalSeparatorAlwaysShown, decimalFormatProperties.decimalSeparatorAlwaysShown);
        bl = bl && this._equalsHelper(this.exponentSignAlwaysShown, decimalFormatProperties.exponentSignAlwaysShown);
        bl = bl && this._equalsHelper(this.formatWidth, decimalFormatProperties.formatWidth);
        bl = bl && this._equalsHelper(this.groupingSize, decimalFormatProperties.groupingSize);
        bl = bl && this._equalsHelper(this.groupingUsed, decimalFormatProperties.groupingUsed);
        bl = bl && this._equalsHelper(this.magnitudeMultiplier, decimalFormatProperties.magnitudeMultiplier);
        bl = bl && this._equalsHelper(this.mathContext, decimalFormatProperties.mathContext);
        bl = bl && this._equalsHelper(this.maximumFractionDigits, decimalFormatProperties.maximumFractionDigits);
        bl = bl && this._equalsHelper(this.maximumIntegerDigits, decimalFormatProperties.maximumIntegerDigits);
        bl = bl && this._equalsHelper(this.maximumSignificantDigits, decimalFormatProperties.maximumSignificantDigits);
        bl = bl && this._equalsHelper(this.minimumExponentDigits, decimalFormatProperties.minimumExponentDigits);
        bl = bl && this._equalsHelper(this.minimumFractionDigits, decimalFormatProperties.minimumFractionDigits);
        bl = bl && this._equalsHelper(this.minimumGroupingDigits, decimalFormatProperties.minimumGroupingDigits);
        bl = bl && this._equalsHelper(this.minimumIntegerDigits, decimalFormatProperties.minimumIntegerDigits);
        bl = bl && this._equalsHelper(this.minimumSignificantDigits, decimalFormatProperties.minimumSignificantDigits);
        bl = bl && this._equalsHelper(this.multiplier, decimalFormatProperties.multiplier);
        bl = bl && this._equalsHelper(this.negativePrefix, decimalFormatProperties.negativePrefix);
        bl = bl && this._equalsHelper(this.negativePrefixPattern, decimalFormatProperties.negativePrefixPattern);
        bl = bl && this._equalsHelper(this.negativeSuffix, decimalFormatProperties.negativeSuffix);
        bl = bl && this._equalsHelper(this.negativeSuffixPattern, decimalFormatProperties.negativeSuffixPattern);
        bl = bl && this._equalsHelper((Object)this.padPosition, (Object)decimalFormatProperties.padPosition);
        bl = bl && this._equalsHelper(this.padString, decimalFormatProperties.padString);
        bl = bl && this._equalsHelper(this.parseCaseSensitive, decimalFormatProperties.parseCaseSensitive);
        bl = bl && this._equalsHelper(this.parseIntegerOnly, decimalFormatProperties.parseIntegerOnly);
        bl = bl && this._equalsHelper((Object)this.parseMode, (Object)decimalFormatProperties.parseMode);
        bl = bl && this._equalsHelper(this.parseNoExponent, decimalFormatProperties.parseNoExponent);
        bl = bl && this._equalsHelper(this.parseToBigDecimal, decimalFormatProperties.parseToBigDecimal);
        bl = bl && this._equalsHelper(this.pluralRules, decimalFormatProperties.pluralRules);
        bl = bl && this._equalsHelper(this.positivePrefix, decimalFormatProperties.positivePrefix);
        bl = bl && this._equalsHelper(this.positivePrefixPattern, decimalFormatProperties.positivePrefixPattern);
        bl = bl && this._equalsHelper(this.positiveSuffix, decimalFormatProperties.positiveSuffix);
        bl = bl && this._equalsHelper(this.positiveSuffixPattern, decimalFormatProperties.positiveSuffixPattern);
        bl = bl && this._equalsHelper(this.roundingIncrement, decimalFormatProperties.roundingIncrement);
        bl = bl && this._equalsHelper((Object)this.roundingMode, (Object)decimalFormatProperties.roundingMode);
        bl = bl && this._equalsHelper(this.secondaryGroupingSize, decimalFormatProperties.secondaryGroupingSize);
        bl = bl && this._equalsHelper(this.signAlwaysShown, decimalFormatProperties.signAlwaysShown);
        return bl;
    }

    private boolean _equalsHelper(boolean bl, boolean bl2) {
        return bl == bl2;
    }

    private boolean _equalsHelper(int n, int n2) {
        return n == n2;
    }

    private boolean _equalsHelper(Object object, Object object2) {
        if (object == object2) {
            return false;
        }
        if (object == null) {
            return true;
        }
        return object.equals(object2);
    }

    private int _hashCode() {
        int n = 0;
        n ^= this._hashCodeHelper(this.compactCustomData);
        n ^= this._hashCodeHelper((Object)this.compactStyle);
        n ^= this._hashCodeHelper(this.currency);
        n ^= this._hashCodeHelper(this.currencyPluralInfo);
        n ^= this._hashCodeHelper((Object)this.currencyUsage);
        n ^= this._hashCodeHelper(this.decimalPatternMatchRequired);
        n ^= this._hashCodeHelper(this.decimalSeparatorAlwaysShown);
        n ^= this._hashCodeHelper(this.exponentSignAlwaysShown);
        n ^= this._hashCodeHelper(this.formatWidth);
        n ^= this._hashCodeHelper(this.groupingSize);
        n ^= this._hashCodeHelper(this.groupingUsed);
        n ^= this._hashCodeHelper(this.magnitudeMultiplier);
        n ^= this._hashCodeHelper(this.mathContext);
        n ^= this._hashCodeHelper(this.maximumFractionDigits);
        n ^= this._hashCodeHelper(this.maximumIntegerDigits);
        n ^= this._hashCodeHelper(this.maximumSignificantDigits);
        n ^= this._hashCodeHelper(this.minimumExponentDigits);
        n ^= this._hashCodeHelper(this.minimumFractionDigits);
        n ^= this._hashCodeHelper(this.minimumGroupingDigits);
        n ^= this._hashCodeHelper(this.minimumIntegerDigits);
        n ^= this._hashCodeHelper(this.minimumSignificantDigits);
        n ^= this._hashCodeHelper(this.multiplier);
        n ^= this._hashCodeHelper(this.negativePrefix);
        n ^= this._hashCodeHelper(this.negativePrefixPattern);
        n ^= this._hashCodeHelper(this.negativeSuffix);
        n ^= this._hashCodeHelper(this.negativeSuffixPattern);
        n ^= this._hashCodeHelper((Object)this.padPosition);
        n ^= this._hashCodeHelper(this.padString);
        n ^= this._hashCodeHelper(this.parseCaseSensitive);
        n ^= this._hashCodeHelper(this.parseIntegerOnly);
        n ^= this._hashCodeHelper((Object)this.parseMode);
        n ^= this._hashCodeHelper(this.parseNoExponent);
        n ^= this._hashCodeHelper(this.parseToBigDecimal);
        n ^= this._hashCodeHelper(this.pluralRules);
        n ^= this._hashCodeHelper(this.positivePrefix);
        n ^= this._hashCodeHelper(this.positivePrefixPattern);
        n ^= this._hashCodeHelper(this.positiveSuffix);
        n ^= this._hashCodeHelper(this.positiveSuffixPattern);
        n ^= this._hashCodeHelper(this.roundingIncrement);
        n ^= this._hashCodeHelper((Object)this.roundingMode);
        n ^= this._hashCodeHelper(this.secondaryGroupingSize);
        return n ^= this._hashCodeHelper(this.signAlwaysShown);
    }

    private int _hashCodeHelper(boolean bl) {
        return bl ? 1 : 0;
    }

    private int _hashCodeHelper(int n) {
        return n * 13;
    }

    private int _hashCodeHelper(Object object) {
        if (object == null) {
            return 1;
        }
        return object.hashCode();
    }

    public DecimalFormatProperties clear() {
        return this._clear();
    }

    public DecimalFormatProperties clone() {
        try {
            return (DecimalFormatProperties)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new UnsupportedOperationException(cloneNotSupportedException);
        }
    }

    public DecimalFormatProperties copyFrom(DecimalFormatProperties decimalFormatProperties) {
        return this._copyFrom(decimalFormatProperties);
    }

    public boolean equals(Object object) {
        if (object == null) {
            return true;
        }
        if (this == object) {
            return false;
        }
        if (!(object instanceof DecimalFormatProperties)) {
            return true;
        }
        return this._equals((DecimalFormatProperties)object);
    }

    public Map<String, Map<String, String>> getCompactCustomData() {
        return this.compactCustomData;
    }

    public CompactDecimalFormat.CompactStyle getCompactStyle() {
        return this.compactStyle;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public CurrencyPluralInfo getCurrencyPluralInfo() {
        return this.currencyPluralInfo;
    }

    public Currency.CurrencyUsage getCurrencyUsage() {
        return this.currencyUsage;
    }

    public boolean getDecimalPatternMatchRequired() {
        return this.decimalPatternMatchRequired;
    }

    public boolean getDecimalSeparatorAlwaysShown() {
        return this.decimalSeparatorAlwaysShown;
    }

    public boolean getExponentSignAlwaysShown() {
        return this.exponentSignAlwaysShown;
    }

    public int getFormatWidth() {
        return this.formatWidth;
    }

    public int getGroupingSize() {
        return this.groupingSize;
    }

    public boolean getGroupingUsed() {
        return this.groupingUsed;
    }

    public int getMagnitudeMultiplier() {
        return this.magnitudeMultiplier;
    }

    public MathContext getMathContext() {
        return this.mathContext;
    }

    public int getMaximumFractionDigits() {
        return this.maximumFractionDigits;
    }

    public int getMaximumIntegerDigits() {
        return this.maximumIntegerDigits;
    }

    public int getMaximumSignificantDigits() {
        return this.maximumSignificantDigits;
    }

    public int getMinimumExponentDigits() {
        return this.minimumExponentDigits;
    }

    public int getMinimumFractionDigits() {
        return this.minimumFractionDigits;
    }

    public int getMinimumGroupingDigits() {
        return this.minimumGroupingDigits;
    }

    public int getMinimumIntegerDigits() {
        return this.minimumIntegerDigits;
    }

    public int getMinimumSignificantDigits() {
        return this.minimumSignificantDigits;
    }

    public BigDecimal getMultiplier() {
        return this.multiplier;
    }

    public String getNegativePrefix() {
        return this.negativePrefix;
    }

    public String getNegativePrefixPattern() {
        return this.negativePrefixPattern;
    }

    public String getNegativeSuffix() {
        return this.negativeSuffix;
    }

    public String getNegativeSuffixPattern() {
        return this.negativeSuffixPattern;
    }

    public Padder.PadPosition getPadPosition() {
        return this.padPosition;
    }

    public String getPadString() {
        return this.padString;
    }

    public boolean getParseCaseSensitive() {
        return this.parseCaseSensitive;
    }

    public boolean getParseIntegerOnly() {
        return this.parseIntegerOnly;
    }

    public ParseMode getParseMode() {
        return this.parseMode;
    }

    public boolean getParseNoExponent() {
        return this.parseNoExponent;
    }

    public boolean getParseToBigDecimal() {
        return this.parseToBigDecimal;
    }

    public PluralRules getPluralRules() {
        return this.pluralRules;
    }

    public String getPositivePrefix() {
        return this.positivePrefix;
    }

    public String getPositivePrefixPattern() {
        return this.positivePrefixPattern;
    }

    public String getPositiveSuffix() {
        return this.positiveSuffix;
    }

    public String getPositiveSuffixPattern() {
        return this.positiveSuffixPattern;
    }

    public BigDecimal getRoundingIncrement() {
        return this.roundingIncrement;
    }

    public RoundingMode getRoundingMode() {
        return this.roundingMode;
    }

    public int getSecondaryGroupingSize() {
        return this.secondaryGroupingSize;
    }

    public boolean getSignAlwaysShown() {
        return this.signAlwaysShown;
    }

    public int hashCode() {
        return this._hashCode();
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        this.readObjectImpl(objectInputStream);
    }

    void readObjectImpl(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.clear();
        objectInputStream.readInt();
        int n = objectInputStream.readInt();
        for (int i = 0; i < n; ++i) {
            String string = (String)objectInputStream.readObject();
            Object object = objectInputStream.readObject();
            Field field = null;
            try {
                field = DecimalFormatProperties.class.getDeclaredField(string);
            } catch (NoSuchFieldException noSuchFieldException) {
                continue;
            } catch (SecurityException securityException) {
                throw new AssertionError((Object)securityException);
            }
            try {
                field.set(this, object);
                continue;
            } catch (IllegalArgumentException illegalArgumentException) {
                throw new AssertionError((Object)illegalArgumentException);
            } catch (IllegalAccessException illegalAccessException) {
                throw new AssertionError((Object)illegalAccessException);
            }
        }
    }

    public DecimalFormatProperties setCompactCustomData(Map<String, Map<String, String>> map) {
        this.compactCustomData = map;
        return this;
    }

    public DecimalFormatProperties setCompactStyle(CompactDecimalFormat.CompactStyle compactStyle) {
        this.compactStyle = compactStyle;
        return this;
    }

    public DecimalFormatProperties setCurrency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public DecimalFormatProperties setCurrencyPluralInfo(CurrencyPluralInfo currencyPluralInfo) {
        if (currencyPluralInfo != null) {
            currencyPluralInfo = (CurrencyPluralInfo)currencyPluralInfo.clone();
        }
        this.currencyPluralInfo = currencyPluralInfo;
        return this;
    }

    public DecimalFormatProperties setCurrencyUsage(Currency.CurrencyUsage currencyUsage) {
        this.currencyUsage = currencyUsage;
        return this;
    }

    public DecimalFormatProperties setDecimalPatternMatchRequired(boolean bl) {
        this.decimalPatternMatchRequired = bl;
        return this;
    }

    public DecimalFormatProperties setDecimalSeparatorAlwaysShown(boolean bl) {
        this.decimalSeparatorAlwaysShown = bl;
        return this;
    }

    public DecimalFormatProperties setExponentSignAlwaysShown(boolean bl) {
        this.exponentSignAlwaysShown = bl;
        return this;
    }

    public DecimalFormatProperties setFormatWidth(int n) {
        this.formatWidth = n;
        return this;
    }

    public DecimalFormatProperties setGroupingSize(int n) {
        this.groupingSize = n;
        return this;
    }

    public DecimalFormatProperties setGroupingUsed(boolean bl) {
        this.groupingUsed = bl;
        return this;
    }

    public DecimalFormatProperties setMagnitudeMultiplier(int n) {
        this.magnitudeMultiplier = n;
        return this;
    }

    public DecimalFormatProperties setMathContext(MathContext mathContext) {
        this.mathContext = mathContext;
        return this;
    }

    public DecimalFormatProperties setMaximumFractionDigits(int n) {
        this.maximumFractionDigits = n;
        return this;
    }

    public DecimalFormatProperties setMaximumIntegerDigits(int n) {
        this.maximumIntegerDigits = n;
        return this;
    }

    public DecimalFormatProperties setMaximumSignificantDigits(int n) {
        this.maximumSignificantDigits = n;
        return this;
    }

    public DecimalFormatProperties setMinimumExponentDigits(int n) {
        this.minimumExponentDigits = n;
        return this;
    }

    public DecimalFormatProperties setMinimumFractionDigits(int n) {
        this.minimumFractionDigits = n;
        return this;
    }

    public DecimalFormatProperties setMinimumGroupingDigits(int n) {
        this.minimumGroupingDigits = n;
        return this;
    }

    public DecimalFormatProperties setMinimumIntegerDigits(int n) {
        this.minimumIntegerDigits = n;
        return this;
    }

    public DecimalFormatProperties setMinimumSignificantDigits(int n) {
        this.minimumSignificantDigits = n;
        return this;
    }

    public DecimalFormatProperties setMultiplier(BigDecimal bigDecimal) {
        this.multiplier = bigDecimal;
        return this;
    }

    public DecimalFormatProperties setNegativePrefix(String string) {
        this.negativePrefix = string;
        return this;
    }

    public DecimalFormatProperties setNegativePrefixPattern(String string) {
        this.negativePrefixPattern = string;
        return this;
    }

    public DecimalFormatProperties setNegativeSuffix(String string) {
        this.negativeSuffix = string;
        return this;
    }

    public DecimalFormatProperties setNegativeSuffixPattern(String string) {
        this.negativeSuffixPattern = string;
        return this;
    }

    public DecimalFormatProperties setPadPosition(Padder.PadPosition padPosition) {
        this.padPosition = padPosition;
        return this;
    }

    public DecimalFormatProperties setPadString(String string) {
        this.padString = string;
        return this;
    }

    public DecimalFormatProperties setParseCaseSensitive(boolean bl) {
        this.parseCaseSensitive = bl;
        return this;
    }

    public DecimalFormatProperties setParseIntegerOnly(boolean bl) {
        this.parseIntegerOnly = bl;
        return this;
    }

    public DecimalFormatProperties setParseMode(ParseMode parseMode) {
        this.parseMode = parseMode;
        return this;
    }

    public DecimalFormatProperties setParseNoExponent(boolean bl) {
        this.parseNoExponent = bl;
        return this;
    }

    public DecimalFormatProperties setParseToBigDecimal(boolean bl) {
        this.parseToBigDecimal = bl;
        return this;
    }

    public DecimalFormatProperties setPluralRules(PluralRules pluralRules) {
        this.pluralRules = pluralRules;
        return this;
    }

    public DecimalFormatProperties setPositivePrefix(String string) {
        this.positivePrefix = string;
        return this;
    }

    public DecimalFormatProperties setPositivePrefixPattern(String string) {
        this.positivePrefixPattern = string;
        return this;
    }

    public DecimalFormatProperties setPositiveSuffix(String string) {
        this.positiveSuffix = string;
        return this;
    }

    public DecimalFormatProperties setPositiveSuffixPattern(String string) {
        this.positiveSuffixPattern = string;
        return this;
    }

    public DecimalFormatProperties setRoundingIncrement(BigDecimal bigDecimal) {
        this.roundingIncrement = bigDecimal;
        return this;
    }

    public DecimalFormatProperties setRoundingMode(RoundingMode roundingMode) {
        this.roundingMode = roundingMode;
        return this;
    }

    public DecimalFormatProperties setSecondaryGroupingSize(int n) {
        this.secondaryGroupingSize = n;
        return this;
    }

    public DecimalFormatProperties setSignAlwaysShown(boolean bl) {
        this.signAlwaysShown = bl;
        return this;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<Properties");
        this.toStringBare(stringBuilder);
        stringBuilder.append(">");
        return stringBuilder.toString();
    }

    public void toStringBare(StringBuilder stringBuilder) {
        Field[] fieldArray;
        for (Field field : fieldArray = DecimalFormatProperties.class.getDeclaredFields()) {
            Object object;
            Object object2;
            try {
                object2 = field.get(this);
                object = field.get(DEFAULT);
            } catch (IllegalArgumentException illegalArgumentException) {
                illegalArgumentException.printStackTrace();
                continue;
            } catch (IllegalAccessException illegalAccessException) {
                illegalAccessException.printStackTrace();
                continue;
            }
            if (object2 == null && object == null) continue;
            if (object2 == null || object == null) {
                stringBuilder.append(" " + field.getName() + ":" + object2);
                continue;
            }
            if (object2.equals(object)) continue;
            stringBuilder.append(" " + field.getName() + ":" + object2);
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        this.writeObjectImpl(objectOutputStream);
    }

    void writeObjectImpl(ObjectOutputStream objectOutputStream) throws IOException {
        Field field;
        Field[] fieldArray;
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(0);
        ArrayList<Field> arrayList = new ArrayList<Field>();
        ArrayList<Object> arrayList2 = new ArrayList<Object>();
        Field[] fieldArray2 = fieldArray = DecimalFormatProperties.class.getDeclaredFields();
        int n = fieldArray2.length;
        for (int i = 0; i < n; ++i) {
            field = fieldArray2[i];
            if (Modifier.isStatic(field.getModifiers())) continue;
            try {
                Object object;
                Object object2 = field.get(this);
                if (object2 == null || object2.equals(object = field.get(DEFAULT))) continue;
                arrayList.add(field);
                arrayList2.add(object2);
                continue;
            } catch (IllegalArgumentException illegalArgumentException) {
                throw new AssertionError((Object)illegalArgumentException);
            } catch (IllegalAccessException illegalAccessException) {
                throw new AssertionError((Object)illegalAccessException);
            }
        }
        int n2 = arrayList.size();
        objectOutputStream.writeInt(n2);
        for (n = 0; n < n2; ++n) {
            Field field2 = (Field)arrayList.get(n);
            field = arrayList2.get(n);
            objectOutputStream.writeObject(field2.getName());
            objectOutputStream.writeObject(field);
        }
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }

    public static enum ParseMode {
        LENIENT,
        STRICT,
        JAVA_COMPATIBILITY;

    }
}

