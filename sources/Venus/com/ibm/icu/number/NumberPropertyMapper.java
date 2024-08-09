/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.number;

import com.ibm.icu.impl.number.AffixPatternProvider;
import com.ibm.icu.impl.number.CurrencyPluralInfoAffixProvider;
import com.ibm.icu.impl.number.CustomSymbolCurrency;
import com.ibm.icu.impl.number.DecimalFormatProperties;
import com.ibm.icu.impl.number.Grouper;
import com.ibm.icu.impl.number.MacroProps;
import com.ibm.icu.impl.number.Padder;
import com.ibm.icu.impl.number.PatternStringParser;
import com.ibm.icu.impl.number.PatternStringUtils;
import com.ibm.icu.impl.number.PropertiesAffixPatternProvider;
import com.ibm.icu.impl.number.RoundingUtils;
import com.ibm.icu.number.CompactNotation;
import com.ibm.icu.number.CurrencyPrecision;
import com.ibm.icu.number.FractionPrecision;
import com.ibm.icu.number.IntegerWidth;
import com.ibm.icu.number.Notation;
import com.ibm.icu.number.NumberFormatter;
import com.ibm.icu.number.Precision;
import com.ibm.icu.number.ScientificNotation;
import com.ibm.icu.number.UnlocalizedNumberFormatter;
import com.ibm.icu.text.CompactDecimalFormat;
import com.ibm.icu.text.DecimalFormatSymbols;
import com.ibm.icu.text.PluralRules;
import com.ibm.icu.util.Currency;
import com.ibm.icu.util.ULocale;
import java.math.BigDecimal;
import java.math.MathContext;

final class NumberPropertyMapper {
    NumberPropertyMapper() {
    }

    public static UnlocalizedNumberFormatter create(DecimalFormatProperties decimalFormatProperties, DecimalFormatSymbols decimalFormatSymbols) {
        MacroProps macroProps = NumberPropertyMapper.oldToNew(decimalFormatProperties, decimalFormatSymbols, null);
        return (UnlocalizedNumberFormatter)NumberFormatter.with().macros(macroProps);
    }

    public static UnlocalizedNumberFormatter create(DecimalFormatProperties decimalFormatProperties, DecimalFormatSymbols decimalFormatSymbols, DecimalFormatProperties decimalFormatProperties2) {
        MacroProps macroProps = NumberPropertyMapper.oldToNew(decimalFormatProperties, decimalFormatSymbols, decimalFormatProperties2);
        return (UnlocalizedNumberFormatter)NumberFormatter.with().macros(macroProps);
    }

    public static UnlocalizedNumberFormatter create(String string, DecimalFormatSymbols decimalFormatSymbols) {
        DecimalFormatProperties decimalFormatProperties = PatternStringParser.parseToProperties(string);
        return NumberPropertyMapper.create(decimalFormatProperties, decimalFormatSymbols);
    }

