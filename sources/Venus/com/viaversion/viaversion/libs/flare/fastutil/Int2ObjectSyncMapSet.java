/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.flare.fastutil;

import com.viaversion.viaversion.libs.fastutil.ints.AbstractIntSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator;
import com.viaversion.viaversion.libs.flare.fastutil.Int2ObjectSyncMap;
import java.util.Iterator;
import java.util.Spliterator;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
final class Int2ObjectSyncMapSet
extends AbstractIntSet
implements IntSet {
    private static final long serialVersionUID = 1L;
    private final Int2ObjectSyncMap<Boolean> map;
    private final IntSet set;

    Int2ObjectSyncMapSet(@NonNull Int2ObjectSyncMap<Boolean> int2ObjectSyncMap) {
        this.map = int2ObjectSyncMap;
        this.set = int2ObjectSyncMap.keySet();
    }

    @Override
    public void clear() {
        this.map.clear();
    }

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public boolean contains(int n) {
        return this.map.containsKey(n);
    }

    @Override
    public boolean remove(int n) {
        return this.map.remove(n) != null;
    }

    @Override
    public boolean add(int n) {
        return this.map.put(n, Boolean.TRUE) == null;
    }

    @Override
    public boolean containsAll(@NonNull IntCollection intCollection) {
        return this.set.containsAll(intCollection);
    }

    @Override
    public boolean removeAll(@NonNull IntCollection intCollection) {
        return this.set.removeAll(intCollection);
    }

    @Override
    public boolean retainAll(@NonNull IntCollection intCollection) {
        return this.set.retainAll(intCollection);
    }

    @Override
    public @NonNull IntIterator iterator() {
        return this.set.iterator();
    }

    @Override
    public @NonNull IntSpliterator spliterator() {
        return this.set.spliterator();
    }

    @Override
    public int[] toArray(int[] nArray) {
        return this.set.toArray(nArray);
    }

    @Override
    public int[] toIntArray() {
        return this.set.toIntArray();
    }

    @Override
    public boolean equals(@Nullable Object object) {
        return object == this || this.set.equals(object);
    }

    @Override
    public @NonNull String toString() {
        return this.set.toString();
    }

    @Override
    public int hashCode() {
        return this.set.hashCode();
    }

    @Override
    public @NonNull Spliterator spliterator() {
        return this.spliterator();
    }

    @Override
    public @NonNull Iterator iterator() {
        return this.iterator();
    }
}

