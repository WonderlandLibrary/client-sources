/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.flare.fastutil;

import com.viaversion.viaversion.libs.fastutil.ints.AbstractIntSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator;
import com.viaversion.viaversion.libs.flare.fastutil.Int2ObjectSyncMap;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

final class Int2ObjectSyncMapSet
extends AbstractIntSet
implements IntSet {
    private static final long serialVersionUID = 1L;
    private final Int2ObjectSyncMap<Boolean> map;
    private final IntSet set;

    Int2ObjectSyncMapSet(@NonNull Int2ObjectSyncMap<Boolean> map) {
        this.map = map;
        this.set = map.keySet();
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
    public boolean contains(int key) {
        return this.map.containsKey(key);
    }

    @Override
    public boolean remove(int key) {
        if (this.map.remove(key) == null) return false;
        return true;
    }

    @Override
    public boolean add(int key) {
        if (this.map.put(key, Boolean.TRUE) != null) return false;
        return true;
    }

    @Override
    public boolean containsAll(@NonNull IntCollection collection) {
        return this.set.containsAll(collection);
    }

    @Override
    public boolean removeAll(@NonNull IntCollection collection) {
        return this.set.removeAll(collection);
    }

    @Override
    public boolean retainAll(@NonNull IntCollection collection) {
        return this.set.retainAll(collection);
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
    public int[] toArray(int[] original) {
        return this.set.toArray(original);
    }

    @Override
    public int[] toIntArray() {
        return this.set.toIntArray();
    }

    @Override
    public boolean equals(@Nullable Object other) {
        if (other == this) return true;
        if (this.set.equals(other)) return true;
        return false;
    }

    @Override
    public @NonNull String toString() {
        return this.set.toString();
    }

    @Override
    public int hashCode() {
        return this.set.hashCode();
    }
}

