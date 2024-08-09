/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.number.parse;

import com.ibm.icu.impl.StaticUnicodeSets;
import com.ibm.icu.impl.StringSegment;
import com.ibm.icu.impl.number.parse.ParsedNumber;
import com.ibm.icu.impl.number.parse.SymbolMatcher;
import com.ibm.icu.text.DecimalFormatSymbols;

public class PercentMatcher
extends SymbolMatcher {
    private static final PercentMatcher DEFAULT = new PercentMatcher();

    public static PercentMatcher getInstance(DecimalFormatSymbols decimalFormatSymbols) {
        String string = decimalFormatSymbols.getPercentString();
        if (PercentMatcher.DEFAULT.uniSet.contains(string)) {
            return DEFAULT;
        }
        return new PercentMatcher(string);
    }

    private PercentMatcher(String string) {
        super(string, PercentMatcher.DEFAULT.uniSet);
    }

    private PercentMatcher() {
        super(StaticUnicodeSets.Key.PERCENT_SIGN);
    }

    @Override
    protected boolean isDisabled(ParsedNumber parsedNumber) {
        return 0 != (parsedNumber.flags & 2);
    }

    @Override
    protected void accept(StringSegment stringSegment, ParsedNumber parsedNumber) {
        parsedNumber.flags |= 2;
        parsedNumber.setCharsConsumed(stringSegment);
    }

    public String toString() {
        return "<PercentMatcher>";
    }
}

