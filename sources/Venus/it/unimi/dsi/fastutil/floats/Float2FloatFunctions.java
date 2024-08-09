/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.floats.AbstractFloat2FloatFunction;
import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.DoubleUnaryOperator;

public final class Float2FloatFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Float2FloatFunctions() {
    }

    public static Float2FloatFunction singleton(float f, float f2) {
        return new Singleton(f, f2);
    }

    public static Float2FloatFunction singleton(Float f, Float f2) {
        return new Singleton(f.floatValue(), f2.floatValue());
    }

    public static Float2FloatFunction synchronize(Float2FloatFunction float2FloatFunction) {
        return new SynchronizedFunction(float2FloatFunction);
    }

    public static Float2FloatFunction synchronize(Float2FloatFunction float2FloatFunction, Object object) {
        return new SynchronizedFunction(float2FloatFunction, object);
    }

    public static Float2FloatFunction unmodifiable(Float2FloatFunction float2FloatFunction) {
        return new UnmodifiableFunction(float2FloatFunction);
    }

    public static Float2FloatFunction primitive(java.util.function.Function<? super Float, ? extends Float> function) {
        Objects.requireNonNull(function);
        if (function instanceof Float2FloatFunction) {
            return (Float2FloatFunction)function;
        }
        if (function instanceof DoubleUnaryOperator) {
            return arg_0 -> Float2FloatFunctions.lambda$primitive$0(function, arg_0);
        }
        return new PrimitiveFunction(function);
    }

    private static float lambda$primitive$0(java.util.function.Function function, float f) {
        return SafeMath.safeDoubleToFloat(((DoubleUnaryOperator)((Object)function)).applyAsDouble(f));
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class PrimitiveFunction
    implements Float2FloatFunction {
        protected final java.util.function.Function<? super Float, ? extends Float> function;

        protected PrimitiveFunction(java.util.function.Function<? super Float, ? extends Float> function) {
            this.function = function;
        }

        @Override
        public boolean containsKey(float f) {
            return this.function.apply(Float.valueOf(f)) != null;
        }

        @Override
        @Deprecated
        public boolean containsKey(Object object) {
            if (object == null) {
                return true;
            }
            return this.function.apply((Float)object) != null;
        }

        @Override
        public float get(float f) {
            Float f2 = this.function.apply(Float.valueOf(f));
            if (f2 == null) {
                return this.defaultReturnValue();
            }
            return f2.floatValue();
        }

        @Override
        @Deprecated
        public Float get(Object object) {
            if (object == null) {
                return null;
            }
            return this.function.apply((Float)object);
        }

        @Override
        @Deprecated
        public Float put(Float f, Float f2) {
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
            return this.put((Float)object, (Float)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableFunction
    extends AbstractFloat2FloatFunction
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Float2FloatFunction function;

        protected UnmodifiableFunction(Float2FloatFunction float2FloatFunction) {
            if (float2FloatFunction == null) {
                throw new NullPointerException();
            }
            this.function = float2FloatFunction;
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
        public boolean containsKey(float f) {
            return this.function.containsKey(f);
        }

        @Override
        public float put(float f, float f2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float get(float f) {
            return this.function.get(f);
        }

        @Override
        public float remove(float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Float put(Float f, Float f2) {
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
            return this.put((Float)object, (Float)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedFunction
    implements Float2FloatFunction,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Float2FloatFunction function;
        protected final Object sync;

        protected SynchronizedFunction(Float2FloatFunction float2FloatFunction, Object object) {
            if (float2FloatFunction == null) {
                throw new NullPointerException();
            }
            this.function = float2FloatFunction;
            this.sync = object;
        }

        protected SynchronizedFunction(Float2FloatFunction float2FloatFunction) {
            if (float2FloatFunction == null) {
                throw new NullPointerException();
            }
            this.function = float2FloatFunction;
            this.sync = this;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
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
        public Float apply(Float f) {
            Object object = this.sync;
            synchronized (object) {
                return (Float)this.function.apply(f);
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
        public boolean containsKey(float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.containsKey(f);
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
        public float put(float f, float f2) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(f, f2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float get(float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.get(f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float remove(float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.remove(f);
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
        public Float put(Float f, Float f2) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(f, f2);
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
            return this.put((Float)object, (Float)object2);
        }

        @Override
        @Deprecated
        public Object apply(Object object) {
            return this.apply((Float)object);
        }
    }

    public static class Singleton
    extends AbstractFloat2FloatFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final float key;
        protected final float value;

        protected Singleton(float f, float f2) {
            this.key = f;
            this.value = f2;
        }

        @Override
        public boolean containsKey(float f) {
            return Float.floatToIntBits(this.key) == Float.floatToIntBits(f);
        }

        @Override
        public float get(float f) {
            return Float.floatToIntBits(this.key) == Float.floatToIntBits(f) ? this.value : this.defRetValue;
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
    extends AbstractFloat2FloatFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyFunction() {
        }

        @Override
        public float get(float f) {
            return 0.0f;
        }

        @Override
        public boolean containsKey(float f) {
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

