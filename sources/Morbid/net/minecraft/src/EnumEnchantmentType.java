package net.minecraft.src;

public enum EnumEnchantmentType
{
    all("all", 0), 
    armor("armor", 1), 
    armor_feet("armor_feet", 2), 
    armor_legs("armor_legs", 3), 
    armor_torso("armor_torso", 4), 
    armor_head("armor_head", 5), 
    weapon("weapon", 6), 
    digger("digger", 7), 
    bow("bow", 8);
    
    private EnumEnchantmentType(final String s, final int n) {
    }
    
    public boolean canEnchantItem(final Item par1Item) {
        if (this == EnumEnchantmentType.all) {
            return true;
        }
        if (!(par1Item instanceof ItemArmor)) {
            return (par1Item instanceof ItemSword) ? (this == EnumEnchantmentType.weapon) : ((par1Item instanceof ItemTool) ? (this == EnumEnchantmentType.digger) : (par1Item instanceof ItemBow && this == EnumEnchantmentType.bow));
        }
        if (this == EnumEnchantmentType.armor) {
            return true;
        }
        final ItemArmor var2 = (ItemArmor)par1Item;
        return (var2.armorType == 0) ? (this == EnumEnchantmentType.armor_head) : ((var2.armorType == 2) ? (this == EnumEnchantmentType.armor_legs) : ((var2.armorType == 1) ? (this == EnumEnchantmentType.armor_torso) : (var2.armorType == 3 && this == EnumEnchantmentType.armor_feet)));
    }
}
