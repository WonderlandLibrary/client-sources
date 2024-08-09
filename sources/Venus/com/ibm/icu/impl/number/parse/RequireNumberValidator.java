/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.number.parse;

import com.ibm.icu.impl.number.parse.ParsedNumber;
import com.ibm.icu.impl.number.parse.ValidationMatcher;

public class RequireNumberValidator
extends ValidationMatcher {
    @Override
    public void postProcess(ParsedNumber parsedNumber) {
        if (!parsedNumber.seenNumber()) {
            parsedNumber.flags |= 0x100;
        }
    }

    public String toString() {
        return "<RequireNumber>";
    }
}

