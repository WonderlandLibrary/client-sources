package com.ibm.icu.impl.number;

import com.ibm.icu.text.*;
import com.ibm.icu.impl.*;
import java.util.*;

public class CurrencyPluralInfoAffixProvider implements AffixPatternProvider
{
    private final PropertiesAffixPatternProvider[] affixesByPlural;
    
    public CurrencyPluralInfoAffixProvider(final CurrencyPluralInfo cpi, final DecimalFormatProperties properties) {
        this.affixesByPlural = new PropertiesAffixPatternProvider[StandardPlural.COUNT];
        final DecimalFormatProperties pluralProperties = new DecimalFormatProperties();
        pluralProperties.copyFrom(properties);
        for (final StandardPlural plural : StandardPlural.VALUES) {
            final String pattern = cpi.getCurrencyPluralPattern(plural.getKeyword());
            PatternStringParser.parseToExistingProperties(pattern, pluralProperties);
            this.affixesByPlural[plural.ordinal()] = new PropertiesAffixPatternProvider(pluralProperties);
        }
    }
    
    @Override
    public char charAt(final int flags, final int i) {
        final int pluralOrdinal = flags & 0xFF;
        return this.affixesByPlural[pluralOrdinal].charAt(flags, i);
    }
    
    @Override
    public int length(final int flags) {
        final int pluralOrdinal = flags & 0xFF;
        return this.affixesByPlural[pluralOrdinal].length(flags);
    }
    
    @Override
    public String getString(final int flags) {
        final int pluralOrdinal = flags & 0xFF;
        return this.affixesByPlural[pluralOrdinal].getString(flags);
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
    public boolean containsSymbolType(final int type) {
        return this.affixesByPlural[StandardPlural.OTHER.ordinal()].containsSymbolType(type);
    }
    
    @Override
    public boolean hasBody() {
        return this.affixesByPlural[StandardPlural.OTHER.ordinal()].hasBody();
    }
}
