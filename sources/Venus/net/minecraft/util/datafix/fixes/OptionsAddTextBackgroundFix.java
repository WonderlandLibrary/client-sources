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

public class OptionsAddTextBackgroundFix
extends DataFix {
    public OptionsAddTextBackgroundFix(Schema schema, boolean bl) {
        super(schema, bl);
    }

    @Override
    public TypeRewriteRule makeRule() {
        return this.fixTypeEverywhereTyped("OptionsAddTextBackgroundFix", this.getInputSchema().getType(TypeReferences.OPTIONS), this::lambda$makeRule$2);
    }

    private double func_219856_a(String string) {
        try {
            double d = 0.9 * Double.parseDouble(string) + 0.1;
            return d / 2.0;
        } catch (NumberFormatException numberFormatException) {
            return 0.5;
        }
    }

    private Typed lambda$makeRule$2(Typed typed) {
        return typed.update(DSL.remainderFinder(), this::lambda$makeRule$1);
    }

    private Dynamic lambda$makeRule$1(Dynamic dynamic) {
        return DataFixUtils.orElse(dynamic.get("chatOpacity").asString().map(arg_0 -> this.lambda$makeRule$0(dynamic, arg_0)).result(), dynamic);
    }

    private Dynamic lambda$makeRule$0(Dynamic dynamic, String string) {
        return dynamic.set("textBackgroundOpacity", dynamic.createDouble(this.func_219856_a(string)));
    }
}

