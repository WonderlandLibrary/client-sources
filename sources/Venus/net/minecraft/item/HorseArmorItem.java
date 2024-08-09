/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class HorseArmorItem
extends Item {
    private final int armorValue;
    private final String field_219979_b;

    public HorseArmorItem(int n, String string, Item.Properties properties) {
        super(properties);
        this.armorValue = n;
        this.field_219979_b = "textures/entity/horse/armor/horse_armor_" + string + ".png";
    }

    public ResourceLocation getArmorTexture() {
        return new ResourceLocation(this.field_219979_b);
    }

    public int getArmorValue() {
        return this.armorValue;
    }
}

