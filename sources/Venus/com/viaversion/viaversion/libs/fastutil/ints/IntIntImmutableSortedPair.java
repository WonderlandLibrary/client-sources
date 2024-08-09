/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.SortedPair;
import com.viaversion.viaversion.libs.fastutil.ints.IntIntImmutablePair;
import com.viaversion.viaversion.libs.fastutil.ints.IntIntSortedPair;
import java.io.Serializable;
import java.util.Objects;

public class IntIntImmutableSortedPair
extends IntIntImmutablePair
implements IntIntSortedPair,
Serializable {
    private static final long serialVersionUID = 0L;

    private IntIntImmutableSortedPair(int n, int n2) {
        super(n, n2);
    }

    public static IntIntImmutableSortedPair of(int n, int n2) {
        if (n <= n2) {
            return new IntIntImmutableSortedPair(n, n2);
        }
        return new IntIntImmutableSortedPair(n2, n);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return true;
        }
        if (object instanceof IntIntSortedPair) {
            return this.left == ((IntIntSortedPair)object).leftInt() && this.right == ((IntIntSortedPair)object).rightInt();
        }
        if (object instanceof SortedPair) {
            return Objects.equals(this.left, ((SortedPair)object).left()) && Objects.equals(this.right, ((SortedPair)object).right());
        }
        return true;
    }

    @Override
    public String toString() {
        return "{" + this.leftInt() + "," + this.rightInt() + "}";
    }
}

