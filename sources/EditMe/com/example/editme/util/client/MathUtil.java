package com.example.editme.util.client;

import java.math.BigDecimal;
import java.math.RoundingMode;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public final class MathUtil {
   public static Vec3d mult(Vec3d var0, float var1) {
      return new Vec3d(var0.field_72450_a * (double)var1, var0.field_72448_b * (double)var1, var0.field_72449_c * (double)var1);
   }

   public static Vec3d div(Vec3d var0, Vec3d var1) {
      return new Vec3d(var0.field_72450_a / var1.field_72450_a, var0.field_72448_b / var1.field_72448_b, var0.field_72449_c / var1.field_72449_c);
   }

   public static double map(double var0, double var2, double var4, double var6, double var8) {
      var0 = (var0 - var2) / (var4 - var2);
      return var6 + var0 * (var8 - var6);
   }

   public static double roundDouble(double var0, int var2) {
      if (var2 < 0) {
         throw new IllegalArgumentException();
      } else {
         BigDecimal var3 = new BigDecimal(var0);
         var3 = var3.setScale(var2, RoundingMode.HALF_UP);
         return var3.doubleValue();
      }
   }

   public static double getDistance(Vec3d var0, double var1, double var3, double var5) {
      double var7 = var0.field_72450_a - var1;
      double var9 = var0.field_72448_b - var3;
      double var11 = var0.field_72449_c - var5;
      return (double)MathHelper.func_76133_a(var7 * var7 + var9 * var9 + var11 * var11);
   }

   public static Vec3d direction(float var0) {
      return new Vec3d(Math.cos(degToRad((double)(var0 + 90.0F))), 0.0D, Math.sin(degToRad((double)(var0 + 90.0F))));
   }

   public static double calculateAngle(double var0, double var2, double var4, double var6) {
      double var8 = Math.toDegrees(Math.atan2(var4 - var0, var6 - var2));
      var8 += Math.ceil(-var8 / 360.0D) * 360.0D;
      return var8;
   }

   public static float clamp(float var0, float var1, float var2) {
      if (var0 <= var1) {
         var0 = var1;
      }

      if (var0 >= var2) {
         var0 = var2;
      }

      return var0;
   }

   public static double round(double var0, int var2) {
      return var2 < 0 ? var0 : (new BigDecimal(var0)).setScale(var2, RoundingMode.HALF_UP).doubleValue();
   }

   public static Vec3d div(Vec3d var0, float var1) {
      return new Vec3d(var0.field_72450_a / (double)var1, var0.field_72448_b / (double)var1, var0.field_72449_c / (double)var1);
   }

   public static Vec3d interpolateEntity(Entity var0, float var1) {
      return new Vec3d(var0.field_70142_S + (var0.field_70165_t - var0.field_70142_S) * (double)var1, var0.field_70137_T + (var0.field_70163_u - var0.field_70137_T) * (double)var1, var0.field_70136_U + (var0.field_70161_v - var0.field_70136_U) * (double)var1);
   }

   public static float[] calcAngle(Vec3d var0, Vec3d var1) {
      double var2 = var1.field_72450_a - var0.field_72450_a;
      double var4 = (var1.field_72448_b - var0.field_72448_b) * -1.0D;
      double var6 = var1.field_72449_c - var0.field_72449_c;
      double var8 = (double)MathHelper.func_76133_a(var2 * var2 + var6 * var6);
      return new float[]{(float)MathHelper.func_76138_g(Math.toDegrees(Math.atan2(var6, var2)) - 90.0D), (float)MathHelper.func_76138_g(Math.toDegrees(Math.atan2(var4, var8)))};
   }

   public static float wrap(float var0) {
      var0 %= 360.0F;
      if (var0 >= 180.0F) {
         var0 -= 360.0F;
      }

      if (var0 < -180.0F) {
         var0 += 360.0F;
      }

      return var0;
   }

   public static Vec3d mult(Vec3d var0, Vec3d var1) {
      return new Vec3d(var0.field_72450_a * var1.field_72450_a, var0.field_72448_b * var1.field_72448_b, var0.field_72449_c * var1.field_72449_c);
   }

   public static double parabolic(double var0, double var2, double var4) {
      return var0 + (var2 - var0) / var4;
   }

   public static double[] directionSpeed(double var0) {
      Minecraft var2 = Minecraft.func_71410_x();
      float var3 = var2.field_71439_g.field_71158_b.field_192832_b;
      float var4 = var2.field_71439_g.field_71158_b.field_78902_a;
      float var5 = var2.field_71439_g.field_70126_B + (var2.field_71439_g.field_70177_z - var2.field_71439_g.field_70126_B) * var2.func_184121_ak();
      if (var3 != 0.0F) {
         if (var4 > 0.0F) {
            var5 += (float)(var3 > 0.0F ? -45 : 45);
         } else if (var4 < 0.0F) {
            var5 += (float)(var3 > 0.0F ? 45 : -45);
         }

         var4 = 0.0F;
         if (var3 > 0.0F) {
            var3 = 1.0F;
         } else if (var3 < 0.0F) {
            var3 = -1.0F;
         }
      }

      double var6 = Math.sin(Math.toRadians((double)(var5 + 90.0F)));
      double var8 = Math.cos(Math.toRadians((double)(var5 + 90.0F)));
      double var10 = (double)var3 * var0 * var8 + (double)var4 * var0 * var6;
      double var12 = (double)var3 * var0 * var6 - (double)var4 * var0 * var8;
      return new double[]{var10, var12};
   }

   public static double linear(double var0, double var2, double var4) {
      return var0 < var2 - var4 ? var0 + var4 : (var0 > var2 + var4 ? var0 - var4 : var2);
   }

   public static double[] calcIntersection(double[] var0, double[] var1) {
      double var2 = var0[3] - var0[1];
      double var4 = var0[0] - var0[2];
      double var6 = var2 * var0[0] + var4 * var0[1];
      double var8 = var1[3] - var1[1];
      double var10 = var1[0] - var1[2];
      double var12 = var8 * var1[0] + var10 * var1[1];
      double var14 = var2 * var10 - var8 * var4;
      return new double[]{(var10 * var6 - var4 * var12) / var14, (var2 * var12 - var8 * var6) / var14};
   }

   public static double degToRad(double var0) {
      return var0 * 0.01745329238474369D;
   }

   public static double[] directionSpeedNoForward(double var0) {
      Minecraft var2 = Minecraft.func_71410_x();
      float var3 = 1.0F;
      if (var2.field_71474_y.field_74370_x.func_151468_f() || var2.field_71474_y.field_74366_z.func_151468_f() || var2.field_71474_y.field_74368_y.func_151468_f() || var2.field_71474_y.field_74351_w.func_151468_f()) {
         var3 = var2.field_71439_g.field_71158_b.field_192832_b;
      }

      float var4 = var2.field_71439_g.field_71158_b.field_78902_a;
      float var5 = var2.field_71439_g.field_70126_B + (var2.field_71439_g.field_70177_z - var2.field_71439_g.field_70126_B) * var2.func_184121_ak();
      if (var3 != 0.0F) {
         if (var4 > 0.0F) {
            var5 += (float)(var3 > 0.0F ? -45 : 45);
         } else if (var4 < 0.0F) {
            var5 += (float)(var3 > 0.0F ? 45 : -45);
         }

         var4 = 0.0F;
         if (var3 > 0.0F) {
            var3 = 1.0F;
         } else if (var3 < 0.0F) {
            var3 = -1.0F;
         }
      }

      double var6 = Math.sin(Math.toRadians((double)(var5 + 90.0F)));
      double var8 = Math.cos(Math.toRadians((double)(var5 + 90.0F)));
      double var10 = (double)var3 * var0 * var8 + (double)var4 * var0 * var6;
      double var12 = (double)var3 * var0 * var6 - (double)var4 * var0 * var8;
      return new double[]{var10, var12};
   }

   public static double radToDeg(double var0) {
      return var0 * 57.295780181884766D;
   }

   public static float roundFloat(float var0, int var1) {
      if (var1 < 0) {
         throw new IllegalArgumentException();
      } else {
         BigDecimal var2 = new BigDecimal((double)var0);
         var2 = var2.setScale(var1, RoundingMode.HALF_UP);
         return var2.floatValue();
      }
   }
}
