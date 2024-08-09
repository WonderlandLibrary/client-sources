/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.chars.AbstractChar2LongFunction;
import it.unimi.dsi.fastutil.chars.Char2LongFunction;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.IntToLongFunction;

public final class Char2LongFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Char2LongFunctions() {
    }

    public static Char2LongFunction singleton(char c, long l) {
        return new Singleton(c, l);
    }

    public static Char2LongFunction singleton(Character c, Long l) {
        return new Singleton(c.charValue(), l);
    }

    public static Char2LongFunction synchronize(Char2LongFunction char2LongFunction) {
        return new SynchronizedFunction(char2LongFunction);
    }

    public static Char2LongFunction synchronize(Char2LongFunction char2LongFunction, Object object) {
        return new SynchronizedFunction(char2LongFunction, object);
    }

    public static Char2LongFunction unmodifiable(Char2LongFunction char2LongFunction) {
        return new UnmodifiableFunction(char2LongFunction);
    }

    public static Char2LongFunction primitive(java.util.function.Function<? super Character, ? extends Long> function) {
        Objects.requireNonNull(function);
        if (function instanceof Char2LongFunction) {
            return (Char2LongFunction)function;
        }
        if (function instanceof IntToLongFunction) {
            return ((IntToLongFunction)((Object)function))::applyAsLong;
        }
        return new PrimitiveFunction(function);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class PrimitiveFunction
    implements Char2LongFunction {
        protected final java.util.function.Function<? super Character, ? extends Long> function;

        protected PrimitiveFunction(java.util.function.Function<? super Character, ? extends Long> function) {
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
        public long get(char c) {
            Long l = this.function.apply(Character.valueOf(c));
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
            return this.function.apply((Character)object);
        }

        @Override
        @Deprecated
        public Long put(Character c, Long l) {
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
            return this.put((Character)object, (Long)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableFunction
    extends AbstractChar2LongFunction
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Char2LongFunction function;

        protected UnmodifiableFunction(Char2LongFunction char2LongFunction) {
            if (char2LongFunction == null) {
                throw new NullPointerException();
            }
            this.function = char2LongFunction;
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
        public boolean containsKey(char c) {
            return this.function.containsKey(c);
        }

        @Override
        public long put(char c, long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long get(char c) {
            return this.function.get(c);
        }

        @Override
        public long remove(char c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Long put(Character c, Long l) {
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
            return this.put((Character)object, (Long)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedFunction
    implements Char2LongFunction,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Char2LongFunction function;
        protected final Object sync;

        protected SynchronizedFunction(Char2LongFunction char2LongFunction, Object object) {
            if (char2LongFunction == null) {
                throw new NullPointerException();
            }
            this.function = char2LongFunction;
            this.sync = object;
        }

        protected SynchronizedFunction(Char2LongFunction char2LongFunction) {
            if (char2LongFunction == null) {
                throw new NullPointerException();
            }
            this.function = char2LongFunction;
            this.sync = this;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public long applyAsLong(int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.applyAsLong(n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Long apply(Character c) {
            Object object = this.sync;
            synchronized (object) {
                return (Long)this.function.apply(c);
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
        public long put(char c, long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(c, l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long get(char c) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.get(c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long remove(char c) {
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
        public Long put(Character c, Long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(c, l);
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
            return this.put((Character)object, (Long)object2);
        }

        @Override
        @Deprecated
        public Object apply(Object object) {
            return this.apply((Character)object);
        }
    }

    public static class Singleton
    extends AbstractChar2LongFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final char key;
        protected final long value;

        protected Singleton(char c, long l) {
            this.key = c;
            this.value = l;
        }

        @Override
        public boolean containsKey(char c) {
            return this.key == c;
        }

        @Override
        public long get(char c) {
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
    extends AbstractChar2LongFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyFunction() {
        }

        @Override
        public long get(char c) {
            return 0L;
        }

        @Override
        public boolean containsKey(char c) {
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

