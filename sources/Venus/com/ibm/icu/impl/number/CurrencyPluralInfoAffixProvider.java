/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.number;

import com.ibm.icu.impl.StandardPlural;
import com.ibm.icu.impl.number.AffixPatternProvider;
import com.ibm.icu.impl.number.DecimalFormatProperties;
import com.ibm.icu.impl.number.PatternStringParser;
import com.ibm.icu.impl.number.PropertiesAffixPatternProvider;
import com.ibm.icu.text.CurrencyPluralInfo;

public class CurrencyPluralInfoAffixProvider
implements AffixPatternProvider {
    private final PropertiesAffixPatternProvider[] affixesByPlural = new PropertiesAffixPatternProvider[StandardPlural.COUNT];

    public CurrencyPluralInfoAffixProvider(CurrencyPluralInfo currencyPluralInfo, DecimalFormatProperties decimalFormatProperties) {
        DecimalFormatProperties decimalFormatProperties2 = new DecimalFormatProperties();
        decimalFormatProperties2.copyFrom(decimalFormatProperties);
        for (StandardPlural standardPlural : StandardPlural.VALUES) {
            String string = currencyPluralInfo.getCurrencyPluralPattern(standardPlural.getKeyword());
            PatternStringParser.parseToExistingProperties(string, decimalFormatProperties2);
            this.affixesByPlural[standardPlural.ordinal()] = new PropertiesAffixPatternProvider(decimalFormatProperties2);
        }
    }

    @Override
    public char charAt(int n, int n2) {
        int n3 = n & 0xFF;
        return this.affixesByPlural[n3].charAt(n, n2);
    }

    @Override
    public int length(int n) {
        int n2 = n & 0xFF;
        return this.affixesByPlural[n2].length(n);
    }

    @Override
    public String getString(int n) {
        int n2 = n & 0xFF;
        return this.affixesByPlural[n2].getString(n);
    }

    @Override
    public boolean positiveHasPlusSign() {
        return this.affixesByPlural[StandardPlural.OTHER.ordinal()].positiveHasPlusSign();
    }

    @Override
    public boolean hasNegativeSubpattern() {
        return this.affixesByPlural[StandardPlural.OTHER.ordinal()].hasNegativeSubpattern();
    }

    @Override
    public boolean negativeHasMinusSign() {
        return this.affixesByPlural[StandardPlural.OTHER.ordinal()].negativeHasMinusSign();
    }

    @Override
    public boolean hasCurrencySign() {
        return this.affixesByPlural[StandardPlural.OTHER.ordinal()].hasCurrencySign();
    }

    @Override
    public boolean containsSymbolType(int n) {
        return this.affixesByPlural[StandardPlural.OTHER.ordinal()].containsSymbolType(n);
    }

    @Override
    public boolean hasBody() {
        return this.affixesByPlural[StandardPlural.OTHER.ordinal()].hasBody();
    }
}

