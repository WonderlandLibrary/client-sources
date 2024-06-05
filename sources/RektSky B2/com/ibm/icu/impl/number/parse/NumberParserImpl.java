package com.ibm.icu.impl.number.parse;

import com.ibm.icu.text.*;
import java.text.*;
import com.ibm.icu.util.*;
import com.ibm.icu.impl.number.*;
import com.ibm.icu.number.*;
import com.ibm.icu.impl.*;
import java.util.*;

public class NumberParserImpl
{
    private final int parseFlags;
    private final List<NumberParseMatcher> matchers;
    private boolean frozen;
    
    public static NumberParserImpl createSimpleParser(final ULocale locale, final String pattern, final int parseFlags) {
        final NumberParserImpl parser = new NumberParserImpl(parseFlags);
        final Currency currency = Currency.getInstance("USD");
        final DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(locale);
        final IgnorablesMatcher ignorables = IgnorablesMatcher.DEFAULT;
        final AffixTokenMatcherFactory factory = new AffixTokenMatcherFactory();
        factory.currency = currency;
        factory.symbols = symbols;
        factory.ignorables = ignorables;
        factory.locale = locale;
        factory.parseFlags = parseFlags;
        final PatternStringParser.ParsedPatternInfo patternInfo = PatternStringParser.parseToPatternInfo(pattern);
        AffixMatcher.createMatchers(patternInfo, parser, factory, ignorables, parseFlags);
        final Grouper grouper = Grouper.forStrategy(NumberFormatter.GroupingStrategy.AUTO).withLocaleData(locale, patternInfo);
        parser.addMatcher(ignorables);
        parser.addMatcher(DecimalMatcher.getInstance(symbols, grouper, parseFlags));
        parser.addMatcher(MinusSignMatcher.getInstance(symbols, false));
        parser.addMatcher(PlusSignMatcher.getInstance(symbols, false));
        parser.addMatcher(PercentMatcher.getInstance(symbols));
        parser.addMatcher(PermilleMatcher.getInstance(symbols));
        parser.addMatcher(NanMatcher.getInstance(symbols, parseFlags));
        parser.addMatcher(InfinityMatcher.getInstance(symbols));
        parser.addMatcher(PaddingMatcher.getInstance("@"));
        parser.addMatcher(ScientificMatcher.getInstance(symbols, grouper));
        parser.addMatcher(CombinedCurrencyMatcher.getInstance(currency, symbols, parseFlags));
        parser.addMatcher(new RequireNumberValidator());
        parser.freeze();
        return parser;
    }
    
    public static Number parseStatic(final String input, final ParsePosition ppos, final DecimalFormatProperties properties, final DecimalFormatSymbols symbols) {
        final NumberParserImpl parser = createParserFromProperties(properties, symbols, false);
        final ParsedNumber result = new ParsedNumber();
        parser.parse(input, true, result);
        if (result.success()) {
            ppos.setIndex(result.charEnd);
            return result.getNumber();
        }
        ppos.setErrorIndex(result.charEnd);
        return null;
    }
    
    public static CurrencyAmount parseStaticCurrency(final String input, final ParsePosition ppos, final DecimalFormatProperties properties, final DecimalFormatSymbols symbols) {
        final NumberParserImpl parser = createParserFromProperties(properties, symbols, true);
        final ParsedNumber result = new ParsedNumber();
        parser.parse(input, true, result);
        if (!result.success()) {
            ppos.setErrorIndex(result.charEnd);
            return null;
        }
        ppos.setIndex(result.charEnd);
        assert result.currencyCode != null;
        return new CurrencyAmount(result.getNumber(), Currency.getInstance(result.currencyCode));
    }
    
    public static NumberParserImpl createDefaultParserForLocale(final ULocale loc) {
        final DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(loc);
        final DecimalFormatProperties properties = PatternStringParser.parseToProperties("0");
        return createParserFromProperties(properties, symbols, false);
    }
    
