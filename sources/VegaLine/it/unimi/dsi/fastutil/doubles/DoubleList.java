/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleListIterator;
import java.util.List;

public interface DoubleList
extends List<Double>,
Comparable<List<? extends Double>>,
DoubleCollection {
    @Override
    public DoubleListIterator iterator();

    @Deprecated
    public DoubleListIterator doubleListIterator();

    @Deprecated
    public DoubleListIterator doubleListIterator(int var1);

    public DoubleListIterator listIterator();

    public DoubleListIterator listIterator(int var1);

    @Deprecated
    public DoubleList doubleSubList(int var1, int var2);

    public DoubleList subList(int var1, int var2);

    public void size(int var1);

    public void getElements(int var1, double[] var2, int var3, int var4);

    public void removeElements(int var1, int var2);

    public void addElements(int var1, double[] var2);

    public void addElements(int var1, double[] var2, int var3, int var4);

    @Override
    public boolean add(double var1);

    @Override
    public void add(int var1, double var2);

    public boolean addAll(int var1, DoubleCollection var2);

    public boolean addAll(int var1, DoubleList var2);

    public boolean addAll(DoubleList var1);

    public double getDouble(int var1);

    public int indexOf(double var1);

    public int lastIndexOf(double var1);

    public double removeDouble(int var1);

    @Override
    public double set(int var1, double var2);
}

