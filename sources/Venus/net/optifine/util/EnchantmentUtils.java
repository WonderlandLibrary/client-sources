/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class EnchantmentUtils {
    private static final Map<String, Enchantment> MAP_ENCHANTMENTS = new HashMap<String, Enchantment>();

    public static Enchantment getEnchantment(String string) {
        Enchantment enchantment = MAP_ENCHANTMENTS.get(string);
        if (enchantment == null) {
            ResourceLocation resourceLocation = new ResourceLocation(string);
            if (Registry.ENCHANTMENT.containsKey(resourceLocation)) {
                enchantment = Registry.ENCHANTMENT.getOrDefault(resourceLocation);
            }
            MAP_ENCHANTMENTS.put(string, enchantment);
        }
        return enchantment;
    }

    public static Enchantment getEnchantment(ResourceLocation resourceLocation) {
        return !Registry.ENCHANTMENT.containsKey(resourceLocation) ? null : Registry.ENCHANTMENT.getOrDefault(resourceLocation);
    }
}

