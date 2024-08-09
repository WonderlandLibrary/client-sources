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
import java.util.function.IntFunction;
import javax.annotation.Nullable;

public final class Hook
implements TypeTemplate {
    private final TypeTemplate element;
    private final HookFunction preRead;
    private final HookFunction postWrite;

    public Hook(TypeTemplate typeTemplate, HookFunction hookFunction, HookFunction hookFunction2) {
        this.element = typeTemplate;
        this.preRead = hookFunction;
        this.postWrite = hookFunction2;
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

    private <A> RewriteResult<A, ?> cap(TypeFamily typeFamily, int n, RewriteResult<A, ?> rewriteResult) {
        return HookType.fix((HookType)this.apply(typeFamily).apply(n), rewriteResult);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof Hook)) {
            return true;
        }
        Hook hook = (Hook)object;
        return Objects.equals(this.element, hook.element) && Objects.equals(this.preRead, hook.preRead) && Objects.equals(this.postWrite, hook.postWrite);
    }

    public int hashCode() {
        return Objects.hash(this.element, this.preRead, this.postWrite);
    }

    public String toString() {
        return "Hook[" + this.element + ", " + this.preRead + ", " + this.postWrite + "]";
    }

    private RewriteResult lambda$hmap$2(TypeFamily typeFamily, IntFunction intFunction, int n) {
        RewriteResult<?, ?> rewriteResult = this.element.hmap(typeFamily, intFunction).apply(n);
        return this.cap(typeFamily, n, rewriteResult);
    }

    private OpticParts lambda$applyO$1(FamilyOptic familyOptic, Type type, Type type2, int n) {
        return this.element.applyO(familyOptic, type, type2).apply(n);
    }

    private Type lambda$apply$0(TypeFamily typeFamily, int n) {
        return DSL.hook(this.element.apply(typeFamily).apply(n), this.preRead, this.postWrite);
    }

    public static final class HookType<A>
    extends Type<A> {
        private final Type<A> delegate;
        private final HookFunction preRead;
        private final HookFunction postWrite;

        public HookType(Type<A> type, HookFunction hookFunction, HookFunction hookFunction2) {
            this.delegate = type;
            this.preRead = hookFunction;
            this.postWrite = hookFunction2;
        }

        @Override
        protected Codec<A> buildCodec() {
            return new Codec<A>(this){
                final HookType this$0;
                {
                    this.this$0 = hookType;
                }

                @Override
                public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> dynamicOps, T t) {
                    return HookType.access$100(this.this$0).codec().decode(dynamicOps, HookType.access$000(this.this$0).apply(dynamicOps, t)).setLifecycle(Lifecycle.experimental());
                }

                @Override
                public <T> DataResult<T> encode(A a, DynamicOps<T> dynamicOps, T t) {
                    return HookType.access$100(this.this$0).codec().encode(a, dynamicOps, t).map(arg_0 -> this.lambda$encode$0(dynamicOps, arg_0)).setLifecycle(Lifecycle.experimental());
                }

                private Object lambda$encode$0(DynamicOps dynamicOps, Object object) {
                    return HookType.access$200(this.this$0).apply(dynamicOps, object);
                }
            };
        }

        @Override
        public RewriteResult<A, ?> all(TypeRewriteRule typeRewriteRule, boolean bl, boolean bl2) {
            return HookType.fix(this, this.delegate.rewriteOrNop(typeRewriteRule));
        }

        @Override
        public Optional<RewriteResult<A, ?>> one(TypeRewriteRule typeRewriteRule) {
            return typeRewriteRule.rewrite(this.delegate).map(this::lambda$one$0);
        }

        @Override
        public Type<?> updateMu(RecursiveTypeFamily recursiveTypeFamily) {
            return new HookType(this.delegate.updateMu(recursiveTypeFamily), this.preRead, this.postWrite);
        }

        @Override
        public TypeTemplate buildTemplate() {
            return DSL.hook(this.delegate.template(), this.preRead, this.postWrite);
        }

        @Override
        public Optional<TaggedChoice.TaggedChoiceType<?>> findChoiceType(String string, int n) {
            return this.delegate.findChoiceType(string, n);
        }

        @Override
        public Optional<Type<?>> findCheckedType(int n) {
            return this.delegate.findCheckedType(n);
        }

        @Override
        public Optional<Type<?>> findFieldTypeOpt(String string) {
            return this.delegate.findFieldTypeOpt(string);
        }

        @Override
        public Optional<A> point(DynamicOps<?> dynamicOps) {
            return this.delegate.point(dynamicOps);
        }

        @Override
        public <FT, FR> Either<TypedOptic<A, ?, FT, FR>, Type.FieldNotFoundException> findTypeInChildren(Type<FT> type, Type<FR> type2, Type.TypeMatcher<FT, FR> typeMatcher, boolean bl) {
            return this.delegate.findType(type, type2, typeMatcher, bl).mapLeft(this::lambda$findTypeInChildren$1);
        }

        public static <A, B> RewriteResult<A, ?> fix(HookType<A> hookType, RewriteResult<A, B> rewriteResult) {
            if (Objects.equals(rewriteResult.view().function(), Functions.id())) {
                return RewriteResult.nop(hookType);
            }
            return HookType.opticView(hookType, rewriteResult, HookType.wrapOptic(TypedOptic.adapter(rewriteResult.view().type(), rewriteResult.view().newType()), hookType.preRead, hookType.postWrite));
        }

        protected static <A, B, FT, FR> TypedOptic<A, B, FT, FR> wrapOptic(TypedOptic<A, B, FT, FR> typedOptic, HookFunction hookFunction, HookFunction hookFunction2) {
            return new TypedOptic<A, B, FT, FR>(typedOptic.bounds(), DSL.hook(typedOptic.sType(), hookFunction, hookFunction2), DSL.hook(typedOptic.tType(), hookFunction, hookFunction2), typedOptic.aType(), typedOptic.bType(), typedOptic.optic());
        }

        public String toString() {
            return "HookType[" + this.delegate + ", " + this.preRead + ", " + this.postWrite + "]";
        }

        @Override
        public boolean equals(Object object, boolean bl, boolean bl2) {
            if (!(object instanceof HookType)) {
                return true;
            }
            HookType hookType = (HookType)object;
            return this.delegate.equals(hookType.delegate, bl, bl2) && Objects.equals(this.preRead, hookType.preRead) && Objects.equals(this.postWrite, hookType.postWrite);
        }

        public int hashCode() {
            return Objects.hash(this.delegate, this.preRead, this.postWrite);
        }

        private TypedOptic lambda$findTypeInChildren$1(TypedOptic typedOptic) {
            return HookType.wrapOptic(typedOptic, this.preRead, this.postWrite);
        }

        private RewriteResult lambda$one$0(RewriteResult rewriteResult) {
            return HookType.fix(this, rewriteResult);
        }

        static HookFunction access$000(HookType hookType) {
            return hookType.preRead;
        }

        static Type access$100(HookType hookType) {
            return hookType.delegate;
        }

        static HookFunction access$200(HookType hookType) {
            return hookType.postWrite;
        }
    }

    public static interface HookFunction {
        public static final HookFunction IDENTITY = new HookFunction(){

            @Override
            public <T> T apply(DynamicOps<T> dynamicOps, T t) {
                return t;
            }
        };

        public <T> T apply(DynamicOps<T> var1, T var2);
    }
}

