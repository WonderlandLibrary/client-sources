package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockNewLeaf;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockPlanks$EnumType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BlockPos$MutableBlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class WorldGenSavannaTree extends WorldGenAbstractTree {
   private static final IBlockState field_181643_a = Blocks.log2.getDefaultState().withProperty(BlockNewLog.VARIANT, BlockPlanks$EnumType.ACACIA);
   private static final IBlockState field_181644_b = Blocks.leaves2.getDefaultState().withProperty(BlockNewLeaf.VARIANT, BlockPlanks$EnumType.ACACIA).withProperty(BlockLeaves.CHECK_DECAY, Boolean.FALSE);

   public WorldGenSavannaTree(boolean var1) {
      super(var1);
   }

   public boolean generate(World var1, Random var2, BlockPos var3) {
      int var4 = var2.nextInt(3) + var2.nextInt(3) + 5;
      boolean var5 = true;
      if(var3.getY() >= 1 && var3.getY() + var4 + 1 <= 256) {
         for(int var6 = var3.getY(); var6 <= var3.getY() + 1 + var4; ++var6) {
            byte var7 = 1;
            if(var6 == var3.getY()) {
               var7 = 0;
            }

            if(var6 >= var3.getY() + 1 + var4 - 2) {
               var7 = 2;
            }

            BlockPos$MutableBlockPos var8 = new BlockPos$MutableBlockPos();

            for(int var9 = var3.getX() - var7; var9 <= var3.getX() + var7; ++var9) {
               for(int var10 = var3.getZ() - var7; var10 <= var3.getZ() + var7; ++var10) {
                  if(var6 < 256) {
                     if(!this.func_150523_a(var1.getBlockState(var8.func_181079_c(var9, var6, var10)).getBlock())) {
                        var5 = false;
                     }
                  } else {
                     var5 = false;
                  }
               }
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private void func_181642_b(World var1, BlockPos var2) {
      this.setBlockAndNotifyAdequately(var1, var2, field_181643_a);
   }

   private void func_175924_b(World var1, BlockPos var2) {
      Material var3 = var1.getBlockState(var2).getBlock().getMaterial();
      if(var3 == Material.air || var3 == Material.leaves) {
         this.setBlockAndNotifyAdequately(var1, var2, field_181644_b);
      }

   }
}
