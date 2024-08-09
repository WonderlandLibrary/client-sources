/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.util;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface Function3<T1, T2, T3, R> {
    public R apply(T1 var1, T2 var2, T3 var3);

    default public Function<T1, BiFunction<T2, T3, R>> curry() {
        return this::lambda$curry$1;
    }

    default public BiFunction<T1, T2, Function<T3, R>> curry2() {
        return this::lambda$curry2$3;
    }

    private Function lambda$curry2$3(Object object, Object object2) {
        return arg_0 -> this.lambda$null$2(object, object2, arg_0);
    }

    private Object lambda$null$2(Object object, Object object2, Object object3) {
        return this.apply(object, object2, object3);
    }

    private BiFunction lambda$curry$1(Object object) {
        return (arg_0, arg_1) -> this.lambda$null$0(object, arg_0, arg_1);
    }

    private Object lambda$null$0(Object object, Object object2, Object object3) {
        return this.apply(object, object2, object3);
    }
}

