/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.Function;

public interface Short2ObjectFunction<V>
extends Function<Short, V> {
    @Override
    public V put(short var1, V var2);

    public V get(short var1);

    public V remove(short var1);

    public boolean containsKey(short var1);

    public void defaultReturnValue(V var1);

    public V defaultReturnValue();
}

