/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.longs.AbstractLong2ObjectFunction;
import it.unimi.dsi.fastutil.longs.Long2ObjectFunction;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.LongFunction;

public final class Long2ObjectFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Long2ObjectFunctions() {
    }

    public static <V> Long2ObjectFunction<V> singleton(long l, V v) {
        return new Singleton<V>(l, v);
    }

    public static <V> Long2ObjectFunction<V> singleton(Long l, V v) {
        return new Singleton<V>(l, v);
    }

    public static <V> Long2ObjectFunction<V> synchronize(Long2ObjectFunction<V> long2ObjectFunction) {
        return new SynchronizedFunction<V>(long2ObjectFunction);
    }

    public static <V> Long2ObjectFunction<V> synchronize(Long2ObjectFunction<V> long2ObjectFunction, Object object) {
        return new SynchronizedFunction<V>(long2ObjectFunction, object);
    }

    public static <V> Long2ObjectFunction<V> unmodifiable(Long2ObjectFunction<V> long2ObjectFunction) {
        return new UnmodifiableFunction<V>(long2ObjectFunction);
    }

    public static <V> Long2ObjectFunction<V> primitive(java.util.function.Function<? super Long, ? extends V> function) {
        Objects.requireNonNull(function);
        if (function instanceof Long2ObjectFunction) {
            return (Long2ObjectFunction)function;
        }
        if (function instanceof LongFunction) {
            return ((LongFunction)((Object)function))::apply;
        }
        return new PrimitiveFunction<V>(function);
    }

    public static class PrimitiveFunction<V>
    implements Long2ObjectFunction<V> {
        protected final java.util.function.Function<? super Long, ? extends V> function;

        protected PrimitiveFunction(java.util.function.Function<? super Long, ? extends V> function) {
            this.function = function;
        }

        @Override
        public boolean containsKey(long l) {
            return this.function.apply(l) != null;
        }

        @Override
        @Deprecated
        public boolean containsKey(Object object) {
            if (object == null) {
                return true;
            }
            return this.function.apply((Long)object) != null;
        }

        @Override
        public V get(long l) {
            V v = this.function.apply(l);
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
            return this.function.apply((Long)object);
        }

        @Override
        @Deprecated
        public V put(Long l, V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object put(Object object, Object object2) {
            return this.put((Long)object, (V)object2);
        }
    }

    public static class UnmodifiableFunction<V>
    extends AbstractLong2ObjectFunction<V>
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Long2ObjectFunction<V> function;

        protected UnmodifiableFunction(Long2ObjectFunction<V> long2ObjectFunction) {
            if (long2ObjectFunction == null) {
                throw new NullPointerException();
            }
            this.function = long2ObjectFunction;
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
        public boolean containsKey(long l) {
            return this.function.containsKey(l);
        }

        @Override
        public V put(long l, V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V get(long l) {
            return this.function.get(l);
        }

        @Override
        public V remove(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public V put(Long l, V v) {
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
            return this.put((Long)object, (V)object2);
        }
    }

    public static class SynchronizedFunction<V>
    implements Long2ObjectFunction<V>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Long2ObjectFunction<V> function;
        protected final Object sync;

        protected SynchronizedFunction(Long2ObjectFunction<V> long2ObjectFunction, Object object) {
            if (long2ObjectFunction == null) {
                throw new NullPointerException();
            }
            this.function = long2ObjectFunction;
            this.sync = object;
        }

        protected SynchronizedFunction(Long2ObjectFunction<V> long2ObjectFunction) {
            if (long2ObjectFunction == null) {
                throw new NullPointerException();
            }
            this.function = long2ObjectFunction;
            this.sync = this;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public V apply(long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.apply(l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public V apply(Long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.apply(l);
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
        public boolean containsKey(long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.containsKey(l);
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
        public V put(long l, V v) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(l, v);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public V get(long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.get(l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public V remove(long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.remove(l);
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
        public V put(Long l, V v) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(l, v);
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
            return this.put((Long)object, (V)object2);
        }

        @Override
        @Deprecated
        public Object apply(Object object) {
            return this.apply((Long)object);
        }
    }

    public static class Singleton<V>
    extends AbstractLong2ObjectFunction<V>
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final long key;
        protected final V value;

        protected Singleton(long l, V v) {
            this.key = l;
            this.value = v;
        }

        @Override
        public boolean containsKey(long l) {
            return this.key == l;
        }

        @Override
        public V get(long l) {
            return (V)(this.key == l ? this.value : this.defRetValue);
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
    extends AbstractLong2ObjectFunction<V>
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyFunction() {
        }

        @Override
        public V get(long l) {
            return null;
        }

        @Override
        public boolean containsKey(long l) {
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

