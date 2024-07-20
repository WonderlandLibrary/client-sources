/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.Function;

public interface Byte2ObjectFunction<V>
extends Function<Byte, V> {
    @Override
    public V put(byte var1, V var2);

    public V get(byte var1);

    public V remove(byte var1);

    public boolean containsKey(byte var1);

    public void defaultReturnValue(V var1);

    public V defaultReturnValue();
}

