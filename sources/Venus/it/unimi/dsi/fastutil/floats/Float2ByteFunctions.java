/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.floats.AbstractFloat2ByteFunction;
import it.unimi.dsi.fastutil.floats.Float2ByteFunction;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.DoubleToIntFunction;

public final class Float2ByteFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Float2ByteFunctions() {
    }

    public static Float2ByteFunction singleton(float f, byte by) {
        return new Singleton(f, by);
    }

    public static Float2ByteFunction singleton(Float f, Byte by) {
        return new Singleton(f.floatValue(), by);
    }

    public static Float2ByteFunction synchronize(Float2ByteFunction float2ByteFunction) {
        return new SynchronizedFunction(float2ByteFunction);
    }

    public static Float2ByteFunction synchronize(Float2ByteFunction float2ByteFunction, Object object) {
        return new SynchronizedFunction(float2ByteFunction, object);
    }

    public static Float2ByteFunction unmodifiable(Float2ByteFunction float2ByteFunction) {
        return new UnmodifiableFunction(float2ByteFunction);
    }

    public static Float2ByteFunction primitive(java.util.function.Function<? super Float, ? extends Byte> function) {
        Objects.requireNonNull(function);
        if (function instanceof Float2ByteFunction) {
            return (Float2ByteFunction)function;
        }
        if (function instanceof DoubleToIntFunction) {
            return arg_0 -> Float2ByteFunctions.lambda$primitive$0(function, arg_0);
        }
        return new PrimitiveFunction(function);
    }

    private static byte lambda$primitive$0(java.util.function.Function function, float f) {
        return SafeMath.safeIntToByte(((DoubleToIntFunction)((Object)function)).applyAsInt(f));
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class PrimitiveFunction
    implements Float2ByteFunction {
        protected final java.util.function.Function<? super Float, ? extends Byte> function;

        protected PrimitiveFunction(java.util.function.Function<? super Float, ? extends Byte> function) {
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
        public byte get(float f) {
            Byte by = this.function.apply(Float.valueOf(f));
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
            return this.function.apply((Float)object);
        }

        @Override
        @Deprecated
        public Byte put(Float f, Byte by) {
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
            return this.put((Float)object, (Byte)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableFunction
    extends AbstractFloat2ByteFunction
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Float2ByteFunction function;

        protected UnmodifiableFunction(Float2ByteFunction float2ByteFunction) {
            if (float2ByteFunction == null) {
                throw new NullPointerException();
            }
            this.function = float2ByteFunction;
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
        public boolean containsKey(float f) {
            return this.function.containsKey(f);
        }

        @Override
        public byte put(float f, byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte get(float f) {
            return this.function.get(f);
        }

        @Override
        public byte remove(float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Byte put(Float f, Byte by) {
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
            return this.put((Float)object, (Byte)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedFunction
    implements Float2ByteFunction,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Float2ByteFunction function;
        protected final Object sync;

        protected SynchronizedFunction(Float2ByteFunction float2ByteFunction, Object object) {
            if (float2ByteFunction == null) {
                throw new NullPointerException();
            }
            this.function = float2ByteFunction;
            this.sync = object;
        }

        protected SynchronizedFunction(Float2ByteFunction float2ByteFunction) {
            if (float2ByteFunction == null) {
                throw new NullPointerException();
            }
            this.function = float2ByteFunction;
            this.sync = this;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
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
        public Byte apply(Float f) {
            Object object = this.sync;
            synchronized (object) {
                return (Byte)this.function.apply(f);
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
        public byte put(float f, byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(f, by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte get(float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.get(f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte remove(float f) {
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
        public Byte put(Float f, Byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(f, by);
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
            return this.put((Float)object, (Byte)object2);
        }

        @Override
        @Deprecated
        public Object apply(Object object) {
            return this.apply((Float)object);
        }
    }

    public static class Singleton
    extends AbstractFloat2ByteFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final float key;
        protected final byte value;

        protected Singleton(float f, byte by) {
            this.key = f;
            this.value = by;
        }

        @Override
        public boolean containsKey(float f) {
            return Float.floatToIntBits(this.key) == Float.floatToIntBits(f);
        }

        @Override
        public byte get(float f) {
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
    extends AbstractFloat2ByteFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyFunction() {
        }

        @Override
        public byte get(float f) {
            return 1;
        }

        @Override
        public boolean containsKey(float f) {
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

