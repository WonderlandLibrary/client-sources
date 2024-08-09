/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.datafix.TypeReferences;

public class SwapHandsFix
extends DataFix {
    private final String field_241315_a_;
    private final String field_241316_b_;
    private final String field_241317_c_;

    public SwapHandsFix(Schema schema, boolean bl, String string, String string2, String string3) {
        super(schema, bl);
        this.field_241315_a_ = string;
        this.field_241316_b_ = string2;
        this.field_241317_c_ = string3;
    }

    @Override
    public TypeRewriteRule makeRule() {
        return this.fixTypeEverywhereTyped(this.field_241315_a_, this.getInputSchema().getType(TypeReferences.OPTIONS), this::lambda$makeRule$2);
    }

    private Typed lambda$makeRule$2(Typed typed) {
        return typed.update(DSL.remainderFinder(), this::lambda$makeRule$1);
    }

    private Dynamic lambda$makeRule$1(Dynamic dynamic) {
        return DataFixUtils.orElse(dynamic.get(this.field_241316_b_).result().map(arg_0 -> this.lambda$makeRule$0(dynamic, arg_0)), dynamic);
    }

    private Dynamic lambda$makeRule$0(Dynamic dynamic, Dynamic dynamic2) {
        return dynamic.set(this.field_241317_c_, dynamic2).remove(this.field_241316_b_);
    }
}

