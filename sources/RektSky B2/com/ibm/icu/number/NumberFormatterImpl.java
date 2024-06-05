package com.ibm.icu.number;

import com.ibm.icu.util.*;
import com.ibm.icu.impl.*;
import com.ibm.icu.text.*;
import com.ibm.icu.impl.number.*;

class NumberFormatterImpl
{
    private static final Currency DEFAULT_CURRENCY;
    final MicroPropsGenerator microPropsGenerator;
    
    public static NumberFormatterImpl fromMacros(final MacroProps macros) {
        final MicroPropsGenerator microPropsGenerator = macrosToMicroGenerator(macros, true);
        return new NumberFormatterImpl(microPropsGenerator);
    }
    
    public static void applyStatic(final MacroProps macros, final DecimalQuantity inValue, final NumberStringBuilder outString) {
        final MicroPropsGenerator microPropsGenerator = macrosToMicroGenerator(macros, false);
        final MicroProps micros = microPropsGenerator.processQuantity(inValue);
        microsToString(micros, inValue, outString);
    }
    
    public static int getPrefixSuffixStatic(final MacroProps macros, final byte signum, final StandardPlural plural, final NumberStringBuilder output) {
        final MicroPropsGenerator microPropsGenerator = macrosToMicroGenerator(macros, false);
        return getPrefixSuffixImpl(microPropsGenerator, signum, output);
    }
    
    private NumberFormatterImpl(final MicroPropsGenerator microPropsGenerator) {
        this.microPropsGenerator = microPropsGenerator;
    }
    
    public void apply(final DecimalQuantity inValue, final NumberStringBuilder outString) {
        final MicroProps micros = this.microPropsGenerator.processQuantity(inValue);
        microsToString(micros, inValue, outString);
    }
    
    public int getPrefixSuffix(final byte signum, final StandardPlural plural, final NumberStringBuilder output) {
        return getPrefixSuffixImpl(this.microPropsGenerator, signum, output);
    }
    
    private static int getPrefixSuffixImpl(final MicroPropsGenerator generator, final byte signum, final NumberStringBuilder output) {
        final DecimalQuantity_DualStorageBCD quantity = new DecimalQuantity_DualStorageBCD(0);
        if (signum < 0) {
            quantity.negate();
        }
        final MicroProps micros = generator.processQuantity(quantity);
        micros.modMiddle.apply(output, 0, 0);
        return micros.modMiddle.getPrefixLength();
    }
    
    private static boolean unitIsCurrency(final MeasureUnit unit) {
        return unit != null && "currency".equals(unit.getType());
    }
    
    private static boolean unitIsNoUnit(final MeasureUnit unit) {
        return unit == null || "none".equals(unit.getType());
    }
    
    private static boolean unitIsPercent(final MeasureUnit unit) {
        return unit != null && "percent".equals(unit.getSubtype());
    }
    
    private static boolean unitIsPermille(final MeasureUnit unit) {
        return unit != null && "permille".equals(unit.getSubtype());
    }
    
