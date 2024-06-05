package net.minecraft.client.renderer.entity.layers;

import net.minecraft.item.*;

static final class SwitchArmorMaterial
{
    static final int[] field_178747_a;
    
    static {
        field_178747_a = new int[ItemArmor.ArmorMaterial.values().length];
        try {
            SwitchArmorMaterial.field_178747_a[ItemArmor.ArmorMaterial.LEATHER.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            SwitchArmorMaterial.field_178747_a[ItemArmor.ArmorMaterial.CHAIN.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            SwitchArmorMaterial.field_178747_a[ItemArmor.ArmorMaterial.IRON.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            SwitchArmorMaterial.field_178747_a[ItemArmor.ArmorMaterial.GOLD.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            SwitchArmorMaterial.field_178747_a[ItemArmor.ArmorMaterial.DIAMOND.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
    }
}
