/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleIterators;
import it.unimi.dsi.fastutil.objects.ObjectIterators;
import java.lang.reflect.Array;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

public abstract class AbstractDoubleCollection
extends AbstractCollection<Double>
implements DoubleCollection {
    protected AbstractDoubleCollection() {
    }

    @Override
    public double[] toArray(double[] a) {
        return this.toDoubleArray(a);
    }

    @Override
    public double[] toDoubleArray() {
        return this.toDoubleArray(null);
    }

    @Override
    public double[] toDoubleArray(double[] a) {
        if (a == null || a.length < this.size()) {
            a = new double[this.size()];
        }
        DoubleIterators.unwrap(this.iterator(), a);
        return a;
    }

    @Override
    public boolean addAll(DoubleCollection c) {
        boolean retVal = false;
        DoubleIterator i = c.iterator();
        int n = c.size();
        while (n-- != 0) {
            if (!this.add(i.nextDouble())) continue;
            retVal = true;
        }
        return retVal;
    }

    @Override
    public boolean containsAll(DoubleCollection c) {
        DoubleIterator i = c.iterator();
        int n = c.size();
        while (n-- != 0) {
            if (this.contains(i.nextDouble())) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean retainAll(DoubleCollection c) {
        boolean retVal = false;
        int n = this.size();
        DoubleIterator i = this.iterator();
        while (n-- != 0) {
            if (c.contains(i.nextDouble())) continue;
            i.remove();
            retVal = true;
        }
        return retVal;
    }

    @Override
    public boolean removeAll(DoubleCollection c) {
        boolean retVal = false;
        int n = c.size();
        DoubleIterator i = c.iterator();
        while (n-- != 0) {
            if (!this.rem(i.nextDouble())) continue;
            retVal = true;
        }
        return retVal;
    }

    @Override
    public Object[] toArray() {
        Object[] a = new Object[this.size()];
        ObjectIterators.unwrap(this.iterator(), a);
        return a;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        int size = this.size();
        if (a.length < size) {
            a = (Object[])Array.newInstance(a.getClass().getComponentType(), size);
        }
        ObjectIterators.unwrap(this.iterator(), a);
        if (size < a.length) {
            a[size] = null;
        }
        return a;
    }

    @Override
    public boolean addAll(Collection<? extends Double> c) {
        boolean retVal = false;
        Iterator<? extends Double> i = c.iterator();
        int n = c.size();
        while (n-- != 0) {
            if (!this.add(i.next())) continue;
            retVal = true;
        }
        return retVal;
    }

    @Override
    public boolean add(double k) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public DoubleIterator doubleIterator() {
        return this.iterator();
    }

    @Override
    public abstract DoubleIterator iterator();

    @Override
    public boolean remove(Object ok) {
        if (ok == null) {
            return false;
        }
        return this.rem((Double)ok);
    }

    @Override
    public boolean add(Double o) {
        return this.add((double)o);
    }

    public boolean rem(Object o) {
        if (o == null) {
            return false;
        }
        return this.rem((Double)o);
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) {
            return false;
        }
        return this.contains((Double)o);
    }

    @Override
    public boolean contains(double k) {
        DoubleIterator iterator = this.iterator();
        while (iterator.hasNext()) {
            if (k != iterator.nextDouble()) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean rem(double k) {
        DoubleIterator iterator = this.iterator();
        while (iterator.hasNext()) {
            if (k != iterator.nextDouble()) continue;
            iterator.remove();
            return true;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        int n = c.size();
        Iterator<?> i = c.iterator();
        while (n-- != 0) {
            if (this.contains(i.next())) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean retVal = false;
        int n = this.size();
        DoubleIterator i = this.iterator();
        while (n-- != 0) {
            if (c.contains(i.next())) continue;
            i.remove();
            retVal = true;
        }
        return retVal;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean retVal = false;
        int n = c.size();
        Iterator<?> i = c.iterator();
        while (n-- != 0) {
            if (!this.remove(i.next())) continue;
            retVal = true;
        }
        return retVal;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        DoubleIterator i = this.iterator();
        int n = this.size();
        boolean first = true;
        s.append("{");
        while (n-- != 0) {
            if (first) {
                first = false;
            } else {
                s.append(", ");
            }
            double k = i.nextDouble();
            s.append(String.valueOf(k));
        }
        s.append("}");
        return s.toString();
    }
}

