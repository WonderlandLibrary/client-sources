/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.util;

import com.mojang.datafixers.util.Function3;
import com.mojang.datafixers.util.Function4;
import com.mojang.datafixers.util.Function5;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface Function6<T1, T2, T3, T4, T5, T6, R> {
    public R apply(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6);

    default public Function<T1, Function5<T2, T3, T4, T5, T6, R>> curry() {
        return this::lambda$curry$1;
    }

    default public BiFunction<T1, T2, Function4<T3, T4, T5, T6, R>> curry2() {
        return this::lambda$curry2$3;
    }

    default public Function3<T1, T2, T3, Function3<T4, T5, T6, R>> curry3() {
        return this::lambda$curry3$5;
    }

    default public Function4<T1, T2, T3, T4, BiFunction<T5, T6, R>> curry4() {
        return this::lambda$curry4$7;
    }

    default public Function5<T1, T2, T3, T4, T5, Function<T6, R>> curry5() {
        return this::lambda$curry5$9;
    }

    private Function lambda$curry5$9(Object object, Object object2, Object object3, Object object4, Object object5) {
        return arg_0 -> this.lambda$null$8(object, object2, object3, object4, object5, arg_0);
    }

    private Object lambda$null$8(Object object, Object object2, Object object3, Object object4, Object object5, Object object6) {
        return this.apply(object, object2, object3, object4, object5, object6);
    }

    private BiFunction lambda$curry4$7(Object object, Object object2, Object object3, Object object4) {
        return (arg_0, arg_1) -> this.lambda$null$6(object, object2, object3, object4, arg_0, arg_1);
    }

    private Object lambda$null$6(Object object, Object object2, Object object3, Object object4, Object object5, Object object6) {
        return this.apply(object, object2, object3, object4, object5, object6);
    }

    private Function3 lambda$curry3$5(Object object, Object object2, Object object3) {
        return (arg_0, arg_1, arg_2) -> this.lambda$null$4(object, object2, object3, arg_0, arg_1, arg_2);
    }

    private Object lambda$null$4(Object object, Object object2, Object object3, Object object4, Object object5, Object object6) {
        return this.apply(object, object2, object3, object4, object5, object6);
    }

    private Function4 lambda$curry2$3(Object object, Object object2) {
        return (arg_0, arg_1, arg_2, arg_3) -> this.lambda$null$2(object, object2, arg_0, arg_1, arg_2, arg_3);
    }

    private Object lambda$null$2(Object object, Object object2, Object object3, Object object4, Object object5, Object object6) {
        return this.apply(object, object2, object3, object4, object5, object6);
    }

    private Function5 lambda$curry$1(Object object) {
        return (arg_0, arg_1, arg_2, arg_3, arg_4) -> this.lambda$null$0(object, arg_0, arg_1, arg_2, arg_3, arg_4);
    }

    private Object lambda$null$0(Object object, Object object2, Object object3, Object object4, Object object5, Object object6) {
        return this.apply(object, object2, object3, object4, object5, object6);
    }
}

