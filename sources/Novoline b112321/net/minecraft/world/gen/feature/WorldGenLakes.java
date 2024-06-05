package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenLakes extends WorldGenerator {
   private Block block;

   public WorldGenLakes(Block var1) {
      this.block = var1;
   }

   public boolean generate(World var1, Random var2, BlockPos var3) {
      for(var3 = var3.a(-8, 0, -8); var3.getY() > 5 && var1.isAirBlock(var3); var3 = var3.down()) {
         ;
      }

      if(var3.getY() <= 4) {
         return false;
      } else {
         var3 = var3.down(4);
         boolean[] var4 = new boolean[2048];
         int var5 = var2.nextInt(4) + 4;

         for(int var6 = 0; var6 < var5; ++var6) {
            double var7 = var2.nextDouble() * 6.0D + 3.0D;
            double var9 = var2.nextDouble() * 4.0D + 2.0D;
            double var11 = var2.nextDouble() * 6.0D + 3.0D;
            double var13 = var2.nextDouble() * (16.0D - var7 - 2.0D) + 1.0D + var7 / 2.0D;
            double var15 = var2.nextDouble() * (8.0D - var9 - 4.0D) + 2.0D + var9 / 2.0D;
            double var17 = var2.nextDouble() * (16.0D - var11 - 2.0D) + 1.0D + var11 / 2.0D;

            for(int var19 = 1; var19 < 15; ++var19) {
               for(int var20 = 1; var20 < 15; ++var20) {
                  for(int var21 = 1; var21 < 7; ++var21) {
                     double var22 = ((double)var19 - var13) / (var7 / 2.0D);
                     double var24 = ((double)var21 - var15) / (var9 / 2.0D);
                     double var26 = ((double)var20 - var17) / (var11 / 2.0D);
                     double var28 = var22 * var22 + var24 * var24 + var26 * var26;
                     if(var28 < 1.0D) {
                        var4[(var19 * 16 + var20) * 8 + var21] = true;
                     }
                  }
               }
            }
         }

         for(int var32 = 0; var32 < 16; ++var32) {
            for(int var37 = 0; var37 < 16; ++var37) {
               for(int var8 = 0; var8 < 8; ++var8) {
                  if(var4[(var32 * 16 + var37) * 8 + var8] || (var32 >= 15 || !var4[((var32 + 1) * 16 + var37) * 8 + var8]) && !var4[((var32 - 1) * 16 + var37) * 8 + var8] && (var37 >= 15 || !var4[(var32 * 16 + var37 + 1) * 8 + var8]) && !var4[(var32 * 16 + var37 - 1) * 8 + var8] && (var8 >= 7 || !var4[(var32 * 16 + var37) * 8 + var8 + 1]) && !var4[(var32 * 16 + var37) * 8 + var8 - 1]) {
                     boolean var48 = false;
                  } else {
                     boolean var10000 = true;
                  }

                  Material var10 = var1.getBlockState(var3.a(var32, var8, var37)).getBlock().getMaterial();
                  if(var8 >= 4 && var10.isLiquid()) {
                     return false;
                  }

                  if(var8 < 4 && !var10.isSolid() && var1.getBlockState(var3.a(var32, var8, var37)).getBlock() != this.block) {
                     return false;
                  }
               }
            }
         }

         for(int var33 = 0; var33 < 16; ++var33) {
            for(int var38 = 0; var38 < 16; ++var38) {
               for(int var42 = 0; var42 < 8; ++var42) {
                  if(var4[(var33 * 16 + var38) * 8 + var42]) {
                     var1.setBlockState(var3.a(var33, var42, var38), var42 >= 4?Blocks.air.getDefaultState():this.block.getDefaultState(), 2);
                  }
               }
            }
         }

         for(int var34 = 0; var34 < 16; ++var34) {
            for(int var39 = 0; var39 < 16; ++var39) {
               for(int var43 = 4; var43 < 8; ++var43) {
                  if(var4[(var34 * 16 + var39) * 8 + var43]) {
                     BlockPos var46 = var3.a(var34, var43 - 1, var39);
                     if(var1.getBlockState(var46).getBlock() == Blocks.dirt && var1.getLightFor(EnumSkyBlock.SKY, var3.a(var34, var43, var39)) > 0) {
                        BiomeGenBase var47 = var1.getBiomeGenForCoords(var46);
                        if(var47.topBlock.getBlock() == Blocks.mycelium) {
                           var1.setBlockState(var46, Blocks.mycelium.getDefaultState(), 2);
                        } else {
                           var1.setBlockState(var46, Blocks.grass.getDefaultState(), 2);
                        }
                     }
                  }
               }
            }
         }

         if(this.block.getMaterial() == Material.lava) {
            for(int var35 = 0; var35 < 16; ++var35) {
               for(int var40 = 0; var40 < 16; ++var40) {
                  for(int var44 = 0; var44 < 8; ++var44) {
                     if(var4[(var35 * 16 + var40) * 8 + var44] || (var35 >= 15 || !var4[((var35 + 1) * 16 + var40) * 8 + var44]) && !var4[((var35 - 1) * 16 + var40) * 8 + var44] && (var40 >= 15 || !var4[(var35 * 16 + var40 + 1) * 8 + var44]) && !var4[(var35 * 16 + var40 - 1) * 8 + var44] && (var44 >= 7 || !var4[(var35 * 16 + var40) * 8 + var44 + 1]) && !var4[(var35 * 16 + var40) * 8 + var44 - 1]) {
                        boolean var50 = false;
                     } else {
                        boolean var49 = true;
                     }

                     if((var44 < 4 || var2.nextInt(2) != 0) && var1.getBlockState(var3.a(var35, var44, var40)).getBlock().getMaterial().isSolid()) {
                        var1.setBlockState(var3.a(var35, var44, var40), Blocks.stone.getDefaultState(), 2);
                     }
                  }
               }
            }
         }

         if(this.block.getMaterial() == Material.water) {
            for(int var36 = 0; var36 < 16; ++var36) {
               for(int var41 = 0; var41 < 16; ++var41) {
                  byte var45 = 4;
                  if(var1.canBlockFreezeWater(var3.a(var36, var45, var41))) {
                     var1.setBlockState(var3.a(var36, var45, var41), Blocks.ice.getDefaultState(), 2);
                  }
               }
            }
         }

         return true;
      }
   }
}
