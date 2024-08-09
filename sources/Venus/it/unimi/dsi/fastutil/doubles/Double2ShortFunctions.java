/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.doubles.AbstractDouble2ShortFunction;
import it.unimi.dsi.fastutil.doubles.Double2ShortFunction;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.DoubleToIntFunction;

public final class Double2ShortFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Double2ShortFunctions() {
    }

    public static Double2ShortFunction singleton(double d, short s) {
        return new Singleton(d, s);
    }

    public static Double2ShortFunction singleton(Double d, Short s) {
        return new Singleton(d, s);
    }

    public static Double2ShortFunction synchronize(Double2ShortFunction double2ShortFunction) {
        return new SynchronizedFunction(double2ShortFunction);
    }

    public static Double2ShortFunction synchronize(Double2ShortFunction double2ShortFunction, Object object) {
        return new SynchronizedFunction(double2ShortFunction, object);
    }

    public static Double2ShortFunction unmodifiable(Double2ShortFunction double2ShortFunction) {
        return new UnmodifiableFunction(double2ShortFunction);
    }

    public static Double2ShortFunction primitive(java.util.function.Function<? super Double, ? extends Short> function) {
        Objects.requireNonNull(function);
        if (function instanceof Double2ShortFunction) {
            return (Double2ShortFunction)function;
        }
        if (function instanceof DoubleToIntFunction) {
            return arg_0 -> Double2ShortFunctions.lambda$primitive$0(function, arg_0);
        }
        return new PrimitiveFunction(function);
    }

    private static short lambda$primitive$0(java.util.function.Function function, double d) {
        return SafeMath.safeIntToShort(((DoubleToIntFunction)((Object)function)).applyAsInt(d));
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class PrimitiveFunction
    implements Double2ShortFunction {
        protected final java.util.function.Function<? super Double, ? extends Short> function;

        protected PrimitiveFunction(java.util.function.Function<? super Double, ? extends Short> function) {
            this.function = function;
        }

        @Override
        public boolean containsKey(double d) {
            return this.function.apply((Double)d) != null;
        }

        @Override
        @Deprecated
        public boolean containsKey(Object object) {
            if (object == null) {
                return true;
            }
            return this.function.apply((Double)object) != null;
        }

        @Override
        public short get(double d) {
            Short s = this.function.apply((Double)d);
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
            return this.function.apply((Double)object);
        }

        @Override
        @Deprecated
        public Short put(Double d, Short s) {
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
            return this.put((Double)object, (Short)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableFunction
    extends AbstractDouble2ShortFunction
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Double2ShortFunction function;

        protected UnmodifiableFunction(Double2ShortFunction double2ShortFunction) {
            if (double2ShortFunction == null) {
                throw new NullPointerException();
            }
            this.function = double2ShortFunction;
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
        public boolean containsKey(double d) {
            return this.function.containsKey(d);
        }

        @Override
        public short put(double d, short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public short get(double d) {
            return this.function.get(d);
        }

        @Override
        public short remove(double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Short put(Double d, Short s) {
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
            return this.put((Double)object, (Short)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedFunction
    implements Double2ShortFunction,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Double2ShortFunction function;
        protected final Object sync;

        protected SynchronizedFunction(Double2ShortFunction double2ShortFunction, Object object) {
            if (double2ShortFunction == null) {
                throw new NullPointerException();
            }
            this.function = double2ShortFunction;
            this.sync = object;
        }

        protected SynchronizedFunction(Double2ShortFunction double2ShortFunction) {
            if (double2ShortFunction == null) {
                throw new NullPointerException();
            }
            this.function = double2ShortFunction;
            this.sync = this;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int applyAsInt(double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.applyAsInt(d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Short apply(Double d) {
            Object object = this.sync;
            synchronized (object) {
                return (Short)this.function.apply(d);
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
        public boolean containsKey(double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.containsKey(d);
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
        public short put(double d, short s) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(d, s);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short get(double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.get(d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short remove(double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.remove(d);
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
        public Short put(Double d, Short s) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(d, s);
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
            return this.put((Double)object, (Short)object2);
        }

        @Override
        @Deprecated
        public Object apply(Object object) {
            return this.apply((Double)object);
        }
    }

    public static class Singleton
    extends AbstractDouble2ShortFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final double key;
        protected final short value;

        protected Singleton(double d, short s) {
            this.key = d;
            this.value = s;
        }

        @Override
        public boolean containsKey(double d) {
            return Double.doubleToLongBits(this.key) == Double.doubleToLongBits(d);
        }

        @Override
        public short get(double d) {
            return Double.doubleToLongBits(this.key) == Double.doubleToLongBits(d) ? this.value : this.defRetValue;
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
    extends AbstractDouble2ShortFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyFunction() {
        }

        @Override
        public short get(double d) {
            return 1;
        }

        @Override
        public boolean containsKey(double d) {
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

