package com.ibm.icu.impl.number;

import com.ibm.icu.util.*;
import com.ibm.icu.text.*;
import java.math.*;
import java.io.*;
import java.util.*;
import java.lang.reflect.*;

public class DecimalFormatProperties implements Cloneable, Serializable
{
    private static final DecimalFormatProperties DEFAULT;
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
    
    private DecimalFormatProperties _copyFrom(final DecimalFormatProperties other) {
        this.compactCustomData = other.compactCustomData;
        this.compactStyle = other.compactStyle;
        this.currency = other.currency;
        this.currencyPluralInfo = other.currencyPluralInfo;
        this.currencyUsage = other.currencyUsage;
        this.decimalPatternMatchRequired = other.decimalPatternMatchRequired;
        this.decimalSeparatorAlwaysShown = other.decimalSeparatorAlwaysShown;
        this.exponentSignAlwaysShown = other.exponentSignAlwaysShown;
        this.formatWidth = other.formatWidth;
        this.groupingSize = other.groupingSize;
        this.groupingUsed = other.groupingUsed;
        this.magnitudeMultiplier = other.magnitudeMultiplier;
        this.mathContext = other.mathContext;
        this.maximumFractionDigits = other.maximumFractionDigits;
        this.maximumIntegerDigits = other.maximumIntegerDigits;
        this.maximumSignificantDigits = other.maximumSignificantDigits;
        this.minimumExponentDigits = other.minimumExponentDigits;
        this.minimumFractionDigits = other.minimumFractionDigits;
        this.minimumGroupingDigits = other.minimumGroupingDigits;
        this.minimumIntegerDigits = other.minimumIntegerDigits;
        this.minimumSignificantDigits = other.minimumSignificantDigits;
        this.multiplier = other.multiplier;
        this.negativePrefix = other.negativePrefix;
        this.negativePrefixPattern = other.negativePrefixPattern;
        this.negativeSuffix = other.negativeSuffix;
        this.negativeSuffixPattern = other.negativeSuffixPattern;
        this.padPosition = other.padPosition;
        this.padString = other.padString;
        this.parseCaseSensitive = other.parseCaseSensitive;
        this.parseIntegerOnly = other.parseIntegerOnly;
        this.parseMode = other.parseMode;
        this.parseNoExponent = other.parseNoExponent;
        this.parseToBigDecimal = other.parseToBigDecimal;
        this.pluralRules = other.pluralRules;
        this.positivePrefix = other.positivePrefix;
        this.positivePrefixPattern = other.positivePrefixPattern;
        this.positiveSuffix = other.positiveSuffix;
        this.positiveSuffixPattern = other.positiveSuffixPattern;
        this.roundingIncrement = other.roundingIncrement;
        this.roundingMode = other.roundingMode;
        this.secondaryGroupingSize = other.secondaryGroupingSize;
        this.signAlwaysShown = other.signAlwaysShown;
        return this;
    }
    
