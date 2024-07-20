/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.Function;

public interface Char2ShortFunction
extends Function<Character, Short> {
    @Override
    public short put(char var1, short var2);

    public short get(char var1);

    public short remove(char var1);

    public boolean containsKey(char var1);

    public void defaultReturnValue(short var1);

    public short defaultReturnValue();
}

