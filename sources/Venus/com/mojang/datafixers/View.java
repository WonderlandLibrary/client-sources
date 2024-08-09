/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.functions.Functions;
import com.mojang.datafixers.functions.PointFree;
import com.mojang.datafixers.functions.PointFreeRule;
import com.mojang.datafixers.kinds.App2;
import com.mojang.datafixers.kinds.K2;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.DynamicOps;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public final class View<A, B>
implements App2<Mu, A, B> {
    private final Type<A> type;
    protected final Type<B> newType;
    private final PointFree<Function<A, B>> function;

    static <A, B> View<A, B> unbox(App2<Mu, A, B> app2) {
        return (View)app2;
    }

    public static <A> View<A, A> nopView(Type<A> type) {
        return View.create(type, type, Functions.id());
    }

    public View(Type<A> type, Type<B> type2, PointFree<Function<A, B>> pointFree) {
        this.type = type;
        this.newType = type2;
        this.function = pointFree;
    }

    public Type<A> type() {
        return this.type;
    }

    public Type<B> newType() {
        return this.newType;
    }

    public PointFree<Function<A, B>> function() {
        return this.function;
    }

    public Type<Function<A, B>> getFuncType() {
        return DSL.func(this.type, this.newType);
    }

    public String toString() {
        return "View[" + this.function + "," + this.newType + "]";
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        View view = (View)object;
        return Objects.equals(this.type, view.type) && Objects.equals(this.newType, view.newType) && Objects.equals(this.function, view.function);
    }

    public int hashCode() {
        return Objects.hash(this.type, this.newType, this.function);
    }

    public Optional<? extends View<A, B>> rewrite(PointFreeRule pointFreeRule) {
        return pointFreeRule.rewrite(DSL.func(this.type, this.newType), this.function()).map(this::lambda$rewrite$0);
    }

    public View<A, B> rewriteOrNop(PointFreeRule pointFreeRule) {
        return DataFixUtils.orElse(this.rewrite(pointFreeRule), this);
    }

    public <C> View<A, C> flatMap(Function<Type<B>, View<B, C>> function) {
        View<B, C> view = function.apply(this.newType);
        return new View<A, B>(this.type, view.newType, Functions.comp(this.newType, view.function(), this.function()));
    }

    public static <A, B> View<A, B> create(Type<A> type, Type<B> type2, PointFree<Function<A, B>> pointFree) {
        return new View<A, B>(type, type2, pointFree);
    }

    public static <A, B> View<A, B> create(String string, Type<A> type, Type<B> type2, Function<DynamicOps<?>, Function<A, B>> function) {
        return new View<A, B>(type, type2, Functions.fun(string, function));
    }

    public <C> View<C, B> compose(View<C, A> view) {
        if (Objects.equals(this.function(), Functions.id())) {
            return new View<C, B>(view.type(), this.newType(), view.function());
        }
        if (Objects.equals(view.function(), Functions.id())) {
            return new View<C, B>(view.type(), this.newType(), this.function());
        }
        return View.create(view.type, this.newType, Functions.comp(view.newType, this.function(), view.function()));
    }

    private View lambda$rewrite$0(PointFree pointFree) {
        return View.create(this.type, this.newType, pointFree);
    }

    static final class Mu
    implements K2 {
        Mu() {
        }
    }
}

