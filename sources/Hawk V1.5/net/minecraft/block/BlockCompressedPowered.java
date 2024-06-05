package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;

public class BlockCompressedPowered extends BlockCompressed {
   private static final String __OBFID = "CL_00000287";

   public BlockCompressedPowered(MapColor var1) {
      super(var1);
      this.setCreativeTab(CreativeTabs.tabRedstone);
   }

   public int isProvidingWeakPower(IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      return 15;
   }

   public boolean canProvidePower() {
      return true;
   }
}
