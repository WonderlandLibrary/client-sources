/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.optics.profunctors;

import com.google.common.reflect.TypeToken;
import com.mojang.datafixers.FunctionType;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.App2;
import com.mojang.datafixers.kinds.K2;
import com.mojang.datafixers.kinds.Kind2;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Profunctor<P extends K2, Mu extends Mu>
extends Kind2<P, Mu> {
    public static <P extends K2, Proof extends Mu> Profunctor<P, Proof> unbox(App<Proof, P> app) {
        return (Profunctor)app;
    }

    public <A, B, C, D> FunctionType<App2<P, A, B>, App2<P, C, D>> dimap(Function<C, A> var1, Function<B, D> var2);

    default public <A, B, C, D> App2<P, C, D> dimap(App2<P, A, B> app2, Function<C, A> function, Function<B, D> function2) {
        return this.dimap(function, function2).apply(app2);
    }

    default public <A, B, C, D> App2<P, C, D> dimap(Supplier<App2<P, A, B>> supplier, Function<C, A> function, Function<B, D> function2) {
        return this.dimap(function, function2).apply(supplier.get());
    }

    default public <A, B, C> App2<P, C, B> lmap(App2<P, A, B> app2, Function<C, A> function) {
        return this.dimap(app2, function, Function.identity());
    }

    default public <A, B, D> App2<P, A, D> rmap(App2<P, A, B> app2, Function<B, D> function) {
        return this.dimap(app2, Function.identity(), function);
    }

    public static interface Mu
    extends Kind2.Mu {
        public static final TypeToken<Mu> TYPE_TOKEN = new TypeToken<Mu>(){};
    }
}

