/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.Function;

public interface Long2CharFunction
extends Function<Long, Character> {
    @Override
    public char put(long var1, char var3);

    public char get(long var1);

    public char remove(long var1);

    public boolean containsKey(long var1);

    public void defaultReturnValue(char var1);

    public char defaultReturnValue();
}

