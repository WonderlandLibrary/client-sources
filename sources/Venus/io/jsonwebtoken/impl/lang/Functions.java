/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.lang;

import io.jsonwebtoken.impl.lang.CheckedFunction;
import io.jsonwebtoken.impl.lang.DelegatingCheckedFunction;
import io.jsonwebtoken.impl.lang.FormattedStringFunction;
import io.jsonwebtoken.impl.lang.FormattedStringSupplier;
import io.jsonwebtoken.impl.lang.Function;
import io.jsonwebtoken.impl.lang.PropagatingExceptionFunction;
import io.jsonwebtoken.lang.Assert;

public final class Functions {
    private Functions() {
    }

    public static <T> Function<T, T> identity() {
        return new Function<T, T>(){

            @Override
            public T apply(T t) {
                return t;
            }
        };
    }

    public static <T, R, E extends RuntimeException> Function<T, R> wrapFmt(CheckedFunction<T, R> checkedFunction, Class<E> clazz, String string) {
        return new PropagatingExceptionFunction<T, R, E>(checkedFunction, clazz, new FormattedStringFunction(string));
    }

    public static <T, R, E extends RuntimeException> Function<T, R> wrap(Function<T, R> function, Class<E> clazz, String string, Object ... objectArray) {
        return new PropagatingExceptionFunction<T, R, E>(new DelegatingCheckedFunction<T, R>(function), clazz, new FormattedStringSupplier(string, objectArray));
    }

    public static <T, V, R> Function<T, R> andThen(Function<T, ? extends V> function, Function<V, R> function2) {
        Assert.notNull(function, "Before function cannot be null.");
        Assert.notNull(function2, "After function cannot be null.");
        return new Function<T, R>(function, function2){
            final Function val$before;
            final Function val$after;
            {
                this.val$before = function;
                this.val$after = function2;
            }

            @Override
            public R apply(T t) {
                Object r = this.val$before.apply(t);
                return this.val$after.apply(r);
            }
        };
    }

    @SafeVarargs
    public static <T, R> Function<T, R> firstResult(Function<T, R> ... functionArray) {
        Assert.notEmpty(functionArray, "Function list cannot be null or empty.");
        return new Function<T, R>(functionArray){
            final Function[] val$fns;
            {
                this.val$fns = functionArray;
            }

            @Override
            public R apply(T t) {
                for (Function function : this.val$fns) {
                    Assert.notNull(function, "Function cannot be null.");
                    Object r = function.apply(t);
                    if (r == null) continue;
                    return r;
                }
                return null;
            }
        };
    }
}

