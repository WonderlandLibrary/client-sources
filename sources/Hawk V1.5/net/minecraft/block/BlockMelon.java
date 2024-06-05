package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class BlockMelon extends Block {
   private static final String __OBFID = "CL_00000267";

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return Items.melon;
   }

   public int quantityDropped(Random var1) {
      return 3 + var1.nextInt(5);
   }

   protected BlockMelon() {
      super(Material.gourd);
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public int quantityDroppedWithBonus(int var1, Random var2) {
      return Math.min(9, this.quantityDropped(var2) + var2.nextInt(1 + var1));
   }
}
