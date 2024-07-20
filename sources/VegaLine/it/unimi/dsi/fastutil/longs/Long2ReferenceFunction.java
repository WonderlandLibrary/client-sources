/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.Function;

public interface Long2ReferenceFunction<V>
extends Function<Long, V> {
    @Override
    public V put(long var1, V var3);

    public V get(long var1);

    public V remove(long var1);

    public boolean containsKey(long var1);

    public void defaultReturnValue(V var1);

    public V defaultReturnValue();
}

