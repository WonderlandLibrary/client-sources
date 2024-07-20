/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.Function;

public interface Long2ByteFunction
extends Function<Long, Byte> {
    @Override
    public byte put(long var1, byte var3);

    public byte get(long var1);

    public byte remove(long var1);

    public boolean containsKey(long var1);

    public void defaultReturnValue(byte var1);

    public byte defaultReturnValue();
}

