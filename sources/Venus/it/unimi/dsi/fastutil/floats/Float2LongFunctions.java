/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.floats.AbstractFloat2LongFunction;
import it.unimi.dsi.fastutil.floats.Float2LongFunction;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.DoubleToLongFunction;

public final class Float2LongFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Float2LongFunctions() {
    }

    public static Float2LongFunction singleton(float f, long l) {
        return new Singleton(f, l);
    }

    public static Float2LongFunction singleton(Float f, Long l) {
        return new Singleton(f.floatValue(), l);
    }

    public static Float2LongFunction synchronize(Float2LongFunction float2LongFunction) {
        return new SynchronizedFunction(float2LongFunction);
    }

    public static Float2LongFunction synchronize(Float2LongFunction float2LongFunction, Object object) {
        return new SynchronizedFunction(float2LongFunction, object);
    }

    public static Float2LongFunction unmodifiable(Float2LongFunction float2LongFunction) {
        return new UnmodifiableFunction(float2LongFunction);
    }

    public static Float2LongFunction primitive(java.util.function.Function<? super Float, ? extends Long> function) {
        Objects.requireNonNull(function);
        if (function instanceof Float2LongFunction) {
            return (Float2LongFunction)function;
        }
        if (function instanceof DoubleToLongFunction) {
            return ((DoubleToLongFunction)((Object)function))::applyAsLong;
        }
        return new PrimitiveFunction(function);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class PrimitiveFunction
    implements Float2LongFunction {
        protected final java.util.function.Function<? super Float, ? extends Long> function;

        protected PrimitiveFunction(java.util.function.Function<? super Float, ? extends Long> function) {
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
        public long get(float f) {
            Long l = this.function.apply(Float.valueOf(f));
            if (l == null) {
                return this.defaultReturnValue();
            }
            return l;
        }

        @Override
        @Deprecated
        public Long get(Object object) {
            if (object == null) {
                return null;
            }
            return this.function.apply((Float)object);
        }

        @Override
        @Deprecated
        public Long put(Float f, Long l) {
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
            return this.put((Float)object, (Long)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableFunction
    extends AbstractFloat2LongFunction
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Float2LongFunction function;

        protected UnmodifiableFunction(Float2LongFunction float2LongFunction) {
            if (float2LongFunction == null) {
                throw new NullPointerException();
            }
            this.function = float2LongFunction;
        }

        @Override
        public int size() {
            return this.function.size();
        }

        @Override
        public long defaultReturnValue() {
            return this.function.defaultReturnValue();
        }

        @Override
        public void defaultReturnValue(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean containsKey(float f) {
            return this.function.containsKey(f);
        }

        @Override
        public long put(float f, long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long get(float f) {
            return this.function.get(f);
        }

        @Override
        public long remove(float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Long put(Float f, Long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Long get(Object object) {
            return this.function.get(object);
        }

        @Override
        @Deprecated
        public Long remove(Object object) {
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
            return this.put((Float)object, (Long)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedFunction
    implements Float2LongFunction,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Float2LongFunction function;
        protected final Object sync;

        protected SynchronizedFunction(Float2LongFunction float2LongFunction, Object object) {
            if (float2LongFunction == null) {
                throw new NullPointerException();
            }
            this.function = float2LongFunction;
            this.sync = object;
        }

        protected SynchronizedFunction(Float2LongFunction float2LongFunction) {
            if (float2LongFunction == null) {
                throw new NullPointerException();
            }
            this.function = float2LongFunction;
            this.sync = this;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public long applyAsLong(double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.applyAsLong(d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Long apply(Float f) {
            Object object = this.sync;
            synchronized (object) {
                return (Long)this.function.apply(f);
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
        public long defaultReturnValue() {
            Object object = this.sync;
            synchronized (object) {
                return this.function.defaultReturnValue();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void defaultReturnValue(long l) {
            Object object = this.sync;
            synchronized (object) {
                this.function.defaultReturnValue(l);
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
        public long put(float f, long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(f, l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long get(float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.get(f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long remove(float f) {
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
        public Long put(Float f, Long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(f, l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Long get(Object object) {
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
        public Long remove(Object object) {
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
            return this.put((Float)object, (Long)object2);
        }

        @Override
        @Deprecated
        public Object apply(Object object) {
            return this.apply((Float)object);
        }
    }

    public static class Singleton
    extends AbstractFloat2LongFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final float key;
        protected final long value;

        protected Singleton(float f, long l) {
            this.key = f;
            this.value = l;
        }

        @Override
        public boolean containsKey(float f) {
            return Float.floatToIntBits(this.key) == Float.floatToIntBits(f);
        }

        @Override
        public long get(float f) {
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
    extends AbstractFloat2LongFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyFunction() {
        }

        @Override
        public long get(float f) {
            return 0L;
        }

        @Override
        public boolean containsKey(float f) {
            return true;
        }

        @Override
        public long defaultReturnValue() {
            return 0L;
        }

        @Override
        public void defaultReturnValue(long l) {
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

