/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.Function;

public interface Long2LongFunction
extends Function<Long, Long> {
    @Override
    public long put(long var1, long var3);

    public long get(long var1);

    public long remove(long var1);

    public boolean containsKey(long var1);

    public void defaultReturnValue(long var1);

    public long defaultReturnValue();
}

