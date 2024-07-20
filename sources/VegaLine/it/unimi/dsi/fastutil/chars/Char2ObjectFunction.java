/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.Function;

public interface Char2ObjectFunction<V>
extends Function<Character, V> {
    @Override
    public V put(char var1, V var2);

    public V get(char var1);

    public V remove(char var1);

    public boolean containsKey(char var1);

    public void defaultReturnValue(V var1);

    public V defaultReturnValue();
}

