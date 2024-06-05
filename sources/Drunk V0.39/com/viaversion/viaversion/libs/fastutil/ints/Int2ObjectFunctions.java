/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunctions$SynchronizedFunction
 *  com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunctions$UnmodifiableFunction
 */
package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Function;
import com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunctions;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.IntFunction;

public final class Int2ObjectFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Int2ObjectFunctions() {
    }

    public static <V> Int2ObjectFunction<V> singleton(int key, V value) {
        return new Singleton<V>(key, value);
    }

    public static <V> Int2ObjectFunction<V> singleton(Integer key, V value) {
        return new Singleton<V>(key, value);
    }

    public static <V> Int2ObjectFunction<V> synchronize(Int2ObjectFunction<V> f) {
        return new SynchronizedFunction(f);
    }

    public static <V> Int2ObjectFunction<V> synchronize(Int2ObjectFunction<V> f, Object sync) {
        return new SynchronizedFunction(f, sync);
    }

    public static <V> Int2ObjectFunction<V> unmodifiable(Int2ObjectFunction<? extends V> f) {
        return new UnmodifiableFunction(f);
    }

    public static <V> Int2ObjectFunction<V> primitive(java.util.function.Function<? super Integer, ? extends V> f) {
        Objects.requireNonNull(f);
        if (f instanceof Int2ObjectFunction) {
            return (Int2ObjectFunction)f;
        }
        if (!(f instanceof IntFunction)) return new PrimitiveFunction<V>(f);
        return ((IntFunction)((Object)f))::apply;
    }

    public static class Singleton<V>
    extends AbstractInt2ObjectFunction<V>
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final int key;
        protected final V value;

        protected Singleton(int key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public boolean containsKey(int k) {
            if (this.key != k) return false;
            return true;
        }

        @Override
        public V get(int k) {
            Object object;
            if (this.key == k) {
                object = this.value;
                return object;
            }
            object = this.defRetValue;
            return object;
        }

        @Override
        public V getOrDefault(int k, V defaultValue) {
            V v;
            if (this.key == k) {
                v = this.value;
                return v;
            }
            v = defaultValue;
            return v;
        }

        @Override
        public int size() {
            return 1;
        }

        public Object clone() {
            return this;
        }
    }

    public static class PrimitiveFunction<V>
    implements Int2ObjectFunction<V> {
        protected final java.util.function.Function<? super Integer, ? extends V> function;

        protected PrimitiveFunction(java.util.function.Function<? super Integer, ? extends V> function) {
            this.function = function;
        }

        @Override
        public boolean containsKey(int key) {
            if (this.function.apply(key) == null) return false;
            return true;
        }

        @Override
        @Deprecated
        public boolean containsKey(Object key) {
            if (key == null) {
                return false;
            }
            if (this.function.apply((Integer)key) == null) return false;
            return true;
        }

        @Override
        public V get(int key) {
            V v = this.function.apply(key);
            if (v != null) return v;
            return null;
        }

        @Override
        public V getOrDefault(int key, V defaultValue) {
            V v = this.function.apply(key);
            if (v != null) return v;
            return defaultValue;
        }

        @Override
        @Deprecated
        public V get(Object key) {
            if (key != null) return this.function.apply((Integer)key);
            return null;
        }

        @Override
        @Deprecated
        public V getOrDefault(Object key, V defaultValue) {
            V v;
            if (key == null) {
                return defaultValue;
            }
            V v2 = this.function.apply((Integer)key);
            if (v2 == null) {
                v = defaultValue;
                return v;
            }
            v = v2;
            return v;
        }

        @Override
        @Deprecated
        public V put(Integer key, V value) {
            throw new UnsupportedOperationException();
        }
    }

    public static class EmptyFunction<V>
    extends AbstractInt2ObjectFunction<V>
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyFunction() {
        }

        @Override
        public V get(int k) {
            return null;
        }

        @Override
        public V getOrDefault(int k, V defaultValue) {
            return defaultValue;
        }

        @Override
        public boolean containsKey(int k) {
            return false;
        }

        @Override
        public V defaultReturnValue() {
            return null;
        }

        @Override
        public void defaultReturnValue(V defRetValue) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public void clear() {
        }

        public Object clone() {
            return EMPTY_FUNCTION;
        }

        public int hashCode() {
            return 0;
        }

        public boolean equals(Object o) {
            if (!(o instanceof Function)) {
                return false;
            }
            if (((Function)o).size() != 0) return false;
            return true;
        }

        public String toString() {
            return "{}";
        }

        private Object readResolve() {
            return EMPTY_FUNCTION;
        }
    }
}

