/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.functions;

import com.mojang.datafixers.FunctionType;
import com.mojang.datafixers.RewriteResult;
import com.mojang.datafixers.functions.Apply;
import com.mojang.datafixers.functions.Bang;
import com.mojang.datafixers.functions.Comp;
import com.mojang.datafixers.functions.Fold;
import com.mojang.datafixers.functions.FunctionWrapper;
import com.mojang.datafixers.functions.Id;
import com.mojang.datafixers.functions.In;
import com.mojang.datafixers.functions.Out;
import com.mojang.datafixers.functions.PointFree;
import com.mojang.datafixers.functions.ProfunctorTransformer;
import com.mojang.datafixers.optics.Optic;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.families.Algebra;
import com.mojang.datafixers.types.templates.RecursivePoint;
import com.mojang.serialization.DynamicOps;
import java.util.Objects;
import java.util.function.Function;

public abstract class Functions {
    private static final Id<?> ID = new Id();

    public static <A, B, C> PointFree<Function<A, C>> comp(Type<B> type, PointFree<Function<B, C>> pointFree, PointFree<Function<A, B>> pointFree2) {
        if (Objects.equals(pointFree, Functions.id())) {
            return pointFree2;
        }
        if (Objects.equals(pointFree2, Functions.id())) {
            return pointFree;
        }
        return new Comp(type, pointFree, pointFree2);
    }

    public static <A, B> PointFree<Function<A, B>> fun(String string, Function<DynamicOps<?>, Function<A, B>> function) {
        return new FunctionWrapper<A, B>(string, function);
    }

    public static <A, B> PointFree<B> app(PointFree<Function<A, B>> pointFree, PointFree<A> pointFree2, Type<A> type) {
        return new Apply<A, B>(pointFree, pointFree2, type);
    }

    public static <S, T, A, B> PointFree<Function<Function<A, B>, Function<S, T>>> profunctorTransformer(Optic<? super FunctionType.Instance.Mu, S, T, A, B> optic) {
        return new ProfunctorTransformer<S, T, A, B>(optic);
    }

    public static <A> Bang<A> bang() {
        return new Bang();
    }

    public static <A> PointFree<Function<A, A>> in(RecursivePoint.RecursivePointType<A> recursivePointType) {
        return new In<A>(recursivePointType);
    }

    public static <A> PointFree<Function<A, A>> out(RecursivePoint.RecursivePointType<A> recursivePointType) {
        return new Out<A>(recursivePointType);
    }

    public static <A, B> PointFree<Function<A, B>> fold(RecursivePoint.RecursivePointType<A> recursivePointType, RewriteResult<?, B> rewriteResult, Algebra algebra, int n) {
        return new Fold<A, B>(recursivePointType, rewriteResult, algebra, n);
    }

    public static <A> PointFree<Function<A, A>> id() {
        return ID;
    }
}

