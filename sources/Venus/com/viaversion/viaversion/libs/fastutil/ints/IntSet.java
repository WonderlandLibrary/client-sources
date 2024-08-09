/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Size64;
import com.viaversion.viaversion.libs.fastutil.ints.AbstractIntSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntArraySet;
import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSets;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface IntSet
extends IntCollection,
Set<Integer> {
    @Override
    public IntIterator iterator();

    @Override
    default public IntSpliterator spliterator() {
        return IntSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(this), 321);
    }

    public boolean remove(int var1);

    @Override
    @Deprecated
    default public boolean remove(Object object) {
        return IntCollection.super.remove(object);
    }

    @Override
    @Deprecated
    default public boolean add(Integer n) {
        return IntCollection.super.add(n);
    }

    @Override
    @Deprecated
    default public boolean contains(Object object) {
        return IntCollection.super.contains(object);
    }

    @Override
    @Deprecated
    default public boolean rem(int n) {
        return this.remove(n);
    }

    public static IntSet of() {
        return IntSets.UNMODIFIABLE_EMPTY_SET;
    }

    public static IntSet of(int n) {
        return IntSets.singleton(n);
    }

    public static IntSet of(int n, int n2) {
        IntArraySet intArraySet = new IntArraySet(2);
        intArraySet.add(n);
        if (!intArraySet.add(n2)) {
            throw new IllegalArgumentException("Duplicate element: " + n2);
        }
        return IntSets.unmodifiable(intArraySet);
    }

    public static IntSet of(int n, int n2, int n3) {
        IntArraySet intArraySet = new IntArraySet(3);
        intArraySet.add(n);
        if (!intArraySet.add(n2)) {
            throw new IllegalArgumentException("Duplicate element: " + n2);
        }
        if (!intArraySet.add(n3)) {
            throw new IllegalArgumentException("Duplicate element: " + n3);
        }
        return IntSets.unmodifiable(intArraySet);
    }

    public static IntSet of(int ... nArray) {
        switch (nArray.length) {
            case 0: {
                return IntSet.of();
            }
            case 1: {
                return IntSet.of(nArray[0]);
            }
            case 2: {
                return IntSet.of(nArray[0], nArray[1]);
            }
            case 3: {
                return IntSet.of(nArray[0], nArray[1], nArray[2]);
            }
        }
        AbstractIntSet abstractIntSet = nArray.length <= 4 ? new IntArraySet(nArray.length) : new IntOpenHashSet(nArray.length);
        for (int n : nArray) {
            if (abstractIntSet.add(n)) continue;
            throw new IllegalArgumentException("Duplicate element: " + n);
        }
        return IntSets.unmodifiable(abstractIntSet);
    }

    @Override
    default public Spliterator spliterator() {
        return this.spliterator();
    }

    @Override
    @Deprecated
    default public boolean add(Object object) {
        return this.add((Integer)object);
    }

    @Override
    default public Iterator iterator() {
        return this.iterator();
    }
}

