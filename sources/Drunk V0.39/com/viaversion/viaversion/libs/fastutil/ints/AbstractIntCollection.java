/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntConsumer;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterators;
import com.viaversion.viaversion.libs.fastutil.ints.IntPredicate;
import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Collection;

public abstract class AbstractIntCollection
extends AbstractCollection<Integer>
implements IntCollection {
    protected AbstractIntCollection() {
    }

    @Override
    public abstract IntIterator iterator();

    @Override
    public boolean add(int k) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(int k) {
        IntIterator iterator = this.iterator();
        do {
            if (!iterator.hasNext()) return false;
        } while (k != iterator.nextInt());
        return true;
    }

    @Override
    public boolean rem(int k) {
        IntIterator iterator = this.iterator();
        do {
            if (!iterator.hasNext()) return false;
        } while (k != iterator.nextInt());
        iterator.remove();
        return true;
    }

    @Override
    @Deprecated
    public boolean add(Integer key) {
        return IntCollection.super.add(key);
    }

    @Override
    @Deprecated
    public boolean contains(Object key) {
        return IntCollection.super.contains(key);
    }

    @Override
    @Deprecated
    public boolean remove(Object key) {
        return IntCollection.super.remove(key);
    }

    @Override
    public int[] toArray(int[] a) {
        int size = this.size();
        if (a == null) {
            a = new int[size];
        } else if (a.length < size) {
            a = Arrays.copyOf(a, size);
        }
        IntIterators.unwrap(this.iterator(), a);
        return a;
    }

    @Override
    public int[] toIntArray() {
        return this.toArray((int[])null);
    }

    @Override
    @Deprecated
    public int[] toIntArray(int[] a) {
        return this.toArray(a);
    }

    @Override
    public final void forEach(IntConsumer action) {
        IntCollection.super.forEach(action);
    }

    @Override
    public final boolean removeIf(IntPredicate filter) {
        return IntCollection.super.removeIf(filter);
    }

    @Override
    public boolean addAll(IntCollection c) {
        boolean retVal = false;
        IntIterator i = c.iterator();
        while (i.hasNext()) {
            if (!this.add(i.nextInt())) continue;
            retVal = true;
        }
        return retVal;
    }

    @Override
    public boolean addAll(Collection<? extends Integer> c) {
        if (!(c instanceof IntCollection)) return super.addAll(c);
        return this.addAll((IntCollection)c);
    }

    @Override
    public boolean containsAll(IntCollection c) {
        IntIterator i = c.iterator();
        do {
            if (!i.hasNext()) return true;
        } while (this.contains(i.nextInt()));
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (!(c instanceof IntCollection)) return super.containsAll(c);
        return this.containsAll((IntCollection)c);
    }

    @Override
    public boolean removeAll(IntCollection c) {
        boolean retVal = false;
        IntIterator i = c.iterator();
        while (i.hasNext()) {
            if (!this.rem(i.nextInt())) continue;
            retVal = true;
        }
        return retVal;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (!(c instanceof IntCollection)) return super.removeAll(c);
        return this.removeAll((IntCollection)c);
    }

    @Override
    public boolean retainAll(IntCollection c) {
        boolean retVal = false;
        IntIterator i = this.iterator();
        while (i.hasNext()) {
            if (c.contains(i.nextInt())) continue;
            i.remove();
            retVal = true;
        }
        return retVal;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (!(c instanceof IntCollection)) return super.retainAll(c);
        return this.retainAll((IntCollection)c);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        IntIterator i = this.iterator();
        int n = this.size();
        boolean first = true;
        s.append("{");
        while (true) {
            if (n-- == 0) {
                s.append("}");
                return s.toString();
            }
            if (first) {
                first = false;
            } else {
                s.append(", ");
            }
            int k = i.nextInt();
            s.append(String.valueOf(k));
        }
    }
}

