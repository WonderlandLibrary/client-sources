/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongIterators;
import it.unimi.dsi.fastutil.objects.ObjectIterators;
import java.lang.reflect.Array;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

public abstract class AbstractLongCollection
extends AbstractCollection<Long>
implements LongCollection {
    protected AbstractLongCollection() {
    }

    @Override
    public long[] toArray(long[] a) {
        return this.toLongArray(a);
    }

    @Override
    public long[] toLongArray() {
        return this.toLongArray(null);
    }

    @Override
    public long[] toLongArray(long[] a) {
        if (a == null || a.length < this.size()) {
            a = new long[this.size()];
        }
        LongIterators.unwrap(this.iterator(), a);
        return a;
    }

    @Override
    public boolean addAll(LongCollection c) {
        boolean retVal = false;
        LongIterator i = c.iterator();
        int n = c.size();
        while (n-- != 0) {
            if (!this.add(i.nextLong())) continue;
            retVal = true;
        }
        return retVal;
    }

    @Override
    public boolean containsAll(LongCollection c) {
        LongIterator i = c.iterator();
        int n = c.size();
        while (n-- != 0) {
            if (this.contains(i.nextLong())) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean retainAll(LongCollection c) {
        boolean retVal = false;
        int n = this.size();
        LongIterator i = this.iterator();
        while (n-- != 0) {
            if (c.contains(i.nextLong())) continue;
            i.remove();
            retVal = true;
        }
        return retVal;
    }

    @Override
    public boolean removeAll(LongCollection c) {
        boolean retVal = false;
        int n = c.size();
        LongIterator i = c.iterator();
        while (n-- != 0) {
            if (!this.rem(i.nextLong())) continue;
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
    public boolean addAll(Collection<? extends Long> c) {
        boolean retVal = false;
        Iterator<? extends Long> i = c.iterator();
        int n = c.size();
        while (n-- != 0) {
            if (!this.add(i.next())) continue;
            retVal = true;
        }
        return retVal;
    }

    @Override
    public boolean add(long k) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public LongIterator longIterator() {
        return this.iterator();
    }

    @Override
    public abstract LongIterator iterator();

    @Override
    public boolean remove(Object ok) {
        if (ok == null) {
            return false;
        }
        return this.rem((Long)ok);
    }

    @Override
    public boolean add(Long o) {
        return this.add((long)o);
    }

    public boolean rem(Object o) {
        if (o == null) {
            return false;
        }
        return this.rem((Long)o);
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) {
            return false;
        }
        return this.contains((Long)o);
    }

    @Override
    public boolean contains(long k) {
        LongIterator iterator = this.iterator();
        while (iterator.hasNext()) {
            if (k != iterator.nextLong()) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean rem(long k) {
        LongIterator iterator = this.iterator();
        while (iterator.hasNext()) {
            if (k != iterator.nextLong()) continue;
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
        LongIterator i = this.iterator();
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
        LongIterator i = this.iterator();
        int n = this.size();
        boolean first = true;
        s.append("{");
        while (n-- != 0) {
            if (first) {
                first = false;
            } else {
                s.append(", ");
            }
            long k = i.nextLong();
            s.append(String.valueOf(k));
        }
        s.append("}");
        return s.toString();
    }
}

