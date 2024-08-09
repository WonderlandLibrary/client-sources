/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.chars.AbstractChar2ByteFunction;
import it.unimi.dsi.fastutil.chars.Char2ByteFunction;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.IntUnaryOperator;

public final class Char2ByteFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Char2ByteFunctions() {
    }

    public static Char2ByteFunction singleton(char c, byte by) {
        return new Singleton(c, by);
    }

    public static Char2ByteFunction singleton(Character c, Byte by) {
        return new Singleton(c.charValue(), by);
    }

    public static Char2ByteFunction synchronize(Char2ByteFunction char2ByteFunction) {
        return new SynchronizedFunction(char2ByteFunction);
    }

    public static Char2ByteFunction synchronize(Char2ByteFunction char2ByteFunction, Object object) {
        return new SynchronizedFunction(char2ByteFunction, object);
    }

    public static Char2ByteFunction unmodifiable(Char2ByteFunction char2ByteFunction) {
        return new UnmodifiableFunction(char2ByteFunction);
    }

    public static Char2ByteFunction primitive(java.util.function.Function<? super Character, ? extends Byte> function) {
        Objects.requireNonNull(function);
        if (function instanceof Char2ByteFunction) {
            return (Char2ByteFunction)function;
        }
        if (function instanceof IntUnaryOperator) {
            return arg_0 -> Char2ByteFunctions.lambda$primitive$0(function, arg_0);
        }
        return new PrimitiveFunction(function);
    }

    private static byte lambda$primitive$0(java.util.function.Function function, char c) {
        return SafeMath.safeIntToByte(((IntUnaryOperator)((Object)function)).applyAsInt(c));
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class PrimitiveFunction
    implements Char2ByteFunction {
        protected final java.util.function.Function<? super Character, ? extends Byte> function;

        protected PrimitiveFunction(java.util.function.Function<? super Character, ? extends Byte> function) {
            this.function = function;
        }

        @Override
        public boolean containsKey(char c) {
            return this.function.apply(Character.valueOf(c)) != null;
        }

        @Override
        @Deprecated
        public boolean containsKey(Object object) {
            if (object == null) {
                return true;
            }
            return this.function.apply((Character)object) != null;
        }

        @Override
        public byte get(char c) {
            Byte by = this.function.apply(Character.valueOf(c));
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
            return this.function.apply((Character)object);
        }

        @Override
        @Deprecated
        public Byte put(Character c, Byte by) {
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
            return this.put((Character)object, (Byte)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableFunction
    extends AbstractChar2ByteFunction
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Char2ByteFunction function;

        protected UnmodifiableFunction(Char2ByteFunction char2ByteFunction) {
            if (char2ByteFunction == null) {
                throw new NullPointerException();
            }
            this.function = char2ByteFunction;
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
        public boolean containsKey(char c) {
            return this.function.containsKey(c);
        }

        @Override
        public byte put(char c, byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte get(char c) {
            return this.function.get(c);
        }

        @Override
        public byte remove(char c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Byte put(Character c, Byte by) {
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
            return this.put((Character)object, (Byte)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedFunction
    implements Char2ByteFunction,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Char2ByteFunction function;
        protected final Object sync;

        protected SynchronizedFunction(Char2ByteFunction char2ByteFunction, Object object) {
            if (char2ByteFunction == null) {
                throw new NullPointerException();
            }
            this.function = char2ByteFunction;
            this.sync = object;
        }

        protected SynchronizedFunction(Char2ByteFunction char2ByteFunction) {
            if (char2ByteFunction == null) {
                throw new NullPointerException();
            }
            this.function = char2ByteFunction;
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
        public Byte apply(Character c) {
            Object object = this.sync;
            synchronized (object) {
                return (Byte)this.function.apply(c);
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
        public boolean containsKey(char c) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.containsKey(c);
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
        public byte put(char c, byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(c, by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte get(char c) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.get(c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte remove(char c) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.remove(c);
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
        public Byte put(Character c, Byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(c, by);
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
            return this.put((Character)object, (Byte)object2);
        }

        @Override
        @Deprecated
        public Object apply(Object object) {
            return this.apply((Character)object);
        }
    }

    public static class Singleton
    extends AbstractChar2ByteFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final char key;
        protected final byte value;

        protected Singleton(char c, byte by) {
            this.key = c;
            this.value = by;
        }

        @Override
        public boolean containsKey(char c) {
            return this.key == c;
        }

        @Override
        public byte get(char c) {
            return this.key == c ? this.value : this.defRetValue;
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
    extends AbstractChar2ByteFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyFunction() {
        }

        @Override
        public byte get(char c) {
            return 1;
        }

        @Override
        public boolean containsKey(char c) {
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

