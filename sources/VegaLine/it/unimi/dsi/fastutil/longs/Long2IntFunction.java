/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.Function;

public interface Long2IntFunction
extends Function<Long, Integer> {
    @Override
    public int put(long var1, int var3);

    public int get(long var1);

    public int remove(long var1);

    public boolean containsKey(long var1);

    public void defaultReturnValue(int var1);

    public int defaultReturnValue();
}

