/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.objects.AbstractReference2CharFunction;
import it.unimi.dsi.fastutil.objects.Reference2CharFunction;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.ToIntFunction;

public final class Reference2CharFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Reference2CharFunctions() {
    }

    public static <K> Reference2CharFunction<K> singleton(K k, char c) {
        return new Singleton<K>(k, c);
    }

    public static <K> Reference2CharFunction<K> singleton(K k, Character c) {
        return new Singleton<K>(k, c.charValue());
    }

    public static <K> Reference2CharFunction<K> synchronize(Reference2CharFunction<K> reference2CharFunction) {
        return new SynchronizedFunction<K>(reference2CharFunction);
    }

    public static <K> Reference2CharFunction<K> synchronize(Reference2CharFunction<K> reference2CharFunction, Object object) {
        return new SynchronizedFunction<K>(reference2CharFunction, object);
    }

    public static <K> Reference2CharFunction<K> unmodifiable(Reference2CharFunction<K> reference2CharFunction) {
        return new UnmodifiableFunction<K>(reference2CharFunction);
    }

    public static <K> Reference2CharFunction<K> primitive(java.util.function.Function<? super K, ? extends Character> function) {
        Objects.requireNonNull(function);
        if (function instanceof Reference2CharFunction) {
            return (Reference2CharFunction)function;
        }
        if (function instanceof ToIntFunction) {
            return arg_0 -> Reference2CharFunctions.lambda$primitive$0(function, arg_0);
        }
        return new PrimitiveFunction<K>(function);
    }

    private static char lambda$primitive$0(java.util.function.Function function, Object object) {
        return SafeMath.safeIntToChar(((ToIntFunction)((Object)function)).applyAsInt(object));
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class PrimitiveFunction<K>
    implements Reference2CharFunction<K> {
        protected final java.util.function.Function<? super K, ? extends Character> function;

        protected PrimitiveFunction(java.util.function.Function<? super K, ? extends Character> function) {
            this.function = function;
        }

        @Override
        public boolean containsKey(Object object) {
            return this.function.apply(object) != null;
        }

        @Override
        public char getChar(Object object) {
            Character c = this.function.apply(object);
            if (c == null) {
                return this.defaultReturnValue();
            }
            return c.charValue();
        }

        @Override
        @Deprecated
        public Character get(Object object) {
            return this.function.apply(object);
        }

        @Override
        @Deprecated
        public Character put(K k, Character c) {
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
            return this.put((K)object, (Character)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableFunction<K>
    extends AbstractReference2CharFunction<K>
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Reference2CharFunction<K> function;

        protected UnmodifiableFunction(Reference2CharFunction<K> reference2CharFunction) {
            if (reference2CharFunction == null) {
                throw new NullPointerException();
            }
            this.function = reference2CharFunction;
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
        public boolean containsKey(Object object) {
            return this.function.containsKey(object);
        }

        @Override
        public char put(K k, char c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char getChar(Object object) {
            return this.function.getChar(object);
        }

        @Override
        public char removeChar(Object object) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Character put(K k, Character c) {
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
            return this.put((K)object, (Character)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedFunction<K>
    implements Reference2CharFunction<K>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Reference2CharFunction<K> function;
        protected final Object sync;

        protected SynchronizedFunction(Reference2CharFunction<K> reference2CharFunction, Object object) {
            if (reference2CharFunction == null) {
                throw new NullPointerException();
            }
            this.function = reference2CharFunction;
            this.sync = object;
        }

        protected SynchronizedFunction(Reference2CharFunction<K> reference2CharFunction) {
            if (reference2CharFunction == null) {
                throw new NullPointerException();
            }
            this.function = reference2CharFunction;
            this.sync = this;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int applyAsInt(K k) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.applyAsInt(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Character apply(K k) {
            Object object = this.sync;
            synchronized (object) {
                return (Character)this.function.apply(k);
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
        public char put(K k, char c) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(k, c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char getChar(Object object) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.function.getChar(object);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char removeChar(Object object) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.function.removeChar(object);
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
        public Character put(K k, Character c) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(k, c);
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
            return this.put((K)object, (Character)object2);
        }

        @Override
        @Deprecated
        public Object apply(Object object) {
            return this.apply(object);
        }
    }

    public static class Singleton<K>
    extends AbstractReference2CharFunction<K>
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final K key;
        protected final char value;

        protected Singleton(K k, char c) {
            this.key = k;
            this.value = c;
        }

        @Override
        public boolean containsKey(Object object) {
            return this.key == object;
        }

        @Override
        public char getChar(Object object) {
            return this.key == object ? this.value : this.defRetValue;
        }

        @Override
        public int size() {
            return 0;
        }

        public Object clone() {
            return this;
        }
    }

    public static class EmptyFunction<K>
    extends AbstractReference2CharFunction<K>
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyFunction() {
        }

        @Override
        public char getChar(Object object) {
            return '\u0001';
        }

        @Override
        public boolean containsKey(Object object) {
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

