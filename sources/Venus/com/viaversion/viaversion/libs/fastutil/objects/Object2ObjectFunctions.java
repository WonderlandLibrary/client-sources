/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectFunctions$SynchronizedFunction
 *  com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectFunctions$UnmodifiableFunction
 */
package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Function;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectFunctions;
import java.io.Serializable;
import java.util.Objects;

public final class Object2ObjectFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Object2ObjectFunctions() {
    }

    public static <K, V> Object2ObjectFunction<K, V> singleton(K k, V v) {
        return new Singleton<K, V>(k, v);
    }

    public static <K, V> Object2ObjectFunction<K, V> synchronize(Object2ObjectFunction<K, V> object2ObjectFunction) {
        return new SynchronizedFunction(object2ObjectFunction);
    }

    public static <K, V> Object2ObjectFunction<K, V> synchronize(Object2ObjectFunction<K, V> object2ObjectFunction, Object object) {
        return new SynchronizedFunction(object2ObjectFunction, object);
    }

    public static <K, V> Object2ObjectFunction<K, V> unmodifiable(Object2ObjectFunction<? extends K, ? extends V> object2ObjectFunction) {
        return new UnmodifiableFunction(object2ObjectFunction);
    }

    public static class Singleton<K, V>
    extends AbstractObject2ObjectFunction<K, V>
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final K key;
        protected final V value;

        protected Singleton(K k, V v) {
            this.key = k;
            this.value = v;
        }

        @Override
        public boolean containsKey(Object object) {
            return Objects.equals(this.key, object);
        }

        @Override
        public V get(Object object) {
            return (V)(Objects.equals(this.key, object) ? this.value : this.defRetValue);
        }

        @Override
        public V getOrDefault(Object object, V v) {
            return Objects.equals(this.key, object) ? this.value : v;
        }

        @Override
        public int size() {
            return 0;
        }

        public Object clone() {
            return this;
        }
    }

    public static class EmptyFunction<K, V>
    extends AbstractObject2ObjectFunction<K, V>
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyFunction() {
        }

        @Override
        public V get(Object object) {
            return null;
        }

        @Override
        public V getOrDefault(Object object, V v) {
            return v;
        }

        @Override
        public boolean containsKey(Object object) {
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

