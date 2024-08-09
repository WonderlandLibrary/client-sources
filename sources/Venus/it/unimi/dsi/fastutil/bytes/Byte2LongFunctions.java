/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.bytes.AbstractByte2LongFunction;
import it.unimi.dsi.fastutil.bytes.Byte2LongFunction;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.IntToLongFunction;

public final class Byte2LongFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Byte2LongFunctions() {
    }

    public static Byte2LongFunction singleton(byte by, long l) {
        return new Singleton(by, l);
    }

    public static Byte2LongFunction singleton(Byte by, Long l) {
        return new Singleton(by, l);
    }

    public static Byte2LongFunction synchronize(Byte2LongFunction byte2LongFunction) {
        return new SynchronizedFunction(byte2LongFunction);
    }

    public static Byte2LongFunction synchronize(Byte2LongFunction byte2LongFunction, Object object) {
        return new SynchronizedFunction(byte2LongFunction, object);
    }

    public static Byte2LongFunction unmodifiable(Byte2LongFunction byte2LongFunction) {
        return new UnmodifiableFunction(byte2LongFunction);
    }

    public static Byte2LongFunction primitive(java.util.function.Function<? super Byte, ? extends Long> function) {
        Objects.requireNonNull(function);
        if (function instanceof Byte2LongFunction) {
            return (Byte2LongFunction)function;
        }
        if (function instanceof IntToLongFunction) {
            return ((IntToLongFunction)((Object)function))::applyAsLong;
        }
        return new PrimitiveFunction(function);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class PrimitiveFunction
    implements Byte2LongFunction {
        protected final java.util.function.Function<? super Byte, ? extends Long> function;

        protected PrimitiveFunction(java.util.function.Function<? super Byte, ? extends Long> function) {
            this.function = function;
        }

        @Override
        public boolean containsKey(byte by) {
            return this.function.apply((Byte)by) != null;
        }

        @Override
        @Deprecated
        public boolean containsKey(Object object) {
            if (object == null) {
                return true;
            }
            return this.function.apply((Byte)object) != null;
        }

        @Override
        public long get(byte by) {
            Long l = this.function.apply((Byte)by);
            if (l == null) {
                return this.defaultReturnValue();
            }
            return l;
        }

        @Override
        @Deprecated
        public Long get(Object object) {
            if (object == null) {
                return null;
            }
            return this.function.apply((Byte)object);
        }

        @Override
        @Deprecated
        public Long put(Byte by, Long l) {
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
            return this.put((Byte)object, (Long)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableFunction
    extends AbstractByte2LongFunction
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Byte2LongFunction function;

        protected UnmodifiableFunction(Byte2LongFunction byte2LongFunction) {
            if (byte2LongFunction == null) {
                throw new NullPointerException();
            }
            this.function = byte2LongFunction;
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
        public boolean containsKey(byte by) {
            return this.function.containsKey(by);
        }

        @Override
        public long put(byte by, long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long get(byte by) {
            return this.function.get(by);
        }

        @Override
        public long remove(byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Long put(Byte by, Long l) {
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
            return this.put((Byte)object, (Long)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedFunction
    implements Byte2LongFunction,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Byte2LongFunction function;
        protected final Object sync;

        protected SynchronizedFunction(Byte2LongFunction byte2LongFunction, Object object) {
            if (byte2LongFunction == null) {
                throw new NullPointerException();
            }
            this.function = byte2LongFunction;
            this.sync = object;
        }

        protected SynchronizedFunction(Byte2LongFunction byte2LongFunction) {
            if (byte2LongFunction == null) {
                throw new NullPointerException();
            }
            this.function = byte2LongFunction;
            this.sync = this;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public long applyAsLong(int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.applyAsLong(n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Long apply(Byte by) {
            Object object = this.sync;
            synchronized (object) {
                return (Long)this.function.apply(by);
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
        public boolean containsKey(byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.containsKey(by);
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
        public long put(byte by, long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(by, l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long get(byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.get(by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long remove(byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.remove(by);
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
        public Long put(Byte by, Long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(by, l);
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
            return this.put((Byte)object, (Long)object2);
        }

        @Override
        @Deprecated
        public Object apply(Object object) {
            return this.apply((Byte)object);
        }
    }

    public static class Singleton
    extends AbstractByte2LongFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final byte key;
        protected final long value;

        protected Singleton(byte by, long l) {
            this.key = by;
            this.value = l;
        }

        @Override
        public boolean containsKey(byte by) {
            return this.key == by;
        }

        @Override
        public long get(byte by) {
            return this.key == by ? this.value : this.defRetValue;
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
    extends AbstractByte2LongFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyFunction() {
        }

        @Override
        public long get(byte by) {
            return 0L;
        }

        @Override
        public boolean containsKey(byte by) {
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

