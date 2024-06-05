package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockVine;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenSwamp extends WorldGenAbstractTree {
   private static final String __OBFID = "CL_00000436";

   private void func_175922_a(World var1, BlockPos var2, int var3) {
      this.func_175905_a(var1, var2, Blocks.vine, var3);
      int var4 = 4;

      for(var2 = var2.offsetDown(); var1.getBlockState(var2).getBlock().getMaterial() == Material.air && var4 > 0; --var4) {
         this.func_175905_a(var1, var2, Blocks.vine, var3);
         var2 = var2.offsetDown();
      }

   }

   public WorldGenSwamp() {
      super(false);
   }

   public boolean generate(World var1, Random var2, BlockPos var3) {
      int var4;
      for(var4 = var2.nextInt(4) + 5; var1.getBlockState(var3.offsetDown()).getBlock().getMaterial() == Material.water; var3 = var3.offsetDown()) {
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
               var9 = 3;
            }

            for(var6 = var3.getX() - var9; var6 <= var3.getX() + var9 && var5; ++var6) {
               for(var7 = var3.getZ() - var9; var7 <= var3.getZ() + var9 && var5; ++var7) {
                  if (var8 >= 0 && var8 < 256) {
                     Block var10 = var1.getBlockState(new BlockPos(var6, var8, var7)).getBlock();
                     if (var10.getMaterial() != Material.air && var10.getMaterial() != Material.leaves) {
                        if (var10 != Blocks.water && var10 != Blocks.flowing_water) {
                           var5 = false;
                        } else if (var8 > var3.getY()) {
                           var5 = false;
                        }
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
            Block var17 = var1.getBlockState(var3.offsetDown()).getBlock();
            if ((var17 == Blocks.grass || var17 == Blocks.dirt) && var3.getY() < 256 - var4 - 1) {
               this.func_175921_a(var1, var3.offsetDown());

               int var11;
               int var12;
               int var18;
               BlockPos var19;
               for(var11 = var3.getY() - 3 + var4; var11 <= var3.getY() + var4; ++var11) {
                  var6 = var11 - (var3.getY() + var4);
                  var7 = 2 - var6 / 2;

                  for(var12 = var3.getX() - var7; var12 <= var3.getX() + var7; ++var12) {
                     var18 = var12 - var3.getX();

                     for(int var13 = var3.getZ() - var7; var13 <= var3.getZ() + var7; ++var13) {
                        int var14 = var13 - var3.getZ();
                        if (Math.abs(var18) != var7 || Math.abs(var14) != var7 || var2.nextInt(2) != 0 && var6 != 0) {
                           var19 = new BlockPos(var12, var11, var13);
                           if (!var1.getBlockState(var19).getBlock().isFullBlock()) {
                              this.func_175906_a(var1, var19, Blocks.leaves);
                           }
                        }
                     }
                  }
               }

               for(var11 = 0; var11 < var4; ++var11) {
                  Block var20 = var1.getBlockState(var3.offsetUp(var11)).getBlock();
                  if (var20.getMaterial() == Material.air || var20.getMaterial() == Material.leaves || var20 == Blocks.flowing_water || var20 == Blocks.water) {
                     this.func_175906_a(var1, var3.offsetUp(var11), Blocks.log);
                  }
               }

               for(var11 = var3.getY() - 3 + var4; var11 <= var3.getY() + var4; ++var11) {
                  var6 = var11 - (var3.getY() + var4);
                  var7 = 2 - var6 / 2;

                  for(var12 = var3.getX() - var7; var12 <= var3.getX() + var7; ++var12) {
                     for(var18 = var3.getZ() - var7; var18 <= var3.getZ() + var7; ++var18) {
                        BlockPos var21 = new BlockPos(var12, var11, var18);
                        if (var1.getBlockState(var21).getBlock().getMaterial() == Material.leaves) {
                           BlockPos var22 = var21.offsetWest();
                           var19 = var21.offsetEast();
                           BlockPos var15 = var21.offsetNorth();
                           BlockPos var16 = var21.offsetSouth();
                           if (var2.nextInt(4) == 0 && var1.getBlockState(var22).getBlock().getMaterial() == Material.air) {
                              this.func_175922_a(var1, var22, BlockVine.field_176275_S);
                           }

                           if (var2.nextInt(4) == 0 && var1.getBlockState(var19).getBlock().getMaterial() == Material.air) {
                              this.func_175922_a(var1, var19, BlockVine.field_176271_T);
                           }

                           if (var2.nextInt(4) == 0 && var1.getBlockState(var15).getBlock().getMaterial() == Material.air) {
                              this.func_175922_a(var1, var15, BlockVine.field_176272_Q);
                           }

                           if (var2.nextInt(4) == 0 && var1.getBlockState(var16).getBlock().getMaterial() == Material.air) {
                              this.func_175922_a(var1, var16, BlockVine.field_176276_R);
                           }
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
