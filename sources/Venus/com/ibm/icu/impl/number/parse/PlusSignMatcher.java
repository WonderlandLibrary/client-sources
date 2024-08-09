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

public class PlusSignMatcher
extends SymbolMatcher {
    private static final PlusSignMatcher DEFAULT = new PlusSignMatcher(false);
    private static final PlusSignMatcher DEFAULT_ALLOW_TRAILING = new PlusSignMatcher(true);
    private final boolean allowTrailing;

    public static PlusSignMatcher getInstance(DecimalFormatSymbols decimalFormatSymbols, boolean bl) {
        String string = decimalFormatSymbols.getPlusSignString();
        if (ParsingUtils.safeContains(PlusSignMatcher.DEFAULT.uniSet, string)) {
            return bl ? DEFAULT_ALLOW_TRAILING : DEFAULT;
        }
        return new PlusSignMatcher(string, bl);
    }

    private PlusSignMatcher(String string, boolean bl) {
        super(string, PlusSignMatcher.DEFAULT.uniSet);
        this.allowTrailing = bl;
    }

    private PlusSignMatcher(boolean bl) {
        super(StaticUnicodeSets.Key.PLUS_SIGN);
        this.allowTrailing = bl;
    }

    @Override
    protected boolean isDisabled(ParsedNumber parsedNumber) {
        return !this.allowTrailing && parsedNumber.seenNumber();
    }

    @Override
    protected void accept(StringSegment stringSegment, ParsedNumber parsedNumber) {
        parsedNumber.setCharsConsumed(stringSegment);
    }

    public String toString() {
        return "<PlusSignMatcher>";
    }
}

