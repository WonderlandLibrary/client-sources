/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.objects.AbstractReference2ByteFunction;
import it.unimi.dsi.fastutil.objects.Reference2ByteFunction;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.ToIntFunction;

public final class Reference2ByteFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Reference2ByteFunctions() {
    }

    public static <K> Reference2ByteFunction<K> singleton(K k, byte by) {
        return new Singleton<K>(k, by);
    }

    public static <K> Reference2ByteFunction<K> singleton(K k, Byte by) {
        return new Singleton<K>(k, by);
    }

    public static <K> Reference2ByteFunction<K> synchronize(Reference2ByteFunction<K> reference2ByteFunction) {
        return new SynchronizedFunction<K>(reference2ByteFunction);
    }

    public static <K> Reference2ByteFunction<K> synchronize(Reference2ByteFunction<K> reference2ByteFunction, Object object) {
        return new SynchronizedFunction<K>(reference2ByteFunction, object);
    }

    public static <K> Reference2ByteFunction<K> unmodifiable(Reference2ByteFunction<K> reference2ByteFunction) {
        return new UnmodifiableFunction<K>(reference2ByteFunction);
    }

    public static <K> Reference2ByteFunction<K> primitive(java.util.function.Function<? super K, ? extends Byte> function) {
        Objects.requireNonNull(function);
        if (function instanceof Reference2ByteFunction) {
            return (Reference2ByteFunction)function;
        }
        if (function instanceof ToIntFunction) {
            return arg_0 -> Reference2ByteFunctions.lambda$primitive$0(function, arg_0);
        }
        return new PrimitiveFunction<K>(function);
    }

    private static byte lambda$primitive$0(java.util.function.Function function, Object object) {
        return SafeMath.safeIntToByte(((ToIntFunction)((Object)function)).applyAsInt(object));
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class PrimitiveFunction<K>
    implements Reference2ByteFunction<K> {
        protected final java.util.function.Function<? super K, ? extends Byte> function;

        protected PrimitiveFunction(java.util.function.Function<? super K, ? extends Byte> function) {
            this.function = function;
        }

        @Override
        public boolean containsKey(Object object) {
            return this.function.apply(object) != null;
        }

        @Override
        public byte getByte(Object object) {
            Byte by = this.function.apply(object);
            if (by == null) {
                return this.defaultReturnValue();
            }
            return by;
        }

        @Override
        @Deprecated
        public Byte get(Object object) {
            return this.function.apply(object);
        }

        @Override
        @Deprecated
        public Byte put(K k, Byte by) {
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
            return this.put((K)object, (Byte)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableFunction<K>
    extends AbstractReference2ByteFunction<K>
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Reference2ByteFunction<K> function;

        protected UnmodifiableFunction(Reference2ByteFunction<K> reference2ByteFunction) {
            if (reference2ByteFunction == null) {
                throw new NullPointerException();
            }
            this.function = reference2ByteFunction;
        }

        @Override
        public int size() {
            return this.function.size();
        }

        @Override
        public byte defaultReturnValue() {
            return this.function.defaultReturnValue();
        }

        @Override
        public void defaultReturnValue(byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean containsKey(Object object) {
            return this.function.containsKey(object);
        }

        @Override
        public byte put(K k, byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte getByte(Object object) {
            return this.function.getByte(object);
        }

        @Override
        public byte removeByte(Object object) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Byte put(K k, Byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Byte get(Object object) {
            return this.function.get(object);
        }

        @Override
        @Deprecated
        public Byte remove(Object object) {
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
            return this.put((K)object, (Byte)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedFunction<K>
    implements Reference2ByteFunction<K>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Reference2ByteFunction<K> function;
        protected final Object sync;

        protected SynchronizedFunction(Reference2ByteFunction<K> reference2ByteFunction, Object object) {
            if (reference2ByteFunction == null) {
                throw new NullPointerException();
            }
            this.function = reference2ByteFunction;
            this.sync = object;
        }

        protected SynchronizedFunction(Reference2ByteFunction<K> reference2ByteFunction) {
            if (reference2ByteFunction == null) {
                throw new NullPointerException();
            }
            this.function = reference2ByteFunction;
            this.sync = this;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int applyAsInt(K k) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.applyAsInt(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Byte apply(K k) {
            Object object = this.sync;
            synchronized (object) {
                return (Byte)this.function.apply(k);
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
        public void defaultReturnValue(byte by) {
            Object object = this.sync;
            synchronized (object) {
                this.function.defaultReturnValue(by);
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
        public byte put(K k, byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(k, by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte getByte(Object object) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.function.getByte(object);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte removeByte(Object object) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.function.removeByte(object);
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
        public Byte put(K k, Byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(k, by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Byte get(Object object) {
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
        public Byte remove(Object object) {
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
            return this.put((K)object, (Byte)object2);
        }

        @Override
        @Deprecated
        public Object apply(Object object) {
            return this.apply(object);
        }
    }

    public static class Singleton<K>
    extends AbstractReference2ByteFunction<K>
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final K key;
        protected final byte value;

        protected Singleton(K k, byte by) {
            this.key = k;
            this.value = by;
        }

        @Override
        public boolean containsKey(Object object) {
            return this.key == object;
        }

        @Override
        public byte getByte(Object object) {
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
    extends AbstractReference2ByteFunction<K>
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyFunction() {
        }

        @Override
        public byte getByte(Object object) {
            return 1;
        }

        @Override
        public boolean containsKey(Object object) {
            return true;
        }

        @Override
        public byte defaultReturnValue() {
            return 1;
        }

        @Override
        public void defaultReturnValue(byte by) {
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

