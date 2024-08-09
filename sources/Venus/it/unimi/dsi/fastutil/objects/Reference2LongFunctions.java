/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.objects.AbstractReference2LongFunction;
import it.unimi.dsi.fastutil.objects.Reference2LongFunction;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.ToLongFunction;

public final class Reference2LongFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Reference2LongFunctions() {
    }

    public static <K> Reference2LongFunction<K> singleton(K k, long l) {
        return new Singleton<K>(k, l);
    }

    public static <K> Reference2LongFunction<K> singleton(K k, Long l) {
        return new Singleton<K>(k, l);
    }

    public static <K> Reference2LongFunction<K> synchronize(Reference2LongFunction<K> reference2LongFunction) {
        return new SynchronizedFunction<K>(reference2LongFunction);
    }

    public static <K> Reference2LongFunction<K> synchronize(Reference2LongFunction<K> reference2LongFunction, Object object) {
        return new SynchronizedFunction<K>(reference2LongFunction, object);
    }

    public static <K> Reference2LongFunction<K> unmodifiable(Reference2LongFunction<K> reference2LongFunction) {
        return new UnmodifiableFunction<K>(reference2LongFunction);
    }

    public static <K> Reference2LongFunction<K> primitive(java.util.function.Function<? super K, ? extends Long> function) {
        Objects.requireNonNull(function);
        if (function instanceof Reference2LongFunction) {
            return (Reference2LongFunction)function;
        }
        if (function instanceof ToLongFunction) {
            return arg_0 -> Reference2LongFunctions.lambda$primitive$0(function, arg_0);
        }
        return new PrimitiveFunction<K>(function);
    }

    private static long lambda$primitive$0(java.util.function.Function function, Object object) {
        return ((ToLongFunction)((Object)function)).applyAsLong(object);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class PrimitiveFunction<K>
    implements Reference2LongFunction<K> {
        protected final java.util.function.Function<? super K, ? extends Long> function;

        protected PrimitiveFunction(java.util.function.Function<? super K, ? extends Long> function) {
            this.function = function;
        }

        @Override
        public boolean containsKey(Object object) {
            return this.function.apply(object) != null;
        }

        @Override
        public long getLong(Object object) {
            Long l = this.function.apply(object);
            if (l == null) {
                return this.defaultReturnValue();
            }
            return l;
        }

        @Override
        @Deprecated
        public Long get(Object object) {
            return this.function.apply(object);
        }

        @Override
        @Deprecated
        public Long put(K k, Long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object get(Object object) {
            return this.get(object);
        }

        @Override
        @Deprecated
        public Object put(Object object, Object object2) {
            return this.put((K)object, (Long)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableFunction<K>
    extends AbstractReference2LongFunction<K>
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Reference2LongFunction<K> function;

        protected UnmodifiableFunction(Reference2LongFunction<K> reference2LongFunction) {
            if (reference2LongFunction == null) {
                throw new NullPointerException();
            }
            this.function = reference2LongFunction;
        }

        @Override
        public int size() {
            return this.function.size();
        }

        @Override
        public long defaultReturnValue() {
            return this.function.defaultReturnValue();
        }

        @Override
        public void defaultReturnValue(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean containsKey(Object object) {
            return this.function.containsKey(object);
        }

        @Override
        public long put(K k, long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long getLong(Object object) {
            return this.function.getLong(object);
        }

        @Override
        public long removeLong(Object object) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Long put(K k, Long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Long get(Object object) {
            return this.function.get(object);
        }

        @Override
        @Deprecated
        public Long remove(Object object) {
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
        public Object remove(Object object) {
            return this.remove(object);
        }

        @Override
        @Deprecated
        public Object get(Object object) {
            return this.get(object);
        }

        @Override
        @Deprecated
        public Object put(Object object, Object object2) {
            return this.put((K)object, (Long)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedFunction<K>
    implements Reference2LongFunction<K>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Reference2LongFunction<K> function;
        protected final Object sync;

        protected SynchronizedFunction(Reference2LongFunction<K> reference2LongFunction, Object object) {
            if (reference2LongFunction == null) {
                throw new NullPointerException();
            }
            this.function = reference2LongFunction;
            this.sync = object;
        }

        protected SynchronizedFunction(Reference2LongFunction<K> reference2LongFunction) {
            if (reference2LongFunction == null) {
                throw new NullPointerException();
            }
            this.function = reference2LongFunction;
            this.sync = this;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long applyAsLong(K k) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.applyAsLong(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Long apply(K k) {
            Object object = this.sync;
            synchronized (object) {
                return (Long)this.function.apply(k);
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
        public long defaultReturnValue() {
            Object object = this.sync;
            synchronized (object) {
                return this.function.defaultReturnValue();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void defaultReturnValue(long l) {
            Object object = this.sync;
            synchronized (object) {
                this.function.defaultReturnValue(l);
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
        public long put(K k, long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(k, l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long getLong(Object object) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.function.getLong(object);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long removeLong(Object object) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.function.removeLong(object);
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
        public Long put(K k, Long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(k, l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Long get(Object object) {
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
        public Long remove(Object object) {
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
        public Object remove(Object object) {
            return this.remove(object);
        }

        @Override
        @Deprecated
        public Object get(Object object) {
            return this.get(object);
        }

        @Override
        @Deprecated
        public Object put(Object object, Object object2) {
            return this.put((K)object, (Long)object2);
        }

        @Override
        @Deprecated
        public Object apply(Object object) {
            return this.apply(object);
        }
    }

    public static class Singleton<K>
    extends AbstractReference2LongFunction<K>
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final K key;
        protected final long value;

        protected Singleton(K k, long l) {
            this.key = k;
            this.value = l;
        }

        @Override
        public boolean containsKey(Object object) {
            return this.key == object;
        }

        @Override
        public long getLong(Object object) {
            return this.key == object ? this.value : this.defRetValue;
        }

        @Override
        public int size() {
            return 0;
        }

        public Object clone() {
            return this;
        }
    }

    public static class EmptyFunction<K>
    extends AbstractReference2LongFunction<K>
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyFunction() {
        }

        @Override
        public long getLong(Object object) {
            return 0L;
        }

        @Override
        public boolean containsKey(Object object) {
            return true;
        }

        @Override
        public long defaultReturnValue() {
            return 0L;
        }

        @Override
        public void defaultReturnValue(long l) {
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