    private boolean _equals(final DecimalFormatProperties other) {
        boolean eq = true;
        eq = (eq && this._equalsHelper(this.compactCustomData, other.compactCustomData));
        eq = (eq && this._equalsHelper(this.compactStyle, other.compactStyle));
        eq = (eq && this._equalsHelper(this.currency, other.currency));
        eq = (eq && this._equalsHelper(this.currencyPluralInfo, other.currencyPluralInfo));
        eq = (eq && this._equalsHelper(this.currencyUsage, other.currencyUsage));
        eq = (eq && this._equalsHelper(this.decimalPatternMatchRequired, other.decimalPatternMatchRequired));
        eq = (eq && this._equalsHelper(this.decimalSeparatorAlwaysShown, other.decimalSeparatorAlwaysShown));
        eq = (eq && this._equalsHelper(this.exponentSignAlwaysShown, other.exponentSignAlwaysShown));
        eq = (eq && this._equalsHelper(this.formatWidth, other.formatWidth));
        eq = (eq && this._equalsHelper(this.groupingSize, other.groupingSize));
        eq = (eq && this._equalsHelper(this.groupingUsed, other.groupingUsed));
        eq = (eq && this._equalsHelper(this.magnitudeMultiplier, other.magnitudeMultiplier));
        eq = (eq && this._equalsHelper(this.mathContext, other.mathContext));
        eq = (eq && this._equalsHelper(this.maximumFractionDigits, other.maximumFractionDigits));
        eq = (eq && this._equalsHelper(this.maximumIntegerDigits, other.maximumIntegerDigits));
        eq = (eq && this._equalsHelper(this.maximumSignificantDigits, other.maximumSignificantDigits));
        eq = (eq && this._equalsHelper(this.minimumExponentDigits, other.minimumExponentDigits));
        eq = (eq && this._equalsHelper(this.minimumFractionDigits, other.minimumFractionDigits));
        eq = (eq && this._equalsHelper(this.minimumGroupingDigits, other.minimumGroupingDigits));
        eq = (eq && this._equalsHelper(this.minimumIntegerDigits, other.minimumIntegerDigits));
        eq = (eq && this._equalsHelper(this.minimumSignificantDigits, other.minimumSignificantDigits));
        eq = (eq && this._equalsHelper(this.multiplier, other.multiplier));
        eq = (eq && this._equalsHelper(this.negativePrefix, other.negativePrefix));
        eq = (eq && this._equalsHelper(this.negativePrefixPattern, other.negativePrefixPattern));
        eq = (eq && this._equalsHelper(this.negativeSuffix, other.negativeSuffix));
        eq = (eq && this._equalsHelper(this.negativeSuffixPattern, other.negativeSuffixPattern));
        eq = (eq && this._equalsHelper(this.padPosition, other.padPosition));
        eq = (eq && this._equalsHelper(this.padString, other.padString));
        eq = (eq && this._equalsHelper(this.parseCaseSensitive, other.parseCaseSensitive));
        eq = (eq && this._equalsHelper(this.parseIntegerOnly, other.parseIntegerOnly));
        eq = (eq && this._equalsHelper(this.parseMode, other.parseMode));
        eq = (eq && this._equalsHelper(this.parseNoExponent, other.parseNoExponent));
        eq = (eq && this._equalsHelper(this.parseToBigDecimal, other.parseToBigDecimal));
        eq = (eq && this._equalsHelper(this.pluralRules, other.pluralRules));
        eq = (eq && this._equalsHelper(this.positivePrefix, other.positivePrefix));
        eq = (eq && this._equalsHelper(this.positivePrefixPattern, other.positivePrefixPattern));
        eq = (eq && this._equalsHelper(this.positiveSuffix, other.positiveSuffix));
        eq = (eq && this._equalsHelper(this.positiveSuffixPattern, other.positiveSuffixPattern));
        eq = (eq && this._equalsHelper(this.roundingIncrement, other.roundingIncrement));
        eq = (eq && this._equalsHelper(this.roundingMode, other.roundingMode));
        eq = (eq && this._equalsHelper(this.secondaryGroupingSize, other.secondaryGroupingSize));
        eq = (eq && this._equalsHelper(this.signAlwaysShown, other.signAlwaysShown));
        return eq;
    }
    
    private boolean _equalsHelper(final boolean mine, final boolean theirs) {
        return mine == theirs;
    }
    
    private boolean _equalsHelper(final int mine, final int theirs) {
        return mine == theirs;
    }
    
    private boolean _equalsHelper(final Object mine, final Object theirs) {
        return mine == theirs || (mine != null && mine.equals(theirs));
    }
    
