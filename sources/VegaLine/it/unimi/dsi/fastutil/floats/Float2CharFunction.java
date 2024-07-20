/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.Function;

public interface Float2CharFunction
extends Function<Float, Character> {
    @Override
    public char put(float var1, char var2);

    public char get(float var1);

    public char remove(float var1);

    public boolean containsKey(float var1);

    public void defaultReturnValue(char var1);

    public char defaultReturnValue();
}

