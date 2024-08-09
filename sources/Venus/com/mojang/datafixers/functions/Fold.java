/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.functions;

import com.google.common.collect.Maps;
import com.mojang.datafixers.RewriteResult;
import com.mojang.datafixers.functions.Functions;
import com.mojang.datafixers.functions.PointFree;
import com.mojang.datafixers.types.families.Algebra;
import com.mojang.datafixers.types.families.RecursiveTypeFamily;
import com.mojang.datafixers.types.families.TypeFamily;
import com.mojang.datafixers.types.templates.RecursivePoint;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DynamicOps;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntFunction;

final class Fold<A, B>
extends PointFree<Function<A, B>> {
    private static final Map<Pair<RecursiveTypeFamily, Algebra>, IntFunction<RewriteResult<?, ?>>> HMAP_CACHE = Maps.newConcurrentMap();
    private static final Map<Pair<IntFunction<RewriteResult<?, ?>>, Integer>, RewriteResult<?, ?>> HMAP_APPLY_CACHE = Maps.newConcurrentMap();
    protected final RecursivePoint.RecursivePointType<A> aType;
    protected final RewriteResult<?, B> function;
    protected final Algebra algebra;
    protected final int index;

    public Fold(RecursivePoint.RecursivePointType<A> recursivePointType, RewriteResult<?, B> rewriteResult, Algebra algebra, int n) {
        this.aType = recursivePointType;
        this.function = rewriteResult;
        this.algebra = algebra;
        this.index = n;
    }

    private <FB> PointFree<Function<A, B>> cap(RewriteResult<?, B> rewriteResult, RewriteResult<?, FB> rewriteResult2) {
        return Functions.comp(rewriteResult2.view().newType(), rewriteResult.view().function(), rewriteResult2.view().function());
    }

    @Override
    public Function<DynamicOps<?>, Function<A, B>> eval() {
        return this::lambda$eval$3;
    }

    @Override
    public String toString(int n) {
        return "fold(" + this.aType + ", " + this.index + ", \n" + Fold.indent(n + 1) + this.algebra.toString(n + 1) + "\n" + Fold.indent(n) + ")";
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        Fold fold = (Fold)object;
        return Objects.equals(this.aType, fold.aType) && Objects.equals(this.algebra, fold.algebra);
    }

    public int hashCode() {
        return Objects.hash(this.aType, this.algebra);
    }

    private Function lambda$eval$3(DynamicOps dynamicOps) {
        return arg_0 -> this.lambda$null$2(dynamicOps, arg_0);
    }

    private Object lambda$null$2(DynamicOps dynamicOps, Object object) {
        RecursiveTypeFamily recursiveTypeFamily = this.aType.family();
        IntFunction intFunction = HMAP_CACHE.computeIfAbsent(Pair.of(recursiveTypeFamily, this.algebra), Fold::lambda$null$0);
        RewriteResult rewriteResult = HMAP_APPLY_CACHE.computeIfAbsent(Pair.of(intFunction, this.index), Fold::lambda$null$1);
        PointFree<Function<A, B>> pointFree = this.cap(this.function, rewriteResult);
        return pointFree.evalCached().apply(dynamicOps).apply(object);
    }

    private static RewriteResult lambda$null$1(Pair pair) {
        return (RewriteResult)((IntFunction)pair.getFirst()).apply((Integer)pair.getSecond());
    }

    private static IntFunction lambda$null$0(Pair pair) {
        return ((RecursiveTypeFamily)pair.getFirst()).template().hmap((TypeFamily)pair.getFirst(), ((RecursiveTypeFamily)pair.getFirst()).fold((Algebra)pair.getSecond()));
    }
}

