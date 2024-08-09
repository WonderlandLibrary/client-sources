/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractMultimap;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.CollectSpliterators;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.Ordering;
import com.google.common.collect.Serialization;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.UnmodifiableIterator;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.j2objc.annotations.Weak;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import javax.annotation.Nullable;

@GwtCompatible(emulated=true)
public abstract class ImmutableMultimap<K, V>
extends AbstractMultimap<K, V>
implements Serializable {
    final transient ImmutableMap<K, ? extends ImmutableCollection<V>> map;
    final transient int size;
    private static final long serialVersionUID = 0L;

    public static <K, V> ImmutableMultimap<K, V> of() {
        return ImmutableListMultimap.of();
    }

    public static <K, V> ImmutableMultimap<K, V> of(K k, V v) {
        return ImmutableListMultimap.of(k, v);
    }

    public static <K, V> ImmutableMultimap<K, V> of(K k, V v, K k2, V v2) {
        return ImmutableListMultimap.of(k, v, k2, v2);
    }

    public static <K, V> ImmutableMultimap<K, V> of(K k, V v, K k2, V v2, K k3, V v3) {
        return ImmutableListMultimap.of(k, v, k2, v2, k3, v3);
    }

    public static <K, V> ImmutableMultimap<K, V> of(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4) {
        return ImmutableListMultimap.of(k, v, k2, v2, k3, v3, k4, v4);
    }

    public static <K, V> ImmutableMultimap<K, V> of(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        return ImmutableListMultimap.of(k, v, k2, v2, k3, v3, k4, v4, k5, v5);
    }

    public static <K, V> Builder<K, V> builder() {
        return new Builder();
    }

    public static <K, V> ImmutableMultimap<K, V> copyOf(Multimap<? extends K, ? extends V> multimap) {
        ImmutableMultimap immutableMultimap;
        if (multimap instanceof ImmutableMultimap && !(immutableMultimap = (ImmutableMultimap)multimap).isPartialView()) {
            return immutableMultimap;
        }
        return ImmutableListMultimap.copyOf(multimap);
    }

    @Beta
    public static <K, V> ImmutableMultimap<K, V> copyOf(Iterable<? extends Map.Entry<? extends K, ? extends V>> iterable) {
        return ImmutableListMultimap.copyOf(iterable);
    }

    ImmutableMultimap(ImmutableMap<K, ? extends ImmutableCollection<V>> immutableMap, int n) {
        this.map = immutableMap;
        this.size = n;
    }

    @Override
    @Deprecated
    @CanIgnoreReturnValue
    public ImmutableCollection<V> removeAll(Object object) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    @CanIgnoreReturnValue
    public ImmutableCollection<V> replaceValues(K k, Iterable<? extends V> iterable) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public abstract ImmutableCollection<V> get(K var1);

    public abstract ImmutableMultimap<V, K> inverse();

    @Override
    @Deprecated
    @CanIgnoreReturnValue
    public boolean put(K k, V v) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    @CanIgnoreReturnValue
    public boolean putAll(K k, Iterable<? extends V> iterable) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    @CanIgnoreReturnValue
    public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    @CanIgnoreReturnValue
    public boolean remove(Object object, Object object2) {
        throw new UnsupportedOperationException();
    }

    boolean isPartialView() {
        return this.map.isPartialView();
    }

    @Override
    public boolean containsKey(@Nullable Object object) {
        return this.map.containsKey(object);
    }

    @Override
    public boolean containsValue(@Nullable Object object) {
        return object != null && super.containsValue(object);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public ImmutableSet<K> keySet() {
        return this.map.keySet();
    }

    @Override
    public ImmutableMap<K, Collection<V>> asMap() {
        return this.map;
    }

    @Override
    Map<K, Collection<V>> createAsMap() {
        throw new AssertionError((Object)"should never be called");
    }

    @Override
    public ImmutableCollection<Map.Entry<K, V>> entries() {
        return (ImmutableCollection)super.entries();
    }

    @Override
    ImmutableCollection<Map.Entry<K, V>> createEntries() {
        return new EntryCollection(this);
    }

    @Override
    UnmodifiableIterator<Map.Entry<K, V>> entryIterator() {
        return new Itr<Map.Entry<K, V>>(this){
            final ImmutableMultimap this$0;
            {
                this.this$0 = immutableMultimap;
                super(immutableMultimap, null);
            }

            @Override
            Map.Entry<K, V> output(K k, V v) {
                return Maps.immutableEntry(k, v);
            }

            @Override
            Object output(Object object, Object object2) {
                return this.output(object, object2);
            }
        };
    }

    @Override
    Spliterator<Map.Entry<K, V>> entrySpliterator() {
        return CollectSpliterators.flatMap(((ImmutableCollection)((Object)((ImmutableMap)this.asMap()).entrySet())).spliterator(), ImmutableMultimap::lambda$entrySpliterator$1, 0x40 | (this instanceof SetMultimap ? 1 : 0), this.size());
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> biConsumer) {
        Preconditions.checkNotNull(biConsumer);
        this.asMap().forEach((arg_0, arg_1) -> ImmutableMultimap.lambda$forEach$3(biConsumer, arg_0, arg_1));
    }

    @Override
    public ImmutableMultiset<K> keys() {
        return (ImmutableMultiset)super.keys();
    }

    @Override
    ImmutableMultiset<K> createKeys() {
        return new Keys(this);
    }

    @Override
    public ImmutableCollection<V> values() {
        return (ImmutableCollection)super.values();
    }

    @Override
    ImmutableCollection<V> createValues() {
        return new Values(this);
    }

    @Override
    UnmodifiableIterator<V> valueIterator() {
        return new Itr<V>(this){
            final ImmutableMultimap this$0;
            {
                this.this$0 = immutableMultimap;
                super(immutableMultimap, null);
            }

            @Override
            V output(K k, V v) {
                return v;
            }
        };
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
    public boolean equals(@Nullable Object object) {
        return super.equals(object);
    }

    @Override
    public Map asMap() {
        return this.asMap();
    }

    @Override
    Iterator valueIterator() {
        return this.valueIterator();
    }

    @Override
    Collection createValues() {
        return this.createValues();
    }

    @Override
    public Collection values() {
        return this.values();
    }

    @Override
    Multiset createKeys() {
        return this.createKeys();
    }

    @Override
    public Multiset keys() {
        return this.keys();
    }

    @Override
    public Set keySet() {
        return this.keySet();
    }

    @Override
    Iterator entryIterator() {
        return this.entryIterator();
    }

    @Override
    Collection createEntries() {
        return this.createEntries();
    }

    @Override
    public Collection entries() {
        return this.entries();
    }

    @Override
    @Deprecated
    @CanIgnoreReturnValue
    public Collection replaceValues(Object object, Iterable iterable) {
        return this.replaceValues(object, iterable);
    }

    @Override
    public boolean containsEntry(@Nullable Object object, @Nullable Object object2) {
        return super.containsEntry(object, object2);
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

    @Override
    public Collection get(Object object) {
        return this.get(object);
    }

    @Override
    @Deprecated
    @CanIgnoreReturnValue
    public Collection removeAll(Object object) {
        return this.removeAll(object);
    }

    private static void lambda$forEach$3(BiConsumer biConsumer, Object object, Collection collection) {
        collection.forEach(arg_0 -> ImmutableMultimap.lambda$null$2(biConsumer, object, arg_0));
    }

    private static void lambda$null$2(BiConsumer biConsumer, Object object, Object object2) {
        biConsumer.accept(object, object2);
    }

    private static Spliterator lambda$entrySpliterator$1(Map.Entry entry) {
        Object k = entry.getKey();
        Collection collection = (Collection)entry.getValue();
        return CollectSpliterators.map(collection.spliterator(), arg_0 -> ImmutableMultimap.lambda$null$0(k, arg_0));
    }

    private static Map.Entry lambda$null$0(Object object, Object object2) {
        return Maps.immutableEntry(object, object2);
    }

    private static final class Values<K, V>
    extends ImmutableCollection<V> {
        @Weak
        private final transient ImmutableMultimap<K, V> multimap;
        private static final long serialVersionUID = 0L;

        Values(ImmutableMultimap<K, V> immutableMultimap) {
            this.multimap = immutableMultimap;
        }

        @Override
        public boolean contains(@Nullable Object object) {
            return this.multimap.containsValue(object);
        }

        @Override
        public UnmodifiableIterator<V> iterator() {
            return this.multimap.valueIterator();
        }

        @Override
        @GwtIncompatible
        int copyIntoArray(Object[] objectArray, int n) {
            for (ImmutableCollection immutableCollection : this.multimap.map.values()) {
                n = immutableCollection.copyIntoArray(objectArray, n);
            }
            return n;
        }

        @Override
        public int size() {
            return this.multimap.size();
        }

        @Override
        boolean isPartialView() {
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }
    }

    class Keys
    extends ImmutableMultiset<K> {
        final ImmutableMultimap this$0;

        Keys(ImmutableMultimap immutableMultimap) {
            this.this$0 = immutableMultimap;
        }

        @Override
        public boolean contains(@Nullable Object object) {
            return this.this$0.containsKey(object);
        }

        @Override
        public int count(@Nullable Object object) {
            Collection collection = this.this$0.map.get(object);
            return collection == null ? 0 : collection.size();
        }

        @Override
        public ImmutableSet<K> elementSet() {
            return this.this$0.keySet();
        }

        @Override
        public int size() {
            return this.this$0.size();
        }

        @Override
        Multiset.Entry<K> getEntry(int n) {
            Map.Entry entry = (Map.Entry)((ImmutableSet)this.this$0.map.entrySet()).asList().get(n);
            return Multisets.immutableEntry(entry.getKey(), ((Collection)entry.getValue()).size());
        }

        @Override
        boolean isPartialView() {
            return false;
        }

        @Override
        public Set elementSet() {
            return this.elementSet();
        }
    }

    private abstract class Itr<T>
    extends UnmodifiableIterator<T> {
        final Iterator<Map.Entry<K, Collection<V>>> mapIterator;
        K key;
        Iterator<V> valueIterator;
        final ImmutableMultimap this$0;

        private Itr(ImmutableMultimap immutableMultimap) {
            this.this$0 = immutableMultimap;
            this.mapIterator = ((ImmutableSet)((ImmutableMap)this.this$0.asMap()).entrySet()).iterator();
            this.key = null;
            this.valueIterator = Iterators.emptyIterator();
        }

        abstract T output(K var1, V var2);

        @Override
        public boolean hasNext() {
            return this.mapIterator.hasNext() || this.valueIterator.hasNext();
        }

        @Override
        public T next() {
            if (!this.valueIterator.hasNext()) {
                Map.Entry entry = this.mapIterator.next();
                this.key = entry.getKey();
                this.valueIterator = entry.getValue().iterator();
            }
            return this.output(this.key, this.valueIterator.next());
        }

        Itr(ImmutableMultimap immutableMultimap, 1 var2_2) {
            this(immutableMultimap);
        }
    }

    private static class EntryCollection<K, V>
    extends ImmutableCollection<Map.Entry<K, V>> {
        @Weak
        final ImmutableMultimap<K, V> multimap;
        private static final long serialVersionUID = 0L;

        EntryCollection(ImmutableMultimap<K, V> immutableMultimap) {
            this.multimap = immutableMultimap;
        }

        @Override
        public UnmodifiableIterator<Map.Entry<K, V>> iterator() {
            return this.multimap.entryIterator();
        }

        @Override
        boolean isPartialView() {
            return this.multimap.isPartialView();
        }

        @Override
        public int size() {
            return this.multimap.size();
        }

        @Override
        public boolean contains(Object object) {
            if (object instanceof Map.Entry) {
                Map.Entry entry = (Map.Entry)object;
                return this.multimap.containsEntry(entry.getKey(), entry.getValue());
            }
            return true;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }
    }

    @GwtIncompatible
    static class FieldSettersHolder {
        static final Serialization.FieldSetter<ImmutableMultimap> MAP_FIELD_SETTER = Serialization.getFieldSetter(ImmutableMultimap.class, "map");
        static final Serialization.FieldSetter<ImmutableMultimap> SIZE_FIELD_SETTER = Serialization.getFieldSetter(ImmutableMultimap.class, "size");
        static final Serialization.FieldSetter<ImmutableSetMultimap> EMPTY_SET_FIELD_SETTER = Serialization.getFieldSetter(ImmutableSetMultimap.class, "emptySet");

        FieldSettersHolder() {
        }
    }

    public static class Builder<K, V> {
        Multimap<K, V> builderMultimap;
        Comparator<? super K> keyComparator;
        Comparator<? super V> valueComparator;

        public Builder() {
            this(MultimapBuilder.linkedHashKeys().arrayListValues().build());
        }

        Builder(Multimap<K, V> multimap) {
            this.builderMultimap = multimap;
        }

        @CanIgnoreReturnValue
        public Builder<K, V> put(K k, V v) {
            CollectPreconditions.checkEntryNotNull(k, v);
            this.builderMultimap.put(k, v);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<K, V> put(Map.Entry<? extends K, ? extends V> entry) {
            return this.put(entry.getKey(), entry.getValue());
        }

        @CanIgnoreReturnValue
        @Beta
        public Builder<K, V> putAll(Iterable<? extends Map.Entry<? extends K, ? extends V>> iterable) {
            for (Map.Entry<K, V> entry : iterable) {
                this.put(entry);
            }
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<K, V> putAll(K k, Iterable<? extends V> iterable) {
            if (k == null) {
                throw new NullPointerException("null key in entry: null=" + Iterables.toString(iterable));
            }
            Collection<V> collection = this.builderMultimap.get(k);
            for (V v : iterable) {
                CollectPreconditions.checkEntryNotNull(k, v);
                collection.add(v);
            }
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<K, V> putAll(K k, V ... VArray) {
            return this.putAll(k, (Iterable<? extends V>)Arrays.asList(VArray));
        }

        @CanIgnoreReturnValue
        public Builder<K, V> putAll(Multimap<? extends K, ? extends V> multimap) {
            for (Map.Entry<K, Collection<V>> entry : multimap.asMap().entrySet()) {
                this.putAll(entry.getKey(), (Iterable)entry.getValue());
            }
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<K, V> orderKeysBy(Comparator<? super K> comparator) {
            this.keyComparator = Preconditions.checkNotNull(comparator);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<K, V> orderValuesBy(Comparator<? super V> comparator) {
            this.valueComparator = Preconditions.checkNotNull(comparator);
            return this;
        }

        @CanIgnoreReturnValue
        Builder<K, V> combine(Builder<K, V> builder) {
            this.putAll(builder.builderMultimap);
            return this;
        }

        public ImmutableMultimap<K, V> build() {
            if (this.valueComparator != null) {
                for (Collection immutableList : this.builderMultimap.asMap().values()) {
                    List list = (List)immutableList;
                    Collections.sort(list, this.valueComparator);
                }
            }
            if (this.keyComparator != null) {
                Multimap multimap = MultimapBuilder.linkedHashKeys().arrayListValues().build();
                ImmutableList<Map.Entry<K, Collection<V>>> immutableList = Ordering.from(this.keyComparator).onKeys().immutableSortedCopy(this.builderMultimap.asMap().entrySet());
                for (Map.Entry entry : immutableList) {
                    multimap.putAll(entry.getKey(), (Iterable)entry.getValue());
                }
                this.builderMultimap = multimap;
            }
            return ImmutableMultimap.copyOf(this.builderMultimap);
        }
    }
}

