/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.AbstractObject2ByteFunction;
import it.unimi.dsi.fastutil.objects.Object2ByteFunction;
import java.io.Serializable;

public class Object2ByteFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Object2ByteFunctions() {
    }

    public static <K> Object2ByteFunction<K> singleton(K key, byte value) {
        return new Singleton<K>(key, value);
    }

    public static <K> Object2ByteFunction<K> singleton(K key, Byte value) {
        return new Singleton<K>(key, value);
    }

    public static <K> Object2ByteFunction<K> synchronize(Object2ByteFunction<K> f) {
        return new SynchronizedFunction<K>(f);
    }

    public static <K> Object2ByteFunction<K> synchronize(Object2ByteFunction<K> f, Object sync) {
        return new SynchronizedFunction<K>(f, sync);
    }

    public static <K> Object2ByteFunction<K> unmodifiable(Object2ByteFunction<K> f) {
        return new UnmodifiableFunction<K>(f);
    }

    public static class UnmodifiableFunction<K>
    extends AbstractObject2ByteFunction<K>
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Object2ByteFunction<K> function;

        protected UnmodifiableFunction(Object2ByteFunction<K> f) {
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
        public boolean containsKey(Object k) {
            return this.function.containsKey(k);
        }

        @Override
        public byte defaultReturnValue() {
            return this.function.defaultReturnValue();
        }

        @Override
        public void defaultReturnValue(byte defRetValue) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte put(K k, byte v) {
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
        public byte removeByte(Object k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte getByte(Object k) {
            return this.function.getByte(k);
        }
    }

    public static class SynchronizedFunction<K>
    extends AbstractObject2ByteFunction<K>
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Object2ByteFunction<K> function;
        protected final Object sync;

        protected SynchronizedFunction(Object2ByteFunction<K> f, Object sync) {
            if (f == null) {
                throw new NullPointerException();
            }
            this.function = f;
            this.sync = sync;
        }

        protected SynchronizedFunction(Object2ByteFunction<K> f) {
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
        public boolean containsKey(Object k) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.containsKey(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte defaultReturnValue() {
            Object object = this.sync;
            synchronized (object) {
                return this.function.defaultReturnValue();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void defaultReturnValue(byte defRetValue) {
            Object object = this.sync;
            synchronized (object) {
                this.function.defaultReturnValue(defRetValue);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte put(K k, byte v) {
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
        public Byte put(K k, Byte v) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(k, v);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Byte get(Object k) {
            Object object = this.sync;
            synchronized (object) {
                return (Byte)this.function.get(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Byte remove(Object k) {
            Object object = this.sync;
            synchronized (object) {
                return (Byte)this.function.remove(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte removeByte(Object k) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.removeByte(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte getByte(Object k) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.getByte(k);
            }
        }
    }

    public static class Singleton<K>
    extends AbstractObject2ByteFunction<K>
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final K key;
        protected final byte value;

        protected Singleton(K key, byte value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public boolean containsKey(Object k) {
            return this.key == null ? k == null : this.key.equals(k);
        }

        @Override
        public byte getByte(Object k) {
            if (this.key == null ? k == null : this.key.equals(k)) {
                return this.value;
            }
            return this.defRetValue;
        }

        @Override
        public int size() {
            return 1;
        }

        public Object clone() {
            return this;
        }
    }

    public static class EmptyFunction<K>
    extends AbstractObject2ByteFunction<K>
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyFunction() {
        }

        @Override
        public byte getByte(Object k) {
            return 0;
        }

        @Override
        public boolean containsKey(Object k) {
            return false;
        }

        @Override
        public byte defaultReturnValue() {
            return 0;
        }

        @Override
        public void defaultReturnValue(byte defRetValue) {
            throw new UnsupportedOperationException();
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

