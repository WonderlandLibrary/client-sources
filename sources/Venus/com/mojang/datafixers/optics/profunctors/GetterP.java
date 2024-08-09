/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.optics.profunctors;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.App2;
import com.mojang.datafixers.kinds.K2;
import com.mojang.datafixers.optics.profunctors.Bicontravariant;
import com.mojang.datafixers.optics.profunctors.Profunctor;
import java.util.function.Function;

public interface GetterP<P extends K2, Mu extends Mu>
extends Profunctor<P, Mu>,
Bicontravariant<P, Mu> {
    public static <P extends K2, Proof extends Mu> GetterP<P, Proof> unbox(App<Proof, P> app) {
        return (GetterP)app;
    }

    default public <A, B, C> App2<P, C, A> secondPhantom(App2<P, C, B> app2) {
        return this.cimap(() -> this.lambda$secondPhantom$1(app2), Function.identity(), GetterP::lambda$secondPhantom$2);
    }

    private static Void lambda$secondPhantom$2(Object object) {
        return null;
    }

    private App2 lambda$secondPhantom$1(App2 app2) {
        return this.rmap(app2, GetterP::lambda$null$0);
    }

    private static Void lambda$null$0(Object object) {
        return null;
    }

    public static interface Mu
    extends Profunctor.Mu,
    Bicontravariant.Mu {
    }
}

