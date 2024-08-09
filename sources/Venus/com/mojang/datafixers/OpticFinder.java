/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.TypedOptic;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Either;
import javax.annotation.Nullable;

public interface OpticFinder<FT> {
    public Type<FT> type();

    public <A, FR> Either<TypedOptic<A, ?, FT, FR>, Type.FieldNotFoundException> findType(Type<A> var1, Type<FR> var2, boolean var3);

    default public <A> Either<TypedOptic<A, ?, FT, FT>, Type.FieldNotFoundException> findType(Type<A> type, boolean bl) {
        return this.findType(type, this.type(), bl);
    }

    default public <GT> OpticFinder<FT> inField(@Nullable String string, Type<GT> type) {
        OpticFinder opticFinder = this;
        return new OpticFinder<FT>(this, opticFinder, type, string){
            final OpticFinder val$outer;
            final Type val$type;
            final String val$name;
            final OpticFinder this$0;
            {
                this.this$0 = opticFinder;
                this.val$outer = opticFinder2;
                this.val$type = type;
                this.val$name = string;
            }

            @Override
            public Type<FT> type() {
                return this.val$outer.type();
            }

            @Override
            public <A, FR> Either<TypedOptic<A, ?, FT, FR>, Type.FieldNotFoundException> findType(Type<A> type, Type<FR> type2, boolean bl) {
                Either either = this.val$outer.findType(this.val$type, type2, bl);
                return either.map(arg_0 -> this.lambda$findType$0(type, bl, arg_0), Either::right);
            }

            private <A, FR, GR> Either<TypedOptic<A, ?, FT, FR>, Type.FieldNotFoundException> cap(Type<A> type, TypedOptic<GT, GR, FT, FR> typedOptic, boolean bl) {
                Either either = DSL.fieldFinder(this.val$name, this.val$type).findType(type, typedOptic.tType(), bl);
                return either.mapLeft(arg_0 -> 1.lambda$cap$1(typedOptic, arg_0));
            }

            private static TypedOptic lambda$cap$1(TypedOptic typedOptic, TypedOptic typedOptic2) {
                return typedOptic2.compose(typedOptic);
            }

            private Either lambda$findType$0(Type type, boolean bl, TypedOptic typedOptic) {
                return this.cap(type, typedOptic, bl);
            }
        };
    }
}