    public static MacroProps oldToNew(DecimalFormatProperties decimalFormatProperties, DecimalFormatSymbols decimalFormatSymbols, DecimalFormatProperties decimalFormatProperties2) {
        int n;
        int n2;
        int n3;
        int n4;
        boolean bl;
        boolean bl2;
        MacroProps macroProps = new MacroProps();
        ULocale uLocale = decimalFormatSymbols.getULocale();
        macroProps.symbols = decimalFormatSymbols;
        PluralRules pluralRules = decimalFormatProperties.getPluralRules();
        if (pluralRules == null && decimalFormatProperties.getCurrencyPluralInfo() != null) {
            pluralRules = decimalFormatProperties.getCurrencyPluralInfo().getPluralRules();
        }
        macroProps.rules = pluralRules;
        AffixPatternProvider affixPatternProvider = decimalFormatProperties.getCurrencyPluralInfo() == null ? new PropertiesAffixPatternProvider(decimalFormatProperties) : new CurrencyPluralInfoAffixProvider(decimalFormatProperties.getCurrencyPluralInfo(), decimalFormatProperties);
        macroProps.affixProvider = affixPatternProvider;
        boolean bl3 = decimalFormatProperties.getCurrency() != null || decimalFormatProperties.getCurrencyPluralInfo() != null || decimalFormatProperties.getCurrencyUsage() != null || affixPatternProvider.hasCurrencySign();
        Currency currency = CustomSymbolCurrency.resolve(decimalFormatProperties.getCurrency(), uLocale, decimalFormatSymbols);
        Currency.CurrencyUsage currencyUsage = decimalFormatProperties.getCurrencyUsage();
        boolean bl4 = bl2 = currencyUsage != null;
        if (!bl2) {
            currencyUsage = Currency.CurrencyUsage.STANDARD;
        }
        if (bl3) {
            macroProps.unit = currency;
        }
        int n5 = decimalFormatProperties.getMaximumIntegerDigits();
        int n6 = decimalFormatProperties.getMinimumIntegerDigits();
        int n7 = decimalFormatProperties.getMaximumFractionDigits();
        int n8 = decimalFormatProperties.getMinimumFractionDigits();
        int n9 = decimalFormatProperties.getMinimumSignificantDigits();
        int n10 = decimalFormatProperties.getMaximumSignificantDigits();
        BigDecimal bigDecimal = decimalFormatProperties.getRoundingIncrement();
        MathContext mathContext = RoundingUtils.getMathContextOrUnlimited(decimalFormatProperties);
        boolean bl5 = n8 != -1 || n7 != -1;
        boolean bl6 = bl = n9 != -1 || n10 != -1;
        if (bl3) {
            if (n8 == -1 && n7 == -1) {
                n8 = currency.getDefaultFractionDigits(currencyUsage);
                n7 = currency.getDefaultFractionDigits(currencyUsage);
            } else if (n8 == -1) {
                n8 = Math.min(n7, currency.getDefaultFractionDigits(currencyUsage));
            } else if (n7 == -1) {
                n7 = Math.max(n8, currency.getDefaultFractionDigits(currencyUsage));
            }
        }
        if (n6 == 0 && n7 != 0) {
            int n11 = n8 = n8 <= 0 ? 1 : n8;
            n7 = n7 < 0 ? -1 : (n7 < n8 ? n8 : n7);
            n6 = 0;
            n5 = n5 < 0 ? -1 : (n5 > 999 ? -1 : n5);
        } else {
            int n12 = n8 = n8 < 0 ? 0 : n8;
            int n13 = n7 < 0 ? -1 : (n7 = n7 < n8 ? n8 : n7);
            int n14 = n6 <= 0 ? 1 : (n6 = n6 > 999 ? 1 : n6);
            n5 = n5 < 0 ? -1 : (n5 < n6 ? n6 : (n5 > 999 ? -1 : n5));
        }
        Precision precision = null;
        if (bl2) {
            precision = Precision.constructCurrency(currencyUsage).withCurrency(currency);
        } else if (bigDecimal != null) {
            precision = PatternStringUtils.ignoreRoundingIncrement(bigDecimal, n7) ? Precision.constructFraction(n8, n7) : Precision.constructIncrement(bigDecimal);
        } else if (bl) {
            int n15 = n9 < 1 ? 1 : (n9 = n9 > 999 ? 999 : n9);
            n10 = n10 < 0 ? 999 : (n10 < n9 ? n9 : (n10 > 999 ? 999 : n10));
            precision = Precision.constructSignificant(n9, n10);
        } else if (bl5) {
            precision = Precision.constructFraction(n8, n7);
        } else if (bl3) {
            precision = Precision.constructCurrency(currencyUsage);
        }
        if (precision != null) {
            macroProps.precision = precision = precision.withMode(mathContext);
        }
        macroProps.integerWidth = IntegerWidth.zeroFillTo(n6).truncateAt(n5);
        macroProps.grouping = Grouper.forProperties(decimalFormatProperties);
        if (decimalFormatProperties.getFormatWidth() > 0) {
            macroProps.padder = Padder.forProperties(decimalFormatProperties);
        }
        macroProps.decimal = decimalFormatProperties.getDecimalSeparatorAlwaysShown() ? NumberFormatter.DecimalSeparatorDisplay.ALWAYS : NumberFormatter.DecimalSeparatorDisplay.AUTO;
        NumberFormatter.SignDisplay signDisplay = macroProps.sign = decimalFormatProperties.getSignAlwaysShown() ? NumberFormatter.SignDisplay.ALWAYS : NumberFormatter.SignDisplay.AUTO;
        if (decimalFormatProperties.getMinimumExponentDigits() != -1) {
            if (n5 > 8) {
                n5 = n6;
                macroProps.integerWidth = IntegerWidth.zeroFillTo(n6).truncateAt(n5);
            } else if (n5 > n6 && n6 > 1) {
                n6 = 1;
                macroProps.integerWidth = IntegerWidth.zeroFillTo(n6).truncateAt(n5);
            }
            int n16 = n5 < 0 ? -1 : n5;
            macroProps.notation = new ScientificNotation(n16, n16 == n6, decimalFormatProperties.getMinimumExponentDigits(), decimalFormatProperties.getExponentSignAlwaysShown() ? NumberFormatter.SignDisplay.ALWAYS : NumberFormatter.SignDisplay.AUTO);
            if (macroProps.precision instanceof FractionPrecision) {
                n4 = decimalFormatProperties.getMaximumIntegerDigits();
                n3 = decimalFormatProperties.getMinimumIntegerDigits();
                n2 = decimalFormatProperties.getMinimumFractionDigits();
                n = decimalFormatProperties.getMaximumFractionDigits();
                if (n3 == 0 && n == 0) {
                    macroProps.precision = Precision.constructInfinite().withMode(mathContext);
                } else if (n3 == 0 && n2 == 0) {
                    macroProps.precision = Precision.constructSignificant(1, n + 1).withMode(mathContext);
                } else {
                    int n17 = n3 + n;
                    if (n4 > n3 && n3 > 1) {
                        n3 = 1;
                    }
                    int n18 = n3 + n2;
                    macroProps.precision = Precision.constructSignificant(n18, n17).withMode(mathContext);
                }
            }
        }
        if (decimalFormatProperties.getCompactStyle() != null) {
            macroProps.notation = decimalFormatProperties.getCompactCustomData() != null ? new CompactNotation(decimalFormatProperties.getCompactCustomData()) : (decimalFormatProperties.getCompactStyle() == CompactDecimalFormat.CompactStyle.LONG ? Notation.compactLong() : Notation.compactShort());
            macroProps.affixProvider = null;
        }
        macroProps.scale = RoundingUtils.scaleFromProperties(decimalFormatProperties);
        if (decimalFormatProperties2 != null) {
            decimalFormatProperties2.setCurrency(currency);
            decimalFormatProperties2.setMathContext(mathContext);
            decimalFormatProperties2.setRoundingMode(mathContext.getRoundingMode());
            decimalFormatProperties2.setMinimumIntegerDigits(n6);
            decimalFormatProperties2.setMaximumIntegerDigits(n5 == -1 ? Integer.MAX_VALUE : n5);
            Precision precision2 = precision instanceof CurrencyPrecision ? ((CurrencyPrecision)precision).withCurrency(currency) : precision;
            n4 = n8;
            n3 = n7;
            n2 = n9;
            n = n10;
            BigDecimal bigDecimal2 = null;
            if (precision2 instanceof Precision.FractionRounderImpl) {
                n4 = ((Precision.FractionRounderImpl)precision2).minFrac;
                n3 = ((Precision.FractionRounderImpl)precision2).maxFrac;
            } else if (precision2 instanceof Precision.IncrementRounderImpl) {
                bigDecimal2 = ((Precision.IncrementRounderImpl)precision2).increment;
                n4 = bigDecimal2.scale();
                n3 = bigDecimal2.scale();
            } else if (precision2 instanceof Precision.SignificantRounderImpl) {
                n2 = ((Precision.SignificantRounderImpl)precision2).minSig;
                n = ((Precision.SignificantRounderImpl)precision2).maxSig;
            }
            decimalFormatProperties2.setMinimumFractionDigits(n4);
            decimalFormatProperties2.setMaximumFractionDigits(n3);
            decimalFormatProperties2.setMinimumSignificantDigits(n2);
            decimalFormatProperties2.setMaximumSignificantDigits(n);
            decimalFormatProperties2.setRoundingIncrement(bigDecimal2);
        }
        return macroProps;
    }
}

