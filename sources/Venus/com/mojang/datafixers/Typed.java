/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers;

import com.google.common.collect.ImmutableList;
import com.google.common.reflect.TypeToken;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypedOptic;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Const;
import com.mojang.datafixers.kinds.IdF;
import com.mojang.datafixers.kinds.Monoid;
import com.mojang.datafixers.optics.Forget;
import com.mojang.datafixers.optics.ForgetOpt;
import com.mojang.datafixers.optics.Inj1;
import com.mojang.datafixers.optics.Inj2;
import com.mojang.datafixers.optics.Optics;
import com.mojang.datafixers.optics.ReForgetC;
import com.mojang.datafixers.optics.Traversal;
import com.mojang.datafixers.optics.profunctors.TraversalP;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.RecursivePoint;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class Typed<A> {
    protected final Type<A> type;
    protected final DynamicOps<?> ops;
    protected final A value;

    public Typed(Type<A> type, DynamicOps<?> dynamicOps, A a) {
        this.type = type;
        this.ops = dynamicOps;
        this.value = a;
    }

    public String toString() {
        return "Typed[" + this.value + "]";
    }

    public <FT> FT get(OpticFinder<FT> opticFinder) {
        return (FT)Forget.unbox(opticFinder.findType(this.type, false).orThrow().apply(new TypeToken<Forget.Instance.Mu<FT>>(this){
            final Typed this$0;
            {
                this.this$0 = typed;
            }
        }, new Forget.Instance(), Optics.forget(Function.identity()))).run(this.value);
    }

    public <FT> Typed<FT> getTyped(OpticFinder<FT> opticFinder) {
        TypedOptic typedOptic = opticFinder.findType(this.type, false).orThrow();
        return new Typed<FT>(typedOptic.aType(), this.ops, Forget.unbox(typedOptic.apply(new TypeToken<Forget.Instance.Mu<FT>>(this){
            final Typed this$0;
            {
                this.this$0 = typed;
            }
        }, new Forget.Instance(), Optics.forget(Function.identity()))).run(this.value));
    }

    public <FT> Optional<FT> getOptional(OpticFinder<FT> opticFinder) {
        TypedOptic<Object, ?, FT, FT> typedOptic = opticFinder.findType(this.type, false).orThrow();
        return ForgetOpt.unbox(typedOptic.apply(new TypeToken<ForgetOpt.Instance.Mu<FT>>(this){
            final Typed this$0;
            {
                this.this$0 = typed;
            }
        }, new ForgetOpt.Instance(), Optics.forgetOpt(Optional::of))).run(this.value);
    }

    public <FT> FT getOrCreate(OpticFinder<FT> opticFinder) {
        return DataFixUtils.or(this.getOptional(opticFinder), () -> this.lambda$getOrCreate$0(opticFinder)).orElseThrow(() -> Typed.lambda$getOrCreate$1(opticFinder));
    }

    public <FT> FT getOrDefault(OpticFinder<FT> opticFinder, FT FT) {
        return (FT)ForgetOpt.unbox(opticFinder.findType(this.type, false).orThrow().apply(new TypeToken<ForgetOpt.Instance.Mu<FT>>(this){
            final Typed this$0;
            {
                this.this$0 = typed;
            }
        }, new ForgetOpt.Instance(), Optics.forgetOpt(Optional::of))).run(this.value).orElse(FT);
    }

    public <FT> Optional<Typed<FT>> getOptionalTyped(OpticFinder<FT> opticFinder) {
        TypedOptic<Object, ?, FT, FT> typedOptic = opticFinder.findType(this.type, false).orThrow();
        return ForgetOpt.unbox(typedOptic.apply(new TypeToken<ForgetOpt.Instance.Mu<FT>>(this){
            final Typed this$0;
            {
                this.this$0 = typed;
            }
        }, new ForgetOpt.Instance(), Optics.forgetOpt(Optional::of))).run(this.value).map(arg_0 -> this.lambda$getOptionalTyped$2(typedOptic, arg_0));
    }

    public <FT> Typed<FT> getOrCreateTyped(OpticFinder<FT> opticFinder) {
        return DataFixUtils.or(this.getOptionalTyped(opticFinder), () -> this.lambda$getOrCreateTyped$3(opticFinder)).orElseThrow(() -> Typed.lambda$getOrCreateTyped$4(opticFinder));
    }

    public <FT> Typed<?> set(OpticFinder<FT> opticFinder, FT FT) {
        return this.set(opticFinder, new Typed<FT>(opticFinder.type(), this.ops, FT));
    }

    public <FT, FR> Typed<?> set(OpticFinder<FT> opticFinder, Type<FR> type, FR FR) {
        return this.set(opticFinder, new Typed<FR>(type, this.ops, FR));
    }

    public <FT, FR> Typed<?> set(OpticFinder<FT> opticFinder, Typed<FR> typed) {
        TypedOptic<A, ?, FT, A> typedOptic = opticFinder.findType(this.type, typed.type, false).orThrow();
        return this.setCap(typedOptic, typed);
    }

    private <B, FT, FR> Typed<B> setCap(TypedOptic<A, B, FT, FR> typedOptic, Typed<FR> typed) {
        B b = ReForgetC.unbox(typedOptic.apply(new TypeToken<ReForgetC.Instance.Mu<FR>>(this){
            final Typed this$0;
            {
                this.this$0 = typed;
            }
        }, new ReForgetC.Instance(), Optics.reForgetC("set", Either.left(Function.identity())))).run(this.value, typed.value);
        return new Typed<B>(typedOptic.tType(), this.ops, b);
    }

    public <FT> Typed<?> updateTyped(OpticFinder<FT> opticFinder, Function<Typed<?>, Typed<?>> function) {
        return this.updateTyped(opticFinder, opticFinder.type(), function);
    }

    public <FT, FR> Typed<?> updateTyped(OpticFinder<FT> opticFinder, Type<FR> type, Function<Typed<?>, Typed<?>> function) {
        TypedOptic<A, ?, FT, FR> typedOptic = opticFinder.findType(this.type, type, false).orThrow();
        return this.updateCap(typedOptic, arg_0 -> this.lambda$updateTyped$6(function, opticFinder, typedOptic, arg_0));
    }

    public <FT> Typed<?> update(OpticFinder<FT> opticFinder, Function<FT, FT> function) {
        return this.update(opticFinder, opticFinder.type(), function);
    }

    public <FT, FR> Typed<?> update(OpticFinder<FT> opticFinder, Type<FR> type, Function<FT, FR> function) {
        TypedOptic<A, ?, FT, FR> typedOptic = opticFinder.findType(this.type, type, false).orThrow();
        return this.updateCap(typedOptic, function);
    }

    public <FT> Typed<?> updateRecursiveTyped(OpticFinder<FT> opticFinder, Function<Typed<?>, Typed<?>> function) {
        return this.updateRecursiveTyped(opticFinder, opticFinder.type(), function);
    }

    public <FT, FR> Typed<?> updateRecursiveTyped(OpticFinder<FT> opticFinder, Type<FR> type, Function<Typed<?>, Typed<?>> function) {
        TypedOptic<A, ?, FT, FR> typedOptic = opticFinder.findType(this.type, type, true).orThrow();
        return this.updateCap(typedOptic, arg_0 -> this.lambda$updateRecursiveTyped$8(function, opticFinder, typedOptic, arg_0));
    }

    public <FT> Typed<?> updateRecursive(OpticFinder<FT> opticFinder, Function<FT, FT> function) {
        return this.updateRecursive(opticFinder, opticFinder.type(), function);
    }

    public <FT, FR> Typed<?> updateRecursive(OpticFinder<FT> opticFinder, Type<FR> type, Function<FT, FR> function) {
        TypedOptic<A, ?, FT, FR> typedOptic = opticFinder.findType(this.type, type, true).orThrow();
        return this.updateCap(typedOptic, function);
    }

    private <B, FT, FR> Typed<B> updateCap(TypedOptic<A, B, FT, FR> typedOptic, Function<FT, FR> function) {
        Traversal<Object, B, FT, FR> traversal = Optics.toTraversal(typedOptic.upCast(TraversalP.Mu.TYPE_TOKEN).orElseThrow(IllegalArgumentException::new));
        Object t = IdF.get(traversal.wander(IdF.Instance.INSTANCE, arg_0 -> Typed.lambda$updateCap$9(function, arg_0)).apply(this.value));
        return new Typed<B>(typedOptic.tType(), this.ops, t);
    }

    public <FT> List<Typed<FT>> getAllTyped(OpticFinder<FT> opticFinder) {
        TypedOptic<A, ?, FT, FT> typedOptic = opticFinder.findType(this.type, opticFinder.type(), false).orThrow();
        return this.getAll(typedOptic).stream().map(arg_0 -> this.lambda$getAllTyped$10(opticFinder, arg_0)).collect(Collectors.toList());
    }

    public <FT> List<FT> getAll(TypedOptic<A, ?, FT, ?> typedOptic) {
        Traversal<Object, ?, FT, ?> traversal = Optics.toTraversal(typedOptic.upCast(TraversalP.Mu.TYPE_TOKEN).orElseThrow(IllegalArgumentException::new));
        return (List)Const.unbox(traversal.wander(new Const.Instance(Monoid.listMonoid()), Typed::lambda$getAll$11).apply(this.value));
    }

    public Typed<A> out() {
        if (!(this.type instanceof RecursivePoint.RecursivePointType)) {
            throw new IllegalArgumentException("Not recursive");
        }
        Type type = ((RecursivePoint.RecursivePointType)this.type).unfold();
        return new Typed(type, this.ops, this.value);
    }

    public <B> Typed<Either<A, B>> inj1(Type<B> type) {
        return new Typed<Object>(DSL.or(this.type, type), this.ops, new Inj1().build(this.value));
    }

    public <B> Typed<Either<B, A>> inj2(Type<B> type) {
        return new Typed<Object>(DSL.or(type, this.type), this.ops, new Inj2().build(this.value));
    }

    public static <A, B> Typed<Pair<A, B>> pair(Typed<A> typed, Typed<B> typed2) {
        return new Typed<Pair<A, B>>(DSL.and(typed.type, typed2.type), typed.ops, Pair.of(typed.value, typed2.value));
    }

    public Type<A> getType() {
        return this.type;
    }

    public DynamicOps<?> getOps() {
        return this.ops;
    }

    public A getValue() {
        return this.value;
    }

    public DataResult<? extends Dynamic<?>> write() {
        return this.type.writeDynamic(this.ops, this.value);
    }

    private static App lambda$getAll$11(Object object) {
        return Const.create(ImmutableList.of(object));
    }

    private Typed lambda$getAllTyped$10(OpticFinder opticFinder, Object object) {
        return new Typed<Object>(opticFinder.type(), this.ops, object);
    }

    private static App lambda$updateCap$9(Function function, Object object) {
        return IdF.create(function.apply(object));
    }

    private Object lambda$updateRecursiveTyped$8(Function function, OpticFinder opticFinder, TypedOptic typedOptic, Object object) {
        Typed typed = (Typed)function.apply(new Typed<Object>(opticFinder.type(), this.ops, object));
        return typedOptic.bType().ifSame(typed).orElseThrow(Typed::lambda$null$7);
    }

    private static IllegalArgumentException lambda$null$7() {
        return new IllegalArgumentException("Function didn't update to the expected type");
    }

    private Object lambda$updateTyped$6(Function function, OpticFinder opticFinder, TypedOptic typedOptic, Object object) {
        Typed typed = (Typed)function.apply(new Typed<Object>(opticFinder.type(), this.ops, object));
        return typedOptic.bType().ifSame(typed).orElseThrow(Typed::lambda$null$5);
    }

    private static IllegalArgumentException lambda$null$5() {
        return new IllegalArgumentException("Function didn't update to the expected type");
    }

    private static IllegalStateException lambda$getOrCreateTyped$4(OpticFinder opticFinder) {
        return new IllegalStateException("Could not create default value for type: " + opticFinder.type());
    }

    private Optional lambda$getOrCreateTyped$3(OpticFinder opticFinder) {
        return opticFinder.type().pointTyped(this.ops);
    }

    private Typed lambda$getOptionalTyped$2(TypedOptic typedOptic, Object object) {
        return new Typed<Object>(typedOptic.aType(), this.ops, object);
    }

    private static IllegalStateException lambda$getOrCreate$1(OpticFinder opticFinder) {
        return new IllegalStateException("Could not create default value for type: " + opticFinder.type());
    }

    private Optional lambda$getOrCreate$0(OpticFinder opticFinder) {
        return opticFinder.type().point(this.ops);
    }
}

