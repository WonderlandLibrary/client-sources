/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunctions$SynchronizedFunction
 *  com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunctions$UnmodifiableFunction
 */
package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Function;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2IntFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunctions;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.ToIntFunction;

public final class Object2IntFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Object2IntFunctions() {
    }

    public static <K> Object2IntFunction<K> singleton(K k, int n) {
        return new Singleton<K>(k, n);
    }

    public static <K> Object2IntFunction<K> singleton(K k, Integer n) {
        return new Singleton<K>(k, n);
    }

    public static <K> Object2IntFunction<K> synchronize(Object2IntFunction<K> object2IntFunction) {
        return new SynchronizedFunction(object2IntFunction);
    }

    public static <K> Object2IntFunction<K> synchronize(Object2IntFunction<K> object2IntFunction, Object object) {
        return new SynchronizedFunction(object2IntFunction, object);
    }

    public static <K> Object2IntFunction<K> unmodifiable(Object2IntFunction<? extends K> object2IntFunction) {
        return new UnmodifiableFunction(object2IntFunction);
    }

    public static <K> Object2IntFunction<K> primitive(java.util.function.Function<? super K, ? extends Integer> function) {
        Objects.requireNonNull(function);
        if (function instanceof Object2IntFunction) {
            return (Object2IntFunction)function;
        }
        if (function instanceof ToIntFunction) {
            return arg_0 -> Object2IntFunctions.lambda$primitive$0(function, arg_0);
        }
        return new PrimitiveFunction<K>(function);
    }

    private static int lambda$primitive$0(java.util.function.Function function, Object object) {
        return ((ToIntFunction)((Object)function)).applyAsInt(object);
    }

    public static class Singleton<K>
    extends AbstractObject2IntFunction<K>
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final K key;
        protected final int value;

        protected Singleton(K k, int n) {
            this.key = k;
            this.value = n;
        }

        @Override
        public boolean containsKey(Object object) {
            return Objects.equals(this.key, object);
        }

        @Override
        public int getInt(Object object) {
            return Objects.equals(this.key, object) ? this.value : this.defRetValue;
        }

        @Override
        public int getOrDefault(Object object, int n) {
            return Objects.equals(this.key, object) ? this.value : n;
        }

        @Override
        public int size() {
            return 0;
        }

        public Object clone() {
            return this;
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class PrimitiveFunction<K>
    implements Object2IntFunction<K> {
        protected final java.util.function.Function<? super K, ? extends Integer> function;

        protected PrimitiveFunction(java.util.function.Function<? super K, ? extends Integer> function) {
            this.function = function;
        }

        @Override
        public boolean containsKey(Object object) {
            return this.function.apply(object) != null;
        }

        @Override
        public int getInt(Object object) {
            Integer n = this.function.apply(object);
            if (n == null) {
                return this.defaultReturnValue();
            }
            return n;
        }

        @Override
        public int getOrDefault(Object object, int n) {
            Integer n2 = this.function.apply(object);
            if (n2 == null) {
                return n;
            }
            return n2;
        }

        @Override
        @Deprecated
        public Integer get(Object object) {
            return this.function.apply(object);
        }

        @Override
        @Deprecated
        public Integer getOrDefault(Object object, Integer n) {
            Integer n2 = this.function.apply(object);
            return n2 == null ? n : n2;
        }

        @Override
        @Deprecated
        public Integer put(K k, Integer n) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object getOrDefault(Object object, Object object2) {
            return this.getOrDefault(object, (Integer)object2);
        }

        @Override
        @Deprecated
        public Object get(Object object) {
            return this.get(object);
        }

        @Override
        @Deprecated
        public Object put(Object object, Object object2) {
            return this.put((K)object, (Integer)object2);
        }
    }

    public static class EmptyFunction<K>
    extends AbstractObject2IntFunction<K>
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyFunction() {
        }

        @Override
        public int getInt(Object object) {
            return 1;
        }

        @Override
        public int getOrDefault(Object object, int n) {
            return n;
        }

        @Override
        public boolean containsKey(Object object) {
            return true;
        }

        @Override
        public int defaultReturnValue() {
            return 1;
        }

        @Override
        public void defaultReturnValue(int n) {
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

