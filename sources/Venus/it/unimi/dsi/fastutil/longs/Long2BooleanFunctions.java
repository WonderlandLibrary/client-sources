/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.longs.AbstractLong2BooleanFunction;
import it.unimi.dsi.fastutil.longs.Long2BooleanFunction;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.LongPredicate;

public final class Long2BooleanFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Long2BooleanFunctions() {
    }

    public static Long2BooleanFunction singleton(long l, boolean bl) {
        return new Singleton(l, bl);
    }

    public static Long2BooleanFunction singleton(Long l, Boolean bl) {
        return new Singleton(l, bl);
    }

    public static Long2BooleanFunction synchronize(Long2BooleanFunction long2BooleanFunction) {
        return new SynchronizedFunction(long2BooleanFunction);
    }

    public static Long2BooleanFunction synchronize(Long2BooleanFunction long2BooleanFunction, Object object) {
        return new SynchronizedFunction(long2BooleanFunction, object);
    }

    public static Long2BooleanFunction unmodifiable(Long2BooleanFunction long2BooleanFunction) {
        return new UnmodifiableFunction(long2BooleanFunction);
    }

    public static Long2BooleanFunction primitive(java.util.function.Function<? super Long, ? extends Boolean> function) {
        Objects.requireNonNull(function);
        if (function instanceof Long2BooleanFunction) {
            return (Long2BooleanFunction)function;
        }
        if (function instanceof LongPredicate) {
            return ((LongPredicate)((Object)function))::test;
        }
        return new PrimitiveFunction(function);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class PrimitiveFunction
    implements Long2BooleanFunction {
        protected final java.util.function.Function<? super Long, ? extends Boolean> function;

        protected PrimitiveFunction(java.util.function.Function<? super Long, ? extends Boolean> function) {
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
        public boolean get(long l) {
            Boolean bl = this.function.apply((Long)l);
            if (bl == null) {
                return this.defaultReturnValue();
            }
            return bl;
        }

        @Override
        @Deprecated
        public Boolean get(Object object) {
            if (object == null) {
                return null;
            }
            return this.function.apply((Long)object);
        }

        @Override
        @Deprecated
        public Boolean put(Long l, Boolean bl) {
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
            return this.put((Long)object, (Boolean)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableFunction
    extends AbstractLong2BooleanFunction
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Long2BooleanFunction function;

        protected UnmodifiableFunction(Long2BooleanFunction long2BooleanFunction) {
            if (long2BooleanFunction == null) {
                throw new NullPointerException();
            }
            this.function = long2BooleanFunction;
        }

        @Override
        public int size() {
            return this.function.size();
        }

        @Override
        public boolean defaultReturnValue() {
            return this.function.defaultReturnValue();
        }

        @Override
        public void defaultReturnValue(boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean containsKey(long l) {
            return this.function.containsKey(l);
        }

        @Override
        public boolean put(long l, boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean get(long l) {
            return this.function.get(l);
        }

        @Override
        public boolean remove(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Boolean put(Long l, Boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Boolean get(Object object) {
            return this.function.get(object);
        }

        @Override
        @Deprecated
        public Boolean remove(Object object) {
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
            return this.put((Long)object, (Boolean)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedFunction
    implements Long2BooleanFunction,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Long2BooleanFunction function;
        protected final Object sync;

        protected SynchronizedFunction(Long2BooleanFunction long2BooleanFunction, Object object) {
            if (long2BooleanFunction == null) {
                throw new NullPointerException();
            }
            this.function = long2BooleanFunction;
            this.sync = object;
        }

        protected SynchronizedFunction(Long2BooleanFunction long2BooleanFunction) {
            if (long2BooleanFunction == null) {
                throw new NullPointerException();
            }
            this.function = long2BooleanFunction;
            this.sync = this;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean test(long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.test(l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Boolean apply(Long l) {
            Object object = this.sync;
            synchronized (object) {
                return (Boolean)this.function.apply(l);
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
        public boolean defaultReturnValue() {
            Object object = this.sync;
            synchronized (object) {
                return this.function.defaultReturnValue();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void defaultReturnValue(boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                this.function.defaultReturnValue(bl);
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
        public boolean put(long l, boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(l, bl);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean get(long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.get(l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(long l) {
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
        public Boolean put(Long l, Boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(l, bl);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Boolean get(Object object) {
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
        public Boolean remove(Object object) {
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
            return this.put((Long)object, (Boolean)object2);
        }

        @Override
        @Deprecated
        public Object apply(Object object) {
            return this.apply((Long)object);
        }
    }

    public static class Singleton
    extends AbstractLong2BooleanFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final long key;
        protected final boolean value;

        protected Singleton(long l, boolean bl) {
            this.key = l;
            this.value = bl;
        }

        @Override
        public boolean containsKey(long l) {
            return this.key == l;
        }

        @Override
        public boolean get(long l) {
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
    extends AbstractLong2BooleanFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyFunction() {
        }

        @Override
        public boolean get(long l) {
            return true;
        }

        @Override
        public boolean containsKey(long l) {
            return true;
        }

        @Override
        public boolean defaultReturnValue() {
            return true;
        }

        @Override
        public void defaultReturnValue(boolean bl) {
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

