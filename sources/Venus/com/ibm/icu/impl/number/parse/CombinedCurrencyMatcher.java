/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.number.parse;

import com.ibm.icu.impl.StandardPlural;
import com.ibm.icu.impl.StringSegment;
import com.ibm.icu.impl.TextTrieMap;
import com.ibm.icu.impl.number.parse.NumberParseMatcher;
import com.ibm.icu.impl.number.parse.ParsedNumber;
import com.ibm.icu.text.DecimalFormatSymbols;
import com.ibm.icu.util.Currency;
import java.util.Iterator;

public class CombinedCurrencyMatcher
implements NumberParseMatcher {
    private final String isoCode;
    private final String currency1;
    private final String currency2;
    private final String[] localLongNames;
    private final String afterPrefixInsert;
    private final String beforeSuffixInsert;
    private final TextTrieMap<Currency.CurrencyStringInfo> longNameTrie;
    private final TextTrieMap<Currency.CurrencyStringInfo> symbolTrie;

    public static CombinedCurrencyMatcher getInstance(Currency currency, DecimalFormatSymbols decimalFormatSymbols, int n) {
        return new CombinedCurrencyMatcher(currency, decimalFormatSymbols, n);
    }

    private CombinedCurrencyMatcher(Currency currency, DecimalFormatSymbols decimalFormatSymbols, int n) {
        this.isoCode = currency.getSubtype();
        this.currency1 = currency.getSymbol(decimalFormatSymbols.getULocale());
        this.currency2 = currency.getCurrencyCode();
        this.afterPrefixInsert = decimalFormatSymbols.getPatternForCurrencySpacing(2, true);
        this.beforeSuffixInsert = decimalFormatSymbols.getPatternForCurrencySpacing(2, false);
        if (0 == (n & 0x2000)) {
            this.longNameTrie = Currency.getParsingTrie(decimalFormatSymbols.getULocale(), 1);
            this.symbolTrie = Currency.getParsingTrie(decimalFormatSymbols.getULocale(), 0);
            this.localLongNames = null;
        } else {
            this.longNameTrie = null;
            this.symbolTrie = null;
            this.localLongNames = new String[StandardPlural.COUNT];
            for (int i = 0; i < StandardPlural.COUNT; ++i) {
                String string = StandardPlural.VALUES.get(i).getKeyword();
                this.localLongNames[i] = currency.getName(decimalFormatSymbols.getLocale(), 2, string, null);
            }
        }
    }

    @Override
    public boolean match(StringSegment stringSegment, ParsedNumber parsedNumber) {
        int n;
        if (parsedNumber.currencyCode != null) {
            return true;
        }
        int n2 = stringSegment.getOffset();
        boolean bl = false;
        if (parsedNumber.seenNumber() && !this.beforeSuffixInsert.isEmpty()) {
            n = stringSegment.getCommonPrefixLength(this.beforeSuffixInsert);
            if (n == this.beforeSuffixInsert.length()) {
                stringSegment.adjustOffset(n);
            }
            bl = bl || n == stringSegment.length();
        }
        boolean bl2 = bl = bl || this.matchCurrency(stringSegment, parsedNumber);
        if (parsedNumber.currencyCode == null) {
            stringSegment.setOffset(n2);
            return bl;
        }
        if (!parsedNumber.seenNumber() && !this.afterPrefixInsert.isEmpty()) {
            n = stringSegment.getCommonPrefixLength(this.afterPrefixInsert);
            if (n == this.afterPrefixInsert.length()) {
                stringSegment.adjustOffset(n);
            }
            bl = bl || n == stringSegment.length();
        }
        return bl;
    }

    private boolean matchCurrency(StringSegment stringSegment, ParsedNumber parsedNumber) {
        boolean bl = false;
        int n = !this.currency1.isEmpty() ? stringSegment.getCaseSensitivePrefixLength(this.currency1) : -1;
        boolean bl2 = bl = bl || n == stringSegment.length();
        if (n == this.currency1.length()) {
            parsedNumber.currencyCode = this.isoCode;
            stringSegment.adjustOffset(n);
            parsedNumber.setCharsConsumed(stringSegment);
            return bl;
        }
        int n2 = !this.currency2.isEmpty() ? stringSegment.getCommonPrefixLength(this.currency2) : -1;
        boolean bl3 = bl = bl || n2 == stringSegment.length();
        if (n2 == this.currency2.length()) {
            parsedNumber.currencyCode = this.isoCode;
            stringSegment.adjustOffset(n2);
            parsedNumber.setCharsConsumed(stringSegment);
            return bl;
        }
        if (this.longNameTrie != null) {
            TextTrieMap.Output output = new TextTrieMap.Output();
            Iterator<Currency.CurrencyStringInfo> iterator2 = this.longNameTrie.get(stringSegment, 0, output);
            boolean bl4 = bl = bl || output.partialMatch;
            if (iterator2 == null) {
                iterator2 = this.symbolTrie.get(stringSegment, 0, output);
                boolean bl5 = bl = bl || output.partialMatch;
            }
            if (iterator2 != null) {
                parsedNumber.currencyCode = iterator2.next().getISOCode();
                stringSegment.adjustOffset(output.matchLength);
                parsedNumber.setCharsConsumed(stringSegment);
                return bl;
            }
        } else {
            int n3 = 0;
            for (int i = 0; i < StandardPlural.COUNT; ++i) {
                String string = this.localLongNames[i];
                if (string.isEmpty()) continue;
                int n4 = stringSegment.getCommonPrefixLength(string);
                if (n4 == string.length() && string.length() > n3) {
                    n3 = string.length();
                }
                bl = bl || n4 > 0;
            }
            if (n3 > 0) {
                parsedNumber.currencyCode = this.isoCode;
                stringSegment.adjustOffset(n3);
                parsedNumber.setCharsConsumed(stringSegment);
                return bl;
            }
        }
        return bl;
    }

    @Override
    public boolean smokeTest(StringSegment stringSegment) {
        return false;
    }

    @Override
    public void postProcess(ParsedNumber parsedNumber) {
    }

    public String toString() {
        return "<CombinedCurrencyMatcher " + this.isoCode + ">";
    }
}

