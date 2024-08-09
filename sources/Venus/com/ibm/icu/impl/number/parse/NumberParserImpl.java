/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.number.parse;

import com.ibm.icu.impl.StringSegment;
import com.ibm.icu.impl.number.AffixPatternProvider;
import com.ibm.icu.impl.number.CurrencyPluralInfoAffixProvider;
import com.ibm.icu.impl.number.CustomSymbolCurrency;
import com.ibm.icu.impl.number.DecimalFormatProperties;
import com.ibm.icu.impl.number.Grouper;
import com.ibm.icu.impl.number.PatternStringParser;
import com.ibm.icu.impl.number.PropertiesAffixPatternProvider;
import com.ibm.icu.impl.number.RoundingUtils;
import com.ibm.icu.impl.number.parse.AffixMatcher;
import com.ibm.icu.impl.number.parse.AffixTokenMatcherFactory;
import com.ibm.icu.impl.number.parse.CombinedCurrencyMatcher;
import com.ibm.icu.impl.number.parse.DecimalMatcher;
import com.ibm.icu.impl.number.parse.IgnorablesMatcher;
import com.ibm.icu.impl.number.parse.InfinityMatcher;
import com.ibm.icu.impl.number.parse.MinusSignMatcher;
import com.ibm.icu.impl.number.parse.MultiplierParseHandler;
import com.ibm.icu.impl.number.parse.NanMatcher;
import com.ibm.icu.impl.number.parse.NumberParseMatcher;
import com.ibm.icu.impl.number.parse.PaddingMatcher;
import com.ibm.icu.impl.number.parse.ParsedNumber;
import com.ibm.icu.impl.number.parse.PercentMatcher;
import com.ibm.icu.impl.number.parse.PermilleMatcher;
import com.ibm.icu.impl.number.parse.PlusSignMatcher;
import com.ibm.icu.impl.number.parse.RequireAffixValidator;
import com.ibm.icu.impl.number.parse.RequireCurrencyValidator;
import com.ibm.icu.impl.number.parse.RequireDecimalSeparatorValidator;
import com.ibm.icu.impl.number.parse.RequireNumberValidator;
import com.ibm.icu.impl.number.parse.ScientificMatcher;
import com.ibm.icu.number.NumberFormatter;
import com.ibm.icu.number.Scale;
import com.ibm.icu.text.DecimalFormatSymbols;
import com.ibm.icu.util.Currency;
import com.ibm.icu.util.CurrencyAmount;
import com.ibm.icu.util.ULocale;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NumberParserImpl {
    private final int parseFlags;
    private final List<NumberParseMatcher> matchers = new ArrayList<NumberParseMatcher>();
    private boolean frozen;
    static final boolean $assertionsDisabled = !NumberParserImpl.class.desiredAssertionStatus();

    public static NumberParserImpl createSimpleParser(ULocale uLocale, String string, int n) {
        NumberParserImpl numberParserImpl = new NumberParserImpl(n);
        Currency currency = Currency.getInstance("USD");
        DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance(uLocale);
        IgnorablesMatcher ignorablesMatcher = IgnorablesMatcher.getInstance(n);
        AffixTokenMatcherFactory affixTokenMatcherFactory = new AffixTokenMatcherFactory();
        affixTokenMatcherFactory.currency = currency;
        affixTokenMatcherFactory.symbols = decimalFormatSymbols;
        affixTokenMatcherFactory.ignorables = ignorablesMatcher;
        affixTokenMatcherFactory.locale = uLocale;
        affixTokenMatcherFactory.parseFlags = n;
        PatternStringParser.ParsedPatternInfo parsedPatternInfo = PatternStringParser.parseToPatternInfo(string);
        AffixMatcher.createMatchers(parsedPatternInfo, numberParserImpl, affixTokenMatcherFactory, ignorablesMatcher, n);
        Grouper grouper = Grouper.forStrategy(NumberFormatter.GroupingStrategy.AUTO).withLocaleData(uLocale, parsedPatternInfo);
        numberParserImpl.addMatcher(ignorablesMatcher);
        numberParserImpl.addMatcher(DecimalMatcher.getInstance(decimalFormatSymbols, grouper, n));
        numberParserImpl.addMatcher(MinusSignMatcher.getInstance(decimalFormatSymbols, false));
        numberParserImpl.addMatcher(PlusSignMatcher.getInstance(decimalFormatSymbols, false));
        numberParserImpl.addMatcher(PercentMatcher.getInstance(decimalFormatSymbols));
        numberParserImpl.addMatcher(PermilleMatcher.getInstance(decimalFormatSymbols));
        numberParserImpl.addMatcher(NanMatcher.getInstance(decimalFormatSymbols, n));
        numberParserImpl.addMatcher(InfinityMatcher.getInstance(decimalFormatSymbols));
        numberParserImpl.addMatcher(PaddingMatcher.getInstance("@"));
        numberParserImpl.addMatcher(ScientificMatcher.getInstance(decimalFormatSymbols, grouper));
        numberParserImpl.addMatcher(CombinedCurrencyMatcher.getInstance(currency, decimalFormatSymbols, n));
        numberParserImpl.addMatcher(new RequireNumberValidator());
        numberParserImpl.freeze();
        return numberParserImpl;
    }

    public static Number parseStatic(String string, ParsePosition parsePosition, DecimalFormatProperties decimalFormatProperties, DecimalFormatSymbols decimalFormatSymbols) {
        NumberParserImpl numberParserImpl = NumberParserImpl.createParserFromProperties(decimalFormatProperties, decimalFormatSymbols, false);
        ParsedNumber parsedNumber = new ParsedNumber();
        numberParserImpl.parse(string, true, parsedNumber);
        if (parsedNumber.success()) {
            parsePosition.setIndex(parsedNumber.charEnd);
            return parsedNumber.getNumber();
        }
        parsePosition.setErrorIndex(parsedNumber.charEnd);
        return null;
    }

    public static CurrencyAmount parseStaticCurrency(String string, ParsePosition parsePosition, DecimalFormatProperties decimalFormatProperties, DecimalFormatSymbols decimalFormatSymbols) {
        NumberParserImpl numberParserImpl = NumberParserImpl.createParserFromProperties(decimalFormatProperties, decimalFormatSymbols, true);
        ParsedNumber parsedNumber = new ParsedNumber();
        numberParserImpl.parse(string, true, parsedNumber);
        if (parsedNumber.success()) {
            parsePosition.setIndex(parsedNumber.charEnd);
            if (!$assertionsDisabled && parsedNumber.currencyCode == null) {
                throw new AssertionError();
            }
            return new CurrencyAmount(parsedNumber.getNumber(), Currency.getInstance(parsedNumber.currencyCode));
        }
        parsePosition.setErrorIndex(parsedNumber.charEnd);
        return null;
    }

    public static NumberParserImpl createDefaultParserForLocale(ULocale uLocale) {
        DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance(uLocale);
        DecimalFormatProperties decimalFormatProperties = PatternStringParser.parseToProperties("0");
        return NumberParserImpl.createParserFromProperties(decimalFormatProperties, decimalFormatSymbols, false);
    }

    public static NumberParserImpl createParserFromProperties(DecimalFormatProperties decimalFormatProperties, DecimalFormatSymbols decimalFormatSymbols, boolean bl) {
        Scale scale;
        ULocale uLocale = decimalFormatSymbols.getULocale();
        AffixPatternProvider affixPatternProvider = decimalFormatProperties.getCurrencyPluralInfo() == null ? new PropertiesAffixPatternProvider(decimalFormatProperties) : new CurrencyPluralInfoAffixProvider(decimalFormatProperties.getCurrencyPluralInfo(), decimalFormatProperties);
        Currency currency = CustomSymbolCurrency.resolve(decimalFormatProperties.getCurrency(), uLocale, decimalFormatSymbols);
        DecimalFormatProperties.ParseMode parseMode = decimalFormatProperties.getParseMode();
        if (parseMode == null) {
            parseMode = DecimalFormatProperties.ParseMode.LENIENT;
        }
        Grouper grouper = Grouper.forProperties(decimalFormatProperties);
        int n = 0;
        if (!decimalFormatProperties.getParseCaseSensitive()) {
            n |= 1;
        }
        if (decimalFormatProperties.getParseIntegerOnly()) {
            n |= 0x10;
        }
        if (decimalFormatProperties.getParseToBigDecimal()) {
            n |= 0x1000;
        }
        if (decimalFormatProperties.getSignAlwaysShown()) {
            n |= 0x400;
        }
        if (parseMode == DecimalFormatProperties.ParseMode.JAVA_COMPATIBILITY) {
            n |= 4;
            n |= 0x100;
            n |= 0x200;
            n |= 0x10000;
        } else if (parseMode == DecimalFormatProperties.ParseMode.STRICT) {
            n |= 8;
            n |= 4;
            n |= 0x100;
            n |= 0x200;
            n |= 0x8000;
        } else {
            n |= 0x80;
        }
        if (grouper.getPrimary() <= 0) {
            n |= 0x20;
        }
        if (bl || affixPatternProvider.hasCurrencySign()) {
            n |= 2;
        }
        if (!bl) {
            n |= 0x2000;
        }
        NumberParserImpl numberParserImpl = new NumberParserImpl(n);
        IgnorablesMatcher ignorablesMatcher = IgnorablesMatcher.getInstance(n);
        AffixTokenMatcherFactory affixTokenMatcherFactory = new AffixTokenMatcherFactory();
        affixTokenMatcherFactory.currency = currency;
        affixTokenMatcherFactory.symbols = decimalFormatSymbols;
        affixTokenMatcherFactory.ignorables = ignorablesMatcher;
        affixTokenMatcherFactory.locale = uLocale;
        affixTokenMatcherFactory.parseFlags = n;
        AffixMatcher.createMatchers(affixPatternProvider, numberParserImpl, affixTokenMatcherFactory, ignorablesMatcher, n);
        if (bl || affixPatternProvider.hasCurrencySign()) {
            numberParserImpl.addMatcher(CombinedCurrencyMatcher.getInstance(currency, decimalFormatSymbols, n));
        }
        if (parseMode == DecimalFormatProperties.ParseMode.LENIENT && affixPatternProvider.containsSymbolType(-3)) {
            numberParserImpl.addMatcher(PercentMatcher.getInstance(decimalFormatSymbols));
        }
        if (parseMode == DecimalFormatProperties.ParseMode.LENIENT && affixPatternProvider.containsSymbolType(-4)) {
            numberParserImpl.addMatcher(PermilleMatcher.getInstance(decimalFormatSymbols));
        }
        if (parseMode == DecimalFormatProperties.ParseMode.LENIENT) {
            numberParserImpl.addMatcher(PlusSignMatcher.getInstance(decimalFormatSymbols, false));
            numberParserImpl.addMatcher(MinusSignMatcher.getInstance(decimalFormatSymbols, false));
        }
        numberParserImpl.addMatcher(NanMatcher.getInstance(decimalFormatSymbols, n));
        numberParserImpl.addMatcher(InfinityMatcher.getInstance(decimalFormatSymbols));
        String string = decimalFormatProperties.getPadString();
        if (string != null && !ignorablesMatcher.getSet().contains(string)) {
            numberParserImpl.addMatcher(PaddingMatcher.getInstance(string));
        }
        numberParserImpl.addMatcher(ignorablesMatcher);
        numberParserImpl.addMatcher(DecimalMatcher.getInstance(decimalFormatSymbols, grouper, n));
        if (!decimalFormatProperties.getParseNoExponent() || decimalFormatProperties.getMinimumExponentDigits() > 0) {
            numberParserImpl.addMatcher(ScientificMatcher.getInstance(decimalFormatSymbols, grouper));
        }
        numberParserImpl.addMatcher(new RequireNumberValidator());
        if (parseMode != DecimalFormatProperties.ParseMode.LENIENT) {
            numberParserImpl.addMatcher(new RequireAffixValidator());
        }
        if (bl) {
            numberParserImpl.addMatcher(new RequireCurrencyValidator());
        }
        if (decimalFormatProperties.getDecimalPatternMatchRequired()) {
            boolean bl2 = decimalFormatProperties.getDecimalSeparatorAlwaysShown() || decimalFormatProperties.getMaximumFractionDigits() != 0;
            numberParserImpl.addMatcher(RequireDecimalSeparatorValidator.getInstance(bl2));
        }
        if ((scale = RoundingUtils.scaleFromProperties(decimalFormatProperties)) != null) {
            numberParserImpl.addMatcher(new MultiplierParseHandler(scale));
        }
        numberParserImpl.freeze();
        return numberParserImpl;
    }

    public NumberParserImpl(int n) {
        this.parseFlags = n;
        this.frozen = false;
    }

    public void addMatcher(NumberParseMatcher numberParseMatcher) {
        if (!$assertionsDisabled && this.frozen) {
            throw new AssertionError();
        }
        this.matchers.add(numberParseMatcher);
    }

    public void addMatchers(Collection<? extends NumberParseMatcher> collection) {
        if (!$assertionsDisabled && this.frozen) {
            throw new AssertionError();
        }
        this.matchers.addAll(collection);
    }

    public void freeze() {
        this.frozen = true;
    }

    public int getParseFlags() {
        return this.parseFlags;
    }

    public void parse(String string, boolean bl, ParsedNumber parsedNumber) {
        this.parse(string, 0, bl, parsedNumber);
    }

    public void parse(String string, int n, boolean bl, ParsedNumber parsedNumber) {
        if (!$assertionsDisabled && !this.frozen) {
            throw new AssertionError();
        }
        if (!($assertionsDisabled || n >= 0 && n < string.length())) {
            throw new AssertionError();
        }
        StringSegment stringSegment = new StringSegment(string, 0 != (this.parseFlags & 1));
        stringSegment.adjustOffset(n);
        if (bl) {
            this.parseGreedy(stringSegment, parsedNumber);
        } else if (0 != (this.parseFlags & 0x4000)) {
            this.parseLongestRecursive(stringSegment, parsedNumber, 1);
        } else {
            this.parseLongestRecursive(stringSegment, parsedNumber, -100);
        }
        for (NumberParseMatcher numberParseMatcher : this.matchers) {
            numberParseMatcher.postProcess(parsedNumber);
        }
        parsedNumber.postProcess();
    }

    private void parseGreedy(StringSegment stringSegment, ParsedNumber parsedNumber) {
        int n = 0;
        while (n < this.matchers.size()) {
            if (stringSegment.length() == 0) {
                return;
            }
            NumberParseMatcher numberParseMatcher = this.matchers.get(n);
            if (!numberParseMatcher.smokeTest(stringSegment)) {
                ++n;
                continue;
            }
            int n2 = stringSegment.getOffset();
            numberParseMatcher.match(stringSegment, parsedNumber);
            if (stringSegment.getOffset() != n2) {
                n = 0;
                continue;
            }
            ++n;
        }
    }

    private void parseLongestRecursive(StringSegment stringSegment, ParsedNumber parsedNumber, int n) {
        if (stringSegment.length() == 0) {
            return;
        }
        if (n == 0) {
            return;
        }
        ParsedNumber parsedNumber2 = new ParsedNumber();
        parsedNumber2.copyFrom(parsedNumber);
        ParsedNumber parsedNumber3 = new ParsedNumber();
        int n2 = stringSegment.getOffset();
        block0: for (int i = 0; i < this.matchers.size(); ++i) {
            NumberParseMatcher numberParseMatcher = this.matchers.get(i);
            if (!numberParseMatcher.smokeTest(stringSegment)) continue;
            int n3 = 0;
            while (n3 < stringSegment.length()) {
                n3 += Character.charCount(stringSegment.codePointAt(n3));
                parsedNumber3.copyFrom(parsedNumber2);
                stringSegment.setLength(n3);
                boolean bl = numberParseMatcher.match(stringSegment, parsedNumber3);
                stringSegment.resetLength();
                if (stringSegment.getOffset() - n2 == n3) {
                    this.parseLongestRecursive(stringSegment, parsedNumber3, n + 1);
                    if (parsedNumber3.isBetterThan(parsedNumber)) {
                        parsedNumber.copyFrom(parsedNumber3);
                    }
                }
                stringSegment.setOffset(n2);
                if (bl) continue;
                continue block0;
            }
        }
    }

    public String toString() {
        return "<NumberParserImpl matchers=" + this.matchers.toString() + ">";
    }
}

