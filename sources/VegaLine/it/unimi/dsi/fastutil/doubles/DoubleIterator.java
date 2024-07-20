/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import java.util.Iterator;

public interface DoubleIterator
extends Iterator<Double> {
    public double nextDouble();

    public int skip(int var1);
}

