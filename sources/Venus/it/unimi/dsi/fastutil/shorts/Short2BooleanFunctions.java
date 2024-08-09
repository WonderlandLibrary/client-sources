/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.shorts.AbstractShort2BooleanFunction;
import it.unimi.dsi.fastutil.shorts.Short2BooleanFunction;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.IntPredicate;

public final class Short2BooleanFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Short2BooleanFunctions() {
    }

    public static Short2BooleanFunction singleton(short s, boolean bl) {
        return new Singleton(s, bl);
    }

    public static Short2BooleanFunction singleton(Short s, Boolean bl) {
        return new Singleton(s, bl);
    }

    public static Short2BooleanFunction synchronize(Short2BooleanFunction short2BooleanFunction) {
        return new SynchronizedFunction(short2BooleanFunction);
    }

    public static Short2BooleanFunction synchronize(Short2BooleanFunction short2BooleanFunction, Object object) {
        return new SynchronizedFunction(short2BooleanFunction, object);
    }

    public static Short2BooleanFunction unmodifiable(Short2BooleanFunction short2BooleanFunction) {
        return new UnmodifiableFunction(short2BooleanFunction);
    }

    public static Short2BooleanFunction primitive(java.util.function.Function<? super Short, ? extends Boolean> function) {
        Objects.requireNonNull(function);
        if (function instanceof Short2BooleanFunction) {
            return (Short2BooleanFunction)function;
        }
        if (function instanceof IntPredicate) {
            return ((IntPredicate)((Object)function))::test;
        }
        return new PrimitiveFunction(function);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class PrimitiveFunction
    implements Short2BooleanFunction {
        protected final java.util.function.Function<? super Short, ? extends Boolean> function;

        protected PrimitiveFunction(java.util.function.Function<? super Short, ? extends Boolean> function) {
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
        public boolean get(short s) {
            Boolean bl = this.function.apply((Short)s);
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
            return this.function.apply((Short)object);
        }

        @Override
        @Deprecated
        public Boolean put(Short s, Boolean bl) {
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
            return this.put((Short)object, (Boolean)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableFunction
    extends AbstractShort2BooleanFunction
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Short2BooleanFunction function;

        protected UnmodifiableFunction(Short2BooleanFunction short2BooleanFunction) {
            if (short2BooleanFunction == null) {
                throw new NullPointerException();
            }
            this.function = short2BooleanFunction;
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
        public boolean containsKey(short s) {
            return this.function.containsKey(s);
        }

        @Override
        public boolean put(short s, boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean get(short s) {
            return this.function.get(s);
        }

        @Override
        public boolean remove(short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Boolean put(Short s, Boolean bl) {
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
            return this.put((Short)object, (Boolean)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedFunction
    implements Short2BooleanFunction,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Short2BooleanFunction function;
        protected final Object sync;

        protected SynchronizedFunction(Short2BooleanFunction short2BooleanFunction, Object object) {
            if (short2BooleanFunction == null) {
                throw new NullPointerException();
            }
            this.function = short2BooleanFunction;
            this.sync = object;
        }

        protected SynchronizedFunction(Short2BooleanFunction short2BooleanFunction) {
            if (short2BooleanFunction == null) {
                throw new NullPointerException();
            }
            this.function = short2BooleanFunction;
            this.sync = this;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean test(int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.test(n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Boolean apply(Short s) {
            Object object = this.sync;
            synchronized (object) {
                return (Boolean)this.function.apply(s);
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
        public boolean put(short s, boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(s, bl);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean get(short s) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.get(s);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(short s) {
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
        public Boolean put(Short s, Boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(s, bl);
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
            return this.put((Short)object, (Boolean)object2);
        }

        @Override
        @Deprecated
        public Object apply(Object object) {
            return this.apply((Short)object);
        }
    }

    public static class Singleton
    extends AbstractShort2BooleanFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final short key;
        protected final boolean value;

        protected Singleton(short s, boolean bl) {
            this.key = s;
            this.value = bl;
        }

        @Override
        public boolean containsKey(short s) {
            return this.key == s;
        }

        @Override
        public boolean get(short s) {
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
    extends AbstractShort2BooleanFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyFunction() {
        }

        @Override
        public boolean get(short s) {
            return true;
        }

        @Override
        public boolean containsKey(short s) {
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

