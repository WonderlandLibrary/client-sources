/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.types.templates;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.FamilyOptic;
import com.mojang.datafixers.OpticParts;
import com.mojang.datafixers.RewriteResult;
import com.mojang.datafixers.TypedOptic;
import com.mojang.datafixers.optics.Optics;
import com.mojang.datafixers.optics.profunctors.AffineP;
import com.mojang.datafixers.optics.profunctors.Profunctor;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.families.TypeFamily;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import java.util.Objects;
import java.util.function.IntFunction;
import javax.annotation.Nullable;

public final class Const
implements TypeTemplate {
    private final Type<?> type;

    public Const(Type<?> type) {
        this.type = type;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public TypeFamily apply(TypeFamily typeFamily) {
        return new TypeFamily(this){
            final Const this$0;
            {
                this.this$0 = const_;
            }

            @Override
            public Type<?> apply(int n) {
                return Const.access$000(this.this$0);
            }
        };
    }

    @Override
    public <A, B> FamilyOptic<A, B> applyO(FamilyOptic<A, B> familyOptic, Type<A> type, Type<B> type2) {
        if (Objects.equals(this.type, type)) {
            return TypeFamily.familyOptic(Const::lambda$applyO$0);
        }
        TypedOptic<?, ?, A, B> typedOptic = this.makeIgnoreOptic(this.type, type, type2);
        return TypeFamily.familyOptic(arg_0 -> Const.lambda$applyO$1(typedOptic, arg_0));
    }

    private <T, A, B> TypedOptic<T, T, A, B> makeIgnoreOptic(Type<T> type, Type<A> type2, Type<B> type3) {
        return new TypedOptic<T, T, A, B>(AffineP.Mu.TYPE_TOKEN, type, type, type2, type3, Optics.affine(Either::left, Const::lambda$makeIgnoreOptic$2));
    }

    public <FT, FR> Either<TypeTemplate, Type.FieldNotFoundException> findFieldOrType(int n, @Nullable String string, Type<FT> type, Type<FR> type2) {
        return DSL.fieldFinder(string, type).findType(this.type, type2, false).mapLeft(Const::lambda$findFieldOrType$3);
    }

    @Override
    public IntFunction<RewriteResult<?, ?>> hmap(TypeFamily typeFamily, IntFunction<RewriteResult<?, ?>> intFunction) {
        return this::lambda$hmap$4;
    }

    public boolean equals(Object object) {
        return object instanceof Const && Objects.equals(this.type, ((Const)object).type);
    }

    public int hashCode() {
        return Objects.hash(this.type);
    }

    public String toString() {
        return "Const[" + this.type + "]";
    }

    public Type<?> type() {
        return this.type;
    }

    private RewriteResult lambda$hmap$4(int n) {
        return RewriteResult.nop(this.type);
    }

    private static TypeTemplate lambda$findFieldOrType$3(TypedOptic typedOptic) {
        return new Const(typedOptic.tType());
    }

    private static Object lambda$makeIgnoreOptic$2(Object object, Object object2) {
        return object2;
    }

    private static OpticParts lambda$applyO$1(TypedOptic typedOptic, int n) {
        return new OpticParts(typedOptic.bounds(), typedOptic.optic());
    }

    private static OpticParts lambda$applyO$0(int n) {
        return new OpticParts(ImmutableSet.of(Profunctor.Mu.TYPE_TOKEN), Optics.id());
    }

    static Type access$000(Const const_) {
        return const_.type;
    }

    public static final class PrimitiveType<A>
    extends Type<A> {
        private final Codec<A> codec;

        public PrimitiveType(Codec<A> codec) {
            this.codec = codec;
        }

        @Override
        public boolean equals(Object object, boolean bl, boolean bl2) {
            return this == object;
        }

        @Override
        public TypeTemplate buildTemplate() {
            return DSL.constType(this);
        }

        @Override
        protected Codec<A> buildCodec() {
            return this.codec;
        }

        public String toString() {
            return this.codec.toString();
        }
    }
}

