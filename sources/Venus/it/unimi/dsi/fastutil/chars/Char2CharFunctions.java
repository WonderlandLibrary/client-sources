/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.chars.AbstractChar2CharFunction;
import it.unimi.dsi.fastutil.chars.Char2CharFunction;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.IntUnaryOperator;

public final class Char2CharFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Char2CharFunctions() {
    }

    public static Char2CharFunction singleton(char c, char c2) {
        return new Singleton(c, c2);
    }

    public static Char2CharFunction singleton(Character c, Character c2) {
        return new Singleton(c.charValue(), c2.charValue());
    }

    public static Char2CharFunction synchronize(Char2CharFunction char2CharFunction) {
        return new SynchronizedFunction(char2CharFunction);
    }

    public static Char2CharFunction synchronize(Char2CharFunction char2CharFunction, Object object) {
        return new SynchronizedFunction(char2CharFunction, object);
    }

    public static Char2CharFunction unmodifiable(Char2CharFunction char2CharFunction) {
        return new UnmodifiableFunction(char2CharFunction);
    }

    public static Char2CharFunction primitive(java.util.function.Function<? super Character, ? extends Character> function) {
        Objects.requireNonNull(function);
        if (function instanceof Char2CharFunction) {
            return (Char2CharFunction)function;
        }
        if (function instanceof IntUnaryOperator) {
            return arg_0 -> Char2CharFunctions.lambda$primitive$0(function, arg_0);
        }
        return new PrimitiveFunction(function);
    }

    private static char lambda$primitive$0(java.util.function.Function function, char c) {
        return SafeMath.safeIntToChar(((IntUnaryOperator)((Object)function)).applyAsInt(c));
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class PrimitiveFunction
    implements Char2CharFunction {
        protected final java.util.function.Function<? super Character, ? extends Character> function;

        protected PrimitiveFunction(java.util.function.Function<? super Character, ? extends Character> function) {
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
        public char get(char c) {
            Character c2 = this.function.apply(Character.valueOf(c));
            if (c2 == null) {
                return this.defaultReturnValue();
            }
            return c2.charValue();
        }

        @Override
        @Deprecated
        public Character get(Object object) {
            if (object == null) {
                return null;
            }
            return this.function.apply((Character)object);
        }

        @Override
        @Deprecated
        public Character put(Character c, Character c2) {
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
            return this.put((Character)object, (Character)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableFunction
    extends AbstractChar2CharFunction
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Char2CharFunction function;

        protected UnmodifiableFunction(Char2CharFunction char2CharFunction) {
            if (char2CharFunction == null) {
                throw new NullPointerException();
            }
            this.function = char2CharFunction;
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
        public boolean containsKey(char c) {
            return this.function.containsKey(c);
        }

        @Override
        public char put(char c, char c2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char get(char c) {
            return this.function.get(c);
        }

        @Override
        public char remove(char c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Character put(Character c, Character c2) {
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
            return this.put((Character)object, (Character)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedFunction
    implements Char2CharFunction,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Char2CharFunction function;
        protected final Object sync;

        protected SynchronizedFunction(Char2CharFunction char2CharFunction, Object object) {
            if (char2CharFunction == null) {
                throw new NullPointerException();
            }
            this.function = char2CharFunction;
            this.sync = object;
        }

        protected SynchronizedFunction(Char2CharFunction char2CharFunction) {
            if (char2CharFunction == null) {
                throw new NullPointerException();
            }
            this.function = char2CharFunction;
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
        public Character apply(Character c) {
            Object object = this.sync;
            synchronized (object) {
                return (Character)this.function.apply(c);
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
        public char put(char c, char c2) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(c, c2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char get(char c) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.get(c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char remove(char c) {
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
        public Character put(Character c, Character c2) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(c, c2);
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
            return this.put((Character)object, (Character)object2);
        }

        @Override
        @Deprecated
        public Object apply(Object object) {
            return this.apply((Character)object);
        }
    }

    public static class Singleton
    extends AbstractChar2CharFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final char key;
        protected final char value;

        protected Singleton(char c, char c2) {
            this.key = c;
            this.value = c2;
        }

        @Override
        public boolean containsKey(char c) {
            return this.key == c;
        }

        @Override
        public char get(char c) {
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
    extends AbstractChar2CharFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyFunction() {
        }

        @Override
        public char get(char c) {
            return '\u0001';
        }

        @Override
        public boolean containsKey(char c) {
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