    private int _hashCode() {
        int hashCode = 0;
        hashCode ^= this._hashCodeHelper(this.compactCustomData);
        hashCode ^= this._hashCodeHelper(this.compactStyle);
        hashCode ^= this._hashCodeHelper(this.currency);
        hashCode ^= this._hashCodeHelper(this.currencyPluralInfo);
        hashCode ^= this._hashCodeHelper(this.currencyUsage);
        hashCode ^= this._hashCodeHelper(this.decimalPatternMatchRequired);
        hashCode ^= this._hashCodeHelper(this.decimalSeparatorAlwaysShown);
        hashCode ^= this._hashCodeHelper(this.exponentSignAlwaysShown);
        hashCode ^= this._hashCodeHelper(this.formatWidth);
        hashCode ^= this._hashCodeHelper(this.groupingSize);
        hashCode ^= this._hashCodeHelper(this.groupingUsed);
        hashCode ^= this._hashCodeHelper(this.magnitudeMultiplier);
        hashCode ^= this._hashCodeHelper(this.mathContext);
        hashCode ^= this._hashCodeHelper(this.maximumFractionDigits);
        hashCode ^= this._hashCodeHelper(this.maximumIntegerDigits);
        hashCode ^= this._hashCodeHelper(this.maximumSignificantDigits);
        hashCode ^= this._hashCodeHelper(this.minimumExponentDigits);
        hashCode ^= this._hashCodeHelper(this.minimumFractionDigits);
        hashCode ^= this._hashCodeHelper(this.minimumGroupingDigits);
        hashCode ^= this._hashCodeHelper(this.minimumIntegerDigits);
        hashCode ^= this._hashCodeHelper(this.minimumSignificantDigits);
        hashCode ^= this._hashCodeHelper(this.multiplier);
        hashCode ^= this._hashCodeHelper(this.negativePrefix);
        hashCode ^= this._hashCodeHelper(this.negativePrefixPattern);
        hashCode ^= this._hashCodeHelper(this.negativeSuffix);
        hashCode ^= this._hashCodeHelper(this.negativeSuffixPattern);
        hashCode ^= this._hashCodeHelper(this.padPosition);
        hashCode ^= this._hashCodeHelper(this.padString);
        hashCode ^= this._hashCodeHelper(this.parseCaseSensitive);
        hashCode ^= this._hashCodeHelper(this.parseIntegerOnly);
        hashCode ^= this._hashCodeHelper(this.parseMode);
        hashCode ^= this._hashCodeHelper(this.parseNoExponent);
        hashCode ^= this._hashCodeHelper(this.parseToBigDecimal);
        hashCode ^= this._hashCodeHelper(this.pluralRules);
        hashCode ^= this._hashCodeHelper(this.positivePrefix);
        hashCode ^= this._hashCodeHelper(this.positivePrefixPattern);
        hashCode ^= this._hashCodeHelper(this.positiveSuffix);
        hashCode ^= this._hashCodeHelper(this.positiveSuffixPattern);
        hashCode ^= this._hashCodeHelper(this.roundingIncrement);
        hashCode ^= this._hashCodeHelper(this.roundingMode);
        hashCode ^= this._hashCodeHelper(this.secondaryGroupingSize);
        hashCode ^= this._hashCodeHelper(this.signAlwaysShown);
        return hashCode;
    }
    
    private int _hashCodeHelper(final boolean value) {
        return value ? 1 : 0;
    }
    
    private int _hashCodeHelper(final int value) {
        return value * 13;
    }
    
    private int _hashCodeHelper(final Object value) {
        if (value == null) {
            return 0;
        }
        return value.hashCode();
    }
    
    public DecimalFormatProperties clear() {
        return this._clear();
    }
    
    public DecimalFormatProperties clone() {
        try {
            return (DecimalFormatProperties)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new UnsupportedOperationException(e);
        }
    }
    
    public DecimalFormatProperties copyFrom(final DecimalFormatProperties other) {
        return this._copyFrom(other);
    }
    
