/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.Function;

public interface Int2LongFunction
extends Function<Integer, Long> {
    @Override
    public long put(int var1, long var2);

    public long get(int var1);

    public long remove(int var1);

    public boolean containsKey(int var1);

    public void defaultReturnValue(long var1);

    public long defaultReturnValue();
}

