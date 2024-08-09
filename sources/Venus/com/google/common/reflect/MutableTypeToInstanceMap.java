/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.reflect;

import com.google.common.annotations.Beta;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.ForwardingMap;
import com.google.common.collect.ForwardingMapEntry;
import com.google.common.collect.ForwardingSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToInstanceMap;
import com.google.common.reflect.TypeToken;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

@Beta
public final class MutableTypeToInstanceMap<B>
extends ForwardingMap<TypeToken<? extends B>, B>
implements TypeToInstanceMap<B> {
    private final Map<TypeToken<? extends B>, B> backingMap = Maps.newHashMap();

    @Override
    @Nullable
    public <T extends B> T getInstance(Class<T> clazz) {
        return this.trustedGet(TypeToken.of(clazz));
    }

    @Override
    @Nullable
    @CanIgnoreReturnValue
    public <T extends B> T putInstance(Class<T> clazz, @Nullable T t) {
        return this.trustedPut(TypeToken.of(clazz), t);
    }

    @Override
    @Nullable
    public <T extends B> T getInstance(TypeToken<T> typeToken) {
        return this.trustedGet(typeToken.rejectTypeVariables());
    }

    @Override
    @Nullable
    @CanIgnoreReturnValue
    public <T extends B> T putInstance(TypeToken<T> typeToken, @Nullable T t) {
        return this.trustedPut(typeToken.rejectTypeVariables(), t);
    }

    @Override
    @Deprecated
    @CanIgnoreReturnValue
    public B put(TypeToken<? extends B> typeToken, B b) {
        throw new UnsupportedOperationException("Please use putInstance() instead.");
    }

    @Override
    @Deprecated
    public void putAll(Map<? extends TypeToken<? extends B>, ? extends B> map) {
        throw new UnsupportedOperationException("Please use putInstance() instead.");
    }

    @Override
    public Set<Map.Entry<TypeToken<? extends B>, B>> entrySet() {
        return UnmodifiableEntry.transformEntries(super.entrySet());
    }

    @Override
    protected Map<TypeToken<? extends B>, B> delegate() {
        return this.backingMap;
    }

    @Nullable
    private <T extends B> T trustedPut(TypeToken<T> typeToken, @Nullable T t) {
        return (T)this.backingMap.put(typeToken, t);
    }

    @Nullable
    private <T extends B> T trustedGet(TypeToken<T> typeToken) {
        return (T)this.backingMap.get(typeToken);
    }

    @Override
    @Deprecated
    @CanIgnoreReturnValue
    public Object put(Object object, Object object2) {
        return this.put((TypeToken)object, object2);
    }

    @Override
    protected Object delegate() {
        return this.delegate();
    }

    private static final class UnmodifiableEntry<K, V>
    extends ForwardingMapEntry<K, V> {
        private final Map.Entry<K, V> delegate;

        static <K, V> Set<Map.Entry<K, V>> transformEntries(Set<Map.Entry<K, V>> set) {
            return new ForwardingSet<Map.Entry<K, V>>(set){
                final Set val$entries;
                {
                    this.val$entries = set;
                }

                @Override
                protected Set<Map.Entry<K, V>> delegate() {
                    return this.val$entries;
                }

                @Override
                public Iterator<Map.Entry<K, V>> iterator() {
                    return UnmodifiableEntry.access$000(super.iterator());
                }

                @Override
                public Object[] toArray() {
                    return this.standardToArray();
                }

                @Override
                public <T> T[] toArray(T[] TArray) {
                    return this.standardToArray(TArray);
                }

                @Override
                protected Collection delegate() {
                    return this.delegate();
                }

                @Override
                protected Object delegate() {
                    return this.delegate();
                }
            };
        }

        private static <K, V> Iterator<Map.Entry<K, V>> transformEntries(Iterator<Map.Entry<K, V>> iterator2) {
            return Iterators.transform(iterator2, new Function<Map.Entry<K, V>, Map.Entry<K, V>>(){

                @Override
                public Map.Entry<K, V> apply(Map.Entry<K, V> entry) {
                    return new UnmodifiableEntry(entry, null);
                }

                @Override
                public Object apply(Object object) {
                    return this.apply((Map.Entry)object);
                }
            });
        }

        private UnmodifiableEntry(Map.Entry<K, V> entry) {
            this.delegate = Preconditions.checkNotNull(entry);
        }

        @Override
        protected Map.Entry<K, V> delegate() {
            return this.delegate;
        }

        @Override
        public V setValue(V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        protected Object delegate() {
            return this.delegate();
        }

        static Iterator access$000(Iterator iterator2) {
            return UnmodifiableEntry.transformEntries(iterator2);
        }

        UnmodifiableEntry(Map.Entry entry, 1 var2_2) {
            this(entry);
        }
    }
}

