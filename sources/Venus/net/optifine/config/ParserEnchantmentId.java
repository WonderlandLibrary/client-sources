/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.config;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.optifine.config.IParserInt;
import net.optifine.util.EnchantmentUtils;

public class ParserEnchantmentId
implements IParserInt {
    @Override
    public int parse(String string, int n) {
        ResourceLocation resourceLocation = new ResourceLocation(string);
        Enchantment enchantment = EnchantmentUtils.getEnchantment(resourceLocation);
        return enchantment == null ? n : Registry.ENCHANTMENT.getId(enchantment);
    }
}

