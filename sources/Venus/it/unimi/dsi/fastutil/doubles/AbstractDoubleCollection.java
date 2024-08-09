/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleIterators;
import java.util.AbstractCollection;
import java.util.Iterator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractDoubleCollection
extends AbstractCollection<Double>
implements DoubleCollection {
    protected AbstractDoubleCollection() {
    }

    @Override
    public abstract DoubleIterator iterator();

    @Override
    public boolean add(double d) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(double d) {
        DoubleIterator doubleIterator = this.iterator();
        while (doubleIterator.hasNext()) {
            if (d != doubleIterator.nextDouble()) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean rem(double d) {
        DoubleIterator doubleIterator = this.iterator();
        while (doubleIterator.hasNext()) {
            if (d != doubleIterator.nextDouble()) continue;
            doubleIterator.remove();
            return false;
        }
        return true;
    }

    @Override
    @Deprecated
    public boolean add(Double d) {
        return DoubleCollection.super.add(d);
    }

    @Override
    @Deprecated
    public boolean contains(Object object) {
        return DoubleCollection.super.contains(object);
    }

    @Override
    @Deprecated
    public boolean remove(Object object) {
        return DoubleCollection.super.remove(object);
    }

    @Override
    public double[] toArray(double[] dArray) {
        if (dArray == null || dArray.length < this.size()) {
            dArray = new double[this.size()];
        }
        DoubleIterators.unwrap(this.iterator(), dArray);
        return dArray;
    }

    @Override
    public double[] toDoubleArray() {
        return this.toArray((double[])null);
    }

    @Override
    @Deprecated
    public double[] toDoubleArray(double[] dArray) {
        return this.toArray(dArray);
    }

    @Override
    public boolean addAll(DoubleCollection doubleCollection) {
        boolean bl = false;
        DoubleIterator doubleIterator = doubleCollection.iterator();
        while (doubleIterator.hasNext()) {
            if (!this.add(doubleIterator.nextDouble())) continue;
            bl = true;
        }
        return bl;
    }

    @Override
    public boolean containsAll(DoubleCollection doubleCollection) {
        DoubleIterator doubleIterator = doubleCollection.iterator();
        while (doubleIterator.hasNext()) {
            if (this.contains(doubleIterator.nextDouble())) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAll(DoubleCollection doubleCollection) {
        boolean bl = false;
        DoubleIterator doubleIterator = doubleCollection.iterator();
        while (doubleIterator.hasNext()) {
            if (!this.rem(doubleIterator.nextDouble())) continue;
            bl = true;
        }
        return bl;
    }

    @Override
    public boolean retainAll(DoubleCollection doubleCollection) {
        boolean bl = false;
        DoubleIterator doubleIterator = this.iterator();
        while (doubleIterator.hasNext()) {
            if (doubleCollection.contains(doubleIterator.nextDouble())) continue;
            doubleIterator.remove();
            bl = true;
        }
        return bl;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        DoubleIterator doubleIterator = this.iterator();
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            double d = doubleIterator.nextDouble();
            stringBuilder.append(String.valueOf(d));
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    @Deprecated
    public boolean add(Object object) {
        return this.add((Double)object);
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }
}

