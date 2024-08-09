/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.longs.AbstractLong2ByteFunction;
import it.unimi.dsi.fastutil.longs.Long2ByteFunction;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.LongToIntFunction;

public final class Long2ByteFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Long2ByteFunctions() {
    }

    public static Long2ByteFunction singleton(long l, byte by) {
        return new Singleton(l, by);
    }

    public static Long2ByteFunction singleton(Long l, Byte by) {
        return new Singleton(l, by);
    }

    public static Long2ByteFunction synchronize(Long2ByteFunction long2ByteFunction) {
        return new SynchronizedFunction(long2ByteFunction);
    }

    public static Long2ByteFunction synchronize(Long2ByteFunction long2ByteFunction, Object object) {
        return new SynchronizedFunction(long2ByteFunction, object);
    }

    public static Long2ByteFunction unmodifiable(Long2ByteFunction long2ByteFunction) {
        return new UnmodifiableFunction(long2ByteFunction);
    }

    public static Long2ByteFunction primitive(java.util.function.Function<? super Long, ? extends Byte> function) {
        Objects.requireNonNull(function);
        if (function instanceof Long2ByteFunction) {
            return (Long2ByteFunction)function;
        }
        if (function instanceof LongToIntFunction) {
            return arg_0 -> Long2ByteFunctions.lambda$primitive$0(function, arg_0);
        }
        return new PrimitiveFunction(function);
    }

    private static byte lambda$primitive$0(java.util.function.Function function, long l) {
        return SafeMath.safeIntToByte(((LongToIntFunction)((Object)function)).applyAsInt(l));
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class PrimitiveFunction
    implements Long2ByteFunction {
        protected final java.util.function.Function<? super Long, ? extends Byte> function;

        protected PrimitiveFunction(java.util.function.Function<? super Long, ? extends Byte> function) {
            this.function = function;
        }

        @Override
        public boolean containsKey(long l) {
            return this.function.apply((Long)l) != null;
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
        public byte get(long l) {
            Byte by = this.function.apply((Long)l);
            if (by == null) {
                return this.defaultReturnValue();
            }
            return by;
        }

        @Override
        @Deprecated
        public Byte get(Object object) {
            if (object == null) {
                return null;
            }
            return this.function.apply((Long)object);
        }

        @Override
        @Deprecated
        public Byte put(Long l, Byte by) {
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
            return this.put((Long)object, (Byte)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableFunction
    extends AbstractLong2ByteFunction
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Long2ByteFunction function;

        protected UnmodifiableFunction(Long2ByteFunction long2ByteFunction) {
            if (long2ByteFunction == null) {
                throw new NullPointerException();
            }
            this.function = long2ByteFunction;
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
        public boolean containsKey(long l) {
            return this.function.containsKey(l);
        }

        @Override
        public byte put(long l, byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte get(long l) {
            return this.function.get(l);
        }

        @Override
        public byte remove(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Byte put(Long l, Byte by) {
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
            return this.put((Long)object, (Byte)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedFunction
    implements Long2ByteFunction,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Long2ByteFunction function;
        protected final Object sync;

        protected SynchronizedFunction(Long2ByteFunction long2ByteFunction, Object object) {
            if (long2ByteFunction == null) {
                throw new NullPointerException();
            }
            this.function = long2ByteFunction;
            this.sync = object;
        }

        protected SynchronizedFunction(Long2ByteFunction long2ByteFunction) {
            if (long2ByteFunction == null) {
                throw new NullPointerException();
            }
            this.function = long2ByteFunction;
            this.sync = this;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int applyAsInt(long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.applyAsInt(l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Byte apply(Long l) {
            Object object = this.sync;
            synchronized (object) {
                return (Byte)this.function.apply(l);
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
        public byte put(long l, byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(l, by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte get(long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.get(l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte remove(long l) {
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
        public Byte put(Long l, Byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(l, by);
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
            return this.put((Long)object, (Byte)object2);
        }

        @Override
        @Deprecated
        public Object apply(Object object) {
            return this.apply((Long)object);
        }
    }

    public static class Singleton
    extends AbstractLong2ByteFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final long key;
        protected final byte value;

        protected Singleton(long l, byte by) {
            this.key = l;
            this.value = by;
        }

        @Override
        public boolean containsKey(long l) {
            return this.key == l;
        }

        @Override
        public byte get(long l) {
            return this.key == l ? this.value : this.defRetValue;
        }

        @Override
        public int size() {
            return 0;
        }

        public Object clone() {
            return this;
        }
    }

    public static class EmptyFunction
    extends AbstractLong2ByteFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyFunction() {
        }

        @Override
        public byte get(long l) {
            return 1;
        }

        @Override
        public boolean containsKey(long l) {
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

