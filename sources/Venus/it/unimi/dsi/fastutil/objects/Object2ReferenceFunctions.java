/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.objects.AbstractObject2ReferenceFunction;
import it.unimi.dsi.fastutil.objects.Object2ReferenceFunction;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

public final class Object2ReferenceFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Object2ReferenceFunctions() {
    }

    public static <K, V> Object2ReferenceFunction<K, V> singleton(K k, V v) {
        return new Singleton<K, V>(k, v);
    }

    public static <K, V> Object2ReferenceFunction<K, V> synchronize(Object2ReferenceFunction<K, V> object2ReferenceFunction) {
        return new SynchronizedFunction<K, V>(object2ReferenceFunction);
    }

    public static <K, V> Object2ReferenceFunction<K, V> synchronize(Object2ReferenceFunction<K, V> object2ReferenceFunction, Object object) {
        return new SynchronizedFunction<K, V>(object2ReferenceFunction, object);
    }

    public static <K, V> Object2ReferenceFunction<K, V> unmodifiable(Object2ReferenceFunction<K, V> object2ReferenceFunction) {
        return new UnmodifiableFunction<K, V>(object2ReferenceFunction);
    }

    public static class UnmodifiableFunction<K, V>
    extends AbstractObject2ReferenceFunction<K, V>
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Object2ReferenceFunction<K, V> function;

        protected UnmodifiableFunction(Object2ReferenceFunction<K, V> object2ReferenceFunction) {
            if (object2ReferenceFunction == null) {
                throw new NullPointerException();
            }
            this.function = object2ReferenceFunction;
        }

        @Override
        public int size() {
            return this.function.size();
        }

        @Override
        public V defaultReturnValue() {
            return this.function.defaultReturnValue();
        }

        @Override
        public void defaultReturnValue(V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean containsKey(Object object) {
            return this.function.containsKey(object);
        }

        @Override
        public V put(K k, V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V get(Object object) {
            return this.function.get(object);
        }

        @Override
        public V remove(Object object) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        public int hashCode() {
            return this.function.hashCode();
        }

        public boolean equals(Object object) {
            return object == this || this.function.equals(object);
        }

        public String toString() {
            return this.function.toString();
        }
    }

    public static class SynchronizedFunction<K, V>
    implements Object2ReferenceFunction<K, V>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Object2ReferenceFunction<K, V> function;
        protected final Object sync;

        protected SynchronizedFunction(Object2ReferenceFunction<K, V> object2ReferenceFunction, Object object) {
            if (object2ReferenceFunction == null) {
                throw new NullPointerException();
            }
            this.function = object2ReferenceFunction;
            this.sync = object;
        }

        protected SynchronizedFunction(Object2ReferenceFunction<K, V> object2ReferenceFunction) {
            if (object2ReferenceFunction == null) {
                throw new NullPointerException();
            }
            this.function = object2ReferenceFunction;
            this.sync = this;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public V apply(K k) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.apply(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int size() {
            Object object = this.sync;
            synchronized (object) {
                return this.function.size();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public V defaultReturnValue() {
            Object object = this.sync;
            synchronized (object) {
                return this.function.defaultReturnValue();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void defaultReturnValue(V v) {
            Object object = this.sync;
            synchronized (object) {
                this.function.defaultReturnValue(v);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsKey(Object object) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.function.containsKey(object);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public V put(K k, V v) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(k, v);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public V get(Object object) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.function.get(object);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public V remove(Object object) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.function.remove(object);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void clear() {
            Object object = this.sync;
            synchronized (object) {
                this.function.clear();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public int hashCode() {
            Object object = this.sync;
            synchronized (object) {
                return this.function.hashCode();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public boolean equals(Object object) {
            if (object == this) {
                return false;
            }
            Object object2 = this.sync;
            synchronized (object2) {
                return this.function.equals(object);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public String toString() {
            Object object = this.sync;
            synchronized (object) {
                return this.function.toString();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            Object object = this.sync;
            synchronized (object) {
                objectOutputStream.defaultWriteObject();
            }
        }
    }

    public static class Singleton<K, V>
    extends AbstractObject2ReferenceFunction<K, V>
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
        public int size() {
            return 0;
        }

        public Object clone() {
            return this;
        }
    }

    public static class EmptyFunction<K, V>
    extends AbstractObject2ReferenceFunction<K, V>
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

