/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.ints.IntArrays;
import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntConsumer;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterators;
import com.viaversion.viaversion.libs.fastutil.ints.IntPredicate;
import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractIntCollection
extends AbstractCollection<Integer>
implements IntCollection {
    protected AbstractIntCollection() {
    }

    @Override
    public abstract IntIterator iterator();

    @Override
    public boolean add(int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(int n) {
        IntIterator intIterator = this.iterator();
        while (intIterator.hasNext()) {
            if (n != intIterator.nextInt()) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean rem(int n) {
        IntIterator intIterator = this.iterator();
        while (intIterator.hasNext()) {
            if (n != intIterator.nextInt()) continue;
            intIterator.remove();
            return false;
        }
        return true;
    }

    @Override
    @Deprecated
    public boolean add(Integer n) {
        return IntCollection.super.add(n);
    }

    @Override
    @Deprecated
    public boolean contains(Object object) {
        return IntCollection.super.contains(object);
    }

    @Override
    @Deprecated
    public boolean remove(Object object) {
        return IntCollection.super.remove(object);
    }

    @Override
    public int[] toArray(int[] nArray) {
        int n = this.size();
        if (nArray == null) {
            nArray = new int[n];
        } else if (nArray.length < n) {
            nArray = Arrays.copyOf(nArray, n);
        }
        IntIterators.unwrap(this.iterator(), nArray);
        return nArray;
    }

    @Override
    public int[] toIntArray() {
        int n = this.size();
        if (n == 0) {
            return IntArrays.EMPTY_ARRAY;
        }
        int[] nArray = new int[n];
        IntIterators.unwrap(this.iterator(), nArray);
        return nArray;
    }

    @Override
    @Deprecated
    public int[] toIntArray(int[] nArray) {
        return this.toArray(nArray);
    }

    @Override
    public final void forEach(IntConsumer intConsumer) {
        IntCollection.super.forEach(intConsumer);
    }

    @Override
    public final boolean removeIf(IntPredicate intPredicate) {
        return IntCollection.super.removeIf(intPredicate);
    }

    @Override
    public boolean addAll(IntCollection intCollection) {
        boolean bl = false;
        IntIterator intIterator = intCollection.iterator();
        while (intIterator.hasNext()) {
            if (!this.add(intIterator.nextInt())) continue;
            bl = true;
        }
        return bl;
    }

    @Override
    public boolean addAll(Collection<? extends Integer> collection) {
        if (collection instanceof IntCollection) {
            return this.addAll((IntCollection)collection);
        }
        return super.addAll(collection);
    }

    @Override
    public boolean containsAll(IntCollection intCollection) {
        IntIterator intIterator = intCollection.iterator();
        while (intIterator.hasNext()) {
            if (this.contains(intIterator.nextInt())) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        if (collection instanceof IntCollection) {
            return this.containsAll((IntCollection)collection);
        }
        return super.containsAll(collection);
    }

    @Override
    public boolean removeAll(IntCollection intCollection) {
        boolean bl = false;
        IntIterator intIterator = intCollection.iterator();
        while (intIterator.hasNext()) {
            if (!this.rem(intIterator.nextInt())) continue;
            bl = true;
        }
        return bl;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        if (collection instanceof IntCollection) {
            return this.removeAll((IntCollection)collection);
        }
        return super.removeAll(collection);
    }

    @Override
    public boolean retainAll(IntCollection intCollection) {
        boolean bl = false;
        IntIterator intIterator = this.iterator();
        while (intIterator.hasNext()) {
            if (intCollection.contains(intIterator.nextInt())) continue;
            intIterator.remove();
            bl = true;
        }
        return bl;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        if (collection instanceof IntCollection) {
            return this.retainAll((IntCollection)collection);
        }
        return super.retainAll(collection);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        IntIterator intIterator = this.iterator();
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            int n2 = intIterator.nextInt();
            stringBuilder.append(String.valueOf(n2));
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    @Deprecated
    public boolean add(Object object) {
        return this.add((Integer)object);
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }
}

