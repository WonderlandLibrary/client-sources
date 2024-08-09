/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.doubles.AbstractDouble2FloatFunction;
import it.unimi.dsi.fastutil.doubles.Double2FloatFunction;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.DoubleUnaryOperator;

public final class Double2FloatFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Double2FloatFunctions() {
    }

    public static Double2FloatFunction singleton(double d, float f) {
        return new Singleton(d, f);
    }

    public static Double2FloatFunction singleton(Double d, Float f) {
        return new Singleton(d, f.floatValue());
    }

    public static Double2FloatFunction synchronize(Double2FloatFunction double2FloatFunction) {
        return new SynchronizedFunction(double2FloatFunction);
    }

    public static Double2FloatFunction synchronize(Double2FloatFunction double2FloatFunction, Object object) {
        return new SynchronizedFunction(double2FloatFunction, object);
    }

    public static Double2FloatFunction unmodifiable(Double2FloatFunction double2FloatFunction) {
        return new UnmodifiableFunction(double2FloatFunction);
    }

    public static Double2FloatFunction primitive(java.util.function.Function<? super Double, ? extends Float> function) {
        Objects.requireNonNull(function);
        if (function instanceof Double2FloatFunction) {
            return (Double2FloatFunction)function;
        }
        if (function instanceof DoubleUnaryOperator) {
            return arg_0 -> Double2FloatFunctions.lambda$primitive$0(function, arg_0);
        }
        return new PrimitiveFunction(function);
    }

    private static float lambda$primitive$0(java.util.function.Function function, double d) {
        return SafeMath.safeDoubleToFloat(((DoubleUnaryOperator)((Object)function)).applyAsDouble(d));
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class PrimitiveFunction
    implements Double2FloatFunction {
        protected final java.util.function.Function<? super Double, ? extends Float> function;

        protected PrimitiveFunction(java.util.function.Function<? super Double, ? extends Float> function) {
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
        public float get(double d) {
            Float f = this.function.apply((Double)d);
            if (f == null) {
                return this.defaultReturnValue();
            }
            return f.floatValue();
        }

        @Override
        @Deprecated
        public Float get(Object object) {
            if (object == null) {
                return null;
            }
            return this.function.apply((Double)object);
        }

        @Override
        @Deprecated
        public Float put(Double d, Float f) {
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
            return this.put((Double)object, (Float)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableFunction
    extends AbstractDouble2FloatFunction
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Double2FloatFunction function;

        protected UnmodifiableFunction(Double2FloatFunction double2FloatFunction) {
            if (double2FloatFunction == null) {
                throw new NullPointerException();
            }
            this.function = double2FloatFunction;
        }

        @Override
        public int size() {
            return this.function.size();
        }

        @Override
        public float defaultReturnValue() {
            return this.function.defaultReturnValue();
        }

        @Override
        public void defaultReturnValue(float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean containsKey(double d) {
            return this.function.containsKey(d);
        }

        @Override
        public float put(double d, float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float get(double d) {
            return this.function.get(d);
        }

        @Override
        public float remove(double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Float put(Double d, Float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Float get(Object object) {
            return this.function.get(object);
        }

        @Override
        @Deprecated
        public Float remove(Object object) {
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
            return this.put((Double)object, (Float)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedFunction
    implements Double2FloatFunction,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Double2FloatFunction function;
        protected final Object sync;

        protected SynchronizedFunction(Double2FloatFunction double2FloatFunction, Object object) {
            if (double2FloatFunction == null) {
                throw new NullPointerException();
            }
            this.function = double2FloatFunction;
            this.sync = object;
        }

        protected SynchronizedFunction(Double2FloatFunction double2FloatFunction) {
            if (double2FloatFunction == null) {
                throw new NullPointerException();
            }
            this.function = double2FloatFunction;
            this.sync = this;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double applyAsDouble(double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.applyAsDouble(d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Float apply(Double d) {
            Object object = this.sync;
            synchronized (object) {
                return (Float)this.function.apply(d);
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
        public float defaultReturnValue() {
            Object object = this.sync;
            synchronized (object) {
                return this.function.defaultReturnValue();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void defaultReturnValue(float f) {
            Object object = this.sync;
            synchronized (object) {
                this.function.defaultReturnValue(f);
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
        public float put(double d, float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(d, f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float get(double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.get(d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float remove(double d) {
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
        public Float put(Double d, Float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(d, f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Float get(Object object) {
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
        public Float remove(Object object) {
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
            return this.put((Double)object, (Float)object2);
        }

        @Override
        @Deprecated
        public Object apply(Object object) {
            return this.apply((Double)object);
        }
    }

    public static class Singleton
    extends AbstractDouble2FloatFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final double key;
        protected final float value;

        protected Singleton(double d, float f) {
            this.key = d;
            this.value = f;
        }

        @Override
        public boolean containsKey(double d) {
            return Double.doubleToLongBits(this.key) == Double.doubleToLongBits(d);
        }

        @Override
        public float get(double d) {
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
    extends AbstractDouble2FloatFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyFunction() {
        }

        @Override
        public float get(double d) {
            return 0.0f;
        }

        @Override
        public boolean containsKey(double d) {
            return true;
        }

        @Override
        public float defaultReturnValue() {
            return 0.0f;
        }

        @Override
        public void defaultReturnValue(float f) {
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

