/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.number.parse;

import com.ibm.icu.impl.StaticUnicodeSets;
import com.ibm.icu.impl.StringSegment;
import com.ibm.icu.impl.number.parse.ParsedNumber;
import com.ibm.icu.impl.number.parse.ParsingUtils;
import com.ibm.icu.impl.number.parse.SymbolMatcher;
import com.ibm.icu.text.DecimalFormatSymbols;

public class MinusSignMatcher
extends SymbolMatcher {
    private static final MinusSignMatcher DEFAULT = new MinusSignMatcher(false);
    private static final MinusSignMatcher DEFAULT_ALLOW_TRAILING = new MinusSignMatcher(true);
    private final boolean allowTrailing;

    public static MinusSignMatcher getInstance(DecimalFormatSymbols decimalFormatSymbols, boolean bl) {
        String string = decimalFormatSymbols.getMinusSignString();
        if (ParsingUtils.safeContains(MinusSignMatcher.DEFAULT.uniSet, string)) {
            return bl ? DEFAULT_ALLOW_TRAILING : DEFAULT;
        }
        return new MinusSignMatcher(string, bl);
    }

    private MinusSignMatcher(String string, boolean bl) {
        super(string, MinusSignMatcher.DEFAULT.uniSet);
        this.allowTrailing = bl;
    }

    private MinusSignMatcher(boolean bl) {
        super(StaticUnicodeSets.Key.MINUS_SIGN);
        this.allowTrailing = bl;
    }

    @Override
    protected boolean isDisabled(ParsedNumber parsedNumber) {
        return !this.allowTrailing && parsedNumber.seenNumber();
    }

    @Override
    protected void accept(StringSegment stringSegment, ParsedNumber parsedNumber) {
        parsedNumber.flags |= 1;
        parsedNumber.setCharsConsumed(stringSegment);
    }

    public String toString() {
        return "<MinusSignMatcher>";
    }
}

