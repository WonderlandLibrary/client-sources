package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenForest extends WorldGenAbstractTree {
   private static final String __OBFID = "CL_00000401";
   private boolean field_150531_a;

   public WorldGenForest(boolean var1, boolean var2) {
      super(var1);
      this.field_150531_a = var2;
   }

   public boolean generate(World var1, Random var2, BlockPos var3) {
      int var4 = var2.nextInt(3) + 5;
      if (this.field_150531_a) {
         var4 += var2.nextInt(7);
      }

      boolean var5 = true;
      if (var3.getY() >= 1 && var3.getY() + var4 + 1 <= 256) {
         int var6;
         int var7;
         for(int var8 = var3.getY(); var8 <= var3.getY() + 1 + var4; ++var8) {
            byte var9 = 1;
            if (var8 == var3.getY()) {
               var9 = 0;
            }

            if (var8 >= var3.getY() + 1 + var4 - 2) {
               var9 = 2;
            }

            for(var6 = var3.getX() - var9; var6 <= var3.getX() + var9 && var5; ++var6) {
               for(var7 = var3.getZ() - var9; var7 <= var3.getZ() + var9 && var5; ++var7) {
                  if (var8 >= 0 && var8 < 256) {
                     if (!this.func_150523_a(var1.getBlockState(new BlockPos(var6, var8, var7)).getBlock())) {
                        var5 = false;
                     }
                  } else {
                     var5 = false;
                  }
               }
            }
         }

         if (!var5) {
            return false;
         } else {
            Block var16 = var1.getBlockState(var3.offsetDown()).getBlock();
            if ((var16 == Blocks.grass || var16 == Blocks.dirt || var16 == Blocks.farmland) && var3.getY() < 256 - var4 - 1) {
               this.func_175921_a(var1, var3.offsetDown());

               int var17;
               for(var17 = var3.getY() - 3 + var4; var17 <= var3.getY() + var4; ++var17) {
                  var6 = var17 - (var3.getY() + var4);
                  var7 = 1 - var6 / 2;

                  for(int var10 = var3.getX() - var7; var10 <= var3.getX() + var7; ++var10) {
                     int var11 = var10 - var3.getX();

                     for(int var12 = var3.getZ() - var7; var12 <= var3.getZ() + var7; ++var12) {
                        int var13 = var12 - var3.getZ();
                        if (Math.abs(var11) != var7 || Math.abs(var13) != var7 || var2.nextInt(2) != 0 && var6 != 0) {
                           BlockPos var14 = new BlockPos(var10, var17, var12);
                           Block var15 = var1.getBlockState(var14).getBlock();
                           if (var15.getMaterial() == Material.air || var15.getMaterial() == Material.leaves) {
                              this.func_175905_a(var1, var14, Blocks.leaves, BlockPlanks.EnumType.BIRCH.func_176839_a());
                           }
                        }
                     }
                  }
               }

               for(var17 = 0; var17 < var4; ++var17) {
                  Block var18 = var1.getBlockState(var3.offsetUp(var17)).getBlock();
                  if (var18.getMaterial() == Material.air || var18.getMaterial() == Material.leaves) {
                     this.func_175905_a(var1, var3.offsetUp(var17), Blocks.log, BlockPlanks.EnumType.BIRCH.func_176839_a());
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
}
