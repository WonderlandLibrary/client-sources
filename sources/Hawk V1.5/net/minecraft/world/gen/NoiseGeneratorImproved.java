package net.minecraft.world.gen;

import java.util.Random;

public class NoiseGeneratorImproved extends NoiseGenerator {
   private static final double[] field_152381_e = new double[]{1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, -1.0D, 0.0D};
   private static final double[] field_152385_i = new double[]{0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 1.0D, -1.0D, -1.0D, 1.0D, 1.0D, -1.0D, -1.0D, 0.0D, 1.0D, 0.0D, -1.0D};
   private int[] permutations;
   private static final String __OBFID = "CL_00000534";
   public double xCoord;
   private static final double[] field_152383_g = new double[]{0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 1.0D, -1.0D, -1.0D, 1.0D, 1.0D, -1.0D, -1.0D, 0.0D, 1.0D, 0.0D, -1.0D};
   private static final double[] field_152382_f = new double[]{1.0D, 1.0D, -1.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D};
   public double yCoord;
   public double zCoord;
   private static final double[] field_152384_h = new double[]{1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, -1.0D, 0.0D};

   public NoiseGeneratorImproved() {
      this(new Random());
   }

   public final double grad(int var1, double var2, double var4, double var6) {
      int var8 = var1 & 15;
      return field_152381_e[var8] * var2 + field_152382_f[var8] * var4 + field_152383_g[var8] * var6;
   }

   public final double func_76309_a(int var1, double var2, double var4) {
      int var6 = var1 & 15;
      return field_152384_h[var6] * var2 + field_152385_i[var6] * var4;
   }

