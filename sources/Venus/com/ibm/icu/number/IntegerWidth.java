/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.number;

public class IntegerWidth {
    static final IntegerWidth DEFAULT = new IntegerWidth(1, -1);
    final int minInt;
    final int maxInt;

    private IntegerWidth(int n, int n2) {
        this.minInt = n;
        this.maxInt = n2;
    }

    public static IntegerWidth zeroFillTo(int n) {
        if (n == 1) {
            return DEFAULT;
        }
        if (n >= 0 && n <= 999) {
            return new IntegerWidth(n, -1);
        }
        throw new IllegalArgumentException("Integer digits must be between 0 and 999 (inclusive)");
    }

    public IntegerWidth truncateAt(int n) {
        if (n == this.maxInt) {
            return this;
        }
        if (n >= 0 && n <= 999 && n >= this.minInt) {
            return new IntegerWidth(this.minInt, n);
        }
        if (this.minInt == 1 && n == -1) {
            return DEFAULT;
        }
        if (n == -1) {
            return new IntegerWidth(this.minInt, -1);
        }
        throw new IllegalArgumentException("Integer digits must be between -1 and 999 (inclusive)");
    }
}

