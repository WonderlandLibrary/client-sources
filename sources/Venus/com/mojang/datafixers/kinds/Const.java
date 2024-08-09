/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.kinds;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.kinds.K1;
import com.mojang.datafixers.kinds.Monoid;
import java.util.function.BiFunction;
import java.util.function.Function;

public final class Const<C, T>
implements App<Mu<C>, T> {
    private final C value;

    public static <C, T> C unbox(App<Mu<C>, T> app) {
        return ((Const)app).value;
    }

    public static <C, T> Const<C, T> create(C c) {
        return new Const<C, T>(c);
    }

    Const(C c) {
        this.value = c;
    }

    public static final class Instance<C>
    implements Applicative<com.mojang.datafixers.kinds.Const$Mu<C>, Mu<C>> {
        private final Monoid<C> monoid;

        public Instance(Monoid<C> monoid) {
            this.monoid = monoid;
        }

        @Override
        public <T, R> App<com.mojang.datafixers.kinds.Const$Mu<C>, R> map(Function<? super T, ? extends R> function, App<com.mojang.datafixers.kinds.Const$Mu<C>, T> app) {
            return Const.create(Const.unbox(app));
        }

        @Override
        public <A> App<com.mojang.datafixers.kinds.Const$Mu<C>, A> point(A a) {
            return Const.create(this.monoid.point());
        }

        @Override
        public <A, R> Function<App<com.mojang.datafixers.kinds.Const$Mu<C>, A>, App<com.mojang.datafixers.kinds.Const$Mu<C>, R>> lift1(App<com.mojang.datafixers.kinds.Const$Mu<C>, Function<A, R>> app) {
            return arg_0 -> this.lambda$lift1$0(app, arg_0);
        }

        @Override
        public <A, B, R> BiFunction<App<com.mojang.datafixers.kinds.Const$Mu<C>, A>, App<com.mojang.datafixers.kinds.Const$Mu<C>, B>, App<com.mojang.datafixers.kinds.Const$Mu<C>, R>> lift2(App<com.mojang.datafixers.kinds.Const$Mu<C>, BiFunction<A, B, R>> app) {
            return (arg_0, arg_1) -> this.lambda$lift2$1(app, arg_0, arg_1);
        }

        private App lambda$lift2$1(App app, App app2, App app3) {
            return Const.create(this.monoid.add(Const.unbox(app), this.monoid.add(Const.unbox(app2), Const.unbox(app3))));
        }

        private App lambda$lift1$0(App app, App app2) {
            return Const.create(this.monoid.add(Const.unbox(app), Const.unbox(app2)));
        }

        public static final class Mu<C>
        implements Applicative.Mu {
        }
    }

    public static final class Mu<C>
    implements K1 {
    }
}

