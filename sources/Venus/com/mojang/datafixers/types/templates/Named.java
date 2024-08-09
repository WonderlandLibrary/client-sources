/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.types.templates;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.TypeToken;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.FamilyOptic;
import com.mojang.datafixers.OpticParts;
import com.mojang.datafixers.RewriteResult;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.TypedOptic;
import com.mojang.datafixers.functions.Functions;
import com.mojang.datafixers.kinds.K1;
import com.mojang.datafixers.optics.Optics;
import com.mojang.datafixers.optics.profunctors.Cartesian;
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
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.IntFunction;
import javax.annotation.Nullable;

public final class Named
implements TypeTemplate {
    private final String name;
    private final TypeTemplate element;

    public Named(String string, TypeTemplate typeTemplate) {
        this.name = string;
        this.element = typeTemplate;
    }

    @Override
    public int size() {
        return this.element.size();
    }

    @Override
    public TypeFamily apply(TypeFamily typeFamily) {
        return arg_0 -> this.lambda$apply$0(typeFamily, arg_0);
    }

    @Override
    public <A, B> FamilyOptic<A, B> applyO(FamilyOptic<A, B> familyOptic, Type<A> type, Type<B> type2) {
        return TypeFamily.familyOptic(arg_0 -> this.lambda$applyO$1(familyOptic, type, type2, arg_0));
    }

    public <FT, FR> Either<TypeTemplate, Type.FieldNotFoundException> findFieldOrType(int n, @Nullable String string, Type<FT> type, Type<FR> type2) {
        return this.element.findFieldOrType(n, string, type, type2);
    }

    @Override
    public IntFunction<RewriteResult<?, ?>> hmap(TypeFamily typeFamily, IntFunction<RewriteResult<?, ?>> intFunction) {
        return arg_0 -> this.lambda$hmap$2(typeFamily, intFunction, arg_0);
    }

    private <A> RewriteResult<Pair<String, A>, ?> cap(TypeFamily typeFamily, int n, RewriteResult<A, ?> rewriteResult) {
        return NamedType.fix((NamedType)this.apply(typeFamily).apply(n), rewriteResult);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof Named)) {
            return true;
        }
        Named named = (Named)object;
        return Objects.equals(this.name, named.name) && Objects.equals(this.element, named.element);
    }

    public int hashCode() {
        return Objects.hash(this.name, this.element);
    }

    public String toString() {
        return "NamedTypeTag[" + this.name + ": " + this.element + "]";
    }

    private RewriteResult lambda$hmap$2(TypeFamily typeFamily, IntFunction intFunction, int n) {
        RewriteResult<?, ?> rewriteResult = this.element.hmap(typeFamily, intFunction).apply(n);
        return this.cap(typeFamily, n, rewriteResult);
    }

    private OpticParts lambda$applyO$1(FamilyOptic familyOptic, Type type, Type type2, int n) {
        return this.element.applyO(familyOptic, type, type2).apply(n);
    }

    private Type lambda$apply$0(TypeFamily typeFamily, int n) {
        return DSL.named(this.name, this.element.apply(typeFamily).apply(n));
    }

    public static final class NamedType<A>
    extends Type<Pair<String, A>> {
        protected final String name;
        protected final Type<A> element;

        public NamedType(String string, Type<A> type) {
            this.name = string;
            this.element = type;
        }

        public static <A, B> RewriteResult<Pair<String, A>, ?> fix(NamedType<A> namedType, RewriteResult<A, B> rewriteResult) {
            if (Objects.equals(rewriteResult.view().function(), Functions.id())) {
                return RewriteResult.nop(namedType);
            }
            return NamedType.opticView(namedType, rewriteResult, NamedType.wrapOptic(namedType.name, TypedOptic.adapter(rewriteResult.view().type(), rewriteResult.view().newType())));
        }

        @Override
        public RewriteResult<Pair<String, A>, ?> all(TypeRewriteRule typeRewriteRule, boolean bl, boolean bl2) {
            RewriteResult<A, ?> rewriteResult = this.element.rewriteOrNop(typeRewriteRule);
            return NamedType.fix(this, rewriteResult);
        }

        @Override
        public Optional<RewriteResult<Pair<String, A>, ?>> one(TypeRewriteRule typeRewriteRule) {
            Optional<RewriteResult<A, ?>> optional = typeRewriteRule.rewrite(this.element);
            return optional.map(this::lambda$one$0);
        }

        @Override
        public Type<?> updateMu(RecursiveTypeFamily recursiveTypeFamily) {
            return DSL.named(this.name, this.element.updateMu(recursiveTypeFamily));
        }

        @Override
        public TypeTemplate buildTemplate() {
            return DSL.named(this.name, this.element.template());
        }

        @Override
        public Optional<TaggedChoice.TaggedChoiceType<?>> findChoiceType(String string, int n) {
            return this.element.findChoiceType(string, n);
        }

        @Override
        public Optional<Type<?>> findCheckedType(int n) {
            return this.element.findCheckedType(n);
        }

        @Override
        protected Codec<Pair<String, A>> buildCodec() {
            return new Codec<Pair<String, A>>(this){
                final NamedType this$0;
                {
                    this.this$0 = namedType;
                }

                @Override
                public <T> DataResult<Pair<Pair<String, A>, T>> decode(DynamicOps<T> dynamicOps, T t) {
                    return this.this$0.element.codec().decode(dynamicOps, t).map(this::lambda$decode$1).setLifecycle(Lifecycle.experimental());
                }

                @Override
                public <T> DataResult<T> encode(Pair<String, A> pair, DynamicOps<T> dynamicOps, T t) {
                    if (!Objects.equals(pair.getFirst(), this.this$0.name)) {
                        return DataResult.error("Named type name doesn't match: expected: " + this.this$0.name + ", got: " + pair.getFirst(), t);
                    }
                    return this.this$0.element.codec().encode(pair.getSecond(), dynamicOps, t).setLifecycle(Lifecycle.experimental());
                }

                @Override
                public DataResult encode(Object object, DynamicOps dynamicOps, Object object2) {
                    return this.encode((Pair)object, dynamicOps, object2);
                }

                private Pair lambda$decode$1(Pair pair) {
                    return pair.mapFirst(this::lambda$null$0);
                }

                private Pair lambda$null$0(Object object) {
                    return Pair.of(this.this$0.name, object);
                }
            };
        }

        public String toString() {
            return "NamedType[\"" + this.name + "\", " + this.element + "]";
        }

        public String name() {
            return this.name;
        }

        public Type<A> element() {
            return this.element;
        }

        @Override
        public boolean equals(Object object, boolean bl, boolean bl2) {
            if (this == object) {
                return false;
            }
            if (!(object instanceof NamedType)) {
                return true;
            }
            NamedType namedType = (NamedType)object;
            return Objects.equals(this.name, namedType.name) && this.element.equals(namedType.element, bl, bl2);
        }

        public int hashCode() {
            return Objects.hash(this.name, this.element);
        }

        @Override
        public Optional<Type<?>> findFieldTypeOpt(String string) {
            return this.element.findFieldTypeOpt(string);
        }

        @Override
        public Optional<Pair<String, A>> point(DynamicOps<?> dynamicOps) {
            return this.element.point(dynamicOps).map(this::lambda$point$1);
        }

        @Override
        public <FT, FR> Either<TypedOptic<Pair<String, A>, ?, FT, FR>, Type.FieldNotFoundException> findTypeInChildren(Type<FT> type, Type<FR> type2, Type.TypeMatcher<FT, FR> typeMatcher, boolean bl) {
            return this.element.findType(type, type2, typeMatcher, bl).mapLeft(this::lambda$findTypeInChildren$2);
        }

        protected static <A, B, FT, FR> TypedOptic<Pair<String, A>, Pair<String, B>, FT, FR> wrapOptic(String string, TypedOptic<A, B, FT, FR> typedOptic) {
            ImmutableSet.Builder builder = ImmutableSet.builder();
            builder.addAll(typedOptic.bounds());
            builder.add(Cartesian.Mu.TYPE_TOKEN);
            return new TypedOptic<Pair<String, A>, Pair<String, B>, FT, FR>((Set<TypeToken<? extends K1>>)((Object)builder.build()), DSL.named(string, typedOptic.sType()), DSL.named(string, typedOptic.tType()), typedOptic.aType(), typedOptic.bType(), Optics.proj2().composeUnchecked(typedOptic.optic()));
        }

        private TypedOptic lambda$findTypeInChildren$2(TypedOptic typedOptic) {
            return NamedType.wrapOptic(this.name, typedOptic);
        }

        private Pair lambda$point$1(Object object) {
            return Pair.of(this.name, object);
        }

        private RewriteResult lambda$one$0(RewriteResult rewriteResult) {
            return NamedType.fix(this, rewriteResult);
        }
    }
}

