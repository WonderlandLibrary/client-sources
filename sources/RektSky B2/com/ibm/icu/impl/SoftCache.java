package com.ibm.icu.impl;

import java.util.concurrent.*;

public abstract class SoftCache<K, V, D> extends CacheBase<K, V, D>
{
    private ConcurrentHashMap<K, Object> map;
    
    public SoftCache() {
        this.map = new ConcurrentHashMap<K, Object>();
    }
    
    @Override
    public final V getInstance(final K key, final D data) {
        Object mapValue = this.map.get(key);
        if (mapValue != null) {
            if (!(mapValue instanceof CacheValue)) {
                return (V)mapValue;
            }
            final CacheValue<V> cv = (CacheValue<V>)mapValue;
            if (cv.isNull()) {
                return null;
            }
            V value = cv.get();
            if (value != null) {
                return value;
            }
            value = this.createInstance(key, data);
            return cv.resetIfCleared(value);
        }
        else {
            final V value2 = this.createInstance(key, data);
            mapValue = ((value2 != null && CacheValue.futureInstancesWillBeStrong()) ? value2 : CacheValue.getInstance(value2));
            mapValue = this.map.putIfAbsent(key, mapValue);
            if (mapValue == null) {
                return value2;
            }
            if (!(mapValue instanceof CacheValue)) {
                return (V)mapValue;
            }
            final CacheValue<V> cv2 = (CacheValue<V>)mapValue;
            return cv2.resetIfCleared(value2);
        }
    }
}
