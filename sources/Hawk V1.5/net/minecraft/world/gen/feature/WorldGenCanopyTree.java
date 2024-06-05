package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class WorldGenCanopyTree extends WorldGenAbstractTree {
   private static final String __OBFID = "CL_00000430";

   private void func_150526_a(World var1, int var2, int var3, int var4) {
      Block var5 = var1.getBlockState(new BlockPos(var2, var3, var4)).getBlock();
      if (var5.getMaterial() == Material.air) {
         this.func_175905_a(var1, new BlockPos(var2, var3, var4), Blocks.leaves2, 1);
      }

   }

   public WorldGenCanopyTree(boolean var1) {
      super(var1);
   }

   public boolean generate(World var1, Random var2, BlockPos var3) {
      int var4 = var2.nextInt(3) + var2.nextInt(2) + 6;
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
            Block var18 = var1.getBlockState(var3.offsetDown()).getBlock();
            if ((var18 == Blocks.grass || var18 == Blocks.dirt) && var3.getY() < 256 - var4 - 1) {
               this.func_175921_a(var1, var3.offsetDown());
               this.func_175921_a(var1, var3.add(1, -1, 0));
               this.func_175921_a(var1, var3.add(1, -1, 1));
               this.func_175921_a(var1, var3.add(0, -1, 1));
               EnumFacing var19 = EnumFacing.Plane.HORIZONTAL.random(var2);
               var6 = var4 - var2.nextInt(4);
               var7 = 2 - var2.nextInt(3);
               int var10 = var3.getX();
               int var11 = var3.getZ();
               int var12 = 0;

               int var13;
               int var14;
               for(var13 = 0; var13 < var4; ++var13) {
                  var14 = var3.getY() + var13;
                  if (var13 >= var6 && var7 > 0) {
                     var10 += var19.getFrontOffsetX();
                     var11 += var19.getFrontOffsetZ();
                     --var7;
                  }

                  BlockPos var15 = new BlockPos(var10, var14, var11);
                  Material var16 = var1.getBlockState(var15).getBlock().getMaterial();
                  if (var16 == Material.air || var16 == Material.leaves) {
                     this.func_175905_a(var1, var15, Blocks.log2, BlockPlanks.EnumType.DARK_OAK.func_176839_a() - 4);
                     this.func_175905_a(var1, var15.offsetEast(), Blocks.log2, BlockPlanks.EnumType.DARK_OAK.func_176839_a() - 4);
                     this.func_175905_a(var1, var15.offsetSouth(), Blocks.log2, BlockPlanks.EnumType.DARK_OAK.func_176839_a() - 4);
                     this.func_175905_a(var1, var15.offsetEast().offsetSouth(), Blocks.log2, BlockPlanks.EnumType.DARK_OAK.func_176839_a() - 4);
                     var12 = var14;
                  }
               }

               for(var13 = -2; var13 <= 0; ++var13) {
                  for(var14 = -2; var14 <= 0; ++var14) {
                     byte var20 = -1;
                     this.func_150526_a(var1, var10 + var13, var12 + var20, var11 + var14);
                     this.func_150526_a(var1, 1 + var10 - var13, var12 + var20, var11 + var14);
                     this.func_150526_a(var1, var10 + var13, var12 + var20, 1 + var11 - var14);
                     this.func_150526_a(var1, 1 + var10 - var13, var12 + var20, 1 + var11 - var14);
                     if ((var13 > -2 || var14 > -1) && (var13 != -1 || var14 != -2)) {
                        byte var22 = 1;
                        this.func_150526_a(var1, var10 + var13, var12 + var22, var11 + var14);
                        this.func_150526_a(var1, 1 + var10 - var13, var12 + var22, var11 + var14);
                        this.func_150526_a(var1, var10 + var13, var12 + var22, 1 + var11 - var14);
                        this.func_150526_a(var1, 1 + var10 - var13, var12 + var22, 1 + var11 - var14);
                     }
                  }
               }

               if (var2.nextBoolean()) {
                  this.func_150526_a(var1, var10, var12 + 2, var11);
                  this.func_150526_a(var1, var10 + 1, var12 + 2, var11);
                  this.func_150526_a(var1, var10 + 1, var12 + 2, var11 + 1);
                  this.func_150526_a(var1, var10, var12 + 2, var11 + 1);
               }

               for(var13 = -3; var13 <= 4; ++var13) {
                  for(var14 = -3; var14 <= 4; ++var14) {
                     if ((var13 != -3 || var14 != -3) && (var13 != -3 || var14 != 4) && (var13 != 4 || var14 != -3) && (var13 != 4 || var14 != 4) && (Math.abs(var13) < 3 || Math.abs(var14) < 3)) {
                        this.func_150526_a(var1, var10 + var13, var12, var11 + var14);
                     }
                  }
               }

               for(var13 = -1; var13 <= 2; ++var13) {
                  for(var14 = -1; var14 <= 2; ++var14) {
                     if ((var13 < 0 || var13 > 1 || var14 < 0 || var14 > 1) && var2.nextInt(3) <= 0) {
                        int var21 = var2.nextInt(3) + 2;

                        int var23;
                        for(var23 = 0; var23 < var21; ++var23) {
                           this.func_175905_a(var1, new BlockPos(var3.getX() + var13, var12 - var23 - 1, var3.getZ() + var14), Blocks.log2, BlockPlanks.EnumType.DARK_OAK.func_176839_a() - 4);
                        }

                        int var17;
                        for(var23 = -1; var23 <= 1; ++var23) {
                           for(var17 = -1; var17 <= 1; ++var17) {
                              this.func_150526_a(var1, var10 + var13 + var23, var12, var11 + var14 + var17);
                           }
                        }

                        for(var23 = -2; var23 <= 2; ++var23) {
                           for(var17 = -2; var17 <= 2; ++var17) {
                              if (Math.abs(var23) != 2 || Math.abs(var17) != 2) {
                                 this.func_150526_a(var1, var10 + var13 + var23, var12 - 1, var11 + var14 + var17);
                              }
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
