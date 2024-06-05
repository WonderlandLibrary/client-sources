package net.minecraft.item;

public class ItemBook extends Item {
   private static final String __OBFID = "CL_00001775";

   public int getItemEnchantability() {
      return 1;
   }

   public boolean isItemTool(ItemStack var1) {
      return var1.stackSize == 1;
   }
}
