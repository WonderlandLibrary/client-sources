/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Size64;
import com.viaversion.viaversion.libs.fastutil.ints.IntBidirectionalIterable;
import com.viaversion.viaversion.libs.fastutil.ints.IntBidirectionalIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntComparator;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.Spliterator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface IntSortedSet
extends IntSet,
SortedSet<Integer>,
IntBidirectionalIterable {
    public IntBidirectionalIterator iterator(int var1);

    @Override
    public IntBidirectionalIterator iterator();

    @Override
    default public IntSpliterator spliterator() {
        return IntSpliterators.asSpliteratorFromSorted(this.iterator(), Size64.sizeOf(this), 341, this.comparator());
    }

    public IntSortedSet subSet(int var1, int var2);

    public IntSortedSet headSet(int var1);

    public IntSortedSet tailSet(int var1);

    public IntComparator comparator();

    public int firstInt();

    public int lastInt();

    @Deprecated
    default public IntSortedSet subSet(Integer n, Integer n2) {
        return this.subSet((int)n, (int)n2);
    }

    @Deprecated
    default public IntSortedSet headSet(Integer n) {
        return this.headSet((int)n);
    }

    @Deprecated
    default public IntSortedSet tailSet(Integer n) {
        return this.tailSet((int)n);
    }

    @Override
    @Deprecated
    default public Integer first() {
        return this.firstInt();
    }

    @Override
    @Deprecated
    default public Integer last() {
        return this.lastInt();
    }

    @Override
    default public IntIterator iterator() {
        return this.iterator();
    }

    @Override
    default public Spliterator spliterator() {
        return this.spliterator();
    }

    @Override
    default public Iterator iterator() {
        return this.iterator();
    }

    @Override
    @Deprecated
    default public Object last() {
        return this.last();
    }

    @Override
    @Deprecated
    default public Object first() {
        return this.first();
    }

    @Override
    @Deprecated
    default public SortedSet tailSet(Object object) {
        return this.tailSet((Integer)object);
    }

    @Override
    @Deprecated
    default public SortedSet headSet(Object object) {
        return this.headSet((Integer)object);
    }

    @Override
    @Deprecated
    default public SortedSet subSet(Object object, Object object2) {
        return this.subSet((Integer)object, (Integer)object2);
    }

    @Override
    default public Comparator comparator() {
        return this.comparator();
    }
}

