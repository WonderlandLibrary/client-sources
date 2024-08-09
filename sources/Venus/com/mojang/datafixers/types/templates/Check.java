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
import java.util.Objects;
import java.util.Optional;
import java.util.function.IntFunction;
import javax.annotation.Nullable;

public final class Check
implements TypeTemplate {
    private final String name;
    private final int index;
    private final TypeTemplate element;

    public Check(String string, int n, TypeTemplate typeTemplate) {
        this.name = string;
        this.index = n;
        this.element = typeTemplate;
    }

    @Override
    public int size() {
        return Math.max(this.index + 1, this.element.size());
    }

    @Override
    public TypeFamily apply(TypeFamily typeFamily) {
        return new TypeFamily(this, typeFamily){
            final TypeFamily val$family;
            final Check this$0;
            {
                this.this$0 = check;
                this.val$family = typeFamily;
            }

            @Override
            public Type<?> apply(int n) {
                if (n < 0) {
                    throw new IndexOutOfBoundsException();
                }
                return new CheckType(Check.access$000(this.this$0), n, Check.access$100(this.this$0), Check.access$200(this.this$0).apply(this.val$family).apply(n));
            }
        };
    }

    @Override
    public <A, B> FamilyOptic<A, B> applyO(FamilyOptic<A, B> familyOptic, Type<A> type, Type<B> type2) {
        return TypeFamily.familyOptic(arg_0 -> this.lambda$applyO$0(familyOptic, type, type2, arg_0));
    }

    public <FT, FR> Either<TypeTemplate, Type.FieldNotFoundException> findFieldOrType(int n, @Nullable String string, Type<FT> type, Type<FR> type2) {
        if (n == this.index) {
            return this.element.findFieldOrType(n, string, type, type2);
        }
        return Either.right(new Type.FieldNotFoundException("Not a matching index"));
    }

    @Override
    public IntFunction<RewriteResult<?, ?>> hmap(TypeFamily typeFamily, IntFunction<RewriteResult<?, ?>> intFunction) {
        return arg_0 -> this.lambda$hmap$1(typeFamily, intFunction, arg_0);
    }

    private <A> RewriteResult<?, ?> cap(TypeFamily typeFamily, int n, RewriteResult<A, ?> rewriteResult) {
        return CheckType.fix((CheckType)this.apply(typeFamily).apply(n), rewriteResult);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof Check)) {
            return true;
        }
        Check check = (Check)object;
        return Objects.equals(this.name, check.name) && this.index == check.index && Objects.equals(this.element, check.element);
    }

    public int hashCode() {
        return Objects.hash(this.name, this.index, this.element);
    }

    public String toString() {
        return "Tag[" + this.name + ", " + this.index + ": " + this.element + "]";
    }

    private RewriteResult lambda$hmap$1(TypeFamily typeFamily, IntFunction intFunction, int n) {
        RewriteResult<?, ?> rewriteResult = this.element.hmap(typeFamily, intFunction).apply(n);
        return this.cap(typeFamily, n, rewriteResult);
    }

    private OpticParts lambda$applyO$0(FamilyOptic familyOptic, Type type, Type type2, int n) {
        return this.element.applyO(familyOptic, type, type2).apply(n);
    }

    static String access$000(Check check) {
        return check.name;
    }

    static int access$100(Check check) {
        return check.index;
    }

    static TypeTemplate access$200(Check check) {
        return check.element;
    }

    public static final class CheckType<A>
    extends Type<A> {
        private final String name;
        private final int index;
        private final int expectedIndex;
        private final Type<A> delegate;

        public CheckType(String string, int n, int n2, Type<A> type) {
            this.name = string;
            this.index = n;
            this.expectedIndex = n2;
            this.delegate = type;
        }

        @Override
        protected Codec<A> buildCodec() {
            return Codec.of(this.delegate.codec(), this::read);
        }

        private <T> DataResult<Pair<A, T>> read(DynamicOps<T> dynamicOps, T t) {
            if (this.index != this.expectedIndex) {
                return DataResult.error("Index mismatch: " + this.index + " != " + this.expectedIndex);
            }
            return this.delegate.codec().decode(dynamicOps, t);
        }

        public static <A, B> RewriteResult<A, ?> fix(CheckType<A> checkType, RewriteResult<A, B> rewriteResult) {
            if (Objects.equals(rewriteResult.view().function(), Functions.id())) {
                return RewriteResult.nop(checkType);
            }
            return CheckType.opticView(checkType, rewriteResult, CheckType.wrapOptic(checkType, TypedOptic.adapter(rewriteResult.view().type(), rewriteResult.view().newType())));
        }

        @Override
        public RewriteResult<A, ?> all(TypeRewriteRule typeRewriteRule, boolean bl, boolean bl2) {
            if (bl2 && this.index != this.expectedIndex) {
                return RewriteResult.nop(this);
            }
            return CheckType.fix(this, this.delegate.rewriteOrNop(typeRewriteRule));
        }

        @Override
        public Optional<RewriteResult<A, ?>> everywhere(TypeRewriteRule typeRewriteRule, PointFreeRule pointFreeRule, boolean bl, boolean bl2) {
            if (bl2 && this.index != this.expectedIndex) {
                return Optional.empty();
            }
            return super.everywhere(typeRewriteRule, pointFreeRule, bl, bl2);
        }

        @Override
        public Optional<RewriteResult<A, ?>> one(TypeRewriteRule typeRewriteRule) {
            return typeRewriteRule.rewrite(this.delegate).map(this::lambda$one$0);
        }

        @Override
        public Type<?> updateMu(RecursiveTypeFamily recursiveTypeFamily) {
            return new CheckType(this.name, this.index, this.expectedIndex, this.delegate.updateMu(recursiveTypeFamily));
        }

        @Override
        public TypeTemplate buildTemplate() {
            return DSL.check(this.name, this.expectedIndex, this.delegate.template());
        }

        @Override
        public Optional<TaggedChoice.TaggedChoiceType<?>> findChoiceType(String string, int n) {
            if (n == this.expectedIndex) {
                return this.delegate.findChoiceType(string, n);
            }
            return Optional.empty();
        }

        @Override
        public Optional<Type<?>> findCheckedType(int n) {
            if (n == this.expectedIndex) {
                return Optional.of(this.delegate);
            }
            return Optional.empty();
        }

        @Override
        public Optional<Type<?>> findFieldTypeOpt(String string) {
            if (this.index == this.expectedIndex) {
                return this.delegate.findFieldTypeOpt(string);
            }
            return Optional.empty();
        }

        @Override
        public Optional<A> point(DynamicOps<?> dynamicOps) {
            if (this.index == this.expectedIndex) {
                return this.delegate.point(dynamicOps);
            }
            return Optional.empty();
        }

        @Override
        public <FT, FR> Either<TypedOptic<A, ?, FT, FR>, Type.FieldNotFoundException> findTypeInChildren(Type<FT> type, Type<FR> type2, Type.TypeMatcher<FT, FR> typeMatcher, boolean bl) {
            if (this.index != this.expectedIndex) {
                return Either.right(new Type.FieldNotFoundException("Incorrect index in CheckType"));
            }
            return this.delegate.findType(type, type2, typeMatcher, bl).mapLeft(this::lambda$findTypeInChildren$1);
        }

        protected static <A, B, FT, FR> TypedOptic<A, B, FT, FR> wrapOptic(CheckType<A> checkType, TypedOptic<A, B, FT, FR> typedOptic) {
            return new TypedOptic<A, B, FT, FR>(typedOptic.bounds(), checkType, new CheckType<B>(checkType.name, checkType.index, checkType.expectedIndex, typedOptic.tType()), typedOptic.aType(), typedOptic.bType(), typedOptic.optic());
        }

        public String toString() {
            return "TypeTag[" + this.index + "~" + this.expectedIndex + "][" + this.name + ": " + this.delegate + "]";
        }

        @Override
        public boolean equals(Object object, boolean bl, boolean bl2) {
            if (!(object instanceof CheckType)) {
                return true;
            }
            CheckType checkType = (CheckType)object;
            if (this.index == checkType.index && this.expectedIndex == checkType.expectedIndex) {
                if (!bl2) {
                    return false;
                }
                if (this.delegate.equals(checkType.delegate, bl, bl2)) {
                    return false;
                }
            }
            return true;
        }

        public int hashCode() {
            return Objects.hash(this.index, this.expectedIndex, this.delegate);
        }

        private TypedOptic lambda$findTypeInChildren$1(TypedOptic typedOptic) {
            return CheckType.wrapOptic(this, typedOptic);
        }

        private RewriteResult lambda$one$0(RewriteResult rewriteResult) {
            return CheckType.fix(this, rewriteResult);
        }
    }
}

