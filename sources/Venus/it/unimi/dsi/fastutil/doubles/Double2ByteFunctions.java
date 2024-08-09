/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.doubles.AbstractDouble2ByteFunction;
import it.unimi.dsi.fastutil.doubles.Double2ByteFunction;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.DoubleToIntFunction;

public final class Double2ByteFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Double2ByteFunctions() {
    }

    public static Double2ByteFunction singleton(double d, byte by) {
        return new Singleton(d, by);
    }

    public static Double2ByteFunction singleton(Double d, Byte by) {
        return new Singleton(d, by);
    }

    public static Double2ByteFunction synchronize(Double2ByteFunction double2ByteFunction) {
        return new SynchronizedFunction(double2ByteFunction);
    }

    public static Double2ByteFunction synchronize(Double2ByteFunction double2ByteFunction, Object object) {
        return new SynchronizedFunction(double2ByteFunction, object);
    }

    public static Double2ByteFunction unmodifiable(Double2ByteFunction double2ByteFunction) {
        return new UnmodifiableFunction(double2ByteFunction);
    }

    public static Double2ByteFunction primitive(java.util.function.Function<? super Double, ? extends Byte> function) {
        Objects.requireNonNull(function);
        if (function instanceof Double2ByteFunction) {
            return (Double2ByteFunction)function;
        }
        if (function instanceof DoubleToIntFunction) {
            return arg_0 -> Double2ByteFunctions.lambda$primitive$0(function, arg_0);
        }
        return new PrimitiveFunction(function);
    }

    private static byte lambda$primitive$0(java.util.function.Function function, double d) {
        return SafeMath.safeIntToByte(((DoubleToIntFunction)((Object)function)).applyAsInt(d));
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class PrimitiveFunction
    implements Double2ByteFunction {
        protected final java.util.function.Function<? super Double, ? extends Byte> function;

        protected PrimitiveFunction(java.util.function.Function<? super Double, ? extends Byte> function) {
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
        public byte get(double d) {
            Byte by = this.function.apply((Double)d);
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
            return this.function.apply((Double)object);
        }

        @Override
        @Deprecated
        public Byte put(Double d, Byte by) {
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
            return this.put((Double)object, (Byte)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableFunction
    extends AbstractDouble2ByteFunction
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Double2ByteFunction function;

        protected UnmodifiableFunction(Double2ByteFunction double2ByteFunction) {
            if (double2ByteFunction == null) {
                throw new NullPointerException();
            }
            this.function = double2ByteFunction;
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
        public boolean containsKey(double d) {
            return this.function.containsKey(d);
        }

        @Override
        public byte put(double d, byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte get(double d) {
            return this.function.get(d);
        }

        @Override
        public byte remove(double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Byte put(Double d, Byte by) {
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
            return this.put((Double)object, (Byte)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedFunction
    implements Double2ByteFunction,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Double2ByteFunction function;
        protected final Object sync;

        protected SynchronizedFunction(Double2ByteFunction double2ByteFunction, Object object) {
            if (double2ByteFunction == null) {
                throw new NullPointerException();
            }
            this.function = double2ByteFunction;
            this.sync = object;
        }

        protected SynchronizedFunction(Double2ByteFunction double2ByteFunction) {
            if (double2ByteFunction == null) {
                throw new NullPointerException();
            }
            this.function = double2ByteFunction;
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
        public Byte apply(Double d) {
            Object object = this.sync;
            synchronized (object) {
                return (Byte)this.function.apply(d);
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
        public byte put(double d, byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(d, by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte get(double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.get(d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte remove(double d) {
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
        public Byte put(Double d, Byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(d, by);
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
            return this.put((Double)object, (Byte)object2);
        }

        @Override
        @Deprecated
        public Object apply(Object object) {
            return this.apply((Double)object);
        }
    }

    public static class Singleton
    extends AbstractDouble2ByteFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final double key;
        protected final byte value;

        protected Singleton(double d, byte by) {
            this.key = d;
            this.value = by;
        }

        @Override
        public boolean containsKey(double d) {
            return Double.doubleToLongBits(this.key) == Double.doubleToLongBits(d);
        }

        @Override
        public byte get(double d) {
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
    extends AbstractDouble2ByteFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyFunction() {
        }

        @Override
        public byte get(double d) {
            return 1;
        }

        @Override
        public boolean containsKey(double d) {
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

