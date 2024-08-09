/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.number;

import com.ibm.icu.impl.CurrencyData;
import com.ibm.icu.impl.FormattedStringBuilder;
import com.ibm.icu.impl.StandardPlural;
import com.ibm.icu.impl.number.AffixPatternProvider;
import com.ibm.icu.impl.number.CompactData;
import com.ibm.icu.impl.number.ConstantAffixModifier;
import com.ibm.icu.impl.number.DecimalQuantity;
import com.ibm.icu.impl.number.DecimalQuantity_DualStorageBCD;
import com.ibm.icu.impl.number.Grouper;
import com.ibm.icu.impl.number.LongNameHandler;
import com.ibm.icu.impl.number.MacroProps;
import com.ibm.icu.impl.number.MicroProps;
import com.ibm.icu.impl.number.MicroPropsGenerator;
import com.ibm.icu.impl.number.MultiplierFormatHandler;
import com.ibm.icu.impl.number.MutablePatternModifier;
import com.ibm.icu.impl.number.Padder;
import com.ibm.icu.impl.number.PatternStringParser;
import com.ibm.icu.impl.number.RoundingUtils;
import com.ibm.icu.number.CompactNotation;
import com.ibm.icu.number.IntegerWidth;
import com.ibm.icu.number.NumberFormatter;
import com.ibm.icu.number.Precision;
import com.ibm.icu.number.ScientificNotation;
import com.ibm.icu.text.DecimalFormatSymbols;
import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.text.NumberingSystem;
import com.ibm.icu.text.PluralRules;
import com.ibm.icu.util.Currency;
import com.ibm.icu.util.MeasureUnit;

class NumberFormatterImpl {
    private static final Currency DEFAULT_CURRENCY = Currency.getInstance("XXX");
    final MicroProps micros = new MicroProps(true);
    final MicroPropsGenerator microPropsGenerator;

    public NumberFormatterImpl(MacroProps macroProps) {
        this.microPropsGenerator = NumberFormatterImpl.macrosToMicroGenerator(macroProps, this.micros, true);
    }

    public static int formatStatic(MacroProps macroProps, DecimalQuantity decimalQuantity, FormattedStringBuilder formattedStringBuilder) {
        MicroProps microProps = NumberFormatterImpl.preProcessUnsafe(macroProps, decimalQuantity);
        int n = NumberFormatterImpl.writeNumber(microProps, decimalQuantity, formattedStringBuilder, 0);
        n += NumberFormatterImpl.writeAffixes(microProps, formattedStringBuilder, 0, n);
        return n;
    }

    public static int getPrefixSuffixStatic(MacroProps macroProps, byte by, StandardPlural standardPlural, FormattedStringBuilder formattedStringBuilder) {
        MicroProps microProps = new MicroProps(false);
        MicroPropsGenerator microPropsGenerator = NumberFormatterImpl.macrosToMicroGenerator(macroProps, microProps, false);
        return NumberFormatterImpl.getPrefixSuffixImpl(microPropsGenerator, by, formattedStringBuilder);
    }

    public int format(DecimalQuantity decimalQuantity, FormattedStringBuilder formattedStringBuilder) {
        MicroProps microProps = this.preProcess(decimalQuantity);
        int n = NumberFormatterImpl.writeNumber(microProps, decimalQuantity, formattedStringBuilder, 0);
        n += NumberFormatterImpl.writeAffixes(microProps, formattedStringBuilder, 0, n);
        return n;
    }

    public MicroProps preProcess(DecimalQuantity decimalQuantity) {
        MicroProps microProps = this.microPropsGenerator.processQuantity(decimalQuantity);
        microProps.rounder.apply(decimalQuantity);
        if (microProps.integerWidth.maxInt == -1) {
            decimalQuantity.setMinInteger(microProps.integerWidth.minInt);
        } else {
            decimalQuantity.setMinInteger(microProps.integerWidth.minInt);
            decimalQuantity.applyMaxInteger(microProps.integerWidth.maxInt);
        }
        return microProps;
    }

    private static MicroProps preProcessUnsafe(MacroProps macroProps, DecimalQuantity decimalQuantity) {
        MicroProps microProps = new MicroProps(false);
        MicroPropsGenerator microPropsGenerator = NumberFormatterImpl.macrosToMicroGenerator(macroProps, microProps, false);
        microProps = microPropsGenerator.processQuantity(decimalQuantity);
        microProps.rounder.apply(decimalQuantity);
        if (microProps.integerWidth.maxInt == -1) {
            decimalQuantity.setMinInteger(microProps.integerWidth.minInt);
        } else {
            decimalQuantity.setMinInteger(microProps.integerWidth.minInt);
            decimalQuantity.applyMaxInteger(microProps.integerWidth.maxInt);
        }
        return microProps;
    }

