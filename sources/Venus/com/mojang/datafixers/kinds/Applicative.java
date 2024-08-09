/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.kinds;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Functor;
import com.mojang.datafixers.kinds.K1;
import com.mojang.datafixers.util.Function10;
import com.mojang.datafixers.util.Function11;
import com.mojang.datafixers.util.Function12;
import com.mojang.datafixers.util.Function13;
import com.mojang.datafixers.util.Function14;
import com.mojang.datafixers.util.Function15;
import com.mojang.datafixers.util.Function16;
import com.mojang.datafixers.util.Function3;
import com.mojang.datafixers.util.Function4;
import com.mojang.datafixers.util.Function5;
import com.mojang.datafixers.util.Function6;
import com.mojang.datafixers.util.Function7;
import com.mojang.datafixers.util.Function8;
import com.mojang.datafixers.util.Function9;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface Applicative<F extends K1, Mu extends Mu>
extends Functor<F, Mu> {
    public static <F extends K1, Mu extends Mu> Applicative<F, Mu> unbox(App<Mu, F> app) {
        return (Applicative)app;
    }

    public <A> App<F, A> point(A var1);

    public <A, R> Function<App<F, A>, App<F, R>> lift1(App<F, Function<A, R>> var1);

    default public <A, B, R> BiFunction<App<F, A>, App<F, B>, App<F, R>> lift2(App<F, BiFunction<A, B, R>> app) {
        return (arg_0, arg_1) -> this.lambda$lift2$0(app, arg_0, arg_1);
    }

    default public <T1, T2, T3, R> Function3<App<F, T1>, App<F, T2>, App<F, T3>, App<F, R>> lift3(App<F, Function3<T1, T2, T3, R>> app) {
        return (arg_0, arg_1, arg_2) -> this.lambda$lift3$1(app, arg_0, arg_1, arg_2);
    }

    default public <T1, T2, T3, T4, R> Function4<App<F, T1>, App<F, T2>, App<F, T3>, App<F, T4>, App<F, R>> lift4(App<F, Function4<T1, T2, T3, T4, R>> app) {
        return (arg_0, arg_1, arg_2, arg_3) -> this.lambda$lift4$2(app, arg_0, arg_1, arg_2, arg_3);
    }

    default public <T1, T2, T3, T4, T5, R> Function5<App<F, T1>, App<F, T2>, App<F, T3>, App<F, T4>, App<F, T5>, App<F, R>> lift5(App<F, Function5<T1, T2, T3, T4, T5, R>> app) {
        return (arg_0, arg_1, arg_2, arg_3, arg_4) -> this.lambda$lift5$3(app, arg_0, arg_1, arg_2, arg_3, arg_4);
    }

    default public <T1, T2, T3, T4, T5, T6, R> Function6<App<F, T1>, App<F, T2>, App<F, T3>, App<F, T4>, App<F, T5>, App<F, T6>, App<F, R>> lift6(App<F, Function6<T1, T2, T3, T4, T5, T6, R>> app) {
        return (arg_0, arg_1, arg_2, arg_3, arg_4, arg_5) -> this.lambda$lift6$4(app, arg_0, arg_1, arg_2, arg_3, arg_4, arg_5);
    }

    default public <T1, T2, T3, T4, T5, T6, T7, R> Function7<App<F, T1>, App<F, T2>, App<F, T3>, App<F, T4>, App<F, T5>, App<F, T6>, App<F, T7>, App<F, R>> lift7(App<F, Function7<T1, T2, T3, T4, T5, T6, T7, R>> app) {
        return (arg_0, arg_1, arg_2, arg_3, arg_4, arg_5, arg_6) -> this.lambda$lift7$5(app, arg_0, arg_1, arg_2, arg_3, arg_4, arg_5, arg_6);
    }

    default public <T1, T2, T3, T4, T5, T6, T7, T8, R> Function8<App<F, T1>, App<F, T2>, App<F, T3>, App<F, T4>, App<F, T5>, App<F, T6>, App<F, T7>, App<F, T8>, App<F, R>> lift8(App<F, Function8<T1, T2, T3, T4, T5, T6, T7, T8, R>> app) {
        return (arg_0, arg_1, arg_2, arg_3, arg_4, arg_5, arg_6, arg_7) -> this.lambda$lift8$6(app, arg_0, arg_1, arg_2, arg_3, arg_4, arg_5, arg_6, arg_7);
    }

    default public <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> Function9<App<F, T1>, App<F, T2>, App<F, T3>, App<F, T4>, App<F, T5>, App<F, T6>, App<F, T7>, App<F, T8>, App<F, T9>, App<F, R>> lift9(App<F, Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, R>> app) {
        return (arg_0, arg_1, arg_2, arg_3, arg_4, arg_5, arg_6, arg_7, arg_8) -> this.lambda$lift9$7(app, arg_0, arg_1, arg_2, arg_3, arg_4, arg_5, arg_6, arg_7, arg_8);
    }

    default public <A, R> App<F, R> ap(App<F, Function<A, R>> app, App<F, A> app2) {
        return this.lift1(app).apply(app2);
    }

    default public <A, R> App<F, R> ap(Function<A, R> function, App<F, A> app) {
        return this.map(function, app);
    }

    default public <A, B, R> App<F, R> ap2(App<F, BiFunction<A, B, R>> app, App<F, A> app2, App<F, B> app3) {
        Function<BiFunction, Function> function = Applicative::lambda$ap2$10;
        return this.ap(this.ap(this.map(function, app), app2), app3);
    }

    default public <T1, T2, T3, R> App<F, R> ap3(App<F, Function3<T1, T2, T3, R>> app, App<F, T1> app2, App<F, T2> app3, App<F, T3> app4) {
        return this.ap2(this.ap(this.map(Function3::curry, app), app2), app3, app4);
    }

    default public <T1, T2, T3, T4, R> App<F, R> ap4(App<F, Function4<T1, T2, T3, T4, R>> app, App<F, T1> app2, App<F, T2> app3, App<F, T3> app4, App<F, T4> app5) {
        return this.ap2(this.ap2(this.map(Function4::curry2, app), app2, app3), app4, app5);
    }

    default public <T1, T2, T3, T4, T5, R> App<F, R> ap5(App<F, Function5<T1, T2, T3, T4, T5, R>> app, App<F, T1> app2, App<F, T2> app3, App<F, T3> app4, App<F, T4> app5, App<F, T5> app6) {
        return this.ap3(this.ap2(this.map(Function5::curry2, app), app2, app3), app4, app5, app6);
    }

    default public <T1, T2, T3, T4, T5, T6, R> App<F, R> ap6(App<F, Function6<T1, T2, T3, T4, T5, T6, R>> app, App<F, T1> app2, App<F, T2> app3, App<F, T3> app4, App<F, T4> app5, App<F, T5> app6, App<F, T6> app7) {
        return this.ap3(this.ap3(this.map(Function6::curry3, app), app2, app3, app4), app5, app6, app7);
    }

    default public <T1, T2, T3, T4, T5, T6, T7, R> App<F, R> ap7(App<F, Function7<T1, T2, T3, T4, T5, T6, T7, R>> app, App<F, T1> app2, App<F, T2> app3, App<F, T3> app4, App<F, T4> app5, App<F, T5> app6, App<F, T6> app7, App<F, T7> app8) {
        return this.ap4(this.ap3(this.map(Function7::curry3, app), app2, app3, app4), app5, app6, app7, app8);
    }

    default public <T1, T2, T3, T4, T5, T6, T7, T8, R> App<F, R> ap8(App<F, Function8<T1, T2, T3, T4, T5, T6, T7, T8, R>> app, App<F, T1> app2, App<F, T2> app3, App<F, T3> app4, App<F, T4> app5, App<F, T5> app6, App<F, T6> app7, App<F, T7> app8, App<F, T8> app9) {
        return this.ap4(this.ap4(this.map(Function8::curry4, app), app2, app3, app4, app5), app6, app7, app8, app9);
    }

    default public <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> App<F, R> ap9(App<F, Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, R>> app, App<F, T1> app2, App<F, T2> app3, App<F, T3> app4, App<F, T4> app5, App<F, T5> app6, App<F, T6> app7, App<F, T7> app8, App<F, T8> app9, App<F, T9> app10) {
        return this.ap5(this.ap4(this.map(Function9::curry4, app), app2, app3, app4, app5), app6, app7, app8, app9, app10);
    }

    default public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R> App<F, R> ap10(App<F, Function10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R>> app, App<F, T1> app2, App<F, T2> app3, App<F, T3> app4, App<F, T4> app5, App<F, T5> app6, App<F, T6> app7, App<F, T7> app8, App<F, T8> app9, App<F, T9> app10, App<F, T10> app11) {
        return this.ap5(this.ap5(this.map(Function10::curry5, app), app2, app3, app4, app5, app6), app7, app8, app9, app10, app11);
    }

    default public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, R> App<F, R> ap11(App<F, Function11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, R>> app, App<F, T1> app2, App<F, T2> app3, App<F, T3> app4, App<F, T4> app5, App<F, T5> app6, App<F, T6> app7, App<F, T7> app8, App<F, T8> app9, App<F, T9> app10, App<F, T10> app11, App<F, T11> app12) {
        return this.ap6(this.ap5(this.map(Function11::curry5, app), app2, app3, app4, app5, app6), app7, app8, app9, app10, app11, app12);
    }

    default public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, R> App<F, R> ap12(App<F, Function12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, R>> app, App<F, T1> app2, App<F, T2> app3, App<F, T3> app4, App<F, T4> app5, App<F, T5> app6, App<F, T6> app7, App<F, T7> app8, App<F, T8> app9, App<F, T9> app10, App<F, T10> app11, App<F, T11> app12, App<F, T12> app13) {
        return this.ap6(this.ap6(this.map(Function12::curry6, app), app2, app3, app4, app5, app6, app7), app8, app9, app10, app11, app12, app13);
    }

    default public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, R> App<F, R> ap13(App<F, Function13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, R>> app, App<F, T1> app2, App<F, T2> app3, App<F, T3> app4, App<F, T4> app5, App<F, T5> app6, App<F, T6> app7, App<F, T7> app8, App<F, T8> app9, App<F, T9> app10, App<F, T10> app11, App<F, T11> app12, App<F, T12> app13, App<F, T13> app14) {
        return this.ap7(this.ap6(this.map(Function13::curry6, app), app2, app3, app4, app5, app6, app7), app8, app9, app10, app11, app12, app13, app14);
    }

    default public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, R> App<F, R> ap14(App<F, Function14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, R>> app, App<F, T1> app2, App<F, T2> app3, App<F, T3> app4, App<F, T4> app5, App<F, T5> app6, App<F, T6> app7, App<F, T7> app8, App<F, T8> app9, App<F, T9> app10, App<F, T10> app11, App<F, T11> app12, App<F, T12> app13, App<F, T13> app14, App<F, T14> app15) {
        return this.ap7(this.ap7(this.map(Function14::curry7, app), app2, app3, app4, app5, app6, app7, app8), app9, app10, app11, app12, app13, app14, app15);
    }

    default public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, R> App<F, R> ap15(App<F, Function15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, R>> app, App<F, T1> app2, App<F, T2> app3, App<F, T3> app4, App<F, T4> app5, App<F, T5> app6, App<F, T6> app7, App<F, T7> app8, App<F, T8> app9, App<F, T9> app10, App<F, T10> app11, App<F, T11> app12, App<F, T12> app13, App<F, T13> app14, App<F, T14> app15, App<F, T15> app16) {
        return this.ap8(this.ap7(this.map(Function15::curry7, app), app2, app3, app4, app5, app6, app7, app8), app9, app10, app11, app12, app13, app14, app15, app16);
    }

    default public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, R> App<F, R> ap16(App<F, Function16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, R>> app, App<F, T1> app2, App<F, T2> app3, App<F, T3> app4, App<F, T4> app5, App<F, T5> app6, App<F, T6> app7, App<F, T7> app8, App<F, T8> app9, App<F, T9> app10, App<F, T10> app11, App<F, T11> app12, App<F, T12> app13, App<F, T13> app14, App<F, T14> app15, App<F, T15> app16, App<F, T16> app17) {
        return this.ap8(this.ap8(this.map(Function16::curry8, app), app2, app3, app4, app5, app6, app7, app8, app9), app10, app11, app12, app13, app14, app15, app16, app17);
    }

    default public <A, B, R> App<F, R> apply2(BiFunction<A, B, R> biFunction, App<F, A> app, App<F, B> app2) {
        return this.ap2(this.point(biFunction), app, app2);
    }

    default public <T1, T2, T3, R> App<F, R> apply3(Function3<T1, T2, T3, R> function3, App<F, T1> app, App<F, T2> app2, App<F, T3> app3) {
        return this.ap3(this.point(function3), app, app2, app3);
    }

    default public <T1, T2, T3, T4, R> App<F, R> apply4(Function4<T1, T2, T3, T4, R> function4, App<F, T1> app, App<F, T2> app2, App<F, T3> app3, App<F, T4> app4) {
        return this.ap4(this.point(function4), app, app2, app3, app4);
    }

    default public <T1, T2, T3, T4, T5, R> App<F, R> apply5(Function5<T1, T2, T3, T4, T5, R> function5, App<F, T1> app, App<F, T2> app2, App<F, T3> app3, App<F, T4> app4, App<F, T5> app5) {
        return this.ap5(this.point(function5), app, app2, app3, app4, app5);
    }

    default public <T1, T2, T3, T4, T5, T6, R> App<F, R> apply6(Function6<T1, T2, T3, T4, T5, T6, R> function6, App<F, T1> app, App<F, T2> app2, App<F, T3> app3, App<F, T4> app4, App<F, T5> app5, App<F, T6> app6) {
        return this.ap6(this.point(function6), app, app2, app3, app4, app5, app6);
    }

    default public <T1, T2, T3, T4, T5, T6, T7, R> App<F, R> apply7(Function7<T1, T2, T3, T4, T5, T6, T7, R> function7, App<F, T1> app, App<F, T2> app2, App<F, T3> app3, App<F, T4> app4, App<F, T5> app5, App<F, T6> app6, App<F, T7> app7) {
        return this.ap7(this.point(function7), app, app2, app3, app4, app5, app6, app7);
    }

    default public <T1, T2, T3, T4, T5, T6, T7, T8, R> App<F, R> apply8(Function8<T1, T2, T3, T4, T5, T6, T7, T8, R> function8, App<F, T1> app, App<F, T2> app2, App<F, T3> app3, App<F, T4> app4, App<F, T5> app5, App<F, T6> app6, App<F, T7> app7, App<F, T8> app8) {
        return this.ap8(this.point(function8), app, app2, app3, app4, app5, app6, app7, app8);
    }

    default public <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> App<F, R> apply9(Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, R> function9, App<F, T1> app, App<F, T2> app2, App<F, T3> app3, App<F, T4> app4, App<F, T5> app5, App<F, T6> app6, App<F, T7> app7, App<F, T8> app8, App<F, T9> app9) {
        return this.ap9(this.point(function9), app, app2, app3, app4, app5, app6, app7, app8, app9);
    }

    private static Function lambda$ap2$10(BiFunction biFunction) {
        return arg_0 -> Applicative.lambda$null$9(biFunction, arg_0);
    }

    private static Function lambda$null$9(BiFunction biFunction, Object object) {
        return arg_0 -> Applicative.lambda$null$8(biFunction, object, arg_0);
    }

    private static Object lambda$null$8(BiFunction biFunction, Object object, Object object2) {
        return biFunction.apply(object, object2);
    }

    private App lambda$lift9$7(App app, App app2, App app3, App app4, App app5, App app6, App app7, App app8, App app9, App app10) {
        return this.ap9(app, app2, app3, app4, app5, app6, app7, app8, app9, app10);
    }

    private App lambda$lift8$6(App app, App app2, App app3, App app4, App app5, App app6, App app7, App app8, App app9) {
        return this.ap8(app, app2, app3, app4, app5, app6, app7, app8, app9);
    }

    private App lambda$lift7$5(App app, App app2, App app3, App app4, App app5, App app6, App app7, App app8) {
        return this.ap7(app, app2, app3, app4, app5, app6, app7, app8);
    }

    private App lambda$lift6$4(App app, App app2, App app3, App app4, App app5, App app6, App app7) {
        return this.ap6(app, app2, app3, app4, app5, app6, app7);
    }

    private App lambda$lift5$3(App app, App app2, App app3, App app4, App app5, App app6) {
        return this.ap5(app, app2, app3, app4, app5, app6);
    }

    private App lambda$lift4$2(App app, App app2, App app3, App app4, App app5) {
        return this.ap4(app, app2, app3, app4, app5);
    }

    private App lambda$lift3$1(App app, App app2, App app3, App app4) {
        return this.ap3(app, app2, app3, app4);
    }

    private App lambda$lift2$0(App app, App app2, App app3) {
        return this.ap2(app, app2, app3);
    }

    public static interface Mu
    extends Functor.Mu {
    }
}

