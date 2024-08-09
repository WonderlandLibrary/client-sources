/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.util.ICUException;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;

public abstract class CacheValue<V> {
    private static volatile Strength strength = Strength.SOFT;
    private static final CacheValue NULL_VALUE = new NullValue(null);

    public static void setStrength(Strength strength) {
        CacheValue.strength = strength;
    }

    public static boolean futureInstancesWillBeStrong() {
        return strength == Strength.STRONG;
    }

    public static <V> CacheValue<V> getInstance(V v) {
        if (v == null) {
            return NULL_VALUE;
        }
        return strength == Strength.STRONG ? new StrongValue<V>(v) : new SoftValue<V>(v);
    }

    public boolean isNull() {
        return true;
    }

    public abstract V get();

    public abstract V resetIfCleared(V var1);

    private static final class SoftValue<V>
    extends CacheValue<V> {
        private volatile Reference<V> ref;

        SoftValue(V v) {
            this.ref = new SoftReference<V>(v);
        }

        @Override
        public V get() {
            return this.ref.get();
        }

        @Override
        public synchronized V resetIfCleared(V v) {
            V v2 = this.ref.get();
            if (v2 == null) {
                this.ref = new SoftReference<V>(v);
                return v;
            }
            return v2;
        }
    }

    private static final class StrongValue<V>
    extends CacheValue<V> {
        private V value;

        StrongValue(V v) {
            this.value = v;
        }

        @Override
        public V get() {
            return this.value;
        }

        @Override
        public V resetIfCleared(V v) {
            return this.value;
        }
    }

    private static final class NullValue<V>
    extends CacheValue<V> {
        private NullValue() {
        }

        @Override
        public boolean isNull() {
            return false;
        }

        @Override
        public V get() {
            return null;
        }

        @Override
        public V resetIfCleared(V v) {
            if (v != null) {
                throw new ICUException("resetting a null value to a non-null value");
            }
            return null;
        }

        NullValue(1 var1_1) {
            this();
        }
    }

    public static enum Strength {
        STRONG,
        SOFT;

    }
}

