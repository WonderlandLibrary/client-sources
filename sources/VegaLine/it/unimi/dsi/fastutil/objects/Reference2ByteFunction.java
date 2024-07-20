/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Function;

public interface Reference2ByteFunction<K>
extends Function<K, Byte> {
    @Override
    public byte put(K var1, byte var2);

    public byte getByte(Object var1);

    public byte removeByte(Object var1);

    public void defaultReturnValue(byte var1);

    public byte defaultReturnValue();
}

