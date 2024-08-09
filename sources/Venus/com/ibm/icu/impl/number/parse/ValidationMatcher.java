/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.number.parse;

import com.ibm.icu.impl.StringSegment;
import com.ibm.icu.impl.number.parse.NumberParseMatcher;
import com.ibm.icu.impl.number.parse.ParsedNumber;

public abstract class ValidationMatcher
implements NumberParseMatcher {
    @Override
    public boolean match(StringSegment stringSegment, ParsedNumber parsedNumber) {
        return true;
    }

    @Override
    public boolean smokeTest(StringSegment stringSegment) {
        return true;
    }
}

