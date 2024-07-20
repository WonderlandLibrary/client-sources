/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.AbstractFloat2DoubleFunction;
import it.unimi.dsi.fastutil.floats.Float2DoubleFunction;
import java.io.Serializable;

public class Float2DoubleFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Float2DoubleFunctions() {
    }

    public static Float2DoubleFunction singleton(float key, double value) {
        return new Singleton(key, value);
    }

    public static Float2DoubleFunction singleton(Float key, Double value) {
        return new Singleton(key.floatValue(), value);
    }

    public static Float2DoubleFunction synchronize(Float2DoubleFunction f) {
        return new SynchronizedFunction(f);
    }

    public static Float2DoubleFunction synchronize(Float2DoubleFunction f, Object sync) {
        return new SynchronizedFunction(f, sync);
    }

    public static Float2DoubleFunction unmodifiable(Float2DoubleFunction f) {
        return new UnmodifiableFunction(f);
    }

    public static class UnmodifiableFunction
    extends AbstractFloat2DoubleFunction
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Float2DoubleFunction function;

        protected UnmodifiableFunction(Float2DoubleFunction f) {
            if (f == null) {
                throw new NullPointerException();
            }
            this.function = f;
        }

        @Override
        public int size() {
            return this.function.size();
        }

        @Override
        public boolean containsKey(float k) {
            return this.function.containsKey(k);
        }

        @Override
        public double defaultReturnValue() {
            return this.function.defaultReturnValue();
        }

        @Override
        public void defaultReturnValue(double defRetValue) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double put(float k, double v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        public String toString() {
            return this.function.toString();
        }

        @Override
        @Deprecated
        public double remove(float k) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public double get(float k) {
            return this.function.get(k);
        }

        @Override
        public boolean containsKey(Object ok) {
            return this.function.containsKey(ok);
        }
    }

    public static class SynchronizedFunction
    extends AbstractFloat2DoubleFunction
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Float2DoubleFunction function;
        protected final Object sync;

        protected SynchronizedFunction(Float2DoubleFunction f, Object sync) {
            if (f == null) {
                throw new NullPointerException();
            }
            this.function = f;
            this.sync = sync;
        }

        protected SynchronizedFunction(Float2DoubleFunction f) {
            if (f == null) {
                throw new NullPointerException();
            }
            this.function = f;
            this.sync = this;
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
        public boolean containsKey(float k) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.containsKey(k);
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
        public void defaultReturnValue(double defRetValue) {
            Object object = this.sync;
            synchronized (object) {
                this.function.defaultReturnValue(defRetValue);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double put(float k, double v) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(k, v);
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
        public String toString() {
            Object object = this.sync;
            synchronized (object) {
                return this.function.toString();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Double put(Float k, Double v) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(k, v);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Double get(Object k) {
            Object object = this.sync;
            synchronized (object) {
                return (Double)this.function.get(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Double remove(Object k) {
            Object object = this.sync;
            synchronized (object) {
                return (Double)this.function.remove(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double remove(float k) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.remove(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double get(float k) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.get(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsKey(Object ok) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.containsKey(ok);
            }
        }
    }

    public static class Singleton
    extends AbstractFloat2DoubleFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final float key;
        protected final double value;

        protected Singleton(float key, double value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public boolean containsKey(float k) {
            return Float.floatToIntBits(this.key) == Float.floatToIntBits(k);
        }

        @Override
        public double get(float k) {
            if (Float.floatToIntBits(this.key) == Float.floatToIntBits(k)) {
                return this.value;
            }
            return this.defRetValue;
        }

        @Override
        public int size() {
            return 1;
        }

        public Object clone() {
            return this;
        }
    }

    public static class EmptyFunction
    extends AbstractFloat2DoubleFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyFunction() {
        }

        @Override
        public double get(float k) {
            return 0.0;
        }

        @Override
        public boolean containsKey(float k) {
            return false;
        }

        @Override
        public double defaultReturnValue() {
            return 0.0;
        }

        @Override
        public void defaultReturnValue(double defRetValue) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Double get(Object k) {
            return null;
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public void clear() {
        }

        private Object readResolve() {
            return EMPTY_FUNCTION;
        }

        public Object clone() {
            return EMPTY_FUNCTION;
        }
    }
}

