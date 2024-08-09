/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.AbstractListMultimap;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Serialization;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import javax.annotation.Nullable;

@GwtCompatible(serializable=true, emulated=true)
public final class ArrayListMultimap<K, V>
extends AbstractListMultimap<K, V> {
    private static final int DEFAULT_VALUES_PER_KEY = 3;
    @VisibleForTesting
    transient int expectedValuesPerKey;
    @GwtIncompatible
    private static final long serialVersionUID = 0L;

    public static <K, V> ArrayListMultimap<K, V> create() {
        return new ArrayListMultimap<K, V>();
    }

    public static <K, V> ArrayListMultimap<K, V> create(int n, int n2) {
        return new ArrayListMultimap<K, V>(n, n2);
    }

    public static <K, V> ArrayListMultimap<K, V> create(Multimap<? extends K, ? extends V> multimap) {
        return new ArrayListMultimap<K, V>(multimap);
    }

    private ArrayListMultimap() {
        super(new HashMap());
        this.expectedValuesPerKey = 3;
    }

    private ArrayListMultimap(int n, int n2) {
        super(Maps.newHashMapWithExpectedSize(n));
        CollectPreconditions.checkNonnegative(n2, "expectedValuesPerKey");
        this.expectedValuesPerKey = n2;
    }

    private ArrayListMultimap(Multimap<? extends K, ? extends V> multimap) {
        this(multimap.keySet().size(), multimap instanceof ArrayListMultimap ? ((ArrayListMultimap)multimap).expectedValuesPerKey : 3);
        this.putAll((Multimap)multimap);
    }

    @Override
    List<V> createCollection() {
        return new ArrayList(this.expectedValuesPerKey);
    }

    @Deprecated
    public void trimToSize() {
        for (Collection collection : this.backingMap().values()) {
            ArrayList arrayList = (ArrayList)collection;
            arrayList.trimToSize();
        }
    }

    @GwtIncompatible
    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        Serialization.writeMultimap(this, objectOutputStream);
    }

    @GwtIncompatible
    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.expectedValuesPerKey = 3;
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
    public Map asMap() {
        return super.asMap();
    }

    @Override
    @CanIgnoreReturnValue
    public boolean put(@Nullable Object object, @Nullable Object object2) {
        return super.put(object, object2);
    }

    @Override
    @CanIgnoreReturnValue
    public List replaceValues(@Nullable Object object, Iterable iterable) {
        return super.replaceValues(object, iterable);
    }

    @Override
    @CanIgnoreReturnValue
    public List removeAll(@Nullable Object object) {
        return super.removeAll(object);
    }

    @Override
    public List get(@Nullable Object object) {
        return super.get(object);
    }

    @Override
    public void forEach(BiConsumer biConsumer) {
        super.forEach(biConsumer);
    }

    @Override
    public Collection entries() {
        return super.entries();
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