    @Override
    public boolean equals(final Object other) {
        return other != null && (this == other || (other instanceof DecimalFormatProperties && this._equals((DecimalFormatProperties)other)));
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
    
    @Override
    public int hashCode() {
        return this._hashCode();
    }
    
    private void readObject(final ObjectInputStream ois) throws IOException, ClassNotFoundException {
        this.readObjectImpl(ois);
    }
    
    void readObjectImpl(final ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        this.clear();
        ois.readInt();
        for (int count = ois.readInt(), i = 0; i < count; ++i) {
            final String name = (String)ois.readObject();
            final Object value = ois.readObject();
            Field field = null;
            try {
                field = DecimalFormatProperties.class.getDeclaredField(name);
            }
            catch (NoSuchFieldException e4) {
                continue;
            }
            catch (SecurityException e) {
                throw new AssertionError((Object)e);
            }
            try {
                field.set(this, value);
            }
            catch (IllegalArgumentException e2) {
                throw new AssertionError((Object)e2);
            }
            catch (IllegalAccessException e3) {
                throw new AssertionError((Object)e3);
            }
        }
    }
    
    public DecimalFormatProperties setCompactCustomData(final Map<String, Map<String, String>> compactCustomData) {
        this.compactCustomData = compactCustomData;
        return this;
    }
    
    public DecimalFormatProperties setCompactStyle(final CompactDecimalFormat.CompactStyle compactStyle) {
        this.compactStyle = compactStyle;
        return this;
    }
    
    public DecimalFormatProperties setCurrency(final Currency currency) {
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
    
    public DecimalFormatProperties setCurrencyUsage(final Currency.CurrencyUsage currencyUsage) {
        this.currencyUsage = currencyUsage;
        return this;
    }
    
    public DecimalFormatProperties setDecimalPatternMatchRequired(final boolean decimalPatternMatchRequired) {
        this.decimalPatternMatchRequired = decimalPatternMatchRequired;
        return this;
    }
    
    public DecimalFormatProperties setDecimalSeparatorAlwaysShown(final boolean alwaysShowDecimal) {
        this.decimalSeparatorAlwaysShown = alwaysShowDecimal;
        return this;
    }
    
    public DecimalFormatProperties setExponentSignAlwaysShown(final boolean exponentSignAlwaysShown) {
        this.exponentSignAlwaysShown = exponentSignAlwaysShown;
        return this;
    }
    
    public DecimalFormatProperties setFormatWidth(final int paddingWidth) {
        this.formatWidth = paddingWidth;
        return this;
    }
    
    public DecimalFormatProperties setGroupingSize(final int groupingSize) {
        this.groupingSize = groupingSize;
        return this;
    }
    
    public DecimalFormatProperties setGroupingUsed(final boolean groupingUsed) {
        this.groupingUsed = groupingUsed;
        return this;
    }
    
    public DecimalFormatProperties setMagnitudeMultiplier(final int magnitudeMultiplier) {
        this.magnitudeMultiplier = magnitudeMultiplier;
        return this;
    }
    
    public DecimalFormatProperties setMathContext(final MathContext mathContext) {
        this.mathContext = mathContext;
        return this;
    }
    
    public DecimalFormatProperties setMaximumFractionDigits(final int maximumFractionDigits) {
        this.maximumFractionDigits = maximumFractionDigits;
        return this;
    }
    
    public DecimalFormatProperties setMaximumIntegerDigits(final int maximumIntegerDigits) {
        this.maximumIntegerDigits = maximumIntegerDigits;
        return this;
    }
    
    public DecimalFormatProperties setMaximumSignificantDigits(final int maximumSignificantDigits) {
        this.maximumSignificantDigits = maximumSignificantDigits;
        return this;
    }
    
    public DecimalFormatProperties setMinimumExponentDigits(final int minimumExponentDigits) {
        this.minimumExponentDigits = minimumExponentDigits;
        return this;
    }
    
    public DecimalFormatProperties setMinimumFractionDigits(final int minimumFractionDigits) {
        this.minimumFractionDigits = minimumFractionDigits;
        return this;
    }
    
    public DecimalFormatProperties setMinimumGroupingDigits(final int minimumGroupingDigits) {
        this.minimumGroupingDigits = minimumGroupingDigits;
        return this;
    }
    
    public DecimalFormatProperties setMinimumIntegerDigits(final int minimumIntegerDigits) {
        this.minimumIntegerDigits = minimumIntegerDigits;
        return this;
    }
    
    public DecimalFormatProperties setMinimumSignificantDigits(final int minimumSignificantDigits) {
        this.minimumSignificantDigits = minimumSignificantDigits;
        return this;
    }
    
    public DecimalFormatProperties setMultiplier(final BigDecimal multiplier) {
        this.multiplier = multiplier;
        return this;
    }
    
    public DecimalFormatProperties setNegativePrefix(final String negativePrefix) {
        this.negativePrefix = negativePrefix;
        return this;
    }
    
    public DecimalFormatProperties setNegativePrefixPattern(final String negativePrefixPattern) {
        this.negativePrefixPattern = negativePrefixPattern;
        return this;
    }
    
    public DecimalFormatProperties setNegativeSuffix(final String negativeSuffix) {
        this.negativeSuffix = negativeSuffix;
        return this;
    }
    
    public DecimalFormatProperties setNegativeSuffixPattern(final String negativeSuffixPattern) {
        this.negativeSuffixPattern = negativeSuffixPattern;
        return this;
    }
    
    public DecimalFormatProperties setPadPosition(final Padder.PadPosition paddingLocation) {
        this.padPosition = paddingLocation;
        return this;
    }
    
    public DecimalFormatProperties setPadString(final String paddingString) {
        this.padString = paddingString;
        return this;
    }
    
    public DecimalFormatProperties setParseCaseSensitive(final boolean parseCaseSensitive) {
        this.parseCaseSensitive = parseCaseSensitive;
        return this;
    }
    
    public DecimalFormatProperties setParseIntegerOnly(final boolean parseIntegerOnly) {
        this.parseIntegerOnly = parseIntegerOnly;
        return this;
    }
    
    public DecimalFormatProperties setParseMode(final ParseMode parseMode) {
        this.parseMode = parseMode;
        return this;
    }
    
    public DecimalFormatProperties setParseNoExponent(final boolean parseNoExponent) {
        this.parseNoExponent = parseNoExponent;
        return this;
    }
    
    public DecimalFormatProperties setParseToBigDecimal(final boolean parseToBigDecimal) {
        this.parseToBigDecimal = parseToBigDecimal;
        return this;
    }
    
    public DecimalFormatProperties setPluralRules(final PluralRules pluralRules) {
        this.pluralRules = pluralRules;
        return this;
    }
    
    public DecimalFormatProperties setPositivePrefix(final String positivePrefix) {
        this.positivePrefix = positivePrefix;
        return this;
    }
    
    public DecimalFormatProperties setPositivePrefixPattern(final String positivePrefixPattern) {
        this.positivePrefixPattern = positivePrefixPattern;
        return this;
    }
    
    public DecimalFormatProperties setPositiveSuffix(final String positiveSuffix) {
        this.positiveSuffix = positiveSuffix;
        return this;
    }
    
    public DecimalFormatProperties setPositiveSuffixPattern(final String positiveSuffixPattern) {
        this.positiveSuffixPattern = positiveSuffixPattern;
        return this;
    }
    
    public DecimalFormatProperties setRoundingIncrement(final BigDecimal roundingIncrement) {
        this.roundingIncrement = roundingIncrement;
        return this;
    }
    
    public DecimalFormatProperties setRoundingMode(final RoundingMode roundingMode) {
        this.roundingMode = roundingMode;
        return this;
    }
    
    public DecimalFormatProperties setSecondaryGroupingSize(final int secondaryGroupingSize) {
        this.secondaryGroupingSize = secondaryGroupingSize;
        return this;
    }
    
    public DecimalFormatProperties setSignAlwaysShown(final boolean signAlwaysShown) {
        this.signAlwaysShown = signAlwaysShown;
        return this;
    }
    
    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        result.append("<Properties");
        this.toStringBare(result);
        result.append(">");
        return result.toString();
    }
    
    public void toStringBare(final StringBuilder result) {
        final Field[] declaredFields;
        final Field[] fields = declaredFields = DecimalFormatProperties.class.getDeclaredFields();
        for (final Field field : declaredFields) {
            Label_0181: {
                Object myValue;
                Object defaultValue;
                try {
                    myValue = field.get(this);
                    defaultValue = field.get(DecimalFormatProperties.DEFAULT);
                }
                catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    break Label_0181;
                }
                catch (IllegalAccessException e2) {
                    e2.printStackTrace();
                    break Label_0181;
                }
                if (myValue != null || defaultValue != null) {
                    if (myValue == null || defaultValue == null) {
                        result.append(" " + field.getName() + ":" + myValue);
                    }
                    else if (!myValue.equals(defaultValue)) {
                        result.append(" " + field.getName() + ":" + myValue);
                    }
                }
            }
        }
    }
    
