/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.functions;

import com.mojang.datafixers.FunctionType;
import com.mojang.datafixers.functions.PointFree;
import com.mojang.datafixers.kinds.App2;
import com.mojang.datafixers.optics.Optic;
import com.mojang.serialization.DynamicOps;
import java.util.Objects;
import java.util.function.Function;

final class ProfunctorTransformer<S, T, A, B>
extends PointFree<Function<Function<A, B>, Function<S, T>>> {
    protected final Optic<? super FunctionType.Instance.Mu, S, T, A, B> optic;
    protected final Function<App2<FunctionType.Mu, A, B>, App2<FunctionType.Mu, S, T>> func;
    private final Function<Function<A, B>, Function<S, T>> unwrappedFunction;

    public ProfunctorTransformer(Optic<? super FunctionType.Instance.Mu, S, T, A, B> optic) {
        this.optic = optic;
        this.func = optic.eval(FunctionType.Instance.INSTANCE);
        this.unwrappedFunction = this::lambda$new$0;
    }

    @Override
    public String toString(int n) {
        return "Optic[" + this.optic + "]";
    }

    @Override
    public Function<DynamicOps<?>, Function<Function<A, B>, Function<S, T>>> eval() {
        return this::lambda$eval$1;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        ProfunctorTransformer profunctorTransformer = (ProfunctorTransformer)object;
        return Objects.equals(this.optic, profunctorTransformer.optic);
    }

    public int hashCode() {
        return Objects.hash(this.optic);
    }

    private Function lambda$eval$1(DynamicOps dynamicOps) {
        return this.unwrappedFunction;
    }

    private Function lambda$new$0(Function function) {
        return FunctionType.unbox(this.func.apply(FunctionType.create(function)));
    }
}

