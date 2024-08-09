/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.SortedPair;
import com.viaversion.viaversion.libs.fastutil.ints.IntIntImmutableSortedPair;
import com.viaversion.viaversion.libs.fastutil.ints.IntIntPair;
import java.io.Serializable;

public interface IntIntSortedPair
extends IntIntPair,
SortedPair<Integer>,
Serializable {
    public static IntIntSortedPair of(int n, int n2) {
        return IntIntImmutableSortedPair.of(n, n2);
    }

    default public boolean contains(int n) {
        return n == this.leftInt() || n == this.rightInt();
    }

    @Override
    @Deprecated
    default public boolean contains(Object object) {
        if (object == null) {
            return true;
        }
        return this.contains((Integer)object);
    }
}

