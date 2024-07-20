/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.Function;

public interface Char2CharFunction
extends Function<Character, Character> {
    @Override
    public char put(char var1, char var2);

    public char get(char var1);

    public char remove(char var1);

    public boolean containsKey(char var1);

    public void defaultReturnValue(char var1);

    public char defaultReturnValue();
}

