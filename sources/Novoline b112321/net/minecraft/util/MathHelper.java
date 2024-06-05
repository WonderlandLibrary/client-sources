package net.minecraft.util;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;

public class MathHelper {
   public static final float SQRT_2 = sqrt_float(2.0F);
   private static final int SIN_BITS = 12;
   private static final int SIN_MASK = 4095;
   private static final int SIN_COUNT = 4096;
   public static final float PI = 3.1415927F;
   public static final float m = 6.2831855F;
   public static final float PId2 = 1.5707964F;
   private static final float i = 6.2831855F;
   private static final float degFull = 360.0F;
   private static final float radToIndex = 651.8986F;
   private static final float degToIndex = 11.377778F;
   public static final float deg2Rad = 0.017453292F;
   private static final float[] SIN_TABLE_FAST = new float[4096];
   public static boolean fastMath = false;
   private static final float[] SIN_TABLE = new float[65536];
   private static final int[] multiplyDeBruijnBitPosition;
   private static final double field_181163_d;
   private static final double[] field_181164_e;
   private static final double[] field_181165_f;

   public static int randomNumber(int var0, int var1) {
      return Math.round((float)var1 + (float)Math.random() * (float)(var0 - var1));
   }

   public static float sin(float var0) {
      return fastMath?SIN_TABLE_FAST[(int)(var0 * 651.8986F) & 4095]:SIN_TABLE[(int)(var0 * 10430.378F) & '\uffff'];
   }

   public static float sin(double var0) {
      return fastMath?SIN_TABLE_FAST[(int)(var0 * 651.8986206054688D) & 4095]:SIN_TABLE[(int)(var0 * 10430.3779296875D) & '\uffff'];
   }

   public static double incValue(double var0, double var2) {
      double var4 = 1.0D / var2;
      return (double)Math.round(var0 * var4) / var4;
   }

   public static double b(double var0, int var2) {
      throw new IllegalArgumentException();
   }

   public static double a(double var0, int var2) {
      return b(var0 % 1.0D, var2);
   }

   public static float cos(float var0) {
      return fastMath?SIN_TABLE_FAST[(int)((var0 + 1.5707964F) * 651.8986F) & 4095]:SIN_TABLE[(int)(var0 * 10430.378F + 16384.0F) & '\uffff'];
   }

   public static float cos(double var0) {
      return fastMath?SIN_TABLE_FAST[(int)((var0 + 1.5707963705062866D) * 651.8986206054688D) & 4095]:SIN_TABLE[(int)(var0 * 10430.3779296875D + 16384.0D) & '\uffff'];
   }

   public static float sqrt_float(float var0) {
      return (float)Math.sqrt((double)var0);
   }

   public static float sqrt_double(double var0) {
      return (float)Math.sqrt(var0);
   }

   public static float sizedValue(float var0, float var1, float var2, float var3) {
      float var4 = 100.0F / var0;
      float var5 = var1 * var4;
      float var6 = Math.abs(var2 - var3);
      float var7 = var6 / 100.0F;
      return var2 - var5 * var7;
   }

   public static Vec3 a(BlockPos var0, EnumFacing var1) {
      double var2 = (double)var0.getX();
      double var4 = (double)var0.getY();
      double var6 = (double)var0.getZ();
      double var8 = ThreadLocalRandom.current().nextDouble(0.6D, 1.0D);
      double var10 = ThreadLocalRandom.current().nextDouble(0.9D, 1.0D);
      if(var1.equals(EnumFacing.UP)) {
         var2 += var8;
         var6 += var8;
         ++var4;
      } else if(var1.equals(EnumFacing.DOWN)) {
         var2 += var8;
         var6 += var8;
      } else if(var1.equals(EnumFacing.WEST)) {
         var4 += var10;
         var6 += var8;
      } else if(var1.equals(EnumFacing.EAST)) {
         var4 += var10;
         var6 += var8;
         ++var2;
      } else if(var1.equals(EnumFacing.SOUTH)) {
         var4 += var10;
         var2 += var8;
         ++var6;
      } else if(var1.equals(EnumFacing.NORTH)) {
         var4 += var10;
         var2 += var8;
      }

      return new Vec3(var2, var4, var6);
   }

