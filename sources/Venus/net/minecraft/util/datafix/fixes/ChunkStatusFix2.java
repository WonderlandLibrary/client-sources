/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.Map;
import java.util.Objects;
import net.minecraft.util.datafix.TypeReferences;

public class ChunkStatusFix2
extends DataFix {
    private static final Map<String, String> field_219825_a = ImmutableMap.builder().put("structure_references", "empty").put("biomes", "empty").put("base", "surface").put("carved", "carvers").put("liquid_carved", "liquid_carvers").put("decorated", "features").put("lighted", "light").put("mobs_spawned", "spawn").put("finalized", "heightmaps").put("fullchunk", "full").build();

    public ChunkStatusFix2(Schema schema, boolean bl) {
        super(schema, bl);
    }

    @Override
    protected TypeRewriteRule makeRule() {
        Type<?> type = this.getInputSchema().getType(TypeReferences.CHUNK);
        Type<?> type2 = type.findFieldType("Level");
        OpticFinder<?> opticFinder = DSL.fieldFinder("Level", type2);
        return this.fixTypeEverywhereTyped("ChunkStatusFix2", type, this.getOutputSchema().getType(TypeReferences.CHUNK), arg_0 -> ChunkStatusFix2.lambda$makeRule$1(opticFinder, arg_0));
    }

    private static Typed lambda$makeRule$1(OpticFinder opticFinder, Typed typed) {
        return typed.updateTyped(opticFinder, ChunkStatusFix2::lambda$makeRule$0);
    }

    private static Typed lambda$makeRule$0(Typed typed) {
        String string;
        Dynamic dynamic = typed.get(DSL.remainderFinder());
        String string2 = dynamic.get("Status").asString("empty");
        return Objects.equals(string2, string = field_219825_a.getOrDefault(string2, "empty")) ? typed : typed.set(DSL.remainderFinder(), dynamic.set("Status", dynamic.createString(string)));
    }
}

