/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.schemas.Schema;
import java.util.Map;
import net.minecraft.util.datafix.fixes.RecipeRenamer;

public class RecipeRenamer1510
extends RecipeRenamer {
    private static final Map<String, String> field_211869_a = ImmutableMap.builder().put("minecraft:acacia_bark", "minecraft:acacia_wood").put("minecraft:birch_bark", "minecraft:birch_wood").put("minecraft:dark_oak_bark", "minecraft:dark_oak_wood").put("minecraft:jungle_bark", "minecraft:jungle_wood").put("minecraft:oak_bark", "minecraft:oak_wood").put("minecraft:spruce_bark", "minecraft:spruce_wood").build();

    public RecipeRenamer1510(Schema schema, boolean bl) {
        super(schema, bl, "Recipes renamening fix", RecipeRenamer1510::lambda$new$0);
    }

    private static String lambda$new$0(String string) {
        return field_211869_a.getOrDefault(string, string);
    }
}

