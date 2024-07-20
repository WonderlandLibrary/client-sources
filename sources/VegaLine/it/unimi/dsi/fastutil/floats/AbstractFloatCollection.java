/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.floats.FloatIterators;
import it.unimi.dsi.fastutil.objects.ObjectIterators;
import java.lang.reflect.Array;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

public abstract class AbstractFloatCollection
extends AbstractCollection<Float>
implements FloatCollection {
    protected AbstractFloatCollection() {
    }

    @Override
    public float[] toArray(float[] a) {
        return this.toFloatArray(a);
    }

    @Override
    public float[] toFloatArray() {
        return this.toFloatArray(null);
    }

    @Override
    public float[] toFloatArray(float[] a) {
        if (a == null || a.length < this.size()) {
            a = new float[this.size()];
        }
        FloatIterators.unwrap(this.iterator(), a);
        return a;
    }

    @Override
    public boolean addAll(FloatCollection c) {
        boolean retVal = false;
        FloatIterator i = c.iterator();
        int n = c.size();
        while (n-- != 0) {
            if (!this.add(i.nextFloat())) continue;
            retVal = true;
        }
        return retVal;
    }

    @Override
    public boolean containsAll(FloatCollection c) {
        FloatIterator i = c.iterator();
        int n = c.size();
        while (n-- != 0) {
            if (this.contains(i.nextFloat())) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean retainAll(FloatCollection c) {
        boolean retVal = false;
        int n = this.size();
        FloatIterator i = this.iterator();
        while (n-- != 0) {
            if (c.contains(i.nextFloat())) continue;
            i.remove();
            retVal = true;
        }
        return retVal;
    }

    @Override
    public boolean removeAll(FloatCollection c) {
        boolean retVal = false;
        int n = c.size();
        FloatIterator i = c.iterator();
        while (n-- != 0) {
            if (!this.rem(i.nextFloat())) continue;
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
    public boolean addAll(Collection<? extends Float> c) {
        boolean retVal = false;
        Iterator<? extends Float> i = c.iterator();
        int n = c.size();
        while (n-- != 0) {
            if (!this.add(i.next())) continue;
            retVal = true;
        }
        return retVal;
    }

    @Override
    public boolean add(float k) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public FloatIterator floatIterator() {
        return this.iterator();
    }

    @Override
    public abstract FloatIterator iterator();

    @Override
    public boolean remove(Object ok) {
        if (ok == null) {
            return false;
        }
        return this.rem(((Float)ok).floatValue());
    }

    @Override
    public boolean add(Float o) {
        return this.add(o.floatValue());
    }

    public boolean rem(Object o) {
        if (o == null) {
            return false;
        }
        return this.rem(((Float)o).floatValue());
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) {
            return false;
        }
        return this.contains(((Float)o).floatValue());
    }

    @Override
    public boolean contains(float k) {
        FloatIterator iterator = this.iterator();
        while (iterator.hasNext()) {
            if (k != iterator.nextFloat()) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean rem(float k) {
        FloatIterator iterator = this.iterator();
        while (iterator.hasNext()) {
            if (k != iterator.nextFloat()) continue;
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
        FloatIterator i = this.iterator();
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
        FloatIterator i = this.iterator();
        int n = this.size();
        boolean first = true;
        s.append("{");
        while (n-- != 0) {
            if (first) {
                first = false;
            } else {
                s.append(", ");
            }
            float k = i.nextFloat();
            s.append(String.valueOf(k));
        }
        s.append("}");
        return s.toString();
    }
}

