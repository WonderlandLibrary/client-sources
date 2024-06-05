package com.ibm.icu.impl;

import java.util.*;
import java.lang.ref.*;

public class SimpleCache<K, V> implements ICUCache<K, V>
{
    private static final int DEFAULT_CAPACITY = 16;
    private volatile Reference<Map<K, V>> cacheRef;
    private int type;
    private int capacity;
    
    public SimpleCache() {
        this.cacheRef = null;
        this.type = 0;
        this.capacity = 16;
    }
    
    public SimpleCache(final int cacheType) {
        this(cacheType, 16);
    }
    
    public SimpleCache(final int cacheType, final int initialCapacity) {
        this.cacheRef = null;
        this.type = 0;
        this.capacity = 16;
        if (cacheType == 1) {
            this.type = cacheType;
        }
        if (initialCapacity > 0) {
            this.capacity = initialCapacity;
        }
    }
    
    @Override
    public V get(final Object key) {
        final Reference<Map<K, V>> ref = this.cacheRef;
        if (ref != null) {
            final Map<K, V> map = ref.get();
            if (map != null) {
                return map.get(key);
            }
        }
        return null;
    }
    
    @Override
    public void put(final K key, final V value) {
        Reference<Map<K, V>> ref = this.cacheRef;
        Map<K, V> map = null;
        if (ref != null) {
            map = ref.get();
        }
        if (map == null) {
            map = Collections.synchronizedMap(new HashMap<K, V>(this.capacity));
            if (this.type == 1) {
                ref = new WeakReference<Map<K, V>>(map);
            }
            else {
                ref = new SoftReference<Map<K, V>>(map);
            }
            this.cacheRef = ref;
        }
        map.put(key, value);
    }
    
    @Override
    public void clear() {
        this.cacheRef = null;
    }
}
