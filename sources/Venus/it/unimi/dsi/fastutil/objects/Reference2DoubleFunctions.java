/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.objects.AbstractReference2DoubleFunction;
import it.unimi.dsi.fastutil.objects.Reference2DoubleFunction;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.ToDoubleFunction;

public final class Reference2DoubleFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Reference2DoubleFunctions() {
    }

    public static <K> Reference2DoubleFunction<K> singleton(K k, double d) {
        return new Singleton<K>(k, d);
    }

    public static <K> Reference2DoubleFunction<K> singleton(K k, Double d) {
        return new Singleton<K>(k, d);
    }

    public static <K> Reference2DoubleFunction<K> synchronize(Reference2DoubleFunction<K> reference2DoubleFunction) {
        return new SynchronizedFunction<K>(reference2DoubleFunction);
    }

    public static <K> Reference2DoubleFunction<K> synchronize(Reference2DoubleFunction<K> reference2DoubleFunction, Object object) {
        return new SynchronizedFunction<K>(reference2DoubleFunction, object);
    }

    public static <K> Reference2DoubleFunction<K> unmodifiable(Reference2DoubleFunction<K> reference2DoubleFunction) {
        return new UnmodifiableFunction<K>(reference2DoubleFunction);
    }

    public static <K> Reference2DoubleFunction<K> primitive(java.util.function.Function<? super K, ? extends Double> function) {
        Objects.requireNonNull(function);
        if (function instanceof Reference2DoubleFunction) {
            return (Reference2DoubleFunction)function;
        }
        if (function instanceof ToDoubleFunction) {
            return arg_0 -> Reference2DoubleFunctions.lambda$primitive$0(function, arg_0);
        }
        return new PrimitiveFunction<K>(function);
    }

    private static double lambda$primitive$0(java.util.function.Function function, Object object) {
        return ((ToDoubleFunction)((Object)function)).applyAsDouble(object);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class PrimitiveFunction<K>
    implements Reference2DoubleFunction<K> {
        protected final java.util.function.Function<? super K, ? extends Double> function;

        protected PrimitiveFunction(java.util.function.Function<? super K, ? extends Double> function) {
            this.function = function;
        }

        @Override
        public boolean containsKey(Object object) {
            return this.function.apply(object) != null;
        }

        @Override
        public double getDouble(Object object) {
            Double d = this.function.apply(object);
            if (d == null) {
                return this.defaultReturnValue();
            }
            return d;
        }

        @Override
        @Deprecated
        public Double get(Object object) {
            return this.function.apply(object);
        }

        @Override
        @Deprecated
        public Double put(K k, Double d) {
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
            return this.put((K)object, (Double)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableFunction<K>
    extends AbstractReference2DoubleFunction<K>
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Reference2DoubleFunction<K> function;

        protected UnmodifiableFunction(Reference2DoubleFunction<K> reference2DoubleFunction) {
            if (reference2DoubleFunction == null) {
                throw new NullPointerException();
            }
            this.function = reference2DoubleFunction;
        }

        @Override
        public int size() {
            return this.function.size();
        }

        @Override
        public double defaultReturnValue() {
            return this.function.defaultReturnValue();
        }

        @Override
        public void defaultReturnValue(double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean containsKey(Object object) {
            return this.function.containsKey(object);
        }

        @Override
        public double put(K k, double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double getDouble(Object object) {
            return this.function.getDouble(object);
        }

        @Override
        public double removeDouble(Object object) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Double put(K k, Double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Double get(Object object) {
            return this.function.get(object);
        }

        @Override
        @Deprecated
        public Double remove(Object object) {
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
            return this.put((K)object, (Double)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedFunction<K>
    implements Reference2DoubleFunction<K>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Reference2DoubleFunction<K> function;
        protected final Object sync;

        protected SynchronizedFunction(Reference2DoubleFunction<K> reference2DoubleFunction, Object object) {
            if (reference2DoubleFunction == null) {
                throw new NullPointerException();
            }
            this.function = reference2DoubleFunction;
            this.sync = object;
        }

        protected SynchronizedFunction(Reference2DoubleFunction<K> reference2DoubleFunction) {
            if (reference2DoubleFunction == null) {
                throw new NullPointerException();
            }
            this.function = reference2DoubleFunction;
            this.sync = this;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double applyAsDouble(K k) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.applyAsDouble(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Double apply(K k) {
            Object object = this.sync;
            synchronized (object) {
                return (Double)this.function.apply(k);
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
        public double defaultReturnValue() {
            Object object = this.sync;
            synchronized (object) {
                return this.function.defaultReturnValue();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void defaultReturnValue(double d) {
            Object object = this.sync;
            synchronized (object) {
                this.function.defaultReturnValue(d);
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
        public double put(K k, double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(k, d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double getDouble(Object object) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.function.getDouble(object);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double removeDouble(Object object) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.function.removeDouble(object);
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
        public Double put(K k, Double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(k, d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Double get(Object object) {
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
        public Double remove(Object object) {
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
            return this.put((K)object, (Double)object2);
        }

        @Override
        @Deprecated
        public Object apply(Object object) {
            return this.apply(object);
        }
    }

    public static class Singleton<K>
    extends AbstractReference2DoubleFunction<K>
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final K key;
        protected final double value;

        protected Singleton(K k, double d) {
            this.key = k;
            this.value = d;
        }

        @Override
        public boolean containsKey(Object object) {
            return this.key == object;
        }

        @Override
        public double getDouble(Object object) {
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
    extends AbstractReference2DoubleFunction<K>
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyFunction() {
        }

        @Override
        public double getDouble(Object object) {
            return 0.0;
        }

        @Override
        public boolean containsKey(Object object) {
            return true;
        }

        @Override
        public double defaultReturnValue() {
            return 0.0;
        }

        @Override
        public void defaultReturnValue(double d) {
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

