/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TaggedChoice;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DynamicOps;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.util.datafix.TypeReferences;

public class TileEntityId
extends DataFix {
    private static final Map<String, String> OLD_TO_NEW_ID_MAP = DataFixUtils.make(Maps.newHashMap(), TileEntityId::lambda$static$0);

    public TileEntityId(Schema schema, boolean bl) {
        super(schema, bl);
    }

    @Override
    public TypeRewriteRule makeRule() {
        Type<?> type = this.getInputSchema().getType(TypeReferences.ITEM_STACK);
        Type<?> type2 = this.getOutputSchema().getType(TypeReferences.ITEM_STACK);
        TaggedChoice.TaggedChoiceType<?> taggedChoiceType = this.getInputSchema().findChoiceType(TypeReferences.BLOCK_ENTITY);
        TaggedChoice.TaggedChoiceType<?> taggedChoiceType2 = this.getOutputSchema().findChoiceType(TypeReferences.BLOCK_ENTITY);
        return TypeRewriteRule.seq(this.convertUnchecked("item stack block entity name hook converter", type, type2), this.fixTypeEverywhere("BlockEntityIdFix", taggedChoiceType, taggedChoiceType2, TileEntityId::lambda$makeRule$3));
    }

    private static Function lambda$makeRule$3(DynamicOps dynamicOps) {
        return TileEntityId::lambda$makeRule$2;
    }

    private static Pair lambda$makeRule$2(Pair pair) {
        return pair.mapFirst(TileEntityId::lambda$makeRule$1);
    }

    private static String lambda$makeRule$1(String string) {
        return OLD_TO_NEW_ID_MAP.getOrDefault(string, string);
    }

    private static void lambda$static$0(HashMap hashMap) {
        hashMap.put("Airportal", "minecraft:end_portal");
        hashMap.put("Banner", "minecraft:banner");
        hashMap.put("Beacon", "minecraft:beacon");
        hashMap.put("Cauldron", "minecraft:brewing_stand");
        hashMap.put("Chest", "minecraft:chest");
        hashMap.put("Comparator", "minecraft:comparator");
        hashMap.put("Control", "minecraft:command_block");
        hashMap.put("DLDetector", "minecraft:daylight_detector");
        hashMap.put("Dropper", "minecraft:dropper");
        hashMap.put("EnchantTable", "minecraft:enchanting_table");
        hashMap.put("EndGateway", "minecraft:end_gateway");
        hashMap.put("EnderChest", "minecraft:ender_chest");
        hashMap.put("FlowerPot", "minecraft:flower_pot");
        hashMap.put("Furnace", "minecraft:furnace");
        hashMap.put("Hopper", "minecraft:hopper");
        hashMap.put("MobSpawner", "minecraft:mob_spawner");
        hashMap.put("Music", "minecraft:noteblock");
        hashMap.put("Piston", "minecraft:piston");
        hashMap.put("RecordPlayer", "minecraft:jukebox");
        hashMap.put("Sign", "minecraft:sign");
        hashMap.put("Skull", "minecraft:skull");
        hashMap.put("Structure", "minecraft:structure_block");
        hashMap.put("Trap", "minecraft:dispenser");
    }
}

