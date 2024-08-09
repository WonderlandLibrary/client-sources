/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.functions;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.functions.Functions;
import com.mojang.datafixers.functions.PointFree;
import com.mojang.datafixers.functions.PointFreeRule;
import com.mojang.datafixers.types.Func;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.DynamicOps;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

final class Comp<A, B, C>
extends PointFree<Function<A, C>> {
    protected final Type<B> middleType;
    protected final PointFree<Function<B, C>> first;
    protected final PointFree<Function<A, B>> second;

    public Comp(Type<B> type, PointFree<Function<B, C>> pointFree, PointFree<Function<A, B>> pointFree2) {
        this.middleType = type;
        this.first = pointFree;
        this.second = pointFree2;
    }

    @Override
    public String toString(int n) {
        return "(\n" + Comp.indent(n + 1) + this.first.toString(n + 1) + "\n" + Comp.indent(n + 1) + "\u25e6\n" + Comp.indent(n + 1) + this.second.toString(n + 1) + "\n" + Comp.indent(n) + ")";
    }

    @Override
    public Optional<? extends PointFree<Function<A, C>>> all(PointFreeRule pointFreeRule, Type<Function<A, C>> type) {
        Func func = (Func)type;
        return Optional.of(Functions.comp(this.middleType, pointFreeRule.rewrite(DSL.func(this.middleType, func.second()), this.first).map(Comp::lambda$all$0).orElse(this.first), pointFreeRule.rewrite(DSL.func(func.first(), this.middleType), this.second).map(Comp::lambda$all$1).orElse(this.second)));
    }

    @Override
    public Optional<? extends PointFree<Function<A, C>>> one(PointFreeRule pointFreeRule, Type<Function<A, C>> type) {
        Func func = (Func)type;
        return pointFreeRule.rewrite(DSL.func(this.middleType, func.second()), this.first).map(this::lambda$one$2).orElseGet(() -> this.lambda$one$4(pointFreeRule, func));
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        Comp comp = (Comp)object;
        return Objects.equals(this.first, comp.first) && Objects.equals(this.second, comp.second);
    }

    public int hashCode() {
        return Objects.hash(this.first, this.second);
    }

    @Override
    public Function<DynamicOps<?>, Function<A, C>> eval() {
        return this::lambda$eval$6;
    }

    private Function lambda$eval$6(DynamicOps dynamicOps) {
        return arg_0 -> this.lambda$null$5(dynamicOps, arg_0);
    }

    private Object lambda$null$5(DynamicOps dynamicOps, Object object) {
        Function<A, B> function = this.second.evalCached().apply(dynamicOps);
        Function<B, C> function2 = this.first.evalCached().apply(dynamicOps);
        return function2.apply(function.apply(object));
    }

    private Optional lambda$one$4(PointFreeRule pointFreeRule, Func func) {
        return pointFreeRule.rewrite(DSL.func(func.first(), this.middleType), this.second).map(this::lambda$null$3);
    }

    private PointFree lambda$null$3(PointFree pointFree) {
        return Functions.comp(this.middleType, this.first, pointFree);
    }

    private Optional lambda$one$2(PointFree pointFree) {
        return Optional.of(Functions.comp(this.middleType, pointFree, this.second));
    }

    private static PointFree lambda$all$1(PointFree pointFree) {
        return pointFree;
    }

    private static PointFree lambda$all$0(PointFree pointFree) {
        return pointFree;
    }
}