    public int getPrefixSuffix(byte by, StandardPlural standardPlural, FormattedStringBuilder formattedStringBuilder) {
        return NumberFormatterImpl.getPrefixSuffixImpl(this.microPropsGenerator, by, formattedStringBuilder);
    }

    private static int getPrefixSuffixImpl(MicroPropsGenerator microPropsGenerator, byte by, FormattedStringBuilder formattedStringBuilder) {
        DecimalQuantity_DualStorageBCD decimalQuantity_DualStorageBCD = new DecimalQuantity_DualStorageBCD(0);
        if (by < 0) {
            decimalQuantity_DualStorageBCD.negate();
        }
        MicroProps microProps = microPropsGenerator.processQuantity(decimalQuantity_DualStorageBCD);
        microProps.modMiddle.apply(formattedStringBuilder, 0, 0);
        return microProps.modMiddle.getPrefixLength();
    }

    public MicroProps getRawMicroProps() {
        return this.micros;
    }

    private static boolean unitIsCurrency(MeasureUnit measureUnit) {
        return measureUnit != null && "currency".equals(measureUnit.getType());
    }

    private static boolean unitIsNoUnit(MeasureUnit measureUnit) {
        return measureUnit == null || "none".equals(measureUnit.getType());
    }

    private static boolean unitIsPercent(MeasureUnit measureUnit) {
        return measureUnit != null && "percent".equals(measureUnit.getSubtype());
    }

    private static boolean unitIsPermille(MeasureUnit measureUnit) {
        return measureUnit != null && "permille".equals(measureUnit.getSubtype());
    }

