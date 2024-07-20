/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.Function;

public interface Short2ByteFunction
extends Function<Short, Byte> {
    @Override
    public byte put(short var1, byte var2);

    public byte get(short var1);

    public byte remove(short var1);

    public boolean containsKey(short var1);

    public void defaultReturnValue(byte var1);

    public byte defaultReturnValue();
}

