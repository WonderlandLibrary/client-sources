/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.Function;

public interface Char2BooleanFunction
extends Function<Character, Boolean> {
    @Override
    public boolean put(char var1, boolean var2);

    public boolean get(char var1);

    public boolean remove(char var1);

    public boolean containsKey(char var1);

    public void defaultReturnValue(boolean var1);

    public boolean defaultReturnValue();
}

