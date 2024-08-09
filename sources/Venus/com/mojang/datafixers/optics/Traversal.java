/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.optics;

import com.mojang.datafixers.FunctionType;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.App2;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.kinds.K1;
import com.mojang.datafixers.kinds.K2;
import com.mojang.datafixers.optics.Optic;
import com.mojang.datafixers.optics.Wander;
import com.mojang.datafixers.optics.profunctors.TraversalP;
import java.util.function.Function;

public interface Traversal<S, T, A, B>
extends Wander<S, T, A, B>,
App2<Mu<A, B>, S, T>,
Optic<TraversalP.Mu, S, T, A, B> {
    public static <S, T, A, B> Traversal<S, T, A, B> unbox(App2<Mu<A, B>, S, T> app2) {
        return (Traversal)app2;
    }

    @Override
    default public <P extends K2> FunctionType<App2<P, A, B>, App2<P, S, T>> eval(App<? extends TraversalP.Mu, P> app) {
        TraversalP<P, ? extends TraversalP.Mu> traversalP = TraversalP.unbox(app);
        return arg_0 -> this.lambda$eval$0(traversalP, arg_0);
    }

    @Override
    default public Function eval(App app) {
        return this.eval(app);
    }

    private App2 lambda$eval$0(TraversalP traversalP, App2 app2) {
        return traversalP.wander(this, app2);
    }

    public static final class Instance<A2, B2>
    implements TraversalP<Mu<A2, B2>, TraversalP.Mu> {
        @Override
        public <A, B, C, D> FunctionType<App2<Mu<A2, B2>, A, B>, App2<Mu<A2, B2>, C, D>> dimap(Function<C, A> function, Function<B, D> function2) {
            return arg_0 -> this.lambda$dimap$0(function2, function, arg_0);
        }

        @Override
        public <S, T, A, B> App2<Mu<A2, B2>, S, T> wander(Wander<S, T, A, B> wander, App2<Mu<A2, B2>, A, B> app2) {
            return new Traversal<S, T, A2, B2>(this, wander, app2){
                final Wander val$wander;
                final App2 val$input;
                final Instance this$0;
                {
                    this.this$0 = instance;
                    this.val$wander = wander;
                    this.val$input = app2;
                }

                @Override
                public <F extends K1> FunctionType<S, App<F, T>> wander(Applicative<F, ?> applicative, FunctionType<A2, App<F, B2>> functionType) {
                    return this.val$wander.wander(applicative, Traversal.unbox(this.val$input).wander(applicative, functionType));
                }
            };
        }

        private App2 lambda$dimap$0(Function function, Function function2, App2 app2) {
            return new Traversal<C, D, A2, B2>(this, function, app2, function2){
                final Function val$h;
                final App2 val$tr;
                final Function val$g;
                final Instance this$0;
                {
                    this.this$0 = instance;
                    this.val$h = function;
                    this.val$tr = app2;
                    this.val$g = function2;
                }

                @Override
                public <F extends K1> FunctionType<C, App<F, D>> wander(Applicative<F, ?> applicative, FunctionType<A2, App<F, B2>> functionType) {
                    return arg_0 -> 1.lambda$wander$0(applicative, this.val$h, this.val$tr, functionType, this.val$g, arg_0);
                }

                private static App lambda$wander$0(Applicative applicative, Function function, App2 app2, FunctionType functionType, Function function2, Object object) {
                    return applicative.map(function, Traversal.unbox(app2).wander(applicative, functionType).apply(function2.apply(object)));
                }
            };
        }
    }

    public static final class Mu<A, B>
    implements K2 {
    }
}

