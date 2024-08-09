/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.Size64;
import it.unimi.dsi.fastutil.doubles.DoubleBigListIterator;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import java.util.Iterator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface DoubleBigList
extends BigList<Double>,
DoubleCollection,
Size64,
Comparable<BigList<? extends Double>> {
    @Override
    public DoubleBigListIterator iterator();

    public DoubleBigListIterator listIterator();

    public DoubleBigListIterator listIterator(long var1);

    public DoubleBigList subList(long var1, long var3);

    public void getElements(long var1, double[][] var3, long var4, long var6);

    public void removeElements(long var1, long var3);

    public void addElements(long var1, double[][] var3);

    public void addElements(long var1, double[][] var3, long var4, long var6);

    @Override
    public void add(long var1, double var3);

    public boolean addAll(long var1, DoubleCollection var3);

    public boolean addAll(long var1, DoubleBigList var3);

    public boolean addAll(DoubleBigList var1);

    public double getDouble(long var1);

    public double removeDouble(long var1);

    @Override
    public double set(long var1, double var3);

    public long indexOf(double var1);

    public long lastIndexOf(double var1);

    @Override
    @Deprecated
    public void add(long var1, Double var3);

    @Override
    @Deprecated
    public Double get(long var1);

    @Override
    @Deprecated
    public long indexOf(Object var1);

    @Override
    @Deprecated
    public long lastIndexOf(Object var1);

    @Override
    @Deprecated
    public Double remove(long var1);

    @Override
    @Deprecated
    public Double set(long var1, Double var3);

    @Override
    default public BigList subList(long l, long l2) {
        return this.subList(l, l2);
    }

    @Override
    default public BigListIterator listIterator(long l) {
        return this.listIterator(l);
    }

    @Override
    default public BigListIterator listIterator() {
        return this.listIterator();
    }

    @Override
    @Deprecated
    default public void add(long l, Object object) {
        this.add(l, (Double)object);
    }

    @Override
    @Deprecated
    default public Object set(long l, Object object) {
        return this.set(l, (Double)object);
    }

    @Override
    @Deprecated
    default public Object remove(long l) {
        return this.remove(l);
    }

    @Override
    @Deprecated
    default public Object get(long l) {
        return this.get(l);
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

