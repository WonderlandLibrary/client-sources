/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.ints.IntComparators;
import java.io.Serializable;
import java.util.Comparator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@FunctionalInterface
public interface IntComparator
extends Comparator<Integer> {
    @Override
    public int compare(int var1, int var2);

    default public IntComparator reversed() {
        return IntComparators.oppositeComparator(this);
    }

    @Override
    @Deprecated
    default public int compare(Integer n, Integer n2) {
        return this.compare((int)n, (int)n2);
    }

    default public IntComparator thenComparing(IntComparator intComparator) {
        return (IntComparator & Serializable)(arg_0, arg_1) -> this.lambda$thenComparing$931d6fed$1(intComparator, arg_0, arg_1);
    }

    @Override
    default public Comparator<Integer> thenComparing(Comparator<? super Integer> comparator) {
        if (comparator instanceof IntComparator) {
            return this.thenComparing((IntComparator)comparator);
        }
        return Comparator.super.thenComparing(comparator);
    }

    @Override
    default public Comparator reversed() {
        return this.reversed();
    }

    @Override
    @Deprecated
    default public int compare(Object object, Object object2) {
        return this.compare((Integer)object, (Integer)object2);
    }

    private int lambda$thenComparing$931d6fed$1(IntComparator intComparator, int n, int n2) {
        int n3 = this.compare(n, n2);
        return n3 == 0 ? intComparator.compare(n, n2) : n3;
    }
}

