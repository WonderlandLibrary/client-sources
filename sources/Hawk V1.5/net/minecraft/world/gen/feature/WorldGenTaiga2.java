package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenTaiga2 extends WorldGenAbstractTree {
   private static final String __OBFID = "CL_00000435";

   public boolean generate(World var1, Random var2, BlockPos var3) {
      int var4 = var2.nextInt(4) + 6;
      int var5 = 1 + var2.nextInt(2);
      int var6 = var4 - var5;
      int var7 = 2 + var2.nextInt(2);
      boolean var8 = true;
      if (var3.getY() >= 1 && var3.getY() + var4 + 1 <= 256) {
         int var9;
         int var10;
         int var13;
         for(int var11 = var3.getY(); var11 <= var3.getY() + 1 + var4 && var8; ++var11) {
            boolean var12 = true;
            if (var11 - var3.getY() < var5) {
               var10 = 0;
            } else {
               var10 = var7;
            }

            for(var9 = var3.getX() - var10; var9 <= var3.getX() + var10 && var8; ++var9) {
               for(var13 = var3.getZ() - var10; var13 <= var3.getZ() + var10 && var8; ++var13) {
                  if (var11 >= 0 && var11 < 256) {
                     Block var14 = var1.getBlockState(new BlockPos(var9, var11, var13)).getBlock();
                     if (var14.getMaterial() != Material.air && var14.getMaterial() != Material.leaves) {
                        var8 = false;
                     }
                  } else {
                     var8 = false;
                  }
               }
            }
         }

         if (!var8) {
            return false;
         } else {
            Block var20 = var1.getBlockState(var3.offsetDown()).getBlock();
            if ((var20 == Blocks.grass || var20 == Blocks.dirt || var20 == Blocks.farmland) && var3.getY() < 256 - var4 - 1) {
               this.func_175921_a(var1, var3.offsetDown());
               var10 = var2.nextInt(2);
               var9 = 1;
               byte var21 = 0;

               int var22;
               for(var22 = 0; var22 <= var6; ++var22) {
                  var13 = var3.getY() + var4 - var22;

                  for(int var15 = var3.getX() - var10; var15 <= var3.getX() + var10; ++var15) {
                     int var16 = var15 - var3.getX();

                     for(int var17 = var3.getZ() - var10; var17 <= var3.getZ() + var10; ++var17) {
                        int var18 = var17 - var3.getZ();
                        if (Math.abs(var16) != var10 || Math.abs(var18) != var10 || var10 <= 0) {
                           BlockPos var19 = new BlockPos(var15, var13, var17);
                           if (!var1.getBlockState(var19).getBlock().isFullBlock()) {
                              this.func_175905_a(var1, var19, Blocks.leaves, BlockPlanks.EnumType.SPRUCE.func_176839_a());
                           }
                        }
                     }
                  }

                  if (var10 >= var9) {
                     var10 = var21;
                     var21 = 1;
                     ++var9;
                     if (var9 > var7) {
                        var9 = var7;
                     }
                  } else {
                     ++var10;
                  }
               }

               var22 = var2.nextInt(3);

               for(var13 = 0; var13 < var4 - var22; ++var13) {
                  Block var23 = var1.getBlockState(var3.offsetUp(var13)).getBlock();
                  if (var23.getMaterial() == Material.air || var23.getMaterial() == Material.leaves) {
                     this.func_175905_a(var1, var3.offsetUp(var13), Blocks.log, BlockPlanks.EnumType.SPRUCE.func_176839_a());
                  }
               }

               return true;
            } else {
               return false;
            }
         }
      } else {
         return false;
      }
   }

   public WorldGenTaiga2(boolean var1) {
      super(var1);
   }
}
