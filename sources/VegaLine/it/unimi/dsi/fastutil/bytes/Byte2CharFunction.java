/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.Function;

public interface Byte2CharFunction
extends Function<Byte, Character> {
    @Override
    public char put(byte var1, char var2);

    public char get(byte var1);

    public char remove(byte var1);

    public boolean containsKey(byte var1);

    public void defaultReturnValue(char var1);

    public char defaultReturnValue();
}

