/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Function;

public interface Reference2CharFunction<K>
extends Function<K, Character> {
    @Override
    public char put(K var1, char var2);

    public char getChar(Object var1);

    public char removeChar(Object var1);

    public void defaultReturnValue(char var1);

    public char defaultReturnValue();
}

