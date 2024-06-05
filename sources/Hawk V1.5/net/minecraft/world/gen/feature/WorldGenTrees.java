package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockVine;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class WorldGenTrees extends WorldGenAbstractTree {
   private final int minTreeHeight;
   private static final String __OBFID = "CL_00000438";
   private final int metaLeaves;
   private final boolean vinesGrow;
   private final int metaWood;

   public WorldGenTrees(boolean var1, int var2, int var3, int var4, boolean var5) {
      super(var1);
      this.minTreeHeight = var2;
      this.metaWood = var3;
      this.metaLeaves = var4;
      this.vinesGrow = var5;
   }

   private void func_175923_a(World var1, BlockPos var2, int var3) {
      this.func_175905_a(var1, var2, Blocks.vine, var3);
      int var4 = 4;

      for(var2 = var2.offsetDown(); var1.getBlockState(var2).getBlock().getMaterial() == Material.air && var4 > 0; --var4) {
         this.func_175905_a(var1, var2, Blocks.vine, var3);
         var2 = var2.offsetDown();
      }

   }

   public WorldGenTrees(boolean var1) {
      this(var1, 4, 0, 0, false);
   }

   public boolean generate(World var1, Random var2, BlockPos var3) {
      int var4 = var2.nextInt(3) + this.minTreeHeight;
      boolean var5 = true;
      if (var3.getY() >= 1 && var3.getY() + var4 + 1 <= 256) {
         byte var6;
         int var7;
         for(int var8 = var3.getY(); var8 <= var3.getY() + 1 + var4; ++var8) {
            var6 = 1;
            if (var8 == var3.getY()) {
               var6 = 0;
            }

            if (var8 >= var3.getY() + 1 + var4 - 2) {
               var6 = 2;
            }

            for(int var9 = var3.getX() - var6; var9 <= var3.getX() + var6 && var5; ++var9) {
               for(var7 = var3.getZ() - var6; var7 <= var3.getZ() + var6 && var5; ++var7) {
                  if (var8 >= 0 && var8 < 256) {
                     if (!this.func_150523_a(var1.getBlockState(new BlockPos(var9, var8, var7)).getBlock())) {
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
            Block var19 = var1.getBlockState(var3.offsetDown()).getBlock();
            if ((var19 == Blocks.grass || var19 == Blocks.dirt || var19 == Blocks.farmland) && var3.getY() < 256 - var4 - 1) {
               this.func_175921_a(var1, var3.offsetDown());
               var6 = 3;
               byte var20 = 0;

               int var10;
               int var11;
               int var12;
               int var13;
               BlockPos var14;
               for(var7 = var3.getY() - var6 + var4; var7 <= var3.getY() + var4; ++var7) {
                  var10 = var7 - (var3.getY() + var4);
                  var11 = var20 + 1 - var10 / 2;

                  for(var12 = var3.getX() - var11; var12 <= var3.getX() + var11; ++var12) {
                     var13 = var12 - var3.getX();

                     for(int var15 = var3.getZ() - var11; var15 <= var3.getZ() + var11; ++var15) {
                        int var16 = var15 - var3.getZ();
                        if (Math.abs(var13) != var11 || Math.abs(var16) != var11 || var2.nextInt(2) != 0 && var10 != 0) {
                           var14 = new BlockPos(var12, var7, var15);
                           Block var17 = var1.getBlockState(var14).getBlock();
                           if (var17.getMaterial() == Material.air || var17.getMaterial() == Material.leaves || var17.getMaterial() == Material.vine) {
                              this.func_175905_a(var1, var14, Blocks.leaves, this.metaLeaves);
                           }
                        }
                     }
                  }
               }

               for(var7 = 0; var7 < var4; ++var7) {
                  Block var21 = var1.getBlockState(var3.offsetUp(var7)).getBlock();
                  if (var21.getMaterial() == Material.air || var21.getMaterial() == Material.leaves || var21.getMaterial() == Material.vine) {
                     this.func_175905_a(var1, var3.offsetUp(var7), Blocks.log, this.metaWood);
                     if (this.vinesGrow && var7 > 0) {
                        if (var2.nextInt(3) > 0 && var1.isAirBlock(var3.add(-1, var7, 0))) {
                           this.func_175905_a(var1, var3.add(-1, var7, 0), Blocks.vine, BlockVine.field_176275_S);
                        }

                        if (var2.nextInt(3) > 0 && var1.isAirBlock(var3.add(1, var7, 0))) {
                           this.func_175905_a(var1, var3.add(1, var7, 0), Blocks.vine, BlockVine.field_176271_T);
                        }

                        if (var2.nextInt(3) > 0 && var1.isAirBlock(var3.add(0, var7, -1))) {
                           this.func_175905_a(var1, var3.add(0, var7, -1), Blocks.vine, BlockVine.field_176272_Q);
                        }

                        if (var2.nextInt(3) > 0 && var1.isAirBlock(var3.add(0, var7, 1))) {
                           this.func_175905_a(var1, var3.add(0, var7, 1), Blocks.vine, BlockVine.field_176276_R);
                        }
                     }
                  }
               }

               if (this.vinesGrow) {
                  for(var7 = var3.getY() - 3 + var4; var7 <= var3.getY() + var4; ++var7) {
                     var10 = var7 - (var3.getY() + var4);
                     var11 = 2 - var10 / 2;

                     for(var12 = var3.getX() - var11; var12 <= var3.getX() + var11; ++var12) {
                        for(var13 = var3.getZ() - var11; var13 <= var3.getZ() + var11; ++var13) {
                           BlockPos var22 = new BlockPos(var12, var7, var13);
                           if (var1.getBlockState(var22).getBlock().getMaterial() == Material.leaves) {
                              BlockPos var23 = var22.offsetWest();
                              var14 = var22.offsetEast();
                              BlockPos var25 = var22.offsetNorth();
                              BlockPos var18 = var22.offsetSouth();
                              if (var2.nextInt(4) == 0 && var1.getBlockState(var23).getBlock().getMaterial() == Material.air) {
                                 this.func_175923_a(var1, var23, BlockVine.field_176275_S);
                              }

                              if (var2.nextInt(4) == 0 && var1.getBlockState(var14).getBlock().getMaterial() == Material.air) {
                                 this.func_175923_a(var1, var14, BlockVine.field_176271_T);
                              }

                              if (var2.nextInt(4) == 0 && var1.getBlockState(var25).getBlock().getMaterial() == Material.air) {
                                 this.func_175923_a(var1, var25, BlockVine.field_176272_Q);
                              }

                              if (var2.nextInt(4) == 0 && var1.getBlockState(var18).getBlock().getMaterial() == Material.air) {
                                 this.func_175923_a(var1, var18, BlockVine.field_176276_R);
                              }
                           }
                        }
                     }
                  }

                  if (var2.nextInt(5) == 0 && var4 > 5) {
                     for(var7 = 0; var7 < 2; ++var7) {
                        for(var10 = 0; var10 < 4; ++var10) {
                           if (var2.nextInt(4 - var7) == 0) {
                              var11 = var2.nextInt(3);
                              EnumFacing var24 = EnumFacing.getHorizontal(var10).getOpposite();
                              this.func_175905_a(var1, var3.add(var24.getFrontOffsetX(), var4 - 5 + var7, var24.getFrontOffsetZ()), Blocks.cocoa, var11 << 2 | EnumFacing.getHorizontal(var10).getHorizontalIndex());
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
