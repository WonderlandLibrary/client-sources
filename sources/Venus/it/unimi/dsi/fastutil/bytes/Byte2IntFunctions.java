/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.bytes.AbstractByte2IntFunction;
import it.unimi.dsi.fastutil.bytes.Byte2IntFunction;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.IntUnaryOperator;

public final class Byte2IntFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Byte2IntFunctions() {
    }

    public static Byte2IntFunction singleton(byte by, int n) {
        return new Singleton(by, n);
    }

    public static Byte2IntFunction singleton(Byte by, Integer n) {
        return new Singleton(by, n);
    }

    public static Byte2IntFunction synchronize(Byte2IntFunction byte2IntFunction) {
        return new SynchronizedFunction(byte2IntFunction);
    }

    public static Byte2IntFunction synchronize(Byte2IntFunction byte2IntFunction, Object object) {
        return new SynchronizedFunction(byte2IntFunction, object);
    }

    public static Byte2IntFunction unmodifiable(Byte2IntFunction byte2IntFunction) {
        return new UnmodifiableFunction(byte2IntFunction);
    }

    public static Byte2IntFunction primitive(java.util.function.Function<? super Byte, ? extends Integer> function) {
        Objects.requireNonNull(function);
        if (function instanceof Byte2IntFunction) {
            return (Byte2IntFunction)function;
        }
        if (function instanceof IntUnaryOperator) {
            return ((IntUnaryOperator)((Object)function))::applyAsInt;
        }
        return new PrimitiveFunction(function);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class PrimitiveFunction
    implements Byte2IntFunction {
        protected final java.util.function.Function<? super Byte, ? extends Integer> function;

        protected PrimitiveFunction(java.util.function.Function<? super Byte, ? extends Integer> function) {
            this.function = function;
        }

        @Override
        public boolean containsKey(byte by) {
            return this.function.apply((Byte)by) != null;
        }

        @Override
        @Deprecated
        public boolean containsKey(Object object) {
            if (object == null) {
                return true;
            }
            return this.function.apply((Byte)object) != null;
        }

        @Override
        public int get(byte by) {
            Integer n = this.function.apply((Byte)by);
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
            return this.function.apply((Byte)object);
        }

        @Override
        @Deprecated
        public Integer put(Byte by, Integer n) {
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
            return this.put((Byte)object, (Integer)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableFunction
    extends AbstractByte2IntFunction
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Byte2IntFunction function;

        protected UnmodifiableFunction(Byte2IntFunction byte2IntFunction) {
            if (byte2IntFunction == null) {
                throw new NullPointerException();
            }
            this.function = byte2IntFunction;
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
        public boolean containsKey(byte by) {
            return this.function.containsKey(by);
        }

        @Override
        public int put(byte by, int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int get(byte by) {
            return this.function.get(by);
        }

        @Override
        public int remove(byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Integer put(Byte by, Integer n) {
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
            return this.put((Byte)object, (Integer)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedFunction
    implements Byte2IntFunction,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Byte2IntFunction function;
        protected final Object sync;

        protected SynchronizedFunction(Byte2IntFunction byte2IntFunction, Object object) {
            if (byte2IntFunction == null) {
                throw new NullPointerException();
            }
            this.function = byte2IntFunction;
            this.sync = object;
        }

        protected SynchronizedFunction(Byte2IntFunction byte2IntFunction) {
            if (byte2IntFunction == null) {
                throw new NullPointerException();
            }
            this.function = byte2IntFunction;
            this.sync = this;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
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
        public Integer apply(Byte by) {
            Object object = this.sync;
            synchronized (object) {
                return (Integer)this.function.apply(by);
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
        public boolean containsKey(byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.containsKey(by);
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
        public int put(byte by, int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(by, n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int get(byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.get(by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int remove(byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.remove(by);
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
        public Integer put(Byte by, Integer n) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(by, n);
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
            return this.put((Byte)object, (Integer)object2);
        }

        @Override
        @Deprecated
        public Object apply(Object object) {
            return this.apply((Byte)object);
        }
    }

    public static class Singleton
    extends AbstractByte2IntFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final byte key;
        protected final int value;

        protected Singleton(byte by, int n) {
            this.key = by;
            this.value = n;
        }

        @Override
        public boolean containsKey(byte by) {
            return this.key == by;
        }

        @Override
        public int get(byte by) {
            return this.key == by ? this.value : this.defRetValue;
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
    extends AbstractByte2IntFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyFunction() {
        }

        @Override
        public int get(byte by) {
            return 1;
        }

        @Override
        public boolean containsKey(byte by) {
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

