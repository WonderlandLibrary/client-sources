/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.types.templates;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.FamilyOptic;
import com.mojang.datafixers.OpticParts;
import com.mojang.datafixers.RewriteResult;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.TypedOptic;
import com.mojang.datafixers.View;
import com.mojang.datafixers.functions.Functions;
import com.mojang.datafixers.functions.PointFreeRule;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.families.RecursiveTypeFamily;
import com.mojang.datafixers.types.families.TypeFamily;
import com.mojang.datafixers.types.templates.TaggedChoice;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import java.util.BitSet;
import java.util.Objects;
import java.util.Optional;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import org.apache.commons.lang3.ObjectUtils;

public final class RecursivePoint
implements TypeTemplate {
    private final int index;

    public RecursivePoint(int n) {
        this.index = n;
    }

    @Override
    public int size() {
        return this.index + 1;
    }

    @Override
    public TypeFamily apply(TypeFamily typeFamily) {
        Type<?> type = typeFamily.apply(this.index);
        return new TypeFamily(this, type){
            final Type val$result;
            final RecursivePoint this$0;
            {
                this.this$0 = recursivePoint;
                this.val$result = type;
            }

            @Override
            public Type<?> apply(int n) {
                return this.val$result;
            }
        };
    }

    @Override
    public <A, B> FamilyOptic<A, B> applyO(FamilyOptic<A, B> familyOptic, Type<A> type, Type<B> type2) {
        return TypeFamily.familyOptic(arg_0 -> this.lambda$applyO$0(familyOptic, arg_0));
    }

    public <FT, FR> Either<TypeTemplate, Type.FieldNotFoundException> findFieldOrType(int n, @Nullable String string, Type<FT> type, Type<FR> type2) {
        return Either.right(new Type.FieldNotFoundException("Recursion point"));
    }

    @Override
    public IntFunction<RewriteResult<?, ?>> hmap(TypeFamily typeFamily, IntFunction<RewriteResult<?, ?>> intFunction) {
        return arg_0 -> this.lambda$hmap$1(intFunction, typeFamily, arg_0);
    }

    public <S, T> RewriteResult<S, T> cap(TypeFamily typeFamily, RewriteResult<S, T> rewriteResult) {
        Type<?> type = typeFamily.apply(this.index);
        if (!(type instanceof RecursivePointType)) {
            throw new IllegalArgumentException("Type error: Recursive point template template got a non-recursice type as an input.");
        }
        if (!Objects.equals(rewriteResult.view().type(), ((RecursivePointType)type).unfold())) {
            throw new IllegalArgumentException("Type error: hmap function input type");
        }
        RecursivePointType recursivePointType = (RecursivePointType)type;
        RecursivePointType<T> recursivePointType2 = recursivePointType.family().buildMuType(rewriteResult.view().newType(), null);
        BitSet bitSet = ObjectUtils.clone(rewriteResult.recData());
        bitSet.set(this.index);
        return RewriteResult.create(View.create(recursivePointType, recursivePointType2, rewriteResult.view().function()), bitSet);
    }

    public boolean equals(Object object) {
        return object instanceof RecursivePoint && this.index == ((RecursivePoint)object).index;
    }

    public int hashCode() {
        return Objects.hash(this.index);
    }

    public String toString() {
        return "Id[" + this.index + "]";
    }

    public int index() {
        return this.index;
    }

    private RewriteResult lambda$hmap$1(IntFunction intFunction, TypeFamily typeFamily, int n) {
        RewriteResult rewriteResult = (RewriteResult)intFunction.apply(this.index);
        return this.cap(typeFamily, rewriteResult);
    }

    private OpticParts lambda$applyO$0(FamilyOptic familyOptic, int n) {
        return familyOptic.apply(this.index);
    }

    public static final class RecursivePointType<A>
    extends Type<A> {
        private final RecursiveTypeFamily family;
        private final int index;
        private final Supplier<Type<A>> delegate;
        @Nullable
        private volatile Type<A> type;

        public RecursivePointType(RecursiveTypeFamily recursiveTypeFamily, int n, Supplier<Type<A>> supplier) {
            this.family = recursiveTypeFamily;
            this.index = n;
            this.delegate = supplier;
        }

        public RecursiveTypeFamily family() {
            return this.family;
        }

        public int index() {
            return this.index;
        }

        public Type<A> unfold() {
            if (this.type == null) {
                this.type = this.delegate.get();
            }
            return this.type;
        }

        @Override
        protected Codec<A> buildCodec() {
            return new Codec<A>(this){
                final RecursivePointType this$0;
                {
                    this.this$0 = recursivePointType;
                }

                @Override
                public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> dynamicOps, T t) {
                    return this.this$0.unfold().codec().decode(dynamicOps, t).setLifecycle(Lifecycle.experimental());
                }

                @Override
                public <T> DataResult<T> encode(A a, DynamicOps<T> dynamicOps, T t) {
                    return this.this$0.unfold().codec().encode(a, dynamicOps, t).setLifecycle(Lifecycle.experimental());
                }
            };
        }

        @Override
        public RewriteResult<A, ?> all(TypeRewriteRule typeRewriteRule, boolean bl, boolean bl2) {
            return this.unfold().all(typeRewriteRule, bl, bl2);
        }

        @Override
        public Optional<RewriteResult<A, ?>> one(TypeRewriteRule typeRewriteRule) {
            return this.unfold().one(typeRewriteRule);
        }

        @Override
        public Optional<RewriteResult<A, ?>> everywhere(TypeRewriteRule typeRewriteRule, PointFreeRule pointFreeRule, boolean bl, boolean bl2) {
            if (bl) {
                return this.family.everywhere(this.index, typeRewriteRule, pointFreeRule).map(RecursivePointType::lambda$everywhere$0);
            }
            return Optional.of(RewriteResult.nop(this));
        }

        @Override
        public Type<?> updateMu(RecursiveTypeFamily recursiveTypeFamily) {
            return recursiveTypeFamily.apply(this.index);
        }

        @Override
        public TypeTemplate buildTemplate() {
            return DSL.id(this.index);
        }

        @Override
        public Optional<TaggedChoice.TaggedChoiceType<?>> findChoiceType(String string, int n) {
            return this.unfold().findChoiceType(string, this.index);
        }

        @Override
        public Optional<Type<?>> findCheckedType(int n) {
            return this.unfold().findCheckedType(this.index);
        }

        @Override
        public Optional<Type<?>> findFieldTypeOpt(String string) {
            return this.unfold().findFieldTypeOpt(string);
        }

        @Override
        public Optional<A> point(DynamicOps<?> dynamicOps) {
            return this.unfold().point(dynamicOps);
        }

        @Override
        public <FT, FR> Either<TypedOptic<A, ?, FT, FR>, Type.FieldNotFoundException> findTypeInChildren(Type<FT> type, Type<FR> type2, Type.TypeMatcher<FT, FR> typeMatcher, boolean bl) {
            return this.family.findType(this.index, type, type2, typeMatcher, bl).mapLeft(this::lambda$findTypeInChildren$1);
        }

        private <B, FT, FR> TypedOptic<A, B, FT, FR> wrapOptic(TypedOptic<A, B, FT, FR> typedOptic) {
            return new TypedOptic<A, B, FT, FR>(typedOptic.bounds(), this, typedOptic.tType(), typedOptic.aType(), typedOptic.bType(), typedOptic.optic());
        }

        public String toString() {
            return "MuType[" + this.family.name() + "_" + this.index + "]";
        }

        @Override
        public boolean equals(Object object, boolean bl, boolean bl2) {
            if (!(object instanceof RecursivePointType)) {
                return true;
            }
            RecursivePointType recursivePointType = (RecursivePointType)object;
            return (bl || Objects.equals(this.family, recursivePointType.family)) && this.index == recursivePointType.index;
        }

        public int hashCode() {
            return Objects.hash(this.family, this.index);
        }

        public View<A, A> in() {
            return View.create(this.unfold(), this, Functions.in(this));
        }

        public View<A, A> out() {
            return View.create(this, this.unfold(), Functions.out(this));
        }

        private TypedOptic lambda$findTypeInChildren$1(TypedOptic typedOptic) {
            if (!Objects.equals(this, typedOptic.sType())) {
                throw new IllegalStateException(":/");
            }
            return typedOptic;
        }

        private static RewriteResult lambda$everywhere$0(RewriteResult rewriteResult) {
            return rewriteResult;
        }
    }
}

