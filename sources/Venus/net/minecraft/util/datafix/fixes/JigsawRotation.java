/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Map;
import java.util.Optional;
import net.minecraft.util.datafix.TypeReferences;

public class JigsawRotation
extends DataFix {
    private static final Map<String, String> field_233290_a_ = ImmutableMap.builder().put("down", "down_south").put("up", "up_north").put("north", "north_up").put("south", "south_up").put("west", "west_up").put("east", "east_up").build();

    public JigsawRotation(Schema schema, boolean bl) {
        super(schema, bl);
    }

    private static Dynamic<?> func_233292_a_(Dynamic<?> dynamic) {
        Optional<String> optional = dynamic.get("Name").asString().result();
        return optional.equals(Optional.of("minecraft:jigsaw")) ? dynamic.update("Properties", JigsawRotation::lambda$func_233292_a_$0) : dynamic;
    }

    @Override
    protected TypeRewriteRule makeRule() {
        return this.fixTypeEverywhereTyped("jigsaw_rotation_fix", this.getInputSchema().getType(TypeReferences.BLOCK_STATE), JigsawRotation::lambda$makeRule$1);
    }

    private static Typed lambda$makeRule$1(Typed typed) {
        return typed.update(DSL.remainderFinder(), JigsawRotation::func_233292_a_);
    }

    private static Dynamic lambda$func_233292_a_$0(Dynamic dynamic) {
        String string = dynamic.get("facing").asString("north");
        return dynamic.remove("facing").set("orientation", dynamic.createString(field_233290_a_.getOrDefault(string, string)));
    }
}

