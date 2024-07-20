/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterators;
import java.lang.reflect.Array;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

public abstract class AbstractObjectCollection<K>
extends AbstractCollection<K>
implements ObjectCollection<K> {
    protected AbstractObjectCollection() {
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
    public boolean addAll(Collection<? extends K> c) {
        boolean retVal = false;
        Iterator<K> i = c.iterator();
        int n = c.size();
        while (n-- != 0) {
            if (!this.add(i.next())) continue;
            retVal = true;
        }
        return retVal;
    }

    @Override
    public boolean add(K k) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public ObjectIterator<K> objectIterator() {
        return this.iterator();
    }

    @Override
    public abstract ObjectIterator<K> iterator();

    @Override
    public boolean remove(Object o) {
        return this.rem(o);
    }

    public boolean rem(Object k) {
        Iterator iterator = this.iterator();
        while (iterator.hasNext()) {
            if (k != iterator.next()) continue;
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
        Iterator i = this.iterator();
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
        Iterator i = this.iterator();
        int n = this.size();
        boolean first = true;
        s.append("{");
        while (n-- != 0) {
            if (first) {
                first = false;
            } else {
                s.append(", ");
            }
            Object k = i.next();
            if (this == k) {
                s.append("(this collection)");
                continue;
            }
            s.append(String.valueOf(k));
        }
        s.append("}");
        return s.toString();
    }
}

