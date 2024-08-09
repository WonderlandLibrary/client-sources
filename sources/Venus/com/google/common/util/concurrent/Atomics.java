/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;
import javax.annotation.Nullable;

@GwtIncompatible
public final class Atomics {
    private Atomics() {
    }

    public static <V> AtomicReference<V> newReference() {
        return new AtomicReference();
    }

    public static <V> AtomicReference<V> newReference(@Nullable V v) {
        return new AtomicReference<V>(v);
    }

    public static <E> AtomicReferenceArray<E> newReferenceArray(int n) {
        return new AtomicReferenceArray(n);
    }

    public static <E> AtomicReferenceArray<E> newReferenceArray(E[] EArray) {
        return new AtomicReferenceArray<E>(EArray);
    }
}

