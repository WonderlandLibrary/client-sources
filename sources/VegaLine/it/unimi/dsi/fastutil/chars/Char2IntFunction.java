/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.Function;

public interface Char2IntFunction
extends Function<Character, Integer> {
    @Override
    public int put(char var1, int var2);

    public int get(char var1);

    public int remove(char var1);

    public boolean containsKey(char var1);

    public void defaultReturnValue(int var1);

    public int defaultReturnValue();
}

