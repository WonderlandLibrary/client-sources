/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.Function;

public interface Char2ByteFunction
extends Function<Character, Byte> {
    @Override
    public byte put(char var1, byte var2);

    public byte get(char var1);

    public byte remove(char var1);

    public boolean containsKey(char var1);

    public void defaultReturnValue(byte var1);

    public byte defaultReturnValue();
}

