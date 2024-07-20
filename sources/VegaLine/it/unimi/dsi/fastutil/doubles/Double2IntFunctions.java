/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.AbstractDouble2IntFunction;
import it.unimi.dsi.fastutil.doubles.Double2IntFunction;
import java.io.Serializable;

public class Double2IntFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Double2IntFunctions() {
    }

    public static Double2IntFunction singleton(double key, int value) {
        return new Singleton(key, value);
    }

    public static Double2IntFunction singleton(Double key, Integer value) {
        return new Singleton(key, value);
    }

    public static Double2IntFunction synchronize(Double2IntFunction f) {
        return new SynchronizedFunction(f);
    }

    public static Double2IntFunction synchronize(Double2IntFunction f, Object sync) {
        return new SynchronizedFunction(f, sync);
    }

    public static Double2IntFunction unmodifiable(Double2IntFunction f) {
        return new UnmodifiableFunction(f);
    }

    public static class UnmodifiableFunction
    extends AbstractDouble2IntFunction
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Double2IntFunction function;

        protected UnmodifiableFunction(Double2IntFunction f) {
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
        public boolean containsKey(double k) {
            return this.function.containsKey(k);
        }

        @Override
        public int defaultReturnValue() {
            return this.function.defaultReturnValue();
        }

        @Override
        public void defaultReturnValue(int defRetValue) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int put(double k, int v) {
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
        public int remove(double k) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public int get(double k) {
            return this.function.get(k);
        }

        @Override
        public boolean containsKey(Object ok) {
            return this.function.containsKey(ok);
        }
    }

    public static class SynchronizedFunction
    extends AbstractDouble2IntFunction
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Double2IntFunction function;
        protected final Object sync;

        protected SynchronizedFunction(Double2IntFunction f, Object sync) {
            if (f == null) {
                throw new NullPointerException();
            }
            this.function = f;
            this.sync = sync;
        }

        protected SynchronizedFunction(Double2IntFunction f) {
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
        public boolean containsKey(double k) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.containsKey(k);
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
        public void defaultReturnValue(int defRetValue) {
            Object object = this.sync;
            synchronized (object) {
                this.function.defaultReturnValue(defRetValue);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int put(double k, int v) {
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
        public Integer put(Double k, Integer v) {
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
        public Integer get(Object k) {
            Object object = this.sync;
            synchronized (object) {
                return (Integer)this.function.get(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Integer remove(Object k) {
            Object object = this.sync;
            synchronized (object) {
                return (Integer)this.function.remove(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int remove(double k) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.remove(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int get(double k) {
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
    extends AbstractDouble2IntFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final double key;
        protected final int value;

        protected Singleton(double key, int value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public boolean containsKey(double k) {
            return Double.doubleToLongBits(this.key) == Double.doubleToLongBits(k);
        }

        @Override
        public int get(double k) {
            if (Double.doubleToLongBits(this.key) == Double.doubleToLongBits(k)) {
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
    extends AbstractDouble2IntFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyFunction() {
        }

        @Override
        public int get(double k) {
            return 0;
        }

        @Override
        public boolean containsKey(double k) {
            return false;
        }

        @Override
        public int defaultReturnValue() {
            return 0;
        }

        @Override
        public void defaultReturnValue(int defRetValue) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Integer get(Object k) {
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

