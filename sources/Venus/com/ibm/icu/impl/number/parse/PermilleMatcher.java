/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.number.parse;

import com.ibm.icu.impl.StaticUnicodeSets;
import com.ibm.icu.impl.StringSegment;
import com.ibm.icu.impl.number.parse.ParsedNumber;
import com.ibm.icu.impl.number.parse.SymbolMatcher;
import com.ibm.icu.text.DecimalFormatSymbols;

public class PermilleMatcher
extends SymbolMatcher {
    private static final PermilleMatcher DEFAULT = new PermilleMatcher();

    public static PermilleMatcher getInstance(DecimalFormatSymbols decimalFormatSymbols) {
        String string = decimalFormatSymbols.getPerMillString();
        if (PermilleMatcher.DEFAULT.uniSet.contains(string)) {
            return DEFAULT;
        }
        return new PermilleMatcher(string);
    }

    private PermilleMatcher(String string) {
        super(string, PermilleMatcher.DEFAULT.uniSet);
    }

    private PermilleMatcher() {
        super(StaticUnicodeSets.Key.PERMILLE_SIGN);
    }

    @Override
    protected boolean isDisabled(ParsedNumber parsedNumber) {
        return 0 != (parsedNumber.flags & 4);
    }

    @Override
    protected void accept(StringSegment stringSegment, ParsedNumber parsedNumber) {
        parsedNumber.flags |= 4;
        parsedNumber.setCharsConsumed(stringSegment);
    }

    public String toString() {
        return "<PermilleMatcher>";
    }
}

