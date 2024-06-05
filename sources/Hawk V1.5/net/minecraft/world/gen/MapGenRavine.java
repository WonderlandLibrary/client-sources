package net.minecraft.world.gen;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;

public class MapGenRavine extends MapGenBase {
   private static final String __OBFID = "CL_00000390";
   private float[] field_75046_d = new float[1024];

   protected void func_180707_a(long var1, int var3, int var4, ChunkPrimer var5, double var6, double var8, double var10, float var12, float var13, float var14, int var15, int var16, double var17) {
      Random var19 = new Random(var1);
      double var20 = (double)(var3 * 16 + 8);
      double var22 = (double)(var4 * 16 + 8);
      float var24 = 0.0F;
      float var25 = 0.0F;
      if (var16 <= 0) {
         int var26 = this.range * 16 - 16;
         var16 = var26 - var19.nextInt(var26 / 4);
      }

      boolean var60 = false;
      if (var15 == -1) {
         var15 = var16 / 2;
         var60 = true;
      }

      float var27 = 1.0F;

      for(int var28 = 0; var28 < 256; ++var28) {
         if (var28 == 0 || var19.nextInt(3) == 0) {
            var27 = 1.0F + var19.nextFloat() * var19.nextFloat() * 1.0F;
         }

         this.field_75046_d[var28] = var27 * var27;
      }

      for(; var15 < var16; ++var15) {
         double var61 = 1.5D + (double)(MathHelper.sin((float)var15 * 3.1415927F / (float)var16) * var12 * 1.0F);
         double var30 = var61 * var17;
         var61 *= (double)var19.nextFloat() * 0.25D + 0.75D;
         var30 *= (double)var19.nextFloat() * 0.25D + 0.75D;
         float var32 = MathHelper.cos(var14);
         float var33 = MathHelper.sin(var14);
         var6 += (double)(MathHelper.cos(var13) * var32);
         var8 += (double)var33;
         var10 += (double)(MathHelper.sin(var13) * var32);
         var14 *= 0.7F;
         var14 += var25 * 0.05F;
         var13 += var24 * 0.05F;
         var25 *= 0.8F;
         var24 *= 0.5F;
         var25 += (var19.nextFloat() - var19.nextFloat()) * var19.nextFloat() * 2.0F;
         var24 += (var19.nextFloat() - var19.nextFloat()) * var19.nextFloat() * 4.0F;
         if (var60 || var19.nextInt(4) != 0) {
            double var34 = var6 - var20;
            double var36 = var10 - var22;
            double var38 = (double)(var16 - var15);
            double var40 = (double)(var12 + 2.0F + 16.0F);
            if (var34 * var34 + var36 * var36 - var38 * var38 > var40 * var40) {
               return;
            }

            if (var6 >= var20 - 16.0D - var61 * 2.0D && var10 >= var22 - 16.0D - var61 * 2.0D && var6 <= var20 + 16.0D + var61 * 2.0D && var10 <= var22 + 16.0D + var61 * 2.0D) {
               int var42 = MathHelper.floor_double(var6 - var61) - var3 * 16 - 1;
               int var43 = MathHelper.floor_double(var6 + var61) - var3 * 16 + 1;
               int var44 = MathHelper.floor_double(var8 - var30) - 1;
               int var45 = MathHelper.floor_double(var8 + var30) + 1;
               int var46 = MathHelper.floor_double(var10 - var61) - var4 * 16 - 1;
               int var47 = MathHelper.floor_double(var10 + var61) - var4 * 16 + 1;
               if (var42 < 0) {
                  var42 = 0;
               }

               if (var43 > 16) {
                  var43 = 16;
               }

               if (var44 < 1) {
                  var44 = 1;
               }

               if (var45 > 248) {
                  var45 = 248;
               }

               if (var46 < 0) {
                  var46 = 0;
               }

               if (var47 > 16) {
                  var47 = 16;
               }

               boolean var48 = false;

               int var49;
               for(var49 = var42; !var48 && var49 < var43; ++var49) {
                  for(int var50 = var46; !var48 && var50 < var47; ++var50) {
                     for(int var51 = var45 + 1; !var48 && var51 >= var44 - 1; --var51) {
                        if (var51 >= 0 && var51 < 256) {
                           IBlockState var52 = var5.getBlockState(var49, var51, var50);
                           if (var52.getBlock() == Blocks.flowing_water || var52.getBlock() == Blocks.water) {
                              var48 = true;
                           }

                           if (var51 != var44 - 1 && var49 != var42 && var49 != var43 - 1 && var50 != var46 && var50 != var47 - 1) {
                              var51 = var44;
                           }
                        }
                     }
                  }
               }

               if (!var48) {
                  for(var49 = var42; var49 < var43; ++var49) {
                     double var62 = ((double)(var49 + var3 * 16) + 0.5D - var6) / var61;

                     for(int var63 = var46; var63 < var47; ++var63) {
                        double var53 = ((double)(var63 + var4 * 16) + 0.5D - var10) / var61;
                        boolean var55 = false;
                        if (var62 * var62 + var53 * var53 < 1.0D) {
                           for(int var56 = var45; var56 > var44; --var56) {
                              double var57 = ((double)(var56 - 1) + 0.5D - var8) / var30;
                              if ((var62 * var62 + var53 * var53) * (double)this.field_75046_d[var56 - 1] + var57 * var57 / 6.0D < 1.0D) {
                                 IBlockState var59 = var5.getBlockState(var49, var56, var63);
                                 if (var59.getBlock() == Blocks.grass) {
                                    var55 = true;
                                 }

                                 if (var59.getBlock() == Blocks.stone || var59.getBlock() == Blocks.dirt || var59.getBlock() == Blocks.grass) {
                                    if (var56 - 1 < 10) {
                                       var5.setBlockState(var49, var56, var63, Blocks.flowing_lava.getDefaultState());
                                    } else {
                                       var5.setBlockState(var49, var56, var63, Blocks.air.getDefaultState());
                                       if (var55 && var5.getBlockState(var49, var56 - 1, var63).getBlock() == Blocks.dirt) {
                                          var5.setBlockState(var49, var56 - 1, var63, this.worldObj.getBiomeGenForCoords(new BlockPos(var49 + var3 * 16, 0, var63 + var4 * 16)).topBlock);
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }

                  if (var60) {
                     break;
                  }
               }
            }
         }
      }

   }

   protected void func_180701_a(World var1, int var2, int var3, int var4, int var5, ChunkPrimer var6) {
      if (this.rand.nextInt(50) == 0) {
         double var7 = (double)(var2 * 16 + this.rand.nextInt(16));
         double var9 = (double)(this.rand.nextInt(this.rand.nextInt(40) + 8) + 20);
         double var11 = (double)(var3 * 16 + this.rand.nextInt(16));
         byte var13 = 1;

         for(int var14 = 0; var14 < var13; ++var14) {
            float var15 = this.rand.nextFloat() * 3.1415927F * 2.0F;
            float var16 = (this.rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
            float var17 = (this.rand.nextFloat() * 2.0F + this.rand.nextFloat()) * 2.0F;
            this.func_180707_a(this.rand.nextLong(), var4, var5, var6, var7, var9, var11, var17, var15, var16, 0, 0, 3.0D);
         }
      }

   }
}
