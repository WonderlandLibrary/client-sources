/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.Function;

public interface Short2CharFunction
extends Function<Short, Character> {
    @Override
    public char put(short var1, char var2);

    public char get(short var1);

    public char remove(short var1);

    public boolean containsKey(short var1);

    public void defaultReturnValue(char var1);

    public char defaultReturnValue();
}

