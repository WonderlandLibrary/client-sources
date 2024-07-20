/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntIterators;
import it.unimi.dsi.fastutil.objects.ObjectIterators;
import java.lang.reflect.Array;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

public abstract class AbstractIntCollection
extends AbstractCollection<Integer>
implements IntCollection {
    protected AbstractIntCollection() {
    }

    @Override
    public int[] toArray(int[] a) {
        return this.toIntArray(a);
    }

    @Override
    public int[] toIntArray() {
        return this.toIntArray(null);
    }

    @Override
    public int[] toIntArray(int[] a) {
        if (a == null || a.length < this.size()) {
            a = new int[this.size()];
        }
        IntIterators.unwrap(this.iterator(), a);
        return a;
    }

    @Override
    public boolean addAll(IntCollection c) {
        boolean retVal = false;
        IntIterator i = c.iterator();
        int n = c.size();
        while (n-- != 0) {
            if (!this.add(i.nextInt())) continue;
            retVal = true;
        }
        return retVal;
    }

    @Override
    public boolean containsAll(IntCollection c) {
        IntIterator i = c.iterator();
        int n = c.size();
        while (n-- != 0) {
            if (this.contains(i.nextInt())) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean retainAll(IntCollection c) {
        boolean retVal = false;
        int n = this.size();
        IntIterator i = this.iterator();
        while (n-- != 0) {
            if (c.contains(i.nextInt())) continue;
            i.remove();
            retVal = true;
        }
        return retVal;
    }

    @Override
    public boolean removeAll(IntCollection c) {
        boolean retVal = false;
        int n = c.size();
        IntIterator i = c.iterator();
        while (n-- != 0) {
            if (!this.rem(i.nextInt())) continue;
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
    public boolean addAll(Collection<? extends Integer> c) {
        boolean retVal = false;
        Iterator<? extends Integer> i = c.iterator();
        int n = c.size();
        while (n-- != 0) {
            if (!this.add(i.next())) continue;
            retVal = true;
        }
        return retVal;
    }

    @Override
    public boolean add(int k) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public IntIterator intIterator() {
        return this.iterator();
    }

    @Override
    public abstract IntIterator iterator();

    @Override
    public boolean remove(Object ok) {
        if (ok == null) {
            return false;
        }
        return this.rem((Integer)ok);
    }

    @Override
    public boolean add(Integer o) {
        return this.add((int)o);
    }

    public boolean rem(Object o) {
        if (o == null) {
            return false;
        }
        return this.rem((Integer)o);
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) {
            return false;
        }
        return this.contains((Integer)o);
    }

    @Override
    public boolean contains(int k) {
        IntIterator iterator = this.iterator();
        while (iterator.hasNext()) {
            if (k != iterator.nextInt()) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean rem(int k) {
        IntIterator iterator = this.iterator();
        while (iterator.hasNext()) {
            if (k != iterator.nextInt()) continue;
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
        IntIterator i = this.iterator();
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
        IntIterator i = this.iterator();
        int n = this.size();
        boolean first = true;
        s.append("{");
        while (n-- != 0) {
            if (first) {
                first = false;
            } else {
                s.append(", ");
            }
            int k = i.nextInt();
            s.append(String.valueOf(k));
        }
        s.append("}");
        return s.toString();
    }
}

