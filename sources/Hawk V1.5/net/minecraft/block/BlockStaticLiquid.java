package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockStaticLiquid extends BlockLiquid {
   private static final String __OBFID = "CL_00000315";

   protected BlockStaticLiquid(Material var1) {
      super(var1);
      this.setTickRandomly(false);
      if (var1 == Material.lava) {
         this.setTickRandomly(true);
      }

   }

   protected boolean isSurroundingBlockFlammable(World var1, BlockPos var2) {
      EnumFacing[] var3 = EnumFacing.values();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         EnumFacing var6 = var3[var5];
         if (this.getCanBlockBurn(var1, var2.offset(var6))) {
            return true;
         }
      }

      return false;
   }

   private void updateLiquid(World var1, BlockPos var2, IBlockState var3) {
      BlockDynamicLiquid var4 = getDynamicLiquidForMaterial(this.blockMaterial);
      var1.setBlockState(var2, var4.getDefaultState().withProperty(LEVEL, var3.getValue(LEVEL)), 2);
      var1.scheduleUpdate(var2, var4, this.tickRate(var1));
   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if (!this.func_176365_e(var1, var2, var3)) {
         this.updateLiquid(var1, var2, var3);
      }

   }

   private boolean getCanBlockBurn(World var1, BlockPos var2) {
      return var1.getBlockState(var2).getBlock().getMaterial().getCanBurn();
   }

   public void updateTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (this.blockMaterial == Material.lava && var1.getGameRules().getGameRuleBooleanValue("doFireTick")) {
         int var5 = var4.nextInt(3);
         if (var5 > 0) {
            BlockPos var6 = var2;

            for(int var7 = 0; var7 < var5; ++var7) {
               var6 = var6.add(var4.nextInt(3) - 1, 1, var4.nextInt(3) - 1);
               Block var8 = var1.getBlockState(var6).getBlock();
               if (var8.blockMaterial == Material.air) {
                  if (this.isSurroundingBlockFlammable(var1, var6)) {
                     var1.setBlockState(var6, Blocks.fire.getDefaultState());
                     return;
                  }
               } else if (var8.blockMaterial.blocksMovement()) {
                  return;
               }
            }
         } else {
            for(int var9 = 0; var9 < 3; ++var9) {
               BlockPos var10 = var2.add(var4.nextInt(3) - 1, 0, var4.nextInt(3) - 1);
               if (var1.isAirBlock(var10.offsetUp()) && this.getCanBlockBurn(var1, var10)) {
                  var1.setBlockState(var10.offsetUp(), Blocks.fire.getDefaultState());
               }
            }
         }
      }

   }
}
