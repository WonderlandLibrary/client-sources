/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import javax.annotation.Nullable;

@GwtCompatible
final class Hashing {
    private static final int C1 = -862048943;
    private static final int C2 = 461845907;
    private static final int MAX_TABLE_SIZE = 0x40000000;

    private Hashing() {
    }

    static int smear(int n) {
        return 461845907 * Integer.rotateLeft(n * -862048943, 15);
    }

    static int smearedHash(@Nullable Object object) {
        return Hashing.smear(object == null ? 0 : object.hashCode());
    }

    static int closedTableSize(int n, double d) {
        int n2;
        if ((n = Math.max(n, 2)) > (int)(d * (double)(n2 = Integer.highestOneBit(n)))) {
            return (n2 <<= 1) > 0 ? n2 : 0x40000000;
        }
        return n2;
    }

    static boolean needsResizing(int n, int n2, double d) {
        return (double)n > d * (double)n2 && n2 < 0x40000000;
    }
}

