/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.util.datafix.TypeReferences;

public class ItemStackEnchantmentFix
extends DataFix {
    private static final Int2ObjectMap<String> field_208047_a = DataFixUtils.make(new Int2ObjectOpenHashMap(), ItemStackEnchantmentFix::lambda$static$0);

    public ItemStackEnchantmentFix(Schema schema, boolean bl) {
        super(schema, bl);
    }

    @Override
    protected TypeRewriteRule makeRule() {
        Type<?> type = this.getInputSchema().getType(TypeReferences.ITEM_STACK);
        OpticFinder<?> opticFinder = type.findField("tag");
        return this.fixTypeEverywhereTyped("ItemStackEnchantmentFix", type, arg_0 -> this.lambda$makeRule$2(opticFinder, arg_0));
    }

    private Dynamic<?> fixTag(Dynamic<?> dynamic) {
        Optional<Dynamic> optional = dynamic.get("ench").asStreamOpt().map(ItemStackEnchantmentFix::lambda$fixTag$4).map(dynamic::createList).result();
        if (optional.isPresent()) {
            dynamic = dynamic.remove("ench").set("Enchantments", optional.get());
        }
        return dynamic.update("StoredEnchantments", ItemStackEnchantmentFix::lambda$fixTag$7);
    }

    private static Dynamic lambda$fixTag$7(Dynamic dynamic) {
        return DataFixUtils.orElse(dynamic.asStreamOpt().map(ItemStackEnchantmentFix::lambda$fixTag$6).map(dynamic::createList).result(), dynamic);
    }

    private static Stream lambda$fixTag$6(Stream stream) {
        return stream.map(ItemStackEnchantmentFix::lambda$fixTag$5);
    }

    private static Dynamic lambda$fixTag$5(Dynamic dynamic) {
        return dynamic.set("id", dynamic.createString(field_208047_a.getOrDefault(dynamic.get("id").asInt(0), "null")));
    }

    private static Stream lambda$fixTag$4(Stream stream) {
        return stream.map(ItemStackEnchantmentFix::lambda$fixTag$3);
    }

    private static Dynamic lambda$fixTag$3(Dynamic dynamic) {
        return dynamic.set("id", dynamic.createString(field_208047_a.getOrDefault(dynamic.get("id").asInt(0), "null")));
    }

    private Typed lambda$makeRule$2(OpticFinder opticFinder, Typed typed) {
        return typed.updateTyped(opticFinder, this::lambda$makeRule$1);
    }

    private Typed lambda$makeRule$1(Typed typed) {
        return typed.update(DSL.remainderFinder(), this::fixTag);
    }

    private static void lambda$static$0(Int2ObjectOpenHashMap int2ObjectOpenHashMap) {
        int2ObjectOpenHashMap.put(0, "minecraft:protection");
        int2ObjectOpenHashMap.put(1, "minecraft:fire_protection");
        int2ObjectOpenHashMap.put(2, "minecraft:feather_falling");
        int2ObjectOpenHashMap.put(3, "minecraft:blast_protection");
        int2ObjectOpenHashMap.put(4, "minecraft:projectile_protection");
        int2ObjectOpenHashMap.put(5, "minecraft:respiration");
        int2ObjectOpenHashMap.put(6, "minecraft:aqua_affinity");
        int2ObjectOpenHashMap.put(7, "minecraft:thorns");
        int2ObjectOpenHashMap.put(8, "minecraft:depth_strider");
        int2ObjectOpenHashMap.put(9, "minecraft:frost_walker");
        int2ObjectOpenHashMap.put(10, "minecraft:binding_curse");
        int2ObjectOpenHashMap.put(16, "minecraft:sharpness");
        int2ObjectOpenHashMap.put(17, "minecraft:smite");
        int2ObjectOpenHashMap.put(18, "minecraft:bane_of_arthropods");
        int2ObjectOpenHashMap.put(19, "minecraft:knockback");
        int2ObjectOpenHashMap.put(20, "minecraft:fire_aspect");
        int2ObjectOpenHashMap.put(21, "minecraft:looting");
        int2ObjectOpenHashMap.put(22, "minecraft:sweeping");
        int2ObjectOpenHashMap.put(32, "minecraft:efficiency");
        int2ObjectOpenHashMap.put(33, "minecraft:silk_touch");
        int2ObjectOpenHashMap.put(34, "minecraft:unbreaking");
        int2ObjectOpenHashMap.put(35, "minecraft:fortune");
        int2ObjectOpenHashMap.put(48, "minecraft:power");
        int2ObjectOpenHashMap.put(49, "minecraft:punch");
        int2ObjectOpenHashMap.put(50, "minecraft:flame");
        int2ObjectOpenHashMap.put(51, "minecraft:infinity");
        int2ObjectOpenHashMap.put(61, "minecraft:luck_of_the_sea");
        int2ObjectOpenHashMap.put(62, "minecraft:lure");
        int2ObjectOpenHashMap.put(65, "minecraft:loyalty");
        int2ObjectOpenHashMap.put(66, "minecraft:impaling");
        int2ObjectOpenHashMap.put(67, "minecraft:riptide");
        int2ObjectOpenHashMap.put(68, "minecraft:channeling");
        int2ObjectOpenHashMap.put(70, "minecraft:mending");
        int2ObjectOpenHashMap.put(71, "minecraft:vanishing_curse");
    }
}

