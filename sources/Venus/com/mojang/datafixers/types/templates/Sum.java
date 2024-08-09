/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.types.templates;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.TypeToken;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.FamilyOptic;
import com.mojang.datafixers.FunctionType;
import com.mojang.datafixers.OpticParts;
import com.mojang.datafixers.RewriteResult;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.TypedOptic;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.kinds.K1;
import com.mojang.datafixers.optics.Optics;
import com.mojang.datafixers.optics.Traversal;
import com.mojang.datafixers.optics.profunctors.TraversalP;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.families.RecursiveTypeFamily;
import com.mojang.datafixers.types.families.TypeFamily;
import com.mojang.datafixers.types.templates.TaggedChoice;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;
import java.util.Objects;
import java.util.Optional;
import java.util.function.IntFunction;
import javax.annotation.Nullable;

public final class Sum
implements TypeTemplate {
    private final TypeTemplate f;
    private final TypeTemplate g;

    public Sum(TypeTemplate typeTemplate, TypeTemplate typeTemplate2) {
        this.f = typeTemplate;
        this.g = typeTemplate2;
    }

    @Override
    public int size() {
        return Math.max(this.f.size(), this.g.size());
    }

    @Override
    public TypeFamily apply(TypeFamily typeFamily) {
        return new TypeFamily(this, typeFamily){
            final TypeFamily val$family;
            final Sum this$0;
            {
                this.this$0 = sum;
                this.val$family = typeFamily;
            }

            @Override
            public Type<?> apply(int n) {
                return DSL.or(Sum.access$000(this.this$0).apply(this.val$family).apply(n), Sum.access$100(this.this$0).apply(this.val$family).apply(n));
            }
        };
    }

    @Override
    public <A, B> FamilyOptic<A, B> applyO(FamilyOptic<A, B> familyOptic, Type<A> type, Type<B> type2) {
        return TypeFamily.familyOptic(arg_0 -> this.lambda$applyO$0(familyOptic, type, type2, arg_0));
    }

    private <A, B, LS, RS, LT, RT> OpticParts<A, B> cap(FamilyOptic<A, B> familyOptic, FamilyOptic<A, B> familyOptic2, int n) {
        TypeToken<TraversalP.Mu> typeToken = TraversalP.Mu.TYPE_TOKEN;
        return new OpticParts(ImmutableSet.of(typeToken), new Traversal<Either<LS, RS>, Either<LT, RT>, A, B>(this, familyOptic, n, typeToken, familyOptic2){
            final FamilyOptic val$lo;
            final int val$index;
            final TypeToken val$bound;
            final FamilyOptic val$ro;
            final Sum this$0;
            {
                this.this$0 = sum;
                this.val$lo = familyOptic;
                this.val$index = n;
                this.val$bound = typeToken;
                this.val$ro = familyOptic2;
            }

            @Override
            public <F extends K1> FunctionType<Either<LS, RS>, App<F, Either<LT, RT>>> wander(Applicative<F, ?> applicative, FunctionType<A, App<F, B>> functionType) {
                return arg_0 -> 2.lambda$wander$2(this.val$lo, this.val$index, this.val$bound, applicative, functionType, this.val$ro, arg_0);
            }

            private static App lambda$wander$2(FamilyOptic familyOptic, int n, TypeToken typeToken, Applicative applicative, FunctionType functionType, FamilyOptic familyOptic2, Either either) {
                return either.map(arg_0 -> 2.lambda$null$0(familyOptic, n, typeToken, applicative, functionType, arg_0), arg_0 -> 2.lambda$null$1(familyOptic2, n, typeToken, applicative, functionType, arg_0));
            }

            private static App lambda$null$1(FamilyOptic familyOptic, int n, TypeToken typeToken, Applicative applicative, FunctionType functionType, Object object) {
                OpticParts opticParts = familyOptic.apply(n);
                Traversal traversal = Optics.toTraversal(opticParts.optic().upCast(opticParts.bounds(), typeToken).orElseThrow(IllegalArgumentException::new));
                return applicative.ap(Either::right, traversal.wander(applicative, functionType).apply(object));
            }

            private static App lambda$null$0(FamilyOptic familyOptic, int n, TypeToken typeToken, Applicative applicative, FunctionType functionType, Object object) {
                OpticParts opticParts = familyOptic.apply(n);
                Traversal traversal = Optics.toTraversal(opticParts.optic().upCast(opticParts.bounds(), typeToken).orElseThrow(IllegalArgumentException::new));
                return applicative.ap(Either::left, traversal.wander(applicative, functionType).apply(object));
            }
        });
    }

    public <FT, FR> Either<TypeTemplate, Type.FieldNotFoundException> findFieldOrType(int n, @Nullable String string, Type<FT> type, Type<FR> type2) {
        Either<TypeTemplate, Type.FieldNotFoundException> either = this.f.findFieldOrType(n, string, type, type2);
        return either.map(this::lambda$findFieldOrType$1, arg_0 -> this.lambda$findFieldOrType$3(n, string, type, type2, arg_0));
    }

    @Override
    public IntFunction<RewriteResult<?, ?>> hmap(TypeFamily typeFamily, IntFunction<RewriteResult<?, ?>> intFunction) {
        return arg_0 -> this.lambda$hmap$4(typeFamily, intFunction, arg_0);
    }

    private <L, R> RewriteResult<?, ?> cap(Type<?> type, RewriteResult<L, ?> rewriteResult, RewriteResult<R, ?> rewriteResult2) {
        return ((SumType)type).mergeViews(rewriteResult, rewriteResult2);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof Sum)) {
            return true;
        }
        Sum sum = (Sum)object;
        return Objects.equals(this.f, sum.f) && Objects.equals(this.g, sum.g);
    }

    public int hashCode() {
        return Objects.hash(this.f, this.g);
    }

    public String toString() {
        return "(" + this.f + " | " + this.g + ")";
    }

    private RewriteResult lambda$hmap$4(TypeFamily typeFamily, IntFunction intFunction, int n) {
        RewriteResult<?, ?> rewriteResult = this.f.hmap(typeFamily, intFunction).apply(n);
        RewriteResult<?, ?> rewriteResult2 = this.g.hmap(typeFamily, intFunction).apply(n);
        return this.cap(this.apply(typeFamily).apply(n), rewriteResult, rewriteResult2);
    }

    private Either lambda$findFieldOrType$3(int n, String string, Type type, Type type2, Type.FieldNotFoundException fieldNotFoundException) {
        return this.g.findFieldOrType(n, string, type, type2).mapLeft(this::lambda$null$2);
    }

    private TypeTemplate lambda$null$2(TypeTemplate typeTemplate) {
        return new Sum(this.f, typeTemplate);
    }

    private Either lambda$findFieldOrType$1(TypeTemplate typeTemplate) {
        return Either.left(new Sum(typeTemplate, this.g));
    }

    private OpticParts lambda$applyO$0(FamilyOptic familyOptic, Type type, Type type2, int n) {
        return this.cap(this.f.applyO(familyOptic, type, type2), this.g.applyO(familyOptic, type, type2), n);
    }

    static TypeTemplate access$000(Sum sum) {
        return sum.f;
    }

    static TypeTemplate access$100(Sum sum) {
        return sum.g;
    }

    public static final class SumType<F, G>
    extends Type<Either<F, G>> {
        protected final Type<F> first;
        protected final Type<G> second;
        private int hashCode;

        public SumType(Type<F> type, Type<G> type2) {
            this.first = type;
            this.second = type2;
        }

        @Override
        public RewriteResult<Either<F, G>, ?> all(TypeRewriteRule typeRewriteRule, boolean bl, boolean bl2) {
            return this.mergeViews(this.first.rewriteOrNop(typeRewriteRule), this.second.rewriteOrNop(typeRewriteRule));
        }

        public <F2, G2> RewriteResult<Either<F, G>, ?> mergeViews(RewriteResult<F, F2> rewriteResult, RewriteResult<G, G2> rewriteResult2) {
            RewriteResult<Either<F, G>, Either<F2, G>> rewriteResult3 = SumType.fixLeft(this, this.first, this.second, rewriteResult);
            RewriteResult<Either<F, G>, Either<F, G2>> rewriteResult4 = SumType.fixRight(rewriteResult3.view().newType(), rewriteResult.view().newType(), this.second, rewriteResult2);
            return rewriteResult4.compose(rewriteResult3);
        }

        @Override
        public Optional<RewriteResult<Either<F, G>, ?>> one(TypeRewriteRule typeRewriteRule) {
            return DataFixUtils.or(typeRewriteRule.rewrite(this.first).map(this::lambda$one$0), () -> this.lambda$one$2(typeRewriteRule));
        }

        private static <F, G, F2> RewriteResult<Either<F, G>, Either<F2, G>> fixLeft(Type<Either<F, G>> type, Type<F> type2, Type<G> type3, RewriteResult<F, F2> rewriteResult) {
            return SumType.opticView(type, rewriteResult, TypedOptic.inj1(type2, type3, rewriteResult.view().newType()));
        }

        private static <F, G, G2> RewriteResult<Either<F, G>, Either<F, G2>> fixRight(Type<Either<F, G>> type, Type<F> type2, Type<G> type3, RewriteResult<G, G2> rewriteResult) {
            return SumType.opticView(type, rewriteResult, TypedOptic.inj2(type2, type3, rewriteResult.view().newType()));
        }

        @Override
        public Type<?> updateMu(RecursiveTypeFamily recursiveTypeFamily) {
            return DSL.or(this.first.updateMu(recursiveTypeFamily), this.second.updateMu(recursiveTypeFamily));
        }

        @Override
        public TypeTemplate buildTemplate() {
            return DSL.or(this.first.template(), this.second.template());
        }

        @Override
        public Optional<TaggedChoice.TaggedChoiceType<?>> findChoiceType(String string, int n) {
            return DataFixUtils.or(this.first.findChoiceType(string, n), () -> this.lambda$findChoiceType$3(string, n));
        }

        @Override
        public Optional<Type<?>> findCheckedType(int n) {
            return DataFixUtils.or(this.first.findCheckedType(n), () -> this.lambda$findCheckedType$4(n));
        }

        @Override
        protected Codec<Either<F, G>> buildCodec() {
            return Codec.either(this.first.codec(), this.second.codec());
        }

        public String toString() {
            return "(" + this.first + " | " + this.second + ")";
        }

        @Override
        public boolean equals(Object object, boolean bl, boolean bl2) {
            if (!(object instanceof SumType)) {
                return true;
            }
            SumType sumType = (SumType)object;
            return this.first.equals(sumType.first, bl, bl2) && this.second.equals(sumType.second, bl, bl2);
        }

        public int hashCode() {
            if (this.hashCode == 0) {
                this.hashCode = Objects.hash(this.first, this.second);
            }
            return this.hashCode;
        }

        @Override
        public Optional<Type<?>> findFieldTypeOpt(String string) {
            return DataFixUtils.or(this.first.findFieldTypeOpt(string), () -> this.lambda$findFieldTypeOpt$5(string));
        }

        @Override
        public Optional<Either<F, G>> point(DynamicOps<?> dynamicOps) {
            return DataFixUtils.or(this.second.point(dynamicOps).map(Either::right), () -> this.lambda$point$6(dynamicOps));
        }

        private static <A, B, LS, RS, LT, RT> TypedOptic<Either<LS, RS>, Either<LT, RT>, A, B> mergeOptics(TypedOptic<LS, LT, A, B> typedOptic, TypedOptic<RS, RT, A, B> typedOptic2) {
            TypeToken<TraversalP.Mu> typeToken = TraversalP.Mu.TYPE_TOKEN;
            return new TypedOptic<Either<LS, RS>, Either<LT, RT>, A, B>(typeToken, DSL.or(typedOptic.sType(), typedOptic2.sType()), DSL.or(typedOptic.tType(), typedOptic2.tType()), typedOptic.aType(), typedOptic.bType(), new Traversal<Either<LS, RS>, Either<LT, RT>, A, B>(typedOptic, typeToken, typedOptic2){
                final TypedOptic val$lo;
                final TypeToken val$bound;
                final TypedOptic val$ro;
                {
                    this.val$lo = typedOptic;
                    this.val$bound = typeToken;
                    this.val$ro = typedOptic2;
                }

                @Override
                public <F extends K1> FunctionType<Either<LS, RS>, App<F, Either<LT, RT>>> wander(Applicative<F, ?> applicative, FunctionType<A, App<F, B>> functionType) {
                    return arg_0 -> 1.lambda$wander$2(this.val$lo, this.val$bound, applicative, functionType, this.val$ro, arg_0);
                }

                private static App lambda$wander$2(TypedOptic typedOptic, TypeToken typeToken, Applicative applicative, FunctionType functionType, TypedOptic typedOptic2, Either either) {
                    return either.map(arg_0 -> 1.lambda$null$0(typedOptic, typeToken, applicative, functionType, arg_0), arg_0 -> 1.lambda$null$1(typedOptic2, typeToken, applicative, functionType, arg_0));
                }

                private static App lambda$null$1(TypedOptic typedOptic, TypeToken typeToken, Applicative applicative, FunctionType functionType, Object object) {
                    Traversal traversal = Optics.toTraversal(typedOptic.optic().upCast(typedOptic.bounds(), typeToken).orElseThrow(IllegalArgumentException::new));
                    return applicative.ap(Either::right, traversal.wander(applicative, functionType).apply(object));
                }

                private static App lambda$null$0(TypedOptic typedOptic, TypeToken typeToken, Applicative applicative, FunctionType functionType, Object object) {
                    Traversal traversal = Optics.toTraversal(typedOptic.optic().upCast(typedOptic.bounds(), typeToken).orElseThrow(IllegalArgumentException::new));
                    return applicative.ap(Either::left, traversal.wander(applicative, functionType).apply(object));
                }
            });
        }

        @Override
        public <FT, FR> Either<TypedOptic<Either<F, G>, ?, FT, FR>, Type.FieldNotFoundException> findTypeInChildren(Type<FT> type, Type<FR> type2, Type.TypeMatcher<FT, FR> typeMatcher, boolean bl) {
            Either<TypedOptic<F, ?, FT, FR>, Type.FieldNotFoundException> either = this.first.findType(type, type2, typeMatcher, bl);
            Either<TypedOptic<G, ?, FT, FR>, Type.FieldNotFoundException> either2 = this.second.findType(type, type2, typeMatcher, bl);
            if (either.left().isPresent() && either2.left().isPresent()) {
                return Either.left(SumType.mergeOptics(either.left().get(), either2.left().get()));
            }
            if (either.left().isPresent()) {
                return either.mapLeft(this::capLeft);
            }
            return either2.mapLeft(this::capRight);
        }

        private <FT, FR, F2> TypedOptic<Either<F, G>, ?, FT, FR> capLeft(TypedOptic<F, F2, FT, FR> typedOptic) {
            return TypedOptic.inj1(typedOptic.sType(), this.second, typedOptic.tType()).compose(typedOptic);
        }

        private <FT, FR, G2> TypedOptic<Either<F, G>, ?, FT, FR> capRight(TypedOptic<G, G2, FT, FR> typedOptic) {
            return TypedOptic.inj2(this.first, typedOptic.sType(), typedOptic.tType()).compose(typedOptic);
        }

        private Optional lambda$point$6(DynamicOps dynamicOps) {
            return this.first.point(dynamicOps).map(Either::left);
        }

        private Optional lambda$findFieldTypeOpt$5(String string) {
            return this.second.findFieldTypeOpt(string);
        }

        private Optional lambda$findCheckedType$4(int n) {
            return this.second.findCheckedType(n);
        }

        private Optional lambda$findChoiceType$3(String string, int n) {
            return this.second.findChoiceType(string, n);
        }

        private Optional lambda$one$2(TypeRewriteRule typeRewriteRule) {
            return typeRewriteRule.rewrite(this.second).map(this::lambda$null$1);
        }

        private RewriteResult lambda$null$1(RewriteResult rewriteResult) {
            return SumType.fixRight(this, this.first, this.second, rewriteResult);
        }

        private RewriteResult lambda$one$0(RewriteResult rewriteResult) {
            return SumType.fixLeft(this, this.first, this.second, rewriteResult);
        }
    }
}

