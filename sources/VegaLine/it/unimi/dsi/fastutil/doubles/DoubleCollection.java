/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.DoubleIterable;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import java.util.Collection;

public interface DoubleCollection
extends Collection<Double>,
DoubleIterable {
    @Override
    public DoubleIterator iterator();

    @Deprecated
    public DoubleIterator doubleIterator();

    @Override
    public <T> T[] toArray(T[] var1);

    public boolean contains(double var1);

    public double[] toDoubleArray();

    public double[] toDoubleArray(double[] var1);

    public double[] toArray(double[] var1);

    @Override
    public boolean add(double var1);

    public boolean rem(double var1);

    public boolean addAll(DoubleCollection var1);

    public boolean containsAll(DoubleCollection var1);

    public boolean removeAll(DoubleCollection var1);

    public boolean retainAll(DoubleCollection var1);
}

