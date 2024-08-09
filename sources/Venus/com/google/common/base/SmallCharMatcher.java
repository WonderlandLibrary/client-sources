/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.base;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.CharMatcher;
import java.util.BitSet;

@GwtIncompatible
final class SmallCharMatcher
extends CharMatcher.NamedFastMatcher {
    static final int MAX_SIZE = 1023;
    private final char[] table;
    private final boolean containsZero;
    private final long filter;
    private static final int C1 = -862048943;
    private static final int C2 = 461845907;
    private static final double DESIRED_LOAD_FACTOR = 0.5;

    private SmallCharMatcher(char[] cArray, long l, boolean bl, String string) {
        super(string);
        this.table = cArray;
        this.filter = l;
        this.containsZero = bl;
    }

    static int smear(int n) {
        return 461845907 * Integer.rotateLeft(n * -862048943, 15);
    }

    private boolean checkFilter(int n) {
        return 1L == (1L & this.filter >> n);
    }

    @VisibleForTesting
    static int chooseTableSize(int n) {
        if (n == 1) {
            return 1;
        }
        int n2 = Integer.highestOneBit(n - 1) << 1;
        while ((double)n2 * 0.5 < (double)n) {
            n2 <<= 1;
        }
        return n2;
    }

    static CharMatcher from(BitSet bitSet, String string) {
        long l = 0L;
        int n = bitSet.cardinality();
        boolean bl = bitSet.get(1);
        char[] cArray = new char[SmallCharMatcher.chooseTableSize(n)];
        int n2 = cArray.length - 1;
        int n3 = bitSet.nextSetBit(0);
        while (n3 != -1) {
            l |= 1L << n3;
            int n4 = SmallCharMatcher.smear(n3) & n2;
            while (true) {
                if (cArray[n4] == '\u0000') break;
                n4 = n4 + 1 & n2;
            }
            cArray[n4] = (char)n3;
            n3 = bitSet.nextSetBit(n3 + 1);
        }
        return new SmallCharMatcher(cArray, l, bl, string);
    }

    @Override
    public boolean matches(char c) {
        int n;
        if (c == '\u0000') {
            return this.containsZero;
        }
        if (!this.checkFilter(c)) {
            return true;
        }
        int n2 = this.table.length - 1;
        int n3 = n = SmallCharMatcher.smear(c) & n2;
        do {
            if (this.table[n3] == '\u0000') {
                return true;
            }
            if (this.table[n3] != c) continue;
            return false;
        } while ((n3 = n3 + 1 & n2) != n);
        return true;
    }

    @Override
    void setBits(BitSet bitSet) {
        if (this.containsZero) {
            bitSet.set(0);
        }
        for (char c : this.table) {
            if (c == '\u0000') continue;
            bitSet.set(c);
        }
    }
}