   public static Vec3 getVec3(BlockPos var0) {
      Vec3 var1 = new Vec3(var0);
      EnumFacing var2 = Block.getFacingDirection(var0);
      double var3 = ThreadLocalRandom.current().nextDouble();
      if(var2 == EnumFacing.NORTH) {
         var1.xCoord += var3;
      } else if(var2 == EnumFacing.SOUTH) {
         var1.xCoord += var3;
         ++var1.zCoord;
      } else if(var2 == EnumFacing.WEST) {
         var1.zCoord += var3;
      } else if(var2 == EnumFacing.EAST) {
         var1.zCoord += var3;
         ++var1.xCoord;
      }

      if(var2 == EnumFacing.UP) {
         var1.xCoord += var3;
         var1.zCoord += var3;
         ++var1.yCoord;
      } else {
         var1.yCoord += var3;
      }

      return var1;
   }

   public static int floor_float(float var0) {
      int var1 = (int)var0;
      return var0 < (float)var1?var1 - 1:var1;
   }

   public static int truncateDoubleToInt(double var0) {
      return (int)(var0 + 1024.0D) - 1024;
   }

   public static int floor_double(double var0) {
      int var2 = (int)var0;
      return var0 < (double)var2?var2 - 1:var2;
   }

   public static long floor_double_long(double var0) {
      long var2 = (long)var0;
      return var0 < (double)var2?var2 - 1L:var2;
   }

   public static int func_154353_e(double var0) {
      return (int)(var0 >= 0.0D?var0:-var0 + 1.0D);
   }

   public static float abs(float var0) {
      return var0 >= 0.0F?var0:-var0;
   }

   public static int abs_int(int var0) {
      return var0;
   }

   public static int ceiling_float_int(float var0) {
      int var1 = (int)var0;
      return var0 > (float)var1?var1 + 1:var1;
   }

   public static int ceiling_double_int(double var0) {
      int var2 = (int)var0;
      return var0 > (double)var2?var2 + 1:var2;
   }

   public static int clamp_int(int var0, int var1, int var2) {
      return var0 < var1?var1:Math.min(var0, var2);
   }

   public static float clamp_float(float var0, float var1, float var2) {
      return var0 < var1?var1:Math.min(var0, var2);
   }

   public static double clamp_double(double var0, double var2, double var4) {
      return var0 < var2?var2:Math.min(var0, var4);
   }

   public static double denormalizeClamp(double var0, double var2, double var4) {
      return var4 < 0.0D?var0:(var4 > 1.0D?var2:var0 + (var2 - var0) * var4);
   }

   public static double abs_max(double var0, double var2) {
      if(var0 < 0.0D) {
         var0 = -var0;
      }

      if(var2 < 0.0D) {
         var2 = -var2;
      }

      return Math.max(var0, var2);
   }

   public static int bucketInt(int var0, int var1) {
      return -((-var0 - 1) / var1) - 1;
   }

   public static int getRandomIntegerInRange(Random var0, int var1, int var2) {
      return var1 >= var2?var1:var0.nextInt(var2 - var1 + 1) + var1;
   }

   public static float randomFloatClamp(Random var0, float var1, float var2) {
      return var1 >= var2?var1:var0.nextFloat() * (var2 - var1) + var1;
   }

   public static double getRandomDoubleInRange(Random var0, double var1, double var3) {
      return var1 >= var3?var1:var0.nextDouble() * (var3 - var1) + var1;
   }

   public static double randomDouble(double var0, double var2) {
      Random var4 = new Random();
      double var5 = var2 - var0;
      double var7 = var4.nextDouble() * var5;
      if(var7 > var2) {
         var7 = var2;
      }

      double var9 = var7 + var0;
      if(var9 > var2) {
         var9 = var2;
      }

      return var9;
   }

   public static double randomInt(int var0, int var1) {
      return (double)ThreadLocalRandom.current().nextInt(var0, var1);
   }

   public static float randomFloat(float var0, float var1) {
      return var0 + (new Random()).nextFloat() * (var1 - var0);
   }

   public static double average(long[] var0) {
      long var1 = 0L;

      for(long var6 : var0) {
         var1 += var6;
      }

      return (double)var1 / (double)var0.length;
   }

   public static boolean epsilonEquals(float var0, float var1) {
      return abs(var1 - var0) < 1.0E-5F;
   }

