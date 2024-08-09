/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntSet;

public abstract class IntExpiringCache<T> {
    private final int intervalMs;
    private long timeCheckMs;
    private Int2ObjectOpenHashMap<Wrapper<T>> map = new Int2ObjectOpenHashMap();

    public IntExpiringCache(int n) {
        this.intervalMs = n;
    }

    public T get(int n) {
        Wrapper<T> wrapper2;
        long l = System.currentTimeMillis();
        if (!this.map.isEmpty() && l >= this.timeCheckMs) {
            this.timeCheckMs = l + (long)this.intervalMs;
            long l2 = l - (long)this.intervalMs;
            IntSet intSet = this.map.keySet();
            IntIterator intIterator = intSet.iterator();
            while (intIterator.hasNext()) {
                Wrapper<T> wrapper3;
                int n2 = intIterator.nextInt();
                if (n2 == n || (wrapper3 = this.map.get(n2)).getAccessTimeMs() > l2) continue;
                intIterator.remove();
            }
        }
        if ((wrapper2 = this.map.get(n)) == null) {
            T t = this.make();
            wrapper2 = new Wrapper<T>(t);
            this.map.put(n, wrapper2);
        }
        wrapper2.setAccessTimeMs(l);
        return wrapper2.getValue();
    }

    protected abstract T make();

    public static class Wrapper<T> {
        private final T value;
        private long accessTimeMs;

        public Wrapper(T t) {
            this.value = t;
        }

        public T getValue() {
            return this.value;
        }

        public long getAccessTimeMs() {
            return this.accessTimeMs;
        }

        public void setAccessTimeMs(long l) {
            this.accessTimeMs = l;
        }
    }
}

