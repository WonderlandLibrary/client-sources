/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.kinds.IdF;
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

public interface Products {
    public static <T1, T2> P2<IdF.Mu, T1, T2> of(T1 T1, T2 T2) {
        return new P2(IdF.create(T1), IdF.create(T2));
    }

    public static final class P16<F extends K1, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        private final App<F, T1> t1;
        private final App<F, T2> t2;
        private final App<F, T3> t3;
        private final App<F, T4> t4;
        private final App<F, T5> t5;
        private final App<F, T6> t6;
        private final App<F, T7> t7;
        private final App<F, T8> t8;
        private final App<F, T9> t9;
        private final App<F, T10> t10;
        private final App<F, T11> t11;
        private final App<F, T12> t12;
        private final App<F, T13> t13;
        private final App<F, T14> t14;
        private final App<F, T15> t15;
        private final App<F, T16> t16;

        public P16(App<F, T1> app, App<F, T2> app2, App<F, T3> app3, App<F, T4> app4, App<F, T5> app5, App<F, T6> app6, App<F, T7> app7, App<F, T8> app8, App<F, T9> app9, App<F, T10> app10, App<F, T11> app11, App<F, T12> app12, App<F, T13> app13, App<F, T14> app14, App<F, T15> app15, App<F, T16> app16) {
            this.t1 = app;
            this.t2 = app2;
            this.t3 = app3;
            this.t4 = app4;
            this.t5 = app5;
            this.t6 = app6;
            this.t7 = app7;
            this.t8 = app8;
            this.t9 = app9;
            this.t10 = app10;
            this.t11 = app11;
            this.t12 = app12;
            this.t13 = app13;
            this.t14 = app14;
            this.t15 = app15;
            this.t16 = app16;
        }

        public <R> App<F, R> apply(Applicative<F, ?> applicative, Function16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, R> function16) {
            return this.apply(applicative, applicative.point(function16));
        }

