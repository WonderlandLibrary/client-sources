package net.minecraft.item;

import net.minecraft.init.*;

public enum ArmorMaterial
{
    LEATHER("LEATHER", 0, "leather", 5, new int[] { 1, 3, 2, 1 }, 15), 
    CHAIN("CHAIN", 1, "chainmail", 15, new int[] { 2, 5, 4, 1 }, 12), 
    IRON("IRON", 2, "iron", 15, new int[] { 2, 6, 5, 2 }, 9), 
    GOLD("GOLD", 3, "gold", 7, new int[] { 2, 5, 3, 1 }, 25), 
    DIAMOND("DIAMOND", 4, "diamond", 33, new int[] { 3, 8, 6, 3 }, 10);
    
    private static final ArmorMaterial[] $VALUES;
    private final String field_179243_f;
    private final int maxDamageFactor;
    private final int[] damageReductionAmountArray;
    private final int enchantability;
    
    private ArmorMaterial(final String p_i45789_1_, final int p_i45789_2_, final String p_i45789_3_, final int p_i45789_4_, final int[] p_i45789_5_, final int p_i45789_6_) {
        this.field_179243_f = p_i45789_3_;
        this.maxDamageFactor = p_i45789_4_;
        this.damageReductionAmountArray = p_i45789_5_;
        this.enchantability = p_i45789_6_;
    }
    
    public int getDurability(final int p_78046_1_) {
        return ItemArmor.access$000()[p_78046_1_] * this.maxDamageFactor;
    }
    
    public int getDamageReductionAmount(final int p_78044_1_) {
        return this.damageReductionAmountArray[p_78044_1_];
    }
    
    public int getEnchantability() {
        return this.enchantability;
    }
    
    public Item getBaseItemForRepair() {
        return (this == ArmorMaterial.LEATHER) ? Items.leather : ((this == ArmorMaterial.CHAIN) ? Items.iron_ingot : ((this == ArmorMaterial.GOLD) ? Items.gold_ingot : ((this == ArmorMaterial.IRON) ? Items.iron_ingot : ((this == ArmorMaterial.DIAMOND) ? Items.diamond : null))));
    }
    
    public String func_179242_c() {
        return this.field_179243_f;
    }
    
    static {
        $VALUES = new ArmorMaterial[] { ArmorMaterial.LEATHER, ArmorMaterial.CHAIN, ArmorMaterial.IRON, ArmorMaterial.GOLD, ArmorMaterial.DIAMOND };
    }
}
