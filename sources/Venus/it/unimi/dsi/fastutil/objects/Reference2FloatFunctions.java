/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.objects.AbstractReference2FloatFunction;
import it.unimi.dsi.fastutil.objects.Reference2FloatFunction;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.ToDoubleFunction;

public final class Reference2FloatFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Reference2FloatFunctions() {
    }

    public static <K> Reference2FloatFunction<K> singleton(K k, float f) {
        return new Singleton<K>(k, f);
    }

    public static <K> Reference2FloatFunction<K> singleton(K k, Float f) {
        return new Singleton<K>(k, f.floatValue());
    }

    public static <K> Reference2FloatFunction<K> synchronize(Reference2FloatFunction<K> reference2FloatFunction) {
        return new SynchronizedFunction<K>(reference2FloatFunction);
    }

    public static <K> Reference2FloatFunction<K> synchronize(Reference2FloatFunction<K> reference2FloatFunction, Object object) {
        return new SynchronizedFunction<K>(reference2FloatFunction, object);
    }

    public static <K> Reference2FloatFunction<K> unmodifiable(Reference2FloatFunction<K> reference2FloatFunction) {
        return new UnmodifiableFunction<K>(reference2FloatFunction);
    }

    public static <K> Reference2FloatFunction<K> primitive(java.util.function.Function<? super K, ? extends Float> function) {
        Objects.requireNonNull(function);
        if (function instanceof Reference2FloatFunction) {
            return (Reference2FloatFunction)function;
        }
        if (function instanceof ToDoubleFunction) {
            return arg_0 -> Reference2FloatFunctions.lambda$primitive$0(function, arg_0);
        }
        return new PrimitiveFunction<K>(function);
    }

    private static float lambda$primitive$0(java.util.function.Function function, Object object) {
        return SafeMath.safeDoubleToFloat(((ToDoubleFunction)((Object)function)).applyAsDouble(object));
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class PrimitiveFunction<K>
    implements Reference2FloatFunction<K> {
        protected final java.util.function.Function<? super K, ? extends Float> function;

        protected PrimitiveFunction(java.util.function.Function<? super K, ? extends Float> function) {
            this.function = function;
        }

        @Override
        public boolean containsKey(Object object) {
            return this.function.apply(object) != null;
        }

        @Override
        public float getFloat(Object object) {
            Float f = this.function.apply(object);
            if (f == null) {
                return this.defaultReturnValue();
            }
            return f.floatValue();
        }

        @Override
        @Deprecated
        public Float get(Object object) {
            return this.function.apply(object);
        }

        @Override
        @Deprecated
        public Float put(K k, Float f) {
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
            return this.put((K)object, (Float)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableFunction<K>
    extends AbstractReference2FloatFunction<K>
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Reference2FloatFunction<K> function;

        protected UnmodifiableFunction(Reference2FloatFunction<K> reference2FloatFunction) {
            if (reference2FloatFunction == null) {
                throw new NullPointerException();
            }
            this.function = reference2FloatFunction;
        }

        @Override
        public int size() {
            return this.function.size();
        }

        @Override
        public float defaultReturnValue() {
            return this.function.defaultReturnValue();
        }

        @Override
        public void defaultReturnValue(float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean containsKey(Object object) {
            return this.function.containsKey(object);
        }

        @Override
        public float put(K k, float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float getFloat(Object object) {
            return this.function.getFloat(object);
        }

        @Override
        public float removeFloat(Object object) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Float put(K k, Float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Float get(Object object) {
            return this.function.get(object);
        }

        @Override
        @Deprecated
        public Float remove(Object object) {
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
            return this.put((K)object, (Float)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedFunction<K>
    implements Reference2FloatFunction<K>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Reference2FloatFunction<K> function;
        protected final Object sync;

        protected SynchronizedFunction(Reference2FloatFunction<K> reference2FloatFunction, Object object) {
            if (reference2FloatFunction == null) {
                throw new NullPointerException();
            }
            this.function = reference2FloatFunction;
            this.sync = object;
        }

        protected SynchronizedFunction(Reference2FloatFunction<K> reference2FloatFunction) {
            if (reference2FloatFunction == null) {
                throw new NullPointerException();
            }
            this.function = reference2FloatFunction;
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
        public Float apply(K k) {
            Object object = this.sync;
            synchronized (object) {
                return (Float)this.function.apply(k);
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
        public float defaultReturnValue() {
            Object object = this.sync;
            synchronized (object) {
                return this.function.defaultReturnValue();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void defaultReturnValue(float f) {
            Object object = this.sync;
            synchronized (object) {
                this.function.defaultReturnValue(f);
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
        public float put(K k, float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(k, f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float getFloat(Object object) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.function.getFloat(object);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float removeFloat(Object object) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.function.removeFloat(object);
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
        public Float put(K k, Float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(k, f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Float get(Object object) {
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
        public Float remove(Object object) {
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
            return this.put((K)object, (Float)object2);
        }

        @Override
        @Deprecated
        public Object apply(Object object) {
            return this.apply(object);
        }
    }

    public static class Singleton<K>
    extends AbstractReference2FloatFunction<K>
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final K key;
        protected final float value;

        protected Singleton(K k, float f) {
            this.key = k;
            this.value = f;
        }

        @Override
        public boolean containsKey(Object object) {
            return this.key == object;
        }

        @Override
        public float getFloat(Object object) {
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
    extends AbstractReference2FloatFunction<K>
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyFunction() {
        }

        @Override
        public float getFloat(Object object) {
            return 0.0f;
        }

        @Override
        public boolean containsKey(Object object) {
            return true;
        }

        @Override
        public float defaultReturnValue() {
            return 0.0f;
        }

        @Override
        public void defaultReturnValue(float f) {
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

