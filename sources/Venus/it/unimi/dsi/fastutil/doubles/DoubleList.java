/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleListIterator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface DoubleList
extends List<Double>,
Comparable<List<? extends Double>>,
DoubleCollection {
    @Override
    public DoubleListIterator iterator();

    public DoubleListIterator listIterator();

    public DoubleListIterator listIterator(int var1);

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

    @Override
    @Deprecated
    default public void add(int n, Double d) {
        this.add(n, (double)d);
    }

    public boolean addAll(int var1, DoubleCollection var2);

    public boolean addAll(int var1, DoubleList var2);

    public boolean addAll(DoubleList var1);

    @Override
    public double set(int var1, double var2);

    public double getDouble(int var1);

    public int indexOf(double var1);

    public int lastIndexOf(double var1);

    @Override
    @Deprecated
    default public boolean contains(Object object) {
        return DoubleCollection.super.contains(object);
    }

    @Override
    @Deprecated
    default public Double get(int n) {
        return this.getDouble(n);
    }

    @Override
    @Deprecated
    default public int indexOf(Object object) {
        return this.indexOf((Double)object);
    }

    @Override
    @Deprecated
    default public int lastIndexOf(Object object) {
        return this.lastIndexOf((Double)object);
    }

    @Override
    @Deprecated
    default public boolean add(Double d) {
        return this.add((double)d);
    }

    public double removeDouble(int var1);

    @Override
    @Deprecated
    default public boolean remove(Object object) {
        return DoubleCollection.super.remove(object);
    }

    @Override
    @Deprecated
    default public Double remove(int n) {
        return this.removeDouble(n);
    }

    @Override
    @Deprecated
    default public Double set(int n, Double d) {
        return this.set(n, (double)d);
    }

    @Override
    default public List subList(int n, int n2) {
        return this.subList(n, n2);
    }

    @Override
    default public ListIterator listIterator(int n) {
        return this.listIterator(n);
    }

    @Override
    default public ListIterator listIterator() {
        return this.listIterator();
    }

    @Override
    @Deprecated
    default public Object remove(int n) {
        return this.remove(n);
    }

    @Override
    @Deprecated
    default public void add(int n, Object object) {
        this.add(n, (Double)object);
    }

    @Override
    @Deprecated
    default public Object set(int n, Object object) {
        return this.set(n, (Double)object);
    }

    @Override
    @Deprecated
    default public Object get(int n) {
        return this.get(n);
    }

    @Override
    @Deprecated
    default public boolean add(Object object) {
        return this.add((Double)object);
    }

    @Override
    default public Iterator iterator() {
        return this.iterator();
    }

    @Override
    default public DoubleIterator iterator() {
        return this.iterator();
    }
}

