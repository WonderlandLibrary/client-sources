/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.function.Function;
import net.minecraft.util.datafix.TypeReferences;

public class AdvancementRenamer
extends DataFix {
    private final String field_230066_a_;
    private final Function<String, String> field_230067_b_;

    public AdvancementRenamer(Schema schema, boolean bl, String string, Function<String, String> function) {
        super(schema, bl);
        this.field_230066_a_ = string;
        this.field_230067_b_ = function;
    }

    @Override
    protected TypeRewriteRule makeRule() {
        return this.fixTypeEverywhereTyped(this.field_230066_a_, this.getInputSchema().getType(TypeReferences.ADVANCEMENTS), this::lambda$makeRule$3);
    }

    private Typed lambda$makeRule$3(Typed typed) {
        return typed.update(DSL.remainderFinder(), this::lambda$makeRule$2);
    }

    private Dynamic lambda$makeRule$2(Dynamic dynamic) {
        return dynamic.updateMapValues(arg_0 -> this.lambda$makeRule$1(dynamic, arg_0));
    }

    private Pair lambda$makeRule$1(Dynamic dynamic, Pair pair) {
        String string = ((Dynamic)pair.getFirst()).asString("");
        return pair.mapFirst(arg_0 -> this.lambda$makeRule$0(dynamic, string, arg_0));
    }

    private Dynamic lambda$makeRule$0(Dynamic dynamic, String string, Dynamic dynamic2) {
        return dynamic.createString(this.field_230067_b_.apply(string));
    }
}

