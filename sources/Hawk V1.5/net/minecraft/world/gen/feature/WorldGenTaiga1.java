package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenTaiga1 extends WorldGenAbstractTree {
   private static final String __OBFID = "CL_00000427";

   public boolean generate(World var1, Random var2, BlockPos var3) {
      int var4 = var2.nextInt(5) + 7;
      int var5 = var4 - var2.nextInt(2) - 3;
      int var6 = var4 - var5;
      int var7 = 1 + var2.nextInt(var6 + 1);
      boolean var8 = true;
      if (var3.getY() >= 1 && var3.getY() + var4 + 1 <= 256) {
         int var9;
         int var10;
         int var11;
         for(int var12 = var3.getY(); var12 <= var3.getY() + 1 + var4 && var8; ++var12) {
            boolean var13 = true;
            if (var12 - var3.getY() < var5) {
               var11 = 0;
            } else {
               var11 = var7;
            }

            for(var9 = var3.getX() - var11; var9 <= var3.getX() + var11 && var8; ++var9) {
               for(var10 = var3.getZ() - var11; var10 <= var3.getZ() + var11 && var8; ++var10) {
                  if (var12 >= 0 && var12 < 256) {
                     if (!this.func_150523_a(var1.getBlockState(new BlockPos(var9, var12, var10)).getBlock())) {
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
            Block var17 = var1.getBlockState(var3.offsetDown()).getBlock();
            if ((var17 == Blocks.grass || var17 == Blocks.dirt) && var3.getY() < 256 - var4 - 1) {
               this.func_175921_a(var1, var3.offsetDown());
               var11 = 0;

               for(var9 = var3.getY() + var4; var9 >= var3.getY() + var5; --var9) {
                  for(var10 = var3.getX() - var11; var10 <= var3.getX() + var11; ++var10) {
                     int var18 = var10 - var3.getX();

                     for(int var14 = var3.getZ() - var11; var14 <= var3.getZ() + var11; ++var14) {
                        int var15 = var14 - var3.getZ();
                        if (Math.abs(var18) != var11 || Math.abs(var15) != var11 || var11 <= 0) {
                           BlockPos var16 = new BlockPos(var10, var9, var14);
                           if (!var1.getBlockState(var16).getBlock().isFullBlock()) {
                              this.func_175905_a(var1, var16, Blocks.leaves, BlockPlanks.EnumType.SPRUCE.func_176839_a());
                           }
                        }
                     }
                  }

                  if (var11 >= 1 && var9 == var3.getY() + var5 + 1) {
                     --var11;
                  } else if (var11 < var7) {
                     ++var11;
                  }
               }

               for(var9 = 0; var9 < var4 - 1; ++var9) {
                  Block var19 = var1.getBlockState(var3.offsetUp(var9)).getBlock();
                  if (var19.getMaterial() == Material.air || var19.getMaterial() == Material.leaves) {
                     this.func_175905_a(var1, var3.offsetUp(var9), Blocks.log, BlockPlanks.EnumType.SPRUCE.func_176839_a());
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

   public WorldGenTaiga1() {
      super(false);
   }
}
