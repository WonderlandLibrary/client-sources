/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.ForwardingMap;
import com.google.common.collect.ImmutableMap;
import com.google.common.primitives.Primitives;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.util.Map;
import javax.annotation.Nullable;

@GwtIncompatible
public final class ImmutableClassToInstanceMap<B>
extends ForwardingMap<Class<? extends B>, B>
implements ClassToInstanceMap<B>,
Serializable {
    private static final ImmutableClassToInstanceMap<Object> EMPTY = new ImmutableClassToInstanceMap(ImmutableMap.of());
    private final ImmutableMap<Class<? extends B>, B> delegate;

    public static <B> ImmutableClassToInstanceMap<B> of() {
        return EMPTY;
    }

    public static <B, T extends B> ImmutableClassToInstanceMap<B> of(Class<T> clazz, T t) {
        ImmutableMap<Class<T>, T> immutableMap = ImmutableMap.of(clazz, t);
        return new ImmutableClassToInstanceMap<T>(immutableMap);
    }

    public static <B> Builder<B> builder() {
        return new Builder();
    }

    public static <B, S extends B> ImmutableClassToInstanceMap<B> copyOf(Map<? extends Class<? extends S>, ? extends S> map) {
        if (map instanceof ImmutableClassToInstanceMap) {
            ImmutableClassToInstanceMap immutableClassToInstanceMap = (ImmutableClassToInstanceMap)map;
            return immutableClassToInstanceMap;
        }
        return new Builder().putAll(map).build();
    }

    private ImmutableClassToInstanceMap(ImmutableMap<Class<? extends B>, B> immutableMap) {
        this.delegate = immutableMap;
    }

    @Override
    protected Map<Class<? extends B>, B> delegate() {
        return this.delegate;
    }

    @Override
    @Nullable
    public <T extends B> T getInstance(Class<T> clazz) {
        return (T)this.delegate.get(Preconditions.checkNotNull(clazz));
    }

    @Override
    @Deprecated
    @CanIgnoreReturnValue
    public <T extends B> T putInstance(Class<T> clazz, T t) {
        throw new UnsupportedOperationException();
    }

    Object readResolve() {
        return this.isEmpty() ? ImmutableClassToInstanceMap.of() : this;
    }

    @Override
    protected Object delegate() {
        return this.delegate();
    }

    ImmutableClassToInstanceMap(ImmutableMap immutableMap, 1 var2_2) {
        this(immutableMap);
    }

    public static final class Builder<B> {
        private final ImmutableMap.Builder<Class<? extends B>, B> mapBuilder = ImmutableMap.builder();

        @CanIgnoreReturnValue
        public <T extends B> Builder<B> put(Class<T> clazz, T t) {
            this.mapBuilder.put(clazz, t);
            return this;
        }

        @CanIgnoreReturnValue
        public <T extends B> Builder<B> putAll(Map<? extends Class<? extends T>, ? extends T> map) {
            for (Map.Entry<Class<T>, T> entry : map.entrySet()) {
                Class<? extends T> clazz = entry.getKey();
                T t = entry.getValue();
                this.mapBuilder.put(clazz, Builder.cast(clazz, t));
            }
            return this;
        }

        private static <B, T extends B> T cast(Class<T> clazz, B b) {
            return Primitives.wrap(clazz).cast(b);
        }

        public ImmutableClassToInstanceMap<B> build() {
            ImmutableMap<Class<B>, B> immutableMap = this.mapBuilder.build();
            if (immutableMap.isEmpty()) {
                return ImmutableClassToInstanceMap.of();
            }
            return new ImmutableClassToInstanceMap(immutableMap, null);
        }
    }
}

