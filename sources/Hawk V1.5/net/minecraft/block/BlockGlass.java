package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.EnumWorldBlockLayer;

public class BlockGlass extends BlockBreakable {
   private static final String __OBFID = "CL_00000249";

   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT;
   }

   protected boolean canSilkHarvest() {
      return true;
   }

   public BlockGlass(Material var1, boolean var2) {
      super(var1, var2);
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public boolean isFullCube() {
      return false;
   }

   public int quantityDropped(Random var1) {
      return 0;
   }
}
