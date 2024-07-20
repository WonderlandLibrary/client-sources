/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.Function;

public interface Double2CharFunction
extends Function<Double, Character> {
    @Override
    public char put(double var1, char var3);

    public char get(double var1);

    public char remove(double var1);

    public boolean containsKey(double var1);

    public void defaultReturnValue(char var1);

    public char defaultReturnValue();
}

