/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.chars.AbstractChar2ObjectFunction;
import it.unimi.dsi.fastutil.chars.Char2ObjectFunction;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.IntFunction;

public final class Char2ObjectFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Char2ObjectFunctions() {
    }

    public static <V> Char2ObjectFunction<V> singleton(char c, V v) {
        return new Singleton<V>(c, v);
    }

    public static <V> Char2ObjectFunction<V> singleton(Character c, V v) {
        return new Singleton<V>(c.charValue(), v);
    }

    public static <V> Char2ObjectFunction<V> synchronize(Char2ObjectFunction<V> char2ObjectFunction) {
        return new SynchronizedFunction<V>(char2ObjectFunction);
    }

    public static <V> Char2ObjectFunction<V> synchronize(Char2ObjectFunction<V> char2ObjectFunction, Object object) {
        return new SynchronizedFunction<V>(char2ObjectFunction, object);
    }

    public static <V> Char2ObjectFunction<V> unmodifiable(Char2ObjectFunction<V> char2ObjectFunction) {
        return new UnmodifiableFunction<V>(char2ObjectFunction);
    }

    public static <V> Char2ObjectFunction<V> primitive(java.util.function.Function<? super Character, ? extends V> function) {
        Objects.requireNonNull(function);
        if (function instanceof Char2ObjectFunction) {
            return (Char2ObjectFunction)function;
        }
        if (function instanceof IntFunction) {
            return ((IntFunction)((Object)function))::apply;
        }
        return new PrimitiveFunction<V>(function);
    }

    public static class PrimitiveFunction<V>
    implements Char2ObjectFunction<V> {
        protected final java.util.function.Function<? super Character, ? extends V> function;

        protected PrimitiveFunction(java.util.function.Function<? super Character, ? extends V> function) {
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
        public V get(char c) {
            V v = this.function.apply(Character.valueOf(c));
            if (v == null) {
                return null;
            }
            return v;
        }

        @Override
        @Deprecated
        public V get(Object object) {
            if (object == null) {
                return null;
            }
            return this.function.apply((Character)object);
        }

        @Override
        @Deprecated
        public V put(Character c, V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object put(Object object, Object object2) {
            return this.put((Character)object, (V)object2);
        }
    }

    public static class UnmodifiableFunction<V>
    extends AbstractChar2ObjectFunction<V>
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Char2ObjectFunction<V> function;

        protected UnmodifiableFunction(Char2ObjectFunction<V> char2ObjectFunction) {
            if (char2ObjectFunction == null) {
                throw new NullPointerException();
            }
            this.function = char2ObjectFunction;
        }

        @Override
        public int size() {
            return this.function.size();
        }

        @Override
        public V defaultReturnValue() {
            return this.function.defaultReturnValue();
        }

        @Override
        public void defaultReturnValue(V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean containsKey(char c) {
            return this.function.containsKey(c);
        }

        @Override
        public V put(char c, V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V get(char c) {
            return this.function.get(c);
        }

        @Override
        public V remove(char c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public V put(Character c, V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public V get(Object object) {
            return this.function.get(object);
        }

        @Override
        @Deprecated
        public V remove(Object object) {
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
        public Object put(Object object, Object object2) {
            return this.put((Character)object, (V)object2);
        }
    }

    public static class SynchronizedFunction<V>
    implements Char2ObjectFunction<V>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Char2ObjectFunction<V> function;
        protected final Object sync;

        protected SynchronizedFunction(Char2ObjectFunction<V> char2ObjectFunction, Object object) {
            if (char2ObjectFunction == null) {
                throw new NullPointerException();
            }
            this.function = char2ObjectFunction;
            this.sync = object;
        }

        protected SynchronizedFunction(Char2ObjectFunction<V> char2ObjectFunction) {
            if (char2ObjectFunction == null) {
                throw new NullPointerException();
            }
            this.function = char2ObjectFunction;
            this.sync = this;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public V apply(int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.apply(n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public V apply(Character c) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.apply(c);
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
        public V defaultReturnValue() {
            Object object = this.sync;
            synchronized (object) {
                return this.function.defaultReturnValue();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void defaultReturnValue(V v) {
            Object object = this.sync;
            synchronized (object) {
                this.function.defaultReturnValue(v);
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
        public V put(char c, V v) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(c, v);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public V get(char c) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.get(c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public V remove(char c) {
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
        public V put(Character c, V v) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(c, v);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public V get(Object object) {
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
        public V remove(Object object) {
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
        public Object put(Object object, Object object2) {
            return this.put((Character)object, (V)object2);
        }

        @Override
        @Deprecated
        public Object apply(Object object) {
            return this.apply((Character)object);
        }
    }

    public static class Singleton<V>
    extends AbstractChar2ObjectFunction<V>
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final char key;
        protected final V value;

        protected Singleton(char c, V v) {
            this.key = c;
            this.value = v;
        }

        @Override
        public boolean containsKey(char c) {
            return this.key == c;
        }

        @Override
        public V get(char c) {
            return (V)(this.key == c ? this.value : this.defRetValue);
        }

        @Override
        public int size() {
            return 0;
        }

        public Object clone() {
            return this;
        }
    }

    public static class EmptyFunction<V>
    extends AbstractChar2ObjectFunction<V>
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyFunction() {
        }

        @Override
        public V get(char c) {
            return null;
        }

        @Override
        public boolean containsKey(char c) {
            return true;
        }

        @Override
        public V defaultReturnValue() {
            return null;
        }

        @Override
        public void defaultReturnValue(V v) {
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

