/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.AbstractDouble2ReferenceFunction;
import it.unimi.dsi.fastutil.doubles.Double2ReferenceFunction;
import java.io.Serializable;

public class Double2ReferenceFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Double2ReferenceFunctions() {
    }

    public static <V> Double2ReferenceFunction<V> singleton(double key, V value) {
        return new Singleton<V>(key, value);
    }

    public static <V> Double2ReferenceFunction<V> singleton(Double key, V value) {
        return new Singleton<V>(key, value);
    }

    public static <V> Double2ReferenceFunction<V> synchronize(Double2ReferenceFunction<V> f) {
        return new SynchronizedFunction<V>(f);
    }

    public static <V> Double2ReferenceFunction<V> synchronize(Double2ReferenceFunction<V> f, Object sync) {
        return new SynchronizedFunction<V>(f, sync);
    }

    public static <V> Double2ReferenceFunction<V> unmodifiable(Double2ReferenceFunction<V> f) {
        return new UnmodifiableFunction<V>(f);
    }

    public static class UnmodifiableFunction<V>
    extends AbstractDouble2ReferenceFunction<V>
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Double2ReferenceFunction<V> function;

        protected UnmodifiableFunction(Double2ReferenceFunction<V> f) {
            if (f == null) {
                throw new NullPointerException();
            }
            this.function = f;
        }

        @Override
        public int size() {
            return this.function.size();
        }

        @Override
        public boolean containsKey(double k) {
            return this.function.containsKey(k);
        }

        @Override
        public V defaultReturnValue() {
            return this.function.defaultReturnValue();
        }

        @Override
        public void defaultReturnValue(V defRetValue) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V put(double k, V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        public String toString() {
            return this.function.toString();
        }

        @Override
        public V remove(double k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V get(double k) {
            return this.function.get(k);
        }

        @Override
        public boolean containsKey(Object ok) {
            return this.function.containsKey(ok);
        }

        @Override
        public V remove(Object k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V get(Object k) {
            return this.function.get(k);
        }
    }

    public static class SynchronizedFunction<V>
    extends AbstractDouble2ReferenceFunction<V>
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Double2ReferenceFunction<V> function;
        protected final Object sync;

        protected SynchronizedFunction(Double2ReferenceFunction<V> f, Object sync) {
            if (f == null) {
                throw new NullPointerException();
            }
            this.function = f;
            this.sync = sync;
        }

        protected SynchronizedFunction(Double2ReferenceFunction<V> f) {
            if (f == null) {
                throw new NullPointerException();
            }
            this.function = f;
            this.sync = this;
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
        public boolean containsKey(double k) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.containsKey(k);
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
        public void defaultReturnValue(V defRetValue) {
            Object object = this.sync;
            synchronized (object) {
                this.function.defaultReturnValue(defRetValue);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public V put(double k, V v) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(k, v);
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
        public String toString() {
            Object object = this.sync;
            synchronized (object) {
                return this.function.toString();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public V put(Double k, V v) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(k, v);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public V get(Object k) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.get(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public V remove(Object k) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.remove(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public V remove(double k) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.remove(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public V get(double k) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.get(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsKey(Object ok) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.containsKey(ok);
            }
        }
    }

    public static class Singleton<V>
    extends AbstractDouble2ReferenceFunction<V>
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final double key;
        protected final V value;

        protected Singleton(double key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public boolean containsKey(double k) {
            return Double.doubleToLongBits(this.key) == Double.doubleToLongBits(k);
        }

        @Override
        public V get(double k) {
            if (Double.doubleToLongBits(this.key) == Double.doubleToLongBits(k)) {
                return this.value;
            }
            return (V)this.defRetValue;
        }

        @Override
        public int size() {
            return 1;
        }

        public Object clone() {
            return this;
        }
    }

    public static class EmptyFunction<V>
    extends AbstractDouble2ReferenceFunction<V>
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyFunction() {
        }

        @Override
        public V get(double k) {
            return null;
        }

        @Override
        public boolean containsKey(double k) {
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
        public V get(Object k) {
            return null;
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public void clear() {
        }

        private Object readResolve() {
            return EMPTY_FUNCTION;
        }

        public Object clone() {
            return EMPTY_FUNCTION;
        }
    }
}

