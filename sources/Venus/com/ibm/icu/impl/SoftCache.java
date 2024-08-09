/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.CacheBase;
import com.ibm.icu.impl.CacheValue;
import java.util.concurrent.ConcurrentHashMap;

public abstract class SoftCache<K, V, D>
extends CacheBase<K, V, D> {
    private ConcurrentHashMap<K, Object> map = new ConcurrentHashMap();

    @Override
    public final V getInstance(K k, D d) {
        CacheValue cacheValue = this.map.get(k);
        if (cacheValue != null) {
            if (!(cacheValue instanceof CacheValue)) {
                return (V)cacheValue;
            }
            CacheValue cacheValue2 = cacheValue;
            if (cacheValue2.isNull()) {
                return null;
            }
            Object v = cacheValue2.get();
            if (v != null) {
                return v;
            }
            v = this.createInstance(k, d);
            return cacheValue2.resetIfCleared(v);
        }
        Object v = this.createInstance(k, d);
        cacheValue = v != null && CacheValue.futureInstancesWillBeStrong() ? v : CacheValue.getInstance(v);
        if ((cacheValue = this.map.putIfAbsent(k, cacheValue)) == null) {
            return v;
        }
        if (!(cacheValue instanceof CacheValue)) {
            return (V)cacheValue;
        }
        CacheValue cacheValue3 = cacheValue;
        return cacheValue3.resetIfCleared(v);
    }
}