   public void populateNoiseArray(double[] var1, double var2, double var4, double var6, int var8, int var9, int var10, double var11, double var13, double var15, double var17) {
      int var19;
      int var20;
      double var21;
      double var23;
      int var25;
      double var26;
      int var28;
      int var29;
      double var30;
      int var32;
      int var33;
      boolean var36;
      boolean var37;
      double var42;
      int var46;
      if (var9 == 1) {
         boolean var34 = false;
         boolean var35 = false;
         var36 = false;
         var37 = false;
         double var38 = 0.0D;
         double var40 = 0.0D;
         var32 = 0;
         var42 = 1.0D / var17;

         for(int var44 = 0; var44 < var8; ++var44) {
            var21 = var2 + (double)var44 * var11 + this.xCoord;
            int var45 = (int)var21;
            if (var21 < (double)var45) {
               --var45;
            }

            var46 = var45 & 255;
            var21 -= (double)var45;
            var23 = var21 * var21 * var21 * (var21 * (var21 * 6.0D - 15.0D) + 10.0D);

            for(var25 = 0; var25 < var10; ++var25) {
               var26 = var6 + (double)var25 * var15 + this.zCoord;
               var28 = (int)var26;
               if (var26 < (double)var28) {
                  --var28;
               }

               var29 = var28 & 255;
               var26 -= (double)var28;
               var30 = var26 * var26 * var26 * (var26 * (var26 * 6.0D - 15.0D) + 10.0D);
               var19 = this.permutations[var46];
               int var47 = this.permutations[var19] + var29;
               int var48 = this.permutations[var46 + 1];
               var20 = this.permutations[var48] + var29;
               var38 = this.lerp(var23, this.func_76309_a(this.permutations[var47], var21, var26), this.grad(this.permutations[var20], var21 - 1.0D, 0.0D, var26));
               var40 = this.lerp(var23, this.grad(this.permutations[var47 + 1], var21, 0.0D, var26 - 1.0D), this.grad(this.permutations[var20 + 1], var21 - 1.0D, 0.0D, var26 - 1.0D));
               double var49 = this.lerp(var30, var38, var40);
               var33 = var32++;
               var1[var33] += var49 * var42;
            }
         }
      } else {
         var19 = 0;
         double var66 = 1.0D / var17;
         var20 = -1;
         var36 = false;
         var37 = false;
         boolean var67 = false;
         boolean var39 = false;
         boolean var68 = false;
         boolean var41 = false;
         var42 = 0.0D;
         var21 = 0.0D;
         double var69 = 0.0D;
         var23 = 0.0D;

         for(var25 = 0; var25 < var8; ++var25) {
            var26 = var2 + (double)var25 * var11 + this.xCoord;
            var28 = (int)var26;
            if (var26 < (double)var28) {
               --var28;
            }

            var29 = var28 & 255;
            var26 -= (double)var28;
            var30 = var26 * var26 * var26 * (var26 * (var26 * 6.0D - 15.0D) + 10.0D);

            for(var46 = 0; var46 < var10; ++var46) {
               double var70 = var6 + (double)var46 * var15 + this.zCoord;
               int var71 = (int)var70;
               if (var70 < (double)var71) {
                  --var71;
               }

               int var50 = var71 & 255;
               var70 -= (double)var71;
               double var51 = var70 * var70 * var70 * (var70 * (var70 * 6.0D - 15.0D) + 10.0D);

               for(int var53 = 0; var53 < var9; ++var53) {
                  double var54 = var4 + (double)var53 * var13 + this.yCoord;
                  int var56 = (int)var54;
                  if (var54 < (double)var56) {
                     --var56;
                  }

                  int var57 = var56 & 255;
                  var54 -= (double)var56;
                  double var58 = var54 * var54 * var54 * (var54 * (var54 * 6.0D - 15.0D) + 10.0D);
                  if (var53 == 0 || var57 != var20) {
                     var20 = var57;
                     int var60 = this.permutations[var29] + var57;
                     int var61 = this.permutations[var60] + var50;
                     int var62 = this.permutations[var60 + 1] + var50;
                     int var63 = this.permutations[var29 + 1] + var57;
                     var32 = this.permutations[var63] + var50;
                     int var64 = this.permutations[var63 + 1] + var50;
                     var42 = this.lerp(var30, this.grad(this.permutations[var61], var26, var54, var70), this.grad(this.permutations[var32], var26 - 1.0D, var54, var70));
                     var21 = this.lerp(var30, this.grad(this.permutations[var62], var26, var54 - 1.0D, var70), this.grad(this.permutations[var64], var26 - 1.0D, var54 - 1.0D, var70));
                     var69 = this.lerp(var30, this.grad(this.permutations[var61 + 1], var26, var54, var70 - 1.0D), this.grad(this.permutations[var32 + 1], var26 - 1.0D, var54, var70 - 1.0D));
                     var23 = this.lerp(var30, this.grad(this.permutations[var62 + 1], var26, var54 - 1.0D, var70 - 1.0D), this.grad(this.permutations[var64 + 1], var26 - 1.0D, var54 - 1.0D, var70 - 1.0D));
                  }

                  double var72 = this.lerp(var58, var42, var21);
                  double var73 = this.lerp(var58, var69, var23);
                  double var74 = this.lerp(var51, var72, var73);
                  var33 = var19++;
                  var1[var33] += var74 * var66;
               }
            }
         }
      }

   }

   public NoiseGeneratorImproved(Random var1) {
      this.permutations = new int[512];
      this.xCoord = var1.nextDouble() * 256.0D;
      this.yCoord = var1.nextDouble() * 256.0D;
      this.zCoord = var1.nextDouble() * 256.0D;

      int var2;
      for(var2 = 0; var2 < 256; this.permutations[var2] = var2++) {
      }

      for(var2 = 0; var2 < 256; ++var2) {
         int var3 = var1.nextInt(256 - var2) + var2;
         int var4 = this.permutations[var2];
         this.permutations[var2] = this.permutations[var3];
         this.permutations[var3] = var4;
         this.permutations[var2 + 256] = this.permutations[var2];
      }

   }

   public final double lerp(double var1, double var3, double var5) {
      return var3 + var1 * (var5 - var3);
   }
}
