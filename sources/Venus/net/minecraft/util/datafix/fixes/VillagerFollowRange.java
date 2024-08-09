/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.datafix.fixes.NamedEntityFix;

public class VillagerFollowRange
extends NamedEntityFix {
    public VillagerFollowRange(Schema schema) {
        super(schema, false, "Villager Follow Range Fix", TypeReferences.ENTITY, "minecraft:villager");
    }

    @Override
    protected Typed<?> fix(Typed<?> typed) {
        return typed.update(DSL.remainderFinder(), VillagerFollowRange::func_233409_a_);
    }

    private static Dynamic<?> func_233409_a_(Dynamic<?> dynamic) {
        return dynamic.update("Attributes", arg_0 -> VillagerFollowRange.lambda$func_233409_a_$1(dynamic, arg_0));
    }

    private static Dynamic lambda$func_233409_a_$1(Dynamic dynamic, Dynamic dynamic2) {
        return dynamic.createList(dynamic2.asStream().map(VillagerFollowRange::lambda$func_233409_a_$0));
    }

    private static Dynamic lambda$func_233409_a_$0(Dynamic dynamic) {
        return dynamic.get("Name").asString("").equals("generic.follow_range") && dynamic.get("Base").asDouble(0.0) == 16.0 ? dynamic.set("Base", dynamic.createDouble(48.0)) : dynamic;
    }
}

