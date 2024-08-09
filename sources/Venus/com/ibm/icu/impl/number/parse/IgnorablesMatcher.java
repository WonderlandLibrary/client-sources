/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.number.parse;

import com.ibm.icu.impl.StaticUnicodeSets;
import com.ibm.icu.impl.StringSegment;
import com.ibm.icu.impl.number.parse.NumberParseMatcher;
import com.ibm.icu.impl.number.parse.ParsedNumber;
import com.ibm.icu.impl.number.parse.SymbolMatcher;
import com.ibm.icu.text.UnicodeSet;

public class IgnorablesMatcher
extends SymbolMatcher
implements NumberParseMatcher.Flexible {
    private static final IgnorablesMatcher DEFAULT = new IgnorablesMatcher(StaticUnicodeSets.get(StaticUnicodeSets.Key.DEFAULT_IGNORABLES));
    private static final IgnorablesMatcher STRICT = new IgnorablesMatcher(StaticUnicodeSets.get(StaticUnicodeSets.Key.STRICT_IGNORABLES));
    private static final IgnorablesMatcher JAVA_COMPATIBILITY = new IgnorablesMatcher(StaticUnicodeSets.get(StaticUnicodeSets.Key.EMPTY));

    public static IgnorablesMatcher getInstance(int n) {
        if (0 != (n & 0x10000)) {
            return JAVA_COMPATIBILITY;
        }
        if (0 != (n & 0x8000)) {
            return STRICT;
        }
        return DEFAULT;
    }

    private IgnorablesMatcher(UnicodeSet unicodeSet) {
        super("", unicodeSet);
    }

    @Override
    protected boolean isDisabled(ParsedNumber parsedNumber) {
        return true;
    }

    @Override
    protected void accept(StringSegment stringSegment, ParsedNumber parsedNumber) {
    }

    public String toString() {
        return "<IgnorablesMatcher>";
    }
}