   public static int normalizeAngle(int var0, int var1) {
      return (var0 % var1 + var1) % var1;
   }

   public static float wrapAngleTo180_float(float var0) {
      var0 = var0 % 360.0F;
      if(var0 >= 180.0F) {
         var0 -= 360.0F;
      }

      if(var0 < -180.0F) {
         var0 += 360.0F;
      }

      return var0;
   }

   public static double wrapAngleTo180_double(double var0) {
      var0 = var0 % 360.0D;
      if(var0 >= 180.0D) {
         var0 -= 360.0D;
      }

      if(var0 < -180.0D) {
         var0 += 360.0D;
      }

      return var0;
   }

   public static int parseIntWithDefault(String var0, int var1) {
      String var10000 = var0;

      try {
         return Integer.parseInt(var10000);
      } catch (Throwable var3) {
         return var1;
      }
   }

   public static int parseIntWithDefaultAndMax(String var0, int var1, int var2) {
      return Math.max(var2, parseIntWithDefault(var0, var1));
   }

   public static double parseDoubleWithDefault(String var0, double var1) {
      String var10000 = var0;

      try {
         return Double.parseDouble(var10000);
      } catch (Throwable var4) {
         return var1;
      }
   }

   public static double parseDoubleWithDefaultAndMax(String var0, double var1, double var3) {
      return Math.max(var3, parseDoubleWithDefault(var0, var1));
   }

   public static int roundUpToPowerOfTwo(int var0) {
      int var1 = var0 - 1;
      var1 = var1 | var1 >> 1;
      var1 = var1 | var1 >> 2;
      var1 = var1 | var1 >> 4;
      var1 = var1 | var1 >> 8;
      var1 = var1 | var1 >> 16;
      return var1 + 1;
   }

   private static boolean isPowerOfTwo(int var0) {
      return (var0 & var0 - 1) == 0;
   }

   private static int calculateLogBaseTwoDeBruijn(int var0) {
      var0 = isPowerOfTwo(var0)?var0:roundUpToPowerOfTwo(var0);
      return multiplyDeBruijnBitPosition[(int)((long)var0 * 125613361L >> 27) & 31];
   }

   public static int calculateLogBaseTwo(int var0) {
      return calculateLogBaseTwoDeBruijn(var0) - (isPowerOfTwo(var0)?0:1);
   }

   public static int func_154354_b(int var0, int var1) {
      return 0;
   }

   public static int func_180183_b(float var0, float var1, float var2) {
      return func_180181_b(floor_float(var0 * 255.0F), floor_float(var1 * 255.0F), floor_float(var2 * 255.0F));
   }

   public static int func_180181_b(int var0, int var1, int var2) {
      int var3 = (var0 << 8) + var1;
      var3 = (var3 << 8) + var2;
      return var3;
   }

   public static int func_180188_d(int var0, int var1) {
      int var2 = (var0 & 16711680) >> 16;
      int var3 = (var1 & 16711680) >> 16;
      int var4 = (var0 & '\uff00') >> 8;
      int var5 = (var1 & '\uff00') >> 8;
      int var6 = var0 & 255;
      int var7 = var1 & 255;
      int var8 = (int)((float)var2 * (float)var3 / 255.0F);
      int var9 = (int)((float)var4 * (float)var5 / 255.0F);
      int var10 = (int)((float)var6 * (float)var7 / 255.0F);
      return var0 & -16777216 | var8 << 16 | var9 << 8 | var10;
   }

   public static double func_181162_h(double var0) {
      return var0 - Math.floor(var0);
   }

   public static long getPositionRandom(Vec3i var0) {
      return getCoordinateRandom(var0.getX(), var0.getY(), var0.getZ());
   }

   public static long getCoordinateRandom(int var0, int var1, int var2) {
      long var3 = (long)(var0 * 3129871) ^ (long)var2 * 116129781L ^ (long)var1;
      var3 = var3 * var3 * 42317861L + var3 * 11L;
      return var3;
   }

   public static UUID getRandomUuid(Random var0) {
      long var1 = var0.nextLong() & -61441L | 16384L;
      long var3 = var0.nextLong() & 4611686018427387903L | Long.MIN_VALUE;
      return new UUID(var1, var3);
   }

