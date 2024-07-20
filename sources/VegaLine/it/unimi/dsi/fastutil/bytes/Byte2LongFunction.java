/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.Function;

public interface Byte2LongFunction
extends Function<Byte, Long> {
    @Override
    public long put(byte var1, long var2);

    public long get(byte var1);

    public long remove(byte var1);

    public boolean containsKey(byte var1);

    public void defaultReturnValue(long var1);

    public long defaultReturnValue();
}

