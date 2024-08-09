/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.kinds;

import com.mojang.datafixers.Products;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.K1;

public interface Kind1<F extends K1, Mu extends Mu>
extends App<Mu, F> {
    public static <F extends K1, Proof extends Mu> Kind1<F, Proof> unbox(App<Proof, F> app) {
        return (Kind1)app;
    }

    default public <T1> Products.P1<F, T1> group(App<F, T1> app) {
        return new Products.P1<F, T1>(app);
    }

    default public <T1, T2> Products.P2<F, T1, T2> group(App<F, T1> app, App<F, T2> app2) {
        return new Products.P2<F, T1, T2>(app, app2);
    }

    default public <T1, T2, T3> Products.P3<F, T1, T2, T3> group(App<F, T1> app, App<F, T2> app2, App<F, T3> app3) {
        return new Products.P3<F, T1, T2, T3>(app, app2, app3);
    }

    default public <T1, T2, T3, T4> Products.P4<F, T1, T2, T3, T4> group(App<F, T1> app, App<F, T2> app2, App<F, T3> app3, App<F, T4> app4) {
        return new Products.P4<F, T1, T2, T3, T4>(app, app2, app3, app4);
    }

    default public <T1, T2, T3, T4, T5> Products.P5<F, T1, T2, T3, T4, T5> group(App<F, T1> app, App<F, T2> app2, App<F, T3> app3, App<F, T4> app4, App<F, T5> app5) {
        return new Products.P5<F, T1, T2, T3, T4, T5>(app, app2, app3, app4, app5);
    }

    default public <T1, T2, T3, T4, T5, T6> Products.P6<F, T1, T2, T3, T4, T5, T6> group(App<F, T1> app, App<F, T2> app2, App<F, T3> app3, App<F, T4> app4, App<F, T5> app5, App<F, T6> app6) {
        return new Products.P6<F, T1, T2, T3, T4, T5, T6>(app, app2, app3, app4, app5, app6);
    }

    default public <T1, T2, T3, T4, T5, T6, T7> Products.P7<F, T1, T2, T3, T4, T5, T6, T7> group(App<F, T1> app, App<F, T2> app2, App<F, T3> app3, App<F, T4> app4, App<F, T5> app5, App<F, T6> app6, App<F, T7> app7) {
        return new Products.P7<F, T1, T2, T3, T4, T5, T6, T7>(app, app2, app3, app4, app5, app6, app7);
    }

    default public <T1, T2, T3, T4, T5, T6, T7, T8> Products.P8<F, T1, T2, T3, T4, T5, T6, T7, T8> group(App<F, T1> app, App<F, T2> app2, App<F, T3> app3, App<F, T4> app4, App<F, T5> app5, App<F, T6> app6, App<F, T7> app7, App<F, T8> app8) {
        return new Products.P8<F, T1, T2, T3, T4, T5, T6, T7, T8>(app, app2, app3, app4, app5, app6, app7, app8);
    }

    default public <T1, T2, T3, T4, T5, T6, T7, T8, T9> Products.P9<F, T1, T2, T3, T4, T5, T6, T7, T8, T9> group(App<F, T1> app, App<F, T2> app2, App<F, T3> app3, App<F, T4> app4, App<F, T5> app5, App<F, T6> app6, App<F, T7> app7, App<F, T8> app8, App<F, T9> app9) {
        return new Products.P9<F, T1, T2, T3, T4, T5, T6, T7, T8, T9>(app, app2, app3, app4, app5, app6, app7, app8, app9);
    }

    default public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Products.P10<F, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> group(App<F, T1> app, App<F, T2> app2, App<F, T3> app3, App<F, T4> app4, App<F, T5> app5, App<F, T6> app6, App<F, T7> app7, App<F, T8> app8, App<F, T9> app9, App<F, T10> app10) {
        return new Products.P10<F, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>(app, app2, app3, app4, app5, app6, app7, app8, app9, app10);
    }

    default public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Products.P11<F, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> group(App<F, T1> app, App<F, T2> app2, App<F, T3> app3, App<F, T4> app4, App<F, T5> app5, App<F, T6> app6, App<F, T7> app7, App<F, T8> app8, App<F, T9> app9, App<F, T10> app10, App<F, T11> app11) {
        return new Products.P11<F, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>(app, app2, app3, app4, app5, app6, app7, app8, app9, app10, app11);
    }

    default public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Products.P12<F, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> group(App<F, T1> app, App<F, T2> app2, App<F, T3> app3, App<F, T4> app4, App<F, T5> app5, App<F, T6> app6, App<F, T7> app7, App<F, T8> app8, App<F, T9> app9, App<F, T10> app10, App<F, T11> app11, App<F, T12> app12) {
        return new Products.P12<F, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>(app, app2, app3, app4, app5, app6, app7, app8, app9, app10, app11, app12);
    }

    default public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Products.P13<F, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> group(App<F, T1> app, App<F, T2> app2, App<F, T3> app3, App<F, T4> app4, App<F, T5> app5, App<F, T6> app6, App<F, T7> app7, App<F, T8> app8, App<F, T9> app9, App<F, T10> app10, App<F, T11> app11, App<F, T12> app12, App<F, T13> app13) {
        return new Products.P13<F, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>(app, app2, app3, app4, app5, app6, app7, app8, app9, app10, app11, app12, app13);
    }

    default public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Products.P14<F, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> group(App<F, T1> app, App<F, T2> app2, App<F, T3> app3, App<F, T4> app4, App<F, T5> app5, App<F, T6> app6, App<F, T7> app7, App<F, T8> app8, App<F, T9> app9, App<F, T10> app10, App<F, T11> app11, App<F, T12> app12, App<F, T13> app13, App<F, T14> app14) {
        return new Products.P14<F, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>(app, app2, app3, app4, app5, app6, app7, app8, app9, app10, app11, app12, app13, app14);
    }

    default public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Products.P15<F, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> group(App<F, T1> app, App<F, T2> app2, App<F, T3> app3, App<F, T4> app4, App<F, T5> app5, App<F, T6> app6, App<F, T7> app7, App<F, T8> app8, App<F, T9> app9, App<F, T10> app10, App<F, T11> app11, App<F, T12> app12, App<F, T13> app13, App<F, T14> app14, App<F, T15> app15) {
        return new Products.P15<F, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>(app, app2, app3, app4, app5, app6, app7, app8, app9, app10, app11, app12, app13, app14, app15);
    }

    default public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Products.P16<F, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> group(App<F, T1> app, App<F, T2> app2, App<F, T3> app3, App<F, T4> app4, App<F, T5> app5, App<F, T6> app6, App<F, T7> app7, App<F, T8> app8, App<F, T9> app9, App<F, T10> app10, App<F, T11> app11, App<F, T12> app12, App<F, T13> app13, App<F, T14> app14, App<F, T15> app15, App<F, T16> app16) {
        return new Products.P16<F, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>(app, app2, app3, app4, app5, app6, app7, app8, app9, app10, app11, app12, app13, app14, app15, app16);
    }

    public static interface Mu
    extends K1 {
    }
}

