/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.util.datafix.NamespacedSchema;
import net.minecraft.util.datafix.TypeReferences;

public class ShulkerBoxItemColor
extends DataFix {
    public static final String[] NAMES_BY_COLOR = new String[]{"minecraft:white_shulker_box", "minecraft:orange_shulker_box", "minecraft:magenta_shulker_box", "minecraft:light_blue_shulker_box", "minecraft:yellow_shulker_box", "minecraft:lime_shulker_box", "minecraft:pink_shulker_box", "minecraft:gray_shulker_box", "minecraft:silver_shulker_box", "minecraft:cyan_shulker_box", "minecraft:purple_shulker_box", "minecraft:blue_shulker_box", "minecraft:brown_shulker_box", "minecraft:green_shulker_box", "minecraft:red_shulker_box", "minecraft:black_shulker_box"};

    public ShulkerBoxItemColor(Schema schema, boolean bl) {
        super(schema, bl);
    }

    @Override
    public TypeRewriteRule makeRule() {
        Type<?> type = this.getInputSchema().getType(TypeReferences.ITEM_STACK);
        OpticFinder<Pair<String, String>> opticFinder = DSL.fieldFinder("id", DSL.named(TypeReferences.ITEM_NAME.typeName(), NamespacedSchema.func_233457_a_()));
        OpticFinder<?> opticFinder2 = type.findField("tag");
        OpticFinder<?> opticFinder3 = opticFinder2.type().findField("BlockEntityTag");
        return this.fixTypeEverywhereTyped("ItemShulkerBoxColorFix", type, arg_0 -> ShulkerBoxItemColor.lambda$makeRule$0(opticFinder, opticFinder2, opticFinder3, arg_0));
    }

    private static Typed lambda$makeRule$0(OpticFinder opticFinder, OpticFinder opticFinder2, OpticFinder opticFinder3, Typed typed) {
        Typed typed2;
        Optional optional;
        Optional optional2;
        Optional optional3 = typed.getOptional(opticFinder);
        if (optional3.isPresent() && Objects.equals(((Pair)optional3.get()).getSecond(), "minecraft:shulker_box") && (optional2 = typed.getOptionalTyped(opticFinder2)).isPresent() && (optional = (typed2 = optional2.get()).getOptionalTyped(opticFinder3)).isPresent()) {
            Typed<Dynamic<?>> typed3 = optional.get();
            Dynamic<?> dynamic = typed3.get(DSL.remainderFinder());
            int n = dynamic.get("Color").asInt(0);
            dynamic.remove("Color");
            return typed.set(opticFinder2, typed2.set(opticFinder3, typed3.set(DSL.remainderFinder(), dynamic))).set(opticFinder, Pair.of(TypeReferences.ITEM_NAME.typeName(), NAMES_BY_COLOR[n % 16]));
        }
        return typed;
    }
}