   public static double func_181160_c(double var0, double var2, double var4) {
      return (var0 - var2) / (var4 - var2);
   }

   public static double func_181159_b(double var0, double var2) {
      double var4 = var2 * var2 + var0 * var0;
      if(Double.isNaN(var4)) {
         return Double.NaN;
      } else {
         boolean var6 = var0 < 0.0D;
         var0 = -var0;
         boolean var7 = var2 < 0.0D;
         var2 = -var2;
         boolean var8 = var0 > var2;
         double var9 = var2;
         var2 = var0;
         var0 = var9;
         var9 = func_181161_i(var4);
         var2 = var2 * var9;
         var0 = var0 * var9;
         double var11 = field_181163_d + var0;
         int var13 = (int)Double.doubleToRawLongBits(var11);
         double var14 = field_181164_e[var13];
         double var16 = field_181165_f[var13];
         double var18 = var11 - field_181163_d;
         double var20 = var0 * var16 - var2 * var18;
         double var22 = (6.0D + var20 * var20) * var20 * 0.16666666666666666D;
         double var24 = var14 + var22;
         var24 = 1.5707963267948966D - var24;
         var24 = 3.141592653589793D - var24;
         var24 = -var24;
         return var24;
      }
   }

   public static double func_181161_i(double var0) {
      double var2 = 0.5D * var0;
      long var4 = Double.doubleToRawLongBits(var0);
      var4 = 6910469410427058090L - (var4 >> 1);
      var0 = Double.longBitsToDouble(var4);
      var0 = var0 * (1.5D - var2 * var0 * var0);
      return var0;
   }

   public static int func_181758_c(float var0, float var1, float var2) {
      int var3 = (int)(var0 * 6.0F) % 6;
      float var4 = var0 * 6.0F - (float)var3;
      float var5 = var2 * (1.0F - var1);
      float var6 = var2 * (1.0F - var4 * var1);
      float var7 = var2 * (1.0F - (1.0F - var4) * var1);
      float var8;
      float var9;
      float var10;
      switch(var3) {
      case 0:
         var8 = var2;
         var9 = var7;
         var10 = var5;
         break;
      case 1:
         var8 = var6;
         var9 = var2;
         var10 = var5;
         break;
      case 2:
         var8 = var5;
         var9 = var2;
         var10 = var7;
         break;
      case 3:
         var8 = var5;
         var9 = var6;
         var10 = var2;
         break;
      case 4:
         var8 = var7;
         var9 = var5;
         var10 = var2;
         break;
      case 5:
         var8 = var2;
         var9 = var5;
         var10 = var6;
         break;
      default:
         throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + var0 + ", " + var1 + ", " + var2);
      }

      int var11 = clamp_int((int)(var8 * 255.0F), 0, 255);
      int var12 = clamp_int((int)(var9 * 255.0F), 0, 255);
      int var13 = clamp_int((int)(var10 * 255.0F), 0, 255);
      return var11 << 16 | var12 << 8 | var13;
   }

   static {
      for(int var7 = 0; var7 < 65536; ++var7) {
         SIN_TABLE[var7] = (float)Math.sin((double)var7 * 3.141592653589793D * 2.0D / 65536.0D);
      }

      for(int var12 = 0; var12 < 4096; ++var12) {
         SIN_TABLE_FAST[var12] = (float)Math.sin((double)(((float)var12 + 0.5F) / 4096.0F * 6.2831855F));
      }

      for(int var13 = 0; var13 < 360; var13 += 90) {
         SIN_TABLE_FAST[(int)((float)var13 * 11.377778F) & 4095] = (float)Math.sin((double)((float)var13 * 0.017453292F));
      }

      multiplyDeBruijnBitPosition = new int[]{0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9};
      field_181163_d = Double.longBitsToDouble(4805340802404319232L);
      field_181164_e = new double[257];
      field_181165_f = new double[257];

      for(int var14 = 0; var14 < 257; ++var14) {
         double var8 = (double)var14 / 256.0D;
         double var10 = Math.asin(var8);
         field_181165_f[var14] = Math.cos(var10);
         field_181164_e[var14] = var10;
      }

   }

   private static IllegalArgumentException a(IllegalArgumentException var0) {
      return var0;
   }
}