    private static MicroPropsGenerator macrosToMicroGenerator(final MacroProps macros, final boolean safe) {
        MicroPropsGenerator chain;
        final MicroProps micros = (MicroProps)(chain = new MicroProps(safe));
        final boolean isCurrency = unitIsCurrency(macros.unit);
        final boolean isNoUnit = unitIsNoUnit(macros.unit);
        final boolean isPercent = isNoUnit && unitIsPercent(macros.unit);
        final boolean isPermille = isNoUnit && unitIsPermille(macros.unit);
        final boolean isCldrUnit = !isCurrency && !isNoUnit;
        final boolean isAccounting = macros.sign == NumberFormatter.SignDisplay.ACCOUNTING || macros.sign == NumberFormatter.SignDisplay.ACCOUNTING_ALWAYS || macros.sign == NumberFormatter.SignDisplay.ACCOUNTING_EXCEPT_ZERO;
        final Currency currency = (Currency)(isCurrency ? macros.unit : NumberFormatterImpl.DEFAULT_CURRENCY);
        NumberFormatter.UnitWidth unitWidth = NumberFormatter.UnitWidth.SHORT;
        if (macros.unitWidth != null) {
            unitWidth = macros.unitWidth;
        }
        PluralRules rules = macros.rules;
        NumberingSystem ns;
        if (macros.symbols instanceof NumberingSystem) {
            ns = (NumberingSystem)macros.symbols;
        }
        else {
            ns = NumberingSystem.getInstance(macros.loc);
        }
        final String nsName = ns.getName();
        if (macros.symbols instanceof DecimalFormatSymbols) {
            micros.symbols = (DecimalFormatSymbols)macros.symbols;
        }
        else {
            micros.symbols = DecimalFormatSymbols.forNumberingSystem(macros.loc, ns);
        }
        String pattern = null;
        if (isCurrency) {
            final CurrencyData.CurrencyFormatInfo info = CurrencyData.provider.getInstance(macros.loc, true).getFormatInfo(currency.getCurrencyCode());
            if (info != null) {
                pattern = info.currencyPattern;
                (micros.symbols = (DecimalFormatSymbols)micros.symbols.clone()).setMonetaryDecimalSeparatorString(info.monetaryDecimalSeparator);
                micros.symbols.setMonetaryGroupingSeparatorString(info.monetaryGroupingSeparator);
            }
        }
        if (pattern == null) {
            int patternStyle;
            if (isPercent || isPermille) {
                patternStyle = 2;
            }
            else if (!isCurrency || unitWidth == NumberFormatter.UnitWidth.FULL_NAME) {
                patternStyle = 0;
            }
            else if (isAccounting) {
                patternStyle = 7;
            }
            else {
                patternStyle = 1;
            }
            pattern = NumberFormat.getPatternForStyleAndNumberingSystem(macros.loc, nsName, patternStyle);
        }
        final PatternStringParser.ParsedPatternInfo patternInfo = PatternStringParser.parseToPatternInfo(pattern);
        if (macros.scale != null) {
            chain = new MultiplierFormatHandler(macros.scale, chain);
        }
        if (macros.precision != null) {
            micros.rounder = macros.precision;
        }
        else if (macros.notation instanceof CompactNotation) {
            micros.rounder = Precision.COMPACT_STRATEGY;
        }
        else if (isCurrency) {
            micros.rounder = Precision.MONETARY_STANDARD;
        }
        else {
            micros.rounder = Precision.DEFAULT_MAX_FRAC_6;
        }
        if (macros.roundingMode != null) {
            micros.rounder = micros.rounder.withMode(macros.roundingMode);
        }
        micros.rounder = micros.rounder.withLocaleData(currency);
        if (macros.grouping instanceof Grouper) {
            micros.grouping = (Grouper)macros.grouping;
        }
        else if (macros.grouping instanceof NumberFormatter.GroupingStrategy) {
            micros.grouping = Grouper.forStrategy((NumberFormatter.GroupingStrategy)macros.grouping);
        }
        else if (macros.notation instanceof CompactNotation) {
            micros.grouping = Grouper.forStrategy(NumberFormatter.GroupingStrategy.MIN2);
        }
        else {
            micros.grouping = Grouper.forStrategy(NumberFormatter.GroupingStrategy.AUTO);
        }
        micros.grouping = micros.grouping.withLocaleData(macros.loc, patternInfo);
        if (macros.padder != null) {
            micros.padding = macros.padder;
        }
        else {
            micros.padding = Padder.NONE;
        }
        if (macros.integerWidth != null) {
            micros.integerWidth = macros.integerWidth;
        }
        else {
            micros.integerWidth = IntegerWidth.DEFAULT;
        }
        if (macros.sign != null) {
            micros.sign = macros.sign;
        }
        else {
            micros.sign = NumberFormatter.SignDisplay.AUTO;
        }
        if (macros.decimal != null) {
            micros.decimal = macros.decimal;
        }
        else {
            micros.decimal = NumberFormatter.DecimalSeparatorDisplay.AUTO;
        }
        micros.useCurrency = isCurrency;
        if (macros.notation instanceof ScientificNotation) {
            chain = ((ScientificNotation)macros.notation).withLocaleData(micros.symbols, safe, chain);
        }
        else {
            micros.modInner = ConstantAffixModifier.EMPTY;
        }
        final MutablePatternModifier patternMod = new MutablePatternModifier(false);
        patternMod.setPatternInfo((macros.affixProvider != null) ? macros.affixProvider : patternInfo);
        patternMod.setPatternAttributes(micros.sign, isPermille);
        if (patternMod.needsPlurals()) {
            if (rules == null) {
                rules = PluralRules.forLocale(macros.loc);
            }
            patternMod.setSymbols(micros.symbols, currency, unitWidth, rules);
        }
        else {
            patternMod.setSymbols(micros.symbols, currency, unitWidth, null);
        }
        if (safe) {
            chain = patternMod.createImmutableAndChain(chain);
        }
        else {
            chain = patternMod.addToChain(chain);
        }
        if (isCldrUnit) {
            if (rules == null) {
                rules = PluralRules.forLocale(macros.loc);
            }
            chain = LongNameHandler.forMeasureUnit(macros.loc, macros.unit, macros.perUnit, unitWidth, rules, chain);
        }
        else if (isCurrency && unitWidth == NumberFormatter.UnitWidth.FULL_NAME) {
            if (rules == null) {
                rules = PluralRules.forLocale(macros.loc);
            }
            chain = LongNameHandler.forCurrencyLongNames(macros.loc, currency, rules, chain);
        }
        else {
            micros.modOuter = ConstantAffixModifier.EMPTY;
        }
        if (macros.notation instanceof CompactNotation) {
            if (rules == null) {
                rules = PluralRules.forLocale(macros.loc);
            }
            final CompactData.CompactType compactType = (macros.unit instanceof Currency && macros.unitWidth != NumberFormatter.UnitWidth.FULL_NAME) ? CompactData.CompactType.CURRENCY : CompactData.CompactType.DECIMAL;
            chain = ((CompactNotation)macros.notation).withLocaleData(macros.loc, nsName, compactType, rules, safe ? patternMod : null, chain);
        }
        return chain;
    }
    
