/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.longs.AbstractLong2ShortFunction;
import it.unimi.dsi.fastutil.longs.Long2ShortFunction;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.LongToIntFunction;

public final class Long2ShortFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Long2ShortFunctions() {
    }

    public static Long2ShortFunction singleton(long l, short s) {
        return new Singleton(l, s);
    }

    public static Long2ShortFunction singleton(Long l, Short s) {
        return new Singleton(l, s);
    }

    public static Long2ShortFunction synchronize(Long2ShortFunction long2ShortFunction) {
        return new SynchronizedFunction(long2ShortFunction);
    }

    public static Long2ShortFunction synchronize(Long2ShortFunction long2ShortFunction, Object object) {
        return new SynchronizedFunction(long2ShortFunction, object);
    }

    public static Long2ShortFunction unmodifiable(Long2ShortFunction long2ShortFunction) {
        return new UnmodifiableFunction(long2ShortFunction);
    }

    public static Long2ShortFunction primitive(java.util.function.Function<? super Long, ? extends Short> function) {
        Objects.requireNonNull(function);
        if (function instanceof Long2ShortFunction) {
            return (Long2ShortFunction)function;
        }
        if (function instanceof LongToIntFunction) {
            return arg_0 -> Long2ShortFunctions.lambda$primitive$0(function, arg_0);
        }
        return new PrimitiveFunction(function);
    }

    private static short lambda$primitive$0(java.util.function.Function function, long l) {
        return SafeMath.safeIntToShort(((LongToIntFunction)((Object)function)).applyAsInt(l));
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class PrimitiveFunction
    implements Long2ShortFunction {
        protected final java.util.function.Function<? super Long, ? extends Short> function;

        protected PrimitiveFunction(java.util.function.Function<? super Long, ? extends Short> function) {
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
        public short get(long l) {
            Short s = this.function.apply((Long)l);
            if (s == null) {
                return this.defaultReturnValue();
            }
            return s;
        }

        @Override
        @Deprecated
        public Short get(Object object) {
            if (object == null) {
                return null;
            }
            return this.function.apply((Long)object);
        }

        @Override
        @Deprecated
        public Short put(Long l, Short s) {
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
            return this.put((Long)object, (Short)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableFunction
    extends AbstractLong2ShortFunction
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Long2ShortFunction function;

        protected UnmodifiableFunction(Long2ShortFunction long2ShortFunction) {
            if (long2ShortFunction == null) {
                throw new NullPointerException();
            }
            this.function = long2ShortFunction;
        }

        @Override
        public int size() {
            return this.function.size();
        }

        @Override
        public short defaultReturnValue() {
            return this.function.defaultReturnValue();
        }

        @Override
        public void defaultReturnValue(short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean containsKey(long l) {
            return this.function.containsKey(l);
        }

        @Override
        public short put(long l, short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public short get(long l) {
            return this.function.get(l);
        }

        @Override
        public short remove(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Short put(Long l, Short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Short get(Object object) {
            return this.function.get(object);
        }

        @Override
        @Deprecated
        public Short remove(Object object) {
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
            return this.put((Long)object, (Short)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedFunction
    implements Long2ShortFunction,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Long2ShortFunction function;
        protected final Object sync;

        protected SynchronizedFunction(Long2ShortFunction long2ShortFunction, Object object) {
            if (long2ShortFunction == null) {
                throw new NullPointerException();
            }
            this.function = long2ShortFunction;
            this.sync = object;
        }

        protected SynchronizedFunction(Long2ShortFunction long2ShortFunction) {
            if (long2ShortFunction == null) {
                throw new NullPointerException();
            }
            this.function = long2ShortFunction;
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
        public Short apply(Long l) {
            Object object = this.sync;
            synchronized (object) {
                return (Short)this.function.apply(l);
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
        public short defaultReturnValue() {
            Object object = this.sync;
            synchronized (object) {
                return this.function.defaultReturnValue();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void defaultReturnValue(short s) {
            Object object = this.sync;
            synchronized (object) {
                this.function.defaultReturnValue(s);
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
        public short put(long l, short s) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(l, s);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short get(long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.get(l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short remove(long l) {
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
        public Short put(Long l, Short s) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(l, s);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Short get(Object object) {
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
        public Short remove(Object object) {
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
            return this.put((Long)object, (Short)object2);
        }

        @Override
        @Deprecated
        public Object apply(Object object) {
            return this.apply((Long)object);
        }
    }

    public static class Singleton
    extends AbstractLong2ShortFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final long key;
        protected final short value;

        protected Singleton(long l, short s) {
            this.key = l;
            this.value = s;
        }

        @Override
        public boolean containsKey(long l) {
            return this.key == l;
        }

        @Override
        public short get(long l) {
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
    extends AbstractLong2ShortFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyFunction() {
        }

        @Override
        public short get(long l) {
            return 1;
        }

        @Override
        public boolean containsKey(long l) {
            return true;
        }

        @Override
        public short defaultReturnValue() {
            return 1;
        }

        @Override
        public void defaultReturnValue(short s) {
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

