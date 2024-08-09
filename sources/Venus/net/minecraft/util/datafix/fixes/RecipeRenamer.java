/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DynamicOps;
import java.util.Objects;
import java.util.function.Function;
import net.minecraft.util.datafix.NamespacedSchema;
import net.minecraft.util.datafix.TypeReferences;

public class RecipeRenamer
extends DataFix {
    private final String field_230073_a_;
    private final Function<String, String> field_230074_b_;

    public RecipeRenamer(Schema schema, boolean bl, String string, Function<String, String> function) {
        super(schema, bl);
        this.field_230073_a_ = string;
        this.field_230074_b_ = function;
    }

    @Override
    protected TypeRewriteRule makeRule() {
        Type<Pair<String, String>> type = DSL.named(TypeReferences.RECIPE.typeName(), NamespacedSchema.func_233457_a_());
        if (!Objects.equals(type, this.getInputSchema().getType(TypeReferences.RECIPE))) {
            throw new IllegalStateException("Recipe type is not what was expected.");
        }
        return this.fixTypeEverywhere(this.field_230073_a_, type, this::lambda$makeRule$1);
    }

    private Function lambda$makeRule$1(DynamicOps dynamicOps) {
        return this::lambda$makeRule$0;
    }

    private Pair lambda$makeRule$0(Pair pair) {
        return pair.mapSecond(this.field_230074_b_);
    }
}

