package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;

public class BlockBreakable extends Block {
   private boolean ignoreSimilarity;
   private static final String __OBFID = "CL_00000254";

   protected BlockBreakable(Material var1, boolean var2) {
      super(var1);
      this.ignoreSimilarity = var2;
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean shouldSideBeRendered(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      IBlockState var4 = var1.getBlockState(var2);
      Block var5 = var4.getBlock();
      if (this == Blocks.glass || this == Blocks.stained_glass) {
         if (var1.getBlockState(var2.offset(var3.getOpposite())) != var4) {
            return true;
         }

         if (var5 == this) {
            return false;
         }
      }

      return !this.ignoreSimilarity && var5 == this ? false : super.shouldSideBeRendered(var1, var2, var3);
   }
}
