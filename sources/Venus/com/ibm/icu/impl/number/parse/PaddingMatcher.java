/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.number.parse;

import com.ibm.icu.impl.StringSegment;
import com.ibm.icu.impl.number.parse.NumberParseMatcher;
import com.ibm.icu.impl.number.parse.ParsedNumber;
import com.ibm.icu.impl.number.parse.SymbolMatcher;
import com.ibm.icu.text.UnicodeSet;

public class PaddingMatcher
extends SymbolMatcher
implements NumberParseMatcher.Flexible {
    public static PaddingMatcher getInstance(String string) {
        return new PaddingMatcher(string);
    }

    private PaddingMatcher(String string) {
        super(string, UnicodeSet.EMPTY);
    }

    @Override
    protected boolean isDisabled(ParsedNumber parsedNumber) {
        return true;
    }

    @Override
    protected void accept(StringSegment stringSegment, ParsedNumber parsedNumber) {
    }

    public String toString() {
        return "<PaddingMatcher>";
    }
}

