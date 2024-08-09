/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.optics;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.FunctionType;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.kinds.K1;
import com.mojang.datafixers.optics.Traversal;
import java.util.List;

public final class ListTraversal<A, B>
implements Traversal<List<A>, List<B>, A, B> {
    @Override
    public <F extends K1> FunctionType<List<A>, App<F, List<B>>> wander(Applicative<F, ?> applicative, FunctionType<A, App<F, B>> functionType) {
        return arg_0 -> ListTraversal.lambda$wander$0(applicative, functionType, arg_0);
    }

    public boolean equals(Object object) {
        return object instanceof ListTraversal;
    }

    public String toString() {
        return "ListTraversal";
    }

    private static App lambda$wander$0(Applicative applicative, FunctionType functionType, List list) {
        App app = applicative.point(ImmutableList.builder());
        for (Object e : list) {
            app = applicative.ap2(applicative.point(ImmutableList.Builder::add), app, (App)functionType.apply(e));
        }
        return applicative.map(ImmutableList.Builder::build, app);
    }
}

