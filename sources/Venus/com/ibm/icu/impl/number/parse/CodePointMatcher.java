/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.number.parse;

import com.ibm.icu.impl.StringSegment;
import com.ibm.icu.impl.number.parse.NumberParseMatcher;
import com.ibm.icu.impl.number.parse.ParsedNumber;

public class CodePointMatcher
implements NumberParseMatcher {
    private final int cp;

    public static CodePointMatcher getInstance(int n) {
        return new CodePointMatcher(n);
    }

    private CodePointMatcher(int n) {
        this.cp = n;
    }

    @Override
    public boolean match(StringSegment stringSegment, ParsedNumber parsedNumber) {
        if (stringSegment.startsWith(this.cp)) {
            stringSegment.adjustOffsetByCodePoint();
            parsedNumber.setCharsConsumed(stringSegment);
        }
        return true;
    }

    @Override
    public boolean smokeTest(StringSegment stringSegment) {
        return stringSegment.startsWith(this.cp);
    }

    @Override
    public void postProcess(ParsedNumber parsedNumber) {
    }

    public String toString() {
        return "<CodePointMatcher U+" + Integer.toHexString(this.cp) + ">";
    }
}

