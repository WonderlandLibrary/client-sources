/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractSetMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Serialization;
import com.google.common.collect.Sets;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import javax.annotation.Nullable;

@GwtCompatible(serializable=true, emulated=true)
public final class HashMultimap<K, V>
extends AbstractSetMultimap<K, V> {
    private static final int DEFAULT_VALUES_PER_KEY = 2;
    @VisibleForTesting
    transient int expectedValuesPerKey = 2;
    @GwtIncompatible
    private static final long serialVersionUID = 0L;

    public static <K, V> HashMultimap<K, V> create() {
        return new HashMultimap<K, V>();
    }

    public static <K, V> HashMultimap<K, V> create(int n, int n2) {
        return new HashMultimap<K, V>(n, n2);
    }

    public static <K, V> HashMultimap<K, V> create(Multimap<? extends K, ? extends V> multimap) {
        return new HashMultimap<K, V>(multimap);
    }

    private HashMultimap() {
        super(new HashMap());
    }

    private HashMultimap(int n, int n2) {
        super(Maps.newHashMapWithExpectedSize(n));
        Preconditions.checkArgument(n2 >= 0);
        this.expectedValuesPerKey = n2;
    }

    private HashMultimap(Multimap<? extends K, ? extends V> multimap) {
        super(Maps.newHashMapWithExpectedSize(multimap.keySet().size()));
        this.putAll(multimap);
    }

    @Override
    Set<V> createCollection() {
        return Sets.newHashSetWithExpectedSize(this.expectedValuesPerKey);
    }

    @GwtIncompatible
    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        Serialization.writeMultimap(this, objectOutputStream);
    }

    @GwtIncompatible
    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.expectedValuesPerKey = 2;
        int n = Serialization.readCount(objectInputStream);
        HashMap hashMap = Maps.newHashMap();
        this.setMap(hashMap);
        Serialization.populateMultimap(this, objectInputStream, n);
    }

    @Override
    public boolean equals(@Nullable Object object) {
        return super.equals(object);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean put(@Nullable Object object, @Nullable Object object2) {
        return super.put(object, object2);
    }

    @Override
    public Map asMap() {
        return super.asMap();
    }

    @Override
    @CanIgnoreReturnValue
    public Set replaceValues(@Nullable Object object, Iterable iterable) {
        return super.replaceValues(object, iterable);
    }

    @Override
    @CanIgnoreReturnValue
    public Set removeAll(@Nullable Object object) {
        return super.removeAll(object);
    }

    @Override
    public Set entries() {
        return super.entries();
    }

    @Override
    public Set get(@Nullable Object object) {
        return super.get(object);
    }

    @Override
    public void forEach(BiConsumer biConsumer) {
        super.forEach(biConsumer);
    }

    @Override
    public Collection values() {
        return super.values();
    }

    @Override
    public void clear() {
        super.clear();
    }

    @Override
    public boolean containsKey(@Nullable Object object) {
        return super.containsKey(object);
    }

    @Override
    public int size() {
        return super.size();
    }

    @Override
    Collection createCollection() {
        return this.createCollection();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public Multiset keys() {
        return super.keys();
    }

    @Override
    public Set keySet() {
        return super.keySet();
    }

    @Override
    @CanIgnoreReturnValue
    public boolean putAll(Multimap multimap) {
        return super.putAll(multimap);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean putAll(@Nullable Object object, Iterable iterable) {
        return super.putAll(object, iterable);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean remove(@Nullable Object object, @Nullable Object object2) {
        return super.remove(object, object2);
    }

    @Override
    public boolean containsEntry(@Nullable Object object, @Nullable Object object2) {
        return super.containsEntry(object, object2);
    }

    @Override
    public boolean containsValue(@Nullable Object object) {
        return super.containsValue(object);
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }
}

