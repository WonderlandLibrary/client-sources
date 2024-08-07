package net.minecraft.enchantment;

import net.minecraft.inventory.EquipmentSlotType;

public class SoulSpeedEnchantment extends Enchantment
{
    public SoulSpeedEnchantment(Rarity rarity, EquipmentSlotType... slots)
    {
        super(rarity, EnchantmentType.ARMOR_FEET, slots);
    }

    /**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    public int getMinEnchantability(int enchantmentLevel)
    {
        return enchantmentLevel * 10;
    }

    public int getMaxEnchantability(int enchantmentLevel)
    {
        return this.getMinEnchantability(enchantmentLevel) + 15;
    }

    public boolean isTreasureEnchantment()
    {
        return true;
    }

    /**
     * Checks if the enchantment can be sold by villagers in their trades.
     */
    public boolean canVillagerTrade()
    {
        return false;
    }

    /**
     * Checks if the enchantment can be applied to loot table drops.
     */
    public boolean canGenerateInLoot()
    {
        return false;
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    public int getMaxLevel()
    {
        return 3;
    }
}
