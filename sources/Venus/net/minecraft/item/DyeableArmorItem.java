/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.Item;

public class DyeableArmorItem
extends ArmorItem
implements IDyeableArmorItem {
    public DyeableArmorItem(IArmorMaterial iArmorMaterial, EquipmentSlotType equipmentSlotType, Item.Properties properties) {
        super(iArmorMaterial, equipmentSlotType, properties);
    }
}

