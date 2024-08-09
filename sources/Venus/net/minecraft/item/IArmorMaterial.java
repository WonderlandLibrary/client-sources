/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;

public interface IArmorMaterial {
    public int getDurability(EquipmentSlotType var1);

    public int getDamageReductionAmount(EquipmentSlotType var1);

    public int getEnchantability();

    public SoundEvent getSoundEvent();

    public Ingredient getRepairMaterial();

    public String getName();

    public float getToughness();

    public float getKnockbackResistance();
}

