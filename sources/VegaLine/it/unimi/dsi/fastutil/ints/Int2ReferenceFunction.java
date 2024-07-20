/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.Function;

public interface Int2ReferenceFunction<V>
extends Function<Integer, V> {
    @Override
    public V put(int var1, V var2);

    public V get(int var1);

    public V remove(int var1);

    public boolean containsKey(int var1);

    public void defaultReturnValue(V var1);

    public V defaultReturnValue();
}

