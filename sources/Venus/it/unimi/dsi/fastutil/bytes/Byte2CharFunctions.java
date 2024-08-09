/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.bytes.AbstractByte2CharFunction;
import it.unimi.dsi.fastutil.bytes.Byte2CharFunction;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.IntUnaryOperator;

public final class Byte2CharFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Byte2CharFunctions() {
    }

    public static Byte2CharFunction singleton(byte by, char c) {
        return new Singleton(by, c);
    }

    public static Byte2CharFunction singleton(Byte by, Character c) {
        return new Singleton(by, c.charValue());
    }

    public static Byte2CharFunction synchronize(Byte2CharFunction byte2CharFunction) {
        return new SynchronizedFunction(byte2CharFunction);
    }

    public static Byte2CharFunction synchronize(Byte2CharFunction byte2CharFunction, Object object) {
        return new SynchronizedFunction(byte2CharFunction, object);
    }

    public static Byte2CharFunction unmodifiable(Byte2CharFunction byte2CharFunction) {
        return new UnmodifiableFunction(byte2CharFunction);
    }

    public static Byte2CharFunction primitive(java.util.function.Function<? super Byte, ? extends Character> function) {
        Objects.requireNonNull(function);
        if (function instanceof Byte2CharFunction) {
            return (Byte2CharFunction)function;
        }
        if (function instanceof IntUnaryOperator) {
            return arg_0 -> Byte2CharFunctions.lambda$primitive$0(function, arg_0);
        }
        return new PrimitiveFunction(function);
    }

    private static char lambda$primitive$0(java.util.function.Function function, byte by) {
        return SafeMath.safeIntToChar(((IntUnaryOperator)((Object)function)).applyAsInt(by));
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class PrimitiveFunction
    implements Byte2CharFunction {
        protected final java.util.function.Function<? super Byte, ? extends Character> function;

        protected PrimitiveFunction(java.util.function.Function<? super Byte, ? extends Character> function) {
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
        public char get(byte by) {
            Character c = this.function.apply((Byte)by);
            if (c == null) {
                return this.defaultReturnValue();
            }
            return c.charValue();
        }

        @Override
        @Deprecated
        public Character get(Object object) {
            if (object == null) {
                return null;
            }
            return this.function.apply((Byte)object);
        }

        @Override
        @Deprecated
        public Character put(Byte by, Character c) {
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
            return this.put((Byte)object, (Character)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableFunction
    extends AbstractByte2CharFunction
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Byte2CharFunction function;

        protected UnmodifiableFunction(Byte2CharFunction byte2CharFunction) {
            if (byte2CharFunction == null) {
                throw new NullPointerException();
            }
            this.function = byte2CharFunction;
        }

        @Override
        public int size() {
            return this.function.size();
        }

        @Override
        public char defaultReturnValue() {
            return this.function.defaultReturnValue();
        }

        @Override
        public void defaultReturnValue(char c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean containsKey(byte by) {
            return this.function.containsKey(by);
        }

        @Override
        public char put(byte by, char c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char get(byte by) {
            return this.function.get(by);
        }

        @Override
        public char remove(byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Character put(Byte by, Character c) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Character get(Object object) {
            return this.function.get(object);
        }

        @Override
        @Deprecated
        public Character remove(Object object) {
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
            return this.put((Byte)object, (Character)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedFunction
    implements Byte2CharFunction,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Byte2CharFunction function;
        protected final Object sync;

        protected SynchronizedFunction(Byte2CharFunction byte2CharFunction, Object object) {
            if (byte2CharFunction == null) {
                throw new NullPointerException();
            }
            this.function = byte2CharFunction;
            this.sync = object;
        }

        protected SynchronizedFunction(Byte2CharFunction byte2CharFunction) {
            if (byte2CharFunction == null) {
                throw new NullPointerException();
            }
            this.function = byte2CharFunction;
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
        public Character apply(Byte by) {
            Object object = this.sync;
            synchronized (object) {
                return (Character)this.function.apply(by);
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
        public char defaultReturnValue() {
            Object object = this.sync;
            synchronized (object) {
                return this.function.defaultReturnValue();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void defaultReturnValue(char c) {
            Object object = this.sync;
            synchronized (object) {
                this.function.defaultReturnValue(c);
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
        public char put(byte by, char c) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(by, c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char get(byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.get(by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char remove(byte by) {
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
        public Character put(Byte by, Character c) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(by, c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Character get(Object object) {
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
        public Character remove(Object object) {
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
            return this.put((Byte)object, (Character)object2);
        }

        @Override
        @Deprecated
        public Object apply(Object object) {
            return this.apply((Byte)object);
        }
    }

    public static class Singleton
    extends AbstractByte2CharFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final byte key;
        protected final char value;

        protected Singleton(byte by, char c) {
            this.key = by;
            this.value = c;
        }

        @Override
        public boolean containsKey(byte by) {
            return this.key == by;
        }

        @Override
        public char get(byte by) {
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
    extends AbstractByte2CharFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyFunction() {
        }

        @Override
        public char get(byte by) {
            return '\u0001';
        }

        @Override
        public boolean containsKey(byte by) {
            return true;
        }

        @Override
        public char defaultReturnValue() {
            return '\u0001';
        }

        @Override
        public void defaultReturnValue(char c) {
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

