/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.Function;

public interface Char2FloatFunction
extends Function<Character, Float> {
    @Override
    public float put(char var1, float var2);

    public float get(char var1);

    public float remove(char var1);

    public boolean containsKey(char var1);

    public void defaultReturnValue(float var1);

    public float defaultReturnValue();
}

