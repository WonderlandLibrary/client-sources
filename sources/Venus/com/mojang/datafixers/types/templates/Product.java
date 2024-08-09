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
import com.mojang.datafixers.optics.Optic;
import com.mojang.datafixers.optics.Optics;
import com.mojang.datafixers.optics.Traversal;
import com.mojang.datafixers.optics.profunctors.TraversalP;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.families.RecursiveTypeFamily;
import com.mojang.datafixers.types.families.TypeFamily;
import com.mojang.datafixers.types.templates.TaggedChoice;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;
import java.util.Objects;
import java.util.Optional;
import java.util.function.IntFunction;
import javax.annotation.Nullable;

public final class Product
implements TypeTemplate {
    private final TypeTemplate f;
    private final TypeTemplate g;

    public Product(TypeTemplate typeTemplate, TypeTemplate typeTemplate2) {
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
            final Product this$0;
            {
                this.this$0 = product;
                this.val$family = typeFamily;
            }

            @Override
            public Type<?> apply(int n) {
                return DSL.and(Product.access$000(this.this$0).apply(this.val$family).apply(n), Product.access$100(this.this$0).apply(this.val$family).apply(n));
            }
        };
    }

    @Override
    public <A, B> FamilyOptic<A, B> applyO(FamilyOptic<A, B> familyOptic, Type<A> type, Type<B> type2) {
        return TypeFamily.familyOptic(arg_0 -> this.lambda$applyO$0(familyOptic, type, type2, arg_0));
    }

    private <A, B, LS, RS, LT, RT> OpticParts<A, B> cap(FamilyOptic<A, B> familyOptic, FamilyOptic<A, B> familyOptic2, int n) {
        TypeToken<TraversalP.Mu> typeToken = TraversalP.Mu.TYPE_TOKEN;
        OpticParts<A, B> opticParts = familyOptic.apply(n);
        OpticParts<A, B> opticParts2 = familyOptic2.apply(n);
        Optic<TraversalP.Mu, ?, ?, A, B> optic = opticParts.optic().upCast(opticParts.bounds(), typeToken).orElseThrow(IllegalArgumentException::new);
        Optic<TraversalP.Mu, ?, ?, A, B> optic2 = opticParts2.optic().upCast(opticParts2.bounds(), typeToken).orElseThrow(IllegalArgumentException::new);
        Traversal<?, ?, A, B> traversal = Optics.toTraversal(optic);
        Traversal<?, ?, A, B> traversal2 = Optics.toTraversal(optic2);
        return new OpticParts(ImmutableSet.of(typeToken), new Traversal<Pair<LS, RS>, Pair<LT, RT>, A, B>(this, traversal, traversal2){
            final Traversal val$lt;
            final Traversal val$rt;
            final Product this$0;
            {
                this.this$0 = product;
                this.val$lt = traversal;
                this.val$rt = traversal2;
            }

            @Override
            public <F extends K1> FunctionType<Pair<LS, RS>, App<F, Pair<LT, RT>>> wander(Applicative<F, ?> applicative, FunctionType<A, App<F, B>> functionType) {
                return arg_0 -> 2.lambda$wander$0(applicative, this.val$lt, functionType, this.val$rt, arg_0);
            }

            private static App lambda$wander$0(Applicative applicative, Traversal traversal, FunctionType functionType, Traversal traversal2, Pair pair) {
                return applicative.ap2(applicative.point(Pair::of), traversal.wander(applicative, functionType).apply(pair.getFirst()), traversal2.wander(applicative, functionType).apply(pair.getSecond()));
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
        return ((ProductType)type).mergeViews(rewriteResult, rewriteResult2);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof Product)) {
            return true;
        }
        Product product = (Product)object;
        return Objects.equals(this.f, product.f) && Objects.equals(this.g, product.g);
    }

    public int hashCode() {
        return Objects.hash(this.f, this.g);
    }

    public String toString() {
        return "(" + this.f + ", " + this.g + ")";
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
        return new Product(this.f, typeTemplate);
    }

    private Either lambda$findFieldOrType$1(TypeTemplate typeTemplate) {
        return Either.left(new Product(typeTemplate, this.g));
    }

    private OpticParts lambda$applyO$0(FamilyOptic familyOptic, Type type, Type type2, int n) {
        return this.cap(this.f.applyO(familyOptic, type, type2), this.g.applyO(familyOptic, type, type2), n);
    }

    static TypeTemplate access$000(Product product) {
        return product.f;
    }

    static TypeTemplate access$100(Product product) {
        return product.g;
    }

    public static final class ProductType<F, G>
    extends Type<Pair<F, G>> {
        protected final Type<F> first;
        protected final Type<G> second;
        private int hashCode;

        public ProductType(Type<F> type, Type<G> type2) {
            this.first = type;
            this.second = type2;
        }

        @Override
        public RewriteResult<Pair<F, G>, ?> all(TypeRewriteRule typeRewriteRule, boolean bl, boolean bl2) {
            return this.mergeViews(this.first.rewriteOrNop(typeRewriteRule), this.second.rewriteOrNop(typeRewriteRule));
        }

        public <F2, G2> RewriteResult<Pair<F, G>, ?> mergeViews(RewriteResult<F, F2> rewriteResult, RewriteResult<G, G2> rewriteResult2) {
            RewriteResult<Pair<F, G>, Pair<F2, G>> rewriteResult3 = ProductType.fixLeft(this, this.first, this.second, rewriteResult);
            RewriteResult<Pair<F, G>, Pair<F, G2>> rewriteResult4 = ProductType.fixRight(rewriteResult3.view().newType(), rewriteResult.view().newType(), this.second, rewriteResult2);
            return rewriteResult4.compose(rewriteResult3);
        }

        @Override
        public Optional<RewriteResult<Pair<F, G>, ?>> one(TypeRewriteRule typeRewriteRule) {
            return DataFixUtils.or(typeRewriteRule.rewrite(this.first).map(this::lambda$one$0), () -> this.lambda$one$2(typeRewriteRule));
        }

        private static <F, G, F2> RewriteResult<Pair<F, G>, Pair<F2, G>> fixLeft(Type<Pair<F, G>> type, Type<F> type2, Type<G> type3, RewriteResult<F, F2> rewriteResult) {
            return ProductType.opticView(type, rewriteResult, TypedOptic.proj1(type2, type3, rewriteResult.view().newType()));
        }

        private static <F, G, G2> RewriteResult<Pair<F, G>, Pair<F, G2>> fixRight(Type<Pair<F, G>> type, Type<F> type2, Type<G> type3, RewriteResult<G, G2> rewriteResult) {
            return ProductType.opticView(type, rewriteResult, TypedOptic.proj2(type2, type3, rewriteResult.view().newType()));
        }

        @Override
        public Type<?> updateMu(RecursiveTypeFamily recursiveTypeFamily) {
            return DSL.and(this.first.updateMu(recursiveTypeFamily), this.second.updateMu(recursiveTypeFamily));
        }

        @Override
        public TypeTemplate buildTemplate() {
            return DSL.and(this.first.template(), this.second.template());
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
        public Codec<Pair<F, G>> buildCodec() {
            return Codec.pair(this.first.codec(), this.second.codec());
        }

        public String toString() {
            return "(" + this.first + ", " + this.second + ")";
        }

        @Override
        public boolean equals(Object object, boolean bl, boolean bl2) {
            if (!(object instanceof ProductType)) {
                return true;
            }
            ProductType productType = (ProductType)object;
            return this.first.equals(productType.first, bl, bl2) && this.second.equals(productType.second, bl, bl2);
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
        public Optional<Pair<F, G>> point(DynamicOps<?> dynamicOps) {
            return this.first.point(dynamicOps).flatMap(arg_0 -> this.lambda$point$7(dynamicOps, arg_0));
        }

        @Override
        public <FT, FR> Either<TypedOptic<Pair<F, G>, ?, FT, FR>, Type.FieldNotFoundException> findTypeInChildren(Type<FT> type, Type<FR> type2, Type.TypeMatcher<FT, FR> typeMatcher, boolean bl) {
            Either<TypedOptic<F, ?, FT, FR>, Type.FieldNotFoundException> either = this.first.findType(type, type2, typeMatcher, bl);
            return either.map(this::capLeft, arg_0 -> this.lambda$findTypeInChildren$8(type, type2, typeMatcher, bl, arg_0));
        }

        private <FT, F2, FR> Either<TypedOptic<Pair<F, G>, ?, FT, FR>, Type.FieldNotFoundException> capLeft(TypedOptic<F, F2, FT, FR> typedOptic) {
            return Either.left(TypedOptic.proj1(typedOptic.sType(), this.second, typedOptic.tType()).compose(typedOptic));
        }

        private <FT, G2, FR> TypedOptic<Pair<F, G>, ?, FT, FR> capRight(TypedOptic<G, G2, FT, FR> typedOptic) {
            return TypedOptic.proj2(this.first, typedOptic.sType(), typedOptic.tType()).compose(typedOptic);
        }

        private Either lambda$findTypeInChildren$8(Type type, Type type2, Type.TypeMatcher typeMatcher, boolean bl, Type.FieldNotFoundException fieldNotFoundException) {
            Either either = this.second.findType(type, type2, typeMatcher, bl);
            return either.mapLeft(this::capRight);
        }

        private Optional lambda$point$7(DynamicOps dynamicOps, Object object) {
            return this.second.point(dynamicOps).map(arg_0 -> ProductType.lambda$null$6(object, arg_0));
        }

        private static Pair lambda$null$6(Object object, Object object2) {
            return Pair.of(object, object2);
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
            return ProductType.fixRight(this, this.first, this.second, rewriteResult);
        }

        private RewriteResult lambda$one$0(RewriteResult rewriteResult) {
            return ProductType.fixLeft(this, this.first, this.second, rewriteResult);
        }
    }
}

