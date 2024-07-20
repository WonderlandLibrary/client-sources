/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.Function;

public interface Double2IntFunction
extends Function<Double, Integer> {
    @Override
    public int put(double var1, int var3);

    public int get(double var1);

    public int remove(double var1);

    public boolean containsKey(double var1);

    public void defaultReturnValue(int var1);

    public int defaultReturnValue();
}

