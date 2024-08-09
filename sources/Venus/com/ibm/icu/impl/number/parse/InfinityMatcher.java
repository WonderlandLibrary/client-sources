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

public class InfinityMatcher
extends SymbolMatcher {
    private static final InfinityMatcher DEFAULT = new InfinityMatcher();

    public static InfinityMatcher getInstance(DecimalFormatSymbols decimalFormatSymbols) {
        String string = decimalFormatSymbols.getInfinity();
        if (ParsingUtils.safeContains(InfinityMatcher.DEFAULT.uniSet, string)) {
            return DEFAULT;
        }
        return new InfinityMatcher(string);
    }

    private InfinityMatcher(String string) {
        super(string, InfinityMatcher.DEFAULT.uniSet);
    }

    private InfinityMatcher() {
        super(StaticUnicodeSets.Key.INFINITY_SIGN);
    }

    @Override
    protected boolean isDisabled(ParsedNumber parsedNumber) {
        return 0 != (parsedNumber.flags & 0x80);
    }

    @Override
    protected void accept(StringSegment stringSegment, ParsedNumber parsedNumber) {
        parsedNumber.flags |= 0x80;
        parsedNumber.setCharsConsumed(stringSegment);
    }

    public String toString() {
        return "<InfinityMatcher>";
    }
}

