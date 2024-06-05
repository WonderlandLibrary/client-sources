package net.minecraft.enchantment;

import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;

public enum EnumEnchantmentType {
   private static final EnumEnchantmentType[] ENUM$VALUES = new EnumEnchantmentType[]{ALL, ARMOR, ARMOR_FEET, ARMOR_LEGS, ARMOR_TORSO, ARMOR_HEAD, WEAPON, DIGGER, FISHING_ROD, BREAKABLE, BOW};
   DIGGER("DIGGER", 7),
   ARMOR_TORSO("ARMOR_TORSO", 4),
   ARMOR_HEAD("ARMOR_HEAD", 5);

   private static final EnumEnchantmentType[] $VALUES = new EnumEnchantmentType[]{ALL, ARMOR, ARMOR_FEET, ARMOR_LEGS, ARMOR_TORSO, ARMOR_HEAD, WEAPON, DIGGER, FISHING_ROD, BREAKABLE, BOW};
   private static final String __OBFID = "CL_00000106";
   ARMOR_LEGS("ARMOR_LEGS", 3),
   ALL("ALL", 0),
   ARMOR_FEET("ARMOR_FEET", 2),
   BREAKABLE("BREAKABLE", 9),
   BOW("BOW", 10),
   FISHING_ROD("FISHING_ROD", 8),
   ARMOR("ARMOR", 1),
   WEAPON("WEAPON", 6);

   private EnumEnchantmentType(String var3, int var4) {
   }

   public boolean canEnchantItem(Item var1) {
      if (this == ALL) {
         return true;
      } else if (this == BREAKABLE && var1.isDamageable()) {
         return true;
      } else if (var1 instanceof ItemArmor) {
         if (this == ARMOR) {
            return true;
         } else {
            ItemArmor var2 = (ItemArmor)var1;
            return var2.armorType == 0 ? this == ARMOR_HEAD : (var2.armorType == 2 ? this == ARMOR_LEGS : (var2.armorType == 1 ? this == ARMOR_TORSO : (var2.armorType == 3 ? this == ARMOR_FEET : false)));
         }
      } else {
         return var1 instanceof ItemSword ? this == WEAPON : (var1 instanceof ItemTool ? this == DIGGER : (var1 instanceof ItemBow ? this == BOW : (var1 instanceof ItemFishingRod ? this == FISHING_ROD : false)));
      }
   }
}
