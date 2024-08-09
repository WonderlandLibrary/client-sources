/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.util;

import com.mojang.datafixers.util.Function10;
import com.mojang.datafixers.util.Function3;
import com.mojang.datafixers.util.Function4;
import com.mojang.datafixers.util.Function5;
import com.mojang.datafixers.util.Function6;
import com.mojang.datafixers.util.Function7;
import com.mojang.datafixers.util.Function8;
import com.mojang.datafixers.util.Function9;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface Function11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, R> {
    public R apply(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9, T10 var10, T11 var11);

    default public Function<T1, Function10<T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, R>> curry() {
        return this::lambda$curry$1;
    }

    default public BiFunction<T1, T2, Function9<T3, T4, T5, T6, T7, T8, T9, T10, T11, R>> curry2() {
        return this::lambda$curry2$3;
    }

    default public Function3<T1, T2, T3, Function8<T4, T5, T6, T7, T8, T9, T10, T11, R>> curry3() {
        return this::lambda$curry3$5;
    }

    default public Function4<T1, T2, T3, T4, Function7<T5, T6, T7, T8, T9, T10, T11, R>> curry4() {
        return this::lambda$curry4$7;
    }

    default public Function5<T1, T2, T3, T4, T5, Function6<T6, T7, T8, T9, T10, T11, R>> curry5() {
        return this::lambda$curry5$9;
    }

    default public Function6<T1, T2, T3, T4, T5, T6, Function5<T7, T8, T9, T10, T11, R>> curry6() {
        return this::lambda$curry6$11;
    }

    default public Function7<T1, T2, T3, T4, T5, T6, T7, Function4<T8, T9, T10, T11, R>> curry7() {
        return this::lambda$curry7$13;
    }

    default public Function8<T1, T2, T3, T4, T5, T6, T7, T8, Function3<T9, T10, T11, R>> curry8() {
        return this::lambda$curry8$15;
    }

    default public Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, BiFunction<T10, T11, R>> curry9() {
        return this::lambda$curry9$17;
    }

    default public Function10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, Function<T11, R>> curry10() {
        return this::lambda$curry10$19;
    }

    private Function lambda$curry10$19(Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10) {
        return arg_0 -> this.lambda$null$18(object, object2, object3, object4, object5, object6, object7, object8, object9, object10, arg_0);
    }

    private Object lambda$null$18(Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10, Object object11) {
        return this.apply(object, object2, object3, object4, object5, object6, object7, object8, object9, object10, object11);
    }

    private BiFunction lambda$curry9$17(Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9) {
        return (arg_0, arg_1) -> this.lambda$null$16(object, object2, object3, object4, object5, object6, object7, object8, object9, arg_0, arg_1);
    }

    private Object lambda$null$16(Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10, Object object11) {
        return this.apply(object, object2, object3, object4, object5, object6, object7, object8, object9, object10, object11);
    }

    private Function3 lambda$curry8$15(Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8) {
        return (arg_0, arg_1, arg_2) -> this.lambda$null$14(object, object2, object3, object4, object5, object6, object7, object8, arg_0, arg_1, arg_2);
    }

    private Object lambda$null$14(Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10, Object object11) {
        return this.apply(object, object2, object3, object4, object5, object6, object7, object8, object9, object10, object11);
    }

    private Function4 lambda$curry7$13(Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7) {
        return (arg_0, arg_1, arg_2, arg_3) -> this.lambda$null$12(object, object2, object3, object4, object5, object6, object7, arg_0, arg_1, arg_2, arg_3);
    }

    private Object lambda$null$12(Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10, Object object11) {
        return this.apply(object, object2, object3, object4, object5, object6, object7, object8, object9, object10, object11);
    }

    private Function5 lambda$curry6$11(Object object, Object object2, Object object3, Object object4, Object object5, Object object6) {
        return (arg_0, arg_1, arg_2, arg_3, arg_4) -> this.lambda$null$10(object, object2, object3, object4, object5, object6, arg_0, arg_1, arg_2, arg_3, arg_4);
    }

    private Object lambda$null$10(Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10, Object object11) {
        return this.apply(object, object2, object3, object4, object5, object6, object7, object8, object9, object10, object11);
    }

    private Function6 lambda$curry5$9(Object object, Object object2, Object object3, Object object4, Object object5) {
        return (arg_0, arg_1, arg_2, arg_3, arg_4, arg_5) -> this.lambda$null$8(object, object2, object3, object4, object5, arg_0, arg_1, arg_2, arg_3, arg_4, arg_5);
    }

    private Object lambda$null$8(Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10, Object object11) {
        return this.apply(object, object2, object3, object4, object5, object6, object7, object8, object9, object10, object11);
    }

    private Function7 lambda$curry4$7(Object object, Object object2, Object object3, Object object4) {
        return (arg_0, arg_1, arg_2, arg_3, arg_4, arg_5, arg_6) -> this.lambda$null$6(object, object2, object3, object4, arg_0, arg_1, arg_2, arg_3, arg_4, arg_5, arg_6);
    }

    private Object lambda$null$6(Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10, Object object11) {
        return this.apply(object, object2, object3, object4, object5, object6, object7, object8, object9, object10, object11);
    }

    private Function8 lambda$curry3$5(Object object, Object object2, Object object3) {
        return (arg_0, arg_1, arg_2, arg_3, arg_4, arg_5, arg_6, arg_7) -> this.lambda$null$4(object, object2, object3, arg_0, arg_1, arg_2, arg_3, arg_4, arg_5, arg_6, arg_7);
    }

    private Object lambda$null$4(Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10, Object object11) {
        return this.apply(object, object2, object3, object4, object5, object6, object7, object8, object9, object10, object11);
    }

    private Function9 lambda$curry2$3(Object object, Object object2) {
        return (arg_0, arg_1, arg_2, arg_3, arg_4, arg_5, arg_6, arg_7, arg_8) -> this.lambda$null$2(object, object2, arg_0, arg_1, arg_2, arg_3, arg_4, arg_5, arg_6, arg_7, arg_8);
    }

    private Object lambda$null$2(Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10, Object object11) {
        return this.apply(object, object2, object3, object4, object5, object6, object7, object8, object9, object10, object11);
    }

    private Function10 lambda$curry$1(Object object) {
        return (arg_0, arg_1, arg_2, arg_3, arg_4, arg_5, arg_6, arg_7, arg_8, arg_9) -> this.lambda$null$0(object, arg_0, arg_1, arg_2, arg_3, arg_4, arg_5, arg_6, arg_7, arg_8, arg_9);
    }

    private Object lambda$null$0(Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10, Object object11) {
        return this.apply(object, object2, object3, object4, object5, object6, object7, object8, object9, object10, object11);
    }
}

