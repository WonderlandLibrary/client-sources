/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.util;

import com.mojang.datafixers.util.Function3;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface Function4<T1, T2, T3, T4, R> {
    public R apply(T1 var1, T2 var2, T3 var3, T4 var4);

    default public Function<T1, Function3<T2, T3, T4, R>> curry() {
        return this::lambda$curry$1;
    }

    default public BiFunction<T1, T2, BiFunction<T3, T4, R>> curry2() {
        return this::lambda$curry2$3;
    }

    default public Function3<T1, T2, T3, Function<T4, R>> curry3() {
        return this::lambda$curry3$5;
    }

    private Function lambda$curry3$5(Object object, Object object2, Object object3) {
        return arg_0 -> this.lambda$null$4(object, object2, object3, arg_0);
    }

    private Object lambda$null$4(Object object, Object object2, Object object3, Object object4) {
        return this.apply(object, object2, object3, object4);
    }

    private BiFunction lambda$curry2$3(Object object, Object object2) {
        return (arg_0, arg_1) -> this.lambda$null$2(object, object2, arg_0, arg_1);
    }

    private Object lambda$null$2(Object object, Object object2, Object object3, Object object4) {
        return this.apply(object, object2, object3, object4);
    }

    private Function3 lambda$curry$1(Object object) {
        return (arg_0, arg_1, arg_2) -> this.lambda$null$0(object, arg_0, arg_1, arg_2);
    }

    private Object lambda$null$0(Object object, Object object2, Object object3, Object object4) {
        return this.apply(object, object2, object3, object4);
    }
}

