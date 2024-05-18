/*     */ package net.minecraft.util;
/*     */ 
/*     */ import java.util.Random;
/*     */ import java.util.UUID;
/*     */ 
/*     */ public class MathHelper
/*     */ {
/*   8 */   public static final float SQRT_2 = sqrt_float(2.0F);
/*     */   private static final int SIN_BITS = 12;
/*     */   private static final int SIN_MASK = 4095;
/*     */   private static final int SIN_COUNT = 4096;
/*     */   public static final float PI = 3.1415927F;
/*     */   public static final float PI2 = 6.2831855F;
/*     */   public static final float PId2 = 1.5707964F;
/*     */   private static final float radFull = 6.2831855F;
/*     */   private static final float degFull = 360.0F;
/*     */   private static final float radToIndex = 651.8986F;
/*     */   private static final float degToIndex = 11.377778F;
/*     */   public static final float deg2Rad = 0.017453292F;
/*  20 */   private static final float[] SIN_TABLE_FAST = new float[4096];
/*     */ 
/*     */   
/*     */   public static boolean fastMath = false;
/*     */ 
/*     */   
/*  26 */   private static final float[] SIN_TABLE = new float[65536];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float sin(float p_76126_0_) {
/*  46 */     return fastMath ? SIN_TABLE_FAST[(int)(p_76126_0_ * 651.8986F) & 0xFFF] : SIN_TABLE[(int)(p_76126_0_ * 10430.378F) & 0xFFFF];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float cos(float value) {
/*  54 */     return fastMath ? SIN_TABLE_FAST[(int)((value + 1.5707964F) * 651.8986F) & 0xFFF] : SIN_TABLE[(int)(value * 10430.378F + 16384.0F) & 0xFFFF];
/*     */   }
/*     */ 
/*     */   
/*     */   public static float sqrt_float(float value) {
/*  59 */     return (float)Math.sqrt(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public static float sqrt_double(double value) {
/*  64 */     return (float)Math.sqrt(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int floor_float(float value) {
/*  72 */     int i = (int)value;
/*  73 */     return (value < i) ? (i - 1) : i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int truncateDoubleToInt(double value) {
/*  81 */     return (int)(value + 1024.0D) - 1024;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int floor_double(double value) {
/*  89 */     int i = (int)value;
/*  90 */     return (value < i) ? (i - 1) : i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long floor_double_long(double value) {
/*  98 */     long i = (long)value;
/*  99 */     return (value < i) ? (i - 1L) : i;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int func_154353_e(double value) {
/* 104 */     return (int)((value >= 0.0D) ? value : (-value + 1.0D));
/*     */   }
/*     */ 
/*     */   
/*     */   public static float abs(float value) {
/* 109 */     return (value >= 0.0F) ? value : -value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int abs_int(int value) {
/* 117 */     return (value >= 0) ? value : -value;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int ceiling_float_int(float value) {
/* 122 */     int i = (int)value;
/* 123 */     return (value > i) ? (i + 1) : i;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int ceiling_double_int(double value) {
/* 128 */     int i = (int)value;
/* 129 */     return (value > i) ? (i + 1) : i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int clamp_int(int num, int min, int max) {
/* 138 */     return (num < min) ? min : ((num > max) ? max : num);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float clamp_float(float num, float min, float max) {
/* 147 */     return (num < min) ? min : ((num > max) ? max : num);
/*     */   }
/*     */ 
/*     */   
/*     */   public static double clamp_double(double num, double min, double max) {
/* 152 */     return (num < min) ? min : ((num > max) ? max : num);
/*     */   }
/*     */ 
/*     */   
/*     */   public static double denormalizeClamp(double p_151238_0_, double p_151238_2_, double p_151238_4_) {
/* 157 */     return (p_151238_4_ < 0.0D) ? p_151238_0_ : ((p_151238_4_ > 1.0D) ? p_151238_2_ : (p_151238_0_ + (p_151238_2_ - p_151238_0_) * p_151238_4_));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double abs_max(double p_76132_0_, double p_76132_2_) {
/* 165 */     if (p_76132_0_ < 0.0D)
/*     */     {
/* 167 */       p_76132_0_ = -p_76132_0_;
/*     */     }
/*     */     
/* 170 */     if (p_76132_2_ < 0.0D)
/*     */     {
/* 172 */       p_76132_2_ = -p_76132_2_;
/*     */     }
/*     */     
/* 175 */     return (p_76132_0_ > p_76132_2_) ? p_76132_0_ : p_76132_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int bucketInt(int p_76137_0_, int p_76137_1_) {
/* 183 */     return (p_76137_0_ < 0) ? (-((-p_76137_0_ - 1) / p_76137_1_) - 1) : (p_76137_0_ / p_76137_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getRandomIntegerInRange(Random p_76136_0_, int p_76136_1_, int p_76136_2_) {
/* 188 */     return (p_76136_1_ >= p_76136_2_) ? p_76136_1_ : (p_76136_0_.nextInt(p_76136_2_ - p_76136_1_ + 1) + p_76136_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static float randomFloatClamp(Random p_151240_0_, float p_151240_1_, float p_151240_2_) {
/* 193 */     return (p_151240_1_ >= p_151240_2_) ? p_151240_1_ : (p_151240_0_.nextFloat() * (p_151240_2_ - p_151240_1_) + p_151240_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static double getRandomDoubleInRange(Random p_82716_0_, double p_82716_1_, double p_82716_3_) {
/* 198 */     return (p_82716_1_ >= p_82716_3_) ? p_82716_1_ : (p_82716_0_.nextDouble() * (p_82716_3_ - p_82716_1_) + p_82716_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static double average(long[] values) {
/* 203 */     long i = 0L; byte b; int j;
/*     */     long[] arrayOfLong;
/* 205 */     for (j = (arrayOfLong = values).length, b = 0; b < j; ) { long l = arrayOfLong[b];
/*     */       
/* 207 */       i += l;
/*     */       b++; }
/*     */     
/* 210 */     return i / values.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean epsilonEquals(float p_180185_0_, float p_180185_1_) {
/* 215 */     return (abs(p_180185_1_ - p_180185_0_) < 1.0E-5F);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int normalizeAngle(int p_180184_0_, int p_180184_1_) {
/* 220 */     return (p_180184_0_ % p_180184_1_ + p_180184_1_) % p_180184_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float wrapAngleTo180_float(float value) {
/* 228 */     value %= 360.0F;
/*     */     
/* 230 */     if (value >= 180.0F)
/*     */     {
/* 232 */       value -= 360.0F;
/*     */     }
/*     */     
/* 235 */     if (value < -180.0F)
/*     */     {
/* 237 */       value += 360.0F;
/*     */     }
/*     */     
/* 240 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double wrapAngleTo180_double(double value) {
/* 248 */     value %= 360.0D;
/*     */     
/* 250 */     if (value >= 180.0D)
/*     */     {
/* 252 */       value -= 360.0D;
/*     */     }
/*     */     
/* 255 */     if (value < -180.0D)
/*     */     {
/* 257 */       value += 360.0D;
/*     */     }
/*     */     
/* 260 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int parseIntWithDefault(String p_82715_0_, int p_82715_1_) {
/*     */     try {
/* 270 */       return Integer.parseInt(p_82715_0_);
/*     */     }
/* 272 */     catch (Throwable var3) {
/*     */       
/* 274 */       return p_82715_1_;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int parseIntWithDefaultAndMax(String p_82714_0_, int p_82714_1_, int p_82714_2_) {
/* 283 */     return Math.max(p_82714_2_, parseIntWithDefault(p_82714_0_, p_82714_1_));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double parseDoubleWithDefault(String p_82712_0_, double p_82712_1_) {
/*     */     try {
/* 293 */       return Double.parseDouble(p_82712_0_);
/*     */     }
/* 295 */     catch (Throwable var4) {
/*     */       
/* 297 */       return p_82712_1_;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static double parseDoubleWithDefaultAndMax(String p_82713_0_, double p_82713_1_, double p_82713_3_) {
/* 303 */     return Math.max(p_82713_3_, parseDoubleWithDefault(p_82713_0_, p_82713_1_));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int roundUpToPowerOfTwo(int value) {
/* 311 */     int i = value - 1;
/* 312 */     i |= i >> 1;
/* 313 */     i |= i >> 2;
/* 314 */     i |= i >> 4;
/* 315 */     i |= i >> 8;
/* 316 */     i |= i >> 16;
/* 317 */     return i + 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isPowerOfTwo(int value) {
/* 325 */     return (value != 0 && (value & value - 1) == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int calculateLogBaseTwoDeBruijn(int value) {
/* 335 */     value = isPowerOfTwo(value) ? value : roundUpToPowerOfTwo(value);
/* 336 */     return multiplyDeBruijnBitPosition[(int)(value * 125613361L >> 27L) & 0x1F];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int calculateLogBaseTwo(int value) {
/* 345 */     return calculateLogBaseTwoDeBruijn(value) - (isPowerOfTwo(value) ? 0 : 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int func_154354_b(int p_154354_0_, int p_154354_1_) {
/* 350 */     if (p_154354_1_ == 0)
/*     */     {
/* 352 */       return 0;
/*     */     }
/* 354 */     if (p_154354_0_ == 0)
/*     */     {
/* 356 */       return p_154354_1_;
/*     */     }
/*     */ 
/*     */     
/* 360 */     if (p_154354_0_ < 0)
/*     */     {
/* 362 */       p_154354_1_ *= -1;
/*     */     }
/*     */     
/* 365 */     int i = p_154354_0_ % p_154354_1_;
/* 366 */     return (i == 0) ? p_154354_0_ : (p_154354_0_ + p_154354_1_ - i);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int func_180183_b(float p_180183_0_, float p_180183_1_, float p_180183_2_) {
/* 372 */     return func_180181_b(floor_float(p_180183_0_ * 255.0F), floor_float(p_180183_1_ * 255.0F), floor_float(p_180183_2_ * 255.0F));
/*     */   }
/*     */ 
/*     */   
/*     */   public static int func_180181_b(int p_180181_0_, int p_180181_1_, int p_180181_2_) {
/* 377 */     int i = (p_180181_0_ << 8) + p_180181_1_;
/* 378 */     i = (i << 8) + p_180181_2_;
/* 379 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int func_180188_d(int p_180188_0_, int p_180188_1_) {
/* 384 */     int i = (p_180188_0_ & 0xFF0000) >> 16;
/* 385 */     int j = (p_180188_1_ & 0xFF0000) >> 16;
/* 386 */     int k = (p_180188_0_ & 0xFF00) >> 8;
/* 387 */     int l = (p_180188_1_ & 0xFF00) >> 8;
/* 388 */     int i1 = (p_180188_0_ & 0xFF) >> 0;
/* 389 */     int j1 = (p_180188_1_ & 0xFF) >> 0;
/* 390 */     int k1 = (int)(i * j / 255.0F);
/* 391 */     int l1 = (int)(k * l / 255.0F);
/* 392 */     int i2 = (int)(i1 * j1 / 255.0F);
/* 393 */     return p_180188_0_ & 0xFF000000 | k1 << 16 | l1 << 8 | i2;
/*     */   }
/*     */ 
/*     */   
/*     */   public static double func_181162_h(double p_181162_0_) {
/* 398 */     return p_181162_0_ - Math.floor(p_181162_0_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static long getPositionRandom(Vec3i pos) {
/* 403 */     return getCoordinateRandom(pos.getX(), pos.getY(), pos.getZ());
/*     */   }
/*     */ 
/*     */   
/*     */   public static long getCoordinateRandom(int x, int y, int z) {
/* 408 */     long i = (x * 3129871) ^ z * 116129781L ^ y;
/* 409 */     i = i * i * 42317861L + i * 11L;
/* 410 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public static UUID getRandomUuid(Random rand) {
/* 415 */     long i = rand.nextLong() & 0xFFFFFFFFFFFF0FFFL | 0x4000L;
/* 416 */     long j = rand.nextLong() & 0x3FFFFFFFFFFFFFFFL | Long.MIN_VALUE;
/* 417 */     return new UUID(i, j);
/*     */   }
/*     */ 
/*     */   
/*     */   public static double func_181160_c(double p_181160_0_, double p_181160_2_, double p_181160_4_) {
/* 422 */     return (p_181160_0_ - p_181160_2_) / (p_181160_4_ - p_181160_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static double func_181159_b(double p_181159_0_, double p_181159_2_) {
/* 427 */     double d0 = p_181159_2_ * p_181159_2_ + p_181159_0_ * p_181159_0_;
/*     */     
/* 429 */     if (Double.isNaN(d0))
/*     */     {
/* 431 */       return Double.NaN;
/*     */     }
/*     */ 
/*     */     
/* 435 */     boolean flag = (p_181159_0_ < 0.0D);
/*     */     
/* 437 */     if (flag)
/*     */     {
/* 439 */       p_181159_0_ = -p_181159_0_;
/*     */     }
/*     */     
/* 442 */     boolean flag1 = (p_181159_2_ < 0.0D);
/*     */     
/* 444 */     if (flag1)
/*     */     {
/* 446 */       p_181159_2_ = -p_181159_2_;
/*     */     }
/*     */     
/* 449 */     boolean flag2 = (p_181159_0_ > p_181159_2_);
/*     */     
/* 451 */     if (flag2) {
/*     */       
/* 453 */       double d1 = p_181159_2_;
/* 454 */       p_181159_2_ = p_181159_0_;
/* 455 */       p_181159_0_ = d1;
/*     */     } 
/*     */     
/* 458 */     double d9 = func_181161_i(d0);
/* 459 */     p_181159_2_ *= d9;
/* 460 */     p_181159_0_ *= d9;
/* 461 */     double d2 = field_181163_d + p_181159_0_;
/* 462 */     int i = (int)Double.doubleToRawLongBits(d2);
/* 463 */     double d3 = field_181164_e[i];
/* 464 */     double d4 = field_181165_f[i];
/* 465 */     double d5 = d2 - field_181163_d;
/* 466 */     double d6 = p_181159_0_ * d4 - p_181159_2_ * d5;
/* 467 */     double d7 = (6.0D + d6 * d6) * d6 * 0.16666666666666666D;
/* 468 */     double d8 = d3 + d7;
/*     */     
/* 470 */     if (flag2)
/*     */     {
/* 472 */       d8 = 1.5707963267948966D - d8;
/*     */     }
/*     */     
/* 475 */     if (flag1)
/*     */     {
/* 477 */       d8 = Math.PI - d8;
/*     */     }
/*     */     
/* 480 */     if (flag)
/*     */     {
/* 482 */       d8 = -d8;
/*     */     }
/*     */     
/* 485 */     return d8;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static double func_181161_i(double p_181161_0_) {
/* 491 */     double d0 = 0.5D * p_181161_0_;
/* 492 */     long i = Double.doubleToRawLongBits(p_181161_0_);
/* 493 */     i = 6910469410427058090L - (i >> 1L);
/* 494 */     p_181161_0_ = Double.longBitsToDouble(i);
/* 495 */     p_181161_0_ *= 1.5D - d0 * p_181161_0_ * p_181161_0_;
/* 496 */     return p_181161_0_;
/*     */   }
/*     */   
/*     */   public static int func_181758_c(float p_181758_0_, float p_181758_1_, float p_181758_2_) {
/*     */     float f4, f5, f6;
/* 501 */     int j, k, l, i = (int)(p_181758_0_ * 6.0F) % 6;
/* 502 */     float f = p_181758_0_ * 6.0F - i;
/* 503 */     float f1 = p_181758_2_ * (1.0F - p_181758_1_);
/* 504 */     float f2 = p_181758_2_ * (1.0F - f * p_181758_1_);
/* 505 */     float f3 = p_181758_2_ * (1.0F - (1.0F - f) * p_181758_1_);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 510 */     switch (i) {
/*     */       
/*     */       case 0:
/* 513 */         f4 = p_181758_2_;
/* 514 */         f5 = f3;
/* 515 */         f6 = f1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 552 */         j = clamp_int((int)(f4 * 255.0F), 0, 255);
/* 553 */         k = clamp_int((int)(f5 * 255.0F), 0, 255);
/* 554 */         l = clamp_int((int)(f6 * 255.0F), 0, 255);
/* 555 */         return j << 16 | k << 8 | l;case 1: f4 = f2; f5 = p_181758_2_; f6 = f1; j = clamp_int((int)(f4 * 255.0F), 0, 255); k = clamp_int((int)(f5 * 255.0F), 0, 255); l = clamp_int((int)(f6 * 255.0F), 0, 255); return j << 16 | k << 8 | l;case 2: f4 = f1; f5 = p_181758_2_; f6 = f3; j = clamp_int((int)(f4 * 255.0F), 0, 255); k = clamp_int((int)(f5 * 255.0F), 0, 255); l = clamp_int((int)(f6 * 255.0F), 0, 255); return j << 16 | k << 8 | l;case 3: f4 = f1; f5 = f2; f6 = p_181758_2_; j = clamp_int((int)(f4 * 255.0F), 0, 255); k = clamp_int((int)(f5 * 255.0F), 0, 255); l = clamp_int((int)(f6 * 255.0F), 0, 255); return j << 16 | k << 8 | l;case 4: f4 = f3; f5 = f1; f6 = p_181758_2_; j = clamp_int((int)(f4 * 255.0F), 0, 255); k = clamp_int((int)(f5 * 255.0F), 0, 255); l = clamp_int((int)(f6 * 255.0F), 0, 255); return j << 16 | k << 8 | l;case 5: f4 = p_181758_2_; f5 = f1; f6 = f2; j = clamp_int((int)(f4 * 255.0F), 0, 255); k = clamp_int((int)(f5 * 255.0F), 0, 255); l = clamp_int((int)(f6 * 255.0F), 0, 255); return j << 16 | k << 8 | l;
/*     */     } 
/*     */     throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + p_181758_0_ + ", " + p_181758_1_ + ", " + p_181758_2_);
/*     */   }
/*     */   static {
/* 560 */     for (int i = 0; i < 65536; i++)
/*     */     {
/* 562 */       SIN_TABLE[i] = (float)Math.sin(i * Math.PI * 2.0D / 65536.0D);
/*     */     }
/*     */     
/* 565 */     for (int j = 0; j < 4096; j++)
/*     */     {
/* 567 */       SIN_TABLE_FAST[j] = (float)Math.sin(((j + 0.5F) / 4096.0F * 6.2831855F));
/*     */     }
/*     */     
/* 570 */     for (int l = 0; l < 360; l += 90)
/*     */     {
/* 572 */       SIN_TABLE_FAST[(int)(l * 11.377778F) & 0xFFF] = (float)Math.sin((l * 0.017453292F)); } 
/*     */   }
/*     */   
/* 575 */   private static final int[] multiplyDeBruijnBitPosition = new int[] { 0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9 };
/* 576 */   private static final double field_181163_d = Double.longBitsToDouble(4805340802404319232L);
/* 577 */   private static final double[] field_181164_e = new double[257];
/* 578 */   private static final double[] field_181165_f = new double[257]; private static final String __OBFID = "CL_00001496";
/*     */   static {
/* 580 */     for (int k = 0; k < 257; k++) {
/*     */       
/* 582 */       double d1 = k / 256.0D;
/* 583 */       double d0 = Math.asin(d1);
/* 584 */       field_181165_f[k] = Math.cos(d0);
/* 585 */       field_181164_e[k] = d0;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraf\\util\MathHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */