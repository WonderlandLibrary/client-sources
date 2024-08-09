/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TieredItem
extends Item {
    private final IItemTier tier;

    public TieredItem(IItemTier iItemTier, Item.Properties properties) {
        super(properties.defaultMaxDamage(iItemTier.getMaxUses()));
        this.tier = iItemTier;
    }

    public IItemTier getTier() {
        return this.tier;
    }

    @Override
    public int getItemEnchantability() {
        return this.tier.getEnchantability();
    }

    @Override
    public boolean getIsRepairable(ItemStack itemStack, ItemStack itemStack2) {
        return this.tier.getRepairMaterial().test(itemStack2) || super.getIsRepairable(itemStack, itemStack2);
    }
}

