/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Set;
import net.minecraft.util.datafix.TypeReferences;

public class WallProperty
extends DataFix {
    private static final Set<String> field_233415_a_ = ImmutableSet.of("minecraft:andesite_wall", "minecraft:brick_wall", "minecraft:cobblestone_wall", "minecraft:diorite_wall", "minecraft:end_stone_brick_wall", "minecraft:granite_wall", "minecraft:mossy_cobblestone_wall", "minecraft:mossy_stone_brick_wall", "minecraft:nether_brick_wall", "minecraft:prismarine_wall", "minecraft:red_nether_brick_wall", "minecraft:red_sandstone_wall", "minecraft:sandstone_wall", "minecraft:stone_brick_wall");

    public WallProperty(Schema schema, boolean bl) {
        super(schema, bl);
    }

    @Override
    public TypeRewriteRule makeRule() {
        return this.fixTypeEverywhereTyped("WallPropertyFix", this.getInputSchema().getType(TypeReferences.BLOCK_STATE), WallProperty::lambda$makeRule$0);
    }

    private static String func_233419_a_(String string) {
        return "true".equals(string) ? "low" : "none";
    }

    private static <T> Dynamic<T> func_233418_a_(Dynamic<T> dynamic, String string) {
        return dynamic.update(string, WallProperty::lambda$func_233418_a_$1);
    }

    private static <T> Dynamic<T> func_233417_a_(Dynamic<T> dynamic) {
        boolean bl = dynamic.get("Name").asString().result().filter(field_233415_a_::contains).isPresent();
        return !bl ? dynamic : dynamic.update("Properties", WallProperty::lambda$func_233417_a_$2);
    }

    private static Dynamic lambda$func_233417_a_$2(Dynamic dynamic) {
        Dynamic dynamic2 = WallProperty.func_233418_a_(dynamic, "east");
        dynamic2 = WallProperty.func_233418_a_(dynamic2, "west");
        dynamic2 = WallProperty.func_233418_a_(dynamic2, "north");
        return WallProperty.func_233418_a_(dynamic2, "south");
    }

    private static Dynamic lambda$func_233418_a_$1(Dynamic dynamic) {
        return DataFixUtils.orElse(dynamic.asString().result().map(WallProperty::func_233419_a_).map(dynamic::createString), dynamic);
    }

    private static Typed lambda$makeRule$0(Typed typed) {
        return typed.update(DSL.remainderFinder(), WallProperty::func_233417_a_);
    }
}

