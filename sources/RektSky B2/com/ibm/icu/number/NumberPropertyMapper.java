package com.ibm.icu.number;

import com.ibm.icu.util.*;
import com.ibm.icu.text.*;
import com.ibm.icu.impl.number.*;
import java.math.*;

final class NumberPropertyMapper
{
    public static UnlocalizedNumberFormatter create(final DecimalFormatProperties properties, final DecimalFormatSymbols symbols) {
        final MacroProps macros = oldToNew(properties, symbols, null);
        return NumberFormatter.with().macros(macros);
    }
    
    public static UnlocalizedNumberFormatter create(final DecimalFormatProperties properties, final DecimalFormatSymbols symbols, final DecimalFormatProperties exportedProperties) {
        final MacroProps macros = oldToNew(properties, symbols, exportedProperties);
        return NumberFormatter.with().macros(macros);
    }
    
    public static UnlocalizedNumberFormatter create(final String pattern, final DecimalFormatSymbols symbols) {
        final DecimalFormatProperties properties = PatternStringParser.parseToProperties(pattern);
        return create(properties, symbols);
    }
    
    public static MacroProps oldToNew(final DecimalFormatProperties properties, final DecimalFormatSymbols symbols, final DecimalFormatProperties exportedProperties) {
        final MacroProps macros = new MacroProps();
        final ULocale locale = symbols.getULocale();
        macros.symbols = symbols;
        PluralRules rules = properties.getPluralRules();
        if (rules == null && properties.getCurrencyPluralInfo() != null) {
            rules = properties.getCurrencyPluralInfo().getPluralRules();
        }
        macros.rules = rules;
        AffixPatternProvider affixProvider;
        if (properties.getCurrencyPluralInfo() == null) {
            affixProvider = new PropertiesAffixPatternProvider(properties);
        }
        else {
            affixProvider = new CurrencyPluralInfoAffixProvider(properties.getCurrencyPluralInfo(), properties);
        }
        macros.affixProvider = affixProvider;
        final boolean useCurrency = properties.getCurrency() != null || properties.getCurrencyPluralInfo() != null || properties.getCurrencyUsage() != null || affixProvider.hasCurrencySign();
        final Currency currency = CustomSymbolCurrency.resolve(properties.getCurrency(), locale, symbols);
        Currency.CurrencyUsage currencyUsage = properties.getCurrencyUsage();
        final boolean explicitCurrencyUsage = currencyUsage != null;
        if (!explicitCurrencyUsage) {
            currencyUsage = Currency.CurrencyUsage.STANDARD;
        }
        if (useCurrency) {
            macros.unit = currency;
        }
        int maxInt = properties.getMaximumIntegerDigits();
        int minInt = properties.getMinimumIntegerDigits();
        int maxFrac = properties.getMaximumFractionDigits();
        int minFrac = properties.getMinimumFractionDigits();
        int minSig = properties.getMinimumSignificantDigits();
        int maxSig = properties.getMaximumSignificantDigits();
        final BigDecimal roundingIncrement = properties.getRoundingIncrement();
        final MathContext mathContext = RoundingUtils.getMathContextOrUnlimited(properties);
        final boolean explicitMinMaxFrac = minFrac != -1 || maxFrac != -1;
        final boolean explicitMinMaxSig = minSig != -1 || maxSig != -1;
        if (useCurrency) {
            if (minFrac == -1 && maxFrac == -1) {
                minFrac = currency.getDefaultFractionDigits(currencyUsage);
                maxFrac = currency.getDefaultFractionDigits(currencyUsage);
            }
            else if (minFrac == -1) {
                minFrac = Math.min(maxFrac, currency.getDefaultFractionDigits(currencyUsage));
            }
            else if (maxFrac == -1) {
                maxFrac = Math.max(minFrac, currency.getDefaultFractionDigits(currencyUsage));
            }
        }
        if (minInt == 0 && maxFrac != 0) {
            minFrac = ((minFrac <= 0) ? 1 : minFrac);
            maxFrac = ((maxFrac < 0) ? -1 : ((maxFrac < minFrac) ? minFrac : maxFrac));
            minInt = 0;
            maxInt = ((maxInt < 0) ? -1 : ((maxInt > 999) ? -1 : maxInt));
        }
        else {
            minFrac = ((minFrac < 0) ? 0 : minFrac);
            maxFrac = ((maxFrac < 0) ? -1 : ((maxFrac < minFrac) ? minFrac : maxFrac));
            minInt = ((minInt <= 0) ? 1 : ((minInt > 999) ? 1 : minInt));
            maxInt = ((maxInt < 0) ? -1 : ((maxInt < minInt) ? minInt : ((maxInt > 999) ? -1 : maxInt)));
        }
        Precision rounding = null;
        if (explicitCurrencyUsage) {
            rounding = Precision.constructCurrency(currencyUsage).withCurrency(currency);
        }
        else if (roundingIncrement != null) {
            rounding = Precision.constructIncrement(roundingIncrement);
        }
        else if (explicitMinMaxSig) {
            minSig = ((minSig < 1) ? 1 : ((minSig > 999) ? 999 : minSig));
            maxSig = ((maxSig < 0) ? 999 : ((maxSig < minSig) ? minSig : ((maxSig > 999) ? 999 : maxSig)));
            rounding = Precision.constructSignificant(minSig, maxSig);
        }
        else if (explicitMinMaxFrac) {
            rounding = Precision.constructFraction(minFrac, maxFrac);
        }
        else if (useCurrency) {
            rounding = Precision.constructCurrency(currencyUsage);
        }
        if (rounding != null) {
            rounding = rounding.withMode(mathContext);
            macros.precision = rounding;
        }
        macros.integerWidth = IntegerWidth.zeroFillTo(minInt).truncateAt(maxInt);
        macros.grouping = Grouper.forProperties(properties);
        if (properties.getFormatWidth() != -1) {
            macros.padder = Padder.forProperties(properties);
        }
        macros.decimal = (properties.getDecimalSeparatorAlwaysShown() ? NumberFormatter.DecimalSeparatorDisplay.ALWAYS : NumberFormatter.DecimalSeparatorDisplay.AUTO);
        macros.sign = (properties.getSignAlwaysShown() ? NumberFormatter.SignDisplay.ALWAYS : NumberFormatter.SignDisplay.AUTO);
        if (properties.getMinimumExponentDigits() != -1) {
            if (maxInt > 8) {
                maxInt = minInt;
                macros.integerWidth = IntegerWidth.zeroFillTo(minInt).truncateAt(maxInt);
            }
            else if (maxInt > minInt && minInt > 1) {
                minInt = 1;
                macros.integerWidth = IntegerWidth.zeroFillTo(minInt).truncateAt(maxInt);
            }
            final int engineering = (maxInt < 0) ? -1 : maxInt;
            macros.notation = new ScientificNotation(engineering, engineering == minInt, properties.getMinimumExponentDigits(), properties.getExponentSignAlwaysShown() ? NumberFormatter.SignDisplay.ALWAYS : NumberFormatter.SignDisplay.AUTO);
            if (macros.precision instanceof FractionPrecision) {
                final int minInt_ = properties.getMinimumIntegerDigits();
                final int minFrac_ = properties.getMinimumFractionDigits();
                final int maxFrac_ = properties.getMaximumFractionDigits();
                if (minInt_ == 0 && maxFrac_ == 0) {
                    macros.precision = Precision.constructInfinite().withMode(mathContext);
                }
                else if (minInt_ == 0 && minFrac_ == 0) {
                    macros.precision = Precision.constructSignificant(1, maxFrac_ + 1).withMode(mathContext);
                }
                else {
                    macros.precision = Precision.constructSignificant(minInt_ + minFrac_, minInt_ + maxFrac_).withMode(mathContext);
                }
            }
        }
        if (properties.getCompactStyle() != null) {
            if (properties.getCompactCustomData() != null) {
                macros.notation = new CompactNotation(properties.getCompactCustomData());
            }
            else if (properties.getCompactStyle() == CompactDecimalFormat.CompactStyle.LONG) {
                macros.notation = Notation.compactLong();
            }
            else {
                macros.notation = Notation.compactShort();
            }
            macros.affixProvider = null;
        }
        macros.scale = RoundingUtils.scaleFromProperties(properties);
        if (exportedProperties != null) {
            exportedProperties.setCurrency(currency);
            exportedProperties.setMathContext(mathContext);
            exportedProperties.setRoundingMode(mathContext.getRoundingMode());
            exportedProperties.setMinimumIntegerDigits(minInt);
            exportedProperties.setMaximumIntegerDigits((maxInt == -1) ? Integer.MAX_VALUE : maxInt);
            Precision rounding_;
            if (rounding instanceof CurrencyPrecision) {
                rounding_ = ((CurrencyPrecision)rounding).withCurrency(currency);
            }
            else {
                rounding_ = rounding;
            }
            int minFrac_2 = minFrac;
            int maxFrac_2 = maxFrac;
            int minSig_ = minSig;
            int maxSig_ = maxSig;
            BigDecimal increment_ = null;
            if (rounding_ instanceof Precision.FractionRounderImpl) {
                minFrac_2 = ((Precision.FractionRounderImpl)rounding_).minFrac;
                maxFrac_2 = ((Precision.FractionRounderImpl)rounding_).maxFrac;
            }
            else if (rounding_ instanceof Precision.IncrementRounderImpl) {
                increment_ = ((Precision.IncrementRounderImpl)rounding_).increment;
                minFrac_2 = increment_.scale();
                maxFrac_2 = increment_.scale();
            }
            else if (rounding_ instanceof Precision.SignificantRounderImpl) {
                minSig_ = ((Precision.SignificantRounderImpl)rounding_).minSig;
                maxSig_ = ((Precision.SignificantRounderImpl)rounding_).maxSig;
            }
            exportedProperties.setMinimumFractionDigits(minFrac_2);
            exportedProperties.setMaximumFractionDigits(maxFrac_2);
            exportedProperties.setMinimumSignificantDigits(minSig_);
            exportedProperties.setMaximumSignificantDigits(maxSig_);
            exportedProperties.setRoundingIncrement(increment_);
        }
        return macros;
    }
}
