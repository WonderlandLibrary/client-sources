/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.floats.AbstractFloat2ReferenceFunction;
import it.unimi.dsi.fastutil.floats.Float2ReferenceFunction;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.DoubleFunction;

public final class Float2ReferenceFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Float2ReferenceFunctions() {
    }

    public static <V> Float2ReferenceFunction<V> singleton(float f, V v) {
        return new Singleton<V>(f, v);
    }

    public static <V> Float2ReferenceFunction<V> singleton(Float f, V v) {
        return new Singleton<V>(f.floatValue(), v);
    }

    public static <V> Float2ReferenceFunction<V> synchronize(Float2ReferenceFunction<V> float2ReferenceFunction) {
        return new SynchronizedFunction<V>(float2ReferenceFunction);
    }

    public static <V> Float2ReferenceFunction<V> synchronize(Float2ReferenceFunction<V> float2ReferenceFunction, Object object) {
        return new SynchronizedFunction<V>(float2ReferenceFunction, object);
    }

    public static <V> Float2ReferenceFunction<V> unmodifiable(Float2ReferenceFunction<V> float2ReferenceFunction) {
        return new UnmodifiableFunction<V>(float2ReferenceFunction);
    }

    public static <V> Float2ReferenceFunction<V> primitive(java.util.function.Function<? super Float, ? extends V> function) {
        Objects.requireNonNull(function);
        if (function instanceof Float2ReferenceFunction) {
            return (Float2ReferenceFunction)function;
        }
        if (function instanceof DoubleFunction) {
            return ((DoubleFunction)((Object)function))::apply;
        }
        return new PrimitiveFunction<V>(function);
    }

    public static class PrimitiveFunction<V>
    implements Float2ReferenceFunction<V> {
        protected final java.util.function.Function<? super Float, ? extends V> function;

        protected PrimitiveFunction(java.util.function.Function<? super Float, ? extends V> function) {
            this.function = function;
        }

        @Override
        public boolean containsKey(float f) {
            return this.function.apply(Float.valueOf(f)) != null;
        }

        @Override
        @Deprecated
        public boolean containsKey(Object object) {
            if (object == null) {
                return true;
            }
            return this.function.apply((Float)object) != null;
        }

        @Override
        public V get(float f) {
            V v = this.function.apply(Float.valueOf(f));
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
            return this.function.apply((Float)object);
        }

        @Override
        @Deprecated
        public V put(Float f, V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object put(Object object, Object object2) {
            return this.put((Float)object, (V)object2);
        }
    }

    public static class UnmodifiableFunction<V>
    extends AbstractFloat2ReferenceFunction<V>
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Float2ReferenceFunction<V> function;

        protected UnmodifiableFunction(Float2ReferenceFunction<V> float2ReferenceFunction) {
            if (float2ReferenceFunction == null) {
                throw new NullPointerException();
            }
            this.function = float2ReferenceFunction;
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
        public boolean containsKey(float f) {
            return this.function.containsKey(f);
        }

        @Override
        public V put(float f, V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V get(float f) {
            return this.function.get(f);
        }

        @Override
        public V remove(float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public V put(Float f, V v) {
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
            return this.put((Float)object, (V)object2);
        }
    }

    public static class SynchronizedFunction<V>
    implements Float2ReferenceFunction<V>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Float2ReferenceFunction<V> function;
        protected final Object sync;

        protected SynchronizedFunction(Float2ReferenceFunction<V> float2ReferenceFunction, Object object) {
            if (float2ReferenceFunction == null) {
                throw new NullPointerException();
            }
            this.function = float2ReferenceFunction;
            this.sync = object;
        }

        protected SynchronizedFunction(Float2ReferenceFunction<V> float2ReferenceFunction) {
            if (float2ReferenceFunction == null) {
                throw new NullPointerException();
            }
            this.function = float2ReferenceFunction;
            this.sync = this;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
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
        public V apply(Float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.apply(f);
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
        public boolean containsKey(float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.containsKey(f);
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
        public V put(float f, V v) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(f, v);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public V get(float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.get(f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public V remove(float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.remove(f);
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
        public V put(Float f, V v) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(f, v);
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
            return this.put((Float)object, (V)object2);
        }

        @Override
        @Deprecated
        public Object apply(Object object) {
            return this.apply((Float)object);
        }
    }

    public static class Singleton<V>
    extends AbstractFloat2ReferenceFunction<V>
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final float key;
        protected final V value;

        protected Singleton(float f, V v) {
            this.key = f;
            this.value = v;
        }

        @Override
        public boolean containsKey(float f) {
            return Float.floatToIntBits(this.key) == Float.floatToIntBits(f);
        }

        @Override
        public V get(float f) {
            return (V)(Float.floatToIntBits(this.key) == Float.floatToIntBits(f) ? this.value : this.defRetValue);
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
    extends AbstractFloat2ReferenceFunction<V>
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyFunction() {
        }

        @Override
        public V get(float f) {
            return null;
        }

        @Override
        public boolean containsKey(float f) {
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

