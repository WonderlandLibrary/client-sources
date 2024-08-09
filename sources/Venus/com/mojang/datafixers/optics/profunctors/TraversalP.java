/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.optics.profunctors;

import com.google.common.reflect.TypeToken;
import com.mojang.datafixers.FunctionType;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.App2;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.kinds.K1;
import com.mojang.datafixers.kinds.K2;
import com.mojang.datafixers.kinds.Traversable;
import com.mojang.datafixers.optics.Wander;
import com.mojang.datafixers.optics.profunctors.AffineP;
import com.mojang.datafixers.optics.profunctors.FunctorProfunctor;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;

public interface TraversalP<P extends K2, Mu extends Mu>
extends AffineP<P, Mu> {
    public static <P extends K2, Proof extends Mu> TraversalP<P, Proof> unbox(App<Proof, P> app) {
        return (TraversalP)app;
    }

    public <S, T, A, B> App2<P, S, T> wander(Wander<S, T, A, B> var1, App2<P, A, B> var2);

    default public <T extends K1, A, B> App2<P, App<T, A>, App<T, B>> traverse(Traversable<T, ?> traversable, App2<P, A, B> app2) {
        return this.wander(new Wander<App<T, A>, App<T, B>, A, B>(this, traversable){
            final Traversable val$traversable;
            final TraversalP this$0;
            {
                this.this$0 = traversalP;
                this.val$traversable = traversable;
            }

            @Override
            public <F extends K1> FunctionType<App<T, A>, App<F, App<T, B>>> wander(Applicative<F, ?> applicative, FunctionType<A, App<F, B>> functionType) {
                return arg_0 -> 1.lambda$wander$0(this.val$traversable, applicative, functionType, arg_0);
            }

            private static App lambda$wander$0(Traversable traversable, Applicative applicative, FunctionType functionType, App app) {
                return traversable.traverse(applicative, functionType, app);
            }
        }, app2);
    }

    @Override
    default public <A, B, C> App2<P, Pair<A, C>, Pair<B, C>> first(App2<P, A, B> app2) {
        return this.dimap(this.traverse(new Pair.Instance(), app2), TraversalP::lambda$first$0, Pair::unbox);
    }

    @Override
    default public <A, B, C> App2<P, Either<A, C>, Either<B, C>> left(App2<P, A, B> app2) {
        return this.dimap(this.traverse(new Either.Instance(), app2), TraversalP::lambda$left$1, Either::unbox);
    }

    default public FunctorProfunctor<Traversable.Mu, P, FunctorProfunctor.Mu<Traversable.Mu>> toFP3() {
        return new FunctorProfunctor<Traversable.Mu, P, FunctorProfunctor.Mu<Traversable.Mu>>(this){
            final TraversalP this$0;
            {
                this.this$0 = traversalP;
            }

            @Override
            public <A, B, F extends K1> App2<P, App<F, A>, App<F, B>> distribute(App<? extends Traversable.Mu, F> app, App2<P, A, B> app2) {
                return this.this$0.traverse(Traversable.unbox(app), app2);
            }
        };
    }

    private static App lambda$left$1(Either either) {
        return either;
    }

    private static App lambda$first$0(Pair pair) {
        return pair;
    }

    public static interface Mu
    extends AffineP.Mu {
        public static final TypeToken<Mu> TYPE_TOKEN = new TypeToken<Mu>(){};
    }
}

