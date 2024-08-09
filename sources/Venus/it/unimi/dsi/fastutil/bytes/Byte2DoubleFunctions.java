/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.bytes.AbstractByte2DoubleFunction;
import it.unimi.dsi.fastutil.bytes.Byte2DoubleFunction;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.IntToDoubleFunction;

public final class Byte2DoubleFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Byte2DoubleFunctions() {
    }

    public static Byte2DoubleFunction singleton(byte by, double d) {
        return new Singleton(by, d);
    }

    public static Byte2DoubleFunction singleton(Byte by, Double d) {
        return new Singleton(by, d);
    }

    public static Byte2DoubleFunction synchronize(Byte2DoubleFunction byte2DoubleFunction) {
        return new SynchronizedFunction(byte2DoubleFunction);
    }

    public static Byte2DoubleFunction synchronize(Byte2DoubleFunction byte2DoubleFunction, Object object) {
        return new SynchronizedFunction(byte2DoubleFunction, object);
    }

    public static Byte2DoubleFunction unmodifiable(Byte2DoubleFunction byte2DoubleFunction) {
        return new UnmodifiableFunction(byte2DoubleFunction);
    }

    public static Byte2DoubleFunction primitive(java.util.function.Function<? super Byte, ? extends Double> function) {
        Objects.requireNonNull(function);
        if (function instanceof Byte2DoubleFunction) {
            return (Byte2DoubleFunction)function;
        }
        if (function instanceof IntToDoubleFunction) {
            return ((IntToDoubleFunction)((Object)function))::applyAsDouble;
        }
        return new PrimitiveFunction(function);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class PrimitiveFunction
    implements Byte2DoubleFunction {
        protected final java.util.function.Function<? super Byte, ? extends Double> function;

        protected PrimitiveFunction(java.util.function.Function<? super Byte, ? extends Double> function) {
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
        public double get(byte by) {
            Double d = this.function.apply((Byte)by);
            if (d == null) {
                return this.defaultReturnValue();
            }
            return d;
        }

        @Override
        @Deprecated
        public Double get(Object object) {
            if (object == null) {
                return null;
            }
            return this.function.apply((Byte)object);
        }

        @Override
        @Deprecated
        public Double put(Byte by, Double d) {
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
            return this.put((Byte)object, (Double)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableFunction
    extends AbstractByte2DoubleFunction
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Byte2DoubleFunction function;

        protected UnmodifiableFunction(Byte2DoubleFunction byte2DoubleFunction) {
            if (byte2DoubleFunction == null) {
                throw new NullPointerException();
            }
            this.function = byte2DoubleFunction;
        }

        @Override
        public int size() {
            return this.function.size();
        }

        @Override
        public double defaultReturnValue() {
            return this.function.defaultReturnValue();
        }

        @Override
        public void defaultReturnValue(double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean containsKey(byte by) {
            return this.function.containsKey(by);
        }

        @Override
        public double put(byte by, double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double get(byte by) {
            return this.function.get(by);
        }

        @Override
        public double remove(byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Double put(Byte by, Double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Double get(Object object) {
            return this.function.get(object);
        }

        @Override
        @Deprecated
        public Double remove(Object object) {
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
            return this.put((Byte)object, (Double)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedFunction
    implements Byte2DoubleFunction,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Byte2DoubleFunction function;
        protected final Object sync;

        protected SynchronizedFunction(Byte2DoubleFunction byte2DoubleFunction, Object object) {
            if (byte2DoubleFunction == null) {
                throw new NullPointerException();
            }
            this.function = byte2DoubleFunction;
            this.sync = object;
        }

        protected SynchronizedFunction(Byte2DoubleFunction byte2DoubleFunction) {
            if (byte2DoubleFunction == null) {
                throw new NullPointerException();
            }
            this.function = byte2DoubleFunction;
            this.sync = this;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public double applyAsDouble(int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.applyAsDouble(n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Double apply(Byte by) {
            Object object = this.sync;
            synchronized (object) {
                return (Double)this.function.apply(by);
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
        public double defaultReturnValue() {
            Object object = this.sync;
            synchronized (object) {
                return this.function.defaultReturnValue();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void defaultReturnValue(double d) {
            Object object = this.sync;
            synchronized (object) {
                this.function.defaultReturnValue(d);
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
        public double put(byte by, double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(by, d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double get(byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.get(by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double remove(byte by) {
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
        public Double put(Byte by, Double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(by, d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Double get(Object object) {
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
        public Double remove(Object object) {
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
            return this.put((Byte)object, (Double)object2);
        }

        @Override
        @Deprecated
        public Object apply(Object object) {
            return this.apply((Byte)object);
        }
    }

    public static class Singleton
    extends AbstractByte2DoubleFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final byte key;
        protected final double value;

        protected Singleton(byte by, double d) {
            this.key = by;
            this.value = d;
        }

        @Override
        public boolean containsKey(byte by) {
            return this.key == by;
        }

        @Override
        public double get(byte by) {
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
    extends AbstractByte2DoubleFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyFunction() {
        }

        @Override
        public double get(byte by) {
            return 0.0;
        }

        @Override
        public boolean containsKey(byte by) {
            return true;
        }

        @Override
        public double defaultReturnValue() {
            return 0.0;
        }

        @Override
        public void defaultReturnValue(double d) {
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

