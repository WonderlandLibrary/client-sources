/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.Function;

public interface Short2LongFunction
extends Function<Short, Long> {
    @Override
    public long put(short var1, long var2);

    public long get(short var1);

    public long remove(short var1);

    public boolean containsKey(short var1);

    public void defaultReturnValue(long var1);

    public long defaultReturnValue();
}

