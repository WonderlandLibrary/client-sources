/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.concurrent;

import java.util.concurrent.atomic.AtomicReference;
import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.apache.commons.lang3.concurrent.ConcurrentInitializer;

public abstract class AtomicInitializer<T>
implements ConcurrentInitializer<T> {
    private final AtomicReference<T> reference = new AtomicReference();

    @Override
    public T get() throws ConcurrentException {
        T t = this.reference.get();
        if (t == null && !this.reference.compareAndSet(null, t = this.initialize())) {
            t = this.reference.get();
        }
        return t;
    }

    protected abstract T initialize() throws ConcurrentException;
}

