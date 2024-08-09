/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

import net.minecraft.potion.Effect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class PotionUtils {
    public static Effect getPotion(ResourceLocation resourceLocation) {
        return !Registry.EFFECTS.containsKey(resourceLocation) ? null : Registry.EFFECTS.getOrDefault(resourceLocation);
    }
}