    private static MicroPropsGenerator macrosToMicroGenerator(MacroProps macroProps, MicroProps microProps, boolean bl) {
        Object object;
        MicroPropsGenerator microPropsGenerator = microProps;
        boolean bl2 = NumberFormatterImpl.unitIsCurrency(macroProps.unit);
        boolean bl3 = NumberFormatterImpl.unitIsNoUnit(macroProps.unit);
        boolean bl4 = NumberFormatterImpl.unitIsPercent(macroProps.unit);
        boolean bl5 = NumberFormatterImpl.unitIsPermille(macroProps.unit);
        boolean bl6 = macroProps.sign == NumberFormatter.SignDisplay.ACCOUNTING || macroProps.sign == NumberFormatter.SignDisplay.ACCOUNTING_ALWAYS || macroProps.sign == NumberFormatter.SignDisplay.ACCOUNTING_EXCEPT_ZERO;
        Currency currency = bl2 ? (Currency)macroProps.unit : DEFAULT_CURRENCY;
        NumberFormatter.UnitWidth unitWidth = NumberFormatter.UnitWidth.SHORT;
        if (macroProps.unitWidth != null) {
            unitWidth = macroProps.unitWidth;
        }
        boolean bl7 = !bl2 && !bl3 && (unitWidth == NumberFormatter.UnitWidth.FULL_NAME || !bl4 && !bl5);
        PluralRules pluralRules = macroProps.rules;
        NumberingSystem numberingSystem = macroProps.symbols instanceof NumberingSystem ? (NumberingSystem)macroProps.symbols : NumberingSystem.getInstance(macroProps.loc);
        microProps.nsName = numberingSystem.getName();
        microProps.symbols = macroProps.symbols instanceof DecimalFormatSymbols ? (DecimalFormatSymbols)macroProps.symbols : DecimalFormatSymbols.forNumberingSystem(macroProps.loc, numberingSystem);
        String string = null;
        if (bl2 && (object = CurrencyData.provider.getInstance(macroProps.loc, true).getFormatInfo(currency.getCurrencyCode())) != null) {
            string = ((CurrencyData.CurrencyFormatInfo)object).currencyPattern;
            microProps.symbols = (DecimalFormatSymbols)microProps.symbols.clone();
            microProps.symbols.setMonetaryDecimalSeparatorString(((CurrencyData.CurrencyFormatInfo)object).monetaryDecimalSeparator);
            microProps.symbols.setMonetaryGroupingSeparatorString(((CurrencyData.CurrencyFormatInfo)object).monetaryGroupingSeparator);
        }
        if (string == null) {
            int n = bl7 ? 0 : (bl4 || bl5 ? 2 : (!bl2 || unitWidth == NumberFormatter.UnitWidth.FULL_NAME ? 0 : (bl6 ? 7 : 1)));
            string = NumberFormat.getPatternForStyleAndNumberingSystem(macroProps.loc, microProps.nsName, n);
        }
        object = PatternStringParser.parseToPatternInfo(string);
        if (macroProps.scale != null) {
            microPropsGenerator = new MultiplierFormatHandler(macroProps.scale, microPropsGenerator);
        }
        microProps.rounder = macroProps.precision != null ? macroProps.precision : (macroProps.notation instanceof CompactNotation ? Precision.COMPACT_STRATEGY : (bl2 ? Precision.MONETARY_STANDARD : Precision.DEFAULT_MAX_FRAC_6));
        if (macroProps.roundingMode != null) {
            microProps.rounder = microProps.rounder.withMode(RoundingUtils.mathContextUnlimited(macroProps.roundingMode));
        }
        microProps.rounder = microProps.rounder.withLocaleData(currency);
        microProps.grouping = macroProps.grouping instanceof Grouper ? (Grouper)macroProps.grouping : (macroProps.grouping instanceof NumberFormatter.GroupingStrategy ? Grouper.forStrategy((NumberFormatter.GroupingStrategy)((Object)macroProps.grouping)) : (macroProps.notation instanceof CompactNotation ? Grouper.forStrategy(NumberFormatter.GroupingStrategy.MIN2) : Grouper.forStrategy(NumberFormatter.GroupingStrategy.AUTO)));
        microProps.grouping = microProps.grouping.withLocaleData(macroProps.loc, (PatternStringParser.ParsedPatternInfo)object);
        microProps.padding = macroProps.padder != null ? macroProps.padder : Padder.NONE;
        microProps.integerWidth = macroProps.integerWidth != null ? macroProps.integerWidth : IntegerWidth.DEFAULT;
        microProps.sign = macroProps.sign != null ? macroProps.sign : NumberFormatter.SignDisplay.AUTO;
        microProps.decimal = macroProps.decimal != null ? macroProps.decimal : NumberFormatter.DecimalSeparatorDisplay.AUTO;
        microProps.useCurrency = bl2;
        if (macroProps.notation instanceof ScientificNotation) {
            microPropsGenerator = ((ScientificNotation)macroProps.notation).withLocaleData(microProps.symbols, bl, microPropsGenerator);
        } else {
            microProps.modInner = ConstantAffixModifier.EMPTY;
        }
        MutablePatternModifier mutablePatternModifier = new MutablePatternModifier(false);
        mutablePatternModifier.setPatternInfo((AffixPatternProvider)(macroProps.affixProvider != null ? macroProps.affixProvider : object), null);
        mutablePatternModifier.setPatternAttributes(microProps.sign, bl5);
        if (mutablePatternModifier.needsPlurals()) {
            if (pluralRules == null) {
                pluralRules = PluralRules.forLocale(macroProps.loc);
            }
            mutablePatternModifier.setSymbols(microProps.symbols, currency, unitWidth, pluralRules);
        } else {
            mutablePatternModifier.setSymbols(microProps.symbols, currency, unitWidth, null);
        }
        microPropsGenerator = bl ? mutablePatternModifier.createImmutableAndChain(microPropsGenerator) : mutablePatternModifier.addToChain(microPropsGenerator);
        if (bl7) {
            if (pluralRules == null) {
                pluralRules = PluralRules.forLocale(macroProps.loc);
            }
            microPropsGenerator = LongNameHandler.forMeasureUnit(macroProps.loc, macroProps.unit, macroProps.perUnit, unitWidth, pluralRules, microPropsGenerator);
        } else if (bl2 && unitWidth == NumberFormatter.UnitWidth.FULL_NAME) {
            if (pluralRules == null) {
                pluralRules = PluralRules.forLocale(macroProps.loc);
            }
            microPropsGenerator = LongNameHandler.forCurrencyLongNames(macroProps.loc, currency, pluralRules, microPropsGenerator);
        } else {
            microProps.modOuter = ConstantAffixModifier.EMPTY;
        }
        if (macroProps.notation instanceof CompactNotation) {
            if (pluralRules == null) {
                pluralRules = PluralRules.forLocale(macroProps.loc);
            }
            CompactData.CompactType compactType = macroProps.unit instanceof Currency && macroProps.unitWidth != NumberFormatter.UnitWidth.FULL_NAME ? CompactData.CompactType.CURRENCY : CompactData.CompactType.DECIMAL;
            microPropsGenerator = ((CompactNotation)macroProps.notation).withLocaleData(macroProps.loc, microProps.nsName, compactType, pluralRules, bl ? mutablePatternModifier : null, microPropsGenerator);
        }
        return microPropsGenerator;
    }

