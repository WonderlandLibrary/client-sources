package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public abstract class WorldGenHugeTrees extends WorldGenAbstractTree {
   protected final int baseHeight;
   protected final IBlockState woodMetadata;
   protected final IBlockState leavesMetadata;
   protected int extraRandomHeight;

   public WorldGenHugeTrees(boolean var1, int var2, int var3, IBlockState var4, IBlockState var5) {
      super(var1);
      this.baseHeight = var2;
      this.extraRandomHeight = var3;
      this.woodMetadata = var4;
      this.leavesMetadata = var5;
   }

   protected int func_150533_a(Random var1) {
      int var2 = var1.nextInt(3) + this.baseHeight;
      if(this.extraRandomHeight > 1) {
         var2 += var1.nextInt(this.extraRandomHeight);
      }

      return var2;
   }

   private boolean func_175926_c(World var1, BlockPos var2, int var3) {
      boolean var4 = true;
      if(var2.getY() >= 1 && var2.getY() + var3 + 1 <= 256) {
         for(int var5 = 0; var5 <= 1 + var3; ++var5) {
            byte var6 = 2;
            var6 = 1;

            for(int var7 = -var6; var7 <= var6; ++var7) {
               for(int var8 = -var6; var8 <= var6; ++var8) {
                  if(var2.getY() + var5 < 0 || var2.getY() + var5 >= 256 || !this.func_150523_a(var1.getBlockState(var2.a(var7, var5, var8)).getBlock())) {
                     var4 = false;
                  }
               }
            }
         }

         return var4;
      } else {
         return false;
      }
   }

   private boolean func_175927_a(BlockPos var1, World var2) {
      BlockPos var3 = var1.down();
      Block var4 = var2.getBlockState(var3).getBlock();
      if((var4 == Blocks.grass || var4 == Blocks.dirt) && var1.getY() >= 2) {
         this.func_175921_a(var2, var3);
         this.func_175921_a(var2, var3.east());
         this.func_175921_a(var2, var3.south());
         this.func_175921_a(var2, var3.south().east());
         return true;
      } else {
         return false;
      }
   }

   protected boolean func_175929_a(World var1, Random var2, BlockPos var3, int var4) {
      return this.func_175926_c(var1, var3, var4) && this.func_175927_a(var3, var1);
   }

   protected void func_175925_a(World var1, BlockPos var2, int var3) {
      int var4 = var3 * var3;

      for(int var5 = -var3; var5 <= var3 + 1; ++var5) {
         for(int var6 = -var3; var6 <= var3 + 1; ++var6) {
            int var7 = var5 - 1;
            int var8 = var6 - 1;
            if(var5 * var5 + var6 * var6 <= var4 || var7 * var7 + var8 * var8 <= var4 || var5 * var5 + var8 * var8 <= var4 || var7 * var7 + var6 * var6 <= var4) {
               BlockPos var9 = var2.a(var5, 0, var6);
               Material var10 = var1.getBlockState(var9).getBlock().getMaterial();
               if(var10 == Material.air || var10 == Material.leaves) {
                  this.setBlockAndNotifyAdequately(var1, var9, this.leavesMetadata);
               }
            }
         }
      }

   }

   protected void func_175928_b(World var1, BlockPos var2, int var3) {
      int var4 = var3 * var3;

      for(int var5 = -var3; var5 <= var3; ++var5) {
         for(int var6 = -var3; var6 <= var3; ++var6) {
            if(var5 * var5 + var6 * var6 <= var4) {
               BlockPos var7 = var2.a(var5, 0, var6);
               Material var8 = var1.getBlockState(var7).getBlock().getMaterial();
               if(var8 == Material.air || var8 == Material.leaves) {
                  this.setBlockAndNotifyAdequately(var1, var7, this.leavesMetadata);
               }
            }
         }
      }

   }
}