    private void writeObject(final ObjectOutputStream oos) throws IOException {
        this.writeObjectImpl(oos);
    }
    
    void writeObjectImpl(final ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeInt(0);
        final ArrayList<Field> fieldsToSerialize = new ArrayList<Field>();
        final ArrayList<Object> valuesToSerialize = new ArrayList<Object>();
        final Field[] declaredFields;
        final Field[] fields = declaredFields = DecimalFormatProperties.class.getDeclaredFields();
        for (final Field field : declaredFields) {
            if (!Modifier.isStatic(field.getModifiers())) {
                try {
                    final Object myValue = field.get(this);
                    if (myValue != null) {
                        final Object defaultValue = field.get(DecimalFormatProperties.DEFAULT);
                        if (!myValue.equals(defaultValue)) {
                            fieldsToSerialize.add(field);
                            valuesToSerialize.add(myValue);
                        }
                    }
                }
                catch (IllegalArgumentException e) {
                    throw new AssertionError((Object)e);
                }
                catch (IllegalAccessException e2) {
                    throw new AssertionError((Object)e2);
                }
            }
        }
        final int count = fieldsToSerialize.size();
        oos.writeInt(count);
        for (int i = 0; i < count; ++i) {
            final Field field2 = fieldsToSerialize.get(i);
            final Object value = valuesToSerialize.get(i);
            oos.writeObject(field2.getName());
            oos.writeObject(value);
        }
    }
    
    static {
        DEFAULT = new DecimalFormatProperties();
    }
    
    public enum ParseMode
    {
        LENIENT, 
        STRICT;
    }
}
