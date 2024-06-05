package net.minecraft.item;

import net.minecraft.init.*;

public enum ToolMaterial
{
    WOOD("WOOD", 0, 0, 59, 2.0f, 0.0f, 15), 
    STONE("STONE", 1, 1, 131, 4.0f, 1.0f, 5), 
    IRON("IRON", 2, 2, 250, 6.0f, 2.0f, 14), 
    EMERALD("EMERALD", 3, 3, 1561, 8.0f, 3.0f, 10), 
    GOLD("GOLD", 4, 0, 32, 12.0f, 0.0f, 22);
    
    private static final ToolMaterial[] $VALUES;
    private final int harvestLevel;
    private final int maxUses;
    private final float efficiencyOnProperMaterial;
    private final float damageVsEntity;
    private final int enchantability;
    
    private ToolMaterial(final String p_i1874_1_, final int p_i1874_2_, final int harvestLevel, final int maxUses, final float efficiency, final float damageVsEntity, final int enchantability) {
        this.harvestLevel = harvestLevel;
        this.maxUses = maxUses;
        this.efficiencyOnProperMaterial = efficiency;
        this.damageVsEntity = damageVsEntity;
        this.enchantability = enchantability;
    }
    
    public int getMaxUses() {
        return this.maxUses;
    }
    
    public float getEfficiencyOnProperMaterial() {
        return this.efficiencyOnProperMaterial;
    }
    
    public float getDamageVsEntity() {
        return this.damageVsEntity;
    }
    
    public int getHarvestLevel() {
        return this.harvestLevel;
    }
    
    public int getEnchantability() {
        return this.enchantability;
    }
    
    public Item getBaseItemForRepair() {
        return (this == ToolMaterial.WOOD) ? Item.getItemFromBlock(Blocks.planks) : ((this == ToolMaterial.STONE) ? Item.getItemFromBlock(Blocks.cobblestone) : ((this == ToolMaterial.GOLD) ? Items.gold_ingot : ((this == ToolMaterial.IRON) ? Items.iron_ingot : ((this == ToolMaterial.EMERALD) ? Items.diamond : null))));
    }
    
    static {
        $VALUES = new ToolMaterial[] { ToolMaterial.WOOD, ToolMaterial.STONE, ToolMaterial.IRON, ToolMaterial.EMERALD, ToolMaterial.GOLD };
    }
}