    public static int writeAffixes(MicroProps microProps, FormattedStringBuilder formattedStringBuilder, int n, int n2) {
        int n3 = microProps.modInner.apply(formattedStringBuilder, n, n2);
        if (microProps.padding.isValid()) {
            microProps.padding.padAndApply(microProps.modMiddle, microProps.modOuter, formattedStringBuilder, n, n2 + n3);
        } else {
            n3 += microProps.modMiddle.apply(formattedStringBuilder, n, n2 + n3);
            n3 += microProps.modOuter.apply(formattedStringBuilder, n, n2 + n3);
        }
        return n3;
    }

    public static int writeNumber(MicroProps microProps, DecimalQuantity decimalQuantity, FormattedStringBuilder formattedStringBuilder, int n) {
        int n2 = 0;
        if (decimalQuantity.isInfinite()) {
            n2 += formattedStringBuilder.insert(n2 + n, microProps.symbols.getInfinity(), NumberFormat.Field.INTEGER);
        } else if (decimalQuantity.isNaN()) {
            n2 += formattedStringBuilder.insert(n2 + n, microProps.symbols.getNaN(), NumberFormat.Field.INTEGER);
        } else {
            n2 += NumberFormatterImpl.writeIntegerDigits(microProps, decimalQuantity, formattedStringBuilder, n2 + n);
            if (decimalQuantity.getLowerDisplayMagnitude() < 0 || microProps.decimal == NumberFormatter.DecimalSeparatorDisplay.ALWAYS) {
                n2 += formattedStringBuilder.insert(n2 + n, microProps.useCurrency ? microProps.symbols.getMonetaryDecimalSeparatorString() : microProps.symbols.getDecimalSeparatorString(), NumberFormat.Field.DECIMAL_SEPARATOR);
            }
            n2 += NumberFormatterImpl.writeFractionDigits(microProps, decimalQuantity, formattedStringBuilder, n2 + n);
        }
        return n2;
    }

    private static int writeIntegerDigits(MicroProps microProps, DecimalQuantity decimalQuantity, FormattedStringBuilder formattedStringBuilder, int n) {
        int n2 = 0;
        int n3 = decimalQuantity.getUpperDisplayMagnitude() + 1;
        for (int i = 0; i < n3; ++i) {
            if (microProps.grouping.groupAtPosition(i, decimalQuantity)) {
                n2 += formattedStringBuilder.insert(n, microProps.useCurrency ? microProps.symbols.getMonetaryGroupingSeparatorString() : microProps.symbols.getGroupingSeparatorString(), NumberFormat.Field.GROUPING_SEPARATOR);
            }
            byte by = decimalQuantity.getDigit(i);
            if (microProps.symbols.getCodePointZero() != -1) {
                n2 += formattedStringBuilder.insertCodePoint(n, microProps.symbols.getCodePointZero() + by, NumberFormat.Field.INTEGER);
                continue;
            }
            n2 += formattedStringBuilder.insert(n, microProps.symbols.getDigitStringsLocal()[by], NumberFormat.Field.INTEGER);
        }
        return n2;
    }

    private static int writeFractionDigits(MicroProps microProps, DecimalQuantity decimalQuantity, FormattedStringBuilder formattedStringBuilder, int n) {
        int n2 = 0;
        int n3 = -decimalQuantity.getLowerDisplayMagnitude();
        for (int i = 0; i < n3; ++i) {
            byte by = decimalQuantity.getDigit(-i - 1);
            if (microProps.symbols.getCodePointZero() != -1) {
                n2 += formattedStringBuilder.insertCodePoint(n2 + n, microProps.symbols.getCodePointZero() + by, NumberFormat.Field.FRACTION);
                continue;
            }
            n2 += formattedStringBuilder.insert(n2 + n, microProps.symbols.getDigitStringsLocal()[by], NumberFormat.Field.FRACTION);
        }
        return n2;
    }
}

