/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.shorts.AbstractShort2LongFunction;
import it.unimi.dsi.fastutil.shorts.Short2LongFunction;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.IntToLongFunction;

public final class Short2LongFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Short2LongFunctions() {
    }

    public static Short2LongFunction singleton(short s, long l) {
        return new Singleton(s, l);
    }

    public static Short2LongFunction singleton(Short s, Long l) {
        return new Singleton(s, l);
    }

    public static Short2LongFunction synchronize(Short2LongFunction short2LongFunction) {
        return new SynchronizedFunction(short2LongFunction);
    }

    public static Short2LongFunction synchronize(Short2LongFunction short2LongFunction, Object object) {
        return new SynchronizedFunction(short2LongFunction, object);
    }

    public static Short2LongFunction unmodifiable(Short2LongFunction short2LongFunction) {
        return new UnmodifiableFunction(short2LongFunction);
    }

    public static Short2LongFunction primitive(java.util.function.Function<? super Short, ? extends Long> function) {
        Objects.requireNonNull(function);
        if (function instanceof Short2LongFunction) {
            return (Short2LongFunction)function;
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
    implements Short2LongFunction {
        protected final java.util.function.Function<? super Short, ? extends Long> function;

        protected PrimitiveFunction(java.util.function.Function<? super Short, ? extends Long> function) {
            this.function = function;
        }

        @Override
        public boolean containsKey(short s) {
            return this.function.apply((Short)s) != null;
        }

        @Override
        @Deprecated
        public boolean containsKey(Object object) {
            if (object == null) {
                return true;
            }
            return this.function.apply((Short)object) != null;
        }

        @Override
        public long get(short s) {
            Long l = this.function.apply((Short)s);
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
            return this.function.apply((Short)object);
        }

        @Override
        @Deprecated
        public Long put(Short s, Long l) {
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
            return this.put((Short)object, (Long)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableFunction
    extends AbstractShort2LongFunction
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Short2LongFunction function;

        protected UnmodifiableFunction(Short2LongFunction short2LongFunction) {
            if (short2LongFunction == null) {
                throw new NullPointerException();
            }
            this.function = short2LongFunction;
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
        public boolean containsKey(short s) {
            return this.function.containsKey(s);
        }

        @Override
        public long put(short s, long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long get(short s) {
            return this.function.get(s);
        }

        @Override
        public long remove(short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Long put(Short s, Long l) {
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
            return this.put((Short)object, (Long)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedFunction
    implements Short2LongFunction,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Short2LongFunction function;
        protected final Object sync;

        protected SynchronizedFunction(Short2LongFunction short2LongFunction, Object object) {
            if (short2LongFunction == null) {
                throw new NullPointerException();
            }
            this.function = short2LongFunction;
            this.sync = object;
        }

        protected SynchronizedFunction(Short2LongFunction short2LongFunction) {
            if (short2LongFunction == null) {
                throw new NullPointerException();
            }
            this.function = short2LongFunction;
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
        public Long apply(Short s) {
            Object object = this.sync;
            synchronized (object) {
                return (Long)this.function.apply(s);
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
        public boolean containsKey(short s) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.containsKey(s);
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
        public long put(short s, long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(s, l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long get(short s) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.get(s);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long remove(short s) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.remove(s);
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
        public Long put(Short s, Long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(s, l);
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
            return this.put((Short)object, (Long)object2);
        }

        @Override
        @Deprecated
        public Object apply(Object object) {
            return this.apply((Short)object);
        }
    }

    public static class Singleton
    extends AbstractShort2LongFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final short key;
        protected final long value;

        protected Singleton(short s, long l) {
            this.key = s;
            this.value = l;
        }

        @Override
        public boolean containsKey(short s) {
            return this.key == s;
        }

        @Override
        public long get(short s) {
            return this.key == s ? this.value : this.defRetValue;
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
    extends AbstractShort2LongFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyFunction() {
        }

        @Override
        public long get(short s) {
            return 0L;
        }

        @Override
        public boolean containsKey(short s) {
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

