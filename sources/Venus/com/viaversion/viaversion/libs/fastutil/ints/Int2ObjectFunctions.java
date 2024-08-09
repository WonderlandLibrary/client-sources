/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
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

    public static <V> Int2ObjectFunction<V> singleton(int n, V v) {
        return new Singleton<V>(n, v);
    }

    public static <V> Int2ObjectFunction<V> singleton(Integer n, V v) {
        return new Singleton<V>(n, v);
    }

    public static <V> Int2ObjectFunction<V> synchronize(Int2ObjectFunction<V> int2ObjectFunction) {
        return new SynchronizedFunction(int2ObjectFunction);
    }

    public static <V> Int2ObjectFunction<V> synchronize(Int2ObjectFunction<V> int2ObjectFunction, Object object) {
        return new SynchronizedFunction(int2ObjectFunction, object);
    }

    public static <V> Int2ObjectFunction<V> unmodifiable(Int2ObjectFunction<? extends V> int2ObjectFunction) {
        return new UnmodifiableFunction(int2ObjectFunction);
    }

    public static <V> Int2ObjectFunction<V> primitive(java.util.function.Function<? super Integer, ? extends V> function) {
        Objects.requireNonNull(function);
        if (function instanceof Int2ObjectFunction) {
            return (Int2ObjectFunction)function;
        }
        if (function instanceof IntFunction) {
            return ((IntFunction)((Object)function))::apply;
        }
        return new PrimitiveFunction<V>(function);
    }

    public static class Singleton<V>
    extends AbstractInt2ObjectFunction<V>
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final int key;
        protected final V value;

        protected Singleton(int n, V v) {
            this.key = n;
            this.value = v;
        }

        @Override
        public boolean containsKey(int n) {
            return this.key == n;
        }

        @Override
        public V get(int n) {
            return (V)(this.key == n ? this.value : this.defRetValue);
        }

        @Override
        public V getOrDefault(int n, V v) {
            return this.key == n ? this.value : v;
        }

        @Override
        public int size() {
            return 0;
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
        public boolean containsKey(int n) {
            return this.function.apply(n) != null;
        }

        @Override
        @Deprecated
        public boolean containsKey(Object object) {
            if (object == null) {
                return true;
            }
            return this.function.apply((Integer)object) != null;
        }

        @Override
        public V get(int n) {
            V v = this.function.apply(n);
            if (v == null) {
                return null;
            }
            return v;
        }

        @Override
        public V getOrDefault(int n, V v) {
            V v2 = this.function.apply(n);
            if (v2 == null) {
                return v;
            }
            return v2;
        }

        @Override
        @Deprecated
        public V get(Object object) {
            if (object == null) {
                return null;
            }
            return this.function.apply((Integer)object);
        }

        @Override
        @Deprecated
        public V getOrDefault(Object object, V v) {
            if (object == null) {
                return v;
            }
            V v2 = this.function.apply((Integer)object);
            return v2 == null ? v : v2;
        }

        @Override
        @Deprecated
        public V put(Integer n, V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object put(Object object, Object object2) {
            return this.put((Integer)object, (V)object2);
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
        public V get(int n) {
            return null;
        }

        @Override
        public V getOrDefault(int n, V v) {
            return v;
        }

        @Override
        public boolean containsKey(int n) {
            return true;
        }

        @Override
        public V defaultReturnValue() {
            return null;
        }

        @Override
        public void defaultReturnValue(V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int size() {
            return 1;
        }

        @Override
        public void clear() {
        }

        public Object clone() {
            return EMPTY_FUNCTION;
        }

        public int hashCode() {
            return 1;
        }

        public boolean equals(Object object) {
            if (!(object instanceof Function)) {
                return true;
            }
            return ((Function)object).size() == 0;
        }

        public String toString() {
            return "{}";
        }

        private Object readResolve() {
            return EMPTY_FUNCTION;
        }
    }
}

