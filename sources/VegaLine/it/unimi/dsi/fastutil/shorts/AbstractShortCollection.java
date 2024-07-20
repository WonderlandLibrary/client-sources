/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.objects.ObjectIterators;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import it.unimi.dsi.fastutil.shorts.ShortIterators;
import java.lang.reflect.Array;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

public abstract class AbstractShortCollection
extends AbstractCollection<Short>
implements ShortCollection {
    protected AbstractShortCollection() {
    }

    @Override
    public short[] toArray(short[] a) {
        return this.toShortArray(a);
    }

    @Override
    public short[] toShortArray() {
        return this.toShortArray(null);
    }

    @Override
    public short[] toShortArray(short[] a) {
        if (a == null || a.length < this.size()) {
            a = new short[this.size()];
        }
        ShortIterators.unwrap(this.iterator(), a);
        return a;
    }

    @Override
    public boolean addAll(ShortCollection c) {
        boolean retVal = false;
        ShortIterator i = c.iterator();
        int n = c.size();
        while (n-- != 0) {
            if (!this.add(i.nextShort())) continue;
            retVal = true;
        }
        return retVal;
    }

    @Override
    public boolean containsAll(ShortCollection c) {
        ShortIterator i = c.iterator();
        int n = c.size();
        while (n-- != 0) {
            if (this.contains(i.nextShort())) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean retainAll(ShortCollection c) {
        boolean retVal = false;
        int n = this.size();
        ShortIterator i = this.iterator();
        while (n-- != 0) {
            if (c.contains(i.nextShort())) continue;
            i.remove();
            retVal = true;
        }
        return retVal;
    }

    @Override
    public boolean removeAll(ShortCollection c) {
        boolean retVal = false;
        int n = c.size();
        ShortIterator i = c.iterator();
        while (n-- != 0) {
            if (!this.rem(i.nextShort())) continue;
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
    public boolean addAll(Collection<? extends Short> c) {
        boolean retVal = false;
        Iterator<? extends Short> i = c.iterator();
        int n = c.size();
        while (n-- != 0) {
            if (!this.add(i.next())) continue;
            retVal = true;
        }
        return retVal;
    }

    @Override
    public boolean add(short k) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public ShortIterator shortIterator() {
        return this.iterator();
    }

    @Override
    public abstract ShortIterator iterator();

    @Override
    public boolean remove(Object ok) {
        if (ok == null) {
            return false;
        }
        return this.rem((Short)ok);
    }

    @Override
    public boolean add(Short o) {
        return this.add((short)o);
    }

    public boolean rem(Object o) {
        if (o == null) {
            return false;
        }
        return this.rem((Short)o);
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) {
            return false;
        }
        return this.contains((Short)o);
    }

    @Override
    public boolean contains(short k) {
        ShortIterator iterator = this.iterator();
        while (iterator.hasNext()) {
            if (k != iterator.nextShort()) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean rem(short k) {
        ShortIterator iterator = this.iterator();
        while (iterator.hasNext()) {
            if (k != iterator.nextShort()) continue;
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
        ShortIterator i = this.iterator();
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
        ShortIterator i = this.iterator();
        int n = this.size();
        boolean first = true;
        s.append("{");
        while (n-- != 0) {
            if (first) {
                first = false;
            } else {
                s.append(", ");
            }
            short k = i.nextShort();
            s.append(String.valueOf(k));
        }
        s.append("}");
        return s.toString();
    }
}