    public static NumberParserImpl createParserFromProperties(final DecimalFormatProperties properties, final DecimalFormatSymbols symbols, final boolean parseCurrency) {
        final ULocale locale = symbols.getULocale();
        AffixPatternProvider affixProvider;
        if (properties.getCurrencyPluralInfo() == null) {
            affixProvider = new PropertiesAffixPatternProvider(properties);
        }
        else {
            affixProvider = new CurrencyPluralInfoAffixProvider(properties.getCurrencyPluralInfo(), properties);
        }
        final Currency currency = CustomSymbolCurrency.resolve(properties.getCurrency(), locale, symbols);
        final boolean isStrict = properties.getParseMode() == DecimalFormatProperties.ParseMode.STRICT;
        final Grouper grouper = Grouper.forProperties(properties);
        int parseFlags = 0;
        if (!properties.getParseCaseSensitive()) {
            parseFlags |= 0x1;
        }
        if (properties.getParseIntegerOnly()) {
            parseFlags |= 0x10;
        }
        if (properties.getParseToBigDecimal()) {
            parseFlags |= 0x1000;
        }
        if (properties.getSignAlwaysShown()) {
            parseFlags |= 0x400;
        }
        if (isStrict) {
            parseFlags |= 0x8;
            parseFlags |= 0x4;
            parseFlags |= 0x100;
            parseFlags |= 0x200;
        }
        else {
            parseFlags |= 0x80;
        }
        if (grouper.getPrimary() <= 0) {
            parseFlags |= 0x20;
        }
        if (parseCurrency || affixProvider.hasCurrencySign()) {
            parseFlags |= 0x2;
        }
        if (!parseCurrency) {
            parseFlags |= 0x2000;
        }
        final IgnorablesMatcher ignorables = isStrict ? IgnorablesMatcher.STRICT : IgnorablesMatcher.DEFAULT;
        final NumberParserImpl parser = new NumberParserImpl(parseFlags);
        final AffixTokenMatcherFactory factory = new AffixTokenMatcherFactory();
        factory.currency = currency;
        factory.symbols = symbols;
        factory.ignorables = ignorables;
        factory.locale = locale;
        AffixMatcher.createMatchers(affixProvider, parser, factory, ignorables, factory.parseFlags = parseFlags);
        if (parseCurrency || affixProvider.hasCurrencySign()) {
            parser.addMatcher(CombinedCurrencyMatcher.getInstance(currency, symbols, parseFlags));
        }
        if (affixProvider.containsSymbolType(-3)) {
            parser.addMatcher(PercentMatcher.getInstance(symbols));
        }
        if (affixProvider.containsSymbolType(-4)) {
            parser.addMatcher(PermilleMatcher.getInstance(symbols));
        }
        if (!isStrict) {
            parser.addMatcher(PlusSignMatcher.getInstance(symbols, false));
            parser.addMatcher(MinusSignMatcher.getInstance(symbols, false));
        }
        parser.addMatcher(NanMatcher.getInstance(symbols, parseFlags));
        parser.addMatcher(InfinityMatcher.getInstance(symbols));
        final String padString = properties.getPadString();
        if (padString != null && !ignorables.getSet().contains(padString)) {
            parser.addMatcher(PaddingMatcher.getInstance(padString));
        }
        parser.addMatcher(ignorables);
        parser.addMatcher(DecimalMatcher.getInstance(symbols, grouper, parseFlags));
        if (!properties.getParseNoExponent() || properties.getMinimumExponentDigits() > 0) {
            parser.addMatcher(ScientificMatcher.getInstance(symbols, grouper));
        }
        parser.addMatcher(new RequireNumberValidator());
        if (isStrict) {
            parser.addMatcher(new RequireAffixValidator());
        }
        if (parseCurrency) {
            parser.addMatcher(new RequireCurrencyValidator());
        }
        if (properties.getDecimalPatternMatchRequired()) {
            final boolean patternHasDecimalSeparator = properties.getDecimalSeparatorAlwaysShown() || properties.getMaximumFractionDigits() != 0;
            parser.addMatcher(RequireDecimalSeparatorValidator.getInstance(patternHasDecimalSeparator));
        }
        final Scale multiplier = RoundingUtils.scaleFromProperties(properties);
        if (multiplier != null) {
            parser.addMatcher(new MultiplierParseHandler(multiplier));
        }
        parser.freeze();
        return parser;
    }
    
    public NumberParserImpl(final int parseFlags) {
        this.matchers = new ArrayList<NumberParseMatcher>();
        this.parseFlags = parseFlags;
        this.frozen = false;
    }
    
    public void addMatcher(final NumberParseMatcher matcher) {
        assert !this.frozen;
        this.matchers.add(matcher);
    }
    
    public void addMatchers(final Collection<? extends NumberParseMatcher> matchers) {
        assert !this.frozen;
        this.matchers.addAll(matchers);
    }
    
    public void freeze() {
        this.frozen = true;
    }
    
    public int getParseFlags() {
        return this.parseFlags;
    }
    
    public void parse(final String input, final boolean greedy, final ParsedNumber result) {
        this.parse(input, 0, greedy, result);
    }
    
    public void parse(final String input, final int start, final boolean greedy, final ParsedNumber result) {
        assert this.frozen;
        assert start >= 0 && start < input.length();
        final StringSegment segment = new StringSegment(input, 0x0 != (this.parseFlags & 0x1));
        segment.adjustOffset(start);
        if (greedy) {
            this.parseGreedyRecursive(segment, result);
        }
        else {
            this.parseLongestRecursive(segment, result);
        }
        for (final NumberParseMatcher matcher : this.matchers) {
            matcher.postProcess(result);
        }
        result.postProcess();
    }
    
    private void parseGreedyRecursive(final StringSegment segment, final ParsedNumber result) {
        if (segment.length() == 0) {
            return;
        }
        final int initialOffset = segment.getOffset();
        for (int i = 0; i < this.matchers.size(); ++i) {
            final NumberParseMatcher matcher = this.matchers.get(i);
            if (matcher.smokeTest(segment)) {
                matcher.match(segment, result);
                if (segment.getOffset() != initialOffset) {
                    this.parseGreedyRecursive(segment, result);
                    segment.setOffset(initialOffset);
                    return;
                }
            }
        }
    }
    
    private void parseLongestRecursive(final StringSegment segment, final ParsedNumber result) {
        if (segment.length() == 0) {
            return;
        }
        final ParsedNumber initial = new ParsedNumber();
        initial.copyFrom(result);
        final ParsedNumber candidate = new ParsedNumber();
        final int initialOffset = segment.getOffset();
        for (int i = 0; i < this.matchers.size(); ++i) {
            final NumberParseMatcher matcher = this.matchers.get(i);
            if (matcher.smokeTest(segment)) {
                int charsToConsume = 0;
                while (charsToConsume < segment.length()) {
                    charsToConsume += Character.charCount(segment.codePointAt(charsToConsume));
                    candidate.copyFrom(initial);
                    segment.setLength(charsToConsume);
                    final boolean maybeMore = matcher.match(segment, candidate);
                    segment.resetLength();
                    if (segment.getOffset() - initialOffset == charsToConsume) {
                        this.parseLongestRecursive(segment, candidate);
                        if (candidate.isBetterThan(result)) {
                            result.copyFrom(candidate);
                        }
                    }
                    segment.setOffset(initialOffset);
                    if (!maybeMore) {
                        break;
                    }
                }
            }
        }
    }
    
    @Override
    public String toString() {
        return "<NumberParserImpl matchers=" + this.matchers.toString() + ">";
    }
}
