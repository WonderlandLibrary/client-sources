/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.types.templates;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.google.common.reflect.TypeToken;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.FamilyOptic;
import com.mojang.datafixers.OpticParts;
import com.mojang.datafixers.RewriteResult;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.TypedOptic;
import com.mojang.datafixers.kinds.K1;
import com.mojang.datafixers.optics.ListTraversal;
import com.mojang.datafixers.optics.Optic;
import com.mojang.datafixers.optics.Optics;
import com.mojang.datafixers.optics.profunctors.TraversalP;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.families.RecursiveTypeFamily;
import com.mojang.datafixers.types.families.TypeFamily;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.IntFunction;
import javax.annotation.Nullable;

public final class CompoundList
implements TypeTemplate {
    private final TypeTemplate key;
    private final TypeTemplate element;

    public CompoundList(TypeTemplate typeTemplate, TypeTemplate typeTemplate2) {
        this.key = typeTemplate;
        this.element = typeTemplate2;
    }

    @Override
    public int size() {
        return Math.max(this.key.size(), this.element.size());
    }

    @Override
    public TypeFamily apply(TypeFamily typeFamily) {
        return arg_0 -> this.lambda$apply$0(typeFamily, arg_0);
    }

    @Override
    public <A, B> FamilyOptic<A, B> applyO(FamilyOptic<A, B> familyOptic, Type<A> type, Type<B> type2) {
        return TypeFamily.familyOptic(arg_0 -> this.lambda$applyO$1(familyOptic, type, type2, arg_0));
    }

    private <S, T, A, B> Optic<?, ?, ?, A, B> cap(Optic<?, S, T, A, B> optic) {
        return new ListTraversal().compose(Optics.proj2()).composeUnchecked(optic);
    }

    public <FT, FR> Either<TypeTemplate, Type.FieldNotFoundException> findFieldOrType(int n, @Nullable String string, Type<FT> type, Type<FR> type2) {
        return this.element.findFieldOrType(n, string, type, type2).mapLeft(this::lambda$findFieldOrType$2);
    }

    @Override
    public IntFunction<RewriteResult<?, ?>> hmap(TypeFamily typeFamily, IntFunction<RewriteResult<?, ?>> intFunction) {
        return arg_0 -> this.lambda$hmap$3(typeFamily, intFunction, arg_0);
    }

    private <L, R> RewriteResult<?, ?> cap(Type<?> type, RewriteResult<L, ?> rewriteResult, RewriteResult<R, ?> rewriteResult2) {
        return ((CompoundListType)type).mergeViews(rewriteResult, rewriteResult2);
    }

    public boolean equals(Object object) {
        return object instanceof CompoundList && Objects.equals(this.element, ((CompoundList)object).element);
    }

    public int hashCode() {
        return Objects.hash(this.element);
    }

    public String toString() {
        return "CompoundList[" + this.element + "]";
    }

    private RewriteResult lambda$hmap$3(TypeFamily typeFamily, IntFunction intFunction, int n) {
        RewriteResult<?, ?> rewriteResult = this.key.hmap(typeFamily, intFunction).apply(n);
        RewriteResult<?, ?> rewriteResult2 = this.element.hmap(typeFamily, intFunction).apply(n);
        return this.cap(this.apply(typeFamily).apply(n), rewriteResult, rewriteResult2);
    }

    private TypeTemplate lambda$findFieldOrType$2(TypeTemplate typeTemplate) {
        return new CompoundList(this.key, typeTemplate);
    }

    private OpticParts lambda$applyO$1(FamilyOptic familyOptic, Type type, Type type2, int n) {
        OpticParts opticParts = this.element.applyO(familyOptic, type, type2).apply(n);
        HashSet<TypeToken<? extends K1>> hashSet = Sets.newHashSet(opticParts.bounds());
        hashSet.add(TraversalP.Mu.TYPE_TOKEN);
        return new OpticParts(hashSet, this.cap(opticParts.optic()));
    }

    private Type lambda$apply$0(TypeFamily typeFamily, int n) {
        return DSL.compoundList(this.key.apply(typeFamily).apply(n), this.element.apply(typeFamily).apply(n));
    }

    public static final class CompoundListType<K, V>
    extends Type<List<Pair<K, V>>> {
        protected final Type<K> key;
        protected final Type<V> element;

        public CompoundListType(Type<K> type, Type<V> type2) {
            this.key = type;
            this.element = type2;
        }

        @Override
        public RewriteResult<List<Pair<K, V>>, ?> all(TypeRewriteRule typeRewriteRule, boolean bl, boolean bl2) {
            return this.mergeViews(this.key.rewriteOrNop(typeRewriteRule), this.element.rewriteOrNop(typeRewriteRule));
        }

        public <K2, V2> RewriteResult<List<Pair<K, V>>, ?> mergeViews(RewriteResult<K, K2> rewriteResult, RewriteResult<V, V2> rewriteResult2) {
            RewriteResult<List<Pair<K, V>>, List<Pair<K2, V>>> rewriteResult3 = CompoundListType.fixKeys(this, this.key, this.element, rewriteResult);
            RewriteResult<List<Pair<K, V>>, List<Pair<K, V2>>> rewriteResult4 = CompoundListType.fixValues(rewriteResult3.view().newType(), rewriteResult.view().newType(), this.element, rewriteResult2);
            return rewriteResult4.compose(rewriteResult3);
        }

        @Override
        public Optional<RewriteResult<List<Pair<K, V>>, ?>> one(TypeRewriteRule typeRewriteRule) {
            return DataFixUtils.or(typeRewriteRule.rewrite(this.key).map(this::lambda$one$0), () -> this.lambda$one$2(typeRewriteRule));
        }

        private static <K, V, K2> RewriteResult<List<Pair<K, V>>, List<Pair<K2, V>>> fixKeys(Type<List<Pair<K, V>>> type, Type<K> type2, Type<V> type3, RewriteResult<K, K2> rewriteResult) {
            return CompoundListType.opticView(type, rewriteResult, TypedOptic.compoundListKeys(type2, rewriteResult.view().newType(), type3));
        }

        private static <K, V, V2> RewriteResult<List<Pair<K, V>>, List<Pair<K, V2>>> fixValues(Type<List<Pair<K, V>>> type, Type<K> type2, Type<V> type3, RewriteResult<V, V2> rewriteResult) {
            return CompoundListType.opticView(type, rewriteResult, TypedOptic.compoundListElements(type2, type3, rewriteResult.view().newType()));
        }

        @Override
        public Type<?> updateMu(RecursiveTypeFamily recursiveTypeFamily) {
            return DSL.compoundList(this.key.updateMu(recursiveTypeFamily), this.element.updateMu(recursiveTypeFamily));
        }

        @Override
        public TypeTemplate buildTemplate() {
            return new CompoundList(this.key.template(), this.element.template());
        }

        @Override
        public Optional<List<Pair<K, V>>> point(DynamicOps<?> dynamicOps) {
            return Optional.of(ImmutableList.of());
        }

        @Override
        public <FT, FR> Either<TypedOptic<List<Pair<K, V>>, ?, FT, FR>, Type.FieldNotFoundException> findTypeInChildren(Type<FT> type, Type<FR> type2, Type.TypeMatcher<FT, FR> typeMatcher, boolean bl) {
            Either<TypedOptic<K, ?, FT, FR>, Type.FieldNotFoundException> either = this.key.findType(type, type2, typeMatcher, bl);
            return either.map(this::capLeft, arg_0 -> this.lambda$findTypeInChildren$3(type, type2, typeMatcher, bl, arg_0));
        }

        private <FT, K2, FR> Either<TypedOptic<List<Pair<K, V>>, ?, FT, FR>, Type.FieldNotFoundException> capLeft(TypedOptic<K, K2, FT, FR> typedOptic) {
            return Either.left(TypedOptic.compoundListKeys(typedOptic.sType(), typedOptic.tType(), this.element).compose(typedOptic));
        }

        private <FT, V2, FR> TypedOptic<List<Pair<K, V>>, ?, FT, FR> capRight(TypedOptic<V, V2, FT, FR> typedOptic) {
            return TypedOptic.compoundListElements(this.key, typedOptic.sType(), typedOptic.tType()).compose(typedOptic);
        }

        @Override
        protected Codec<List<Pair<K, V>>> buildCodec() {
            return Codec.compoundList(this.key.codec(), this.element.codec());
        }

        public String toString() {
            return "CompoundList[" + this.key + " -> " + this.element + "]";
        }

        @Override
        public boolean equals(Object object, boolean bl, boolean bl2) {
            if (!(object instanceof CompoundListType)) {
                return true;
            }
            CompoundListType compoundListType = (CompoundListType)object;
            return this.key.equals(compoundListType.key, bl, bl2) && this.element.equals(compoundListType.element, bl, bl2);
        }

        public int hashCode() {
            return Objects.hash(this.key, this.element);
        }

        public Type<K> getKey() {
            return this.key;
        }

        public Type<V> getElement() {
            return this.element;
        }

        private Either lambda$findTypeInChildren$3(Type type, Type type2, Type.TypeMatcher typeMatcher, boolean bl, Type.FieldNotFoundException fieldNotFoundException) {
            Either either = this.element.findType(type, type2, typeMatcher, bl);
            return either.mapLeft(this::capRight);
        }

        private Optional lambda$one$2(TypeRewriteRule typeRewriteRule) {
            return typeRewriteRule.rewrite(this.element).map(this::lambda$null$1);
        }

        private RewriteResult lambda$null$1(RewriteResult rewriteResult) {
            return CompoundListType.fixValues(this, this.key, this.element, rewriteResult);
        }

        private RewriteResult lambda$one$0(RewriteResult rewriteResult) {
            return CompoundListType.fixKeys(this, this.key, this.element, rewriteResult);
        }
    }
}

