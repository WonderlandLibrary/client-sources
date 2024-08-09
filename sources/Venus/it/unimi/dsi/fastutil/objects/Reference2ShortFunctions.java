/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.objects.AbstractReference2ShortFunction;
import it.unimi.dsi.fastutil.objects.Reference2ShortFunction;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.ToIntFunction;

public final class Reference2ShortFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Reference2ShortFunctions() {
    }

    public static <K> Reference2ShortFunction<K> singleton(K k, short s) {
        return new Singleton<K>(k, s);
    }

    public static <K> Reference2ShortFunction<K> singleton(K k, Short s) {
        return new Singleton<K>(k, s);
    }

    public static <K> Reference2ShortFunction<K> synchronize(Reference2ShortFunction<K> reference2ShortFunction) {
        return new SynchronizedFunction<K>(reference2ShortFunction);
    }

    public static <K> Reference2ShortFunction<K> synchronize(Reference2ShortFunction<K> reference2ShortFunction, Object object) {
        return new SynchronizedFunction<K>(reference2ShortFunction, object);
    }

    public static <K> Reference2ShortFunction<K> unmodifiable(Reference2ShortFunction<K> reference2ShortFunction) {
        return new UnmodifiableFunction<K>(reference2ShortFunction);
    }

    public static <K> Reference2ShortFunction<K> primitive(java.util.function.Function<? super K, ? extends Short> function) {
        Objects.requireNonNull(function);
        if (function instanceof Reference2ShortFunction) {
            return (Reference2ShortFunction)function;
        }
        if (function instanceof ToIntFunction) {
            return arg_0 -> Reference2ShortFunctions.lambda$primitive$0(function, arg_0);
        }
        return new PrimitiveFunction<K>(function);
    }

    private static short lambda$primitive$0(java.util.function.Function function, Object object) {
        return SafeMath.safeIntToShort(((ToIntFunction)((Object)function)).applyAsInt(object));
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class PrimitiveFunction<K>
    implements Reference2ShortFunction<K> {
        protected final java.util.function.Function<? super K, ? extends Short> function;

        protected PrimitiveFunction(java.util.function.Function<? super K, ? extends Short> function) {
            this.function = function;
        }

        @Override
        public boolean containsKey(Object object) {
            return this.function.apply(object) != null;
        }

        @Override
        public short getShort(Object object) {
            Short s = this.function.apply(object);
            if (s == null) {
                return this.defaultReturnValue();
            }
            return s;
        }

        @Override
        @Deprecated
        public Short get(Object object) {
            return this.function.apply(object);
        }

        @Override
        @Deprecated
        public Short put(K k, Short s) {
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
            return this.put((K)object, (Short)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableFunction<K>
    extends AbstractReference2ShortFunction<K>
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Reference2ShortFunction<K> function;

        protected UnmodifiableFunction(Reference2ShortFunction<K> reference2ShortFunction) {
            if (reference2ShortFunction == null) {
                throw new NullPointerException();
            }
            this.function = reference2ShortFunction;
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
        public boolean containsKey(Object object) {
            return this.function.containsKey(object);
        }

        @Override
        public short put(K k, short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public short getShort(Object object) {
            return this.function.getShort(object);
        }

        @Override
        public short removeShort(Object object) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Short put(K k, Short s) {
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
            return this.put((K)object, (Short)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedFunction<K>
    implements Reference2ShortFunction<K>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Reference2ShortFunction<K> function;
        protected final Object sync;

        protected SynchronizedFunction(Reference2ShortFunction<K> reference2ShortFunction, Object object) {
            if (reference2ShortFunction == null) {
                throw new NullPointerException();
            }
            this.function = reference2ShortFunction;
            this.sync = object;
        }

        protected SynchronizedFunction(Reference2ShortFunction<K> reference2ShortFunction) {
            if (reference2ShortFunction == null) {
                throw new NullPointerException();
            }
            this.function = reference2ShortFunction;
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
        public Short apply(K k) {
            Object object = this.sync;
            synchronized (object) {
                return (Short)this.function.apply(k);
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
        public short put(K k, short s) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(k, s);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short getShort(Object object) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.function.getShort(object);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short removeShort(Object object) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.function.removeShort(object);
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
        public Short put(K k, Short s) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(k, s);
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
            return this.put((K)object, (Short)object2);
        }

        @Override
        @Deprecated
        public Object apply(Object object) {
            return this.apply(object);
        }
    }

    public static class Singleton<K>
    extends AbstractReference2ShortFunction<K>
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final K key;
        protected final short value;

        protected Singleton(K k, short s) {
            this.key = k;
            this.value = s;
        }

        @Override
        public boolean containsKey(Object object) {
            return this.key == object;
        }

        @Override
        public short getShort(Object object) {
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
    extends AbstractReference2ShortFunction<K>
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyFunction() {
        }

        @Override
        public short getShort(Object object) {
            return 1;
        }

        @Override
        public boolean containsKey(Object object) {
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

