/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.longs.AbstractLong2IntFunction;
import it.unimi.dsi.fastutil.longs.Long2IntFunction;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.LongToIntFunction;

public final class Long2IntFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Long2IntFunctions() {
    }

    public static Long2IntFunction singleton(long l, int n) {
        return new Singleton(l, n);
    }

    public static Long2IntFunction singleton(Long l, Integer n) {
        return new Singleton(l, n);
    }

    public static Long2IntFunction synchronize(Long2IntFunction long2IntFunction) {
        return new SynchronizedFunction(long2IntFunction);
    }

    public static Long2IntFunction synchronize(Long2IntFunction long2IntFunction, Object object) {
        return new SynchronizedFunction(long2IntFunction, object);
    }

    public static Long2IntFunction unmodifiable(Long2IntFunction long2IntFunction) {
        return new UnmodifiableFunction(long2IntFunction);
    }

    public static Long2IntFunction primitive(java.util.function.Function<? super Long, ? extends Integer> function) {
        Objects.requireNonNull(function);
        if (function instanceof Long2IntFunction) {
            return (Long2IntFunction)function;
        }
        if (function instanceof LongToIntFunction) {
            return ((LongToIntFunction)((Object)function))::applyAsInt;
        }
        return new PrimitiveFunction(function);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class PrimitiveFunction
    implements Long2IntFunction {
        protected final java.util.function.Function<? super Long, ? extends Integer> function;

        protected PrimitiveFunction(java.util.function.Function<? super Long, ? extends Integer> function) {
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
        public int get(long l) {
            Integer n = this.function.apply((Long)l);
            if (n == null) {
                return this.defaultReturnValue();
            }
            return n;
        }

        @Override
        @Deprecated
        public Integer get(Object object) {
            if (object == null) {
                return null;
            }
            return this.function.apply((Long)object);
        }

        @Override
        @Deprecated
        public Integer put(Long l, Integer n) {
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
            return this.put((Long)object, (Integer)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableFunction
    extends AbstractLong2IntFunction
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Long2IntFunction function;

        protected UnmodifiableFunction(Long2IntFunction long2IntFunction) {
            if (long2IntFunction == null) {
                throw new NullPointerException();
            }
            this.function = long2IntFunction;
        }

        @Override
        public int size() {
            return this.function.size();
        }

        @Override
        public int defaultReturnValue() {
            return this.function.defaultReturnValue();
        }

        @Override
        public void defaultReturnValue(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean containsKey(long l) {
            return this.function.containsKey(l);
        }

        @Override
        public int put(long l, int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int get(long l) {
            return this.function.get(l);
        }

        @Override
        public int remove(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Integer put(Long l, Integer n) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Integer get(Object object) {
            return this.function.get(object);
        }

        @Override
        @Deprecated
        public Integer remove(Object object) {
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
            return this.put((Long)object, (Integer)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedFunction
    implements Long2IntFunction,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Long2IntFunction function;
        protected final Object sync;

        protected SynchronizedFunction(Long2IntFunction long2IntFunction, Object object) {
            if (long2IntFunction == null) {
                throw new NullPointerException();
            }
            this.function = long2IntFunction;
            this.sync = object;
        }

        protected SynchronizedFunction(Long2IntFunction long2IntFunction) {
            if (long2IntFunction == null) {
                throw new NullPointerException();
            }
            this.function = long2IntFunction;
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
        public Integer apply(Long l) {
            Object object = this.sync;
            synchronized (object) {
                return (Integer)this.function.apply(l);
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
        public int defaultReturnValue() {
            Object object = this.sync;
            synchronized (object) {
                return this.function.defaultReturnValue();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void defaultReturnValue(int n) {
            Object object = this.sync;
            synchronized (object) {
                this.function.defaultReturnValue(n);
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
        public int put(long l, int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(l, n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int get(long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.get(l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int remove(long l) {
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
        public Integer put(Long l, Integer n) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(l, n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Integer get(Object object) {
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
        public Integer remove(Object object) {
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
            return this.put((Long)object, (Integer)object2);
        }

        @Override
        @Deprecated
        public Object apply(Object object) {
            return this.apply((Long)object);
        }
    }

    public static class Singleton
    extends AbstractLong2IntFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final long key;
        protected final int value;

        protected Singleton(long l, int n) {
            this.key = l;
            this.value = n;
        }

        @Override
        public boolean containsKey(long l) {
            return this.key == l;
        }

        @Override
        public int get(long l) {
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
    extends AbstractLong2IntFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyFunction() {
        }

        @Override
        public int get(long l) {
            return 1;
        }

        @Override
        public boolean containsKey(long l) {
            return true;
        }

        @Override
        public int defaultReturnValue() {
            return 1;
        }

        @Override
        public void defaultReturnValue(int n) {
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

