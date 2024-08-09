/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.optics.profunctors;

import com.google.common.reflect.TypeToken;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.App2;
import com.mojang.datafixers.kinds.CocartesianLike;
import com.mojang.datafixers.kinds.K1;
import com.mojang.datafixers.kinds.K2;
import com.mojang.datafixers.optics.profunctors.FunctorProfunctor;
import com.mojang.datafixers.optics.profunctors.Profunctor;
import com.mojang.datafixers.util.Either;

public interface Cocartesian<P extends K2, Mu extends Mu>
extends Profunctor<P, Mu> {
    public static <P extends K2, Proof extends Mu> Cocartesian<P, Proof> unbox(App<Proof, P> app) {
        return (Cocartesian)app;
    }

    public <A, B, C> App2<P, Either<A, C>, Either<B, C>> left(App2<P, A, B> var1);

    default public <A, B, C> App2<P, Either<C, A>, Either<C, B>> right(App2<P, A, B> app2) {
        return this.dimap(this.left(app2), Either::swap, Either::swap);
    }

    default public FunctorProfunctor<CocartesianLike.Mu, P, FunctorProfunctor.Mu<CocartesianLike.Mu>> toFP() {
        return new FunctorProfunctor<CocartesianLike.Mu, P, FunctorProfunctor.Mu<CocartesianLike.Mu>>(this){
            final Cocartesian this$0;
            {
                this.this$0 = cocartesian;
            }

            @Override
            public <A, B, F extends K1> App2<P, App<F, A>, App<F, B>> distribute(App<? extends CocartesianLike.Mu, F> app, App2<P, A, B> app2) {
                return this.cap(CocartesianLike.unbox(app), app2);
            }

            private <A, B, F extends K1, C> App2<P, App<F, A>, App<F, B>> cap(CocartesianLike<F, C, ?> cocartesianLike, App2<P, A, B> app2) {
                return this.this$0.dimap(this.this$0.left(app2), arg_0 -> 1.lambda$cap$0(cocartesianLike, arg_0), cocartesianLike::from);
            }

            private static Either lambda$cap$0(CocartesianLike cocartesianLike, App app) {
                return Either.unbox(cocartesianLike.to(app));
            }
        };
    }

    public static interface Mu
    extends Profunctor.Mu {
        public static final TypeToken<Mu> TYPE_TOKEN = new TypeToken<Mu>(){};
    }
}

