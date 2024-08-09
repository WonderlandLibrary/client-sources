/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.types;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.FieldFinder;
import com.mojang.datafixers.FunctionType;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.RewriteResult;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.TypedOptic;
import com.mojang.datafixers.View;
import com.mojang.datafixers.functions.Functions;
import com.mojang.datafixers.functions.PointFreeRule;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.K1;
import com.mojang.datafixers.types.families.RecursiveTypeFamily;
import com.mojang.datafixers.types.templates.TaggedChoice;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import org.apache.commons.lang3.mutable.MutableObject;
import org.apache.commons.lang3.tuple.Triple;

public abstract class Type<A>
implements App<Mu, A> {
    private static final Map<Triple<Type<?>, TypeRewriteRule, PointFreeRule>, CompletableFuture<Optional<? extends RewriteResult<?, ?>>>> PENDING_REWRITE_CACHE = Maps.newConcurrentMap();
    private static final Map<Triple<Type<?>, TypeRewriteRule, PointFreeRule>, Optional<? extends RewriteResult<?, ?>>> REWRITE_CACHE = Maps.newConcurrentMap();
    @Nullable
    private TypeTemplate template;
    @Nullable
    private Codec<A> codec;

    public static <A> Type<A> unbox(App<Mu, A> app) {
        return (Type)app;
    }

    public RewriteResult<A, ?> rewriteOrNop(TypeRewriteRule typeRewriteRule) {
        return DataFixUtils.orElseGet(typeRewriteRule.rewrite(this), this::lambda$rewriteOrNop$0);
    }

    public static <S, T, A, B> RewriteResult<S, T> opticView(Type<S> type, RewriteResult<A, B> rewriteResult, TypedOptic<S, T, A, B> typedOptic) {
        if (Objects.equals(rewriteResult.view().function(), Functions.id())) {
            return RewriteResult.nop(type);
        }
        return RewriteResult.create(View.create(typedOptic.sType(), typedOptic.tType(), Functions.app(Functions.profunctorTransformer(typedOptic.upCast(FunctionType.Instance.Mu.TYPE_TOKEN).orElseThrow(IllegalArgumentException::new)), rewriteResult.view().function(), DSL.func(typedOptic.aType(), rewriteResult.view().newType()))), rewriteResult.recData());
    }

    public RewriteResult<A, ?> all(TypeRewriteRule typeRewriteRule, boolean bl, boolean bl2) {
        return RewriteResult.nop(this);
    }

    public Optional<RewriteResult<A, ?>> one(TypeRewriteRule typeRewriteRule) {
        return Optional.empty();
    }

    public Optional<RewriteResult<A, ?>> everywhere(TypeRewriteRule typeRewriteRule, PointFreeRule pointFreeRule, boolean bl, boolean bl2) {
        TypeRewriteRule typeRewriteRule2 = TypeRewriteRule.seq(TypeRewriteRule.orElse(typeRewriteRule, TypeRewriteRule::nop), TypeRewriteRule.all(TypeRewriteRule.everywhere(typeRewriteRule, pointFreeRule, bl, bl2), bl, bl2));
        return this.rewrite(typeRewriteRule2, pointFreeRule);
    }

    public Type<?> updateMu(RecursiveTypeFamily recursiveTypeFamily) {
        return this;
    }

    public TypeTemplate template() {
        if (this.template == null) {
            this.template = this.buildTemplate();
        }
        return this.template;
    }

    public abstract TypeTemplate buildTemplate();

    public Optional<TaggedChoice.TaggedChoiceType<?>> findChoiceType(String string, int n) {
        return Optional.empty();
    }

    public Optional<Type<?>> findCheckedType(int n) {
        return Optional.empty();
    }

    public final <T> DataResult<Pair<A, Dynamic<T>>> read(Dynamic<T> dynamic) {
        return this.codec().decode(dynamic.getOps(), dynamic.getValue()).map(arg_0 -> Type.lambda$read$2(dynamic, arg_0));
    }

    public final Codec<A> codec() {
        if (this.codec == null) {
            this.codec = this.buildCodec();
        }
        return this.codec;
    }

    protected abstract Codec<A> buildCodec();

    public final <T> DataResult<T> write(DynamicOps<T> dynamicOps, A a) {
        return this.codec().encode(a, dynamicOps, dynamicOps.empty());
    }

    public final <T> DataResult<Dynamic<T>> writeDynamic(DynamicOps<T> dynamicOps, A a) {
        return this.write(dynamicOps, a).map(arg_0 -> Type.lambda$writeDynamic$3(dynamicOps, arg_0));
    }

    public <T> DataResult<Pair<Typed<A>, T>> readTyped(Dynamic<T> dynamic) {
        return this.readTyped(dynamic.getOps(), dynamic.getValue());
    }

    public <T> DataResult<Pair<Typed<A>, T>> readTyped(DynamicOps<T> dynamicOps, T t) {
        return this.codec().decode(dynamicOps, t).map(arg_0 -> this.lambda$readTyped$5(dynamicOps, arg_0));
    }

    public <T> DataResult<Pair<Optional<?>, T>> read(DynamicOps<T> dynamicOps, TypeRewriteRule typeRewriteRule, PointFreeRule pointFreeRule, T t) {
        return this.codec().decode(dynamicOps, t).map(arg_0 -> this.lambda$read$8(typeRewriteRule, pointFreeRule, dynamicOps, arg_0));
    }

    public <T> DataResult<T> readAndWrite(DynamicOps<T> dynamicOps, Type<?> type, TypeRewriteRule typeRewriteRule, PointFreeRule pointFreeRule, T t) {
        Optional<RewriteResult<A, ?>> optional = this.rewrite(typeRewriteRule, pointFreeRule);
        if (!optional.isPresent()) {
            return DataResult.error("Could not build a rewrite rule: " + typeRewriteRule + " " + pointFreeRule, t);
        }
        View<A, ?> view = optional.get().view();
        return this.codec().decode(dynamicOps, t).flatMap(arg_0 -> this.lambda$readAndWrite$9(dynamicOps, type, view, arg_0));
    }

    private <T, B> DataResult<T> capWrite(DynamicOps<T> dynamicOps, Type<?> type, T t, A a, View<A, B> view) {
        if (!type.equals(view.newType(), true, false)) {
            return DataResult.error("Rewritten type doesn't match");
        }
        return view.newType().codec().encode(view.function().evalCached().apply(dynamicOps).apply(a), dynamicOps, t);
    }

    public Optional<RewriteResult<A, ?>> rewrite(TypeRewriteRule typeRewriteRule, PointFreeRule pointFreeRule) {
        Triple<Type, TypeRewriteRule, PointFreeRule> triple = Triple.of(this, typeRewriteRule, pointFreeRule);
        Optional<RewriteResult<A, ?>> optional = REWRITE_CACHE.get(triple);
        if (optional != null) {
            return optional;
        }
        MutableObject mutableObject = new MutableObject();
        CompletableFuture completableFuture = PENDING_REWRITE_CACHE.computeIfAbsent(triple, arg_0 -> Type.lambda$rewrite$10(mutableObject, arg_0));
        if (mutableObject.getValue() != null) {
            Optional<RewriteResult<A, ?>> optional2 = typeRewriteRule.rewrite(this).flatMap(arg_0 -> Type.lambda$rewrite$12(pointFreeRule, arg_0));
            REWRITE_CACHE.put(triple, optional2);
            completableFuture.complete(optional2);
            PENDING_REWRITE_CACHE.remove(triple);
            return optional2;
        }
        return (Optional)completableFuture.join();
    }

    public <FT, FR> Type<?> getSetType(OpticFinder<FT> opticFinder, Type<FR> type) {
        return opticFinder.findType(this, type, false).orThrow().tType();
    }

    public Optional<Type<?>> findFieldTypeOpt(String string) {
        return Optional.empty();
    }

    public Type<?> findFieldType(String string) {
        return this.findFieldTypeOpt(string).orElseThrow(() -> Type.lambda$findFieldType$13(string));
    }

    public OpticFinder<?> findField(String string) {
        return new FieldFinder(string, this.findFieldType(string));
    }

    public Optional<A> point(DynamicOps<?> dynamicOps) {
        return Optional.empty();
    }

    public Optional<Typed<A>> pointTyped(DynamicOps<?> dynamicOps) {
        return this.point(dynamicOps).map(arg_0 -> this.lambda$pointTyped$14(dynamicOps, arg_0));
    }

    public <FT, FR> Either<TypedOptic<A, ?, FT, FR>, FieldNotFoundException> findTypeCached(Type<FT> type, Type<FR> type2, TypeMatcher<FT, FR> typeMatcher, boolean bl) {
        return this.findType(type, type2, typeMatcher, bl);
    }

    public <FT, FR> Either<TypedOptic<A, ?, FT, FR>, FieldNotFoundException> findType(Type<FT> type, Type<FR> type2, TypeMatcher<FT, FR> typeMatcher, boolean bl) {
        return typeMatcher.match(this).map(Either::left, arg_0 -> this.lambda$findType$15(type, type2, typeMatcher, bl, arg_0));
    }

    public <FT, FR> Either<TypedOptic<A, ?, FT, FR>, FieldNotFoundException> findTypeInChildren(Type<FT> type, Type<FR> type2, TypeMatcher<FT, FR> typeMatcher, boolean bl) {
        return Either.right(new FieldNotFoundException("No more children"));
    }

    public OpticFinder<A> finder() {
        return DSL.typeFinder(this);
    }

    public <B> Optional<A> ifSame(Typed<B> typed) {
        return this.ifSame(typed.getType(), typed.getValue());
    }

    public <B> Optional<A> ifSame(Type<B> type, B b) {
        if (this.equals(type, true, false)) {
            return Optional.of(b);
        }
        return Optional.empty();
    }

    public <B> Optional<RewriteResult<A, ?>> ifSame(Type<B> type, RewriteResult<B, ?> rewriteResult) {
        if (this.equals(type, true, false)) {
            return Optional.of(rewriteResult);
        }
        return Optional.empty();
    }

    public final boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        return this.equals(object, false, false);
    }

    public abstract boolean equals(Object var1, boolean var2, boolean var3);

    private Either lambda$findType$15(Type type, Type type2, TypeMatcher typeMatcher, boolean bl, FieldNotFoundException fieldNotFoundException) {
        if (fieldNotFoundException instanceof Continue) {
            return this.findTypeInChildren(type, type2, typeMatcher, bl);
        }
        return Either.right(fieldNotFoundException);
    }

    private Typed lambda$pointTyped$14(DynamicOps dynamicOps, Object object) {
        return new Typed<Object>(this, dynamicOps, object);
    }

    private static IllegalArgumentException lambda$findFieldType$13(String string) {
        return new IllegalArgumentException("Field not found: " + string);
    }

    private static Optional lambda$rewrite$12(PointFreeRule pointFreeRule, RewriteResult rewriteResult) {
        return rewriteResult.view().rewrite(pointFreeRule).map(arg_0 -> Type.lambda$null$11(rewriteResult, arg_0));
    }

    private static RewriteResult lambda$null$11(RewriteResult rewriteResult, View view) {
        return RewriteResult.create(view, rewriteResult.recData());
    }

    private static CompletableFuture lambda$rewrite$10(MutableObject mutableObject, Triple triple) {
        CompletableFuture completableFuture = new CompletableFuture();
        mutableObject.setValue(completableFuture);
        return completableFuture;
    }

    private DataResult lambda$readAndWrite$9(DynamicOps dynamicOps, Type type, View view, Pair pair) {
        return this.capWrite(dynamicOps, type, pair.getSecond(), pair.getFirst(), view);
    }

    private Pair lambda$read$8(TypeRewriteRule typeRewriteRule, PointFreeRule pointFreeRule, DynamicOps dynamicOps, Pair pair) {
        return pair.mapFirst(arg_0 -> this.lambda$null$7(typeRewriteRule, pointFreeRule, dynamicOps, arg_0));
    }

    private Optional lambda$null$7(TypeRewriteRule typeRewriteRule, PointFreeRule pointFreeRule, DynamicOps dynamicOps, Object object) {
        return this.rewrite(typeRewriteRule, pointFreeRule).map(arg_0 -> Type.lambda$null$6(dynamicOps, object, arg_0));
    }

    private static Object lambda$null$6(DynamicOps dynamicOps, Object object, RewriteResult rewriteResult) {
        return rewriteResult.view().function().evalCached().apply(dynamicOps).apply(object);
    }

    private Pair lambda$readTyped$5(DynamicOps dynamicOps, Pair pair) {
        return pair.mapFirst(arg_0 -> this.lambda$null$4(dynamicOps, arg_0));
    }

    private Typed lambda$null$4(DynamicOps dynamicOps, Object object) {
        return new Typed<Object>(this, dynamicOps, object);
    }

    private static Dynamic lambda$writeDynamic$3(DynamicOps dynamicOps, Object object) {
        return new Dynamic<Object>(dynamicOps, object);
    }

    private static Pair lambda$read$2(Dynamic dynamic, Pair pair) {
        return pair.mapSecond(arg_0 -> Type.lambda$null$1(dynamic, arg_0));
    }

    private static Dynamic lambda$null$1(Dynamic dynamic, Object object) {
        return new Dynamic<Object>(dynamic.getOps(), object);
    }

    private RewriteResult lambda$rewriteOrNop$0() {
        return RewriteResult.nop(this);
    }

    public static final class Continue
    extends FieldNotFoundException {
        public Continue() {
            super("Continue");
        }
    }

    public static class FieldNotFoundException
    extends TypeError {
        public FieldNotFoundException(String string) {
            super(string);
        }
    }

    public static abstract class TypeError {
        private final String message;

        public TypeError(String string) {
            this.message = string;
        }

        public String toString() {
            return this.message;
        }
    }

    public static interface TypeMatcher<FT, FR> {
        public <S> Either<TypedOptic<S, ?, FT, FR>, FieldNotFoundException> match(Type<S> var1);
    }

    public static class Mu
    implements K1 {
    }
}

