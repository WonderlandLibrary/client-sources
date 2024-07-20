/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByte2FloatFunction;
import it.unimi.dsi.fastutil.bytes.Byte2FloatFunction;
import java.io.Serializable;

public class Byte2FloatFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Byte2FloatFunctions() {
    }

    public static Byte2FloatFunction singleton(byte key, float value) {
        return new Singleton(key, value);
    }

    public static Byte2FloatFunction singleton(Byte key, Float value) {
        return new Singleton(key, value.floatValue());
    }

    public static Byte2FloatFunction synchronize(Byte2FloatFunction f) {
        return new SynchronizedFunction(f);
    }

    public static Byte2FloatFunction synchronize(Byte2FloatFunction f, Object sync) {
        return new SynchronizedFunction(f, sync);
    }

    public static Byte2FloatFunction unmodifiable(Byte2FloatFunction f) {
        return new UnmodifiableFunction(f);
    }

    public static class UnmodifiableFunction
    extends AbstractByte2FloatFunction
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Byte2FloatFunction function;

        protected UnmodifiableFunction(Byte2FloatFunction f) {
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
        public boolean containsKey(byte k) {
            return this.function.containsKey(k);
        }

        @Override
        public float defaultReturnValue() {
            return this.function.defaultReturnValue();
        }

        @Override
        public void defaultReturnValue(float defRetValue) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float put(byte k, float v) {
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
        public float remove(byte k) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public float get(byte k) {
            return this.function.get(k);
        }

        @Override
        public boolean containsKey(Object ok) {
            return this.function.containsKey(ok);
        }
    }

    public static class SynchronizedFunction
    extends AbstractByte2FloatFunction
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Byte2FloatFunction function;
        protected final Object sync;

        protected SynchronizedFunction(Byte2FloatFunction f, Object sync) {
            if (f == null) {
                throw new NullPointerException();
            }
            this.function = f;
            this.sync = sync;
        }

        protected SynchronizedFunction(Byte2FloatFunction f) {
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
        public boolean containsKey(byte k) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.containsKey(k);
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
        public void defaultReturnValue(float defRetValue) {
            Object object = this.sync;
            synchronized (object) {
                this.function.defaultReturnValue(defRetValue);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float put(byte k, float v) {
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
        public Float put(Byte k, Float v) {
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
        public Float get(Object k) {
            Object object = this.sync;
            synchronized (object) {
                return (Float)this.function.get(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Float remove(Object k) {
            Object object = this.sync;
            synchronized (object) {
                return (Float)this.function.remove(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float remove(byte k) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.remove(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float get(byte k) {
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
    extends AbstractByte2FloatFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final byte key;
        protected final float value;

        protected Singleton(byte key, float value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public boolean containsKey(byte k) {
            return this.key == k;
        }

        @Override
        public float get(byte k) {
            if (this.key == k) {
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
    extends AbstractByte2FloatFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyFunction() {
        }

        @Override
        public float get(byte k) {
            return 0.0f;
        }

        @Override
        public boolean containsKey(byte k) {
            return false;
        }

        @Override
        public float defaultReturnValue() {
            return 0.0f;
        }

        @Override
        public void defaultReturnValue(float defRetValue) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Float get(Object k) {
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

