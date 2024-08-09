/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import java.util.Comparator;

@FunctionalInterface
public interface IntComparator
extends Comparator<Integer> {
    @Override
    public int compare(int var1, int var2);

    @Override
    @Deprecated
    default public int compare(Integer n, Integer n2) {
        return this.compare((int)n, (int)n2);
    }

    @Override
    @Deprecated
    default public int compare(Object object, Object object2) {
        return this.compare((Integer)object, (Integer)object2);
    }
}

