package net.minecraft.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

final class CreativeTabs$12 extends CreativeTabs {
   CreativeTabs$12(int var1, String var2) {
      super(var1, var2);
   }

   public Item getTabIconItem() {
      return Item.getItemFromBlock(Blocks.chest);
   }
}
