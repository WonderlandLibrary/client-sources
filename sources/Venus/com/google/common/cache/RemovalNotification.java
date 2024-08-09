/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.cache;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.cache.RemovalCause;
import java.util.AbstractMap;
import javax.annotation.Nullable;

@GwtCompatible
public final class RemovalNotification<K, V>
extends AbstractMap.SimpleImmutableEntry<K, V> {
    private final RemovalCause cause;
    private static final long serialVersionUID = 0L;

    public static <K, V> RemovalNotification<K, V> create(@Nullable K k, @Nullable V v, RemovalCause removalCause) {
        return new RemovalNotification<K, V>(k, v, removalCause);
    }

    private RemovalNotification(@Nullable K k, @Nullable V v, RemovalCause removalCause) {
        super(k, v);
        this.cause = Preconditions.checkNotNull(removalCause);
    }

    public RemovalCause getCause() {
        return this.cause;
    }

    public boolean wasEvicted() {
        return this.cause.wasEvicted();
    }
}

