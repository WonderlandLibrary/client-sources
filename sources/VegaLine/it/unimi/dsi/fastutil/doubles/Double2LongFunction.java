/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.Function;

public interface Double2LongFunction
extends Function<Double, Long> {
    @Override
    public long put(double var1, long var3);

    public long get(double var1);

    public long remove(double var1);

    public boolean containsKey(double var1);

    public void defaultReturnValue(long var1);

    public long defaultReturnValue();
}

