/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.types.templates;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.FamilyOptic;
import com.mojang.datafixers.RewriteResult;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.families.TypeFamily;
import com.mojang.datafixers.util.Either;
import java.util.function.IntFunction;
import javax.annotation.Nullable;

public interface TypeTemplate {
    public int size();

    public TypeFamily apply(TypeFamily var1);

    default public Type<?> toSimpleType() {
        return this.apply(new TypeFamily(this){
            final TypeTemplate this$0;
            {
                this.this$0 = typeTemplate;
            }

            @Override
            public Type<?> apply(int n) {
                return DSL.emptyPartType();
            }
        }).apply(-1);
    }

    public <A, B> Either<TypeTemplate, Type.FieldNotFoundException> findFieldOrType(int var1, @Nullable String var2, Type<A> var3, Type<B> var4);

    public IntFunction<RewriteResult<?, ?>> hmap(TypeFamily var1, IntFunction<RewriteResult<?, ?>> var2);

    public <A, B> FamilyOptic<A, B> applyO(FamilyOptic<A, B> var1, Type<A> var2, Type<B> var3);
}

