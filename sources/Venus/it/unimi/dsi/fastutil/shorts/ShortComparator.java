/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import java.util.Comparator;

@FunctionalInterface
public interface ShortComparator
extends Comparator<Short> {
    @Override
    public int compare(short var1, short var2);

    @Override
    @Deprecated
    default public int compare(Short s, Short s2) {
        return this.compare((short)s, (short)s2);
    }

    @Override
    @Deprecated
    default public int compare(Object object, Object object2) {
        return this.compare((Short)object, (Short)object2);
    }
}

