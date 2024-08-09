/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.reflect;

import com.google.common.annotations.Beta;
import com.google.common.collect.ForwardingMap;
import com.google.common.collect.ImmutableMap;
import com.google.common.reflect.TypeToInstanceMap;
import com.google.common.reflect.TypeToken;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Map;

@Beta
public final class ImmutableTypeToInstanceMap<B>
extends ForwardingMap<TypeToken<? extends B>, B>
implements TypeToInstanceMap<B> {
    private final ImmutableMap<TypeToken<? extends B>, B> delegate;

    public static <B> ImmutableTypeToInstanceMap<B> of() {
        return new ImmutableTypeToInstanceMap(ImmutableMap.of());
    }

    public static <B> Builder<B> builder() {
        return new Builder(null);
    }

    private ImmutableTypeToInstanceMap(ImmutableMap<TypeToken<? extends B>, B> immutableMap) {
        this.delegate = immutableMap;
    }

    @Override
    public <T extends B> T getInstance(TypeToken<T> typeToken) {
        return this.trustedGet(typeToken.rejectTypeVariables());
    }

    @Override
    @Deprecated
    @CanIgnoreReturnValue
    public <T extends B> T putInstance(TypeToken<T> typeToken, T t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T extends B> T getInstance(Class<T> clazz) {
        return this.trustedGet(TypeToken.of(clazz));
    }

    @Override
    @Deprecated
    @CanIgnoreReturnValue
    public <T extends B> T putInstance(Class<T> clazz, T t) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    @CanIgnoreReturnValue
    public B put(TypeToken<? extends B> typeToken, B b) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public void putAll(Map<? extends TypeToken<? extends B>, ? extends B> map) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected Map<TypeToken<? extends B>, B> delegate() {
        return this.delegate;
    }

    private <T extends B> T trustedGet(TypeToken<T> typeToken) {
        return (T)this.delegate.get(typeToken);
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

    ImmutableTypeToInstanceMap(ImmutableMap immutableMap, 1 var2_2) {
        this(immutableMap);
    }

    @Beta
    public static final class Builder<B> {
        private final ImmutableMap.Builder<TypeToken<? extends B>, B> mapBuilder = ImmutableMap.builder();

        private Builder() {
        }

        @CanIgnoreReturnValue
        public <T extends B> Builder<B> put(Class<T> clazz, T t) {
            this.mapBuilder.put(TypeToken.of(clazz), t);
            return this;
        }

        @CanIgnoreReturnValue
        public <T extends B> Builder<B> put(TypeToken<T> typeToken, T t) {
            this.mapBuilder.put(typeToken.rejectTypeVariables(), t);
            return this;
        }

        public ImmutableTypeToInstanceMap<B> build() {
            return new ImmutableTypeToInstanceMap(this.mapBuilder.build(), null);
        }

        Builder(1 var1_1) {
            this();
        }
    }
}

