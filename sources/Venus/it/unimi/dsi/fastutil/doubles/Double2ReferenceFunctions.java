/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.doubles.AbstractDouble2ReferenceFunction;
import it.unimi.dsi.fastutil.doubles.Double2ReferenceFunction;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.DoubleFunction;

public final class Double2ReferenceFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Double2ReferenceFunctions() {
    }

    public static <V> Double2ReferenceFunction<V> singleton(double d, V v) {
        return new Singleton<V>(d, v);
    }

    public static <V> Double2ReferenceFunction<V> singleton(Double d, V v) {
        return new Singleton<V>(d, v);
    }

    public static <V> Double2ReferenceFunction<V> synchronize(Double2ReferenceFunction<V> double2ReferenceFunction) {
        return new SynchronizedFunction<V>(double2ReferenceFunction);
    }

    public static <V> Double2ReferenceFunction<V> synchronize(Double2ReferenceFunction<V> double2ReferenceFunction, Object object) {
        return new SynchronizedFunction<V>(double2ReferenceFunction, object);
    }

    public static <V> Double2ReferenceFunction<V> unmodifiable(Double2ReferenceFunction<V> double2ReferenceFunction) {
        return new UnmodifiableFunction<V>(double2ReferenceFunction);
    }

    public static <V> Double2ReferenceFunction<V> primitive(java.util.function.Function<? super Double, ? extends V> function) {
        Objects.requireNonNull(function);
        if (function instanceof Double2ReferenceFunction) {
            return (Double2ReferenceFunction)function;
        }
        if (function instanceof DoubleFunction) {
            return ((DoubleFunction)((Object)function))::apply;
        }
        return new PrimitiveFunction<V>(function);
    }

    public static class PrimitiveFunction<V>
    implements Double2ReferenceFunction<V> {
        protected final java.util.function.Function<? super Double, ? extends V> function;

        protected PrimitiveFunction(java.util.function.Function<? super Double, ? extends V> function) {
            this.function = function;
        }

        @Override
        public boolean containsKey(double d) {
            return this.function.apply(d) != null;
        }

        @Override
        @Deprecated
        public boolean containsKey(Object object) {
            if (object == null) {
                return true;
            }
            return this.function.apply((Double)object) != null;
        }

        @Override
        public V get(double d) {
            V v = this.function.apply(d);
            if (v == null) {
                return null;
            }
            return v;
        }

        @Override
        @Deprecated
        public V get(Object object) {
            if (object == null) {
                return null;
            }
            return this.function.apply((Double)object);
        }

        @Override
        @Deprecated
        public V put(Double d, V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object put(Object object, Object object2) {
            return this.put((Double)object, (V)object2);
        }
    }

    public static class UnmodifiableFunction<V>
    extends AbstractDouble2ReferenceFunction<V>
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Double2ReferenceFunction<V> function;

        protected UnmodifiableFunction(Double2ReferenceFunction<V> double2ReferenceFunction) {
            if (double2ReferenceFunction == null) {
                throw new NullPointerException();
            }
            this.function = double2ReferenceFunction;
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
        public boolean containsKey(double d) {
            return this.function.containsKey(d);
        }

        @Override
        public V put(double d, V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V get(double d) {
            return this.function.get(d);
        }

        @Override
        public V remove(double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public V put(Double d, V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public V get(Object object) {
            return this.function.get(object);
        }

        @Override
        @Deprecated
        public V remove(Object object) {
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

        @Override
        @Deprecated
        public Object put(Object object, Object object2) {
            return this.put((Double)object, (V)object2);
        }
    }

    public static class SynchronizedFunction<V>
    implements Double2ReferenceFunction<V>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Double2ReferenceFunction<V> function;
        protected final Object sync;

        protected SynchronizedFunction(Double2ReferenceFunction<V> double2ReferenceFunction, Object object) {
            if (double2ReferenceFunction == null) {
                throw new NullPointerException();
            }
            this.function = double2ReferenceFunction;
            this.sync = object;
        }

        protected SynchronizedFunction(Double2ReferenceFunction<V> double2ReferenceFunction) {
            if (double2ReferenceFunction == null) {
                throw new NullPointerException();
            }
            this.function = double2ReferenceFunction;
            this.sync = this;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public V apply(double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.apply(d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public V apply(Double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.apply(d);
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
        public boolean containsKey(double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.containsKey(d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
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
        public V put(double d, V v) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(d, v);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public V get(double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.get(d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public V remove(double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.remove(d);
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
        @Override
        @Deprecated
        public V put(Double d, V v) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(d, v);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
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
        @Deprecated
        public V remove(Object object) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.function.remove(object);
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

        @Override
        @Deprecated
        public Object put(Object object, Object object2) {
            return this.put((Double)object, (V)object2);
        }

        @Override
        @Deprecated
        public Object apply(Object object) {
            return this.apply((Double)object);
        }
    }

    public static class Singleton<V>
    extends AbstractDouble2ReferenceFunction<V>
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final double key;
        protected final V value;

        protected Singleton(double d, V v) {
            this.key = d;
            this.value = v;
        }

        @Override
        public boolean containsKey(double d) {
            return Double.doubleToLongBits(this.key) == Double.doubleToLongBits(d);
        }

        @Override
        public V get(double d) {
            return (V)(Double.doubleToLongBits(this.key) == Double.doubleToLongBits(d) ? this.value : this.defRetValue);
        }

        @Override
        public int size() {
            return 0;
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
        public V get(double d) {
            return null;
        }

        @Override
        public boolean containsKey(double d) {
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

