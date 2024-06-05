package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenBigMushroom extends WorldGenerator {
   private static final String __OBFID = "CL_00000415";
   private int mushroomType = -1;

   public WorldGenBigMushroom() {
      super(false);
   }

   public WorldGenBigMushroom(int var1) {
      super(true);
      this.mushroomType = var1;
   }

   public boolean generate(World var1, Random var2, BlockPos var3) {
      int var4 = var2.nextInt(2);
      if (this.mushroomType >= 0) {
         var4 = this.mushroomType;
      }

      int var5 = var2.nextInt(3) + 4;
      boolean var6 = true;
      if (var3.getY() >= 1 && var3.getY() + var5 + 1 < 256) {
         int var7;
         int var8;
         Block var11;
         for(int var9 = var3.getY(); var9 <= var3.getY() + 1 + var5; ++var9) {
            byte var10 = 3;
            if (var9 <= var3.getY() + 3) {
               var10 = 0;
            }

            for(var7 = var3.getX() - var10; var7 <= var3.getX() + var10 && var6; ++var7) {
               for(var8 = var3.getZ() - var10; var8 <= var3.getZ() + var10 && var6; ++var8) {
                  if (var9 >= 0 && var9 < 256) {
                     var11 = var1.getBlockState(new BlockPos(var7, var9, var8)).getBlock();
                     if (var11.getMaterial() != Material.air && var11.getMaterial() != Material.leaves) {
                        var6 = false;
                     }
                  } else {
                     var6 = false;
                  }
               }
            }
         }

         if (!var6) {
            return false;
         } else {
            Block var15 = var1.getBlockState(var3.offsetDown()).getBlock();
            if (var15 != Blocks.dirt && var15 != Blocks.grass && var15 != Blocks.mycelium) {
               return false;
            } else {
               int var16 = var3.getY() + var5;
               if (var4 == 1) {
                  var16 = var3.getY() + var5 - 3;
               }

               for(var7 = var16; var7 <= var3.getY() + var5; ++var7) {
                  var8 = 1;
                  if (var7 < var3.getY() + var5) {
                     ++var8;
                  }

                  if (var4 == 0) {
                     var8 = 3;
                  }

                  for(int var17 = var3.getX() - var8; var17 <= var3.getX() + var8; ++var17) {
                     for(int var12 = var3.getZ() - var8; var12 <= var3.getZ() + var8; ++var12) {
                        int var13 = 5;
                        if (var17 == var3.getX() - var8) {
                           --var13;
                        }

                        if (var17 == var3.getX() + var8) {
                           ++var13;
                        }

                        if (var12 == var3.getZ() - var8) {
                           var13 -= 3;
                        }

                        if (var12 == var3.getZ() + var8) {
                           var13 += 3;
                        }

                        if (var4 == 0 || var7 < var3.getY() + var5) {
                           if ((var17 == var3.getX() - var8 || var17 == var3.getX() + var8) && (var12 == var3.getZ() - var8 || var12 == var3.getZ() + var8)) {
                              continue;
                           }

                           if (var17 == var3.getX() - (var8 - 1) && var12 == var3.getZ() - var8) {
                              var13 = 1;
                           }

                           if (var17 == var3.getX() - var8 && var12 == var3.getZ() - (var8 - 1)) {
                              var13 = 1;
                           }

                           if (var17 == var3.getX() + (var8 - 1) && var12 == var3.getZ() - var8) {
                              var13 = 3;
                           }

                           if (var17 == var3.getX() + var8 && var12 == var3.getZ() - (var8 - 1)) {
                              var13 = 3;
                           }

                           if (var17 == var3.getX() - (var8 - 1) && var12 == var3.getZ() + var8) {
                              var13 = 7;
                           }

                           if (var17 == var3.getX() - var8 && var12 == var3.getZ() + (var8 - 1)) {
                              var13 = 7;
                           }

                           if (var17 == var3.getX() + (var8 - 1) && var12 == var3.getZ() + var8) {
                              var13 = 9;
                           }

                           if (var17 == var3.getX() + var8 && var12 == var3.getZ() + (var8 - 1)) {
                              var13 = 9;
                           }
                        }

                        if (var13 == 5 && var7 < var3.getY() + var5) {
                           var13 = 0;
                        }

                        if (var13 != 0 || var3.getY() >= var3.getY() + var5 - 1) {
                           BlockPos var14 = new BlockPos(var17, var7, var12);
                           if (!var1.getBlockState(var14).getBlock().isFullBlock()) {
                              this.func_175905_a(var1, var14, Block.getBlockById(Block.getIdFromBlock(Blocks.brown_mushroom_block) + var4), var13);
                           }
                        }
                     }
                  }
               }

               for(var7 = 0; var7 < var5; ++var7) {
                  var11 = var1.getBlockState(var3.offsetUp(var7)).getBlock();
                  if (!var11.isFullBlock()) {
                     this.func_175905_a(var1, var3.offsetUp(var7), Block.getBlockById(Block.getIdFromBlock(Blocks.brown_mushroom_block) + var4), 10);
                  }
               }

               return true;
            }
         }
      } else {
         return false;
      }
   }
}
