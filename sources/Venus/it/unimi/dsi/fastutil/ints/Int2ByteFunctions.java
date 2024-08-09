/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.ints.AbstractInt2ByteFunction;
import it.unimi.dsi.fastutil.ints.Int2ByteFunction;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.IntUnaryOperator;

public final class Int2ByteFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Int2ByteFunctions() {
    }

    public static Int2ByteFunction singleton(int n, byte by) {
        return new Singleton(n, by);
    }

    public static Int2ByteFunction singleton(Integer n, Byte by) {
        return new Singleton(n, by);
    }

    public static Int2ByteFunction synchronize(Int2ByteFunction int2ByteFunction) {
        return new SynchronizedFunction(int2ByteFunction);
    }

    public static Int2ByteFunction synchronize(Int2ByteFunction int2ByteFunction, Object object) {
        return new SynchronizedFunction(int2ByteFunction, object);
    }

    public static Int2ByteFunction unmodifiable(Int2ByteFunction int2ByteFunction) {
        return new UnmodifiableFunction(int2ByteFunction);
    }

    public static Int2ByteFunction primitive(java.util.function.Function<? super Integer, ? extends Byte> function) {
        Objects.requireNonNull(function);
        if (function instanceof Int2ByteFunction) {
            return (Int2ByteFunction)function;
        }
        if (function instanceof IntUnaryOperator) {
            return arg_0 -> Int2ByteFunctions.lambda$primitive$0(function, arg_0);
        }
        return new PrimitiveFunction(function);
    }

    private static byte lambda$primitive$0(java.util.function.Function function, int n) {
        return SafeMath.safeIntToByte(((IntUnaryOperator)((Object)function)).applyAsInt(n));
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class PrimitiveFunction
    implements Int2ByteFunction {
        protected final java.util.function.Function<? super Integer, ? extends Byte> function;

        protected PrimitiveFunction(java.util.function.Function<? super Integer, ? extends Byte> function) {
            this.function = function;
        }

        @Override
        public boolean containsKey(int n) {
            return this.function.apply((Integer)n) != null;
        }

        @Override
        @Deprecated
        public boolean containsKey(Object object) {
            if (object == null) {
                return true;
            }
            return this.function.apply((Integer)object) != null;
        }

        @Override
        public byte get(int n) {
            Byte by = this.function.apply((Integer)n);
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
            return this.function.apply((Integer)object);
        }

        @Override
        @Deprecated
        public Byte put(Integer n, Byte by) {
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
            return this.put((Integer)object, (Byte)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableFunction
    extends AbstractInt2ByteFunction
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Int2ByteFunction function;

        protected UnmodifiableFunction(Int2ByteFunction int2ByteFunction) {
            if (int2ByteFunction == null) {
                throw new NullPointerException();
            }
            this.function = int2ByteFunction;
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
        public boolean containsKey(int n) {
            return this.function.containsKey(n);
        }

        @Override
        public byte put(int n, byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte get(int n) {
            return this.function.get(n);
        }

        @Override
        public byte remove(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Byte put(Integer n, Byte by) {
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
            return this.put((Integer)object, (Byte)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedFunction
    implements Int2ByteFunction,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Int2ByteFunction function;
        protected final Object sync;

        protected SynchronizedFunction(Int2ByteFunction int2ByteFunction, Object object) {
            if (int2ByteFunction == null) {
                throw new NullPointerException();
            }
            this.function = int2ByteFunction;
            this.sync = object;
        }

        protected SynchronizedFunction(Int2ByteFunction int2ByteFunction) {
            if (int2ByteFunction == null) {
                throw new NullPointerException();
            }
            this.function = int2ByteFunction;
            this.sync = this;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int applyAsInt(int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.applyAsInt(n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Byte apply(Integer n) {
            Object object = this.sync;
            synchronized (object) {
                return (Byte)this.function.apply(n);
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
        public boolean containsKey(int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.containsKey(n);
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
        public byte put(int n, byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(n, by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte get(int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.get(n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte remove(int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.remove(n);
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
        public Byte put(Integer n, Byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(n, by);
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
            return this.put((Integer)object, (Byte)object2);
        }

        @Override
        @Deprecated
        public Object apply(Object object) {
            return this.apply((Integer)object);
        }
    }

    public static class Singleton
    extends AbstractInt2ByteFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final int key;
        protected final byte value;

        protected Singleton(int n, byte by) {
            this.key = n;
            this.value = by;
        }

        @Override
        public boolean containsKey(int n) {
            return this.key == n;
        }

        @Override
        public byte get(int n) {
            return this.key == n ? this.value : this.defRetValue;
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
    extends AbstractInt2ByteFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyFunction() {
        }

        @Override
        public byte get(int n) {
            return 1;
        }

        @Override
        public boolean containsKey(int n) {
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

