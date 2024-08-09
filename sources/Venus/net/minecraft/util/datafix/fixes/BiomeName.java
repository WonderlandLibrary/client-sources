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
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import net.minecraft.util.datafix.NamespacedSchema;
import net.minecraft.util.datafix.TypeReferences;

public class BiomeName
extends DataFix {
    private final String field_233377_a_;
    private final Map<String, String> field_233378_b_;

    public BiomeName(Schema schema, boolean bl, String string, Map<String, String> map) {
        super(schema, bl);
        this.field_233378_b_ = map;
        this.field_233377_a_ = string;
    }

    @Override
    protected TypeRewriteRule makeRule() {
        Type<Pair<String, String>> type = DSL.named(TypeReferences.BIOME.typeName(), NamespacedSchema.func_233457_a_());
        if (!Objects.equals(type, this.getInputSchema().getType(TypeReferences.BIOME))) {
            throw new IllegalStateException("Biome type is not what was expected.");
        }
        return this.fixTypeEverywhere(this.field_233377_a_, type, this::lambda$makeRule$2);
    }

    private Function lambda$makeRule$2(DynamicOps dynamicOps) {
        return this::lambda$makeRule$1;
    }

    private Pair lambda$makeRule$1(Pair pair) {
        return pair.mapSecond(this::lambda$makeRule$0);
    }

    private String lambda$makeRule$0(String string) {
        return this.field_233378_b_.getOrDefault(string, string);
    }
}

