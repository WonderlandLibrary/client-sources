/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import java.util.Comparator;

@FunctionalInterface
public interface LongComparator
extends Comparator<Long> {
    @Override
    public int compare(long var1, long var3);

    @Override
    @Deprecated
    default public int compare(Long l, Long l2) {
        return this.compare((long)l, (long)l2);
    }

    @Override
    @Deprecated
    default public int compare(Object object, Object object2) {
        return this.compare((Long)object, (Long)object2);
    }
}

