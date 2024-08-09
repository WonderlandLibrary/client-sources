/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.ints.AbstractInt2ShortFunction;
import it.unimi.dsi.fastutil.ints.Int2ShortFunction;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.IntUnaryOperator;

public final class Int2ShortFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Int2ShortFunctions() {
    }

    public static Int2ShortFunction singleton(int n, short s) {
        return new Singleton(n, s);
    }

    public static Int2ShortFunction singleton(Integer n, Short s) {
        return new Singleton(n, s);
    }

    public static Int2ShortFunction synchronize(Int2ShortFunction int2ShortFunction) {
        return new SynchronizedFunction(int2ShortFunction);
    }

    public static Int2ShortFunction synchronize(Int2ShortFunction int2ShortFunction, Object object) {
        return new SynchronizedFunction(int2ShortFunction, object);
    }

    public static Int2ShortFunction unmodifiable(Int2ShortFunction int2ShortFunction) {
        return new UnmodifiableFunction(int2ShortFunction);
    }

    public static Int2ShortFunction primitive(java.util.function.Function<? super Integer, ? extends Short> function) {
        Objects.requireNonNull(function);
        if (function instanceof Int2ShortFunction) {
            return (Int2ShortFunction)function;
        }
        if (function instanceof IntUnaryOperator) {
            return arg_0 -> Int2ShortFunctions.lambda$primitive$0(function, arg_0);
        }
        return new PrimitiveFunction(function);
    }

    private static short lambda$primitive$0(java.util.function.Function function, int n) {
        return SafeMath.safeIntToShort(((IntUnaryOperator)((Object)function)).applyAsInt(n));
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class PrimitiveFunction
    implements Int2ShortFunction {
        protected final java.util.function.Function<? super Integer, ? extends Short> function;

        protected PrimitiveFunction(java.util.function.Function<? super Integer, ? extends Short> function) {
            this.function = function;
        }

        @Override
        public boolean containsKey(int n) {
            return this.function.apply((Integer)n) != null;
        }

        @Override
        @Deprecated
        public boolean containsKey(Object object) {
            if (object == null) {
                return true;
            }
            return this.function.apply((Integer)object) != null;
        }

        @Override
        public short get(int n) {
            Short s = this.function.apply((Integer)n);
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
            return this.function.apply((Integer)object);
        }

        @Override
        @Deprecated
        public Short put(Integer n, Short s) {
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
            return this.put((Integer)object, (Short)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableFunction
    extends AbstractInt2ShortFunction
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Int2ShortFunction function;

        protected UnmodifiableFunction(Int2ShortFunction int2ShortFunction) {
            if (int2ShortFunction == null) {
                throw new NullPointerException();
            }
            this.function = int2ShortFunction;
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
        public boolean containsKey(int n) {
            return this.function.containsKey(n);
        }

        @Override
        public short put(int n, short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public short get(int n) {
            return this.function.get(n);
        }

        @Override
        public short remove(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Short put(Integer n, Short s) {
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
            return this.put((Integer)object, (Short)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedFunction
    implements Int2ShortFunction,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Int2ShortFunction function;
        protected final Object sync;

        protected SynchronizedFunction(Int2ShortFunction int2ShortFunction, Object object) {
            if (int2ShortFunction == null) {
                throw new NullPointerException();
            }
            this.function = int2ShortFunction;
            this.sync = object;
        }

        protected SynchronizedFunction(Int2ShortFunction int2ShortFunction) {
            if (int2ShortFunction == null) {
                throw new NullPointerException();
            }
            this.function = int2ShortFunction;
            this.sync = this;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
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
        public Short apply(Integer n) {
            Object object = this.sync;
            synchronized (object) {
                return (Short)this.function.apply(n);
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
        public boolean containsKey(int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.containsKey(n);
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
        public short put(int n, short s) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(n, s);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short get(int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.get(n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short remove(int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.remove(n);
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
        public Short put(Integer n, Short s) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(n, s);
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
            return this.put((Integer)object, (Short)object2);
        }

        @Override
        @Deprecated
        public Object apply(Object object) {
            return this.apply((Integer)object);
        }
    }

    public static class Singleton
    extends AbstractInt2ShortFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final int key;
        protected final short value;

        protected Singleton(int n, short s) {
            this.key = n;
            this.value = s;
        }

        @Override
        public boolean containsKey(int n) {
            return this.key == n;
        }

        @Override
        public short get(int n) {
            return this.key == n ? this.value : this.defRetValue;
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
    extends AbstractInt2ShortFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyFunction() {
        }

        @Override
        public short get(int n) {
            return 1;
        }

        @Override
        public boolean containsKey(int n) {
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

