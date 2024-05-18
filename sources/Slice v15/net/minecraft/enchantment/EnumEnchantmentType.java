package net.minecraft.enchantment;

import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;





public enum EnumEnchantmentType
{
  ALL("ALL", 0), 
  ARMOR("ARMOR", 1), 
  ARMOR_FEET("ARMOR_FEET", 2), 
  ARMOR_LEGS("ARMOR_LEGS", 3), 
  ARMOR_TORSO("ARMOR_TORSO", 4), 
  ARMOR_HEAD("ARMOR_HEAD", 5), 
  WEAPON("WEAPON", 6), 
  DIGGER("DIGGER", 7), 
  FISHING_ROD("FISHING_ROD", 8), 
  BREAKABLE("BREAKABLE", 9), 
  BOW("BOW", 10);
  
  private static final EnumEnchantmentType[] $VALUES = { ALL, ARMOR, ARMOR_FEET, ARMOR_LEGS, ARMOR_TORSO, ARMOR_HEAD, WEAPON, DIGGER, FISHING_ROD, BREAKABLE, BOW };
  
  private static final String __OBFID = "CL_00000106";
  

  private EnumEnchantmentType(String p_i1927_1_, int p_i1927_2_) {}
  

  public boolean canEnchantItem(Item p_77557_1_)
  {
    if (this == ALL)
    {
      return true;
    }
    if ((this == BREAKABLE) && (p_77557_1_.isDamageable()))
    {
      return true;
    }
    if ((p_77557_1_ instanceof ItemArmor))
    {
      if (this == ARMOR)
      {
        return true;
      }
      

      ItemArmor var2 = (ItemArmor)p_77557_1_;
      return this == ARMOR_HEAD;
    }
    


    return this == WEAPON;
  }
}
