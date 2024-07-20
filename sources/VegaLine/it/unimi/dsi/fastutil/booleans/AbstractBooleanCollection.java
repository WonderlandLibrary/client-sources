/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.booleans;

import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.booleans.BooleanIterators;
import it.unimi.dsi.fastutil.objects.ObjectIterators;
import java.lang.reflect.Array;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

public abstract class AbstractBooleanCollection
extends AbstractCollection<Boolean>
implements BooleanCollection {
    protected AbstractBooleanCollection() {
    }

    @Override
    public boolean[] toArray(boolean[] a) {
        return this.toBooleanArray(a);
    }

    @Override
    public boolean[] toBooleanArray() {
        return this.toBooleanArray(null);
    }

    @Override
    public boolean[] toBooleanArray(boolean[] a) {
        if (a == null || a.length < this.size()) {
            a = new boolean[this.size()];
        }
        BooleanIterators.unwrap(this.iterator(), a);
        return a;
    }

    @Override
    public boolean addAll(BooleanCollection c) {
        boolean retVal = false;
        BooleanIterator i = c.iterator();
        int n = c.size();
        while (n-- != 0) {
            if (!this.add(i.nextBoolean())) continue;
            retVal = true;
        }
        return retVal;
    }

    @Override
    public boolean containsAll(BooleanCollection c) {
        BooleanIterator i = c.iterator();
        int n = c.size();
        while (n-- != 0) {
            if (this.contains(i.nextBoolean())) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean retainAll(BooleanCollection c) {
        boolean retVal = false;
        int n = this.size();
        BooleanIterator i = this.iterator();
        while (n-- != 0) {
            if (c.contains(i.nextBoolean())) continue;
            i.remove();
            retVal = true;
        }
        return retVal;
    }

    @Override
    public boolean removeAll(BooleanCollection c) {
        boolean retVal = false;
        int n = c.size();
        BooleanIterator i = c.iterator();
        while (n-- != 0) {
            if (!this.rem(i.nextBoolean())) continue;
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
    public boolean addAll(Collection<? extends Boolean> c) {
        boolean retVal = false;
        Iterator<? extends Boolean> i = c.iterator();
        int n = c.size();
        while (n-- != 0) {
            if (!this.add(i.next())) continue;
            retVal = true;
        }
        return retVal;
    }

    @Override
    public boolean add(boolean k) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public BooleanIterator booleanIterator() {
        return this.iterator();
    }

    @Override
    public abstract BooleanIterator iterator();

    @Override
    public boolean remove(Object ok) {
        if (ok == null) {
            return false;
        }
        return this.rem((Boolean)ok);
    }

    @Override
    public boolean add(Boolean o) {
        return this.add((boolean)o);
    }

    public boolean rem(Object o) {
        if (o == null) {
            return false;
        }
        return this.rem((Boolean)o);
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) {
            return false;
        }
        return this.contains((Boolean)o);
    }

    @Override
    public boolean contains(boolean k) {
        BooleanIterator iterator = this.iterator();
        while (iterator.hasNext()) {
            if (k != iterator.nextBoolean()) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean rem(boolean k) {
        BooleanIterator iterator = this.iterator();
        while (iterator.hasNext()) {
            if (k != iterator.nextBoolean()) continue;
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
        BooleanIterator i = this.iterator();
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
        BooleanIterator i = this.iterator();
        int n = this.size();
        boolean first = true;
        s.append("{");
        while (n-- != 0) {
            if (first) {
                first = false;
            } else {
                s.append(", ");
            }
            boolean k = i.nextBoolean();
            s.append(String.valueOf(k));
        }
        s.append("}");
        return s.toString();
    }
}

