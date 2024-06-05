package me.mees.remix.modules.render;

import net.minecraft.enchantment.*;

public class EnchantEntry
{
    private Enchantment enchant;
    private String name;
    
    public EnchantEntry(final Enchantment enchant, final String name) {
        this.enchant = enchant;
        this.name = name;
    }
    
    public Enchantment getEnchant() {
        return this.enchant;
    }
    
    public String getName() {
        return this.name;
    }
}
