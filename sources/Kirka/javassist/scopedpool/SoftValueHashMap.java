/*
 * Decompiled with CFR 0.143.
 */
package javassist.scopedpool;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SoftValueHashMap
extends AbstractMap
implements Map {
    private Map hash;
    private ReferenceQueue queue = new ReferenceQueue();

    public Set entrySet() {
        this.processQueue();
        return this.hash.entrySet();
    }

    private void processQueue() {
        SoftValueRef ref;
        while ((ref = (SoftValueRef)this.queue.poll()) != null) {
            if (ref != (SoftValueRef)this.hash.get(ref.key)) continue;
            this.hash.remove(ref.key);
        }
    }

    public SoftValueHashMap(int initialCapacity, float loadFactor) {
        this.hash = new HashMap(initialCapacity, loadFactor);
    }

    public SoftValueHashMap(int initialCapacity) {
        this.hash = new HashMap(initialCapacity);
    }

    public SoftValueHashMap() {
        this.hash = new HashMap();
    }

    public SoftValueHashMap(Map t) {
        this(Math.max(2 * t.size(), 11), 0.75f);
        this.putAll(t);
    }

    @Override
    public int size() {
        this.processQueue();
        return this.hash.size();
    }

    @Override
    public boolean isEmpty() {
        this.processQueue();
        return this.hash.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        this.processQueue();
        return this.hash.containsKey(key);
    }

    public Object get(Object key) {
        this.processQueue();
        SoftReference ref = (SoftReference)this.hash.get(key);
        if (ref != null) {
            return ref.get();
        }
        return null;
    }

    public Object put(Object key, Object value) {
        this.processQueue();
        SoftValueRef rtn = this.hash.put(key, SoftValueRef.create(key, value, this.queue));
        if (rtn != null) {
            rtn = ((SoftReference)rtn).get();
        }
        return rtn;
    }

    public Object remove(Object key) {
        this.processQueue();
        return this.hash.remove(key);
    }

    @Override
    public void clear() {
        this.processQueue();
        this.hash.clear();
    }

    private static class SoftValueRef
    extends SoftReference {
        public Object key;

        private SoftValueRef(Object key, Object val, ReferenceQueue q) {
            super(val, q);
            this.key = key;
        }

        private static SoftValueRef create(Object key, Object val, ReferenceQueue q) {
            if (val == null) {
                return null;
            }
            return new SoftValueRef(key, val, q);
        }
    }

}

