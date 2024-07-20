/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByte2ShortFunction;
import it.unimi.dsi.fastutil.bytes.Byte2ShortFunction;
import java.io.Serializable;

public class Byte2ShortFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Byte2ShortFunctions() {
    }

    public static Byte2ShortFunction singleton(byte key, short value) {
        return new Singleton(key, value);
    }

    public static Byte2ShortFunction singleton(Byte key, Short value) {
        return new Singleton(key, value);
    }

    public static Byte2ShortFunction synchronize(Byte2ShortFunction f) {
        return new SynchronizedFunction(f);
    }

    public static Byte2ShortFunction synchronize(Byte2ShortFunction f, Object sync) {
        return new SynchronizedFunction(f, sync);
    }

    public static Byte2ShortFunction unmodifiable(Byte2ShortFunction f) {
        return new UnmodifiableFunction(f);
    }

    public static class UnmodifiableFunction
    extends AbstractByte2ShortFunction
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Byte2ShortFunction function;

        protected UnmodifiableFunction(Byte2ShortFunction f) {
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
        public short defaultReturnValue() {
            return this.function.defaultReturnValue();
        }

        @Override
        public void defaultReturnValue(short defRetValue) {
            throw new UnsupportedOperationException();
        }

        @Override
        public short put(byte k, short v) {
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
        public short remove(byte k) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public short get(byte k) {
            return this.function.get(k);
        }

        @Override
        public boolean containsKey(Object ok) {
            return this.function.containsKey(ok);
        }
    }

    public static class SynchronizedFunction
    extends AbstractByte2ShortFunction
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Byte2ShortFunction function;
        protected final Object sync;

        protected SynchronizedFunction(Byte2ShortFunction f, Object sync) {
            if (f == null) {
                throw new NullPointerException();
            }
            this.function = f;
            this.sync = sync;
        }

        protected SynchronizedFunction(Byte2ShortFunction f) {
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
        public void defaultReturnValue(short defRetValue) {
            Object object = this.sync;
            synchronized (object) {
                this.function.defaultReturnValue(defRetValue);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short put(byte k, short v) {
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
        public Short put(Byte k, Short v) {
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
        public Short get(Object k) {
            Object object = this.sync;
            synchronized (object) {
                return (Short)this.function.get(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Short remove(Object k) {
            Object object = this.sync;
            synchronized (object) {
                return (Short)this.function.remove(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short remove(byte k) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.remove(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short get(byte k) {
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
    extends AbstractByte2ShortFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final byte key;
        protected final short value;

        protected Singleton(byte key, short value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public boolean containsKey(byte k) {
            return this.key == k;
        }

        @Override
        public short get(byte k) {
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
    extends AbstractByte2ShortFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyFunction() {
        }

        @Override
        public short get(byte k) {
            return 0;
        }

        @Override
        public boolean containsKey(byte k) {
            return false;
        }

        @Override
        public short defaultReturnValue() {
            return 0;
        }

        @Override
        public void defaultReturnValue(short defRetValue) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Short get(Object k) {
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

