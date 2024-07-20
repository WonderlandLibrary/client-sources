/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Function;

public interface Reference2DoubleFunction<K>
extends Function<K, Double> {
    @Override
    public double put(K var1, double var2);

    public double getDouble(Object var1);

    public double removeDouble(Object var1);

    public void defaultReturnValue(double var1);

    public double defaultReturnValue();
}

