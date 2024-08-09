/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import java.io.Serializable;
import java.util.Map;
import javax.annotation.Nullable;

@GwtCompatible
public final class Functions {
    private Functions() {
    }

    public static Function<Object, String> toStringFunction() {
        return ToStringFunction.INSTANCE;
    }

    public static <E> Function<E, E> identity() {
        return IdentityFunction.INSTANCE;
    }

    public static <K, V> Function<K, V> forMap(Map<K, V> map) {
        return new FunctionForMapNoDefault<K, V>(map);
    }

    public static <K, V> Function<K, V> forMap(Map<K, ? extends V> map, @Nullable V v) {
        return new ForMapWithDefault<K, V>(map, v);
    }

    public static <A, B, C> Function<A, C> compose(Function<B, C> function, Function<A, ? extends B> function2) {
        return new FunctionComposition<A, B, C>(function, function2);
    }

    public static <T> Function<T, Boolean> forPredicate(Predicate<T> predicate) {
        return new PredicateFunction(predicate, null);
    }

    public static <E> Function<Object, E> constant(@Nullable E e) {
        return new ConstantFunction<E>(e);
    }

    public static <T> Function<Object, T> forSupplier(Supplier<T> supplier) {
        return new SupplierFunction(supplier, null);
    }

    private static class SupplierFunction<T>
    implements Function<Object, T>,
    Serializable {
        private final Supplier<T> supplier;
        private static final long serialVersionUID = 0L;

        private SupplierFunction(Supplier<T> supplier) {
            this.supplier = Preconditions.checkNotNull(supplier);
        }

        @Override
        public T apply(@Nullable Object object) {
            return this.supplier.get();
        }

        @Override
        public boolean equals(@Nullable Object object) {
            if (object instanceof SupplierFunction) {
                SupplierFunction supplierFunction = (SupplierFunction)object;
                return this.supplier.equals(supplierFunction.supplier);
            }
            return true;
        }

        public int hashCode() {
            return this.supplier.hashCode();
        }

        public String toString() {
            return "Functions.forSupplier(" + this.supplier + ")";
        }

        SupplierFunction(Supplier supplier, 1 var2_2) {
            this(supplier);
        }
    }

    private static class ConstantFunction<E>
    implements Function<Object, E>,
    Serializable {
        private final E value;
        private static final long serialVersionUID = 0L;

        public ConstantFunction(@Nullable E e) {
            this.value = e;
        }

        @Override
        public E apply(@Nullable Object object) {
            return this.value;
        }

        @Override
        public boolean equals(@Nullable Object object) {
            if (object instanceof ConstantFunction) {
                ConstantFunction constantFunction = (ConstantFunction)object;
                return Objects.equal(this.value, constantFunction.value);
            }
            return true;
        }

        public int hashCode() {
            return this.value == null ? 0 : this.value.hashCode();
        }

        public String toString() {
            return "Functions.constant(" + this.value + ")";
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static class PredicateFunction<T>
    implements Function<T, Boolean>,
    Serializable {
        private final Predicate<T> predicate;
        private static final long serialVersionUID = 0L;

        private PredicateFunction(Predicate<T> predicate) {
            this.predicate = Preconditions.checkNotNull(predicate);
        }

        @Override
        public Boolean apply(@Nullable T t) {
            return this.predicate.apply(t);
        }

        @Override
        public boolean equals(@Nullable Object object) {
            if (object instanceof PredicateFunction) {
                PredicateFunction predicateFunction = (PredicateFunction)object;
                return this.predicate.equals(predicateFunction.predicate);
            }
            return true;
        }

        public int hashCode() {
            return this.predicate.hashCode();
        }

        public String toString() {
            return "Functions.forPredicate(" + this.predicate + ")";
        }

        @Override
        public Object apply(@Nullable Object object) {
            return this.apply(object);
        }

        PredicateFunction(Predicate predicate, 1 var2_2) {
            this(predicate);
        }
    }

    private static class FunctionComposition<A, B, C>
    implements Function<A, C>,
    Serializable {
        private final Function<B, C> g;
        private final Function<A, ? extends B> f;
        private static final long serialVersionUID = 0L;

        public FunctionComposition(Function<B, C> function, Function<A, ? extends B> function2) {
            this.g = Preconditions.checkNotNull(function);
            this.f = Preconditions.checkNotNull(function2);
        }

        @Override
        public C apply(@Nullable A a) {
            return this.g.apply(this.f.apply(a));
        }

        @Override
        public boolean equals(@Nullable Object object) {
            if (object instanceof FunctionComposition) {
                FunctionComposition functionComposition = (FunctionComposition)object;
                return this.f.equals(functionComposition.f) && this.g.equals(functionComposition.g);
            }
            return true;
        }

        public int hashCode() {
            return this.f.hashCode() ^ this.g.hashCode();
        }

        public String toString() {
            return this.g + "(" + this.f + ")";
        }
    }

    private static class ForMapWithDefault<K, V>
    implements Function<K, V>,
    Serializable {
        final Map<K, ? extends V> map;
        final V defaultValue;
        private static final long serialVersionUID = 0L;

        ForMapWithDefault(Map<K, ? extends V> map, @Nullable V v) {
            this.map = Preconditions.checkNotNull(map);
            this.defaultValue = v;
        }

        @Override
        public V apply(@Nullable K k) {
            V v = this.map.get(k);
            return v != null || this.map.containsKey(k) ? v : this.defaultValue;
        }

        @Override
        public boolean equals(@Nullable Object object) {
            if (object instanceof ForMapWithDefault) {
                ForMapWithDefault forMapWithDefault = (ForMapWithDefault)object;
                return this.map.equals(forMapWithDefault.map) && Objects.equal(this.defaultValue, forMapWithDefault.defaultValue);
            }
            return true;
        }

        public int hashCode() {
            return Objects.hashCode(this.map, this.defaultValue);
        }

        public String toString() {
            return "Functions.forMap(" + this.map + ", defaultValue=" + this.defaultValue + ")";
        }
    }

    private static class FunctionForMapNoDefault<K, V>
    implements Function<K, V>,
    Serializable {
        final Map<K, V> map;
        private static final long serialVersionUID = 0L;

        FunctionForMapNoDefault(Map<K, V> map) {
            this.map = Preconditions.checkNotNull(map);
        }

        @Override
        public V apply(@Nullable K k) {
            V v = this.map.get(k);
            Preconditions.checkArgument(v != null || this.map.containsKey(k), "Key '%s' not present in map", k);
            return v;
        }

        @Override
        public boolean equals(@Nullable Object object) {
            if (object instanceof FunctionForMapNoDefault) {
                FunctionForMapNoDefault functionForMapNoDefault = (FunctionForMapNoDefault)object;
                return this.map.equals(functionForMapNoDefault.map);
            }
            return true;
        }

        public int hashCode() {
            return this.map.hashCode();
        }

        public String toString() {
            return "Functions.forMap(" + this.map + ")";
        }
    }

    private static enum IdentityFunction implements Function<Object, Object>
    {
        INSTANCE;


        @Override
        @Nullable
        public Object apply(@Nullable Object object) {
            return object;
        }

        public String toString() {
            return "Functions.identity()";
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static enum ToStringFunction implements Function<Object, String>
    {
        INSTANCE;


        @Override
        public String apply(Object object) {
            Preconditions.checkNotNull(object);
            return object.toString();
        }

        public String toString() {
            return "Functions.toStringFunction()";
        }

        @Override
        public Object apply(Object object) {
            return this.apply(object);
        }
    }
}

