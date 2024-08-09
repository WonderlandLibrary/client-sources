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
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.families.RecursiveTypeFamily;
import com.mojang.datafixers.types.families.TypeFamily;
import com.mojang.datafixers.types.templates.Const;
import com.mojang.datafixers.types.templates.RecursivePoint;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import java.util.Objects;
import java.util.Optional;
import java.util.function.IntFunction;
import javax.annotation.Nullable;

public final class Tag
implements TypeTemplate {
    private final String name;
    private final TypeTemplate element;

    public Tag(String string, TypeTemplate typeTemplate) {
        this.name = string;
        this.element = typeTemplate;
    }

    @Override
    public int size() {
        return this.element.size();
    }

    @Override
    public TypeFamily apply(TypeFamily typeFamily) {
        return new TypeFamily(this, typeFamily){
            final TypeFamily val$family;
            final Tag this$0;
            {
                this.this$0 = tag;
                this.val$family = typeFamily;
            }

            @Override
            public Type<?> apply(int n) {
                return DSL.field(Tag.access$000(this.this$0), Tag.access$100(this.this$0).apply(this.val$family).apply(n));
            }
        };
    }

    @Override
    public <A, B> FamilyOptic<A, B> applyO(FamilyOptic<A, B> familyOptic, Type<A> type, Type<B> type2) {
        return TypeFamily.familyOptic(arg_0 -> this.lambda$applyO$0(familyOptic, type, type2, arg_0));
    }

    public <FT, FR> Either<TypeTemplate, Type.FieldNotFoundException> findFieldOrType(int n, @Nullable String string, Type<FT> type, Type<FR> type2) {
        if (!Objects.equals(string, this.name)) {
            return Either.right(new Type.FieldNotFoundException("Names don't match"));
        }
        if (this.element instanceof Const) {
            Const const_ = (Const)this.element;
            if (Objects.equals(type, const_.type())) {
                return Either.left(new Tag(string, new Const(type2)));
            }
            return Either.right(new Type.FieldNotFoundException("don't match"));
        }
        if (Objects.equals(type, type2)) {
            return Either.left(this);
        }
        if (type instanceof RecursivePoint.RecursivePointType && this.element instanceof RecursivePoint && ((RecursivePoint)this.element).index() == ((RecursivePoint.RecursivePointType)type).index()) {
            if (type2 instanceof RecursivePoint.RecursivePointType) {
                if (((RecursivePoint.RecursivePointType)type2).index() == ((RecursivePoint)this.element).index()) {
                    return Either.left(this);
                }
            } else {
                return Either.left(DSL.constType(type2));
            }
        }
        return Either.right(new Type.FieldNotFoundException("Recursive field"));
    }

    @Override
    public IntFunction<RewriteResult<?, ?>> hmap(TypeFamily typeFamily, IntFunction<RewriteResult<?, ?>> intFunction) {
        return this.element.hmap(typeFamily, intFunction);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof Tag)) {
            return true;
        }
        Tag tag = (Tag)object;
        return Objects.equals(this.name, tag.name) && Objects.equals(this.element, tag.element);
    }

    public int hashCode() {
        return Objects.hash(this.name, this.element);
    }

    public String toString() {
        return "NameTag[" + this.name + ": " + this.element + "]";
    }

    private OpticParts lambda$applyO$0(FamilyOptic familyOptic, Type type, Type type2, int n) {
        return this.element.applyO(familyOptic, type, type2).apply(n);
    }

    static String access$000(Tag tag) {
        return tag.name;
    }

    static TypeTemplate access$100(Tag tag) {
        return tag.element;
    }

    public static final class TagType<A>
    extends Type<A> {
        protected final String name;
        protected final Type<A> element;

        public TagType(String string, Type<A> type) {
            this.name = string;
            this.element = type;
        }

        @Override
        public RewriteResult<A, ?> all(TypeRewriteRule typeRewriteRule, boolean bl, boolean bl2) {
            RewriteResult<A, ?> rewriteResult = this.element.rewriteOrNop(typeRewriteRule);
            return RewriteResult.create(this.cap(rewriteResult.view()), rewriteResult.recData());
        }

        private <B> View<A, ?> cap(View<A, B> view) {
            if (Objects.equals(view.function(), Functions.id())) {
                return View.nopView(this);
            }
            return View.create(this, DSL.field(this.name, view.newType()), view.function());
        }

        @Override
        public Optional<RewriteResult<A, ?>> one(TypeRewriteRule typeRewriteRule) {
            Optional<RewriteResult<A, ?>> optional = typeRewriteRule.rewrite(this.element);
            return optional.map(this::lambda$one$0);
        }

        @Override
        public Type<?> updateMu(RecursiveTypeFamily recursiveTypeFamily) {
            return DSL.field(this.name, this.element.updateMu(recursiveTypeFamily));
        }

        @Override
        public TypeTemplate buildTemplate() {
            return DSL.field(this.name, this.element.template());
        }

        @Override
        protected Codec<A> buildCodec() {
            return ((MapCodec)this.element.codec().fieldOf(this.name)).codec();
        }

        public String toString() {
            return "Tag[\"" + this.name + "\", " + this.element + "]";
        }

        @Override
        public boolean equals(Object object, boolean bl, boolean bl2) {
            if (this == object) {
                return false;
            }
            if (object == null || this.getClass() != object.getClass()) {
                return true;
            }
            TagType tagType = (TagType)object;
            return Objects.equals(this.name, tagType.name) && this.element.equals(tagType.element, bl, bl2);
        }

        public int hashCode() {
            return Objects.hash(this.name, this.element);
        }

        @Override
        public Optional<Type<?>> findFieldTypeOpt(String string) {
            if (Objects.equals(string, this.name)) {
                return Optional.of(this.element);
            }
            return Optional.empty();
        }

        @Override
        public Optional<A> point(DynamicOps<?> dynamicOps) {
            return this.element.point(dynamicOps);
        }

        @Override
        public <FT, FR> Either<TypedOptic<A, ?, FT, FR>, Type.FieldNotFoundException> findTypeInChildren(Type<FT> type, Type<FR> type2, Type.TypeMatcher<FT, FR> typeMatcher, boolean bl) {
            return this.element.findType(type, type2, typeMatcher, bl).mapLeft(this::wrapOptic);
        }

        private <B, FT, FR> TypedOptic<A, B, FT, FR> wrapOptic(TypedOptic<A, B, FT, FR> typedOptic) {
            return new TypedOptic<A, B, FT, FR>(typedOptic.bounds(), DSL.field(this.name, typedOptic.sType()), DSL.field(this.name, typedOptic.tType()), typedOptic.aType(), typedOptic.bType(), typedOptic.optic());
        }

        public String name() {
            return this.name;
        }

        public Type<A> element() {
            return this.element;
        }

        private RewriteResult lambda$one$0(RewriteResult rewriteResult) {
            return RewriteResult.create(this.cap(rewriteResult.view()), rewriteResult.recData());
        }
    }
}

