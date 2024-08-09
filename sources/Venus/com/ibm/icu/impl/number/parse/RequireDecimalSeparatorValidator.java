/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.number.parse;

import com.ibm.icu.impl.number.parse.ParsedNumber;
import com.ibm.icu.impl.number.parse.ValidationMatcher;

public class RequireDecimalSeparatorValidator
extends ValidationMatcher {
    private static final RequireDecimalSeparatorValidator A = new RequireDecimalSeparatorValidator(true);
    private static final RequireDecimalSeparatorValidator B = new RequireDecimalSeparatorValidator(false);
    private final boolean patternHasDecimalSeparator;

    public static RequireDecimalSeparatorValidator getInstance(boolean bl) {
        return bl ? A : B;
    }

    private RequireDecimalSeparatorValidator(boolean bl) {
        this.patternHasDecimalSeparator = bl;
    }

    @Override
    public void postProcess(ParsedNumber parsedNumber) {
        boolean bl;
        boolean bl2 = bl = 0 != (parsedNumber.flags & 0x20);
        if (bl != this.patternHasDecimalSeparator) {
            parsedNumber.flags |= 0x100;
        }
    }

    public String toString() {
        return "<RequireDecimalSeparator>";
    }
}

