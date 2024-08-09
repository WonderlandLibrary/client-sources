/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.shorts.AbstractShort2IntFunction;
import it.unimi.dsi.fastutil.shorts.Short2IntFunction;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.IntUnaryOperator;

public final class Short2IntFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Short2IntFunctions() {
    }

    public static Short2IntFunction singleton(short s, int n) {
        return new Singleton(s, n);
    }

    public static Short2IntFunction singleton(Short s, Integer n) {
        return new Singleton(s, n);
    }

    public static Short2IntFunction synchronize(Short2IntFunction short2IntFunction) {
        return new SynchronizedFunction(short2IntFunction);
    }

    public static Short2IntFunction synchronize(Short2IntFunction short2IntFunction, Object object) {
        return new SynchronizedFunction(short2IntFunction, object);
    }

    public static Short2IntFunction unmodifiable(Short2IntFunction short2IntFunction) {
        return new UnmodifiableFunction(short2IntFunction);
    }

    public static Short2IntFunction primitive(java.util.function.Function<? super Short, ? extends Integer> function) {
        Objects.requireNonNull(function);
        if (function instanceof Short2IntFunction) {
            return (Short2IntFunction)function;
        }
        if (function instanceof IntUnaryOperator) {
            return ((IntUnaryOperator)((Object)function))::applyAsInt;
        }
        return new PrimitiveFunction(function);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class PrimitiveFunction
    implements Short2IntFunction {
        protected final java.util.function.Function<? super Short, ? extends Integer> function;

        protected PrimitiveFunction(java.util.function.Function<? super Short, ? extends Integer> function) {
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
        public int get(short s) {
            Integer n = this.function.apply((Short)s);
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
            return this.function.apply((Short)object);
        }

        @Override
        @Deprecated
        public Integer put(Short s, Integer n) {
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
            return this.put((Short)object, (Integer)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableFunction
    extends AbstractShort2IntFunction
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Short2IntFunction function;

        protected UnmodifiableFunction(Short2IntFunction short2IntFunction) {
            if (short2IntFunction == null) {
                throw new NullPointerException();
            }
            this.function = short2IntFunction;
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
        public boolean containsKey(short s) {
            return this.function.containsKey(s);
        }

        @Override
        public int put(short s, int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int get(short s) {
            return this.function.get(s);
        }

        @Override
        public int remove(short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Integer put(Short s, Integer n) {
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
            return this.put((Short)object, (Integer)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedFunction
    implements Short2IntFunction,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Short2IntFunction function;
        protected final Object sync;

        protected SynchronizedFunction(Short2IntFunction short2IntFunction, Object object) {
            if (short2IntFunction == null) {
                throw new NullPointerException();
            }
            this.function = short2IntFunction;
            this.sync = object;
        }

        protected SynchronizedFunction(Short2IntFunction short2IntFunction) {
            if (short2IntFunction == null) {
                throw new NullPointerException();
            }
            this.function = short2IntFunction;
            this.sync = this;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public int applyAsInt(int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.applyAsInt(n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Integer apply(Short s) {
            Object object = this.sync;
            synchronized (object) {
                return (Integer)this.function.apply(s);
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
        public int put(short s, int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(s, n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int get(short s) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.get(s);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int remove(short s) {
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
        public Integer put(Short s, Integer n) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(s, n);
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
            return this.put((Short)object, (Integer)object2);
        }

        @Override
        @Deprecated
        public Object apply(Object object) {
            return this.apply((Short)object);
        }
    }

    public static class Singleton
    extends AbstractShort2IntFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final short key;
        protected final int value;

        protected Singleton(short s, int n) {
            this.key = s;
            this.value = n;
        }

        @Override
        public boolean containsKey(short s) {
            return this.key == s;
        }

        @Override
        public int get(short s) {
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
    extends AbstractShort2IntFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyFunction() {
        }

        @Override
        public int get(short s) {
            return 1;
        }

        @Override
        public boolean containsKey(short s) {
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

