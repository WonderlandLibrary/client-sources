/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.util;

import com.mojang.datafixers.util.Function10;
import com.mojang.datafixers.util.Function11;
import com.mojang.datafixers.util.Function12;
import com.mojang.datafixers.util.Function13;
import com.mojang.datafixers.util.Function14;
import com.mojang.datafixers.util.Function15;
import com.mojang.datafixers.util.Function3;
import com.mojang.datafixers.util.Function4;
import com.mojang.datafixers.util.Function5;
import com.mojang.datafixers.util.Function6;
import com.mojang.datafixers.util.Function7;
import com.mojang.datafixers.util.Function8;
import com.mojang.datafixers.util.Function9;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface Function16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, R> {
    public R apply(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9, T10 var10, T11 var11, T12 var12, T13 var13, T14 var14, T15 var15, T16 var16);

    default public Function<T1, Function15<T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, R>> curry() {
        return this::lambda$curry$1;
    }

    default public BiFunction<T1, T2, Function14<T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, R>> curry2() {
        return this::lambda$curry2$3;
    }

    default public Function3<T1, T2, T3, Function13<T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, R>> curry3() {
        return this::lambda$curry3$5;
    }

    default public Function4<T1, T2, T3, T4, Function12<T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, R>> curry4() {
        return this::lambda$curry4$7;
    }

    default public Function5<T1, T2, T3, T4, T5, Function11<T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, R>> curry5() {
        return this::lambda$curry5$9;
    }

    default public Function6<T1, T2, T3, T4, T5, T6, Function10<T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, R>> curry6() {
        return this::lambda$curry6$11;
    }

    default public Function7<T1, T2, T3, T4, T5, T6, T7, Function9<T8, T9, T10, T11, T12, T13, T14, T15, T16, R>> curry7() {
        return this::lambda$curry7$13;
    }

    default public Function8<T1, T2, T3, T4, T5, T6, T7, T8, Function8<T9, T10, T11, T12, T13, T14, T15, T16, R>> curry8() {
        return this::lambda$curry8$15;
    }

    default public Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, Function7<T10, T11, T12, T13, T14, T15, T16, R>> curry9() {
        return this::lambda$curry9$17;
    }

    default public Function10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, Function6<T11, T12, T13, T14, T15, T16, R>> curry10() {
        return this::lambda$curry10$19;
    }

    default public Function11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, Function5<T12, T13, T14, T15, T16, R>> curry11() {
        return this::lambda$curry11$21;
    }

    default public Function12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, Function4<T13, T14, T15, T16, R>> curry12() {
        return this::lambda$curry12$23;
    }

    default public Function13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, Function3<T14, T15, T16, R>> curry13() {
        return this::lambda$curry13$25;
    }

    default public Function14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, BiFunction<T15, T16, R>> curry14() {
        return this::lambda$curry14$27;
    }

    default public Function15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, Function<T16, R>> curry15() {
        return this::lambda$curry15$29;
    }

    private Function lambda$curry15$29(Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10, Object object11, Object object12, Object object13, Object object14, Object object15) {
        return arg_0 -> this.lambda$null$28(object, object2, object3, object4, object5, object6, object7, object8, object9, object10, object11, object12, object13, object14, object15, arg_0);
    }

    private Object lambda$null$28(Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10, Object object11, Object object12, Object object13, Object object14, Object object15, Object object16) {
        return this.apply(object, object2, object3, object4, object5, object6, object7, object8, object9, object10, object11, object12, object13, object14, object15, object16);
    }

    private BiFunction lambda$curry14$27(Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10, Object object11, Object object12, Object object13, Object object14) {
        return (arg_0, arg_1) -> this.lambda$null$26(object, object2, object3, object4, object5, object6, object7, object8, object9, object10, object11, object12, object13, object14, arg_0, arg_1);
    }

    private Object lambda$null$26(Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10, Object object11, Object object12, Object object13, Object object14, Object object15, Object object16) {
        return this.apply(object, object2, object3, object4, object5, object6, object7, object8, object9, object10, object11, object12, object13, object14, object15, object16);
    }

    private Function3 lambda$curry13$25(Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10, Object object11, Object object12, Object object13) {
        return (arg_0, arg_1, arg_2) -> this.lambda$null$24(object, object2, object3, object4, object5, object6, object7, object8, object9, object10, object11, object12, object13, arg_0, arg_1, arg_2);
    }

    private Object lambda$null$24(Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10, Object object11, Object object12, Object object13, Object object14, Object object15, Object object16) {
        return this.apply(object, object2, object3, object4, object5, object6, object7, object8, object9, object10, object11, object12, object13, object14, object15, object16);
    }

    private Function4 lambda$curry12$23(Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10, Object object11, Object object12) {
        return (arg_0, arg_1, arg_2, arg_3) -> this.lambda$null$22(object, object2, object3, object4, object5, object6, object7, object8, object9, object10, object11, object12, arg_0, arg_1, arg_2, arg_3);
    }

    private Object lambda$null$22(Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10, Object object11, Object object12, Object object13, Object object14, Object object15, Object object16) {
        return this.apply(object, object2, object3, object4, object5, object6, object7, object8, object9, object10, object11, object12, object13, object14, object15, object16);
    }

    private Function5 lambda$curry11$21(Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10, Object object11) {
        return (arg_0, arg_1, arg_2, arg_3, arg_4) -> this.lambda$null$20(object, object2, object3, object4, object5, object6, object7, object8, object9, object10, object11, arg_0, arg_1, arg_2, arg_3, arg_4);
    }

    private Object lambda$null$20(Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10, Object object11, Object object12, Object object13, Object object14, Object object15, Object object16) {
        return this.apply(object, object2, object3, object4, object5, object6, object7, object8, object9, object10, object11, object12, object13, object14, object15, object16);
    }

    private Function6 lambda$curry10$19(Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10) {
        return (arg_0, arg_1, arg_2, arg_3, arg_4, arg_5) -> this.lambda$null$18(object, object2, object3, object4, object5, object6, object7, object8, object9, object10, arg_0, arg_1, arg_2, arg_3, arg_4, arg_5);
    }

    private Object lambda$null$18(Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10, Object object11, Object object12, Object object13, Object object14, Object object15, Object object16) {
        return this.apply(object, object2, object3, object4, object5, object6, object7, object8, object9, object10, object11, object12, object13, object14, object15, object16);
    }

    private Function7 lambda$curry9$17(Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9) {
        return (arg_0, arg_1, arg_2, arg_3, arg_4, arg_5, arg_6) -> this.lambda$null$16(object, object2, object3, object4, object5, object6, object7, object8, object9, arg_0, arg_1, arg_2, arg_3, arg_4, arg_5, arg_6);
    }

    private Object lambda$null$16(Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10, Object object11, Object object12, Object object13, Object object14, Object object15, Object object16) {
        return this.apply(object, object2, object3, object4, object5, object6, object7, object8, object9, object10, object11, object12, object13, object14, object15, object16);
    }

    private Function8 lambda$curry8$15(Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8) {
        return (arg_0, arg_1, arg_2, arg_3, arg_4, arg_5, arg_6, arg_7) -> this.lambda$null$14(object, object2, object3, object4, object5, object6, object7, object8, arg_0, arg_1, arg_2, arg_3, arg_4, arg_5, arg_6, arg_7);
    }

    private Object lambda$null$14(Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10, Object object11, Object object12, Object object13, Object object14, Object object15, Object object16) {
        return this.apply(object, object2, object3, object4, object5, object6, object7, object8, object9, object10, object11, object12, object13, object14, object15, object16);
    }

    private Function9 lambda$curry7$13(Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7) {
        return (arg_0, arg_1, arg_2, arg_3, arg_4, arg_5, arg_6, arg_7, arg_8) -> this.lambda$null$12(object, object2, object3, object4, object5, object6, object7, arg_0, arg_1, arg_2, arg_3, arg_4, arg_5, arg_6, arg_7, arg_8);
    }

    private Object lambda$null$12(Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10, Object object11, Object object12, Object object13, Object object14, Object object15, Object object16) {
        return this.apply(object, object2, object3, object4, object5, object6, object7, object8, object9, object10, object11, object12, object13, object14, object15, object16);
    }

    private Function10 lambda$curry6$11(Object object, Object object2, Object object3, Object object4, Object object5, Object object6) {
        return (arg_0, arg_1, arg_2, arg_3, arg_4, arg_5, arg_6, arg_7, arg_8, arg_9) -> this.lambda$null$10(object, object2, object3, object4, object5, object6, arg_0, arg_1, arg_2, arg_3, arg_4, arg_5, arg_6, arg_7, arg_8, arg_9);
    }

    private Object lambda$null$10(Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10, Object object11, Object object12, Object object13, Object object14, Object object15, Object object16) {
        return this.apply(object, object2, object3, object4, object5, object6, object7, object8, object9, object10, object11, object12, object13, object14, object15, object16);
    }

    private Function11 lambda$curry5$9(Object object, Object object2, Object object3, Object object4, Object object5) {
        return (arg_0, arg_1, arg_2, arg_3, arg_4, arg_5, arg_6, arg_7, arg_8, arg_9, arg_10) -> this.lambda$null$8(object, object2, object3, object4, object5, arg_0, arg_1, arg_2, arg_3, arg_4, arg_5, arg_6, arg_7, arg_8, arg_9, arg_10);
    }

    private Object lambda$null$8(Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10, Object object11, Object object12, Object object13, Object object14, Object object15, Object object16) {
        return this.apply(object, object2, object3, object4, object5, object6, object7, object8, object9, object10, object11, object12, object13, object14, object15, object16);
    }

    private Function12 lambda$curry4$7(Object object, Object object2, Object object3, Object object4) {
        return (arg_0, arg_1, arg_2, arg_3, arg_4, arg_5, arg_6, arg_7, arg_8, arg_9, arg_10, arg_11) -> this.lambda$null$6(object, object2, object3, object4, arg_0, arg_1, arg_2, arg_3, arg_4, arg_5, arg_6, arg_7, arg_8, arg_9, arg_10, arg_11);
    }

    private Object lambda$null$6(Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10, Object object11, Object object12, Object object13, Object object14, Object object15, Object object16) {
        return this.apply(object, object2, object3, object4, object5, object6, object7, object8, object9, object10, object11, object12, object13, object14, object15, object16);
    }

    private Function13 lambda$curry3$5(Object object, Object object2, Object object3) {
        return (arg_0, arg_1, arg_2, arg_3, arg_4, arg_5, arg_6, arg_7, arg_8, arg_9, arg_10, arg_11, arg_12) -> this.lambda$null$4(object, object2, object3, arg_0, arg_1, arg_2, arg_3, arg_4, arg_5, arg_6, arg_7, arg_8, arg_9, arg_10, arg_11, arg_12);
    }

    private Object lambda$null$4(Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10, Object object11, Object object12, Object object13, Object object14, Object object15, Object object16) {
        return this.apply(object, object2, object3, object4, object5, object6, object7, object8, object9, object10, object11, object12, object13, object14, object15, object16);
    }

    private Function14 lambda$curry2$3(Object object, Object object2) {
        return (arg_0, arg_1, arg_2, arg_3, arg_4, arg_5, arg_6, arg_7, arg_8, arg_9, arg_10, arg_11, arg_12, arg_13) -> this.lambda$null$2(object, object2, arg_0, arg_1, arg_2, arg_3, arg_4, arg_5, arg_6, arg_7, arg_8, arg_9, arg_10, arg_11, arg_12, arg_13);
    }

    private Object lambda$null$2(Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10, Object object11, Object object12, Object object13, Object object14, Object object15, Object object16) {
        return this.apply(object, object2, object3, object4, object5, object6, object7, object8, object9, object10, object11, object12, object13, object14, object15, object16);
    }

    private Function15 lambda$curry$1(Object object) {
        return (arg_0, arg_1, arg_2, arg_3, arg_4, arg_5, arg_6, arg_7, arg_8, arg_9, arg_10, arg_11, arg_12, arg_13, arg_14) -> this.lambda$null$0(object, arg_0, arg_1, arg_2, arg_3, arg_4, arg_5, arg_6, arg_7, arg_8, arg_9, arg_10, arg_11, arg_12, arg_13, arg_14);
    }

    private Object lambda$null$0(Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10, Object object11, Object object12, Object object13, Object object14, Object object15, Object object16) {
        return this.apply(object, object2, object3, object4, object5, object6, object7, object8, object9, object10, object11, object12, object13, object14, object15, object16);
    }
}

