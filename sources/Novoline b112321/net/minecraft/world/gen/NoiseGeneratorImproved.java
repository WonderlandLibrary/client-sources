package net.minecraft.world.gen;

import java.util.Random;
import net.minecraft.world.gen.NoiseGenerator;

public class NoiseGeneratorImproved extends NoiseGenerator {
   private int[] permutations;
   public double xCoord;
   public double yCoord;
   public double zCoord;
   private static final double[] field_152381_e = new double[]{1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, -1.0D, 0.0D};
   private static final double[] field_152382_f = new double[]{1.0D, 1.0D, -1.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D};
   private static final double[] field_152383_g = new double[]{0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 1.0D, -1.0D, -1.0D, 1.0D, 1.0D, -1.0D, -1.0D, 0.0D, 1.0D, 0.0D, -1.0D};
   private static final double[] field_152384_h = new double[]{1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, -1.0D, 0.0D};
   private static final double[] field_152385_i = new double[]{0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 1.0D, -1.0D, -1.0D, 1.0D, 1.0D, -1.0D, -1.0D, 0.0D, 1.0D, 0.0D, -1.0D};

   public NoiseGeneratorImproved() {
      this(new Random());
   }

   public NoiseGeneratorImproved(Random var1) {
      this.permutations = new int[512];
      this.xCoord = var1.nextDouble() * 256.0D;
      this.yCoord = var1.nextDouble() * 256.0D;
      this.zCoord = var1.nextDouble() * 256.0D;

      for(int var2 = 0; var2 < 256; this.permutations[var2] = var2++) {
         ;
      }

      for(int var5 = 0; var5 < 256; ++var5) {
         int var3 = var1.nextInt(256 - var5) + var5;
         int var4 = this.permutations[var5];
         this.permutations[var5] = this.permutations[var3];
         this.permutations[var3] = var4;
         this.permutations[var5 + 256] = this.permutations[var5];
      }

   }

   public final double lerp(double var1, double var3, double var5) {
      return var3 + var1 * (var5 - var3);
   }

   public final double func_76309_a(int var1, double var2, double var4) {
      int var6 = var1 & 15;
      return field_152384_h[var6] * var2 + field_152385_i[var6] * var4;
   }

   public final double grad(int var1, double var2, double var4, double var6) {
      int var8 = var1 & 15;
      return field_152381_e[var8] * var2 + field_152382_f[var8] * var4 + field_152383_g[var8] * var6;
   }

   public void populateNoiseArray(double[] var1, double var2, double var4, double var6, int var8, int var9, int var10, double var11, double var13, double var15, double var17) {
      if(var9 == 1) {
         int var19 = 0;
         int var20 = 0;
         int var21 = 0;
         int var22 = 0;
         double var23 = 0.0D;
         double var25 = 0.0D;
         int var27 = 0;
         double var28 = 1.0D / var17;

         for(int var30 = 0; var30 < var8; ++var30) {
            double var31 = var2 + (double)var30 * var11 + this.xCoord;
            int var33 = (int)var31;
            if(var31 < (double)var33) {
               --var33;
            }

            int var34 = var33 & 255;
            var31 = var31 - (double)var33;
            double var35 = var31 * var31 * var31 * (var31 * (var31 * 6.0D - 15.0D) + 10.0D);

            for(int var37 = 0; var37 < var10; ++var37) {
               double var38 = var6 + (double)var37 * var15 + this.zCoord;
               int var40 = (int)var38;
               if(var38 < (double)var40) {
                  --var40;
               }

               int var41 = var40 & 255;
               var38 = var38 - (double)var40;
               double var42 = var38 * var38 * var38 * (var38 * (var38 * 6.0D - 15.0D) + 10.0D);
               var19 = this.permutations[var34] + 0;
               var20 = this.permutations[var19] + var41;
               var21 = this.permutations[var34 + 1] + 0;
               var22 = this.permutations[var21] + var41;
               var23 = this.lerp(var35, this.func_76309_a(this.permutations[var20], var31, var38), this.grad(this.permutations[var22], var31 - 1.0D, 0.0D, var38));
               var25 = this.lerp(var35, this.grad(this.permutations[var20 + 1], var31, 0.0D, var38 - 1.0D), this.grad(this.permutations[var22 + 1], var31 - 1.0D, 0.0D, var38 - 1.0D));
               double var44 = this.lerp(var42, var23, var25);
               int var46 = var27++;
               var1[var46] += var44 * var28;
            }
         }
      } else {
         int var66 = 0;
         double var68 = 1.0D / var17;
         int var71 = -1;
         int var73 = 0;
         int var24 = 0;
         int var77 = 0;
         int var26 = 0;
         int var80 = 0;
         int var82 = 0;
         double var29 = 0.0D;
         double var85 = 0.0D;
         double var86 = 0.0D;
         double var87 = 0.0D;

         for(int var88 = 0; var88 < var8; ++var88) {
            double var90 = var2 + (double)var88 * var11 + this.xCoord;
            int var92 = (int)var90;
            if(var90 < (double)var92) {
               --var92;
            }

            int var93 = var92 & 255;
            var90 = var90 - (double)var92;
            double var94 = var90 * var90 * var90 * (var90 * (var90 * 6.0D - 15.0D) + 10.0D);

            for(int var95 = 0; var95 < var10; ++var95) {
               double var45 = var6 + (double)var95 * var15 + this.zCoord;
               int var47 = (int)var45;
               if(var45 < (double)var47) {
                  --var47;
               }

               int var48 = var47 & 255;
               var45 = var45 - (double)var47;
               double var49 = var45 * var45 * var45 * (var45 * (var45 * 6.0D - 15.0D) + 10.0D);

               for(int var51 = 0; var51 < var9; ++var51) {
                  double var52 = var4 + (double)var51 * var13 + this.yCoord;
                  int var54 = (int)var52;
                  if(var52 < (double)var54) {
                     --var54;
                  }

                  int var55 = var54 & 255;
                  var52 = var52 - (double)var54;
                  double var56 = var52 * var52 * var52 * (var52 * (var52 * 6.0D - 15.0D) + 10.0D);
                  if(var55 != var71) {
                     var71 = var55;
                     var73 = this.permutations[var93] + var55;
                     var24 = this.permutations[var73] + var48;
                     var77 = this.permutations[var73 + 1] + var48;
                     var26 = this.permutations[var93 + 1] + var55;
                     var80 = this.permutations[var26] + var48;
                     var82 = this.permutations[var26 + 1] + var48;
                     var29 = this.lerp(var94, this.grad(this.permutations[var24], var90, var52, var45), this.grad(this.permutations[var80], var90 - 1.0D, var52, var45));
                     var85 = this.lerp(var94, this.grad(this.permutations[var77], var90, var52 - 1.0D, var45), this.grad(this.permutations[var82], var90 - 1.0D, var52 - 1.0D, var45));
                     var86 = this.lerp(var94, this.grad(this.permutations[var24 + 1], var90, var52, var45 - 1.0D), this.grad(this.permutations[var80 + 1], var90 - 1.0D, var52, var45 - 1.0D));
                     var87 = this.lerp(var94, this.grad(this.permutations[var77 + 1], var90, var52 - 1.0D, var45 - 1.0D), this.grad(this.permutations[var82 + 1], var90 - 1.0D, var52 - 1.0D, var45 - 1.0D));
                  }

                  double var58 = this.lerp(var56, var29, var85);
                  double var60 = this.lerp(var56, var86, var87);
                  double var62 = this.lerp(var49, var58, var60);
                  int var64 = var66++;
                  var1[var64] += var62 * var68;
               }
            }
         }
      }

   }
}
