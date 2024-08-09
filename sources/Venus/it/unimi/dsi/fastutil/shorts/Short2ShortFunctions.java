/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.shorts.AbstractShort2ShortFunction;
import it.unimi.dsi.fastutil.shorts.Short2ShortFunction;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.IntUnaryOperator;

public final class Short2ShortFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Short2ShortFunctions() {
    }

    public static Short2ShortFunction singleton(short s, short s2) {
        return new Singleton(s, s2);
    }

    public static Short2ShortFunction singleton(Short s, Short s2) {
        return new Singleton(s, s2);
    }

    public static Short2ShortFunction synchronize(Short2ShortFunction short2ShortFunction) {
        return new SynchronizedFunction(short2ShortFunction);
    }

    public static Short2ShortFunction synchronize(Short2ShortFunction short2ShortFunction, Object object) {
        return new SynchronizedFunction(short2ShortFunction, object);
    }

    public static Short2ShortFunction unmodifiable(Short2ShortFunction short2ShortFunction) {
        return new UnmodifiableFunction(short2ShortFunction);
    }

    public static Short2ShortFunction primitive(java.util.function.Function<? super Short, ? extends Short> function) {
        Objects.requireNonNull(function);
        if (function instanceof Short2ShortFunction) {
            return (Short2ShortFunction)function;
        }
        if (function instanceof IntUnaryOperator) {
            return arg_0 -> Short2ShortFunctions.lambda$primitive$0(function, arg_0);
        }
        return new PrimitiveFunction(function);
    }

    private static short lambda$primitive$0(java.util.function.Function function, short s) {
        return SafeMath.safeIntToShort(((IntUnaryOperator)((Object)function)).applyAsInt(s));
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class PrimitiveFunction
    implements Short2ShortFunction {
        protected final java.util.function.Function<? super Short, ? extends Short> function;

        protected PrimitiveFunction(java.util.function.Function<? super Short, ? extends Short> function) {
            this.function = function;
        }

        @Override
        public boolean containsKey(short s) {
            return this.function.apply((Short)s) != null;
        }

        @Override
        @Deprecated
        public boolean containsKey(Object object) {
            if (object == null) {
                return true;
            }
            return this.function.apply((Short)object) != null;
        }

        @Override
        public short get(short s) {
            Short s2 = this.function.apply((Short)s);
            if (s2 == null) {
                return this.defaultReturnValue();
            }
            return s2;
        }

        @Override
        @Deprecated
        public Short get(Object object) {
            if (object == null) {
                return null;
            }
            return this.function.apply((Short)object);
        }

        @Override
        @Deprecated
        public Short put(Short s, Short s2) {
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
            return this.put((Short)object, (Short)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableFunction
    extends AbstractShort2ShortFunction
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Short2ShortFunction function;

        protected UnmodifiableFunction(Short2ShortFunction short2ShortFunction) {
            if (short2ShortFunction == null) {
                throw new NullPointerException();
            }
            this.function = short2ShortFunction;
        }

        @Override
        public int size() {
            return this.function.size();
        }

        @Override
        public short defaultReturnValue() {
            return this.function.defaultReturnValue();
        }

        @Override
        public void defaultReturnValue(short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean containsKey(short s) {
            return this.function.containsKey(s);
        }

        @Override
        public short put(short s, short s2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public short get(short s) {
            return this.function.get(s);
        }

        @Override
        public short remove(short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Short put(Short s, Short s2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Short get(Object object) {
            return this.function.get(object);
        }

        @Override
        @Deprecated
        public Short remove(Object object) {
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
            return this.put((Short)object, (Short)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedFunction
    implements Short2ShortFunction,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Short2ShortFunction function;
        protected final Object sync;

        protected SynchronizedFunction(Short2ShortFunction short2ShortFunction, Object object) {
            if (short2ShortFunction == null) {
                throw new NullPointerException();
            }
            this.function = short2ShortFunction;
            this.sync = object;
        }

        protected SynchronizedFunction(Short2ShortFunction short2ShortFunction) {
            if (short2ShortFunction == null) {
                throw new NullPointerException();
            }
            this.function = short2ShortFunction;
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
        public Short apply(Short s) {
            Object object = this.sync;
            synchronized (object) {
                return (Short)this.function.apply(s);
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
        public void defaultReturnValue(short s) {
            Object object = this.sync;
            synchronized (object) {
                this.function.defaultReturnValue(s);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsKey(short s) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.containsKey(s);
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
        public short put(short s, short s2) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(s, s2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short get(short s) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.get(s);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short remove(short s) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.remove(s);
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
        public Short put(Short s, Short s2) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(s, s2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Short get(Object object) {
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
        public Short remove(Object object) {
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
            return this.put((Short)object, (Short)object2);
        }

        @Override
        @Deprecated
        public Object apply(Object object) {
            return this.apply((Short)object);
        }
    }

    public static class Singleton
    extends AbstractShort2ShortFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final short key;
        protected final short value;

        protected Singleton(short s, short s2) {
            this.key = s;
            this.value = s2;
        }

        @Override
        public boolean containsKey(short s) {
            return this.key == s;
        }

        @Override
        public short get(short s) {
            return this.key == s ? this.value : this.defRetValue;
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
    extends AbstractShort2ShortFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyFunction() {
        }

        @Override
        public short get(short s) {
            return 1;
        }

        @Override
        public boolean containsKey(short s) {
            return true;
        }

        @Override
        public short defaultReturnValue() {
            return 1;
        }

        @Override
        public void defaultReturnValue(short s) {
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

