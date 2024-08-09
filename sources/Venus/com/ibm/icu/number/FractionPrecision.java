/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.number;

import com.ibm.icu.number.Precision;

public abstract class FractionPrecision
extends Precision {
    FractionPrecision() {
    }

    public Precision withMinDigits(int n) {
        if (n >= 1 && n <= 999) {
            return FractionPrecision.constructFractionSignificant(this, n, -1);
        }
        throw new IllegalArgumentException("Significant digits must be between 1 and 999 (inclusive)");
    }

    public Precision withMaxDigits(int n) {
        if (n >= 1 && n <= 999) {
            return FractionPrecision.constructFractionSignificant(this, -1, n);
        }
        throw new IllegalArgumentException("Significant digits must be between 1 and 999 (inclusive)");
    }
}

