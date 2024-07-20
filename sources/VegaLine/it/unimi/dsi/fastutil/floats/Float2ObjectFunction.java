/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.Function;

public interface Float2ObjectFunction<V>
extends Function<Float, V> {
    @Override
    public V put(float var1, V var2);

    public V get(float var1);

    public V remove(float var1);

    public boolean containsKey(float var1);

    public void defaultReturnValue(V var1);

    public V defaultReturnValue();
}

