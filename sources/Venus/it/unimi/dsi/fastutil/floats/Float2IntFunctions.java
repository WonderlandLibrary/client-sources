/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.floats.AbstractFloat2IntFunction;
import it.unimi.dsi.fastutil.floats.Float2IntFunction;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.DoubleToIntFunction;

public final class Float2IntFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Float2IntFunctions() {
    }

    public static Float2IntFunction singleton(float f, int n) {
        return new Singleton(f, n);
    }

    public static Float2IntFunction singleton(Float f, Integer n) {
        return new Singleton(f.floatValue(), n);
    }

    public static Float2IntFunction synchronize(Float2IntFunction float2IntFunction) {
        return new SynchronizedFunction(float2IntFunction);
    }

    public static Float2IntFunction synchronize(Float2IntFunction float2IntFunction, Object object) {
        return new SynchronizedFunction(float2IntFunction, object);
    }

    public static Float2IntFunction unmodifiable(Float2IntFunction float2IntFunction) {
        return new UnmodifiableFunction(float2IntFunction);
    }

    public static Float2IntFunction primitive(java.util.function.Function<? super Float, ? extends Integer> function) {
        Objects.requireNonNull(function);
        if (function instanceof Float2IntFunction) {
            return (Float2IntFunction)function;
        }
        if (function instanceof DoubleToIntFunction) {
            return ((DoubleToIntFunction)((Object)function))::applyAsInt;
        }
        return new PrimitiveFunction(function);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class PrimitiveFunction
    implements Float2IntFunction {
        protected final java.util.function.Function<? super Float, ? extends Integer> function;

        protected PrimitiveFunction(java.util.function.Function<? super Float, ? extends Integer> function) {
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
        public int get(float f) {
            Integer n = this.function.apply(Float.valueOf(f));
            if (n == null) {
                return this.defaultReturnValue();
            }
            return n;
        }

        @Override
        @Deprecated
        public Integer get(Object object) {
            if (object == null) {
                return null;
            }
            return this.function.apply((Float)object);
        }

        @Override
        @Deprecated
        public Integer put(Float f, Integer n) {
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
            return this.put((Float)object, (Integer)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableFunction
    extends AbstractFloat2IntFunction
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Float2IntFunction function;

        protected UnmodifiableFunction(Float2IntFunction float2IntFunction) {
            if (float2IntFunction == null) {
                throw new NullPointerException();
            }
            this.function = float2IntFunction;
        }

        @Override
        public int size() {
            return this.function.size();
        }

        @Override
        public int defaultReturnValue() {
            return this.function.defaultReturnValue();
        }

        @Override
        public void defaultReturnValue(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean containsKey(float f) {
            return this.function.containsKey(f);
        }

        @Override
        public int put(float f, int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int get(float f) {
            return this.function.get(f);
        }

        @Override
        public int remove(float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Integer put(Float f, Integer n) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Integer get(Object object) {
            return this.function.get(object);
        }

        @Override
        @Deprecated
        public Integer remove(Object object) {
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
            return this.put((Float)object, (Integer)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedFunction
    implements Float2IntFunction,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Float2IntFunction function;
        protected final Object sync;

        protected SynchronizedFunction(Float2IntFunction float2IntFunction, Object object) {
            if (float2IntFunction == null) {
                throw new NullPointerException();
            }
            this.function = float2IntFunction;
            this.sync = object;
        }

        protected SynchronizedFunction(Float2IntFunction float2IntFunction) {
            if (float2IntFunction == null) {
                throw new NullPointerException();
            }
            this.function = float2IntFunction;
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
        public Integer apply(Float f) {
            Object object = this.sync;
            synchronized (object) {
                return (Integer)this.function.apply(f);
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
        public void defaultReturnValue(int n) {
            Object object = this.sync;
            synchronized (object) {
                this.function.defaultReturnValue(n);
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
        public int put(float f, int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(f, n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int get(float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.get(f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int remove(float f) {
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
        public Integer put(Float f, Integer n) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(f, n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Integer get(Object object) {
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
        public Integer remove(Object object) {
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
            return this.put((Float)object, (Integer)object2);
        }

        @Override
        @Deprecated
        public Object apply(Object object) {
            return this.apply((Float)object);
        }
    }

    public static class Singleton
    extends AbstractFloat2IntFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final float key;
        protected final int value;

        protected Singleton(float f, int n) {
            this.key = f;
            this.value = n;
        }

        @Override
        public boolean containsKey(float f) {
            return Float.floatToIntBits(this.key) == Float.floatToIntBits(f);
        }

        @Override
        public int get(float f) {
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
    extends AbstractFloat2IntFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyFunction() {
        }

        @Override
        public int get(float f) {
            return 1;
        }

        @Override
        public boolean containsKey(float f) {
            return true;
        }

        @Override
        public int defaultReturnValue() {
            return 1;
        }

        @Override
        public void defaultReturnValue(int n) {
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

