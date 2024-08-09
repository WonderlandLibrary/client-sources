/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.EmptyImmutableSetMultimap;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Ordering;
import com.google.common.collect.Serialization;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.UnmodifiableIterator;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.concurrent.LazyInit;
import com.google.j2objc.annotations.RetainedWith;
import com.google.j2objc.annotations.Weak;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@GwtCompatible(serializable=true, emulated=true)
public class ImmutableSetMultimap<K, V>
extends ImmutableMultimap<K, V>
implements SetMultimap<K, V> {
    private final transient ImmutableSet<V> emptySet;
    @LazyInit
    @RetainedWith
    private transient ImmutableSetMultimap<V, K> inverse;
    private transient ImmutableSet<Map.Entry<K, V>> entries;
    @GwtIncompatible
    private static final long serialVersionUID = 0L;

    @Beta
    public static <T, K, V> Collector<T, ?, ImmutableSetMultimap<K, V>> toImmutableSetMultimap(Function<? super T, ? extends K> function, Function<? super T, ? extends V> function2) {
        Preconditions.checkNotNull(function, "keyFunction");
        Preconditions.checkNotNull(function2, "valueFunction");
        return Collector.of(ImmutableSetMultimap::builder, (arg_0, arg_1) -> ImmutableSetMultimap.lambda$toImmutableSetMultimap$0(function, function2, arg_0, arg_1), Builder::combine, Builder::build, new Collector.Characteristics[0]);
    }

    @Beta
    public static <T, K, V> Collector<T, ?, ImmutableSetMultimap<K, V>> flatteningToImmutableSetMultimap(Function<? super T, ? extends K> function, Function<? super T, ? extends Stream<? extends V>> function2) {
        Preconditions.checkNotNull(function);
        Preconditions.checkNotNull(function2);
        return Collectors.collectingAndThen(Multimaps.flatteningToMultimap(arg_0 -> ImmutableSetMultimap.lambda$flatteningToImmutableSetMultimap$1(function, arg_0), arg_0 -> ImmutableSetMultimap.lambda$flatteningToImmutableSetMultimap$2(function2, arg_0), MultimapBuilder.linkedHashKeys().linkedHashSetValues()::build), ImmutableSetMultimap::copyOf);
    }

    public static <K, V> ImmutableSetMultimap<K, V> of() {
        return EmptyImmutableSetMultimap.INSTANCE;
    }

    public static <K, V> ImmutableSetMultimap<K, V> of(K k, V v) {
        Builder<K, V> builder = ImmutableSetMultimap.builder();
        builder.put((Object)k, (Object)v);
        return builder.build();
    }

    public static <K, V> ImmutableSetMultimap<K, V> of(K k, V v, K k2, V v2) {
        Builder<K, V> builder = ImmutableSetMultimap.builder();
        builder.put((Object)k, (Object)v);
        builder.put((Object)k2, (Object)v2);
        return builder.build();
    }

    public static <K, V> ImmutableSetMultimap<K, V> of(K k, V v, K k2, V v2, K k3, V v3) {
        Builder<K, V> builder = ImmutableSetMultimap.builder();
        builder.put((Object)k, (Object)v);
        builder.put((Object)k2, (Object)v2);
        builder.put((Object)k3, (Object)v3);
        return builder.build();
    }

    public static <K, V> ImmutableSetMultimap<K, V> of(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4) {
        Builder<K, V> builder = ImmutableSetMultimap.builder();
        builder.put((Object)k, (Object)v);
        builder.put((Object)k2, (Object)v2);
        builder.put((Object)k3, (Object)v3);
        builder.put((Object)k4, (Object)v4);
        return builder.build();
    }

    public static <K, V> ImmutableSetMultimap<K, V> of(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        Builder<K, V> builder = ImmutableSetMultimap.builder();
        builder.put((Object)k, (Object)v);
        builder.put((Object)k2, (Object)v2);
        builder.put((Object)k3, (Object)v3);
        builder.put((Object)k4, (Object)v4);
        builder.put((Object)k5, (Object)v5);
        return builder.build();
    }

    public static <K, V> Builder<K, V> builder() {
        return new Builder();
    }

    public static <K, V> ImmutableSetMultimap<K, V> copyOf(Multimap<? extends K, ? extends V> multimap) {
        return ImmutableSetMultimap.copyOf(multimap, null);
    }

    private static <K, V> ImmutableSetMultimap<K, V> copyOf(Multimap<? extends K, ? extends V> multimap, Comparator<? super V> comparator) {
        Object object;
        Preconditions.checkNotNull(multimap);
        if (multimap.isEmpty() && comparator == null) {
            return ImmutableSetMultimap.of();
        }
        if (multimap instanceof ImmutableSetMultimap && !((ImmutableMultimap)(object = (ImmutableSetMultimap)multimap)).isPartialView()) {
            return object;
        }
        object = new ImmutableMap.Builder(multimap.asMap().size());
        int n = 0;
        for (Map.Entry<K, Collection<V>> entry : multimap.asMap().entrySet()) {
            K k = entry.getKey();
            Collection<? extends V> collection = entry.getValue();
            ImmutableSet<V> immutableSet = ImmutableSetMultimap.valueSet(comparator, collection);
            if (immutableSet.isEmpty()) continue;
            ((ImmutableMap.Builder)object).put(k, immutableSet);
            n += immutableSet.size();
        }
        return new ImmutableSetMultimap(((ImmutableMap.Builder)object).build(), n, comparator);
    }

    @Beta
    public static <K, V> ImmutableSetMultimap<K, V> copyOf(Iterable<? extends Map.Entry<? extends K, ? extends V>> iterable) {
        return ((Builder)new Builder().putAll((Iterable)iterable)).build();
    }

    ImmutableSetMultimap(ImmutableMap<K, ImmutableSet<V>> immutableMap, int n, @Nullable Comparator<? super V> comparator) {
        super(immutableMap, n);
        this.emptySet = ImmutableSetMultimap.emptySet(comparator);
    }

    @Override
    public ImmutableSet<V> get(@Nullable K k) {
        ImmutableSet immutableSet = (ImmutableSet)this.map.get(k);
        return MoreObjects.firstNonNull(immutableSet, this.emptySet);
    }

    @Override
    public ImmutableSetMultimap<V, K> inverse() {
        ImmutableSetMultimap<K, V> immutableSetMultimap = this.inverse;
        return immutableSetMultimap == null ? (this.inverse = this.invert()) : immutableSetMultimap;
    }

    private ImmutableSetMultimap<V, K> invert() {
        Builder<K, V> builder = ImmutableSetMultimap.builder();
        for (Map.Entry entry : this.entries()) {
            builder.put(entry.getValue(), entry.getKey());
        }
        ImmutableMultimap immutableMultimap = builder.build();
        ((ImmutableSetMultimap)immutableMultimap).inverse = this;
        return immutableMultimap;
    }

    @Override
    @Deprecated
    @CanIgnoreReturnValue
    public ImmutableSet<V> removeAll(Object object) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    @CanIgnoreReturnValue
    public ImmutableSet<V> replaceValues(K k, Iterable<? extends V> iterable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ImmutableSet<Map.Entry<K, V>> entries() {
        ImmutableSet<Map.Entry<K, V>> immutableSet = this.entries;
        return immutableSet == null ? (this.entries = new EntrySet(this)) : immutableSet;
    }

    private static <V> ImmutableSet<V> valueSet(@Nullable Comparator<? super V> comparator, Collection<? extends V> collection) {
        return comparator == null ? ImmutableSet.copyOf(collection) : ImmutableSortedSet.copyOf(comparator, collection);
    }

    private static <V> ImmutableSet<V> emptySet(@Nullable Comparator<? super V> comparator) {
        return comparator == null ? ImmutableSet.of() : ImmutableSortedSet.emptySet(comparator);
    }

    private static <V> ImmutableSet.Builder<V> valuesBuilder(@Nullable Comparator<? super V> comparator) {
        return comparator == null ? new ImmutableSet.Builder() : new ImmutableSortedSet.Builder<V>(comparator);
    }

    @GwtIncompatible
    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.valueComparator());
        Serialization.writeMultimap(this, objectOutputStream);
    }

    @Nullable
    Comparator<? super V> valueComparator() {
        return this.emptySet instanceof ImmutableSortedSet ? ((ImmutableSortedSet)this.emptySet).comparator() : null;
    }

    @GwtIncompatible
    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        ImmutableMap immutableMap;
        objectInputStream.defaultReadObject();
        Comparator comparator = (Comparator)objectInputStream.readObject();
        int n = objectInputStream.readInt();
        if (n < 0) {
            throw new InvalidObjectException("Invalid key count " + n);
        }
        ImmutableMap.Builder<Object, ImmutableCollection> builder = ImmutableMap.builder();
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            Object object = objectInputStream.readObject();
            int n3 = objectInputStream.readInt();
            if (n3 <= 0) {
                throw new InvalidObjectException("Invalid value count " + n3);
            }
            ImmutableSet.Builder<V> builder2 = ImmutableSetMultimap.valuesBuilder(comparator);
            for (int j = 0; j < n3; ++j) {
                builder2.add(objectInputStream.readObject());
            }
            ImmutableCollection immutableCollection = builder2.build();
            if (immutableCollection.size() != n3) {
                throw new InvalidObjectException("Duplicate key-value pairs exist for key " + object);
            }
            builder.put(object, immutableCollection);
            n2 += n3;
        }
        try {
            immutableMap = builder.build();
        } catch (IllegalArgumentException illegalArgumentException) {
            throw (InvalidObjectException)new InvalidObjectException(illegalArgumentException.getMessage()).initCause(illegalArgumentException);
        }
        ImmutableMultimap.FieldSettersHolder.MAP_FIELD_SETTER.set((ImmutableMultimap)this, immutableMap);
        ImmutableMultimap.FieldSettersHolder.SIZE_FIELD_SETTER.set((ImmutableMultimap)this, n2);
        ImmutableMultimap.FieldSettersHolder.EMPTY_SET_FIELD_SETTER.set(this, ImmutableSetMultimap.emptySet(comparator));
    }

    @Override
    public ImmutableCollection entries() {
        return this.entries();
    }

    @Override
    public ImmutableMultimap inverse() {
        return this.inverse();
    }

    @Override
    public ImmutableCollection get(@Nullable Object object) {
        return this.get(object);
    }

    @Override
    @Deprecated
    @CanIgnoreReturnValue
    public ImmutableCollection replaceValues(Object object, Iterable iterable) {
        return this.replaceValues(object, iterable);
    }

    @Override
    @Deprecated
    @CanIgnoreReturnValue
    public ImmutableCollection removeAll(Object object) {
        return this.removeAll(object);
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
    public Collection get(@Nullable Object object) {
        return this.get(object);
    }

    @Override
    @Deprecated
    @CanIgnoreReturnValue
    public Collection removeAll(Object object) {
        return this.removeAll(object);
    }

    @Override
    public Set entries() {
        return this.entries();
    }

    @Override
    @Deprecated
    @CanIgnoreReturnValue
    public Set replaceValues(Object object, Iterable iterable) {
        return this.replaceValues(object, iterable);
    }

    @Override
    @Deprecated
    @CanIgnoreReturnValue
    public Set removeAll(Object object) {
        return this.removeAll(object);
    }

    @Override
    public Set get(@Nullable Object object) {
        return this.get(object);
    }

    private static Stream lambda$flatteningToImmutableSetMultimap$2(Function function, Object object) {
        return ((Stream)function.apply(object)).peek(Preconditions::checkNotNull);
    }

    private static Object lambda$flatteningToImmutableSetMultimap$1(Function function, Object object) {
        return Preconditions.checkNotNull(function.apply(object));
    }

    private static void lambda$toImmutableSetMultimap$0(Function function, Function function2, Builder builder, Object object) {
        builder.put(function.apply(object), function2.apply(object));
    }

    static ImmutableSetMultimap access$000(Multimap multimap, Comparator comparator) {
        return ImmutableSetMultimap.copyOf(multimap, comparator);
    }

    private static final class EntrySet<K, V>
    extends ImmutableSet<Map.Entry<K, V>> {
        @Weak
        private final transient ImmutableSetMultimap<K, V> multimap;

        EntrySet(ImmutableSetMultimap<K, V> immutableSetMultimap) {
            this.multimap = immutableSetMultimap;
        }

        @Override
        public boolean contains(@Nullable Object object) {
            if (object instanceof Map.Entry) {
                Map.Entry entry = (Map.Entry)object;
                return this.multimap.containsEntry(entry.getKey(), entry.getValue());
            }
            return true;
        }

        @Override
        public int size() {
            return this.multimap.size();
        }

        @Override
        public UnmodifiableIterator<Map.Entry<K, V>> iterator() {
            return this.multimap.entryIterator();
        }

        @Override
        boolean isPartialView() {
            return true;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }
    }

    public static final class Builder<K, V>
    extends ImmutableMultimap.Builder<K, V> {
        public Builder() {
            super(MultimapBuilder.linkedHashKeys().linkedHashSetValues().build());
        }

        @Override
        @CanIgnoreReturnValue
        public Builder<K, V> put(K k, V v) {
            this.builderMultimap.put(Preconditions.checkNotNull(k), Preconditions.checkNotNull(v));
            return this;
        }

        @Override
        @CanIgnoreReturnValue
        public Builder<K, V> put(Map.Entry<? extends K, ? extends V> entry) {
            this.builderMultimap.put(Preconditions.checkNotNull(entry.getKey()), Preconditions.checkNotNull(entry.getValue()));
            return this;
        }

        @Override
        @CanIgnoreReturnValue
        @Beta
        public Builder<K, V> putAll(Iterable<? extends Map.Entry<? extends K, ? extends V>> iterable) {
            super.putAll(iterable);
            return this;
        }

        @Override
        @CanIgnoreReturnValue
        public Builder<K, V> putAll(K k, Iterable<? extends V> iterable) {
            Collection collection = this.builderMultimap.get(Preconditions.checkNotNull(k));
            for (V v : iterable) {
                collection.add(Preconditions.checkNotNull(v));
            }
            return this;
        }

        @Override
        @CanIgnoreReturnValue
        public Builder<K, V> putAll(K k, V ... VArray) {
            return this.putAll((Object)k, Arrays.asList(VArray));
        }

        @Override
        @CanIgnoreReturnValue
        public Builder<K, V> putAll(Multimap<? extends K, ? extends V> multimap) {
            for (Map.Entry<K, Collection<V>> entry : multimap.asMap().entrySet()) {
                this.putAll((Object)entry.getKey(), entry.getValue());
            }
            return this;
        }

        @Override
        @CanIgnoreReturnValue
        Builder<K, V> combine(ImmutableMultimap.Builder<K, V> builder) {
            super.combine(builder);
            return this;
        }

        @Override
        @CanIgnoreReturnValue
        public Builder<K, V> orderKeysBy(Comparator<? super K> comparator) {
            this.keyComparator = Preconditions.checkNotNull(comparator);
            return this;
        }

        @Override
        @CanIgnoreReturnValue
        public Builder<K, V> orderValuesBy(Comparator<? super V> comparator) {
            super.orderValuesBy(comparator);
            return this;
        }

        @Override
        public ImmutableSetMultimap<K, V> build() {
            if (this.keyComparator != null) {
                Multimap multimap = MultimapBuilder.linkedHashKeys().linkedHashSetValues().build();
                ImmutableList immutableList = Ordering.from(this.keyComparator).onKeys().immutableSortedCopy(this.builderMultimap.asMap().entrySet());
                for (Map.Entry entry : immutableList) {
                    multimap.putAll(entry.getKey(), (Iterable)entry.getValue());
                }
                this.builderMultimap = multimap;
            }
            return ImmutableSetMultimap.access$000(this.builderMultimap, this.valueComparator);
        }

        @Override
        public ImmutableMultimap build() {
            return this.build();
        }

        @Override
        @CanIgnoreReturnValue
        ImmutableMultimap.Builder combine(ImmutableMultimap.Builder builder) {
            return this.combine(builder);
        }

        @Override
        @CanIgnoreReturnValue
        public ImmutableMultimap.Builder orderValuesBy(Comparator comparator) {
            return this.orderValuesBy(comparator);
        }

        @Override
        @CanIgnoreReturnValue
        public ImmutableMultimap.Builder orderKeysBy(Comparator comparator) {
            return this.orderKeysBy(comparator);
        }

        @Override
        @CanIgnoreReturnValue
        public ImmutableMultimap.Builder putAll(Multimap multimap) {
            return this.putAll(multimap);
        }

        @Override
        @CanIgnoreReturnValue
        public ImmutableMultimap.Builder putAll(Object object, Object[] objectArray) {
            return this.putAll(object, objectArray);
        }

        @Override
        @CanIgnoreReturnValue
        public ImmutableMultimap.Builder putAll(Object object, Iterable iterable) {
            return this.putAll(object, iterable);
        }

        @Override
        @CanIgnoreReturnValue
        @Beta
        public ImmutableMultimap.Builder putAll(Iterable iterable) {
            return this.putAll(iterable);
        }

        @Override
        @CanIgnoreReturnValue
        public ImmutableMultimap.Builder put(Map.Entry entry) {
            return this.put(entry);
        }

        @Override
        @CanIgnoreReturnValue
        public ImmutableMultimap.Builder put(Object object, Object object2) {
            return this.put(object, object2);
        }
    }
}

