/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.booleans;

import java.util.Comparator;

@FunctionalInterface
public interface BooleanComparator
extends Comparator<Boolean> {
    @Override
    public int compare(boolean var1, boolean var2);

    @Override
    @Deprecated
    default public int compare(Boolean bl, Boolean bl2) {
        return this.compare((boolean)bl, (boolean)bl2);
    }

    @Override
    @Deprecated
    default public int compare(Object object, Object object2) {
        return this.compare((Boolean)object, (Boolean)object2);
    }
}

