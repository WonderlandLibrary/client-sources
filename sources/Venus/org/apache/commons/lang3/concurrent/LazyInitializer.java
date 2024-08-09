/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.concurrent;

import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.apache.commons.lang3.concurrent.ConcurrentInitializer;

public abstract class LazyInitializer<T>
implements ConcurrentInitializer<T> {
    private volatile T object;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public T get() throws ConcurrentException {
        T t = this.object;
        if (t == null) {
            LazyInitializer lazyInitializer = this;
            synchronized (lazyInitializer) {
                t = this.object;
                if (t == null) {
                    this.object = t = this.initialize();
                }
            }
        }
        return t;
    }

    protected abstract T initialize() throws ConcurrentException;
}

