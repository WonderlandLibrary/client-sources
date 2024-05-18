package net.minecraft.enchantment;

import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentMending extends Enchantment {
	public EnchantmentMending(Enchantment.Rarity rarityIn, EntityEquipmentSlot... slots) {
		super(rarityIn, EnumEnchantmentType.BREAKABLE, slots);
		this.setName("mending");
	}

	/**
	 * Returns the minimal value of enchantability needed on the enchantment level passed.
	 */
	@Override
	public int getMinEnchantability(int enchantmentLevel) {
		return enchantmentLevel * 25;
	}

	/**
	 * Returns the maximum value of enchantability nedded on the enchantment level passed.
	 */
	@Override
	public int getMaxEnchantability(int enchantmentLevel) {
		return this.getMinEnchantability(enchantmentLevel) + 50;
	}

	@Override
	public boolean isTreasureEnchantment() {
		return true;
	}

	/**
	 * Returns the maximum level that the enchantment can have.
	 */
	@Override
	public int getMaxLevel() {
		return 1;
	}
}
