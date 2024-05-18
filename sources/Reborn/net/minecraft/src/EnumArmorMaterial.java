package net.minecraft.src;

public enum EnumArmorMaterial
{
    CLOTH("CLOTH", 0, 5, new int[] { 1, 3, 2, 1 }, 15), 
    CHAIN("CHAIN", 1, 15, new int[] { 2, 5, 4, 1 }, 12), 
    IRON("IRON", 2, 15, new int[] { 2, 6, 5, 2 }, 9), 
    GOLD("GOLD", 3, 7, new int[] { 2, 5, 3, 1 }, 25), 
    DIAMOND("DIAMOND", 4, 33, new int[] { 3, 8, 6, 3 }, 10);
    
    private int maxDamageFactor;
    private int[] damageReductionAmountArray;
    private int enchantability;
    
    private EnumArmorMaterial(final String s, final int n, final int par3, final int[] par4ArrayOfInteger, final int par5) {
        this.maxDamageFactor = par3;
        this.damageReductionAmountArray = par4ArrayOfInteger;
        this.enchantability = par5;
    }
    
    public int getDurability(final int par1) {
        return ItemArmor.getMaxDamageArray()[par1] * this.maxDamageFactor;
    }
    
    public int getDamageReductionAmount(final int par1) {
        return this.damageReductionAmountArray[par1];
    }
    
    public int getEnchantability() {
        return this.enchantability;
    }
    
    public int getArmorCraftingMaterial() {
        return (this == EnumArmorMaterial.CLOTH) ? Item.leather.itemID : ((this == EnumArmorMaterial.CHAIN) ? Item.ingotIron.itemID : ((this == EnumArmorMaterial.GOLD) ? Item.ingotGold.itemID : ((this == EnumArmorMaterial.IRON) ? Item.ingotIron.itemID : ((this == EnumArmorMaterial.DIAMOND) ? Item.diamond.itemID : 0))));
    }
}
