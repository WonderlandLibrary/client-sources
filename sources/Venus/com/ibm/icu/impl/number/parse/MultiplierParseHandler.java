/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.number.parse;

import com.ibm.icu.impl.number.parse.ParsedNumber;
import com.ibm.icu.impl.number.parse.ValidationMatcher;
import com.ibm.icu.number.Scale;

public class MultiplierParseHandler
extends ValidationMatcher {
    private final Scale multiplier;

    public MultiplierParseHandler(Scale scale) {
        this.multiplier = scale;
    }

    @Override
    public void postProcess(ParsedNumber parsedNumber) {
        if (parsedNumber.quantity != null) {
            this.multiplier.applyReciprocalTo(parsedNumber.quantity);
        }
    }

    public String toString() {
        return "<MultiplierHandler " + this.multiplier + ">";
    }
}

