/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractSortedKeySortedSetMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Ordering;
import com.google.common.collect.Serialization;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.BiConsumer;
import javax.annotation.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@GwtCompatible(serializable=true, emulated=true)
public class TreeMultimap<K, V>
extends AbstractSortedKeySortedSetMultimap<K, V> {
    private transient Comparator<? super K> keyComparator;
    private transient Comparator<? super V> valueComparator;
    @GwtIncompatible
    private static final long serialVersionUID = 0L;

    public static <K extends Comparable, V extends Comparable> TreeMultimap<K, V> create() {
        return new TreeMultimap(Ordering.natural(), Ordering.natural());
    }

    public static <K, V> TreeMultimap<K, V> create(Comparator<? super K> comparator, Comparator<? super V> comparator2) {
        return new TreeMultimap<K, V>(Preconditions.checkNotNull(comparator), Preconditions.checkNotNull(comparator2));
    }

    public static <K extends Comparable, V extends Comparable> TreeMultimap<K, V> create(Multimap<? extends K, ? extends V> multimap) {
        return new TreeMultimap<K, V>(Ordering.natural(), Ordering.natural(), multimap);
    }

    TreeMultimap(Comparator<? super K> comparator, Comparator<? super V> comparator2) {
        super(new TreeMap(comparator));
        this.keyComparator = comparator;
        this.valueComparator = comparator2;
    }

    private TreeMultimap(Comparator<? super K> comparator, Comparator<? super V> comparator2, Multimap<? extends K, ? extends V> multimap) {
        this(comparator, comparator2);
        this.putAll((Multimap)multimap);
    }

    @Override
    SortedSet<V> createCollection() {
        return new TreeSet<V>(this.valueComparator);
    }

    @Override
    Collection<V> createCollection(@Nullable K k) {
        if (k == null) {
            this.keyComparator().compare(k, k);
        }
        return super.createCollection(k);
    }

    @Deprecated
    public Comparator<? super K> keyComparator() {
        return this.keyComparator;
    }

    @Override
    public Comparator<? super V> valueComparator() {
        return this.valueComparator;
    }

    @Override
    @GwtIncompatible
    public NavigableSet<V> get(@Nullable K k) {
        return (NavigableSet)super.get((Object)k);
    }

    @Override
    public NavigableSet<K> keySet() {
        return (NavigableSet)super.keySet();
    }

    @Override
    public NavigableMap<K, Collection<V>> asMap() {
        return (NavigableMap)super.asMap();
    }

    @GwtIncompatible
    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.keyComparator());
        objectOutputStream.writeObject(this.valueComparator());
        Serialization.writeMultimap(this, objectOutputStream);
    }

    @GwtIncompatible
    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.keyComparator = Preconditions.checkNotNull((Comparator)objectInputStream.readObject());
        this.valueComparator = Preconditions.checkNotNull((Comparator)objectInputStream.readObject());
        this.setMap(new TreeMap(this.keyComparator));
        Serialization.populateMultimap(this, objectInputStream);
    }

    @Override
    public SortedSet keySet() {
        return this.keySet();
    }

    @Override
    public SortedMap asMap() {
        return this.asMap();
    }

    @Override
    public Collection values() {
        return super.values();
    }

    @Override
    public Map asMap() {
        return this.asMap();
    }

    @Override
    @CanIgnoreReturnValue
    public SortedSet replaceValues(@Nullable Object object, Iterable iterable) {
        return super.replaceValues(object, iterable);
    }

    @Override
    @CanIgnoreReturnValue
    public SortedSet removeAll(@Nullable Object object) {
        return super.removeAll(object);
    }

    @Override
    @GwtIncompatible
    public SortedSet get(@Nullable Object object) {
        return this.get(object);
    }

    @Override
    @GwtIncompatible
    public Set get(@Nullable Object object) {
        return this.get(object);
    }

    @Override
    public Set keySet() {
        return this.keySet();
    }

    @Override
    @GwtIncompatible
    public Collection get(@Nullable Object object) {
        return this.get(object);
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
    public Set entries() {
        return super.entries();
    }

    @Override
    Set createCollection() {
        return this.createCollection();
    }

    @Override
    public void forEach(BiConsumer biConsumer) {
        super.forEach(biConsumer);
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

