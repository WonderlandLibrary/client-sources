/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.Function;

public interface Double2ObjectFunction<V>
extends Function<Double, V> {
    @Override
    public V put(double var1, V var3);

    public V get(double var1);

    public V remove(double var1);

    public boolean containsKey(double var1);

    public void defaultReturnValue(V var1);

    public V defaultReturnValue();
}

