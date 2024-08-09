/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.functions;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.functions.Functions;
import com.mojang.datafixers.functions.PointFree;
import com.mojang.datafixers.functions.PointFreeRule;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.DynamicOps;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

final class Apply<A, B>
extends PointFree<B> {
    protected final PointFree<Function<A, B>> func;
    protected final PointFree<A> arg;
    protected final Type<A> argType;

    public Apply(PointFree<Function<A, B>> pointFree, PointFree<A> pointFree2, Type<A> type) {
        this.func = pointFree;
        this.arg = pointFree2;
        this.argType = type;
    }

    @Override
    public Function<DynamicOps<?>, B> eval() {
        return this::lambda$eval$0;
    }

    @Override
    public String toString(int n) {
        return "(ap " + this.func.toString(n + 1) + "\n" + Apply.indent(n + 1) + this.arg.toString(n + 1) + "\n" + Apply.indent(n) + ")";
    }

    @Override
    public Optional<? extends PointFree<B>> all(PointFreeRule pointFreeRule, Type<B> type) {
        return Optional.of(Functions.app(pointFreeRule.rewrite(DSL.func(this.argType, type), this.func).map(Apply::lambda$all$1).orElse(this.func), pointFreeRule.rewrite(this.argType, this.arg).map(Apply::lambda$all$2).orElse(this.arg), this.argType));
    }

    @Override
    public Optional<? extends PointFree<B>> one(PointFreeRule pointFreeRule, Type<B> type) {
        return pointFreeRule.rewrite(DSL.func(this.argType, type), this.func).map(this::lambda$one$3).orElseGet(() -> this.lambda$one$5(pointFreeRule));
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof Apply)) {
            return true;
        }
        Apply apply = (Apply)object;
        return Objects.equals(this.func, apply.func) && Objects.equals(this.arg, apply.arg);
    }

    public int hashCode() {
        return Objects.hash(this.func, this.arg);
    }

    private Optional lambda$one$5(PointFreeRule pointFreeRule) {
        return pointFreeRule.rewrite(this.argType, this.arg).map(this::lambda$null$4);
    }

    private PointFree lambda$null$4(PointFree pointFree) {
        return Functions.app(this.func, pointFree, this.argType);
    }

    private Optional lambda$one$3(PointFree pointFree) {
        return Optional.of(Functions.app(pointFree, this.arg, this.argType));
    }

    private static PointFree lambda$all$2(PointFree pointFree) {
        return pointFree;
    }

    private static PointFree lambda$all$1(PointFree pointFree) {
        return pointFree;
    }

    private Object lambda$eval$0(DynamicOps dynamicOps) {
        return this.func.evalCached().apply(dynamicOps).apply(this.arg.evalCached().apply(dynamicOps));
    }
}

