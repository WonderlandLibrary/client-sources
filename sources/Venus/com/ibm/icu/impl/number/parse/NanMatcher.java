/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.number.parse;

import com.ibm.icu.impl.StringSegment;
import com.ibm.icu.impl.number.parse.ParsedNumber;
import com.ibm.icu.impl.number.parse.SymbolMatcher;
import com.ibm.icu.text.DecimalFormatSymbols;
import com.ibm.icu.text.UnicodeSet;

public class NanMatcher
extends SymbolMatcher {
    private static final NanMatcher DEFAULT = new NanMatcher("NaN");

    public static NanMatcher getInstance(DecimalFormatSymbols decimalFormatSymbols, int n) {
        String string = decimalFormatSymbols.getNaN();
        if (NanMatcher.DEFAULT.string.equals(string)) {
            return DEFAULT;
        }
        return new NanMatcher(string);
    }

    private NanMatcher(String string) {
        super(string, UnicodeSet.EMPTY);
    }

    @Override
    protected boolean isDisabled(ParsedNumber parsedNumber) {
        return parsedNumber.seenNumber();
    }

    @Override
    protected void accept(StringSegment stringSegment, ParsedNumber parsedNumber) {
        parsedNumber.flags |= 0x40;
        parsedNumber.setCharsConsumed(stringSegment);
    }

    public String toString() {
        return "<NanMatcher>";
    }
}

