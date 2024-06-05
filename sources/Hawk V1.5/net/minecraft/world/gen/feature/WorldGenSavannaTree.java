package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class WorldGenSavannaTree extends WorldGenAbstractTree {
   private static final String __OBFID = "CL_00000432";

   private void func_175924_b(World var1, BlockPos var2) {
      Material var3 = var1.getBlockState(var2).getBlock().getMaterial();
      if (var3 == Material.air || var3 == Material.leaves) {
         this.func_175905_a(var1, var2, Blocks.leaves2, 0);
      }

   }

   public WorldGenSavannaTree(boolean var1) {
      super(var1);
   }

   public boolean generate(World var1, Random var2, BlockPos var3) {
      int var4 = var2.nextInt(3) + var2.nextInt(3) + 5;
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
            Block var21 = var1.getBlockState(var3.offsetDown()).getBlock();
            if ((var21 == Blocks.grass || var21 == Blocks.dirt) && var3.getY() < 256 - var4 - 1) {
               this.func_175921_a(var1, var3.offsetDown());
               EnumFacing var22 = EnumFacing.Plane.HORIZONTAL.random(var2);
               var6 = var4 - var2.nextInt(4) - 1;
               var7 = 3 - var2.nextInt(3);
               int var10 = var3.getX();
               int var11 = var3.getZ();
               int var12 = 0;

               int var13;
               for(int var14 = 0; var14 < var4; ++var14) {
                  var13 = var3.getY() + var14;
                  if (var14 >= var6 && var7 > 0) {
                     var10 += var22.getFrontOffsetX();
                     var11 += var22.getFrontOffsetZ();
                     --var7;
                  }

                  BlockPos var15 = new BlockPos(var10, var13, var11);
                  Material var16 = var1.getBlockState(var15).getBlock().getMaterial();
                  if (var16 == Material.air || var16 == Material.leaves) {
                     this.func_175905_a(var1, var15, Blocks.log2, BlockPlanks.EnumType.ACACIA.func_176839_a() - 4);
                     var12 = var13;
                  }
               }

               BlockPos var23 = new BlockPos(var10, var12, var11);

               int var24;
               for(var13 = -3; var13 <= 3; ++var13) {
                  for(var24 = -3; var24 <= 3; ++var24) {
                     if (Math.abs(var13) != 3 || Math.abs(var24) != 3) {
                        this.func_175924_b(var1, var23.add(var13, 0, var24));
                     }
                  }
               }

               var23 = var23.offsetUp();

               for(var13 = -1; var13 <= 1; ++var13) {
                  for(var24 = -1; var24 <= 1; ++var24) {
                     this.func_175924_b(var1, var23.add(var13, 0, var24));
                  }
               }

               this.func_175924_b(var1, var23.offsetEast(2));
               this.func_175924_b(var1, var23.offsetWest(2));
               this.func_175924_b(var1, var23.offsetSouth(2));
               this.func_175924_b(var1, var23.offsetNorth(2));
               var10 = var3.getX();
               var11 = var3.getZ();
               EnumFacing var25 = EnumFacing.Plane.HORIZONTAL.random(var2);
               if (var25 != var22) {
                  var13 = var6 - var2.nextInt(2) - 1;
                  var24 = 1 + var2.nextInt(3);
                  var12 = 0;

                  int var17;
                  for(int var18 = var13; var18 < var4 && var24 > 0; --var24) {
                     if (var18 >= 1) {
                        var17 = var3.getY() + var18;
                        var10 += var25.getFrontOffsetX();
                        var11 += var25.getFrontOffsetZ();
                        BlockPos var19 = new BlockPos(var10, var17, var11);
                        Material var20 = var1.getBlockState(var19).getBlock().getMaterial();
                        if (var20 == Material.air || var20 == Material.leaves) {
                           this.func_175905_a(var1, var19, Blocks.log2, BlockPlanks.EnumType.ACACIA.func_176839_a() - 4);
                           var12 = var17;
                        }
                     }

                     ++var18;
                  }

                  if (var12 > 0) {
                     BlockPos var26 = new BlockPos(var10, var12, var11);

                     int var27;
                     for(var17 = -2; var17 <= 2; ++var17) {
                        for(var27 = -2; var27 <= 2; ++var27) {
                           if (Math.abs(var17) != 2 || Math.abs(var27) != 2) {
                              this.func_175924_b(var1, var26.add(var17, 0, var27));
                           }
                        }
                     }

                     var26 = var26.offsetUp();

                     for(var17 = -1; var17 <= 1; ++var17) {
                        for(var27 = -1; var27 <= 1; ++var27) {
                           this.func_175924_b(var1, var26.add(var17, 0, var27));
                        }
                     }
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
