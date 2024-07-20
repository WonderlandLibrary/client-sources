/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.Function;

public interface Int2CharFunction
extends Function<Integer, Character> {
    @Override
    public char put(int var1, char var2);

    public char get(int var1);

    public char remove(int var1);

    public boolean containsKey(int var1);

    public void defaultReturnValue(char var1);

    public char defaultReturnValue();
}

