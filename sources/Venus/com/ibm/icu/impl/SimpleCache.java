/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.ICUCache;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SimpleCache<K, V>
implements ICUCache<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private volatile Reference<Map<K, V>> cacheRef = null;
    private int type = 0;
    private int capacity = 16;

    public SimpleCache() {
    }

    public SimpleCache(int n) {
        this(n, 16);
    }

    public SimpleCache(int n, int n2) {
        if (n == 1) {
            this.type = n;
        }
        if (n2 > 0) {
            this.capacity = n2;
        }
    }

    @Override
    public V get(Object object) {
        Map<K, V> map;
        Reference<Map<K, V>> reference = this.cacheRef;
        if (reference != null && (map = reference.get()) != null) {
            return map.get(object);
        }
        return null;
    }

    @Override
    public void put(K k, V v) {
        Reference reference = this.cacheRef;
        Map<K, V> map = null;
        if (reference != null) {
            map = reference.get();
        }
        if (map == null) {
            map = Collections.synchronizedMap(new HashMap(this.capacity));
            reference = this.type == 1 ? new WeakReference<Map<K, V>>(map) : new SoftReference<Map<K, V>>(map);
            this.cacheRef = reference;
        }
        map.put(k, v);
    }

    @Override
    public void clear() {
        this.cacheRef = null;
    }
}

