package net.minecraft.src;

public enum EnumToolMaterial
{
    WOOD("WOOD", 0, 0, 59, 2.0f, 0, 15), 
    STONE("STONE", 1, 1, 131, 4.0f, 1, 5), 
    IRON("IRON", 2, 2, 250, 6.0f, 2, 14), 
    EMERALD("EMERALD", 3, 3, 1561, 8.0f, 3, 10), 
    GOLD("GOLD", 4, 0, 32, 12.0f, 0, 22);
    
    private final int harvestLevel;
    private final int maxUses;
    private final float efficiencyOnProperMaterial;
    private final int damageVsEntity;
    private final int enchantability;
    
    private EnumToolMaterial(final String s, final int n, final int par3, final int par4, final float par5, final int par6, final int par7) {
        this.harvestLevel = par3;
        this.maxUses = par4;
        this.efficiencyOnProperMaterial = par5;
        this.damageVsEntity = par6;
        this.enchantability = par7;
    }
    
    public int getMaxUses() {
        return this.maxUses;
    }
    
    public float getEfficiencyOnProperMaterial() {
        return this.efficiencyOnProperMaterial;
    }
    
    public int getDamageVsEntity() {
        return this.damageVsEntity;
    }
    
    public int getHarvestLevel() {
        return this.harvestLevel;
    }
    
    public int getEnchantability() {
        return this.enchantability;
    }
    
    public int getToolCraftingMaterial() {
        return (this == EnumToolMaterial.WOOD) ? Block.planks.blockID : ((this == EnumToolMaterial.STONE) ? Block.cobblestone.blockID : ((this == EnumToolMaterial.GOLD) ? Item.ingotGold.itemID : ((this == EnumToolMaterial.IRON) ? Item.ingotIron.itemID : ((this == EnumToolMaterial.EMERALD) ? Item.diamond.itemID : 0))));
    }
}
