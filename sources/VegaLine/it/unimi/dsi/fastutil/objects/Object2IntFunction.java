/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Function;

public interface Object2IntFunction<K>
extends Function<K, Integer> {
    @Override
    public int put(K var1, int var2);

    public int getInt(Object var1);

    public int removeInt(Object var1);

    public void defaultReturnValue(int var1);

    public int defaultReturnValue();
}

