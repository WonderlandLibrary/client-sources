/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

import java.lang.reflect.Array;
import java.util.ArrayDeque;

public class ArrayCache {
    private Class elementClass = null;
    private int maxCacheSize = 0;
    private ArrayDeque cache = new ArrayDeque();

    public ArrayCache(Class clazz, int n) {
        this.elementClass = clazz;
        this.maxCacheSize = n;
    }

    public synchronized Object allocate(int n) {
        Object object = this.cache.pollLast();
        if (object == null || Array.getLength(object) < n) {
            object = Array.newInstance(this.elementClass, n);
        }
        return object;
    }

    public synchronized void free(Object object) {
        if (object != null) {
            Class<?> clazz = object.getClass();
            if (clazz.getComponentType() != this.elementClass) {
                throw new IllegalArgumentException("Wrong component type");
            }
            if (this.cache.size() < this.maxCacheSize) {
                this.cache.add(object);
            }
        }
    }
}

