/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.EmptyImmutableListMultimap;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Serialization;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.concurrent.LazyInit;
import com.google.j2objc.annotations.RetainedWith;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@GwtCompatible(serializable=true, emulated=true)
public class ImmutableListMultimap<K, V>
extends ImmutableMultimap<K, V>
implements ListMultimap<K, V> {
    @LazyInit
    @RetainedWith
    private transient ImmutableListMultimap<V, K> inverse;
    @GwtIncompatible
    private static final long serialVersionUID = 0L;

    @Beta
    public static <T, K, V> Collector<T, ?, ImmutableListMultimap<K, V>> toImmutableListMultimap(Function<? super T, ? extends K> function, Function<? super T, ? extends V> function2) {
        Preconditions.checkNotNull(function, "keyFunction");
        Preconditions.checkNotNull(function2, "valueFunction");
        return Collector.of(ImmutableListMultimap::builder, (arg_0, arg_1) -> ImmutableListMultimap.lambda$toImmutableListMultimap$0(function, function2, arg_0, arg_1), Builder::combine, Builder::build, new Collector.Characteristics[0]);
    }

    @Beta
    public static <T, K, V> Collector<T, ?, ImmutableListMultimap<K, V>> flatteningToImmutableListMultimap(Function<? super T, ? extends K> function, Function<? super T, ? extends Stream<? extends V>> function2) {
        Preconditions.checkNotNull(function);
        Preconditions.checkNotNull(function2);
        return Collectors.collectingAndThen(Multimaps.flatteningToMultimap(arg_0 -> ImmutableListMultimap.lambda$flatteningToImmutableListMultimap$1(function, arg_0), arg_0 -> ImmutableListMultimap.lambda$flatteningToImmutableListMultimap$2(function2, arg_0), MultimapBuilder.linkedHashKeys().arrayListValues()::build), ImmutableListMultimap::copyOf);
    }

    public static <K, V> ImmutableListMultimap<K, V> of() {
        return EmptyImmutableListMultimap.INSTANCE;
    }

    public static <K, V> ImmutableListMultimap<K, V> of(K k, V v) {
        Builder<K, V> builder = ImmutableListMultimap.builder();
        builder.put((Object)k, (Object)v);
        return builder.build();
    }

    public static <K, V> ImmutableListMultimap<K, V> of(K k, V v, K k2, V v2) {
        Builder<K, V> builder = ImmutableListMultimap.builder();
        builder.put((Object)k, (Object)v);
        builder.put((Object)k2, (Object)v2);
        return builder.build();
    }

    public static <K, V> ImmutableListMultimap<K, V> of(K k, V v, K k2, V v2, K k3, V v3) {
        Builder<K, V> builder = ImmutableListMultimap.builder();
        builder.put((Object)k, (Object)v);
        builder.put((Object)k2, (Object)v2);
        builder.put((Object)k3, (Object)v3);
        return builder.build();
    }

    public static <K, V> ImmutableListMultimap<K, V> of(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4) {
        Builder<K, V> builder = ImmutableListMultimap.builder();
        builder.put((Object)k, (Object)v);
        builder.put((Object)k2, (Object)v2);
        builder.put((Object)k3, (Object)v3);
        builder.put((Object)k4, (Object)v4);
        return builder.build();
    }

    public static <K, V> ImmutableListMultimap<K, V> of(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        Builder<K, V> builder = ImmutableListMultimap.builder();
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

    public static <K, V> ImmutableListMultimap<K, V> copyOf(Multimap<? extends K, ? extends V> multimap) {
        Object object;
        if (multimap.isEmpty()) {
            return ImmutableListMultimap.of();
        }
        if (multimap instanceof ImmutableListMultimap && !((ImmutableMultimap)(object = (ImmutableListMultimap)multimap)).isPartialView()) {
            return object;
        }
        object = new ImmutableMap.Builder(multimap.asMap().size());
        int n = 0;
        for (Map.Entry<K, Collection<V>> entry : multimap.asMap().entrySet()) {
            ImmutableList<V> immutableList = ImmutableList.copyOf(entry.getValue());
            if (immutableList.isEmpty()) continue;
            ((ImmutableMap.Builder)object).put(entry.getKey(), immutableList);
            n += immutableList.size();
        }
        return new ImmutableListMultimap(((ImmutableMap.Builder)object).build(), n);
    }

    @Beta
    public static <K, V> ImmutableListMultimap<K, V> copyOf(Iterable<? extends Map.Entry<? extends K, ? extends V>> iterable) {
        return ((Builder)new Builder().putAll((Iterable)iterable)).build();
    }

    ImmutableListMultimap(ImmutableMap<K, ImmutableList<V>> immutableMap, int n) {
        super(immutableMap, n);
    }

    @Override
    public ImmutableList<V> get(@Nullable K k) {
        ImmutableList immutableList = (ImmutableList)this.map.get(k);
        return immutableList == null ? ImmutableList.of() : immutableList;
    }

    @Override
    public ImmutableListMultimap<V, K> inverse() {
        ImmutableListMultimap<K, V> immutableListMultimap = this.inverse;
        return immutableListMultimap == null ? (this.inverse = this.invert()) : immutableListMultimap;
    }

    private ImmutableListMultimap<V, K> invert() {
        Builder<K, V> builder = ImmutableListMultimap.builder();
        for (Map.Entry entry : this.entries()) {
            builder.put(entry.getValue(), entry.getKey());
        }
        ImmutableMultimap immutableMultimap = builder.build();
        ((ImmutableListMultimap)immutableMultimap).inverse = this;
        return immutableMultimap;
    }

    @Override
    @Deprecated
    @CanIgnoreReturnValue
    public ImmutableList<V> removeAll(Object object) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    @CanIgnoreReturnValue
    public ImmutableList<V> replaceValues(K k, Iterable<? extends V> iterable) {
        throw new UnsupportedOperationException();
    }

    @GwtIncompatible
    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        Serialization.writeMultimap(this, objectOutputStream);
    }

    @GwtIncompatible
    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        ImmutableMap immutableMap;
        objectInputStream.defaultReadObject();
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
            ImmutableList.Builder builder2 = ImmutableList.builder();
            for (int j = 0; j < n3; ++j) {
                builder2.add(objectInputStream.readObject());
            }
            builder.put(object, builder2.build());
            n2 += n3;
        }
        try {
            immutableMap = builder.build();
        } catch (IllegalArgumentException illegalArgumentException) {
            throw (InvalidObjectException)new InvalidObjectException(illegalArgumentException.getMessage()).initCause(illegalArgumentException);
        }
        ImmutableMultimap.FieldSettersHolder.MAP_FIELD_SETTER.set((ImmutableMultimap)this, immutableMap);
        ImmutableMultimap.FieldSettersHolder.SIZE_FIELD_SETTER.set((ImmutableMultimap)this, n2);
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
    @Deprecated
    @CanIgnoreReturnValue
    public List replaceValues(Object object, Iterable iterable) {
        return this.replaceValues(object, iterable);
    }

    @Override
    @Deprecated
    @CanIgnoreReturnValue
    public List removeAll(Object object) {
        return this.removeAll(object);
    }

    @Override
    public List get(@Nullable Object object) {
        return this.get(object);
    }

    private static Stream lambda$flatteningToImmutableListMultimap$2(Function function, Object object) {
        return ((Stream)function.apply(object)).peek(Preconditions::checkNotNull);
    }

    private static Object lambda$flatteningToImmutableListMultimap$1(Function function, Object object) {
        return Preconditions.checkNotNull(function.apply(object));
    }

    private static void lambda$toImmutableListMultimap$0(Function function, Function function2, Builder builder, Object object) {
        builder.put(function.apply(object), function2.apply(object));
    }

    public static final class Builder<K, V>
    extends ImmutableMultimap.Builder<K, V> {
        @Override
        @CanIgnoreReturnValue
        public Builder<K, V> put(K k, V v) {
            super.put(k, v);
            return this;
        }

        @Override
        @CanIgnoreReturnValue
        public Builder<K, V> put(Map.Entry<? extends K, ? extends V> entry) {
            super.put(entry);
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
            super.putAll(k, iterable);
            return this;
        }

        @Override
        @CanIgnoreReturnValue
        public Builder<K, V> putAll(K k, V ... VArray) {
            super.putAll(k, VArray);
            return this;
        }

        @Override
        @CanIgnoreReturnValue
        public Builder<K, V> putAll(Multimap<? extends K, ? extends V> multimap) {
            super.putAll(multimap);
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
            super.orderKeysBy(comparator);
            return this;
        }

        @Override
        @CanIgnoreReturnValue
        public Builder<K, V> orderValuesBy(Comparator<? super V> comparator) {
            super.orderValuesBy(comparator);
            return this;
        }

        @Override
        public ImmutableListMultimap<K, V> build() {
            return (ImmutableListMultimap)super.build();
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