    private static void microsToString(final MicroProps micros, final DecimalQuantity quantity, final NumberStringBuilder string) {
        micros.rounder.apply(quantity);
        if (micros.integerWidth.maxInt == -1) {
            quantity.setIntegerLength(micros.integerWidth.minInt, Integer.MAX_VALUE);
        }
        else {
            quantity.setIntegerLength(micros.integerWidth.minInt, micros.integerWidth.maxInt);
        }
        int length = writeNumber(micros, quantity, string);
        length += micros.modInner.apply(string, 0, length);
        if (micros.padding.isValid()) {
            micros.padding.padAndApply(micros.modMiddle, micros.modOuter, string, 0, length);
        }
        else {
            length += micros.modMiddle.apply(string, 0, length);
            length += micros.modOuter.apply(string, 0, length);
        }
    }
    
    private static int writeNumber(final MicroProps micros, final DecimalQuantity quantity, final NumberStringBuilder string) {
        int length = 0;
        if (quantity.isInfinite()) {
            length += string.insert(length, micros.symbols.getInfinity(), NumberFormat.Field.INTEGER);
        }
        else if (quantity.isNaN()) {
            length += string.insert(length, micros.symbols.getNaN(), NumberFormat.Field.INTEGER);
        }
        else {
            length += writeIntegerDigits(micros, quantity, string);
            if (quantity.getLowerDisplayMagnitude() < 0 || micros.decimal == NumberFormatter.DecimalSeparatorDisplay.ALWAYS) {
                length += string.insert(length, micros.useCurrency ? micros.symbols.getMonetaryDecimalSeparatorString() : micros.symbols.getDecimalSeparatorString(), NumberFormat.Field.DECIMAL_SEPARATOR);
            }
            length += writeFractionDigits(micros, quantity, string);
        }
        return length;
    }
    
    private static int writeIntegerDigits(final MicroProps micros, final DecimalQuantity quantity, final NumberStringBuilder string) {
        int length = 0;
        for (int integerCount = quantity.getUpperDisplayMagnitude() + 1, i = 0; i < integerCount; ++i) {
            if (micros.grouping.groupAtPosition(i, quantity)) {
                length += string.insert(0, micros.useCurrency ? micros.symbols.getMonetaryGroupingSeparatorString() : micros.symbols.getGroupingSeparatorString(), NumberFormat.Field.GROUPING_SEPARATOR);
            }
            final byte nextDigit = quantity.getDigit(i);
            if (micros.symbols.getCodePointZero() != -1) {
                length += string.insertCodePoint(0, micros.symbols.getCodePointZero() + nextDigit, NumberFormat.Field.INTEGER);
            }
            else {
                length += string.insert(0, micros.symbols.getDigitStringsLocal()[nextDigit], NumberFormat.Field.INTEGER);
            }
        }
        return length;
    }
    
    private static int writeFractionDigits(final MicroProps micros, final DecimalQuantity quantity, final NumberStringBuilder string) {
        int length = 0;
        for (int fractionCount = -quantity.getLowerDisplayMagnitude(), i = 0; i < fractionCount; ++i) {
            final byte nextDigit = quantity.getDigit(-i - 1);
            if (micros.symbols.getCodePointZero() != -1) {
                length += string.appendCodePoint(micros.symbols.getCodePointZero() + nextDigit, NumberFormat.Field.FRACTION);
            }
            else {
                length += string.append(micros.symbols.getDigitStringsLocal()[nextDigit], NumberFormat.Field.FRACTION);
            }
        }
        return length;
    }
    
    static {
        DEFAULT_CURRENCY = Currency.getInstance("XXX");
    }
}
