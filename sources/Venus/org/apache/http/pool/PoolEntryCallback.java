/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.pool;

import org.apache.http.pool.PoolEntry;

public interface PoolEntryCallback<T, C> {
    public void process(PoolEntry<T, C> var1);
}

