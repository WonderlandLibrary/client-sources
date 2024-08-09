/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.longs.AbstractLong2FloatFunction;
import it.unimi.dsi.fastutil.longs.Long2FloatFunction;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.LongToDoubleFunction;

public final class Long2FloatFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Long2FloatFunctions() {
    }

    public static Long2FloatFunction singleton(long l, float f) {
        return new Singleton(l, f);
    }

    public static Long2FloatFunction singleton(Long l, Float f) {
        return new Singleton(l, f.floatValue());
    }

    public static Long2FloatFunction synchronize(Long2FloatFunction long2FloatFunction) {
        return new SynchronizedFunction(long2FloatFunction);
    }

    public static Long2FloatFunction synchronize(Long2FloatFunction long2FloatFunction, Object object) {
        return new SynchronizedFunction(long2FloatFunction, object);
    }

    public static Long2FloatFunction unmodifiable(Long2FloatFunction long2FloatFunction) {
        return new UnmodifiableFunction(long2FloatFunction);
    }

    public static Long2FloatFunction primitive(java.util.function.Function<? super Long, ? extends Float> function) {
        Objects.requireNonNull(function);
        if (function instanceof Long2FloatFunction) {
            return (Long2FloatFunction)function;
        }
        if (function instanceof LongToDoubleFunction) {
            return arg_0 -> Long2FloatFunctions.lambda$primitive$0(function, arg_0);
        }
        return new PrimitiveFunction(function);
    }

    private static float lambda$primitive$0(java.util.function.Function function, long l) {
        return SafeMath.safeDoubleToFloat(((LongToDoubleFunction)((Object)function)).applyAsDouble(l));
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class PrimitiveFunction
    implements Long2FloatFunction {
        protected final java.util.function.Function<? super Long, ? extends Float> function;

        protected PrimitiveFunction(java.util.function.Function<? super Long, ? extends Float> function) {
            this.function = function;
        }

        @Override
        public boolean containsKey(long l) {
            return this.function.apply((Long)l) != null;
        }

        @Override
        @Deprecated
        public boolean containsKey(Object object) {
            if (object == null) {
                return true;
            }
            return this.function.apply((Long)object) != null;
        }

        @Override
        public float get(long l) {
            Float f = this.function.apply((Long)l);
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
            return this.function.apply((Long)object);
        }

        @Override
        @Deprecated
        public Float put(Long l, Float f) {
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
            return this.put((Long)object, (Float)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableFunction
    extends AbstractLong2FloatFunction
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Long2FloatFunction function;

        protected UnmodifiableFunction(Long2FloatFunction long2FloatFunction) {
            if (long2FloatFunction == null) {
                throw new NullPointerException();
            }
            this.function = long2FloatFunction;
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
        public boolean containsKey(long l) {
            return this.function.containsKey(l);
        }

        @Override
        public float put(long l, float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float get(long l) {
            return this.function.get(l);
        }

        @Override
        public float remove(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Float put(Long l, Float f) {
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
            return this.put((Long)object, (Float)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedFunction
    implements Long2FloatFunction,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Long2FloatFunction function;
        protected final Object sync;

        protected SynchronizedFunction(Long2FloatFunction long2FloatFunction, Object object) {
            if (long2FloatFunction == null) {
                throw new NullPointerException();
            }
            this.function = long2FloatFunction;
            this.sync = object;
        }

        protected SynchronizedFunction(Long2FloatFunction long2FloatFunction) {
            if (long2FloatFunction == null) {
                throw new NullPointerException();
            }
            this.function = long2FloatFunction;
            this.sync = this;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double applyAsDouble(long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.applyAsDouble(l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Float apply(Long l) {
            Object object = this.sync;
            synchronized (object) {
                return (Float)this.function.apply(l);
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
        public boolean containsKey(long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.containsKey(l);
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
        public float put(long l, float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(l, f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float get(long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.get(l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float remove(long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.remove(l);
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
        public Float put(Long l, Float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(l, f);
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
            return this.put((Long)object, (Float)object2);
        }

        @Override
        @Deprecated
        public Object apply(Object object) {
            return this.apply((Long)object);
        }
    }

    public static class Singleton
    extends AbstractLong2FloatFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final long key;
        protected final float value;

        protected Singleton(long l, float f) {
            this.key = l;
            this.value = f;
        }

        @Override
        public boolean containsKey(long l) {
            return this.key == l;
        }

        @Override
        public float get(long l) {
            return this.key == l ? this.value : this.defRetValue;
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
    extends AbstractLong2FloatFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyFunction() {
        }

        @Override
        public float get(long l) {
            return 0.0f;
        }

        @Override
        public boolean containsKey(long l) {
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