        public <R> App<F, R> apply(Applicative<F, ?> applicative, App<F, Function16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, R>> app) {
            return applicative.ap16(app, this.t1, this.t2, this.t3, this.t4, this.t5, this.t6, this.t7, this.t8, this.t9, this.t10, this.t11, this.t12, this.t13, this.t14, this.t15, this.t16);
        }
    }

    public static final class P15<F extends K1, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> {
        private final App<F, T1> t1;
        private final App<F, T2> t2;
        private final App<F, T3> t3;
        private final App<F, T4> t4;
        private final App<F, T5> t5;
        private final App<F, T6> t6;
        private final App<F, T7> t7;
        private final App<F, T8> t8;
        private final App<F, T9> t9;
        private final App<F, T10> t10;
        private final App<F, T11> t11;
        private final App<F, T12> t12;
        private final App<F, T13> t13;
        private final App<F, T14> t14;
        private final App<F, T15> t15;

        public P15(App<F, T1> app, App<F, T2> app2, App<F, T3> app3, App<F, T4> app4, App<F, T5> app5, App<F, T6> app6, App<F, T7> app7, App<F, T8> app8, App<F, T9> app9, App<F, T10> app10, App<F, T11> app11, App<F, T12> app12, App<F, T13> app13, App<F, T14> app14, App<F, T15> app15) {
            this.t1 = app;
            this.t2 = app2;
            this.t3 = app3;
            this.t4 = app4;
            this.t5 = app5;
            this.t6 = app6;
            this.t7 = app7;
            this.t8 = app8;
            this.t9 = app9;
            this.t10 = app10;
            this.t11 = app11;
            this.t12 = app12;
            this.t13 = app13;
            this.t14 = app14;
            this.t15 = app15;
        }

        public <R> App<F, R> apply(Applicative<F, ?> applicative, Function15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, R> function15) {
            return this.apply(applicative, applicative.point(function15));
        }

        public <R> App<F, R> apply(Applicative<F, ?> applicative, App<F, Function15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, R>> app) {
            return applicative.ap15(app, this.t1, this.t2, this.t3, this.t4, this.t5, this.t6, this.t7, this.t8, this.t9, this.t10, this.t11, this.t12, this.t13, this.t14, this.t15);
        }
    }

    public static final class P14<F extends K1, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> {
        private final App<F, T1> t1;
        private final App<F, T2> t2;
        private final App<F, T3> t3;
        private final App<F, T4> t4;
        private final App<F, T5> t5;
        private final App<F, T6> t6;
        private final App<F, T7> t7;
        private final App<F, T8> t8;
        private final App<F, T9> t9;
        private final App<F, T10> t10;
        private final App<F, T11> t11;
        private final App<F, T12> t12;
        private final App<F, T13> t13;
        private final App<F, T14> t14;

        public P14(App<F, T1> app, App<F, T2> app2, App<F, T3> app3, App<F, T4> app4, App<F, T5> app5, App<F, T6> app6, App<F, T7> app7, App<F, T8> app8, App<F, T9> app9, App<F, T10> app10, App<F, T11> app11, App<F, T12> app12, App<F, T13> app13, App<F, T14> app14) {
            this.t1 = app;
            this.t2 = app2;
            this.t3 = app3;
            this.t4 = app4;
            this.t5 = app5;
            this.t6 = app6;
            this.t7 = app7;
            this.t8 = app8;
            this.t9 = app9;
            this.t10 = app10;
            this.t11 = app11;
            this.t12 = app12;
            this.t13 = app13;
            this.t14 = app14;
        }

        public <R> App<F, R> apply(Applicative<F, ?> applicative, Function14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, R> function14) {
            return this.apply(applicative, applicative.point(function14));
        }

        public <R> App<F, R> apply(Applicative<F, ?> applicative, App<F, Function14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, R>> app) {
            return applicative.ap14(app, this.t1, this.t2, this.t3, this.t4, this.t5, this.t6, this.t7, this.t8, this.t9, this.t10, this.t11, this.t12, this.t13, this.t14);
        }
    }

    public static final class P13<F extends K1, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> {
        private final App<F, T1> t1;
        private final App<F, T2> t2;
        private final App<F, T3> t3;
        private final App<F, T4> t4;
        private final App<F, T5> t5;
        private final App<F, T6> t6;
        private final App<F, T7> t7;
        private final App<F, T8> t8;
        private final App<F, T9> t9;
        private final App<F, T10> t10;
        private final App<F, T11> t11;
        private final App<F, T12> t12;
        private final App<F, T13> t13;

        public P13(App<F, T1> app, App<F, T2> app2, App<F, T3> app3, App<F, T4> app4, App<F, T5> app5, App<F, T6> app6, App<F, T7> app7, App<F, T8> app8, App<F, T9> app9, App<F, T10> app10, App<F, T11> app11, App<F, T12> app12, App<F, T13> app13) {
            this.t1 = app;
            this.t2 = app2;
            this.t3 = app3;
            this.t4 = app4;
            this.t5 = app5;
            this.t6 = app6;
            this.t7 = app7;
            this.t8 = app8;
            this.t9 = app9;
            this.t10 = app10;
            this.t11 = app11;
            this.t12 = app12;
            this.t13 = app13;
        }

        public <R> App<F, R> apply(Applicative<F, ?> applicative, Function13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, R> function13) {
            return this.apply(applicative, applicative.point(function13));
        }

        public <R> App<F, R> apply(Applicative<F, ?> applicative, App<F, Function13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, R>> app) {
            return applicative.ap13(app, this.t1, this.t2, this.t3, this.t4, this.t5, this.t6, this.t7, this.t8, this.t9, this.t10, this.t11, this.t12, this.t13);
        }
    }

    public static final class P12<F extends K1, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> {
        private final App<F, T1> t1;
        private final App<F, T2> t2;
        private final App<F, T3> t3;
        private final App<F, T4> t4;
        private final App<F, T5> t5;
        private final App<F, T6> t6;
        private final App<F, T7> t7;
        private final App<F, T8> t8;
        private final App<F, T9> t9;
        private final App<F, T10> t10;
        private final App<F, T11> t11;
        private final App<F, T12> t12;

        public P12(App<F, T1> app, App<F, T2> app2, App<F, T3> app3, App<F, T4> app4, App<F, T5> app5, App<F, T6> app6, App<F, T7> app7, App<F, T8> app8, App<F, T9> app9, App<F, T10> app10, App<F, T11> app11, App<F, T12> app12) {
            this.t1 = app;
            this.t2 = app2;
            this.t3 = app3;
            this.t4 = app4;
            this.t5 = app5;
            this.t6 = app6;
            this.t7 = app7;
            this.t8 = app8;
            this.t9 = app9;
            this.t10 = app10;
            this.t11 = app11;
            this.t12 = app12;
        }

        public <R> App<F, R> apply(Applicative<F, ?> applicative, Function12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, R> function12) {
            return this.apply(applicative, applicative.point(function12));
        }

        public <R> App<F, R> apply(Applicative<F, ?> applicative, App<F, Function12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, R>> app) {
            return applicative.ap12(app, this.t1, this.t2, this.t3, this.t4, this.t5, this.t6, this.t7, this.t8, this.t9, this.t10, this.t11, this.t12);
        }
    }

    public static final class P11<F extends K1, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> {
        private final App<F, T1> t1;
        private final App<F, T2> t2;
        private final App<F, T3> t3;
        private final App<F, T4> t4;
        private final App<F, T5> t5;
        private final App<F, T6> t6;
        private final App<F, T7> t7;
        private final App<F, T8> t8;
        private final App<F, T9> t9;
        private final App<F, T10> t10;
        private final App<F, T11> t11;

        public P11(App<F, T1> app, App<F, T2> app2, App<F, T3> app3, App<F, T4> app4, App<F, T5> app5, App<F, T6> app6, App<F, T7> app7, App<F, T8> app8, App<F, T9> app9, App<F, T10> app10, App<F, T11> app11) {
            this.t1 = app;
            this.t2 = app2;
            this.t3 = app3;
            this.t4 = app4;
            this.t5 = app5;
            this.t6 = app6;
            this.t7 = app7;
            this.t8 = app8;
            this.t9 = app9;
            this.t10 = app10;
            this.t11 = app11;
        }

        public <R> App<F, R> apply(Applicative<F, ?> applicative, Function11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, R> function11) {
            return this.apply(applicative, applicative.point(function11));
        }

        public <R> App<F, R> apply(Applicative<F, ?> applicative, App<F, Function11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, R>> app) {
            return applicative.ap11(app, this.t1, this.t2, this.t3, this.t4, this.t5, this.t6, this.t7, this.t8, this.t9, this.t10, this.t11);
        }
    }

    public static final class P10<F extends K1, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> {
        private final App<F, T1> t1;
        private final App<F, T2> t2;
        private final App<F, T3> t3;
        private final App<F, T4> t4;
        private final App<F, T5> t5;
        private final App<F, T6> t6;
        private final App<F, T7> t7;
        private final App<F, T8> t8;
        private final App<F, T9> t9;
        private final App<F, T10> t10;

        public P10(App<F, T1> app, App<F, T2> app2, App<F, T3> app3, App<F, T4> app4, App<F, T5> app5, App<F, T6> app6, App<F, T7> app7, App<F, T8> app8, App<F, T9> app9, App<F, T10> app10) {
            this.t1 = app;
            this.t2 = app2;
            this.t3 = app3;
            this.t4 = app4;
            this.t5 = app5;
            this.t6 = app6;
            this.t7 = app7;
            this.t8 = app8;
            this.t9 = app9;
            this.t10 = app10;
        }

        public <R> App<F, R> apply(Applicative<F, ?> applicative, Function10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R> function10) {
            return this.apply(applicative, applicative.point(function10));
        }

        public <R> App<F, R> apply(Applicative<F, ?> applicative, App<F, Function10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R>> app) {
            return applicative.ap10(app, this.t1, this.t2, this.t3, this.t4, this.t5, this.t6, this.t7, this.t8, this.t9, this.t10);
        }
    }

    public static final class P9<F extends K1, T1, T2, T3, T4, T5, T6, T7, T8, T9> {
        private final App<F, T1> t1;
        private final App<F, T2> t2;
        private final App<F, T3> t3;
        private final App<F, T4> t4;
        private final App<F, T5> t5;
        private final App<F, T6> t6;
        private final App<F, T7> t7;
        private final App<F, T8> t8;
        private final App<F, T9> t9;

        public P9(App<F, T1> app, App<F, T2> app2, App<F, T3> app3, App<F, T4> app4, App<F, T5> app5, App<F, T6> app6, App<F, T7> app7, App<F, T8> app8, App<F, T9> app9) {
            this.t1 = app;
            this.t2 = app2;
            this.t3 = app3;
            this.t4 = app4;
            this.t5 = app5;
            this.t6 = app6;
            this.t7 = app7;
            this.t8 = app8;
            this.t9 = app9;
        }

        public <R> App<F, R> apply(Applicative<F, ?> applicative, Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, R> function9) {
            return this.apply(applicative, applicative.point(function9));
        }

        public <R> App<F, R> apply(Applicative<F, ?> applicative, App<F, Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, R>> app) {
            return applicative.ap9(app, this.t1, this.t2, this.t3, this.t4, this.t5, this.t6, this.t7, this.t8, this.t9);
        }
    }

    public static final class P8<F extends K1, T1, T2, T3, T4, T5, T6, T7, T8> {
        private final App<F, T1> t1;
        private final App<F, T2> t2;
        private final App<F, T3> t3;
        private final App<F, T4> t4;
        private final App<F, T5> t5;
        private final App<F, T6> t6;
        private final App<F, T7> t7;
        private final App<F, T8> t8;

        public P8(App<F, T1> app, App<F, T2> app2, App<F, T3> app3, App<F, T4> app4, App<F, T5> app5, App<F, T6> app6, App<F, T7> app7, App<F, T8> app8) {
            this.t1 = app;
            this.t2 = app2;
            this.t3 = app3;
            this.t4 = app4;
            this.t5 = app5;
            this.t6 = app6;
            this.t7 = app7;
            this.t8 = app8;
        }

        public App<F, T1> t1() {
            return this.t1;
        }

        public App<F, T2> t2() {
            return this.t2;
        }

        public App<F, T3> t3() {
            return this.t3;
        }

        public App<F, T4> t4() {
            return this.t4;
        }

        public App<F, T5> t5() {
            return this.t5;
        }

        public App<F, T6> t6() {
            return this.t6;
        }

        public App<F, T7> t7() {
            return this.t7;
        }

        public App<F, T8> t8() {
            return this.t8;
        }

        public <R> App<F, R> apply(Applicative<F, ?> applicative, Function8<T1, T2, T3, T4, T5, T6, T7, T8, R> function8) {
            return this.apply(applicative, applicative.point(function8));
        }

        public <R> App<F, R> apply(Applicative<F, ?> applicative, App<F, Function8<T1, T2, T3, T4, T5, T6, T7, T8, R>> app) {
            return applicative.ap8(app, this.t1, this.t2, this.t3, this.t4, this.t5, this.t6, this.t7, this.t8);
        }
    }

    public static final class P7<F extends K1, T1, T2, T3, T4, T5, T6, T7> {
        private final App<F, T1> t1;
        private final App<F, T2> t2;
        private final App<F, T3> t3;
        private final App<F, T4> t4;
        private final App<F, T5> t5;
        private final App<F, T6> t6;
        private final App<F, T7> t7;

        public P7(App<F, T1> app, App<F, T2> app2, App<F, T3> app3, App<F, T4> app4, App<F, T5> app5, App<F, T6> app6, App<F, T7> app7) {
            this.t1 = app;
            this.t2 = app2;
            this.t3 = app3;
            this.t4 = app4;
            this.t5 = app5;
            this.t6 = app6;
            this.t7 = app7;
        }

        public App<F, T1> t1() {
            return this.t1;
        }

        public App<F, T2> t2() {
            return this.t2;
        }

        public App<F, T3> t3() {
            return this.t3;
        }

        public App<F, T4> t4() {
            return this.t4;
        }

        public App<F, T5> t5() {
            return this.t5;
        }

        public App<F, T6> t6() {
            return this.t6;
        }

        public App<F, T7> t7() {
            return this.t7;
        }

        public <T8> P8<F, T1, T2, T3, T4, T5, T6, T7, T8> and(App<F, T8> app) {
            return new P8<F, T1, T2, T3, T4, T5, T6, T7, T8>(this.t1, this.t2, this.t3, this.t4, this.t5, this.t6, this.t7, app);
        }

        public <R> App<F, R> apply(Applicative<F, ?> applicative, Function7<T1, T2, T3, T4, T5, T6, T7, R> function7) {
            return this.apply(applicative, applicative.point(function7));
        }

        public <R> App<F, R> apply(Applicative<F, ?> applicative, App<F, Function7<T1, T2, T3, T4, T5, T6, T7, R>> app) {
            return applicative.ap7(app, this.t1, this.t2, this.t3, this.t4, this.t5, this.t6, this.t7);
        }

        static App access$2000(P7 p7) {
            return p7.t1;
        }

        static App access$2100(P7 p7) {
            return p7.t2;
        }

        static App access$2200(P7 p7) {
            return p7.t3;
        }

        static App access$2300(P7 p7) {
            return p7.t4;
        }

        static App access$2400(P7 p7) {
            return p7.t5;
        }

        static App access$2500(P7 p7) {
            return p7.t6;
        }

        static App access$2600(P7 p7) {
            return p7.t7;
        }
    }

    public static final class P6<F extends K1, T1, T2, T3, T4, T5, T6> {
        private final App<F, T1> t1;
        private final App<F, T2> t2;
        private final App<F, T3> t3;
        private final App<F, T4> t4;
        private final App<F, T5> t5;
        private final App<F, T6> t6;

        public P6(App<F, T1> app, App<F, T2> app2, App<F, T3> app3, App<F, T4> app4, App<F, T5> app5, App<F, T6> app6) {
            this.t1 = app;
            this.t2 = app2;
            this.t3 = app3;
            this.t4 = app4;
            this.t5 = app5;
            this.t6 = app6;
        }

        public App<F, T1> t1() {
            return this.t1;
        }

        public App<F, T2> t2() {
            return this.t2;
        }

        public App<F, T3> t3() {
            return this.t3;
        }

        public App<F, T4> t4() {
            return this.t4;
        }

        public App<F, T5> t5() {
            return this.t5;
        }

        public App<F, T6> t6() {
            return this.t6;
        }

        public <T7> P7<F, T1, T2, T3, T4, T5, T6, T7> and(App<F, T7> app) {
            return new P7<F, T1, T2, T3, T4, T5, T6, T7>(this.t1, this.t2, this.t3, this.t4, this.t5, this.t6, app);
        }

        public <T7, T8> P8<F, T1, T2, T3, T4, T5, T6, T7, T8> and(P2<F, T7, T8> p2) {
            return new P8(this.t1, this.t2, this.t3, this.t4, this.t5, this.t6, P2.access$000(p2), P2.access$100(p2));
        }

        public <R> App<F, R> apply(Applicative<F, ?> applicative, Function6<T1, T2, T3, T4, T5, T6, R> function6) {
            return this.apply(applicative, applicative.point(function6));
        }

        public <R> App<F, R> apply(Applicative<F, ?> applicative, App<F, Function6<T1, T2, T3, T4, T5, T6, R>> app) {
            return applicative.ap6(app, this.t1, this.t2, this.t3, this.t4, this.t5, this.t6);
        }

        static App access$1400(P6 p6) {
            return p6.t1;
        }

        static App access$1500(P6 p6) {
            return p6.t2;
        }

        static App access$1600(P6 p6) {
            return p6.t3;
        }

        static App access$1700(P6 p6) {
            return p6.t4;
        }

        static App access$1800(P6 p6) {
            return p6.t5;
        }

        static App access$1900(P6 p6) {
            return p6.t6;
        }
    }

    public static final class P5<F extends K1, T1, T2, T3, T4, T5> {
        private final App<F, T1> t1;
        private final App<F, T2> t2;
        private final App<F, T3> t3;
        private final App<F, T4> t4;
        private final App<F, T5> t5;

        public P5(App<F, T1> app, App<F, T2> app2, App<F, T3> app3, App<F, T4> app4, App<F, T5> app5) {
            this.t1 = app;
            this.t2 = app2;
            this.t3 = app3;
            this.t4 = app4;
            this.t5 = app5;
        }

        public App<F, T1> t1() {
            return this.t1;
        }

        public App<F, T2> t2() {
            return this.t2;
        }

        public App<F, T3> t3() {
            return this.t3;
        }

        public App<F, T4> t4() {
            return this.t4;
        }

        public App<F, T5> t5() {
            return this.t5;
        }

        public <T6> P6<F, T1, T2, T3, T4, T5, T6> and(App<F, T6> app) {
            return new P6<F, T1, T2, T3, T4, T5, T6>(this.t1, this.t2, this.t3, this.t4, this.t5, app);
        }

        public <T6, T7> P7<F, T1, T2, T3, T4, T5, T6, T7> and(P2<F, T6, T7> p2) {
            return new P7(this.t1, this.t2, this.t3, this.t4, this.t5, P2.access$000(p2), P2.access$100(p2));
        }

        public <T6, T7, T8> P8<F, T1, T2, T3, T4, T5, T6, T7, T8> and(P3<F, T6, T7, T8> p3) {
            return new P8(this.t1, this.t2, this.t3, this.t4, this.t5, P3.access$200(p3), P3.access$300(p3), P3.access$400(p3));
        }

        public <R> App<F, R> apply(Applicative<F, ?> applicative, Function5<T1, T2, T3, T4, T5, R> function5) {
            return this.apply(applicative, applicative.point(function5));
        }

        public <R> App<F, R> apply(Applicative<F, ?> applicative, App<F, Function5<T1, T2, T3, T4, T5, R>> app) {
            return applicative.ap5(app, this.t1, this.t2, this.t3, this.t4, this.t5);
        }

        static App access$900(P5 p5) {
            return p5.t1;
        }

        static App access$1000(P5 p5) {
            return p5.t2;
        }

        static App access$1100(P5 p5) {
            return p5.t3;
        }

        static App access$1200(P5 p5) {
            return p5.t4;
        }

        static App access$1300(P5 p5) {
            return p5.t5;
        }
    }

    public static final class P4<F extends K1, T1, T2, T3, T4> {
        private final App<F, T1> t1;
        private final App<F, T2> t2;
        private final App<F, T3> t3;
        private final App<F, T4> t4;

        public P4(App<F, T1> app, App<F, T2> app2, App<F, T3> app3, App<F, T4> app4) {
            this.t1 = app;
            this.t2 = app2;
            this.t3 = app3;
            this.t4 = app4;
        }

        public App<F, T1> t1() {
            return this.t1;
        }

        public App<F, T2> t2() {
            return this.t2;
        }

        public App<F, T3> t3() {
            return this.t3;
        }

        public App<F, T4> t4() {
            return this.t4;
        }

        public <T5> P5<F, T1, T2, T3, T4, T5> and(App<F, T5> app) {
            return new P5<F, T1, T2, T3, T4, T5>(this.t1, this.t2, this.t3, this.t4, app);
        }

        public <T5, T6> P6<F, T1, T2, T3, T4, T5, T6> and(P2<F, T5, T6> p2) {
            return new P6(this.t1, this.t2, this.t3, this.t4, P2.access$000(p2), P2.access$100(p2));
        }

        public <T5, T6, T7> P7<F, T1, T2, T3, T4, T5, T6, T7> and(P3<F, T5, T6, T7> p3) {
            return new P7(this.t1, this.t2, this.t3, this.t4, P3.access$200(p3), P3.access$300(p3), P3.access$400(p3));
        }

        public <T5, T6, T7, T8> P8<F, T1, T2, T3, T4, T5, T6, T7, T8> and(P4<F, T5, T6, T7, T8> p4) {
            return new P8<F, T1, T2, T3, T4, T1, T2, T3, T4>(this.t1, this.t2, this.t3, this.t4, p4.t1, p4.t2, p4.t3, p4.t4);
        }

        public <R> App<F, R> apply(Applicative<F, ?> applicative, Function4<T1, T2, T3, T4, R> function4) {
            return this.apply(applicative, applicative.point(function4));
        }

        public <R> App<F, R> apply(Applicative<F, ?> applicative, App<F, Function4<T1, T2, T3, T4, R>> app) {
            return applicative.ap4(app, this.t1, this.t2, this.t3, this.t4);
        }

        static App access$500(P4 p4) {
            return p4.t1;
        }

        static App access$600(P4 p4) {
            return p4.t2;
        }

        static App access$700(P4 p4) {
            return p4.t3;
        }

        static App access$800(P4 p4) {
            return p4.t4;
        }
    }

    public static final class P3<F extends K1, T1, T2, T3> {
        private final App<F, T1> t1;
        private final App<F, T2> t2;
        private final App<F, T3> t3;

        public P3(App<F, T1> app, App<F, T2> app2, App<F, T3> app3) {
            this.t1 = app;
            this.t2 = app2;
            this.t3 = app3;
        }

        public App<F, T1> t1() {
            return this.t1;
        }

        public App<F, T2> t2() {
            return this.t2;
        }

        public App<F, T3> t3() {
            return this.t3;
        }

        public <T4> P4<F, T1, T2, T3, T4> and(App<F, T4> app) {
            return new P4<F, T1, T2, T3, T4>(this.t1, this.t2, this.t3, app);
        }

        public <T4, T5> P5<F, T1, T2, T3, T4, T5> and(P2<F, T4, T5> p2) {
            return new P5(this.t1, this.t2, this.t3, P2.access$000(p2), P2.access$100(p2));
        }

        public <T4, T5, T6> P6<F, T1, T2, T3, T4, T5, T6> and(P3<F, T4, T5, T6> p3) {
            return new P6<F, T1, T2, T3, T1, T2, T3>(this.t1, this.t2, this.t3, p3.t1, p3.t2, p3.t3);
        }

        public <T4, T5, T6, T7> P7<F, T1, T2, T3, T4, T5, T6, T7> and(P4<F, T4, T5, T6, T7> p4) {
            return new P7(this.t1, this.t2, this.t3, P4.access$500(p4), P4.access$600(p4), P4.access$700(p4), P4.access$800(p4));
        }

        public <T4, T5, T6, T7, T8> P8<F, T1, T2, T3, T4, T5, T6, T7, T8> and(P5<F, T4, T5, T6, T7, T8> p5) {
            return new P8(this.t1, this.t2, this.t3, P5.access$900(p5), P5.access$1000(p5), P5.access$1100(p5), P5.access$1200(p5), P5.access$1300(p5));
        }

        public <R> App<F, R> apply(Applicative<F, ?> applicative, Function3<T1, T2, T3, R> function3) {
            return this.apply(applicative, applicative.point(function3));
        }

        public <R> App<F, R> apply(Applicative<F, ?> applicative, App<F, Function3<T1, T2, T3, R>> app) {
            return applicative.ap3(app, this.t1, this.t2, this.t3);
        }

        static App access$200(P3 p3) {
            return p3.t1;
        }

        static App access$300(P3 p3) {
            return p3.t2;
        }

        static App access$400(P3 p3) {
            return p3.t3;
        }
    }

    public static final class P2<F extends K1, T1, T2> {
        private final App<F, T1> t1;
        private final App<F, T2> t2;

        public P2(App<F, T1> app, App<F, T2> app2) {
            this.t1 = app;
            this.t2 = app2;
        }

        public App<F, T1> t1() {
            return this.t1;
        }

        public App<F, T2> t2() {
            return this.t2;
        }

        public <T3> P3<F, T1, T2, T3> and(App<F, T3> app) {
            return new P3<F, T1, T2, T3>(this.t1, this.t2, app);
        }

        public <T3, T4> P4<F, T1, T2, T3, T4> and(P2<F, T3, T4> p2) {
            return new P4<F, T1, T2, T1, T2>(this.t1, this.t2, p2.t1, p2.t2);
        }

        public <T3, T4, T5> P5<F, T1, T2, T3, T4, T5> and(P3<F, T3, T4, T5> p3) {
            return new P5(this.t1, this.t2, P3.access$200(p3), P3.access$300(p3), P3.access$400(p3));
        }

        public <T3, T4, T5, T6> P6<F, T1, T2, T3, T4, T5, T6> and(P4<F, T3, T4, T5, T6> p4) {
            return new P6(this.t1, this.t2, P4.access$500(p4), P4.access$600(p4), P4.access$700(p4), P4.access$800(p4));
        }

        public <T3, T4, T5, T6, T7> P7<F, T1, T2, T3, T4, T5, T6, T7> and(P5<F, T3, T4, T5, T6, T7> p5) {
            return new P7(this.t1, this.t2, P5.access$900(p5), P5.access$1000(p5), P5.access$1100(p5), P5.access$1200(p5), P5.access$1300(p5));
        }

        public <T3, T4, T5, T6, T7, T8> P8<F, T1, T2, T3, T4, T5, T6, T7, T8> and(P6<F, T3, T4, T5, T6, T7, T8> p6) {
            return new P8(this.t1, this.t2, P6.access$1400(p6), P6.access$1500(p6), P6.access$1600(p6), P6.access$1700(p6), P6.access$1800(p6), P6.access$1900(p6));
        }

        public <R> App<F, R> apply(Applicative<F, ?> applicative, BiFunction<T1, T2, R> biFunction) {
            return this.apply(applicative, applicative.point(biFunction));
        }

        public <R> App<F, R> apply(Applicative<F, ?> applicative, App<F, BiFunction<T1, T2, R>> app) {
            return applicative.ap2(app, this.t1, this.t2);
        }

        static App access$000(P2 p2) {
            return p2.t1;
        }

        static App access$100(P2 p2) {
            return p2.t2;
        }
    }

    public static final class P1<F extends K1, T1> {
        private final App<F, T1> t1;

        public P1(App<F, T1> app) {
            this.t1 = app;
        }

        public App<F, T1> t1() {
            return this.t1;
        }

        public <T2> P2<F, T1, T2> and(App<F, T2> app) {
            return new P2<F, T1, T2>(this.t1, app);
        }

        public <T2, T3> P3<F, T1, T2, T3> and(P2<F, T2, T3> p2) {
            return new P3(this.t1, P2.access$000(p2), P2.access$100(p2));
        }

        public <T2, T3, T4> P4<F, T1, T2, T3, T4> and(P3<F, T2, T3, T4> p3) {
            return new P4(this.t1, P3.access$200(p3), P3.access$300(p3), P3.access$400(p3));
        }

        public <T2, T3, T4, T5> P5<F, T1, T2, T3, T4, T5> and(P4<F, T2, T3, T4, T5> p4) {
            return new P5(this.t1, P4.access$500(p4), P4.access$600(p4), P4.access$700(p4), P4.access$800(p4));
        }

        public <T2, T3, T4, T5, T6> P6<F, T1, T2, T3, T4, T5, T6> and(P5<F, T2, T3, T4, T5, T6> p5) {
            return new P6(this.t1, P5.access$900(p5), P5.access$1000(p5), P5.access$1100(p5), P5.access$1200(p5), P5.access$1300(p5));
        }

        public <T2, T3, T4, T5, T6, T7> P7<F, T1, T2, T3, T4, T5, T6, T7> and(P6<F, T2, T3, T4, T5, T6, T7> p6) {
            return new P7(this.t1, P6.access$1400(p6), P6.access$1500(p6), P6.access$1600(p6), P6.access$1700(p6), P6.access$1800(p6), P6.access$1900(p6));
        }

        public <T2, T3, T4, T5, T6, T7, T8> P8<F, T1, T2, T3, T4, T5, T6, T7, T8> and(P7<F, T2, T3, T4, T5, T6, T7, T8> p7) {
            return new P8(this.t1, P7.access$2000(p7), P7.access$2100(p7), P7.access$2200(p7), P7.access$2300(p7), P7.access$2400(p7), P7.access$2500(p7), P7.access$2600(p7));
        }

        public <R> App<F, R> apply(Applicative<F, ?> applicative, Function<T1, R> function) {
            return this.apply(applicative, applicative.point(function));
        }

        public <R> App<F, R> apply(Applicative<F, ?> applicative, App<F, Function<T1, R>> app) {
            return applicative.ap(app, this.t1);
        }
    }
}

