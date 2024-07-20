/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.Function;

public interface Char2LongFunction
extends Function<Character, Long> {
    @Override
    public long put(char var1, long var2);

    public long get(char var1);

    public long remove(char var1);

    public boolean containsKey(char var1);

    public void defaultReturnValue(long var1);

    public long defaultReturnValue();
}

